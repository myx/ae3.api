package ru.myx.ae3.base;

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
final class BasePropertyFilterPrimitive extends BasePropertyDataPrimitive {
	
	BaseProperty	property;
	
	String			propertyName;
	
	/**
	 * @param name
	 * @param property
	 * @param propertyName
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BasePropertyFilterPrimitive(final BasePrimitiveString name,
			final BaseProperty property,
			final String propertyName,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		super( name, writable, enumerable, dynamic );
		if (property instanceof BasePropertyFilterPrimitive) {
			final BasePropertyFilterPrimitive filter = (BasePropertyFilterPrimitive) property;
			this.property = filter.property;
			this.propertyName = filter.propertyName;
		} else {
			assert property != null : "NULL property!";
			this.property = property;
			this.propertyName = propertyName;
		}
	}
	
	/**
	 * @param property
	 * @param propertyName
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public BasePropertyFilterPrimitive(final BaseProperty property,
			final String propertyName,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		super( writable, enumerable, dynamic );
		if (property instanceof BasePropertyFilterPrimitive) {
			final BasePropertyFilterPrimitive filter = (BasePropertyFilterPrimitive) property;
			this.property = filter.property;
			this.propertyName = filter.propertyName;
		} else {
			assert property != null : "NULL property!";
			this.property = property;
			this.propertyName = propertyName;
		}
	}
	
	@Override
	public final BaseObject<?> findAndGet(final BaseObject<?> instance, final BasePrimitive<?> name) {
		assert this.name != null : "Name should be established already!";
		return this.name == name
				? this.property.propertyGet( instance, name )
				: null;
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final BasePrimitive<?> name) {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name == this.name : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		return this.property.propertyGet( instance, this.propertyName );
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final String name) {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name.equals( this.name.toString() ) : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		return this.property.propertyGet( instance, this.propertyName );
	}
	
	@Override
	public BaseObject<?> propertyGetAndSet(final BaseObject<?> instance, final String name, final BaseObject<?> value) {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name.equals( this.name.toString() ) : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		return this.property.propertyGetAndSet( instance, this.propertyName, value );
	}
	
	@Override
	public final boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) throws IllegalArgumentException {
		assert this.name != null : "Name should be established already!";
		assert name != null : "Name is NULL";
		assert name.equals( this.name.toString() ) : "Names must match for this type of property: (this.name="
				+ this.name
				+ ", name="
				+ name
				+ ")";
		/**
		 * must not change attributes.
		 */
		return this.property.propertySet( instance, this.propertyName, value, writable, enumerable, dynamic );
	}
}
