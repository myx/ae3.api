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
public interface BaseArrayDynamic<O extends Object, T extends Object> extends BaseArrayWritable<O, T>,
		BaseArrayAdvanced<O, T> {
	/**
	 * @param value
	 * @return
	 */
	boolean add(T value);
	
	/**
	 * DEFAULT implementation, for scripts could be redefined.
	 * 
	 * @return
	 */
	BaseObject<?> baseDefaultPop();
	
	/**
	 * DEFAULT implementation, for scripts could be redefined.
	 * 
	 * @param value
	 * @return new length
	 */
	int baseDefaultPush(BaseObject<? extends T> value);
	
	/**
	 * DEFAULT implementation, for scripts could be redefined.
	 * 
	 * @param values
	 * @return new length
	 */
	int baseDefaultPush(BaseObject<? extends T>... values);
	
	/**
	 * DEFAULT implementation, for scripts could be redefined.
	 * 
	 * @return
	 */
	BaseObject<?> baseDefaultShift();
	
	/**
	 * @param start
	 * @param count
	 * @param values
	 * @return
	 */
	BaseArray<?, ?> baseDefaultSplice(int start, int count, BaseObject<? extends T>... values);
	
	/**
	 * DEFAULT implementation, for scripts could be redefined.
	 * 
	 * @param value
	 * @return new length
	 */
	int baseDefaultUnshift(BaseObject<? extends T> value);
	
	/**
	 * DEFAULT implementation, for scripts could be redefined.
	 * 
	 * @param values
	 * @return new length
	 */
	int baseDefaultUnshift(BaseObject<? extends T>... values);
	
	/**
	 * @param index
	 * @return
	 */
	BaseObject<?> baseRemove(int index);
	
	@Override
	boolean baseSet(int index, BaseObject<? extends T> value);
	
	/**
	 * @param value
	 * @return
	 */
	boolean contains(Object value);
	
	/**
	 * @param index
	 * @return
	 */
	T remove(int index);
	
	@Override
	T set(int index, T value);
}
