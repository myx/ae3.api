package ru.myx.ae3.base;

interface CacheDoubleImpl {
	CacheNumber cacheGetCreate(final double key);
	
	void cachePutInternal(double key, CacheNumber value);
	
	void cacheRemove(double key, CacheNumber value);
}
