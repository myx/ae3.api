package ru.myx.ae3.cache;

import ru.myx.ae3.common.WaitTimeoutException;

/**
 * @author myx
 * 
 */
public final class CacheWaitTimeoutException extends WaitTimeoutException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3964670765959973976L;
	
	/**
	 * @param message
	 */
	public CacheWaitTimeoutException(final String message) {
		super( message );
	}
}
