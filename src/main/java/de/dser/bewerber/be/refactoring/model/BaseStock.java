package de.dser.bewerber.be.refactoring.model;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.PropertyUtils;

import de.dser.bewerber.be.refactoring.NumberFormatUtil;

public class BaseStock implements Cloneable {
    private static final long serialVersionUID = -3717307572295622667L;
    private Long updateId = Long.valueOf(-1L);
    private String wkn;
    private String isin;
    private String name;
    private StockCategory category;
    private StockProperty branche;
    private StockProperty country;
    private StockProperty currency;
    private String bankProductType;
    private StockType type;
    private String area;
    private String emitter;
    private int rating = -1;
    private String folder;
    private Integer riskClass = Integer.valueOf(-1);
    private Date firstDate;
    private String firstDateStr;
    private Date lastDate;
    private String lastDateStr;
    private Double lastPrice = Double.valueOf(-1.0D);
    private String lastPriceStr;
    private Double originaleLastPrice;
    private String originaleLastPriceStr;
    private boolean optimizeAble = false;
    private TopHoldings topHoldings;
    private Date runtimeEnd;
    private PayoutType payoutType;
    private Double coupon;
    private StockProperty investmentPeriod;
    private Double orderBuyValue;
    private Double orderAcquisitionValue;
    private Double orderDispositionValue;
    private Double orderDenominationValue;
    private String orderDenominationCurrency;
    private boolean tradingRestrictionInChunks;
    private StockRestrictionOrderPresetType orderPresetType;
    private Boolean blockTrading;
    private Boolean memberOfStockUniverse;
    private Boolean certificate;
    private Date certificateSubscriptionStart;
    private Date certificateSubscriptionFinish;
    private Boolean etf;
    private Date tradingStart;
    private Date tradingEnd;
    private Date saleStart;
    private Boolean stock;
    private Boolean bond;
    private List<StockCategoryType> stockCategoryTypes;
    private Double fee;
    private Double kickback;
    private Double issueSurcharge;
    private Double minOfOptimization;
    private Double maxOfOptimization;
    private double nominalValue;

    public BaseStock() {
    }

    public void copyProperties(BaseStock stock) {
        try {
            PropertyUtils.copyProperties(this, stock);
        } catch (Exception var3) {
            throw new RuntimeException("fail to copy properties", var3);
        }
    }

    public Long getUpdateId() {
        return this.updateId;
    }

    public void setUpdateId(long updateId) {
        this.updateId = new Long(updateId);
    }

    public StockCategory getCategory() {
        return this.category;
    }

    public void setCategory(StockCategory category) {
        this.category = category;
    }

    public String getBankProductType() {
        return this.bankProductType;
    }

    public void setBankProductType(String bankProductType) {
        this.bankProductType = bankProductType;
    }

    public Date getFirstDate() {
        return this.firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
        this.firstDateStr = DateFormat.getDateInstance(2, new Locale("de", "DE")).format(firstDate);
    }

    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getIsin() {
        return this.isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Date getLastDate() {
        return this.lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
        this.lastDateStr = DateFormat.getDateInstance(2, new Locale("de", "DE")).format(lastDate);
    }

    public Double getLastPrice() {
        return this.lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
        String str = NumberFormatUtil.exactFormat(lastPrice.doubleValue(), 3, 3);
        this.lastPriceStr = str.substring(0, str.length() - 1);
    }

    public String getLastPriceStr() {
        return this.lastPriceStr;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRiskClass() {
        return this.riskClass;
    }

    public void setRiskClass(Integer riskClass) {
        this.riskClass = riskClass;
    }

    public String getWkn() {
        return this.wkn;
    }

    public void setWkn(String wkn) {
        this.wkn = wkn;
    }

    public String getFirstDateStr() {
        return this.firstDateStr;
    }

    public String getLastDateStr() {
        return this.lastDateStr;
    }

    public Double getOriginaleLastPrice() {
        return this.originaleLastPrice;
    }

    public void setOriginaleLastPrice(Double convertedLastPrice) {
        this.originaleLastPrice = convertedLastPrice;
        String str = NumberFormatUtil.exactFormat(convertedLastPrice.doubleValue(), 3, 3);
        this.originaleLastPriceStr = str.substring(0, str.length() - 1);
    }

    public String getOriginaleLastPriceStr() {
        return this.originaleLastPriceStr;
    }

    public StockType getType() {
        return this.type;
    }

    public String getArea() {
        return this.area;
    }

    public String getEmitter() {
        return this.emitter;
    }

    public int getRating() {
        return this.rating;
    }

    public void setType(StockType type) {
        this.type = type;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setEmitter(String emitter) {
        this.emitter = emitter;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isoptimizeable() {
        return this.optimizeAble;
    }

    public void setOptimizeAble(boolean optimizeAble) {
        this.optimizeAble = optimizeAble;
    }

    public TopHoldings getTopHoldings() {
        return this.topHoldings;
    }

    public void setTopHoldings(TopHoldings topHoldings) {
        this.topHoldings = topHoldings;
    }

    public StockProperty getBranche() {
        return this.branche;
    }

    public void setBranche(StockProperty branche) {
        this.branche = branche;
    }

    public StockProperty getCountry() {
        return this.country;
    }

    public void setCountry(StockProperty country) {
        this.country = country;
    }

    public StockProperty getCurrency() {
        return this.currency;
    }

    public void setCurrency(StockProperty currency) {
        this.currency = currency;
    }

    public Date getRuntimeEnd() {
        return this.runtimeEnd;
    }

    public void setRuntimeEnd(Date duration) {
        this.runtimeEnd = duration;
    }

    public PayoutType getPayoutType() {
        return this.payoutType;
    }

    public void setPayoutType(PayoutType payoutType) {
        this.payoutType = payoutType;
    }

    public Double getCoupon() {
        return this.coupon;
    }

    public void setCoupon(Double coupon) {
        this.coupon = coupon;
    }

    public StockProperty getInvestmentPeriod() {
        return this.investmentPeriod;
    }

    public void setInvestmentPeriod(StockProperty investmentPeriod) {
        this.investmentPeriod = investmentPeriod;
    }

    public Double getOrderBuyValue() {
        return this.orderBuyValue;
    }

    public void setOrderBuyValue(Double orderBuyValue) {
        this.orderBuyValue = orderBuyValue;
    }

    public Double getOrderAcquisitionValue() {
        return this.orderAcquisitionValue;
    }

    public void setOrderAcquisitionValue(Double orderAcquisitionValue) {
        this.orderAcquisitionValue = orderAcquisitionValue;
    }

    public Double getOrderDispositionValue() {
        return this.orderDispositionValue;
    }

    public void setOrderDispositionValue(Double orderDispositionValue) {
        this.orderDispositionValue = orderDispositionValue;
    }

    public Double getOrderDenominationValue() {
        return this.orderDenominationValue;
    }

    public void setOrderDenominationValue(Double orderDenominationValue) {
        this.orderDenominationValue = orderDenominationValue;
    }

    public String getOrderDenominationCurrency() {
        return this.orderDenominationCurrency;
    }

    public void setOrderDenominationCurrency(String orderDenominationCurrency) {
        this.orderDenominationCurrency = orderDenominationCurrency;
    }

    public boolean isTradingRestrictionInChunks() {
        return this.tradingRestrictionInChunks;
    }

    public void setTradingRestrictionInChunks(boolean tradingRestrictionInChunks) {
        this.tradingRestrictionInChunks = tradingRestrictionInChunks;
    }

    public StockRestrictionOrderPresetType getOrderPresetType() {
        return this.orderPresetType;
    }

    public void setOrderPresetType(StockRestrictionOrderPresetType orderPresetType) {
        this.orderPresetType = orderPresetType;
    }

    public Boolean getBlockTrading() {
        return this.blockTrading;
    }

    public void setBlockTrading(Boolean blockTrading) {
        this.blockTrading = blockTrading;
    }

    public Boolean getMemberOfStockUniverse() {
        return this.memberOfStockUniverse;
    }

    public void setMemberOfStockUniverse(Boolean memberOfStockUniverse) {
        this.memberOfStockUniverse = memberOfStockUniverse;
    }

    public Boolean getCertificate() {
        return this.certificate;
    }

    public void setCertificate(Boolean certificate) {
        this.certificate = certificate;
    }

    public Date getCertificateSubscriptionStart() {
        return this.certificateSubscriptionStart;
    }

    public void setCertificateSubscriptionStart(Date certificateSubscriptionStart) {
        this.certificateSubscriptionStart = certificateSubscriptionStart;
    }

    public Date getCertificateSubscriptionFinish() {
        return this.certificateSubscriptionFinish;
    }

    public void setCertificateSubscriptionFinish(Date certificateSubscriptionFinish) {
        this.certificateSubscriptionFinish = certificateSubscriptionFinish;
    }

    public Boolean getEtf() {
        return this.etf;
    }

    public void setEtf(Boolean etf) {
        this.etf = etf;
    }

    public Date getTradingStart() {
        return this.tradingStart;
    }

    public void setTradingStart(Date tradingStart) {
        this.tradingStart = tradingStart;
    }

    public Date getTradingEnd() {
        return this.tradingEnd;
    }

    public void setTradingEnd(Date tradingEnd) {
        this.tradingEnd = tradingEnd;
    }

    public Date getSaleStart() {
        return this.saleStart;
    }

    public void setSaleStart(Date saleStart) {
        this.saleStart = saleStart;
    }

    public Boolean getStock() {
        return this.stock;
    }

    public void setStock(Boolean stock) {
        this.stock = stock;
    }

    public Boolean getBond() {
        return this.bond;
    }

    public void setBond(Boolean bond) {
        this.bond = bond;
    }

    public List<StockCategoryType> getStockCategoryTypes() {
        return this.stockCategoryTypes;
    }

    public void setStockCategoryTypes(List<StockCategoryType> stockCategoryTypes) {
        this.stockCategoryTypes = stockCategoryTypes;
    }

    public Double getMinOfOptimization() {
        return this.minOfOptimization;
    }

    public void setMinOfOptimization(Double minOfOptimization) {
        this.minOfOptimization = minOfOptimization;
    }

    public Double getMaxOfOptimization() {
        return this.maxOfOptimization;
    }

    public void setMaxOfOptimization(Double maxOfOptimization) {
        this.maxOfOptimization = maxOfOptimization;
    }

    public double getNominalValue() {
        return this.nominalValue;
    }

    public void setNominalValue(double nominalValue) {
        this.nominalValue = nominalValue;
    }

    public double getCalculatedPriceByStockType() {
        return StockType.BOND.equals(this.getType())?(new Money(this.getLastPrice().doubleValue() / this.getNominalValue())).getValue().doubleValue():this.getLastPrice().doubleValue();
    }

    public boolean equals(Object obj) {
        return obj instanceof BaseStock?((BaseStock)obj).getWkn().equals(this.getWkn()):false;
    }

    public int hashCode() {
        return (new String(BaseStock.class.getName() + ":" + this.getWkn())).hashCode();
    }

    public String toString() {
        return MessageFormat.format("BaseStock [wkn:{0}, isin:{1}, name:{2}]", new Object[]{this.getWkn(), this.getIsin(), this.getName()});
    }

    public BaseStock clone() {
        BaseStock baseStock = new BaseStock();
        baseStock.copyProperties(this);
        baseStock.setCategory(this.getCategory().clone());
        if(this.getTopHoldings() != null) {
            baseStock.setTopHoldings((TopHoldings)this.getTopHoldings().clone());
        }

        return baseStock;
    }
}

