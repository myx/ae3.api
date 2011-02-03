package ru.myx.ae3.base;

import java.util.Iterator;

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
public interface BaseArrayAdvanced<O extends Object, T extends Object> extends BaseArray<O, T> {
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	Iterator<BaseObject<?>>	ITERATOR_EMPTY	= (Iterator<BaseObject<?>>) (Iterator<?>) BaseObject.ITERATOR_EMPTY;
	
	/**
	 * @param value
	 * @return
	 */
	boolean baseContains(BaseObject<?> value);
	
	/**
	 * Returns a one-level deep copy of a portion of an array.
	 * 
	 * Check BaseArrayHelper for generic static implementation.
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	BaseArrayAdvanced<?, ?> baseDefaultSlice(final int start, final int end);
	
	/**
	 * @return
	 */
	Iterator<? extends BaseObject<?>> baseIterator();
}
