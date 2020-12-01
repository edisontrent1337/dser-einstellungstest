package de.dser.bewerber.be.refactoring;

import de.dser.bewerber.be.refactoring.model.ToOptimizeStock;

import java.util.ArrayList;
import java.util.List;

public class StockLoadingRepository {
	public List<ToOptimizeStock> loadStocksToAdjust(String optimizationId) {
		return new ArrayList<ToOptimizeStock>();
	}
}
