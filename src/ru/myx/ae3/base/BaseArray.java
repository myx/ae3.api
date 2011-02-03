package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * @param <O>
 *            baseValue() type
 * @param <T>
 *            java array element type
 * 
 */
@ReflectionDisable
public interface BaseArray<O extends Object, T extends Object> extends BaseObject<O> {
	/**
	 * 
	 */
	// public static final BaseObject<?> PROTOTYPE = new
	// BaseNativeArray<Object>( true );
	BaseObject<?>	PROTOTYPE	= new BaseNativeObject();
	
	/**
	 * NULL or ADVANCED
	 * 
	 * @return
	 */
	BaseArrayAdvanced<? extends O, ? extends T> baseArrayAdvanced();
	
	/**
	 * NULL or DYNAMIC
	 * 
	 * @return
	 */
	BaseArrayDynamic<? extends O, ? extends T> baseArrayDynamic();
	
	/**
	 * NULL or WRITABLE
	 * 
	 * @return
	 */
	BaseArrayWritable<? extends O, ? extends T> baseArrayWritable();
	
	/**
	 * Not generic - cause have to return BaseObject.UNDEFINED instead of null's
	 * and so on.
	 * 
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	BaseObject<?> baseGet(final int index, BaseObject<?> defaultValue);
	
	/**
	 * For compatibility with java list - have to unwrap objects here cause
	 * otherwise there is no way to provide customers with proper types.
	 * 
	 * @param i
	 * @return object
	 */
	T get(final int i);
	
	/**
	 * Must be equivalent to this.length() == 0, but implementation has a chance
	 * to be faster.
	 * 
	 * @return
	 */
	boolean isEmpty();
	
	/**
	 * @return
	 */
	int length();
}
