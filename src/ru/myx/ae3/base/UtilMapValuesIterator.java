package ru.myx.ae3.base;

import java.util.Iterator;

final class UtilMapValuesIterator implements Iterator<Object> {
	/**
	 * 
	 */
	private final BaseObject<?>		baseObject;
	
	private final Iterator<String>	keys;
	
	private String					lastKey	= null;
	
	/**
	 * @param baseObject
	 */
	UtilMapValuesIterator(final BaseObject<?> baseObject) {
		this.baseObject = baseObject;
		this.keys = baseObject.baseGetOwnIterator();
	}
	
	@Override
	public boolean hasNext() {
		return this.keys.hasNext();
	}
	
	@Override
	public Object next() {
		final String key = this.lastKey = this.keys.next();
		return this.baseObject.baseGet( key ).baseValue();
	}
	
	@Override
	public void remove() {
		this.baseObject.baseDelete( this.lastKey );
	}
}
