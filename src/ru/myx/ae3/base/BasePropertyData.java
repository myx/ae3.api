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
abstract class BasePropertyData<P extends BasePropertyData<P, K>, K> implements BaseProperty {
	
	/**
	 * 
	 */
	protected static final short	ATTR_WRITABLE	= 0x0001;
	
	/**
	 * 
	 */
	protected static final short	ATTR_ENUMERABLE	= 0x0002;
	
	/**
	 * 
	 */
	protected static final short	ATTR_DYNAMIC	= 0x0004;
	
	K								name;
	
	short							attributes;
	
	/**
	 * Linked list navigation
	 */
	P								prev;
	
	/**
	 * Linked list navigation
	 */
	P								next;
	
	/**
	 * @param attributes
	 */
	BasePropertyData() {
		//
	}
	
	/**
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	BasePropertyData(final boolean writable, final boolean enumerable, final boolean dynamic) {
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
	BasePropertyData(final int attributes) {
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
	public BasePropertyData(final K name, final boolean writable, final boolean enumerable, final boolean dynamic) {
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
	
	public final boolean isDynamic() {
		assert this.name != null : "Name should be established already!";
		return (this.attributes & BasePropertyData.ATTR_DYNAMIC) != 0;
	}
	
	@Override
	public abstract boolean isDynamic(final String name);
	
	public final boolean isEnumerable() {
		assert this.name != null : "Name should be established already!";
		return (this.attributes & BasePropertyData.ATTR_ENUMERABLE) != 0;
	}
	
	@Override
	public abstract boolean isEnumerable(final String name);
	
	@Override
	public final boolean isProceduralSetter(final String name) {
		return false;
	}
	
	public final boolean isWritable() {
		assert this.name != null : "Name should be established already!";
		return (this.attributes & BasePropertyData.ATTR_WRITABLE) != 0;
	}
	
	@Override
	public abstract boolean isWritable(final String name);
	
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
}
