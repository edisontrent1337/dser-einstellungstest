package de.dser.bewerber.be.refactoring.model;

import org.jdom.Element;

public interface ToOptimizeStock extends Cloneable, IStock {
	   
	   public static final int PREADJUSTMENT_MANUALLY = 0;
	   
	   public static final int PREADJUSTMENT_HOLD = 1;
	   
	   public static final int PREADJUSTMENT_NOBUY = 2;
	   
	   public static final int PREADJUSTMENT_NOSALE = 3;
	   
	   public static final int PREADJUSTMENT_LIQUIDATE = 4;
	   
	   public static final int PREADJUSTMENT_KEEP = 5;
	   
	   public static final int IGNORE = 6;

	   public double getMaximum();

	   public void setMaximum(double maximum);

	    public Double getManualMaximum();

	    public Double getManualMinimum();

	   public double getMinimum();

	   public void setMinimum(double minimum);

	   public double getMinimumProportionToBy();

	   public void setMinimumProportionToBy(double minimumProportionToBy);

	   public boolean isAutoAdjustMinimum();

	   public boolean isAutoAdjustMaximum();

	   public boolean isAutoAdjustMinimumProportionToBy();
	   
	   public boolean isCurrentlyAdjustedMinimum();
	   
	   public boolean isCurrentlyAdjustedMaximum();

	   public void setAutoAdjustMinimum(boolean automatically);

	   public void setAutoAdjustMaximum(boolean automatically);

	   public void setAutoAdjustMinimumProportionToBy(boolean automatically);
	   
	   public void setCurrentlyAdjustedMinimum(boolean adjusted);
	   
	   public void setCurrentlyAdjustedMaximum(boolean adjusted);

	   public int getPreadjustment();

	   public void setPreadjustment(int preadjustment);
	   
	   public boolean isPreadjustmentChanged();
	   
	   public void setPreadjustmentChanged(boolean preadjustmentChanged);
	   
	   public boolean isDisableRestrictionEditing();
	   
	   public void setDisableRestrictionEditing(boolean disableRestrictionEditing);
	   
	   public boolean isDisablePreadjustmentEditing();
	   
	   public void setDisablePreadjustmentEditing(boolean disablePreadjustmentEditing);

	   public ToOptimizeStock clone();

	   public Element toXML();
	}
