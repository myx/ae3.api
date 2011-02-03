package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.util.IteratorSingle;

abstract class BasePropertyDataPrimitive extends BasePropertyData<BasePropertyDataPrimitive, BasePrimitive<?>>
		implements BasePropertiesPrimitive {
	/**
	 * @param name
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BasePropertyDataPrimitive(final BasePrimitive<?> name,
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
	
	/**
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BasePropertyDataPrimitive(final boolean writable, final boolean enumerable, final boolean dynamic) {
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
	public BasePropertyDataPrimitive(final int attributes) {
		super( attributes );
		this.name = null;
		this.attributes = (short) attributes;
		this.next = null;
	}
	
	@Override
	public final BasePropertiesPrimitive add(final BasePrimitive<?> name, final BasePropertyDataPrimitive property) {
		assert this.name != null : "Name should be established already!";
		assert this.name != name : "Should not have same name!";
		return new PropertiesPrimitiveDirectToHash( this, name, property );
	}
	
	@Override
	public final BasePropertyDataPrimitive delete(final BasePrimitive<?> name) {
		assert this.name != null : "Name should be established already!";
		return this.name == name
				? null
				: this;
	}
	
	@Override
	public BasePropertiesPrimitive delete(final BasePropertyDataPrimitive name) {
		return this == name
				? null
				: this;
	}
	
	@Override
	public final BasePropertyDataPrimitive find(final BasePrimitive<?> name) {
		assert this.name != null : "Name should be established already!";
		return this.name == name
				? this
				: null;
	}
	
	@Override
	public final boolean hasEnumerableProperties() {
		return this.isEnumerable();
	}
	
	@Override
	public final boolean isDynamic(final String name) {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name == this.name.toString() || name.equals( this.name.toString() ) : "Names must match for this type of property: (this.name="
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
		assert name == this.name.toString() || name.equals( this.name.toString() ) : "Names must match for this type of property: (this.name="
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
		assert name == this.name.toString() || name.equals( this.name.toString() ) : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		return (this.attributes & BasePropertyData.ATTR_WRITABLE) != 0;
	}
	
	@Override
	public final Iterator<BasePrimitive<?>> iteratorAll() {
		return new IteratorSingle<BasePrimitive<?>>( this.name );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final Iterator<BasePrimitive<?>> iteratorEnumerable() {
		return this.isEnumerable()
				? new IteratorSingle<BasePrimitive<?>>( this.name )
				: (Iterator<BasePrimitive<?>>) (Iterator<?>) BaseObject.ITERATOR_EMPTY;
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
