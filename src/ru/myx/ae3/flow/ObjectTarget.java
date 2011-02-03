/*
 * Created on 01.12.2005
 */
package ru.myx.ae3.flow;

/**
 * @author myx
 * 
 * @param <T>
 */
public interface ObjectTarget<T> {
	/**
	 * Dummy target. Can be used anywhere ;) Absorb method returns <b>true </b>
	 * value to exhaust any source.
	 */
	public static final ObjectTarget<Object>	NUL_TARGET	= new NulTarget();
	
	/**
	 * @param object
	 * @return boolean
	 * @throws Exception
	 */
	boolean absorb(final T object) throws Exception;
	
	/**
	 * Returns base class that this target is able to absorb, i.e. Object.class
	 * if any kinds of object are acceptable.
	 * 
	 * @return class
	 */
	Class<? extends T> accepts();
	
	/**
	 * 
	 */
	void close();
}
