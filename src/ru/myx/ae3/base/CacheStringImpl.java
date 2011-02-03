package ru.myx.ae3.base;

interface CacheStringImpl {
	CacheString cacheGetCreate(String key);
	
	void cachePutInternal(String key, CacheString value);
	
	void cacheRemove(String key, CacheString value);
}
