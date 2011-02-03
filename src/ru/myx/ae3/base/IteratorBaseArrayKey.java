package ru.myx.ae3.base;

import java.util.Iterator;

final class IteratorBaseArrayKey implements Iterator<String> {
	private final BaseArray<?, ?>	array;
	
	private int						index;
	
	IteratorBaseArrayKey(final BaseArray<?, ?> array) {
		this.array = array;
		this.index = 0;
	}
	
	@Override
	public boolean hasNext() {
		return this.index < this.array.length();
	}
	
	@Override
	public String next() {
		return String.valueOf( this.index++ );
	}
	
	@Override
	public void remove() {
		// ignore
	}
}
