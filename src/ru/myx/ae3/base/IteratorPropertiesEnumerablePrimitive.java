package ru.myx.ae3.base;

import java.util.Iterator;

final class IteratorPropertiesEnumerablePrimitive implements Iterator<BasePrimitive<?>> {
	private BasePropertyDataPrimitive	property;
	
	IteratorPropertiesEnumerablePrimitive(final BasePropertyDataPrimitive first) {
		this.property = first;
	}
	
	@Override
	public boolean hasNext() {
		return this.property != null;
	}
	
	@Override
	public BasePrimitive<?> next() {
		assert this.property.name != null : "Property name is NULL";
		try {
			return this.property.name;
		} finally {
			for (;;) {
				this.property = this.property.next;
				if (this.property == null || this.property.isEnumerable( this.property.name.toString() )) {
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
