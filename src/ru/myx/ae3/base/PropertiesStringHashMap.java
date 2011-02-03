/**
 * 
 */
package ru.myx.ae3.base;

import java.util.HashMap;
import java.util.Iterator;

final class PropertiesStringHashMap extends HashMap<String, BasePropertyDataString> implements BasePropertiesString {
	private static final long		serialVersionUID	= 2888363574028818360L;
	
	private BasePropertyDataString	first;
	
	private BasePropertyDataString	last;
	
	PropertiesStringHashMap() {
		super( 8, 2.0f );
	}
	
	PropertiesStringHashMap(final BasePropertyDataString property1,
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
		final BasePropertyDataString replaced = this.put( name, property );
		if (property.isEnumerable()) {
			if (replaced != null && replaced.isEnumerable()) {
				if (replaced == this.first) {
					if (replaced == this.last) {
						this.first = this.last = property;
					} else {
						replaced.next.prev = property;
						property.next = replaced.next;
						this.first = property;
					}
				} else if (replaced == this.last) {
					replaced.prev.next = property;
					property.prev = replaced.prev;
					this.last = property;
				} else {
					replaced.next.prev = property;
					property.next = replaced.next;
					replaced.prev.next = property;
					property.prev = replaced.prev;
				}
			} else {
				if (this.last == null) {
					this.first = this.last = property;
				} else {
					this.last.next = property;
					property.prev = this.last;
					this.last = property;
				}
			}
		} else {
			if (replaced != null && replaced.isEnumerable()) {
				if (replaced == this.first) {
					if (replaced == this.last) {
						this.first = this.last = null;
					} else {
						replaced.next.prev = null;
						this.first = replaced.next;
					}
				} else if (replaced == this.last) {
					replaced.prev.next = null;
					this.last = replaced.prev;
				} else {
					replaced.prev.next = replaced.next;
					replaced.next.prev = replaced.prev;
					replaced.prev = null;
					/**
					 * don't clear removed.next here used for enumeration:
					 * <p>
					 * Properties of the object being enumerated may be deleted
					 * during enumeration. If a property that has not yet been
					 * visited during enumeration is deleted, then it will not
					 * be visited. If new properties are added to the object
					 * being enumerated during enumeration, the newly added
					 * properties are not guaranteed to be visited in the active
					 * enumeration. Deleted: The mechanics of enumerating the
					 * properties (step 5 in the first algorithm, step 6 in the
					 * second) is implementation dependent. The order of
					 * enumeration is defined by the object.
					 */
				}
			}
		}
		return this;
	}
	
	@Override
	public BasePropertiesString delete(final String name) {
		final BasePropertyDataString replaced = this.remove( name );
		if (replaced == null || !replaced.isEnumerable()) {
			return this;
		}
		if (replaced == this.first) {
			if (replaced == this.last) {
				this.first = this.last = null;
			} else {
				replaced.next.prev = null;
				this.first = replaced.next;
			}
		} else if (replaced == this.last) {
			replaced.prev.next = null;
			this.last = replaced.prev;
		} else {
			replaced.prev.next = replaced.next;
			replaced.next.prev = replaced.prev;
			replaced.prev = null;
			/**
			 * don't clear removed.next here used for enumeration:
			 * <p>
			 * Properties of the object being enumerated may be deleted during
			 * enumeration. If a property that has not yet been visited during
			 * enumeration is deleted, then it will not be visited. If new
			 * properties are added to the object being enumerated during
			 * enumeration, the newly added properties are not guaranteed to be
			 * visited in the active enumeration. Deleted: The mechanics of
			 * enumerating the properties (step 5 in the first algorithm, step 6
			 * in the second) is implementation dependent. The order of
			 * enumeration is defined by the object.
			 */
		}
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
		return this.first != null;
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
		return this.first == null
				? this.keySet().iterator()
				: new IteratorSequenceString( this.iteratorEnumerable(), this.keySet().iterator() );
	}
	
	@Override
	public Iterator<String> iteratorEnumerable() {
		final BasePropertyDataString first = this.first;
		return first == null
				? BaseObject.ITERATOR_EMPTY
				: new IteratorPropertiesAll<BasePropertyDataString, String>( first );
	}
}
