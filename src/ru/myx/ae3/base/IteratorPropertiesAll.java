package ru.myx.ae3.base;

import java.util.Iterator;

final class IteratorPropertiesAll<P extends BasePropertyData<P, K>, K> implements Iterator<K> {
	private P	property;
	
	IteratorPropertiesAll(final P first) {
		this.property = first;
	}
	
	@Override
	public boolean hasNext() {
		return this.property != null;
	}
	
	@Override
	public K next() {
		assert this.property.name != null : "Property name is NULL";
		try {
			return this.property.name;
		} finally {
			this.property = this.property.next;
		}
	}
	
	@Override
	public void remove() {
		// ignore
	}
}
