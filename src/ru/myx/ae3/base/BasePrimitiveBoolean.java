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
public abstract class BasePrimitiveBoolean extends BasePrimitiveAbstract<Boolean> {
	
	/**
	 * Boolean.prototype
	 */
	public static final BaseObject<?>	PROTOTYPE	= new BaseNativeObject( BaseObject.PROTOTYPE );
	
	/**
	 * non-public method
	 */
	BasePrimitiveBoolean() {
		//
	}
	
	/**
	 * Not an array
	 */
	@Override
	public final BaseArray<?, ?> baseArray() {
		return null;
	}
	
	@Override
	public final String baseClass() {
		return "boolean";
	}
	
	@Override
	public final BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		/**
		 * Have no other own properties!
		 */
		/**
		 * <code>
		return BasePrimitiveBoolean.PROTOTYPE.baseGet( name, defaultValue );
		 * </code>
		 */
		for (BaseObject<?> object = BasePrimitiveBoolean.PROTOTYPE;;) {
			for (final BaseProperty property = object.baseGetOwnProperty( name ); property != null;) {
				return property.propertyGet( this, name );
			}
			object = object.basePrototype();
			if (object == null) {
				return defaultValue;
			}
		}
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name) {
		/**
		 * Have no other own properties!
		 */
		for (BaseObject<?> object = BasePrimitiveBoolean.PROTOTYPE;;) {
			for (final BaseProperty property = object.baseGetOwnProperty( name ); property != null;) {
				return property.propertyGet( this, name );
			}
			object = object.basePrototype();
			if (object == null) {
				return BaseObject.UNDEFINED;
			}
		}
	}
	
	@Override
	public final boolean baseIsPrimitiveBoolean() {
		return true;
	}
	
	@Override
	public final boolean baseIsPrimitiveInteger() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitiveNumber() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitiveString() {
		return false;
	}
	
	@Override
	public final BaseObject<?> basePrototype() {
		return BasePrimitiveBoolean.PROTOTYPE;
	}
	
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return this;
	}
	
	/**
	 * @return
	 */
	public abstract BasePrimitiveBoolean baseToBooleanXorTue();
	
	/**
	 * Return tyoe is Boolean
	 */
	@Override
	public abstract Boolean baseValue();
	
	/**
	 * @return
	 */
	public abstract Boolean baseValueXorTrue();
	
	/**
	 * @return
	 */
	public abstract boolean booleanValue();
	
	/**
	 * @return
	 */
	public abstract boolean booleanValueXorTrue();
	
}
