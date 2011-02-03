package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 */
@ReflectionDisable
final class PrimitiveNumberOne extends PrimitiveNumberIntegerAbstract {
	private static final long	serialVersionUID	= 6777998057139262710L;
	
	private static final Long	ONE					= new Long( 1L );
	
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
	}
	
	@Override
	public final BasePrimitiveString baseToString() {
		return BasePrimitiveString.ONE;
	}
	
	@Override
	public final String stringValue() {
		return BasePrimitiveString.ONE.toString();
	}
	
	@Override
	public final Number baseValue() {
		return PrimitiveNumberOne.ONE;
	}
	
	@Override
	public double doubleValue() {
		return 1.0;
	}
	
	@Override
	public float floatValue() {
		return 1.0f;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
	public int intValue() {
		return 1;
	}
	
	@Override
	public long longValue() {
		return 1L;
	}
}
