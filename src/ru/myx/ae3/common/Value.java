package ru.myx.ae3.common;

/**
 * @author myx
 * 
 * @param <V>
 *            result
 */
public interface Value<V> {
	/**
	 * Retrieves the current value
	 * 
	 * @return value
	 * @throws WaitTimeoutException
	 *             this maskable exception is for those cases when this method
	 *             is backed by some kind of asynchronous system and timed out.
	 */
	V baseValue() throws WaitTimeoutException;
}
