/*
 * Created on 25.04.2006
 */
package ru.myx.ae3.cache;

/**
 * @author myx
 * @param <A>
 *            Attachment type
 * 
 * @param <R>
 *            Result object type
 */
public interface CreationHandlerObject<A, R> {
	/**
	 * @param attachment
	 * @param key
	 * @return object
	 */
	public R create(final A attachment, final String key);
	
	/**
	 * @return ttl
	 */
	public long getTTL();
}
