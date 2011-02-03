package ru.myx.ae3.common;

/**
 * @author myx
 * 
 */
public class WaitTimeoutException extends RuntimeException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5004925137770379151L;
	
	/**
	 * @param message
	 */
	public WaitTimeoutException(final String message) {
		super( message );
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public WaitTimeoutException(final String message, final Throwable cause) {
		super( message, cause );
	}
}
