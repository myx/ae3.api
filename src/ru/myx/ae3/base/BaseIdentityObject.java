package ru.myx.ae3.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.Bindings;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * new Object() or simply {} in javascript will create an instance of this
 * class.
 * 
 * Методы isPrimitiveXXX зафиксированы в значение false.
 * 
 * @author myx
 * 
 */
@ReflectionDisable
public final class BaseIdentityObject extends BaseIdentity<Bindings> implements Bindings
/**
 * Bindings are Map<String, Object>
 */
{
	
	/**
	 * @param object
	 * @return
	 */
	public static final BaseIdentityObject fromBase(final BaseObject<?> object) {
		return object instanceof BaseIdentityObject
				? (BaseIdentityObject) object
				: new BaseIdentityObject( object );
	}
	
	/**
	 * @param map
	 * @return
	 */
	public static final BaseIdentityObject fromMap(final Map<String, Object> map) {
		if (map instanceof BaseIdentityObject) {
			return (BaseIdentityObject) map;
		}
		final BaseIdentityObject base = new BaseIdentityObject();
		if (map != null) {
			base.putAll( map );
		}
		return base;
	}
	
	/**
	 * 
	 */
	public BaseIdentityObject() {
		super( BaseObject.PROTOTYPE );
		assert !this.baseHasOwnProperties() : "Own properties!";
	}
	
	/**
	 * @param prototype
	 *            null to specify that object should not have any prototype.
	 *            normal objects should have prototype chain getting us to
	 *            BaseObject.PROTOTYPE
	 */
	public BaseIdentityObject(final BaseObject<?> prototype) {
		super( prototype );
		assert !this.baseHasOwnProperties() : "Own properties!";
		assert prototype == null || prototype.baseArray() == null : "Array prototype for native object!";
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public BaseIdentityObject(final BasePrimitiveString key, final BaseObject<?> value) {
		super( BaseObject.PROTOTYPE );
		assert !this.baseHasOwnProperties() : "Own properties!";
		this.properties = new BasePropertyHolderPrimitive( key, value, true, true, true );
	}
	
	/**
	 * @param key
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BaseIdentityObject(final BasePrimitiveString key,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		super( BaseObject.PROTOTYPE );
		assert !this.baseHasOwnProperties() : "Own properties!";
		this.properties = new BasePropertyHolderPrimitive( key, value, writable, enumerable, dynamic );
	}
	
	/**
	 * Not an array
	 */
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	/**
	 * overrides default iterator implementation.
	 */
	@Override
	public void baseClear() {
		this.properties = null;
	}
	
	@Override
	public boolean baseDefine(
			final BasePrimitiveString nameInstance,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert nameInstance != null : "Name is NULL";
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
		final BaseProperty property = this.baseGetOwnProperty( nameInstance );
		if (property == null) {
			/**
			 * IS extensible!
			 */
			// if (this.baseIsExtensible()) {
			this.properties = this.properties == null
					? new BasePropertyHolderPrimitive( nameInstance, value, writable, enumerable, dynamic )
					: this.properties.add( nameInstance, new BasePropertyHolderPrimitive( value,
							writable,
							enumerable,
							dynamic ) );
			return true;
			// }
			// return false;
		}
		final String nameString = nameInstance.toString();
		/**
		 * 5. If Result(1) is the same as Desc, then return true.
		 */
		// !!! ignore
		/**
		 * 6. If the [[Dynamic]] attribute of Result(1) is true, then<br>
		 * a. Alter the P property of O to have the state described by Desc. <br>
		 * b. Return true. <br>
		 */
		if (property.isDynamic( nameString )) {
			return property.propertySet( this, nameString, value, writable, enumerable, dynamic );
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
		if (dynamic
				|| !property.isWritable( nameString )
				|| writable != property.isWritable( nameString )
				|| enumerable != property.isEnumerable( nameString )) {
			return false;
		}
		/**
		 * 10. Alter the [[Value]] field of the P property of O to be the same
		 * as the [[Value]] field of Desc, thereby altering the property to have
		 * the state described by Desc. <br>
		 */
		return property.propertySet( this, nameString, value, writable, enumerable, dynamic );
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert name != null : "Name is NULL";
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
		final BasePrimitiveString nameInstance = Base.forString( name );
		final BaseProperty property = this.baseGetOwnProperty( nameInstance );
		if (property == null) {
			/**
			 * IS extensible!
			 */
			// if (this.baseIsExtensible()) {
			this.properties = this.properties == null
					? new BasePropertyHolderPrimitive( nameInstance, value, writable, enumerable, dynamic )
					: this.properties.add( nameInstance, new BasePropertyHolderPrimitive( value,
							writable,
							enumerable,
							dynamic ) );
			return true;
			// }
			// return false;
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
		if (dynamic
				|| !property.isWritable( name )
				|| writable != property.isWritable( name )
				|| enumerable != property.isEnumerable( name )) {
			return false;
		}
		/**
		 * 10. Alter the [[Value]] field of the P property of O to be the same
		 * as the [[Value]] field of Desc, thereby altering the property to have
		 * the state described by Desc. <br>
		 */
		return property.propertySet( this, name, value, writable, enumerable, dynamic );
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
		assert name != null : "property name shouldn't be NULL";
		final BasePropertiesPrimitive properties = this.properties;
		if (properties == null) {
			return true;
		}
		final BasePrimitiveString nameInstance = Base.forString( name );
		final BaseProperty property = properties.find( nameInstance );
		if (property == null) {
			return true;
		}
		if (property.isDynamic( name )) {
			this.properties = properties.delete( nameInstance );
			return true;
		}
		return false;
	}
	
	@Override
	public final BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		if (this.properties != null) {
			for (final BaseObject<?> property = this.properties.findAndGet( this, name ); property != null;) {
				return property;
			}
			/**
			 * OLD CODE<code>
			for (final BaseProperty property = this.properties.find( name.baseToString() ); property != null;) {
				return property.propertyGet( this, name );
			}
			 * </code>
			 */
		}
		for (BaseObject<?> object = this.prototype; object != null; object = object.basePrototype()) {
			for (final BaseProperty property = object.baseGetOwnProperty( name ); property != null;) {
				return property.propertyGet( this, name );
			}
		}
		return defaultValue;
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
				: new IteratorStringForPrimitive( this.properties.iteratorAll() );
	}
	
	/**
	 * Never returns NULL
	 * 
	 * @return
	 */
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return this.properties == null
				? BaseObject.ITERATOR_EMPTY
				: new IteratorStringForPrimitive( this.properties.iteratorEnumerable() );
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		final BasePropertiesPrimitive properties = this.properties;
		return properties == null
				? null
				: properties.find( name );
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		final BasePropertiesPrimitive properties = this.properties;
		return properties == null
				? null
				: properties.find( Base.forString( name ) );
	}
	
	@Override
	public boolean baseHasOwnProperties() {
		final BasePropertiesPrimitive properties = this.properties;
		return properties != null && properties.hasEnumerableProperties();
	}
	
	@Override
	public final boolean baseIsExtensible() {
		return true;
	}
	
	@Override
	public Bindings baseValue() {
		return this;
	}
	
	@Override
	public void clear() {
		this.baseClear();
	}
	
	@Override
	public boolean containsKey(final Object key) {
		return Base.hasProperty( this, String.valueOf( key ) );
	}
	
	@Override
	public boolean containsValue(final Object value) {
		for (final Iterator<String> iterator = this.baseGetIterator(); iterator.hasNext();) {
			final String key = iterator.next();
			final BaseObject<?> x = this.baseGet( key );
			if (x == value
					|| x.baseValue() == value
					|| x.baseValue() == null
					&& value == null
					|| x.baseValue().equals( value )) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		return new UtilMapEntrySet( this );
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
		/**
		 * 5. If x is NaN, return false. <br>
		 * 6. If y is NaN, return false. <br>
		 */
		if (o == BasePrimitiveNumber.NAN) {
			return false;
		}
		/**
		 * 14. If x is null and y is undefined, return true.<br>
		 * 15. If x is undefined and y is null, return true. <br>
		 */
		if (o == BaseObject.UNDEFINED || o == BaseObject.NULL) {
			return false;
		}
		final BaseObject<?> object = (BaseObject<?>) o;
		assert !this.baseIsPrimitive() : "BaseIdentityObject is NOT primitive!";
		{
			if (object.baseIsPrimitive()) {
				/**
				 * 18. If Type(x) is Boolean, return the result of the
				 * comparison ToNumber(x) == y.<br>
				 * 19. If Type(y) is Boolean, return the result of the
				 * comparison x == ToNumber(y). <br>
				 */
				if (object.baseIsPrimitiveBoolean()) {
					return this.baseToNumber().equals( object );
				}
				/**
				 * 20. If Type(x) is either String or Number and Type(y) is
				 * Object, return the result of the comparison x ==
				 * ToPrimitive(y).<br>
				 * 21. If Type(x) is Object and Type(y) is either String or
				 * Number, return the result of the comparison ToPrimitive(x) ==
				 * y.<br>
				 */
				if (object.baseIsPrimitiveString()) {
					return this.baseToPrimitive( ToPrimitiveHint.STRING ).equals( object );
				}
				if (object.baseIsPrimitiveNumber()) {
					return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).equals( object );
				}
				/**
				 * 
				 */
				return this.baseToPrimitive( null ).equals( object );
			}
		}
		
		/**
		 * !!! NON-STANDARD: check properties recursively!
		 */
		if (o instanceof BaseIdentity<?> && this.prototype == object.basePrototype()) {
			for (final Iterator<String> iterator = this.baseGetOwnAllIterator(); iterator.hasNext();) {
				final String key = iterator.next();
				final BaseObject<?> original = this.baseGet( key );
				assert original != null : "NULL java value";
				final BaseObject<?> effective = object.baseGet( key );
				assert effective != null : "NULL java value";
				if (original != effective && !original.equals( effective )) {
					return false;
				}
			}
			for (final Iterator<String> iterator = object.baseGetOwnAllIterator(); iterator.hasNext();) {
				final String key = iterator.next();
				final BaseObject<?> original = this.baseGet( key );
				assert original != null : "NULL java value";
				final BaseObject<?> effective = object.baseGet( key );
				assert effective != null : "NULL java value";
				if (original != effective && !original.equals( effective )) {
					return false;
				}
			}
			return true;
		}
		/**
		 * 22. Return false.
		 */
		return false;
	}
	
	@Override
	public Object get(final Object key) {
		return this.baseGet( String.valueOf( key ) ).baseValue();
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (this.properties != null) {
			for (final Iterator<BasePrimitive<?>> iterator = this.properties.iteratorAll(); iterator != null
					&& iterator.hasNext();) {
				final BasePrimitive<?> key = iterator.next();
				{
					hashCode >>>= 11;
					hashCode ^= key.hashCode();
				}
				{
					hashCode >>>= 11;
					final BaseProperty property = this.properties.find( key );
					hashCode ^= property.propertyGet( this, key ).hashCode();
				}
			}
		}
		if (this.prototype != null && this.prototype != BaseObject.PROTOTYPE) {
			hashCode >>>= 11;
			hashCode ^= this.prototype.hashCode();
		}
		return hashCode;
	}
	
	@Override
	public boolean isEmpty() {
		return !this.baseHasProperties();
	}
	
	@Override
	public Set<String> keySet() {
		return new UtilMapKeySet( this );
	}
	
	@Override
	public Object put(final String key, final Object value) {
		final String name = String.valueOf( key );
		try {
			return this.baseGet( name ).baseValue();
		} finally {
			Base.put( this, name, Base.forUnknown( value ) );
		}
	}
	
	@Override
	public void putAll(final Map<? extends String, ? extends Object> t) {
		for (final Map.Entry<? extends String, ? extends Object> entry : t.entrySet()) {
			this.put( entry.getKey(), entry.getValue() );
		}
	}
	
	/**
	 * Funny method returns same object. For use in in-line initializations.
	 * <p>
	 * Will return NULL if put fails, but this possible only when prototype
	 * object explicitly prohibits it or property was previously set as
	 * read-only.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public final BaseIdentityObject putAppend(final BasePrimitiveString key, final BaseObject<?> value) {
		return this.baseDefine( key, value, true, true, true )
				? this
				: null;
	}
	
	/**
	 * Funny method returns same object. For use in in-line initializations.
	 * <p>
	 * Will return NULL if put fails, but this possible only when prototype
	 * object explicitly prohibits it or property was previously set as
	 * read-only.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public final BaseIdentityObject putAppend(final String key, final BaseObject<?> value) {
		return this.baseDefine( key, value, true, true, true )
				? this
				: null;
	}
	
	@Override
	public Object remove(final Object key) {
		final String name = String.valueOf( key );
		try {
			return this.baseGet( name ).baseValue();
		} finally {
			this.baseDelete( name );
		}
	}
	
	@Override
	public int size() {
		int count = 0;
		for (final Iterator<String> keys = this.baseGetOwnIterator(); keys.hasNext(); keys.next()) {
			count++;
		}
		return count;
	}
	
	@Override
	public String toString() {
		return "[object Object]";
	}
	
	@Override
	public Collection<Object> values() {
		return new UtilMapValues( this );
	}
	
}
