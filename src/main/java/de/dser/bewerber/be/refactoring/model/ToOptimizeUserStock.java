package de.dser.bewerber.be.refactoring.model;

import java.text.MessageFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Element;

import de.dser.bewerber.be.refactoring.NumberFormatUtil;

public class ToOptimizeUserStock extends UserStock implements ToOptimizeStock {

	private static final long serialVersionUID = 54195404897067869L;

	private double minimum = 0d;

	private double maximum = 100d;

	private double minimumProportionToBy;

	private boolean autoAdjustMinimum = true;

	private boolean autoAdjustMaximum = true;

	private boolean autoAdjustMinimumProportionToBy = true;

	private int preadjustment = PREADJUSTMENT_MANUALLY;

	private boolean currentlyAdjustedMinimum;

	private boolean currentlyAdjustedMaximum;

	private boolean disableRestrictionEditing = false;

	private boolean disablePreadjustmentEditing = false;

	private boolean preadjustmentChanged = false;

	public ToOptimizeUserStock() {
		super();
	}

	public static ToOptimizeUserStock createInstanceFromStock(BaseStock stock) {
		ToOptimizeUserStock userStock = new ToOptimizeUserStock();
		userStock.copyProperties(stock);
		userStock.setPreadjustment(mapOrderPresetType(stock.getOrderPresetType()));
		return userStock;
	}

	public static ToOptimizeUserStock createInstanceFromStock(UserStock stock) {
		ToOptimizeUserStock userStock = new ToOptimizeUserStock();
		userStock.copyProperties(stock);
		userStock.setPreadjustment(mapOrderPresetType(stock.getOrderPresetType()));
		return userStock;
	}

	private static int mapOrderPresetType(final StockRestrictionOrderPresetType type) {
		if (type == null) {
			return PREADJUSTMENT_MANUALLY;
		}

		switch (type) {
			case Buy:
				return PREADJUSTMENT_NOSALE;
			case Keep:
				return PREADJUSTMENT_HOLD;
			case Sale:
				return PREADJUSTMENT_NOBUY;
		}

		return PREADJUSTMENT_MANUALLY;
	}

	public void copyProperties(ToOptimizeUserStock stock) {
		try {
			PropertyUtils.copyProperties(this, stock);
			this.setPreadjustment(mapOrderPresetType(stock.getOrderPresetType()));
		} catch (Exception e) {
			throw new RuntimeException("fail to copy properties", e);
		}
	}

	public void copyProperties(UserStock stock) {
		try {
			super.copyProperties(stock);
			this.setPreadjustment(mapOrderPresetType(stock.getOrderPresetType()));
		} catch (Exception e) {
			throw new RuntimeException("fail to copy properties", e);
		}
	}


	public double getDepotValue() {
		if (preadjustment == ToOptimizeStock.IGNORE) {
			return 0d;
		}
		return super.getDepotValue();
	}

	public double getMaximum() {
		return this.maximum;
	}

	public double getMinimum() {
		return this.minimum;
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
	}

	public void setMinimum(double minimum) {
		this.minimum = minimum;
	}

	public double getMinimumProportionToBy() {
		return this.minimumProportionToBy;
	}

	public void setMinimumProportionToBy(double minimumProportionToBy) {
		this.minimumProportionToBy = minimumProportionToBy;
	}

	public boolean isAutoAdjustMinimum() {
		return this.autoAdjustMinimum;
	}

	public void setAutoAdjustMinimum(boolean autoAdjustMinimum) {
		this.autoAdjustMinimum = autoAdjustMinimum;
	}

	public boolean isAutoAdjustMaximum() {
		return this.autoAdjustMaximum;
	}

	public void setAutoAdjustMaximum(boolean autoAdjustMaximum) {
		this.autoAdjustMaximum = autoAdjustMaximum;
	}

	public boolean isAutoAdjustMinimumProportionToBy() {
		return this.autoAdjustMinimumProportionToBy;
	}

	public void setAutoAdjustMinimumProportionToBy(boolean autoAdjustMinimumProportionToBy) {
		this.autoAdjustMinimumProportionToBy = autoAdjustMinimumProportionToBy;
	}

	public int getPreadjustment() {
		return preadjustment;
	}

	public void setPreadjustment(int preadjustment) {
		this.preadjustment = preadjustment;
	}

	public boolean isPreadjustmentChanged() {
		return preadjustmentChanged;
	}

	public void setPreadjustmentChanged(boolean preadjustmentChanged) {
		this.preadjustmentChanged = preadjustmentChanged;
	}

	public boolean isCurrentlyAdjustedMinimum() {
		return currentlyAdjustedMinimum;
	}

	public void setCurrentlyAdjustedMinimum(boolean currentlyAdjusted) {
		this.currentlyAdjustedMinimum = currentlyAdjusted;
	}


	public boolean isCurrentlyAdjustedMaximum() {
		return currentlyAdjustedMaximum;
	}

	public void setCurrentlyAdjustedMaximum(boolean currentlyAdjusted) {
		this.currentlyAdjustedMaximum = currentlyAdjusted;
	}

	public boolean isDisableRestrictionEditing() {
		return disableRestrictionEditing;
	}

	public void setDisableRestrictionEditing(boolean disableRestrictionEditing) {
		this.disableRestrictionEditing = disableRestrictionEditing;
	}

	public boolean isDisablePreadjustmentEditing() {
		return disablePreadjustmentEditing;
	}

	public void setDisablePreadjustmentEditing(boolean disablePreadjustmentEditing) {
		this.disablePreadjustmentEditing = disablePreadjustmentEditing;
	}

	public Double getManualMaximum() {
		return getMaxOfOptimization();
	}

	public Double getManualMinimum() {
		return getMinOfOptimization();
	}

	@Override
	public int hashCode() {
		return new String(ToOptimizeStock.class.getName() + ":" + getWkn()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BaseStock) {
			BaseStock mobj = (BaseStock) obj;
			return (mobj.getWkn().equals(this.getWkn()));
		}
		return false;
	}

	public ToOptimizeUserStock clone() {
		ToOptimizeUserStock stock = new ToOptimizeUserStock();
		stock.copyProperties(this);
		return stock;
	}

	@Override
	public Element toXML() {
		Element stock = new Element("stock");

		Element element = new Element("wkn");
		element.addContent(getWkn());
		stock.addContent(element);
		element = new Element("isin");
		element.addContent(getIsin());
		stock.addContent(element);
		element = new Element("name");
		element.addContent(getName());
		stock.addContent(element);
		element = new Element("class");
		element.addContent(getCategory().getName());
		stock.addContent(element);
		element = new Element("folder");
		element.addContent(getFolder());
		stock.addContent(element);
		element = new Element("riskClass");
		element.addContent("" + getRiskClass());
		stock.addContent(element);
		element = new Element("lastDate");
		element.addContent(getLastDateStr());
		stock.addContent(element);
		element = new Element("lastPrice");
		element.addContent(getLastPriceStr());
		stock.addContent(element);
		element = new Element("source");
		element.addContent("user");
		stock.addContent(element);
		element = new Element("minimum");
		element.addContent(NumberFormatUtil.exactFormat(getMinimum(), 2, 2));
		stock.addContent(element);
		element = new Element("maximum");
		element.addContent(NumberFormatUtil.exactFormat(getMaximum(), 2, 2));
		stock.addContent(element);

		return stock;
	}

	@Override
	public String toString() {
		return "ToOptimizeUserStock{" +
				"minimum=" + minimum +
				", maximum=" + maximum +
				", minimumProportionToBy=" + minimumProportionToBy +
				", autoAdjustMinimum=" + autoAdjustMinimum +
				", autoAdjustMaximum=" + autoAdjustMaximum +
				", autoAdjustMinimumProportionToBy=" + autoAdjustMinimumProportionToBy +
				", preadjustment=" + preadjustment +
				", currentlyAdjustedMinimum=" + currentlyAdjustedMinimum +
				", currentlyAdjustedMaximum=" + currentlyAdjustedMaximum +
				", disableRestrictionEditing=" + disableRestrictionEditing +
				", disablePreadjustmentEditing=" + disablePreadjustmentEditing +
				", preadjustmentChanged=" + preadjustmentChanged +
				'}';
	}
}
