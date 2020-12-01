package de.dser.bewerber.be.refactoring;

import de.dser.bewerber.be.refactoring.model.StockCategory;
import de.dser.bewerber.be.refactoring.model.ToOptimizeStock;
import de.dser.bewerber.be.refactoring.model.ToOptimizeUserStock;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RefractoringControllerTest {

	private static final String TEST_ID = "TEST_ID";
	private StockLoadingRepository repository = new StockLoadingRepository();
	private RefractoringController refractoringController = new RefractoringController(repository);
	private List<ToOptimizeStock> stocks;

	private ToOptimizeUserStock testStock;

	@Before
	public void cleanAndInit() {
		this.stocks = repository.loadStocksToAdjust(TEST_ID);
		this.testStock = new ToOptimizeUserStock();
		testStock.setOptimizeAble(true);
		testStock.setCategory(new StockCategory(1L, "testCategory"));

	}

	@Test
	public void testAdjustPreAdjustments() {

	}

	@Test
	public void testManualAdjustment() {
		testStock.setOptimizeAble(false);
		testStock.setPreadjustment(ToOptimizeStock.PREADJUSTMENT_MANUALLY);

		Long stockCategoryID = testStock.getCategory().getId();
		refractoringController.performManualAdjustment(testStock, true, Collections.singletonList(stockCategoryID));
		assertEquals(ToOptimizeStock.PREADJUSTMENT_HOLD, testStock.getPreadjustment());
	}


	@Test
	public void testNoSaleAdjustment() {
		// test application of minimum
		BigDecimal value = BigDecimal.valueOf(20);
		testStock.setMinimum(10);
		refractoringController.performNoSaleAdjustment(testStock, value);
		assertFalse(testStock.isAutoAdjustMaximum());
		assertEquals(value.doubleValue(), testStock.getMinimum(), 0.00001d);
		assertTrue(testStock.isCurrentlyAdjustedMinimum());

		// test application of maximum
		testStock = new ToOptimizeUserStock();
		testStock.setOptimizeAble(false);
		testStock.setMaximum(30);
		testStock.setMinimum(10);

		refractoringController.performNoSaleAdjustment(testStock, value);
		assertEquals(value.doubleValue(), testStock.getMinimum(), 0.0001f);
		assertEquals(value.doubleValue(), testStock.getMaximum(), 0.0001f);
		assertEquals(testStock.getMinimum(), testStock.getMaximum(), 0.0001f);

		// test application of maximum when maximum < minimum
		testStock = new ToOptimizeUserStock();
		testStock.setOptimizeAble(false);
		testStock.setMaximum(10);
		testStock.setMinimum(30);

		refractoringController.performNoSaleAdjustment(testStock, value);
		assertEquals(value.doubleValue(), testStock.getMinimum(), 0.0001f);
		assertEquals(value.doubleValue(), testStock.getMaximum(), 0.0001f);
		assertEquals(testStock.getMinimum(), testStock.getMaximum(), 0.0001f);
	}

	@Test
	public void testHoldAdjustment() {
		BigDecimal value = BigDecimal.valueOf(20);
		refractoringController.performHoldAdjustment(testStock, value);
		assertEquals(value.doubleValue(), testStock.getMinimum(), 0.0001d);
		assertEquals(value.doubleValue(), testStock.getMaximum(), 0.0001d);
		assertTrue(testStock.isCurrentlyAdjustedMaximum());
		assertTrue(testStock.isCurrentlyAdjustedMinimum());
		assertFalse(testStock.isAutoAdjustMaximum());
	}

	@Test
	public void testLiquidateAdjustment() {
		refractoringController.performLiquidateAdjustment(testStock);
		assertEquals(0, testStock.getMinimum(), 0.0001d);
		assertEquals(0, testStock.getMaximum(), 0.0001d);
		assertTrue(testStock.isCurrentlyAdjustedMaximum());
		assertTrue(testStock.isCurrentlyAdjustedMinimum());
		assertFalse(testStock.isAutoAdjustMaximum());
	}

	@Test
	public void testNoBuyAdjustment() {
		BigDecimal value = BigDecimal.valueOf(10);
		// test application of maximum
		testStock.setMaximum(20);
		refractoringController.performNoBuyAdjustment(testStock, value);
		assertFalse(testStock.isAutoAdjustMaximum());
		assertTrue(testStock.isCurrentlyAdjustedMaximum());
		assertEquals(10, testStock.getMaximum(), 0.00001d);

		// test application of minimum
		testStock = new ToOptimizeUserStock();
		testStock.setOptimizeAble(false);
		testStock.setMaximum(10);
		testStock.setMinimum(5);

		refractoringController.performNoBuyAdjustment(testStock, value);

		assertEquals(10, testStock.getMinimum(), 0.00001d);
		assertTrue(testStock.isCurrentlyAdjustedMinimum());
	}

}
