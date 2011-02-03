package ru.myx.ae3.base;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;

final class UtilMapValues extends AbstractCollection<Object> {
	/**
	 * 
	 */
	final BaseObject<?>	baseObject;
	
	/**
	 * @param baseNativeObject
	 */
	UtilMapValues(final BaseObject<?> baseNativeObject) {
		this.baseObject = baseNativeObject;
	}
	
	@Override
	public Iterator<Object> iterator() {
		return new UtilMapValuesIterator( this.baseObject );
	}
	
	@Override
	public int size() {
		return ((Map<?, ?>) this.baseObject).size();
	}
}
