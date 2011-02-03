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
final class PrimitiveNumberNegativeInfinity extends BasePrimitiveNumber {
	private static final Double	NINF				= new Double( Double.NEGATIVE_INFINITY );
	
	private static final long	serialVersionUID	= -9153570506351843678L;
	
	PrimitiveNumberNegativeInfinity() {
		//
	}
	
	@Override
	public boolean baseIsPrimitiveInteger() {
		return false;
	}
	
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
	}
	
	@Override
	public final BasePrimitiveNumber baseToInt32() {
		return BasePrimitiveNumber.ZERO;
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
		return BasePrimitiveString.NINFINITY;
	}
	
	@Override
	public Number baseValue() {
		return PrimitiveNumberNegativeInfinity.NINF;
	}
	
	@Override
	public double doubleValue() {
		return Double.NEGATIVE_INFINITY;
	}
	
	@Override
	public float floatValue() {
		return Float.NEGATIVE_INFINITY;
	}
	
	@Override
	public int hashCode() {
		return this.baseValue().hashCode();
	}
	
	/**
	 * <pre>
	 * 9.5 ToInt32: (Signed 32 Bit Integer) 
	 * The operator ToInt32 converts its argument to one of 232 integer values in the range −231 through 231−1, inclusive. 
	 * This operator functions as follows: 
	 * 1. Call ToNumber on the input argument. 
	 * 2. If Result(1) is NaN, +0, −0, +∞ , or −∞, return +0. 
	 * 3. Compute sign(Result(1)) * floor(abs(Result(1))). 
	 * 4. Compute Result(3) modulo 232; that is, a finite integer value k of Number type with positive sign and less than 
	 * 232 in magnitude such the mathematical difference of Result(3) and k is mathematically an integer multiple of 
	 * 232. 
	 * 5. If Result(4) is greater than or equal to 231, return Result(4)− 232, otherwise return Result(4).
	 * 
	 * </pre>
	 */
	@Override
	public int intValue() {
		return 0;
	}
	
	@Override
	public long longValue() {
		return 0;
	}
}
