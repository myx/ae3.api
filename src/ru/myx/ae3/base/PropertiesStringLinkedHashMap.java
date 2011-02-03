/**
 * 
 */
package ru.myx.ae3.base;

import java.util.Iterator;
import java.util.LinkedHashMap;

final class PropertiesStringLinkedHashMap extends LinkedHashMap<String, BasePropertyDataString> implements
		BasePropertiesString {
	
	static class IteratorIteratorPropertiesEnumerable implements Iterator<String> {
		private final Iterator<BasePropertyDataString>	i;
		
		IteratorIteratorPropertiesEnumerable(final Iterator<BasePropertyDataString> i) {
			assert i != null;
			this.i = i;
		}
		
		@Override
		public boolean hasNext() {
			if (true) {
				return true;
			}
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String next() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException( "No way, sorry" );
		}
	}
	
	private static final long	serialVersionUID	= 2883053574028818360L;
	
	PropertiesStringLinkedHashMap() {
		super( 8, 2.0f );
	}
	
	PropertiesStringLinkedHashMap(final BasePropertyDataString property1,
			final String name2,
			final BasePropertyDataString property2) {
		super( 8, 0.8f );
		this.add( property1.name, property1 );
		this.add( name2, property2 );
	}
	
	@Override
	public BasePropertiesString add(final String name, final BasePropertyDataString property) {
		assert property != null : "NULL property";
		assert name != null : "NULL property name";
		assert property.name == null || property.name.equals( name ) : "Property is already assigned!";
		assert property.next == null : "Property is already assigned!";
		property.name = name;
		this.put( name, property );
		return this;
	}
	
	@Override
	public BasePropertiesString delete(final String name) {
		this.remove( name );
		if (this.size() == 1) {
			final BasePropertyDataString property = this.values().iterator().next();
			property.next = null;
			return property;
		}
		return this;
	}
	
	@Override
	public BasePropertyDataString find(final String name) {
		return this.get( name );
	}
	
	@Override
	public boolean hasEnumerableProperties() {
		for (final BasePropertyDataString property : this.values()) {
			if (property.isEnumerable( property.name )) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * COMMENTS:
	 * 
	 * 1) put only enumerable properties to a linked chain, use this.keySet()
	 * for 'all'
	 * 
	 * 2) use sequence - to put enumerable elements in the front.
	 * 
	 */
	@Override
	public Iterator<String> iteratorAll() {
		return this.keySet().iterator();
	}
	
	@Override
	public Iterator<String> iteratorEnumerable() {
		// TODO Auto-generated method stub
		return BaseObject.ITERATOR_EMPTY;
	}
}
