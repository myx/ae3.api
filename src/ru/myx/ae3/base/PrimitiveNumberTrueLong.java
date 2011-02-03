/**
 * 
 */
package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 */
@ReflectionDisable
final class PrimitiveNumberTrueLong extends BasePrimitiveNumber {
	
	private static final long	serialVersionUID	= -3223708759715053207L;
	
	private final Number		number;
	
	PrimitiveNumberTrueLong(final long value) {
		assert value != 0 : "Must not be zero";
		assert value != 1 : "Must not be one";
		assert value != (int) value : "Must not be an int32: immutable";
		this.number = new Long( value );
	}
	
	PrimitiveNumberTrueLong(final Number number) {
		assert number != null : "Must not be NULL";
		assert !Double.isNaN( number.doubleValue() ) : "Must not be NaN: immutable";
		assert number.doubleValue() == number.longValue() : "Must be an integer: immutable";
		assert number.intValue() != number.longValue() : "Must not be an int32: immutable";
		assert number.longValue() != 0 : "Must not be zero: immutable";
		assert number.longValue() != 1 : "Must not be one: immutable";
		assert !Double.isInfinite( number.doubleValue() ) : "Must be finite: immutable";
		this.number = number;
	}
	
	@Override
	public final boolean baseIsPrimitiveInteger() {
		return true;
	}
	
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
	}
	
	@Override
	public final BasePrimitiveNumber baseToInt32() {
		final long value = this.number.longValue();
		final int intValue = (int) value;
		return value == intValue
				? this
				: Base.forInteger( intValue );
	}
	
	/**
	 * 9.4 ToInteger
	 * <p>
	 * The operator ToInteger converts its argument to an integral numeric
	 * value. This operator functions as follows:<br>
	 * 1. Call ToNumber on the input argument. <br>
	 * 2. If Result(1) is NaN, return +0. <br>
	 * 3. If Result(1) is +0, −0, +∞, or −∞, return Result(1).<br>
	 * 4. Compute sign(Result(1)) * floor(abs(Result(1))).<br>
	 * 5. Return Result(4).
	 * <p>
	 * 
	 * @return number
	 */
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return this;
	}
	
	@Override
	public final BasePrimitiveString baseToString() {
		return Base.forString( Long.toString( this.number.longValue() ) );
	}
	
	@Override
	public final Number baseValue() {
		return this.number;
	}
	
	@Override
	public double doubleValue() {
		return this.number.doubleValue();
	}
	
	@Override
	public float floatValue() {
		return this.number.floatValue();
	}
	
	/**
	 * java Long's hash code!
	 */
	@Override
	public int hashCode() {
		final long value = this.number.longValue();
		return (int) (value ^ value >>> 32);
	}
	
	@Override
	public int intValue() {
		return this.number.intValue();
	}
	
	@Override
	public long longValue() {
		return this.number.longValue();
	}
}
