package de.dser.bewerber.be.refactoring.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import de.dser.bewerber.be.refactoring.math.IMathDataValue;

public class Money implements Serializable, IMathDataValue {
	private static final long serialVersionUID = 4060132330538414123L;

	private static final int DEFAULT_MONEY_SALE = 4;
	private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

	private final BigDecimal value;

    public Money() {
        this(ZERO.getValue());
    }

    public Money(final BigDecimal value) {
        this.value = value.setScale(DEFAULT_MONEY_SALE, DEFAULT_ROUNDING_MODE);
    }

    public Money(final String value) {
        this(new BigDecimal(value));
    }

    public Money(final double value) {
        this(new BigDecimal(value));
    }

    public BigDecimal getValue() {
        return value;
    }

    public Money add(final IMathDataValue augend) {
        return new Money(this.value.add(augend.getValue()));
    }

    public Money subtract(final IMathDataValue subtrahend) {
        return new Money(this.value.subtract(subtrahend.getValue()));
    }

    public Money divide(final IMathDataValue divisor){
        return new Money(this.value.divide(divisor.getValue(),DEFAULT_ROUNDING_MODE));
    }

    public Money multiply(final IMathDataValue multiplier) {
        return new Money(this.getValue().multiply(multiplier.getValue()));
    }

	public int compareTo(final IMathDataValue value) {
		return this.getValue().compareTo(value.getValue());
	}

	public boolean equal(final IMathDataValue value){
		return this.getValue().compareTo(value.getValue()) == 0;
	}

	public boolean equalLess(final IMathDataValue value){
		return this.getValue().compareTo(value.getValue()) <= 0;
	}

	public boolean less(final IMathDataValue value){
		return this.getValue().compareTo(value.getValue()) < 0;
	}

	public boolean equalGreaterThan(final IMathDataValue value){
		return this.getValue().compareTo(value.getValue()) >= 0;
	}

	public boolean greaterThan(final IMathDataValue value){
		return this.getValue().compareTo(value.getValue()) > 0;
	}
}
