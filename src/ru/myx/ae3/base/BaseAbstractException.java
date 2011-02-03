package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * Correct property support.
 * 
 * @author myx
 * 
 */
@ReflectionDisable
public abstract class BaseAbstractException extends RuntimeException implements BaseObject<RuntimeException> {
	
	/**
	 * ERROR PROTOTYPE
	 */
	public static final BaseObject<?>	PROTOTYPE			= new BaseNativeObject( BaseObject.PROTOTYPE );
	
	/**
	 * Prototype object
	 */
	protected BaseObject<?>				prototype;
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 85952158101027976L;
	
	/**
	 * @param prototype
	 */
	protected BaseAbstractException(final BaseObject<?> prototype) {
		assert prototype != null : "Prototype is java null";
		this.prototype = prototype == BaseObject.UNDEFINED
				? null
				: prototype;
	}
	
	/**
	 * @param prototype
	 * @param message
	 * 
	 */
	protected BaseAbstractException(final BaseObject<?> prototype, final String message) {
		super( message );
		assert prototype != null : "Prototype is java null";
		this.prototype = prototype == BaseObject.UNDEFINED
				? null
				: prototype;
	}
	
	/**
	 * @param prototype
	 * @param message
	 * @param cause
	 * 
	 */
	public BaseAbstractException(final BaseObject<?> prototype, final String message, final Throwable cause) {
		super( message, cause );
		assert prototype != null : "Prototype is java null";
		this.prototype = prototype == BaseObject.UNDEFINED
				? null
				: prototype;
	}
	
	/**
	 * @param prototype
	 * @param cause
	 * 
	 */
	public BaseAbstractException(final BaseObject<?> prototype, final Throwable cause) {
		super( cause );
		assert prototype != null : "Prototype is java null";
		this.prototype = prototype == BaseObject.UNDEFINED
				? null
				: prototype;
	}
	
	/**
	 * @param cause
	 * @param prototype
	 */
	public BaseAbstractException(final Throwable cause, final BaseObject<?> prototype) {
		super( cause );
		assert prototype != null : "Prototype is java null";
		this.prototype = prototype == BaseObject.UNDEFINED
				? null
				: prototype;
	}
	
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	/**
	 * Returns a function associated with this object. Executes code associated
	 * with the object. Invoked via a function call expression. Objects that
	 * implement this internal method are called functions.
	 * 
	 * null result means that this object is not a function.
	 * 
	 * 13.2.1 [[Call]]
	 * <p>
	 * When the [[Call]] property for a Function object F is called, the
	 * following steps are taken:<br>
	 * 1. Establish a new execution context using F's FormalParameterList, the
	 * passed arguments list, and the this value as described in Section 10.2.3.
	 * <br>
	 * 2. Evaluate F's FunctionBody. <br>
	 * 3. Exit the execution context established in step 1, restoring the
	 * previous execution context.<br>
	 * 4. If Result(2).type is throw then throw Result(2).value. <br>
	 * 5. If Result(2).type is return then return Result(2).value. <br>
	 * 6. (Result(2).type must be normal.) Return undefined.
	 * <p>
	 * 
	 * 
	 * @return result
	 */
	@Override
	public BaseFunction<?> baseCall() {
		return null;
	}
	
	/**
	 * The value of the [[Class]] property is defined by this specification for
	 * every kind of built-in object. The value of the [[Class]] property of a
	 * host object may be any value, even a value used by a built-in object for
	 * its [[Class]] property. The value of a [[Class]] property is used
	 * internally to distinguish different kinds of built-in objects. Note that
	 * this specification does not provide any means for a program to access
	 * that value except through Object.prototype.toString (see 15.2.4.2).
	 * 
	 * @return class
	 */
	@Override
	public String baseClass() {
		return "Error";
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
	public void baseClear() {
		final Iterator<String> iterator = this.baseGetOwnIterator();
		for (; iterator.hasNext();) {
			this.baseDelete( iterator.next() );
		}
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
		final BaseProperty property = this.baseGetOwnProperty( name );
		if (property != null && property.isWritable( name.toString() )) {
			return property.propertySet( this, name.toString(), value, writable, enumerable, dynamic );
		}
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		final BaseProperty property = this.baseGetOwnProperty( name );
		if (property != null && property.isWritable( name )) {
			return property.propertySet( this,//
					name,
					value,
					writable,
					enumerable,
					dynamic );
		}
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		final BaseProperty property = this.baseGetOwnProperty( name );
		if (property != null && property.isWritable( name )) {
			return property.propertySet( this,
					name,
					Base.forDouble( value ),
					writable,
					enumerable,
					dynamic );
		}
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		final BaseProperty property = this.baseGetOwnProperty( name );
		if (property != null && property.isWritable( name )) {
			return property.propertySet( this,
					name,
					Base.forLong( value ),
					writable,
					enumerable,
					dynamic );
		}
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		final BaseProperty property = this.baseGetOwnProperty( name );
		if (property != null && property.isWritable( name )) {
			return property.propertySet( this, name, Base.forString( value ), writable, enumerable, dynamic );
		}
		return false;
	}
	
	/**
	 * [[Delete]] (P) When the [[Delete]] method of O is called with property
	 * name P, the following steps are taken:<br>
	 * 1. If O doesn’t have a property with name P, return true.<br>
	 * 2. If the property has the DontDelete attribute, return false.<br>
	 * 3. Remove the property with name P from O.<br>
	 * 4. Return true.
	 * 
	 * @param name
	 * @return boolean
	 */
	@Override
	public boolean baseDelete(final String name) {
		return false;
	}
	
	@Override
	public final BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		assert name != null : "NULL java object as a property name";
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = this.prototype; object != null; object = object.basePrototype()) {
			for (final BaseProperty property = object.baseGetOwnProperty( name ); property != null;) {
				return property.propertyGet( this, name );
			}
		}
		return defaultValue;
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name) {
		assert name != null : "NULL java object as a property name";
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = this.prototype; object != null; object = object.basePrototype()) {
			for (final BaseProperty property = object.baseGetOwnProperty( name ); property != null;) {
				return property.propertyGet( this, name );
			}
		}
		return BaseObject.UNDEFINED;
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name, final BaseObject<?> defaultValue) {
		assert name != null : "NULL java object as a property name";
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = this.prototype; object != null; object = object.basePrototype()) {
			for (final BaseProperty property = object.baseGetOwnProperty( name ); property != null;) {
				return property.propertyGet( this, name );
			}
		}
		return defaultValue;
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
		return this.baseGetOwnIterator();
	}
	
	/**
	 * Never returns NULL. Should return BaseObject.ITERATOR_EMPTY at least.
	 * 
	 * @return
	 */
	@Override
	public Iterator<String> baseGetOwnAllIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	/**
	 * Never returns NULL. Should return BaseObject.ITERATOR_EMPTY at least.
	 * 
	 * @return
	 */
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
	
	@Override
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	/**
	 * equivalent to: return this.hasOwnProperties || this.prototype &&
	 * this.prototype.hasProperties
	 */
	@Override
	public boolean baseHasProperties() {
		final boolean ownProperties = this.baseHasOwnProperties();
		if (ownProperties) {
			return true;
		}
		final BaseObject<?> prototype = this.basePrototype();
		return prototype == null
				? false
				: prototype.baseHasProperties();
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitive() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitiveBoolean() {
		return false;
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
	
	/**
	 * The value of the [[Prototype]] property must be either an object or null,
	 * and every [[Prototype]] chain must have finite length (that is, starting
	 * from any object, recursively accessing the [[Prototype]] property must
	 * eventually lead to a null value). Whether or not a native object can have
	 * a host object as its [[Prototype]] depends on the implementation.
	 * 
	 * @return prototype object or null
	 */
	@Override
	public final BaseObject<?> basePrototype() {
		return this.prototype;
	}
	
	@Override
	public void basePutAll(final BaseObject<?> value) {
		if (value == null || value.baseIsPrimitive()) {
			return;
		}
		for (final Iterator<String> iterator = value.baseGetIterator(); iterator.hasNext();) {
			final String key = iterator.next();
			this.baseDefine( key, value.baseGet( key ), true, true, true );
		}
	}
	
	/**
	 * 
	 * @return boolean
	 */
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
	}
	
	/**
	 * 9.5 ToInt32: (Signed 32 Bit Integer)
	 * <p>
	 * The operator ToInt32 converts its argument to one of 232 integer values
	 * in the range −231 through 231−1, inclusive.
	 * <p>
	 * This operator functions as follows:<br>
	 * 1. Call ToNumber on the input argument.<br>
	 * 2. If Result(1) is NaN, +0, −0, +∞, or −∞, return +0.<br>
	 * 3. Compute sign(Result(1)) * floor(abs(Result(1))).<br>
	 * 4. Compute Result(3) modulo 232; that is, a finite integer value k of
	 * Number type with positive sign and less than 232 in magnitude such the
	 * mathematical difference of Result(3) and k is mathematically an integer
	 * multiple of 232. <br>
	 * 5. If Result(4) is greater than or equal to 231, return Result(4)− 232,
	 * otherwise return Result(4).
	 * <p>
	 * NOTE Given the above definition of ToInt32: <br>
	 * The ToInt32 operation is idempotent: if applied to a result that it
	 * produced, the second application leaves that value unchanged. <br>
	 * ToInt32(ToUint32(x)) is equal to ToInt32(x) for all values of x. (It is
	 * to preserve this latter property that +∞ and −∞ are mapped to +0.)
	 * <p>
	 * ToInt32 maps −0 to +0.
	 * <p>
	 * 
	 * @return number
	 */
	@Override
	public final BasePrimitiveNumber baseToInt32() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToInt32();
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
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToInteger();
	}
	
	/**
	 * For Object
	 * <p>
	 * Apply the following steps:<br>
	 * 1. Call ToPrimitive(input argument, hint Number).<br>
	 * 2. Call ToNumber(Result(1)). <br>
	 * 3. Return Result(2). <br>
	 * 
	 * @return number
	 */
	@Override
	public final BasePrimitiveNumber baseToNumber() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToNumber();
	}
	
	/**
	 * [[DefaultValue]] (hint) When the [[DefaultValue]] method of O is called
	 * with hint String, the following steps are taken:<br>
	 * 1. Call the [[Get]] method of object O with argument "toString".<br>
	 * 2. If Result(1) is not an object, go to step 5.<br>
	 * 3. Call the [[Call]] method of Result(1), with O as the this value and an
	 * empty argument list.<br>
	 * 4. If Result(3) is a primitive value, return Result(3).<br>
	 * 5. Call the [[Get]] method of object O with argument "valueOf".<br>
	 * 6. If Result(5) is not an object, go to step 9.<br>
	 * 7. Call the [[Call]] method of Result(5), with O as the this value and an
	 * empty argument list.<br>
	 * 8. If Result(7) is a primitive value, return Result(7).<br>
	 * 9. Throw a TypeError exception.
	 * <p>
	 * When the [[DefaultValue]] method of O is called with hint Number, the
	 * following steps are taken:<br>
	 * 1. Call the [[Get]] method of object O with argument "valueOf".<br>
	 * 2. If Result(1) is not an object, go to step 5.<br>
	 * 3. Call the [[Call]] method of Result(1), with O as the this value and an
	 * empty argument list.<br>
	 * 4. If Result(3) is a primitive value, return Result(3).<br>
	 * 5. Call the [[Get]] method of object O with argument "toString".<br>
	 * 6. If Result(5) is not an object, go to step 9.<br>
	 * 7. Call the [[Call]] method of Result(5), with O as the this value and an
	 * empty argument list.<br>
	 * 8. If Result(7) is a primitive value, return Result(7).<br>
	 * 9. Throw a TypeError exception.<b> When the [[DefaultValue]] method of O
	 * is called with no hint, then it behaves as if the hint were Number,
	 * unless O is a Date object (see 15.9), in which case it behaves as if the
	 * hint were String. The above specification of [[DefaultValue]] for native
	 * objects can return only primitive values. If a host object implements its
	 * own [[DefaultValue]] method, it must ensure that its [[DefaultValue]]
	 * method can return only primitive values.
	 * <p>
	 * ToPrimitive The operator ToPrimitive takes a Value argument and an
	 * optional argument PreferredType. The operator ToPrimitive converts its
	 * value argument to a non-Object type. If an object is capable of
	 * converting to more than one primitive type, it may use the optional hint
	 * PreferredType to favour that type
	 * 
	 * @param hint
	 * @return EcmaPrimitive
	 */
	@Override
	public final BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this.baseToString();
	}
	
	/**
	 * @return string
	 */
	@Override
	public abstract BasePrimitiveString baseToString();
	
	/**
	 * Internal state information associated with this object.
	 * 
	 * Returns NULL by default. Override to return anything else %).
	 * 
	 * @return internal
	 */
	@Override
	public RuntimeException baseValue() {
		return this;
	}
	
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
	 * ECMAScript Language Specification   Edition 3   24-Mar-00 
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
	
	@Override
	public int hashCode() {
		final Object value = this.baseValue();
		return value == null
				? 0
				: value.hashCode();
	}
}
