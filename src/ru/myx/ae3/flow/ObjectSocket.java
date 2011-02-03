/*
 * Created on 09.12.2005
 */
package ru.myx.ae3.flow;

/**
 * @author myx
 * 
 * @param <S>
 * @param <T>
 */
public interface ObjectSocket<S, T> {
	/**
	 * @return source
	 */
	ObjectSource<S> getSource();
	
	/**
	 * @return target
	 */
	ObjectTarget<T> getTarget();
}
