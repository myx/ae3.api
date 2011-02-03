/**
 * 
 */
package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.common.Value;
import ru.myx.ae3.reflect.ReflectionDisable;
import ru.myx.ae3.reflect.ReflectionHidden;

/**
 * @author myx
 * @param <T>
 */
@ReflectionDisable
public interface BaseObject<T> extends Value<T>
/* , Comparable<BaseObject<?>> - consflicts with JDK */
/* , Comparable<Object> - consflicts with JDK */
/* , Iterable<String> - conflicts with collections */
/* , Map< String , Object > - too complicated */{
	/**
	 * BaseObject PROTOTYPE - root prototype has no prototype
	 */
	@ReflectionHidden
	BaseObject<?>			PROTOTYPE		= new BaseNativeObject( null );
	
	/**
	 * iterator() method should not ever return NULL, return this value at
	 * least.
	 */
	@ReflectionHidden
	Iterator<String>		ITERATOR_EMPTY	= new IteratorEmpty();
	
	/**
	 * FALSE
	 */
	@ReflectionHidden
	BasePrimitiveBoolean	FALSE			= new PrimitiveBooleanFalse();
	
	/**
	 * NULL
	 */
	@ReflectionHidden
	BasePrimitiveNull		NULL			= new BasePrimitiveNull();
	
	/**
	 * TRUE
	 */
	@ReflectionHidden
	BasePrimitiveBoolean	TRUE			= new PrimitiveBooleanTrue();
	
	/**
	 * UNDEFINED
	 */
	@ReflectionHidden
	BasePrimitiveUndefined	UNDEFINED		= new BasePrimitiveUndefined();
	
	/**
	 * Return NULL if not an array.
	 * 
	 * @return
	 */
	BaseArray<? extends Object, ? extends Object> baseArray();
	
	/**
	 * Return NULL is not a writable array.
	 * 
	 * @return TODO
	 */
	// BaseArrayWritable<? extends Object, ? extends Object>
	// baseArrayWritable();
	
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
	BaseFunction<?> baseCall();
	
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
	String baseClass();
	
	/**
	 * Should delete all deletable own properties.
	 * 
	 * <pre>
	 * final Iterator&lt;String&gt; iterator = this.baseGetOwnIterator();
	 * for (; iterator.hasNext();) {
	 * 	this.baseDelete( iterator.next() );
	 * }
	 * </pre>
	 * 
	 */
	void baseClear();
	
	/**
	 * Returns constructor associated with this object. Constructs an object.
	 * Invoked via the new operator. Objects that implement this internal method
	 * are called constructors.
	 * 
	 * null result means that this object is not a function.
	 * 
	 * 13.2.2 [[Construct]]
	 * <p>
	 * When the [[Construct]] property for a Function object F is called, the
	 * following steps are taken:<br>
	 * 1. Create a new native ECMAScript object. <br>
	 * 2. Set the [[Class]] property of Result(1) to "Object".<br>
	 * 3. Get the value of the prototype property of the F. <br>
	 * 4. If Result(3) is an object, set the [[Prototype]] property of Result(1)
	 * to Result(3).<br>
	 * 5. If Result(3) is not an object, set the [[Prototype]] property of
	 * Result(1) to the original Object prototype object as described in section
	 * 15.2.3.1. <br>
	 * 6. Invoke the [[Call]] property of F, providing Result(1) as the this
	 * value and providing the argument list passed into [[Construct]] as the
	 * argument values.<br>
	 * 7. If Type(Result(6)) is Object then return Result(6).<br>
	 * 8. Return Result(1).
	 * <p>
	 * 
	 * @return null by default
	 */
	BaseFunction<?> baseConstruct();
	
	/**
	 * 
	 * Sets object's own property with specified attributes attached. In
	 * scripting, explicit store object specifier (i.e. "a.b = 5" as opposed to
	 * "a = 5") should use this method.
	 * 
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic);
	
	/**
	 * 
	 * Sets object's own property with specified attributes attached. In
	 * scripting, explicit store object specifier (i.e. "a.b = 5" as opposed to
	 * "a = 5") should use this method.
	 * 
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic);
	
	/**
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic);
	
	/**
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic);
	
	/**
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic);
	
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
	boolean baseDelete(final String name);
	
	/**
	 * [[Get]] (P) When the [[Get]] method of O is called with property name P,
	 * the following steps are taken: <br>
	 * 1. If O doesn’t have a property with name P, go to step 4. <br>
	 * 2. Get the value of the property. <br>
	 * 3. Return Result(2). <br>
	 * 4. If the [[Prototype]] of O is null, return undefined. <br>
	 * 5. Call the [[Get]] method of [[Prototype]] with property name P. <br>
	 * 6. Return Result(5).
	 * 
	 * @param name
	 * @param defaultValue
	 * @return property
	 */
	BaseObject<?> baseGet(final BasePrimitiveString name, BaseObject<?> defaultValue);
	
	/**
	 * [[Get]] (P) When the [[Get]] method of O is called with property name P,
	 * the following steps are taken: <br>
	 * 1. If O doesn’t have a property with name P, go to step 4. <br>
	 * 2. Get the value of the property. <br>
	 * 3. Return Result(2). <br>
	 * 4. If the [[Prototype]] of O is null, return undefined. <br>
	 * 5. Call the [[Get]] method of [[Prototype]] with property name P. <br>
	 * 6. Return Result(5).
	 * 
	 * @param name
	 * @return property
	 */
	BaseObject<?> baseGet(final String name);
	
	/**
	 * Alias for: HasProperty(name) ? Get(name) : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	BaseObject<?> baseGet(final String name, final BaseObject<?> defaultValue);
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toBoolean() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	boolean baseGetBoolean(final String name, final boolean defaultValue);
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toNumber() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	double baseGetDouble(final String name, final double defaultValue);
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toInteger() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	long baseGetInteger(final String name, final long defaultValue);
	
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
	Iterator<String> baseGetIterator();
	
	/**
	 * Never returns null. BaseObject.ITERATOR_EMPTY at least
	 * 
	 * Should list all of the own properties with no regard to 'enumerable'
	 * attribute;
	 * 
	 * @return
	 */
	Iterator<String> baseGetOwnAllIterator();
	
	/**
	 * Never returns null. BaseObject.ITERATOR_EMPTY at least
	 * 
	 * Should list only those own properties whose 'enumerable' attribute is set
	 * to TRUE.
	 * 
	 * @return
	 */
	Iterator<String> baseGetOwnIterator();
	
	/**
	 * 8.6.2.1.2 [[GetOwnProperty]] (P)
	 * <p>
	 * When the [[GetOwnProperty]] method of O is called with property name P,
	 * the following steps are taken: <br>
	 * 1. If Type(O) is String, and Type(P) is Number, go to step 4.<br>
	 * 2. If O is an instance of String, and Type(P) is a Number, go to step 4.<br>
	 * 3. Go to step 6. <br>
	 * 4. Call the [[Call]] method of the specified String.prototype.charAt
	 * method with O as the this value and argument P. <br>
	 * 5. Return a DDesc describing Result(4).<br>
	 * 6. If O doesn‘t have an own property with name P, return undefined.<br>
	 * 7. If O has an own data property with name P, return a DDesc describing
	 * its current state<br>
	 * 8. If O has an own procedural property with name P, return a PDesc
	 * describing its current state.<br>
	 * 
	 * @param name
	 * @return
	 */
	BaseProperty baseGetOwnProperty(final BasePrimitiveString name);
	
	/**
	 * 8.6.2.1.2 [[GetOwnProperty]] (P)
	 * <p>
	 * When the [[GetOwnProperty]] method of O is called with property name P,
	 * the following steps are taken: <br>
	 * 1. If Type(O) is String, and Type(P) is Number, go to step 4.<br>
	 * 2. If O is an instance of String, and Type(P) is a Number, go to step 4.<br>
	 * 3. Go to step 6. <br>
	 * 4. Call the [[Call]] method of the specified String.prototype.charAt
	 * method with O as the this value and argument P. <br>
	 * 5. Return a DDesc describing Result(4).<br>
	 * 6. If O doesn‘t have an own property with name P, return undefined.<br>
	 * 7. If O has an own data property with name P, return a DDesc describing
	 * its current state<br>
	 * 8. If O has an own procedural property with name P, return a PDesc
	 * describing its current state.<br>
	 * 
	 * @param name
	 * @return
	 */
	BaseProperty baseGetOwnProperty(final String name);
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toString() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	String baseGetString(final String name, final String defaultValue);
	
	/**
	 * Returns false when no enumerable own properties available of any kind.
	 * 
	 * @return
	 */
	boolean baseHasOwnProperties();
	
	/**
	 * Returns false when no enumerable properties available of any kind.
	 * 
	 * @return
	 */
	boolean baseHasProperties();
	
	/**
	 * @return
	 */
	boolean baseIsExtensible();
	
	/**
	 * @return
	 */
	boolean baseIsPrimitive();
	
	/**
	 * @return
	 */
	boolean baseIsPrimitiveBoolean();
	
	/**
	 * @return
	 */
	boolean baseIsPrimitiveInteger();
	
	/**
	 * @return
	 */
	boolean baseIsPrimitiveNumber();
	
	/**
	 * @return
	 */
	boolean baseIsPrimitiveString();
	
	/**
	 * The value of the [[Prototype]] property must be either an object or null,
	 * and every [[Prototype]] chain must have finite length (that is, starting
	 * from any object, recursively accessing the [[Prototype]] property must
	 * eventually lead to a null value). Whether or not a native object can have
	 * a host object as its [[Prototype]] depends on the implementation.
	 * 
	 * @return prototype object or null
	 */
	BaseObject<?> basePrototype();
	
	/**
	 * Puts all elements like:<br>
	 * <code>
	 * for(String key : value){ 
	 * 	defineOwnPeoperty( key, value.get( key ), true, true, true ); 
	 * }
	 * </code>
	 * 
	 * Can save some iteration if implemented object is known to be read-only.
	 * 
	 * @param value
	 */
	void basePutAll(final BaseObject<?> value);
	
	/**
	 * 
	 * @return boolean
	 */
	BasePrimitiveBoolean baseToBoolean();
	
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
	BasePrimitiveNumber baseToInt32();
	
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
	BasePrimitiveNumber baseToInteger();
	
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
	BasePrimitiveNumber baseToNumber();
	
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
	 *            primitives must be able to accept null and always return this,
	 *            non-primitives can throw exceptions with null hint argument.
	 * @return EcmaPrimitive
	 */
	BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint);
	
	/**
	 * @return string
	 */
	BasePrimitiveString baseToString();
	
	/**
	 * Internal state information associated with this object.
	 * 
	 * @return internal
	 */
	@Override
	T baseValue();
	
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
	 * 
	 * 
	 * Can be something like java.lang.Object.equals(), but in Java equals()
	 * specification should also check for objects being different instances
	 * with same context. In JavaScript { a : 5 } == { a : 5 } will evaluate to
	 * false.
	 * 
	 * Can be something like baseEquals, but in Java equals() specification
	 * should also check for objects being different instances with same
	 * context. In JavaScript { a : 5 } == { a : 5 } will evaluate to false.
	 * 
	 * @param o
	 * @return
	 * 
	 * 
	 */
	@Override
	boolean equals(final Object o);
	
	/**
	 * should implement!
	 * 
	 * @return
	 */
	@Override
	int hashCode();
}
