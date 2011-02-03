/**
 * 
 */
package ru.myx.ae3.base;

import java.lang.ref.ReferenceQueue;

final class CacheQueue extends ReferenceQueue<BaseObject<?>> {
	static final CacheQueue	QUEUE_INTEGER	= new CacheQueue();
	
	static final CacheQueue	QUEUE_LONG		= new CacheQueue();
	
	static final CacheQueue	QUEUE_DOUBLE	= new CacheQueue();
	
	static final CacheQueue	QUEUE_STRING	= new CacheQueue();
	
	private CacheQueue() {
		//
	}
	
	/**
	 * WARNING: ensure this to be called
	 * 
	 * @throws InterruptedException
	 */
	final void checkLoop() throws InterruptedException {
		for (;;) {
			// final CacheQueueEntry object = (CacheQueueEntry)
			// CacheQueue.QUEUE.poll();
			final CacheQueueEntry object = (CacheQueueEntry) this.remove();
			assert object != null : "says: ...blocks until one becomes available";
			object.cleanup();
		}
	}
}
