package ru.myx.ae3.base;

interface CacheLongImpl {
	CacheLong cacheGetCreate(final long key);
	
	void cacheRemove(long key, CacheLong value);
}
