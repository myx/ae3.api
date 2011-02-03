package ru.myx.ae3.base;

import java.util.List;

import ru.myx.ae3.exec.ExecFunction;

abstract class AbstractBaseImpl {
	public abstract CacheDoubleImpl createCacheDouble();
	
	public abstract CacheIntegerImpl createCacheInteger();
	
	public abstract CacheLongImpl createCacheLong();
	
	public abstract CacheStringImpl createCacheString();
	
	public abstract BaseFunction<?> javaNativeFunction(ExecFunction object);
	
	public abstract <T extends Number> BaseObject<T> javaNumberToBaseObjectNumber(T object);
	
	public abstract <T> BaseArray<List<T>, T> javaObjectToBaseArray(final List<T> list);
	
	public abstract <T> BaseObject<T> javaObjectToBaseObject(T object);
	
	public abstract BaseAbstractException javaThrowableToBaseObject(Throwable object);
}
