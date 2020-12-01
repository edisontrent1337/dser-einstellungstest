package de.dser.bewerber.be.refactoring;

import de.dser.bewerber.be.refactoring.model.ToOptimizeStock;
import de.dser.bewerber.be.refactoring.model.ToOptimizeUserStock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class RefractoringController {

	private final StockLoadingRepository stockLoadingRepository;

	public RefractoringController(StockLoadingRepository stockLoadingRepository) {
		this.stockLoadingRepository = stockLoadingRepository;
	}

	public void adjustPreAdjustments(String optimizationId, double capital, boolean restrictFavoriteStocksAndHighlightDeviation, List<Long> restrictedAssetCategoryIdList) {
		List<ToOptimizeStock> stocks = stockLoadingRepository.loadStocksToAdjust(optimizationId);
		BigDecimal availableCapital = BigDecimal.valueOf(capital);
		availableCapital = availableCapital.setScale(10, RoundingMode.HALF_UP);
		// das Kapital kann 0 sein, weil beispielsweise elaxy Bestaende ohne
		// Wert uebermittelt
		if (availableCapital.compareTo(BigDecimal.valueOf(0d)) > 0) {
			for (ToOptimizeStock stock : stocks) {
				if (stock instanceof ToOptimizeUserStock) {
					ToOptimizeUserStock userStock = (ToOptimizeUserStock) stock;
					BigDecimal value = BigDecimal.valueOf(userStock.getDepotValue());
					value = value.setScale(10, RoundingMode.HALF_UP);
					BigDecimal factor = BigDecimal.valueOf(100d);
					value = value.divide(availableCapital, 10, RoundingMode.HALF_UP).multiply(factor);

					performManualAdjustment(userStock, restrictFavoriteStocksAndHighlightDeviation, restrictedAssetCategoryIdList);

					int preAdjustment = stock.getPreadjustment();
					if (preAdjustment == ToOptimizeStock.PREADJUSTMENT_HOLD) {
						performHoldAdjustment(userStock, value);
					} else if (preAdjustment == ToOptimizeStock.PREADJUSTMENT_LIQUIDATE) {
						performLiquidateAdjustment(userStock);
					} else if (preAdjustment == ToOptimizeStock.PREADJUSTMENT_NOBUY) {
						performNoBuyAdjustment(userStock, value);
					} else if (preAdjustment == ToOptimizeStock.PREADJUSTMENT_NOSALE) {
						performNoSaleAdjustment(userStock, value);
					}
				} else if (!stock.isoptimizeable() && stock.getMinimum() != stock.getMaximum()) {
					stock.setMinimum(0d);
					stock.setMaximum(0d);
					stock.setCurrentlyAdjustedMinimum(true);
					stock.setCurrentlyAdjustedMaximum(true);
					stock.setAutoAdjustMaximum(false);
				}
			}
		}
	}

	public void performManualAdjustment(ToOptimizeStock userStock, boolean restrictFavoriteStocksAndHighlightDeviation, List<Long> restrictedAssetCategoryIdList) {
		if (!userStock.isoptimizeable() && userStock.getPreadjustment() == ToOptimizeStock.PREADJUSTMENT_MANUALLY) {
			if (restrictFavoriteStocksAndHighlightDeviation) {
				int preadjustment = ToOptimizeStock.PREADJUSTMENT_LIQUIDATE;
				if (restrictedAssetCategoryIdList.contains(userStock.getCategory().getId())) {
					preadjustment = ToOptimizeStock.PREADJUSTMENT_HOLD;
				}
				userStock.setPreadjustment(preadjustment);
			} else {
				if (userStock.getMinimum() != userStock.getMaximum()) {
					userStock.setPreadjustment(ToOptimizeStock.PREADJUSTMENT_HOLD);
				}
			}
		}
	}

	public void performHoldAdjustment(ToOptimizeUserStock userStock, BigDecimal value) {
		userStock.setMinimum(value.doubleValue());
		userStock.setMaximum(value.doubleValue());
		userStock.setCurrentlyAdjustedMinimum(true);
		userStock.setCurrentlyAdjustedMaximum(true);
		userStock.setAutoAdjustMaximum(false);
	}

	public void performLiquidateAdjustment(ToOptimizeStock userStock) {
		userStock.setMinimum(0d);
		userStock.setMaximum(0d);
		userStock.setCurrentlyAdjustedMinimum(true);
		userStock.setCurrentlyAdjustedMaximum(true);
		userStock.setAutoAdjustMaximum(false);
	}

	public void performNoBuyAdjustment(ToOptimizeStock userStock, BigDecimal value) {
		userStock.setAutoAdjustMaximum(false);
		if (value.doubleValue() < userStock.getMaximum()) {
			userStock.setMaximum(value.doubleValue());
			userStock.setCurrentlyAdjustedMaximum(true);
		}
		if (!userStock.isoptimizeable() && userStock.getMaximum() != userStock.getMinimum()) {
			userStock.setMinimum(userStock.getMaximum());
			userStock.setCurrentlyAdjustedMinimum(true);
		} else if (userStock.getMinimum() > userStock.getMaximum()) {
			userStock.setMinimum(userStock.getMaximum());
			userStock.setCurrentlyAdjustedMinimum(true);
		}
	}

	public void performNoSaleAdjustment(ToOptimizeStock userStock, BigDecimal value) {
		userStock.setAutoAdjustMaximum(false);
		if (value.doubleValue() > userStock.getMinimum() || userStock.isAutoAdjustMinimum()) {
			userStock.setMinimum(value.doubleValue());
			userStock.setCurrentlyAdjustedMinimum(true);
		}
		if (!userStock.isoptimizeable() && userStock.getMaximum() != userStock.getMinimum() ||
				userStock.getMaximum() < userStock.getMinimum()) {
			userStock.setMaximum(userStock.getMinimum());
			userStock.setCurrentlyAdjustedMaximum(true);
		}

	}
}
