package de.dser.bewerber.be.biz.controllers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import de.dser.bewerber.be.biz.controllers.model.DtoCalculationModel;
import de.dser.bewerber.be.biz.controllers.model.DtoStockAsset;
import de.dser.bewerber.be.biz.controllers.model.DtoStockPrice;
import de.dser.bewerber.be.biz.controllers.util.CalendarUtil;

import java.util.*;

public class MyBusinessLogicController {

	private CalendarUtil calendarUtil;

	public MyBusinessLogicController(CalendarUtil calendarUtil) {
		this.calendarUtil = calendarUtil;
	}

	public double calculateValueOfStockWithLatestPrice(List<DtoStockPrice> stockPrices, String wkn, double amount) {
		ImmutableListMultimap<Calendar, DtoStockPrice> timeToStock = Multimaps.index(Iterables.filter(stockPrices, createWknFilter(wkn)), createKeyByWknFunction());
		Calendar lastDate = Collections.max(timeToStock.keySet());
		ImmutableList<DtoStockPrice> dtoStockPrices = timeToStock.get(lastDate);
		return dtoStockPrices.get(dtoStockPrices.size() - 1).getPrice() * amount;
	}

	public double calculateCurrentSumOfPortfolio(List<DtoStockAsset> assets, List<DtoStockPrice> prices) {
		ImmutableListMultimap<String, DtoStockPrice> wknToPrices = Multimaps.index(prices, createWknKeyFunction());
		List<DtoCalculationModel> calculationModel = extractCalculationModel(assets, wknToPrices);
		return calculateSumValue(calculationModel);
	}


	private double calculateSumValue(List<DtoCalculationModel> calculationModel) {

		double sum = 0d;
		for (DtoCalculationModel dtoCalculationModel : calculationModel) {
			Double price = dtoCalculationModel.getPrice();
			if (price == null) {
				price = 0d;
			}
			if (checkPricePositiv(price))
				sum += calculateCurrentValue(price, dtoCalculationModel.getAmount());
		}

		return sum;
	}

	private boolean checkPricePositiv(double price) {
		return price > 0d;
	}

	private double calculateCurrentValue(double price, double amount) {
		return price * amount;
	}

	private List<DtoCalculationModel> extractCalculationModel(List<DtoStockAsset> assets, ImmutableListMultimap<String, DtoStockPrice> wknToPrices) {
		List<DtoCalculationModel> ret = new ArrayList<DtoCalculationModel>();

		for (DtoStockAsset dtoStockAsset : assets) {
			String wkn = dtoStockAsset.getWkn();
			DtoCalculationModel model = new DtoCalculationModel();
			model.setWkn(wkn);
			model.setAmount(dtoStockAsset.getAmount());

			ImmutableList<DtoStockPrice> immutableList = wknToPrices.get(wkn);
			if (immutableList != null && immutableList.size() > 0) {
				List<DtoStockPrice> stockPrices = Lists.newArrayList(immutableList);
				Collections.sort(stockPrices, createStockPriceComparator());
				DtoStockPrice lastPrice = stockPrices.get(0);
				model.setPrice(lastPrice.getPrice());
				model.setCurrency(lastPrice.getCurrency());
			}
			ret.add(model);

		}
		return ret;
	}

	private Comparator<? super DtoStockPrice> createStockPriceComparator() {
		return new Comparator<DtoStockPrice>() {
			public int compare(DtoStockPrice o1, DtoStockPrice o2) {
				return o1.getDateOfPrice().compareTo(o2.getDateOfPrice()) * -1;
			}
		};
	}

	private Function<? super DtoStockPrice, String> createWknKeyFunction() {
		return new Function<DtoStockPrice, String>() {
			public String apply(DtoStockPrice input) {
				return input.getWkn();
			}
		};
	}

	private Predicate<DtoStockPrice> createWknFilter(final String wknToFilter) {
		return new Predicate<DtoStockPrice>() {
			public boolean apply(DtoStockPrice input) {
				return input.getWkn().equals(wknToFilter);
			}
		};
	}

	private Function<? super DtoStockPrice, Calendar> createKeyByWknFunction() {
		return new Function<DtoStockPrice, Calendar>() {
			public Calendar apply(DtoStockPrice input) {
				return calendarUtil.normalizeDate(input.getDateOfPrice());
			}
		};
	}
}
