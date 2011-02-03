/*
 * Created on 09.12.2005
 */
package ru.myx.ae3.act;

/**
 * @author myx
 * 
 * @param <A>
 * @param <R>
 */
public interface ActFunction<A, R> {
	/**
	 * Executes Runnable, returns NULL
	 */
	ActFunction<Runnable, Void>	FUNCTION_RUN_RUNNABLE	= new FunctionRunRunnable();
	
	/**
	 * @param arg
	 * @return function result
	 * @throws Exception
	 */
	R execute(final A arg) throws Exception;
}
