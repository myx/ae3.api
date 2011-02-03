/**
 * 
 */
package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseArray;
import ru.myx.ae3.base.BaseFunction;
import ru.myx.ae3.base.BaseObject;

/**
 * Replace with BaseArray
 * 
 * @author myx
 * @param <T>
 * 
 */
public interface ExecArguments<T extends Object> extends BaseArray<T, Object> {
	
	/**
	 * Argument at given index.
	 * 
	 * @param i
	 * 
	 * @return object
	 */
	@Override
	BaseObject<?> baseGet(int i, BaseObject<?> defaultValue);
	
	/**
	 * @return
	 * 
	 */
	BaseFunction<?> execCallee();
	
	/**
	 * @return clone or this
	 */
	ExecArguments<?> execDetachable();
	
	/**
	 * @return boolean
	 */
	boolean hasNamedArguments();
	
	/**
	 * Argument list length.
	 * 
	 * @return integer
	 */
	@Override
	int length();
}
