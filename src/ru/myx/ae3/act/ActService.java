/*
 * Created on 26.03.2006
 */
package ru.myx.ae3.act;

/**
 * @author myx
 * 
 */
public interface ActService {
	/**
	 * @return true to continue, false to exit
	 * @throws Throwable
	 */
	boolean main() throws Throwable;
	
	/**
	 * @return true to continue, false to exit
	 */
	boolean start();
	
	/**
	 * @return true to continue, false to exit
	 */
	boolean stop();
	
	/**
	 * @param t
	 * @return true to continue, false to exit
	 */
	boolean unhandledException(final Throwable t);
}
