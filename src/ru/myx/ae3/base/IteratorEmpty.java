package ru.myx.ae3.base;

import java.util.Iterator;

final class IteratorEmpty implements Iterator<String> {
	
	@Override
	public boolean hasNext() {
		return false;
	}
	
	@Override
	public String next() {
		return null;
	}
	
	@Override
	public void remove() {
		// ignore
	}
	
}
