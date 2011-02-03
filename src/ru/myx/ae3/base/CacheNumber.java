/**
 * 
 */
package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 */
@ReflectionDisable
abstract class CacheNumber extends Number implements Comparable<Number> {
	
	private static final long	serialVersionUID	= 3385532496929251221L;
	
	abstract BasePrimitiveNumber getPrimitive();
}
