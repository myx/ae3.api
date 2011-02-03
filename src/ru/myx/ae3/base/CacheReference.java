/**
 * 
 */
package ru.myx.ae3.base;

import java.lang.ref.WeakReference;

/**
 * NORMALLY SOFT REFERENCE
 * 
 * @author myx
 * 
 */
final class CacheReference<T extends CacheQueueEntry, P extends BasePrimitive<?>> extends WeakReference<P> implements
		CacheQueueEntry {
	final T	cache;
	
	CacheReference(final T cache, final P object, final CacheQueue queue) {
		super( object, queue );
		this.cache = cache;
	}
	
	@Override
	public final void cleanup() {
		this.cache.cleanup();
	}
}
