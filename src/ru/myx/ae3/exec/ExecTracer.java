package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;

/**
 * @author myx
 * 
 */
public interface ExecTracer {
	/**
	 * @param function
	 * @param thisValue
	 * @param arguments
	 * @return
	 */
	boolean traceCall(ExecFunction function, BaseObject<?> thisValue, ExecArguments<?> arguments);
	
	/**
	 * @param debug
	 * @return must return true (for use in assertions)
	 */
	boolean traceDebug(final Object debug);
}
