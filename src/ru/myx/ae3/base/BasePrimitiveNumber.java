/**
 * 
 */
package ru.myx.ae3.base;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 */
// JAVA NATURE: public abstract class BasePrimitiveNumber extends
// BasePrimitive<Number> {
@ReflectionDisable
public abstract class BasePrimitiveNumber extends Number implements BasePrimitive<Number> {
	
	private static final class NumberFormatter {
		private static final int			CAPACITY	= 32;
		
		private static volatile int			counter		= 0;
		
		private static final NumberFormat[]	POOL1		= new NumberFormat[NumberFormatter.CAPACITY];
		
		NumberFormatter() {
			for (int i = 0; i < NumberFormatter.CAPACITY; i++) {
				NumberFormatter.POOL1[i] = NumberFormat.getInstance( Locale.US );
				NumberFormatter.POOL1[i].setMinimumIntegerDigits( 1 );
				NumberFormatter.POOL1[i].setGroupingUsed( false );
			}
		}
		
		String formatFixed(final double d, final int digits) {
			final int index = NumberFormatter.counter++;
			final NumberFormat current = NumberFormatter.POOL1[index % NumberFormatter.CAPACITY];
			synchronized (current) {
				current.setMaximumFractionDigits( digits );
				current.setMinimumFractionDigits( digits );
				return current.format( d );
			}
		}
		
		String formatPrecision(final double d, final int digits) {
			/**
			 * FIXME incorrect,
			 * https://developer.mozilla.org/en/JavaScript/Reference
			 * /Global_Objects/Number/toPrecision
			 */
			final int index = NumberFormatter.counter++;
			final NumberFormat current = NumberFormatter.POOL1[index % NumberFormatter.CAPACITY];
			final BigDecimal decimal = BigDecimal.valueOf( d );
			final int precision = decimal.precision();
			final int scale = decimal.scale();
			synchronized (current) {
				if (precision >= scale) {
					current.setMaximumFractionDigits( digits - (precision - scale) );
					current.setMinimumFractionDigits( 0 );
				} else {
					current.setMaximumFractionDigits( 0 );
					current.setMinimumFractionDigits( 0 );
				}
				return current.format( d );
			}
		}
	}
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	E					= new PrimitiveNumberTrue( Math.E );
	
	private static final NumberFormatter	FORMATTER			= new NumberFormatter();
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	LN10				= new PrimitiveNumberTrue( Math.log( 10.0d ) );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	LN2					= new PrimitiveNumberTrue( Math.log( 2.0d ) );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	LOG10E				= new PrimitiveNumberTrue( Math.log( Math.E )
																		/ Math.log( 10 ) );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	LOG2E				= new PrimitiveNumberTrue( Math.log( Math.E )
																		/ Math.log( 2 ) );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	MAX_VALUE			= new PrimitiveNumberTrue( Double.MAX_VALUE );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	MIN_VALUE			= new PrimitiveNumberTrue( Double.MIN_VALUE );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	MONE				= new PrimitiveNumberTrueInteger( -1 );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	NAN					= new PrimitiveNumberNan();
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	NINF				= new PrimitiveNumberNegativeInfinity();
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	ONE					= new PrimitiveNumberOne();
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	PI					= new PrimitiveNumberTrue( Math.PI );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	PINF				= new PrimitiveNumberPositiveInfinity();
	
	/**
	 * 
	 */
	public static final BaseObject<?>		PROTOTYPE;
	
	private static final long				serialVersionUID	= -8440077818682500467L;
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	SQRT1_2				= new PrimitiveNumberTrue( Math.sqrt( 0.5d ) );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	SQRT2				= new PrimitiveNumberTrue( Math.sqrt( 2.0d ) );
	
	/**
	 * 
	 */
	public static final BasePrimitiveNumber	ZERO				= new PrimitiveNumberZero();
	
	static {
		PROTOTYPE = new BaseNativeObject( BaseObject.PROTOTYPE );
	}
	
	/**
	 * non-public method
	 */
	BasePrimitiveNumber() {
		//
	}
	
	/**
	 * Not an array
	 */
	@Override
	public final BaseArray<?, ?> baseArray() {
		return null;
	}
	
	/**
	 * Not a function
	 */
	@Override
	public final BaseFunction<?> baseCall() {
		return null;
	}
	
	@Override
	public final String baseClass() {
		return "number";
	}
	
	/**
	 * <pre>
	 * final Iterator&lt;String&gt; iterator = this.baseGetOwnIterator();
	 * for (; iterator.hasNext();) {
	 * 	this.baseDelete( iterator.next() );
	 * }
	 * </pre>
	 */
	@Override
	public final void baseClear() {
		// ignore
	}
	
	/**
	 * Not a constructor
	 */
	@Override
	public final BaseFunction<?> baseConstruct() {
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
	public boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	/**
	 * No properties
	 */
	@Override
	public final boolean baseDelete(final String name) {
		return false;
	}
	
	@Override
	public final BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		/**
		 * Have no other own properties!
		 */
		/**
		 * <code>
		return BasePrimitiveNumber.PROTOTYPE.baseGet( name, defaultValue );
		 * </code>
		 */
		for (BaseObject<?> object = BasePrimitiveNumber.PROTOTYPE;;) {
			final BaseProperty property = object.baseGetOwnProperty( name );
			if (property != null) {
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
		return BasePrimitiveNumber.PROTOTYPE.baseGet( name, BaseObject.UNDEFINED );
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name, final BaseObject<?> defaultValue) {
		/**
		 * Have no other own properties!
		 */
		return BasePrimitiveNumber.PROTOTYPE.baseGet( name, defaultValue );
	}
	
	/**
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final boolean baseGetBoolean(final String name, final boolean defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToBoolean() == BaseObject.TRUE;
	}
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toBoolean() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final double baseGetDouble(final String name, final double defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToNumber().doubleValue();
	}
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toBoolean() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final long baseGetInteger(final String name, final long defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToInteger().longValue();
	}
	
	/**
	 * Never returns NULL. Should return BaseObject.ITERATOR_EMPTY at least.
	 * <p>
	 * Properties of the object being enumerated may be deleted during
	 * enumeration. If a property that has not yet been visited during
	 * enumeration is deleted, then it will not be visited. If new properties
	 * are added to the object being enumerated during enumeration, the newly
	 * added properties are not guaranteed to be visited in the active
	 * enumeration.
	 * <p>
	 * Enumerating the properties of an object includes enumerating properties
	 * of its prototype, and the prototype of the prototype, and so on,
	 * recursively; but a property of a prototype is not enumerated if it is
	 * ―shadowed because some previous object in the prototype chain has a
	 * property with the same name. Deleted: The mechanics of enumerating the
	 * properties (step 5 in the first algorithm, step 6 in the second) is
	 * implementation dependent. The order of enumeration is defined by the
	 * object.
	 * 
	 * @return
	 */
	@Override
	public final Iterator<String> baseGetIterator() {
		final BaseObject<?> prototype = this.basePrototype();
		return prototype == null
				? BaseObject.ITERATOR_EMPTY
				: prototype.baseGetIterator();
	}
	
	/**
	 * No properties
	 */
	@Override
	public final Iterator<String> baseGetOwnAllIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	/**
	 * No properties
	 */
	@Override
	public final Iterator<String> baseGetOwnIterator() {
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
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toBoolean() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final String baseGetString(final String name, final String defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToString().baseValue();
	}
	
	/**
	 * No properties
	 */
	@Override
	public final boolean baseHasOwnProperties() {
		return false;
	}
	
	/**
	 * No properties at all
	 */
	@Override
	public final boolean baseHasProperties() {
		return false;
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	/**
	 * Sure
	 * 
	 * it is not ok to use this method while knowing that this object is
	 * actually a number.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitive() {
		return true;
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a number.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitiveBoolean() {
		return false;
	}
	
	@Override
	public abstract boolean baseIsPrimitiveInteger();
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a number.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitiveNumber() {
		return true;
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a number.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitiveString() {
		return false;
	}
	
	@Override
	public final BaseObject<?> basePrototype() {
		return BasePrimitiveNumber.PROTOTYPE;
	}
	
	/**
	 * Read-Only
	 */
	@Override
	public void basePutAll(final BaseObject<?> value) {
		//
	}
	
	/**
	 * @param digits
	 * @return
	 */
	public String baseToFixed(final int digits) {
		return BasePrimitiveNumber.FORMATTER.formatFixed( this.doubleValue(), digits );
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a number.
	 */
	@Deprecated
	@Override
	public final BasePrimitiveNumber baseToNumber() {
		return this;
	}
	
	/**
	 * @param digits
	 * @return
	 */
	public String baseToPrecision(final int digits) {
		return BasePrimitiveNumber.FORMATTER.formatPrecision( this.doubleValue(), digits );
	}
	
	/**
	 * Already a primitive
	 * 
	 * it is not ok to use this method while knowing that this object is
	 * actually a number.
	 */
	@Deprecated
	@Override
	public final BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this;
	}
	
	/**
	 * @return string
	 */
	@Override
	public abstract BasePrimitiveString baseToString();
	
	/**
	 * @return
	 */
	@Override
	public abstract double doubleValue();
	
	/**
	 * <pre>
	 * 11.9.1 The Equals Operator ( == ) 
	 * The production EqualityExpression : EqualityExpression == RelationalExpression is evaluated as follows: 
	 * 1. Evaluate EqualityExpression. 
	 * 2. Call GetValue(Result(1)). 
	 * 3. Evaluate RelationalExpression. 
	 * 4. Call GetValue(Result(3)). 
	 * 5. Perform the comparison Result(4) == Result(2). (Section 11.9.3.) 
	 * 6. Return Result(5).
	 * </pre>
	 * 
	 * <pre>
	 * 11.9.3 The Abstract Equality Comparison Algorithm 
	 * The comparison x == y, where x and y are values, produces true or false. Such a comparison is performed as 
	 * follows: 
	 * 1. If Type(x) is different from Type(y), go to step 14. 
	 * 2. If Type(x) is Undefined, return true. 
	 * 3. If Type(x) is Null, return true. 
	 * 4. If Type(x) is not Number, go to step 11. 
	 * 5. If x is NaN, return false. 
	 * 6. If y is NaN, return false. 
	 * 7. If x is the same number value as y, return true. 
	 * 8. If x is +0 and y is −0, return true. 
	 * 9. If x is −0 and y is +0, return true. 
	 * 10. Return false. 
	 * 11. If Type(x) is String, then return true if x and y are exactly the same sequence of characters (same length and 
	 * same characters in corresponding positions). Otherwise, return false. 
	 * 12. If Type(x) is Boolean, return true if x and y are both true or both false. Otherwise, return false. 
	 * 13. Return true if x and y refer to the same object or if they refer to objects joined to each other (section 13.1.2). 
	 * Otherwise, return false. 
	 * 14. If x is null and y is undefined, return true. 
	 * 15. If x is undefined and y is null, return true. 
	 * 16. If Type(x) is Number and Type(y) is String, 
	 * return the result of the comparison x == ToNumber(y). 
	 * 17. If Type(x) is String and Type(y) is Number, 
	 * return the result of the comparison ToNumber(x) == y. 
	 * 18. If Type(x) is Boolean, return the result of the comparison ToNumber(x) == y. 
	 * 19. If Type(y) is Boolean, return the result of the comparison x == ToNumber(y). 
	 * 20. If Type(x) is either String or Number and Type(y) is Object, 
	 * return the result of the comparison x == ToPrimitive(y). 
	 * 21. If Type(x) is Object and Type(y) is either String or Number, 
	 * return the result of the comparison ToPrimitive(x) == y. 
	 * 22. Return false.
	 * </pre>
	 * 
	 * NOTE Given the above definition of equality:<br>
	 * String comparison can be forced by: &quot;&quot; + a ==&quot;&quot; +b.<br>
	 * Numeric comparison can be forced by: a - 0== b- 0. <br>
	 * Boolean comparison can be forced by: !a == !b.
	 * <p>
	 * The equality operators maintain the following invariants:<br>
	 * 1. A != B is equivalent to !(A == B). <br>
	 * 2. A == B is equivalent to B == A, except in the order of evaluation of A
	 * and B.<br>
	 * The equality operator is not always transitive. For example, there might
	 * be two distinct String objects, each representing the same string value;
	 * each String object would be considered equal to the string value by the
	 * == operator, but the two String objects would not be equal to each other.
	 * <p>
	 * Comparison of strings uses a simple equality test on sequences of code
	 * point value values. There is no attempt to use the more complex,
	 * semantically oriented definitions of character or string equality and
	 * collating order defined in the Unicode 2.0 specification. Therefore
	 * strings that are canonically equal according to the Unicode standard
	 * could test as unequal. In effect this algorithm assumes that both strings
	 * are already in normalised form.
	 * <p>
	 * 
	 * Type x is this and is an object of any type.
	 */
	@Override
	public final boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof BaseObject<?>) {
			return ((BaseObject<?>) o).baseToNumber().baseValue().equals( this.baseValue() );
		}
		if (o instanceof Number) {
			final Object baseValue = this.baseValue();
			if (baseValue != this) {
				return o.equals( baseValue );
			}
			return ((Number) o).doubleValue() == this.doubleValue();
		}
		return false;
	}
	
	/**
	 * @return
	 */
	@Override
	public abstract float floatValue();
	
	@Override
	public abstract int hashCode();
	
	/**
	 * Ecma: ToInt32
	 * 
	 * @return
	 */
	@Override
	public abstract int intValue();
	
	/**
	 * Ecma: ToInt64 (ToInteger rules are not compatible with java long)
	 * 
	 * @return
	 */
	@Override
	public abstract long longValue();
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @return
	 */
	public final boolean propertySet(final BaseObject<?> instance, final String name, final double value) {
		return this.propertySet( instance, name, value, true, true, true );
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @return
	 */
	public final boolean propertySet(final BaseObject<?> instance, final String name, final long value) {
		return this.propertySet( instance, name, value, true, true, true );
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @return
	 */
	public final boolean propertySet(final BaseObject<?> instance, final String name, final String value) {
		return this.propertySet( instance, name, value, true, true, true );
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public String stringValue() {
		return this.baseToString().stringValue();
	}
	
	/**
	 * Yes - this way only
	 * 
	 * return this.stringValue()
	 */
	@Override
	public final String toString() {
		return this.stringValue();
	}
	
}
