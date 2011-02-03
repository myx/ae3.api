package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.util.IteratorSingle;

/**
 * 
 * 8.6.1 Property Attributes
 * <p>
 * A property can have zero or more attributes from the following set:<br<
 * <b>ReadOnly</b> - The property is a read-only property. Attempts by
 * ECMAScript code to write to the property will be ignored. (Note, however,
 * that in some cases the value of a property with the ReadOnly attribute may
 * change over time because of actions taken by the host environment; therefore
 * “ReadOnly” does not mean “constant and unchanging”!)<br>
 * <b>DontEnum</b> - The property is not to be enumerated by a for-in
 * enumeration (section 12.6.4). <br>
 * <b>DontDelete</b> - Attempts to delete the property will be ignored. See the
 * description of the delete operator in section 11.4.1.<br>
 * <p>
 * Internal properties not mentioned here since they are implemented not as
 * properties.
 * <p>
 * 
 * @author myx
 * 
 */
abstract class BasePropertyDataString extends BasePropertyData<BasePropertyDataString, String> implements
		BasePropertiesString {
	
	/**
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BasePropertyDataString(final boolean writable, final boolean enumerable, final boolean dynamic) {
		this.name = null;
		this.attributes = (short) ((writable
				? BasePropertyData.ATTR_WRITABLE
				: 0) + (enumerable
				? BasePropertyData.ATTR_ENUMERABLE
				: 0) + (dynamic
				? BasePropertyData.ATTR_DYNAMIC
				: 0));
		this.next = null;
	}
	
	/**
	 * @param attributes
	 */
	public BasePropertyDataString(final int attributes) {
		this.name = null;
		this.attributes = (short) attributes;
		this.next = null;
	}
	
	/**
	 * @param name
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BasePropertyDataString(final String name,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert name != null : "Property name is NULL";
		this.name = name;
		this.attributes = (short) ((writable
				? BasePropertyData.ATTR_WRITABLE
				: 0) + (enumerable
				? BasePropertyData.ATTR_ENUMERABLE
				: 0) + (dynamic
				? BasePropertyData.ATTR_DYNAMIC
				: 0));
		this.next = null;
	}
	
	@Override
	public final BasePropertiesString add(final String name, final BasePropertyDataString property) {
		assert this.name != null : "Name should be established already!";
		assert !this.name.equals( name ) : "Should not have same name!";
		return new PropertiesStringDirectToHash( this, name, property );
	}
	
	@Override
	public final BasePropertyDataString delete(final String name) {
		assert this.name != null : "Name should be established already!";
		return this.name.equals( name )
				? null
				: this;
	}
	
	@Override
	public final BasePropertyDataString find(final String name) {
		assert this.name != null : "Name should be established already!";
		/**
		 * CODE2 <code>
		</code>
		 */
		return this.name.equals( name )
				? this
				: null;
		/**
		 * CODE1 <code>
		return this.name == name || this.name.equals( name )
				? this
				: null;
		</code>
		 */
	}
	
	@Override
	public final boolean hasEnumerableProperties() {
		return this.isEnumerable();
	}
	
	@Override
	public final boolean isDynamic(final String name) {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name == this.name || name.equals( this.name ) : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		return (this.attributes & BasePropertyData.ATTR_DYNAMIC) != 0;
	}
	
	@Override
	public final boolean isEnumerable(final String name) {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name == this.name || name.equals( this.name ) : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		return (this.attributes & BasePropertyData.ATTR_ENUMERABLE) != 0;
	}
	
	@Override
	public final boolean isWritable(final String name) {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name == this.name || name.equals( this.name ) : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		return (this.attributes & BasePropertyData.ATTR_WRITABLE) != 0;
	}
	
	@Override
	public final Iterator<String> iteratorAll() {
		return new IteratorSingle<String>( this.name );
	}
	
	@Override
	public final Iterator<String> iteratorEnumerable() {
		return this.isEnumerable()
				? new IteratorSingle<String>( this.name )
				: BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public abstract BaseObject<?> propertyGet(final BaseObject<?> instance, final BasePrimitive<?> name);
	
	@Override
	public abstract BaseObject<?> propertyGet(final BaseObject<?> instance, final String name);
	
	@Override
	public abstract BaseObject<?> propertyGetAndSet(
			final BaseObject<?> instance,
			final String name,
			final BaseObject<?> value);
	
	@Override
	public abstract boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) throws IllegalArgumentException;
	
	@Override
	public final int size() {
		return 1;
	}
}
