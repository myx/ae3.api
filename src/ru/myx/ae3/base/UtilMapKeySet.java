package ru.myx.ae3.base;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

final class UtilMapKeySet extends AbstractSet<String> {
	/**
	 * 
	 */
	private final BaseObject<?>	baseObject;
	
	/**
	 * @param baseObject
	 */
	UtilMapKeySet(final BaseObject<?> baseObject) {
		assert baseObject instanceof Map : "Have to be an instance of java.util.Map";
		this.baseObject = baseObject;
	}
	
	@Override
	public Iterator<String> iterator() {
		return this.baseObject.baseGetOwnIterator();
	}
	
	@Override
	public int size() {
		return ((Map<?, ?>) this.baseObject).size();
	}
}
