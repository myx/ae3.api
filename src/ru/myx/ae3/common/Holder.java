package ru.myx.ae3.common;

/**
 * @author myx
 * 
 * @param <V>
 *            value type
 */
public interface Holder<V> extends Value<V> {
	/**
	 * Retrieves the current value stored in this holder.
	 * 
	 * @return value
	 */
	@Override
	V baseValue();
	
	/**
	 * Indicates possibility to change this holder's value via replace methods.
	 * Returns <b>true </b> when value is constant and cannot be changed.
	 * 
	 * @return boolean
	 */
	boolean execCanSet();
	
	/**
	 * Sets this holder to a new value but only if current value is equal to one
	 * specified. Returns true on success and false otherwise.
	 * 
	 * @param compare
	 * @param value
	 * @return boolean
	 */
	boolean execCompareAndSet(V compare, V value);
	
	/**
	 * Sets this holder to a new value, returns previous one.
	 * 
	 * @param value
	 * @return previous value
	 */
	V execGetAndSet(V value);
	
	/**
	 * Sets this holder to a new value, returns previous one.
	 * 
	 * @param value
	 */
	void execSet(V value);
}
