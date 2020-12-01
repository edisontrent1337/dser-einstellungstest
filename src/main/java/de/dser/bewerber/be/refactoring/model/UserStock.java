package de.dser.bewerber.be.refactoring.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;
import org.jdom.Element;

import de.dser.bewerber.be.refactoring.NumberFormatUtil;

public class UserStock extends BaseStock  {
    private static final long serialVersionUID = -6103161396233395670L;
    private double amount = 0.0D;
    private String amountStr = "";
    private Date purchaseDate = null;
    private String purchaseDateStr = null;
    private double purchaseCourse = 0.0D;
    private String purchaseCourseStr = "";
    private Double yield = null;
    private String yieldStr = "";
    private double depotValue = 0.0D;
    private String depotValueStr = "";
    private double depotPart = 0.0D;
    private String depotPartStr = "";
    private boolean blockOptimization = false;
    private boolean blockSales = false;
    private double freeToSale;
    private String freeToSaleStr;
    private StockBalance stockBalance;

    public UserStock() {
    }

    public static UserStock createInstanceFromStock(BaseStock stock) {
        UserStock userStock = new UserStock();
        userStock.copyProperties(stock);
        return userStock;
    }

    public void copyProperties(BaseStock stock) {
        try {
            PropertyUtils.copyProperties(this, stock);
        } catch (Exception var3) {
            throw new RuntimeException("fail to copy properties", var3);
        }
    }

    public void copyProperties(UserStock stock) {
        try {
            PropertyUtils.copyProperties(this, stock);
        } catch (Exception var3) {
            throw new RuntimeException("fail to copy properties", var3);
        }
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        this.amountStr = NumberFormatUtil.exactFormat(amount, 2, 2);
    }

    public String getAmountStr() {
        return this.amountStr;
    }

    public double getDepotPart() {
        return this.depotPart;
    }

    public void setDepotPart(double depotPart) {
        this.depotPart = depotPart;
        this.depotPartStr = NumberFormatUtil.exactFormat(depotPart, 2, 2);
    }

    public double getDepotValue() {
        return this.depotValue;
    }

    public void setDepotValue(double depotValue) {
        this.depotValue = depotValue;
        this.depotValueStr = NumberFormatUtil.exactFormat(depotValue, 2, 2);
    }

    public double getPurchaseCourse() {
        return this.purchaseCourse;
    }

    public void setPurchaseCourse(double purchaseCourse) {
        this.purchaseCourse = purchaseCourse;
        String str = NumberFormatUtil.exactFormat(purchaseCourse, 3, 3);
        this.purchaseCourseStr = str.substring(0, str.length() - 1);
    }

    public Double getYield() {
        return this.yield;
    }

    public Double getYieldValue() {
        return this.yield == null?null:Double.valueOf(this.yield.doubleValue() / 100.0D);
    }

    public void setYield(Double yield) {
        this.yield = yield;
        String str = "";
        if(yield != null) {
            str = NumberFormatUtil.exactFormat(yield.doubleValue(), 2, 2);
        }

        this.yieldStr = str;
    }

    public Date getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
        if(purchaseDate == null) {
            this.purchaseDateStr = "";
        } else {
            this.purchaseDateStr = DateFormat.getDateInstance(2, new Locale("de", "DE")).format(purchaseDate);
        }

    }

    public String getPurchaseDateStr() {
        return this.purchaseDateStr;
    }

    public String getDepotPartStr() {
        return this.depotPartStr;
    }

    public String getDepotValueStr() {
        return this.depotValueStr;
    }

    public String getPurchaseCourseStr() {
        return this.purchaseCourseStr;
    }

    public String getYieldStr() {
        return this.yieldStr;
    }

    public boolean isBlockOptimization() {
        return this.blockOptimization;
    }

    public void setBlockOptimization(boolean blockOptimization) {
        this.blockOptimization = blockOptimization;
    }

    public boolean isBlockSales() {
        return this.blockSales;
    }

    public void setBlockSales(boolean blockSales) {
        this.blockSales = blockSales;
    }

    public double getFreeToSale() {
        return this.freeToSale;
    }

    public void setFreeToSale(double freeToSale) {
        this.freeToSale = freeToSale;
        String str = NumberFormatUtil.exactFormat(freeToSale, 3, 3);
        this.freeToSaleStr = str.substring(0, str.length() - 1);
    }

    public String getFreeToSaleStr() {
        return this.freeToSaleStr;
    }

    public StockBalance getStockBalance() {
        return this.stockBalance;
    }

    public void setStockBalance(StockBalance stockBalance) {
        this.stockBalance = stockBalance;
    }

    public Element toXML() {
        Element stock = new Element("stock");
        Element element = new Element("wkn");
        element.addContent(this.getWkn());
        stock.addContent(element);
        element = new Element("isin");
        element.addContent(this.getIsin());
        stock.addContent(element);
        element = new Element("name");
        element.addContent(this.getName());
        stock.addContent(element);
        element = new Element("class");
        element.addContent(this.getCategory().getName());
        stock.addContent(element);
        element = new Element("folder");
        element.addContent(this.getFolder());
        stock.addContent(element);
        element = new Element("riskClass");
        element.addContent("" + this.getRiskClass());
        stock.addContent(element);
        element = new Element("lastDate");
        element.addContent(this.getLastDateStr());
        stock.addContent(element);
        element = new Element("lastPrice");
        if(this.getType() == null || !this.getType().equals(StockType.MODELED_STOCK) && !this.getType().equals(StockType.NONFUNGIBLE)) {
            element.addContent(this.getLastPriceStr());
        } else {
            element.addContent("");
        }

        stock.addContent(element);
        element = new Element("amount");
        if(this.getType() != null && this.getType().equals(StockType.MODELED_STOCK)) {
            element.addContent("");
        } else {
            element.addContent(this.getAmountStr());
        }

        stock.addContent(element);
        element = new Element("purchaseDate");
        if(this.getType() != null && this.getType().equals(StockType.MODELED_STOCK)) {
            element.addContent("");
        } else {
            element.addContent(this.getPurchaseDateStr());
        }

        stock.addContent(element);
        element = new Element("purchaseCourse");
        if(this.getType() == null || !this.getType().equals(StockType.MODELED_STOCK) && !this.getType().equals(StockType.NONFUNGIBLE)) {
            element.addContent(this.getPurchaseCourseStr());
        } else {
            element.addContent("");
        }

        stock.addContent(element);
        element = new Element("depotValue");
        element.addContent(this.getDepotValueStr());
        stock.addContent(element);
        element = new Element("depotPart");
        element.addContent(this.getDepotPartStr());
        element = new Element("blockOptimization");
        element.addContent("" + this.isBlockOptimization());
        element = new Element("blockSales");
        element.addContent("" + this.isBlockSales());
        element = new Element("freeToSale");
        element.addContent("" + this.getFreeToSaleStr());
        stock.addContent(element);
        return stock;
    }

    public int hashCode() {
        return (new String(UserStock.class.getName() + ":" + this.getWkn())).hashCode();
    }

    public UserStock clone() {
        UserStock stock = new UserStock();

        try {
            PropertyUtils.copyProperties(stock, this);
            return stock;
        } catch (Exception var3) {
            throw new RuntimeException("fail to copy properties", var3);
        }
    }
}
