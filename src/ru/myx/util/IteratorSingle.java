package ru.myx.util;

import java.util.Iterator;

/**
 * @author myx
 * @param <K>
 * 
 */
public final class IteratorSingle<K> implements Iterator<K> {
	private K	key;
	
	/**
	 * @param key
	 */
	public IteratorSingle(final K key) {
		this.key = key;
	}
	
	@Override
	public boolean hasNext() {
		return this.key != null;
	}
	
	@Override
	public K next() {
		try {
			return this.key;
		} finally {
			this.key = null;
		}
	}
	
	@Override
	public void remove() {
		// ignore
	}
}
