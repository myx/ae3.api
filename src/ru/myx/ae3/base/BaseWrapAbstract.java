package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * 
 * BaseWrapAbstract is intended for wrapping of non-base java objects.
 * 
 * The reason for it's existence is that it has reference implementation for
 * most of base object's methods.
 * 
 * 
 * Abstract 'wrapped java object' object with NO own properties by design, see
 * BaseWrapObjectAbstract for one WITH own properties.
 * 
 * Only 2 abstract methods: baseValue & baseToString.
 * 
 * @author myx
 * 
 * @param <T>
 */
@ReflectionDisable
public abstract class BaseWrapAbstract<T extends Object> extends BaseAbstract<T> {
	
	/**
	 * Prototype object
	 */
	protected BaseObject<?>	prototype;
	
	/**
	 * @param prototype
	 */
	public BaseWrapAbstract(final BaseObject<?> prototype) {
		assert prototype != null : "Prototype is java null";
		this.prototype = prototype == BaseObject.UNDEFINED
				? BaseObject.PROTOTYPE
				: prototype;
	}
	
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	@Override
	public BaseFunction<?> baseCall() {
		return null;
	}
	
	@Override
	public String baseClass() {
		return "Object";
	}
	
	@Override
	public BaseFunction<?> baseConstruct() {
		return null;
	}
	
	@Override
	public boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.baseDefine( name, Base.forDouble( value ), writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.baseDefine( name, Base.forLong( value ), writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.baseDefine( name, Base.forString( value ), writable, enumerable, dynamic );
	}
	
	@Override
	public boolean baseDelete(final String name) {
		return false;
	}
	
	/**
	 * same as BaseAbstract's but makes use of this.prototype field
	 */
	@Override
	public BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		{
			final BaseProperty property = this.baseGetOwnProperty( name );
			if (property != null) {
				return property.propertyGet( this, name );
			}
		}
		/**
		 * <code>
		final BaseObject<?> object = this.prototype;
		return object == null
				? defaultValue
				: object.baseGet( name, defaultValue );
		 * </code>
		 */
		for (BaseObject<?> object = this.prototype; object != null; object = object.basePrototype()) {
			final BaseProperty property = object.baseGetOwnProperty( name );
			if (property != null) {
				return property.propertyGet( this, name );
			}
		}
		return defaultValue;
	}
	
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		return null;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		return null;
	}
	
	@Override
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	@Override
	public BaseObject<?> basePrototype() {
		return this.prototype;
	}
	
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
	}
	
	@Override
	public BasePrimitiveNumber baseToInt32() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToInt32();
	}
	
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToInteger();
	}
	
	@Override
	public BasePrimitiveNumber baseToNumber() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToNumber();
	}
	
	@Override
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this.baseToString();
	}
	
	@Override
	public abstract BasePrimitiveString baseToString();
	
	/**
	 * For 'Wrap' objects, baseValue() must return wrapped object and never
	 * return 'this' reference.
	 * 
	 * @return internal
	 */
	@Override
	public abstract T baseValue();
	
	/**
	 * standart ECMA reference alike impl
	 */
	@Override
	public boolean equals(final Object o) {
		/**
		 * Fits here, booleans, undefined and null are fixed:<br>
		 * 2. If Type(x) is Undefined, return true.<br>
		 * 3. If Type(x) is Null, return true. 12. If Type(x) is Boolean, return
		 * true if x and y are both true or both false. Otherwise, return false.
		 */
		if (o == this) {
			return true;
		}
		/**
		 * instanceof is always false on NULL object
		 */
		if (!(o instanceof BaseObject<?>)) {
			return false;
		}
		if (o == BasePrimitiveNumber.NAN) {
			return false;
		}
		final BaseObject<?> object = (BaseObject<?>) o;
		if (this.baseIsPrimitiveNumber()) {
			assert false : "PrimitiveNumber object should have it's own equals method implementation!";
			/**
			 * 7. If x is the same number value as y, return true.<br>
			 * 8. If x is +0 and y is −0, return true.<br>
			 * 9. If x is −0 and y is +0, return true.<br>
			 * 10. Return false. <br>
			 */
			if (object.baseIsPrimitiveNumber()) {
				return this.baseValue().equals( object.baseValue() );
			}
			/**
			 * 16. If Type(x) is Number and Type(y) is String,<br>
			 * return the result of the comparison x == ToNumber(y).
			 */
			if (object.baseIsPrimitiveString()) {
				return this.baseValue().equals( object.baseToNumber().baseValue() );
			}
			/**
			 * 20. If Type(x) is either String or Number and Type(y) is Object,<br>
			 * return the result of the comparison x == ToPrimitive(y).
			 */
			if (!object.baseIsPrimitive()) {
				return this.equals( object.baseToPrimitive( ToPrimitiveHint.NUMBER ) );
			}
		}
		if (this.baseIsPrimitiveString()) {
			assert false : "PrimitiveString object should have it's own equals method implementation!";
			/**
			 * 11. If Type(x) is String, then return true if x and y are exactly
			 * the same sequence of characters (same length and same characters
			 * in corresponding positions). Otherwise, return false.
			 */
			if (object.baseIsPrimitiveString()) {
				return this.baseValue().equals( object.baseValue() );
			}
			/**
			 * 17. If Type(x) is String and Type(y) is Number,<br>
			 * return the result of the comparison ToNumber(x) == y.
			 */
			if (object.baseIsPrimitiveNumber()) {
				return this.baseToNumber().baseValue().equals( object.baseValue() );
			}
			/**
			 * 20. If Type(x) is either String or Number and Type(y) is Object,<br>
			 * return the result of the comparison x == ToPrimitive(y).
			 */
			if (!object.baseIsPrimitive()) {
				return this.equals( object.baseToPrimitive( ToPrimitiveHint.STRING ) );
			}
		}
		/**
		 * !!!SKIPPED:<br>
		 * 13. Return true if x and y refer to the same object or if they refer
		 * to objects joined to each other (section 13.1.2). Otherwise, return
		 * false.
		 * 
		 */
		/**
		 * 14. If x is null and y is undefined, return true.<br>
		 * 15. If x is undefined and y is null, return true. <br>
		 */
		if (o == BaseObject.UNDEFINED || o == BaseObject.NULL) {
			return false;
		}
		/**
		 * 18. If Type(x) is Boolean,<br>
		 * return the result of the comparison ToNumber(x) == y.
		 */
		assert !this.baseIsPrimitiveBoolean() : "PrimitiveBoolean object must have it's own equals method implementation!";
		/**
		 * 19. If Type(y) is Boolean,<br>
		 * return the result of the comparison x == ToNumber(y).
		 */
		if (object.baseIsPrimitiveBoolean()) {
			return this.equals( object.baseToNumber() );
		}
		/**
		 * 21. If Type(x) is Object and Type(y) is either String or Number,<br>
		 * return the result of the comparison ToPrimitive(x) == y.
		 */
		if (!this.baseIsPrimitive()) {
			if (object.baseIsPrimitiveString()) {
				return this.baseToPrimitive( ToPrimitiveHint.STRING ).equals( object );
			}
			if (object.baseIsPrimitiveNumber()) {
				return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).equals( object );
			}
		}
		/**
		 * 22. Return false.
		 */
		return false;
	}
	
	/**
	 * For 'Wrap' objects this method must return [same] hashCode [as] of
	 * wrapped object.
	 * 
	 * TODO: final?
	 */
	@Override
	public int hashCode() {
		final Object value = this.baseValue();
		assert value != this : "BaseWrapAbstract should not return itself as a value, user BaseHostObject, class="
				+ this.getClass().getName();
		return value == null
				? 0
				: value.hashCode();
	}
	
	/**
	 * For 'Wrap' objects this method must return same result as of wrapped
	 * object.
	 * 
	 * TODO: final?
	 */
	@Override
	public String toString() {
		return String.valueOf( this.baseValue() );
	}
}
