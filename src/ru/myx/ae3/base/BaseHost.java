package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * Represents built-in host provided objects, that are originally instances of
 * BaseObject, so they should not be wrapped.
 * 
 * BaseHostObject's baseValue() method MUST return 'this'.
 * 
 * @author myx
 * 
 * @param <T>
 */
@ReflectionDisable
public interface BaseHost<T extends BaseHost<?>> extends BaseObject<T> {
	//
}
