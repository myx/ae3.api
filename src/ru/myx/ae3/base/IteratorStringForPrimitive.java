package ru.myx.ae3.base;

import java.util.Iterator;

/**
 * @author myx
 * 
 */
public final class IteratorStringForPrimitive implements Iterator<String> {
	private final Iterator<BasePrimitive<?>>	iterator;
	
	/**
	 * @param iterator
	 */
	public IteratorStringForPrimitive(final Iterator<BasePrimitive<?>> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public boolean hasNext() {
		return this.iterator.hasNext();
	}
	
	@Override
	public String next() {
		return this.iterator.next().toString();
	}
	
	@Override
	public void remove() {
		// ignore
	}
}
