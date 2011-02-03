/*
 * Created on 01.12.2005
 */
package ru.myx.ae3.flow;

/**
 * @author myx
 * 
 * @param <T>
 */
public interface ObjectSource<T> {
	/**
	 * 
	 */
	public static final ObjectSource<?>	NUL_SOURCE	= new NulSource();
	
	/**
	 * Connects a target to this source. This method returns immediately. All
	 * data that becomes available in this source will be passed to an
	 * Absorb.Target instance passed to this method. Target will be closed when
	 * this source is exausted. Target will remain connected till this source is
	 * exhausted or target will indicate no more interest via <b>false </b>
	 * result of one of absorbation method calls. Throws IllegalStateException
	 * if this source already has a target connected to it.
	 * 
	 * @param traget
	 * @throws Exception
	 */
	void connectTarget(final ObjectTarget<T> traget) throws Exception;
	
	/**
	 * Returns <b>true </b> to indicate that there are no more objects to be
	 * ever retrieved via any of nextXXX methods.
	 * 
	 * @return boolean
	 */
	boolean isExhausted();
	
	/**
	 * Returns <b>true </b> to indicate that there is at least one object to be
	 * retrieved via one of nextXXX methods.
	 * 
	 * @return boolean
	 */
	boolean isReady();
	
	/**
	 * Returns next available object.
	 * 
	 * @return object
	 */
	T next();
}
