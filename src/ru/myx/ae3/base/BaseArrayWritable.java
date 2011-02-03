package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 * @param <O>
 *            baseValue() type
 * @param <T>
 *            java array element type
 */
@ReflectionDisable
public interface BaseArrayWritable<O extends Object, T extends Object> extends BaseArray<O, T> {
	
	/**
	 * @param index
	 * @param value
	 * @return
	 */
	boolean baseSet(int index, BaseObject<? extends T> value);
	
	/**
	 * @param index
	 * @param value
	 * @return
	 */
	T set(int index, T value);
}
