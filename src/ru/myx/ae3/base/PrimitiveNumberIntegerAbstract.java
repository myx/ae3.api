/**
 * 
 */
package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * for 'instanceof' INT32 number
 * 
 * @author myx
 * 
 */
@ReflectionDisable
abstract class PrimitiveNumberIntegerAbstract extends BasePrimitiveNumber {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 874681045643468680L;
	
	@Override
	public final boolean baseIsPrimitiveInteger() {
		return true;
	}
	
	@Override
	public final BasePrimitiveNumber baseToInt32() {
		return this;
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
	public final BasePrimitiveNumber baseToInteger() {
		return this;
	}
}
