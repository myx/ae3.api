package ru.myx.ae3.base;

interface CacheIntegerImpl {
	CacheInteger cacheGetCreate(final int key);
	
	void cacheRemove(int key, CacheInteger value);
}
