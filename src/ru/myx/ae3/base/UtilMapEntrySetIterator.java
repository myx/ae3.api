package ru.myx.ae3.base;

import java.util.Iterator;
import java.util.Map;

final class UtilMapEntrySetIterator implements Iterator<Map.Entry<String, Object>> {
	/**
	 * 
	 */
	private final BaseObject<?>		baseObject;
	
	private final Iterator<String>	keys;
	
	private String					lastKey	= null;
	
	/**
	 * @param utilMapEntrySet
	 */
	UtilMapEntrySetIterator(final BaseObject<?> baseObject) {
		this.baseObject = baseObject;
		this.keys = baseObject.baseGetOwnIterator();
	}
	
	@Override
	public boolean hasNext() {
		return this.keys.hasNext();
	}
	
	@Override
	public java.util.Map.Entry<String, Object> next() {
		final String key = this.lastKey = this.keys.next();
		final BaseObject<?> baseObject = this.baseObject;
		return new Map.Entry<String, Object>() {
			@Override
			public String getKey() {
				return key;
			}
			
			@Override
			public Object getValue() {
				return baseObject.baseGet( key ).baseValue();
			}
			
			@Override
			public Object setValue(final Object value) {
				try {
					return baseObject.baseGet( key ).baseValue();
				} finally {
					Base.put( baseObject, key, Base.forUnknown( value ) );
				}
			}
			//
		};
	}
	
	@Override
	public void remove() {
		this.baseObject.baseDelete( this.lastKey );
	}
}
