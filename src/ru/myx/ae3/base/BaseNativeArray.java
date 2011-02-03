package ru.myx.ae3.base;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ru.myx.ae3.reflect.ReflectionDisable;
import ru.myx.util.IteratorSingle;

/**
 * new Array() or simply [] in javascript will create an instance of this class.
 * 
 * Методы isPrimitiveXXX зафиксированы в значение false.
 * 
 * @author myx
 * @param <T>
 * 
 */
@ReflectionDisable
public class BaseNativeArray<T> extends BaseNative<List<T>> implements BaseProperty, BaseArrayDynamic<List<T>, T>,
		List<T> {
	private final List<BaseObject<? extends T>>	array;
	
	/**
	 * 
	 */
	public BaseNativeArray() {
		super( BaseArray.PROTOTYPE );
		this.array = new ArrayList<BaseObject<? extends T>>();
	}
	
	/**
	 * @param element
	 * 
	 */
	public BaseNativeArray(final BaseObject<? extends T> element) {
		super( BaseArray.PROTOTYPE );
		this.array = new ArrayList<BaseObject<? extends T>>();
		this.baseDefaultPush( element );
	}
	
	/**
	 * @param elements
	 * 
	 */
	public BaseNativeArray(final BaseObject<? extends T>... elements) {
		super( BaseArray.PROTOTYPE );
		this.array = new ArrayList<BaseObject<? extends T>>( elements.length + 16 );
		this.baseDefaultPush( elements );
	}
	
	/**
	 * special constructor for BaseArray.PROTOTYPE
	 */
	BaseNativeArray(final boolean b) {
		super( BaseObject.PROTOTYPE );
		assert b;
		this.array = Collections.emptyList();
	}
	
	/**
	 * @param length
	 * 
	 *            This is not ecma array constructor with length argument, this
	 *            length is just a hint, see
	 *            <code>BaseNativeArray( length, value )</code> for filler
	 *            constructor
	 * 
	 */
	public BaseNativeArray(final int length) {
		super( BaseArray.PROTOTYPE );
		this.array = new ArrayList<BaseObject<? extends T>>( length );
	}
	
	/**
	 * @param length
	 * @param value
	 *            - filler value
	 * 
	 *            To emulate ecma array constructor with length argument use
	 *            expression like:
	 *            <code>new BaseNativeArray<Object>( x, BaseObject.UNDEFINED )</code>
	 */
	public BaseNativeArray(final int length, final BaseObject<? extends T> value) {
		super( BaseArray.PROTOTYPE );
		this.array = new ArrayList<BaseObject<? extends T>>( length );
		for (int i = 0; i < length; i++) {
			this.array.add( value );
		}
	}
	
	private BaseNativeArray(final List<BaseObject<? extends T>> array) {
		super( BaseArray.PROTOTYPE );
		this.array = array;
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public BaseNativeArray(final String key, final BaseObject<?> value) {
		super( BaseArray.PROTOTYPE );
		this.properties = new BasePropertyHolderString( key, value, true, true, true );
		this.array = new ArrayList<BaseObject<? extends T>>();
	}
	
	/**
	 * @param key
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BaseNativeArray(final String key,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		super( BaseArray.PROTOTYPE );
		this.properties = new BasePropertyHolderString( key, value, writable, enumerable, dynamic );
		this.array = new ArrayList<BaseObject<? extends T>>();
	}
	
	/**
	 * @param element
	 * 
	 */
	/**
	 * <code>
	public BaseNativeArray(final T element) {
		super( BaseArray.PROTOTYPE );
		this.array = new ArrayList<BaseObject<? extends T>>();
		this.add( element );
	}
	</code>
	 */
	
	/**
	 * @param elements
	 * 
	 */
	/**
	 * <code>
	public BaseNativeArray(final T... elements) {
		super( BaseArray.PROTOTYPE );
		this.array = new ArrayList<BaseObject<? extends T>>( elements.length );
		for (final T element : elements) {
			this.add( element );
		}
	}
	</code>
	 */
	
	@Override
	public void add(final int index, final T element) {
		this.array.add( index, Base.forUnknown( element ) );
	}
	
	@Override
	public final boolean add(final T object) {
		return this.array.add( Base.forUnknown( object ) );
	}
	
	@Override
	public boolean addAll(final Collection<? extends T> c) {
		return this.array.addAll( new AbstractCollection<BaseObject<? extends T>>() {
			@Override
			public Iterator<BaseObject<? extends T>> iterator() {
				final Iterator<? extends T> iterator = c.iterator();
				return new Iterator<BaseObject<? extends T>>() {
					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}
					
					@Override
					public BaseObject<? extends T> next() {
						return Base.forUnknown( iterator.next() );
					}
					
					@Override
					public void remove() {
						iterator.remove();
					}
				};
			}
			
			@Override
			public int size() {
				return c.size();
			}
		} );
	}
	
	@Override
	public boolean addAll(final int index, final Collection<? extends T> c) {
		return this.array.addAll( index, new AbstractCollection<BaseObject<? extends T>>() {
			@Override
			public Iterator<BaseObject<? extends T>> iterator() {
				final Iterator<? extends T> iterator = c.iterator();
				return new Iterator<BaseObject<? extends T>>() {
					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}
					
					@Override
					public BaseObject<? extends T> next() {
						return Base.forUnknown( iterator.next() );
					}
					
					@Override
					public void remove() {
						iterator.remove();
					}
				};
			}
			
			@Override
			public int size() {
				return c.size();
			}
		} );
	}
	
	@Override
	public BaseArray<List<T>, T> baseArray() {
		return this;
	}
	
	@Override
	public BaseArrayAdvanced<List<T>, T> baseArrayAdvanced() {
		return this;
	}
	
	@Override
	public BaseArrayDynamic<List<T>, T> baseArrayDynamic() {
		return this;
	}
	
	@Override
	public BaseArrayWritable<List<T>, T> baseArrayWritable() {
		return this;
	}
	
	@Override
	public final boolean baseContains(final BaseObject<?> object) {
		return this.array.contains( object );
	}
	
	@Override
	public BaseObject<?> baseDefaultPop() {
		final int length = this.array.size();
		return length == 0
				? BaseObject.UNDEFINED
				: this.array.remove( length - 1 );
	}
	
	@Override
	public final int baseDefaultPush(final BaseObject<? extends T> object) {
		this.array.add( object );
		return this.array.size();
	}
	
	@Override
	public final int baseDefaultPush(final BaseObject<? extends T>... objects) {
		for (final BaseObject<? extends T> object : objects) {
			this.array.add( object );
		}
		return this.array.size();
	}
	
	@Override
	public BaseObject<?> baseDefaultShift() {
		final int length = this.array.size();
		return length == 0
				? BaseObject.UNDEFINED
				: this.array.remove( 0 );
	}
	
	@Override
	public BaseArrayAdvanced<?, ?> baseDefaultSlice(final int start, final int end) {
		final int length = this.array.size();
		final int startIdx = start >= 0
				? start > length
						? length
						: start
				: Math.max( length + start, 0 );
		final int endIdx = end > 0
				? end > length
						? length
						: end
				: Math.max( length + end, 0 );
		final BaseNativeArray<Object> result = new BaseNativeArray<Object>();
		for (int i = startIdx; i < endIdx; i++) {
			result.array.add( this.array.get( i ) );
		}
		return result;
	}
	
	@Override
	public BaseArray<?, ?> baseDefaultSplice(final int start, final int count, final BaseObject<? extends T>... values) {
		/**
		 * 3. Let lenVal be the result of calling the [[Get]] internal method of
		 * O with argument "length".<br>
		 * 4. Let len be ToUint32(lenVal).
		 */
		final int length = this.array.size();
		/**
		 * 5. Let relativeStart be ToInteger(start).<br>
		 * 6. If relativeStart is negative, let actualStart be max((len +
		 * relativeStart),0); else let actualStart be min(relativeStart, len).
		 */
		final int actualStart = start < 0
				? Math.max( length + start, 0 )
				: Math.min( start, length );
		/**
		 * 7. Let actualDeleteCount be min(max(ToInteger(deleteCount),0), len –
		 * actualStart).
		 */
		final int deleteCount = Math.min( Math.max( count, 0 ), length - start );
		final BaseArray<?, ?> result = this.baseDefaultSlice( actualStart, deleteCount );
		final int insertCount = values.length;
		if (deleteCount > insertCount) {
			for (int i = deleteCount - insertCount; i > 0; i--) {
				this.array.remove( start );
			}
			for (int i = 0; i < insertCount; i++) {
				this.array.set( start + i, values[i] );
			}
		} else //
		if (deleteCount < insertCount) {
			this.array.addAll( start, Arrays.asList( values ).subList( insertCount - deleteCount, insertCount ) );
			for (int i = 0; i < insertCount - deleteCount; i++) {
				this.array.set( start + i, values[i] );
			}
		} else {
			for (int i = 0; i < insertCount; i++) {
				this.array.set( start + i, values[i] );
			}
		}
		return result;
	}
	
	@Override
	public final int baseDefaultUnshift(final BaseObject<? extends T> object) {
		this.array.add( 0, object );
		return this.array.size();
	}
	
	@Override
	public final int baseDefaultUnshift(final BaseObject<? extends T>... objects) {
		this.array.addAll( 0, Arrays.asList( objects ) );
		return this.array.size();
	}
	
	@Override
	public boolean baseDefine(
			final BasePrimitiveString namePrimitive,
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
		final BaseProperty property = this.baseGetOwnProperty( namePrimitive );
		final String nameString = namePrimitive.toString();
		if (property == null) {
			if (this.baseIsExtensible()) {
				this.properties = this.properties == null
						? new BasePropertyHolderString( nameString, value, writable, enumerable, dynamic )
						: this.properties.add( nameString, new BasePropertyHolderString( value,
								writable,
								enumerable,
								dynamic ) );
				return true;
			}
			return false;
		}
		/**
		 * 5. If Result(1) is the same as Desc, then return true.
		 */
		//
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
		if (!property.isWritable( nameString )
				|| writable != property.isWritable( nameString )
				|| enumerable != property.isEnumerable( nameString )
				|| dynamic != property.isDynamic( nameString )) {
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
		//
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
	public BaseObject<?> baseGet(final int index, final BaseObject<?> defaultValue) {
		return index >= 0 && index < this.array.size()
				? this.array.get( index )
				: defaultValue;
	}
	
	@Override
	public Iterator<String> baseGetOwnAllIterator() {
		if (this.array.isEmpty()) {
			return this.properties == null
					? new IteratorSingle<String>( BasePrimitiveString.PROPERTY_JAVA_LENGTH )
					: new IteratorSequenceString( this.properties.iteratorAll(),
							new IteratorSingle<String>( BasePrimitiveString.PROPERTY_JAVA_LENGTH ) );
		}
		if (this.properties == null) {
			return new IteratorSequenceString( new IteratorBaseArrayKey( this ),
					new IteratorSingle<String>( BasePrimitiveString.PROPERTY_JAVA_LENGTH ) );
		}
		final Iterator<String> iterator = this.properties.iteratorAll();
		assert iterator != null : "NULL iterator: use BaseObject.ITERATOR_EMPTY";
		return iterator == BaseObject.ITERATOR_EMPTY
				? new IteratorSequenceString( new IteratorSequenceString( new IteratorBaseArrayKey( this ), iterator ),
						new IteratorSingle<String>( BasePrimitiveString.PROPERTY_JAVA_LENGTH ) )
				: new IteratorSequenceString( new IteratorBaseArrayKey( this ), iterator );
	}
	
	/**
	 * Never returns NULL
	 * 
	 * @return
	 */
	@Override
	public Iterator<String> baseGetOwnIterator() {
		if (this.array.isEmpty()) {
			return this.properties == null
					? BaseObject.ITERATOR_EMPTY
					: this.properties.iteratorEnumerable();
		}
		if (this.properties == null) {
			return new IteratorBaseArrayKey( this );
		}
		final Iterator<String> iterator = this.properties.iteratorEnumerable();
		assert iterator != null : "NULL iterator: use BaseObject.ITERATOR_EMPTY";
		return iterator == BaseObject.ITERATOR_EMPTY
				? new IteratorBaseArrayKey( this )
				: new IteratorSequenceString( new IteratorBaseArrayKey( this ), iterator );
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		if (name == BasePrimitiveString.PROPERTY_BASE_LENGTH) {
			return this;
		}
		final BasePropertiesString properties = this.properties;
		return properties == null
				? null
				: properties.find( name.toString() );
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		if (BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name )) {
			return this;
		}
		final BasePropertiesString properties = this.properties;
		return properties == null
				? null
				: properties.find( name );
	}
	
	@Override
	public final boolean baseHasOwnProperties() {
		final BasePropertiesString properties = this.properties;
		return properties != null && properties.hasEnumerableProperties();
	}
	
	@Override
	public boolean baseIsExtensible() {
		return true;
	}
	
	@Override
	public Iterator<BaseObject<? extends T>> baseIterator() {
		return this.array.iterator();
	}
	
	@Override
	public BaseObject<? extends T> baseRemove(final int index) {
		return this.array.remove( index );
	}
	
	/**
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final boolean baseSet(final int index, final BaseObject<? extends T> object) {
		if (index < 0) {
			return false;
		}
		int length = this.array.size();
		if (index < length) {
			this.array.set( index, object );
			return true;
		}
		for (; length < index; length++) {
			this.array.add( (BaseObject<T>) BaseObject.UNDEFINED );
		}
		this.array.add( index, object );
		return true;
	}
	
	@Override
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this.baseToString();
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		final int length = this.size();
		if (length == 0) {
			return BasePrimitiveString.EMPTY;
		}
		final StringBuilder builder = new StringBuilder( length * 10 );
		for (int i = 0; i < length; i++) {
			final BaseObject<?> element = this.array.get( i );
			assert element != null : "NULL java value!";
			if (i != 0) {
				builder.append( ',' );
			}
			if (element == BaseObject.UNDEFINED || element == BaseObject.NULL) {
				// ignore
			} else {
				builder.append( element.baseToString().baseValue() );
			}
		}
		return Base.forString( builder.toString() );
	}
	
	@Override
	public final List<T> baseValue() {
		return this;
	}
	
	@Override
	public void clear() {
		this.array.clear();
	}
	
	@Override
	public boolean contains(final Object o) {
		return this.array.contains( o ) || this.array.contains( Base.forUnknown( o ) );
	}
	
	@Override
	public boolean containsAll(final Collection<?> c) {
		return this.array.containsAll( c );
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
		assert !this.baseIsPrimitive() : "BaseNativeObject is NOT primitive!";
		/**
		 * 14. If x is null and y is undefined, return true.<br>
		 * 15. If x is undefined and y is null, return true. <br>
		 */
		if (o == BaseObject.UNDEFINED || o == BaseObject.NULL) {
			return false;
		}
		/**
		 * 21. If Type(x) is Object and Type(y) is either String or Number,<br>
		 * return the result of the comparison ToPrimitive(x) == y.
		 */
		{
			if (object.baseIsPrimitiveString()) {
				return this.baseToPrimitive( ToPrimitiveHint.STRING ).equals( object );
			}
			if (object.baseIsPrimitiveNumber()) {
				return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).equals( object );
			}
		}
		
		/**
		 * !!! NON-STANDARD: check arrays and properties!
		 */
		if (o instanceof BaseNativeArray<?> && this.prototype == object.basePrototype()) {
			final BaseArray<?, ?> array = object.baseArray();
			final int length = this.length();
			if (array.length() != length) {
				return false;
			}
			for (int i = 0; i < length; i++) {
				final BaseObject<?> original = this.baseGet( i, BaseObject.UNDEFINED );
				assert original != null : "NULL java value";
				final BaseObject<?> effective = array.baseGet( i, BaseObject.UNDEFINED );
				assert effective != null : "NULL java value";
				if (original != effective && !original.equals( effective )) {
					return false;
				}
			}
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
	public T get(final int index) {
		return this.array.get( index ).baseValue();
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (this.array != null) {
			final int length = this.array.size();
			for (int i = 0; i < length; i++) {
				hashCode >>>= 11;
				final Object value = this.array.get( i );
				hashCode ^= value == null
						? 0
						: value.hashCode();
			}
		}
		if (this.properties != null) {
			for (final Iterator<String> iterator = this.properties.iteratorAll(); iterator != null
					&& iterator.hasNext();) {
				final String key = iterator.next();
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
		if (this.prototype != null && this.prototype != BaseObject.PROTOTYPE && this.prototype != BaseArray.PROTOTYPE) {
			hashCode >>>= 11;
			hashCode ^= this.prototype.hashCode();
		}
		return hashCode;
	}
	
	@Override
	public int indexOf(final Object o) {
		return this.array.indexOf( o );
	}
	
	@Override
	public boolean isDynamic(final String name) {
		assert BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name ) : "Expected to be equal to 'length', but: "
				+ name;
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return this.array.isEmpty();
	}
	
	@Override
	public boolean isEnumerable(final String name) {
		assert BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name ) : "Expected to be equal to 'length', but: "
				+ name;
		return false;
	}
	
	@Override
	public final boolean isProceduralSetter(final String name) {
		assert BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name ) : "Expected to be equal to 'length', but: "
				+ name;
		return false;
	}
	
	@Override
	public boolean isWritable(final String name) {
		assert BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name ) : "Expected to be equal to 'length', but: "
				+ name;
		return false;
	}
	
	@Override
	public Iterator<T> iterator() {
		final Iterator<BaseObject<? extends T>> iterator = this.array.iterator();
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public T next() {
				return iterator.next().baseValue();
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}
	
	@Override
	public int lastIndexOf(final Object o) {
		return this.array.lastIndexOf( Base.forUnknown( o ) );
	}
	
	@Override
	public int length() {
		return this.array.size();
	}
	
	@Override
	public ListIterator<T> listIterator() {
		return this.listIterator( 0 );
	}
	
	@Override
	public ListIterator<T> listIterator(final int index) {
		final ListIterator<BaseObject<? extends T>> iterator = this.array.listIterator( index );
		return new ListIterator<T>() {
			@Override
			public void add(final T e) {
				iterator.add( Base.forUnknown( e ) );
			}
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}
			
			@Override
			public boolean hasPrevious() {
				return iterator.hasPrevious();
			}
			
			@Override
			public T next() {
				return iterator.next().baseValue();
			}
			
			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}
			
			@Override
			public T previous() {
				return iterator.previous().baseValue();
			}
			
			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}
			
			@Override
			public void remove() {
				iterator.remove();
			}
			
			@Override
			public void set(final T e) {
				iterator.set( Base.forUnknown( e ) );
			}
		};
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final BasePrimitive<?> name) {
		assert BasePrimitiveString.PROPERTY_BASE_LENGTH == name : "Expected to be equal to 'length', but: " + name;
		return Base.forInteger( this.size() );
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final String name) {
		assert BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name ) : "Expected to be equal to 'length', but: "
				+ name;
		return Base.forInteger( this.size() );
	}
	
	@Override
	public BaseObject<?> propertyGetAndSet(final BaseObject<?> instance, final String name, final BaseObject<?> value) {
		assert BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name ) : "Expected to be equal to 'length', but: "
				+ name;
		return Base.forInteger( this.size() );
	}
	
	@Override
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name ) : "Expected to be equal to 'length', but: "
				+ name;
		return false;
	}
	
	/**
	 * Funny method returns same object. For use in in-line initializations.
	 * <p>
	 * Will return NULL if put fails, but this possible only when prototype
	 * object explicitly prohibits it or property was previously set as
	 * read-only.
	 * 
	 * @param value
	 * @return
	 */
	public final BaseNativeArray<T> putAppend(final BaseObject<? extends T> value) {
		return this.array.add( value )
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
	public final BaseNativeArray<T> putAppend(final String key, final BaseObject<?> value) {
		return this.baseDefine( key, value, true, true, true )
				? this
				: null;
	}
	
	@Override
	public T remove(final int index) {
		return this.array.remove( index ).baseValue();
	}
	
	@Override
	public boolean remove(final Object o) {
		return this.array.remove( o );
	}
	
	@Override
	public boolean removeAll(final Collection<?> c) {
		return this.array.removeAll( c );
	}
	
	@Override
	public boolean retainAll(final Collection<?> c) {
		return this.array.retainAll( c );
	}
	
	/**
	 * @param object
	 */
	@Override
	public final T set(final int index, final T object) {
		return this.array.set( index, Base.forUnknown( object ) ).baseValue();
	}
	
	@Override
	public int size() {
		return this.array.size();
	}
	
	@Override
	public List<T> subList(final int fromIndex, final int toIndex) {
		return new BaseNativeArray<T>( this.array.subList( fromIndex, toIndex ) );
	}
	
	@Override
	public Object[] toArray() {
		return this.array.toArray();
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(final T[] a) {
		return this.array.toArray( a );
	}
	
	@Override
	public String toString() {
		return this.array.toString();
	}
}
