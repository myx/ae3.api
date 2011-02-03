package ru.myx.ae3.base;

import java.util.Iterator;

final class IteratorPropertiesEnumerableString implements Iterator<String> {
	private BasePropertyDataString	property;
	
	IteratorPropertiesEnumerableString(final BasePropertyDataString first) {
		this.property = first;
	}
	
	@Override
	public boolean hasNext() {
		return this.property != null;
	}
	
	@Override
	public String next() {
		assert this.property.name != null : "Property name is NULL";
		try {
			return this.property.name;
		} finally {
			for (;;) {
				this.property = this.property.next;
				if (this.property == null || this.property.isEnumerable( this.property.name )) {
					break;
				}
			}
		}
	}
	
	@Override
	public void remove() {
		// ignore
	}
}
