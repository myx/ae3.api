package ru.myx.ae3.base;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

final class UtilMapEntrySet extends AbstractSet<Map.Entry<String, Object>> {
	/**
	 * 
	 */
	private final BaseObject<?>	baseObject;
	
	/**
	 * @param baseObject
	 */
	UtilMapEntrySet(final BaseObject<?> baseObject) {
		assert baseObject instanceof Map : "Have to be an instance of java.util.Map";
		this.baseObject = baseObject;
	}
	
	@Override
	public Iterator<Map.Entry<String, Object>> iterator() {
		return new UtilMapEntrySetIterator( this.baseObject );
	}
	
	@Override
	public int size() {
		return ((Map<?, ?>) this.baseObject).size();
	}
}
