package ru.myx.ae3.base;

import java.util.Iterator;

/**
 * @author myx
 * 
 */
interface BasePropertiesPrimitive {
	/**
	 * @param name
	 * @param property
	 * @return same or new props
	 */
	BasePropertiesPrimitive add(final BasePrimitive<?> name, final BasePropertyDataPrimitive property);
	
	/**
	 * @param name
	 * @return same or new props
	 */
	BasePropertiesPrimitive delete(final BasePrimitive<?> name);
	
	/**
	 * @param name
	 * @return same or new props
	 */
	BasePropertiesPrimitive delete(final BasePropertyDataPrimitive name);
	
	BasePropertyDataPrimitive find(final BasePrimitive<?> name);
	
	BaseObject<?> findAndGet(final BaseObject<?> instance, final BasePrimitive<?> name);
	
	boolean hasEnumerableProperties();
	
	Iterator<BasePrimitive<?>> iteratorAll();
	
	Iterator<BasePrimitive<?>> iteratorEnumerable();
	
	/**
	 * not really used - just for fun / because we can
	 * 
	 * @return
	 */
	int size();
}
