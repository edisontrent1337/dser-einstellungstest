package de.dser.bewerber.be.biz.controllers;

import com.google.common.collect.Lists;
import de.dser.bewerber.be.biz.controllers.model.DtoStockAsset;
import de.dser.bewerber.be.biz.controllers.model.DtoStockPrice;
import de.dser.bewerber.be.biz.controllers.util.CalendarUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MyBusinessLogicControllerTest {


	private MyBusinessLogicController businessLogicController;
	@Before
	public void setup() {
		businessLogicController = new MyBusinessLogicController(new CalendarUtil());
	}
	@Test
	public void testCalculateValueOfStockWithLatestPrice() throws Exception {
		List<DtoStockPrice> stockPrices = Lists.newArrayList(
				createDtoStockPrice("766400", "2016-01-01 09:32:12", 23d, "EUR"),
				createDtoStockPrice("766400", "2016-01-02 12:32:12", 25d, "EUR"),
				createDtoStockPrice("766400", "2016-01-03 14:32:12", 27.22d, "EUR")
				);
		double calculatedValue = businessLogicController.calculateValueOfStockWithLatestPrice(stockPrices, "766400", 234d);
		Assert.assertEquals(6369.48, calculatedValue, 0.000001);
	}
	
	@Test
	public void testCalculateValueOfStockWithLatestPrice_intermediateDayPrices() throws Exception {
		List<DtoStockPrice> stockPrices = Lists.newArrayList(
				createDtoStockPrice("766400", "2016-01-01 09:32:12", 23d, "EUR"),
				createDtoStockPrice("766400", "2016-01-03 12:32:12", 25d, "EUR"),
				createDtoStockPrice("766400", "2016-01-03 14:32:12", 27.22d, "EUR")
				);
		double calculatedValue = businessLogicController.calculateValueOfStockWithLatestPrice(stockPrices, "766400", 234d);
		Assert.assertEquals(6369.48, calculatedValue, 0.000001);
	}
	
	@Test
	public void testCalculateCurrentSumOfPortfolio() throws Exception {
		List<DtoStockAsset> assets = Lists.newArrayList(
				createStockAsset("766400", 2d), createStockAsset("A0FD23", 3.4d), createStockAsset("PAH004", 12d)
				);
		
		List<DtoStockPrice> prices = Lists.newArrayList(
				createDtoStockPrice("766400", "2016-01-01 09:32:12", 76d, "EUR"),
				createDtoStockPrice("PAH004", "2016-01-03 02:32:12", 23d, "EUR"),
				createDtoStockPrice("PAH004", "2016-01-06 09:32:12", 8d, "EUR"),
				createDtoStockPrice("710000", "2016-01-02 19:32:12", 23d, "EUR")
				);
		
		double sum = businessLogicController.calculateCurrentSumOfPortfolio(assets, prices);
		Assert.assertEquals(248.0, sum, 0.00001);
	}
	
	private DtoStockAsset createStockAsset(String wkn, Double amount) {
		DtoStockAsset ret = new DtoStockAsset();
		ret.setAmount(amount);
		ret.setWkn(wkn);
		return ret;
	}
	private DtoStockPrice createDtoStockPrice(String wkn, String dateTime, Double price, String currency) throws ParseException {
		DtoStockPrice ret = new DtoStockPrice();
		ret.setWkn(wkn);
		ret.setDateOfPrice(parseDateTime(dateTime));
		ret.setPrice(price);
		ret.setCurrency(currency);
		return ret;
	}
	private Calendar parseDateTime(String dateTime) throws ParseException {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
		
		return calendar;
	}
	

}
