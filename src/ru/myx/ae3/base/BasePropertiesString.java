package ru.myx.ae3.base;

import java.util.Iterator;

/**
 * @author myx
 * 
 */
interface BasePropertiesString {
	/**
	 * @param name
	 * @param property
	 * @return same or new props
	 */
	BasePropertiesString add(final String name, final BasePropertyDataString property);
	
	/**
	 * @param name
	 * @return same or new props
	 */
	BasePropertiesString delete(final String name);
	
	/**
	 * @param name
	 * @return property
	 */
	BasePropertyDataString find(final String name);
	
	boolean hasEnumerableProperties();
	
	Iterator<String> iteratorAll();
	
	Iterator<String> iteratorEnumerable();
	
	/**
	 * not really used - just for fun / because we can
	 * 
	 * @return
	 */
	int size();
}
