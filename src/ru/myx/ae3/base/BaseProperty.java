/**
 * 
 */
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
public interface BaseProperty {
	
	/**
	 * @param name
	 * @return boolean
	 */
	boolean isDynamic(String name);
	
	/**
	 * @param name
	 * 
	 * @return boolean
	 */
	boolean isEnumerable(String name);
	
	/**
	 * @param name
	 * @return
	 */
	boolean isProceduralSetter(final String name);
	
	/**
	 * @param name
	 * @return boolean
	 */
	boolean isWritable(String name);
	
	/**
	 * @param instance
	 * @param name
	 * @return value
	 */
	BaseObject<?> propertyGet(BaseObject<?> instance, BasePrimitive<?> name);
	
	/**
	 * @param instance
	 * @param name
	 * @return value
	 */
	BaseObject<?> propertyGet(BaseObject<?> instance, String name);
	
	/**
	 * FUCKING METHOD USED ONLY ONCE!!!
	 * 
	 * @param instance
	 * @param name
	 * @param value
	 * 
	 * @return
	 */
	BaseObject<?> propertyGetAndSet(BaseObject<?> instance, String name, BaseObject<?> value);
	
	/**
	 * 
	 * Should not change attributes. Attributes are still provided for
	 * 'synthetic' property objects, which are always existing, for them to be
	 * able to set 'em for newly created properties.
	 * 
	 * @param instance
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	boolean propertySet(
			BaseObject<?> instance,
			String name,
			BaseObject<?> value,
			boolean writable,
			boolean enumerable,
			boolean dynamic);
}
