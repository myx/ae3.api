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
final class PrimitiveNumberNan extends BasePrimitiveNumber {
	private static final Double	NaN					= new Double( Double.NaN );
	
	private static final long	serialVersionUID	= 5560896981483738379L;
	
	PrimitiveNumberNan() {
		//
	}
	
	@Override
	public boolean baseIsPrimitiveInteger() {
		return false;
	}
	
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.FALSE;
	}
	
	@Override
	public final BasePrimitiveNumber baseToInt32() {
		return BasePrimitiveNumber.ZERO;
	}
	
	@Override
	public final BasePrimitiveNumber baseToInteger() {
		return BasePrimitiveNumber.ZERO;
	}
	
	@Override
	public final BasePrimitiveString baseToString() {
		return BasePrimitiveString.NAN;
	}
	
	@Override
	public Number baseValue() {
		return PrimitiveNumberNan.NaN;
	}
	
	@Override
	public double doubleValue() {
		return Double.NaN;
	}
	
	@Override
	public float floatValue() {
		return Float.NaN;
	}
	
	@Override
	public final int hashCode() {
		return this.baseValue().hashCode();
	}
	
	@Override
	public int intValue() {
		return 0;
	}
	
	@Override
	public long longValue() {
		return 0;
	}
}
