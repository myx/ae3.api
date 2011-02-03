package ru.myx.ae3.base;

import java.util.Date;
import java.util.Iterator;

import ru.myx.ae3.Engine;
import ru.myx.ae3.help.Format;
import ru.myx.ae3.reflect.ReflectionHidden;

/**
 * @author myx
 * 
 */
public class BaseDate extends Date implements BaseObject<Date> {
	/**
	 * 
	 */
	private static final long			serialVersionUID	= -8671137391039373014L;
	
	/**
	 * 
	 */
	@ReflectionHidden
	public static final BaseObject<?>	PROTOTYPE			= new BaseNativeObject( BaseObject.PROTOTYPE );
	
	private BasePropertiesString		properties			= null;
	
	/**
	 * 
	 */
	public static final BaseDate		UNKNOWN				= Base.forDate( new Date( 0L ) {
																private static final long	serialVersionUID	= -2348545687999247899L;
																
																@Override
																public final long getTime() {
																	return -1L;
																}
																
																@Override
																public final void setTime(final long time) {
																	throw new UnsupportedOperationException( "UNDEFINED date - read-only!" );
																}
															} );
	
	/**
	 * 
	 */
	public static final BaseDate		UNDEFINED			= BaseDate.UNKNOWN;
	
	/**
	 * 
	 */
	public static final BaseDate		NOW					= Base.forDate( Engine.CURRENT_TIME );
	
	/**
	 * Current date
	 */
	public BaseDate() {
		super();
	}
	
	/**
	 * @param date
	 * 
	 */
	public BaseDate(final Date date) {
		super( date.getTime() );
	}
	
	/**
	 * @param date
	 * 
	 */
	public BaseDate(final long date) {
		super( date );
	}
	
	/**
	 * Not an array
	 */
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
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
		return "Object";
	}
	
	/**
	 * overrides default iterator implementation.
	 */
	
	@Override
	public void baseClear() {
		this.properties = null;
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
		return this.baseDefine( name.toString(), value, writable, enumerable, dynamic );
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert value != null : "Shouldn't be NULL, use BaseObject.UNDEFINED or BaseObject.NULL instead";
		/**
		 * 1. Call the [[GetOwnProperty]] method of O with property name P.<br>
		 * 2. Get the [[Extensible]] property of O. <br>
		 * 3. If Result(1) is undefined and Result(2) is true, then<br>
		 * a. Create an own property named P of object O whose state is
		 * described by Desc.<br>
		 * b. Return true. <br>
		 * 4. If Result(1) is undefined and Result(2) is false, then<br>
		 * a. If Strict is true, then throw TypeError. <br>
		 * b. Else return false. <br>
		 * 
		 */
		final BaseProperty property = this.baseGetOwnProperty( name );
		if (property == null) {
			if (this.baseIsExtensible()) {
				this.properties = this.properties == null
						? new BasePropertyHolderString( name, value, writable, enumerable, dynamic )
						: this.properties.add( name,
								new BasePropertyHolderString( value, writable, enumerable, dynamic ) );
				return true;
			}
			return false;
		}
		/**
		 * 5. If Result(1) is the same as Desc, then return true.
		 */
		// !!! ignore
		/**
		 * 6. If the [[Dynamic]] attribute of Result(1) is true, then<br>
		 * a. Alter the P property of O to have the state described by Desc. <br>
		 * b. Return true. <br>
		 */
		if (property.isDynamic( name )) {
			return property.propertySet( this, name, value, writable, enumerable, dynamic );
		}
		/**
		 * 7. If Result(1) or Desc is a PDesc, then<br>
		 * a. If Strict is true, then throw something. <br>
		 * b. Else return false. <br>
		 */
		// !!! skipped
		/**
		 * 8. Get the [[Writeable]] component of Result(1), which is a DDesc.<br>
		 * 9. If Result(8) is false, or if Result(1) and Desc differ in any
		 * component besides [[Value]], then<br>
		 * a. If Strict is true, then throw something. <br>
		 * b. Else return false. <br>
		 */
		if (!property.isWritable( name )
				|| writable != property.isWritable( name )
				|| enumerable != property.isEnumerable( name )
				|| dynamic != property.isDynamic( name )) {
			return false;
		}
		/**
		 * 10. Alter the [[Value]] field of the P property of O to be the same
		 * as the [[Value]] field of Desc, thereby altering the property to have
		 * the state described by Desc. <br>
		 */
		return property.propertySet( this, name, value, writable, enumerable, dynamic );
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
	public final boolean baseDelete(final String name) {
		assert name != null : "property name shouldn't be NULL";
		final BasePropertiesString properties = this.properties;
		if (properties == null) {
			return true;
		}
		final BaseProperty property = properties.find( name );
		if (property == null) {
			return true;
		}
		if (property.isDynamic( name )) {
			this.properties = properties.delete( name );
			return true;
		}
		return false;
	}
	
	@Override
	public final BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = BaseDate.PROTOTYPE;;) {
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
		assert name != null : "NULL java object as a property name";
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = BaseDate.PROTOTYPE;;) {
			final BaseProperty property = object.baseGetOwnProperty( name );
			if (property != null) {
				return property.propertyGet( this, name );
			}
			object = object.basePrototype();
			if (object == null) {
				return BaseObject.UNDEFINED;
			}
		}
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name, final BaseObject<?> defaultValue) {
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = BaseDate.PROTOTYPE;;) {
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
		final Iterator<String> iterator = this.baseGetOwnIterator();
		assert iterator != null : "NULL iterator: use BaseObject.ITERATOR_EMPTY";
		final BaseObject<?> prototype = this.basePrototype();
		if (prototype == null) {
			return iterator;
		}
		if (iterator == BaseObject.ITERATOR_EMPTY) {
			return prototype.baseGetIterator();
		}
		assert iterator.hasNext() : "Should not be empty, class=" + this.getClass() + "!";
		return new IteratorHierarchy( iterator, prototype );
	}
	
	/**
	 * Never returns NULL
	 * 
	 * @return
	 */
	
	@Override
	public Iterator<String> baseGetOwnAllIterator() {
		return this.properties == null
				? BaseObject.ITERATOR_EMPTY
				: this.properties.iteratorAll();
	}
	
	/**
	 * Never returns NULL. Should return BaseObject.ITERATOR_EMPTY at least.
	 * 
	 * @return
	 */
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return this.properties == null
				? BaseObject.ITERATOR_EMPTY
				: this.properties.iteratorEnumerable();
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		final BasePropertiesString properties = this.properties;
		if (properties == null) {
			return null;
		}
		return properties.find( name.toString() );
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		final BasePropertiesString properties = this.properties;
		if (properties == null) {
			return null;
		}
		return properties.find( name );
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
	public final boolean baseHasOwnProperties() {
		final BasePropertiesString properties = this.properties;
		return properties != null && properties.hasEnumerableProperties();
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
		return true;
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
		return BaseDate.PROTOTYPE;
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
	 * A scope chain that defines the environment in which a Function object is
	 * executed.
	 * 
	 * @return scope
	 */
	public BaseObject<?> baseScope() {
		return null;
	}
	
	/**
	 * Always TRUE
	 */
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
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
	public BasePrimitiveNumber baseToInt32() {
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
	public BasePrimitiveNumber baseToInteger() {
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
	public BasePrimitiveNumber baseToNumber() {
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
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		/**
		 * FIXME: looks like totally fucked - slow by design :(
		 */
		if (hint == null || hint == ToPrimitiveHint.NUMBER) {
			{
				final BaseObject<?> valueOf = this.baseGet( "valueOf" );
				assert valueOf != null : "Never returns java NULLs";
				if (BaseDate.PROTOTYPE.baseGet( "valueOf" ) != valueOf) {
					final BaseFunction<?> call = valueOf.baseCall();
					if (call != null) {
						throw new UnsupportedOperationException( "Cannot make calls here yet!" );
					}
				}
			}
			{
				final BaseObject<?> toString = this.baseGet( "toString" );
				assert toString != null : "Never returns java NULLs";
				if (BaseDate.PROTOTYPE.baseGet( "toString" ) != toString) {
					final BaseFunction<?> call = toString.baseCall();
					if (call != null) {
						throw new UnsupportedOperationException( "Cannot make calls here yet!" );
					}
				}
			}
			return Base.forLong( this.getTime() );
		}
		{
			{
				final BaseObject<?> toString = this.baseGet( "toString" );
				assert toString != null : "Never returns java NULLs";
				if (BaseDate.PROTOTYPE.baseGet( "toString" ) != toString) {
					final BaseFunction<?> call = toString.baseCall();
					if (call != null) {
						throw new UnsupportedOperationException( "Cannot make calls here yet!" );
					}
				}
			}
			{
				final BaseObject<?> valueOf = this.baseGet( "valueOf" );
				assert valueOf != null : "Never returns java NULLs";
				if (BaseDate.PROTOTYPE.baseGet( "valueOf" ) != valueOf) {
					final BaseFunction<?> call = valueOf.baseCall();
					if (call != null) {
						throw new UnsupportedOperationException( "Cannot make calls here yet!" );
					}
				}
			}
			return Base.forString( this.toISOString() );
		}
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return Base.forString( Format.Ecma.date( this ) );
	}
	
	@Override
	public Date baseValue() {
		return this;
	}
	
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
		 * !!! NON-STANDARD: Date only, java enchancement!
		 */
		if (o instanceof Date) {
			return this.getTime() == ((Date) o).getTime();
		}
		/**
		 * 22. Return false.
		 */
		return false;
	}
	
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
	
	/**
	 * @return
	 */
	public String toISOString() {
		return Format.Ecma.date( this );
	}
	
	@Override
	public String toString() {
		return Format.Compact.dateRelative( this );
	}
	
	/**
	 * @return
	 */
	public String toUTCString() {
		return Format.Web.date( this );
	}
	
	/**
	 * JavaScript date.UTC() method<br>
	 * 
	 * Returns the number of milliseconds in a date string since midnight of
	 * January 1, 1970, according to universal time
	 * 
	 * @return
	 */
	public final long UTC() {
		return this.getTime();
	}
	
}
