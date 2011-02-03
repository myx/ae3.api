package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseAbstractException;
import ru.myx.ae3.base.BaseObject;

/**
 * @author myx
 * 
 */
public abstract class ExecNonMaskedException extends BaseAbstractException {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3427642301252080907L;
	
	/**
	 * @param prototype
	 * 
	 */
	protected ExecNonMaskedException(final BaseObject<?> prototype) {
		super( prototype );
	}
	
	/**
	 * @param prototype
	 * @param message
	 */
	protected ExecNonMaskedException(final BaseObject<?> prototype, final String message) {
		super( prototype, message );
	}
	
	/**
	 * @param prototype
	 * @param message
	 * @param cause
	 */
	protected ExecNonMaskedException(final BaseObject<?> prototype, final String message, final Throwable cause) {
		super( prototype, message, cause );
	}
}
