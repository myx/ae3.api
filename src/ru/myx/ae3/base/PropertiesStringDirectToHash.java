/**
 * 
 */
package ru.myx.ae3.base;

import java.util.Iterator;

final class PropertiesStringDirectToHash implements BasePropertiesString {
	private static final long		serialVersionUID	= 2888363574028818360L;
	
	/**
	 * Normally 5
	 */
	// private static final short TRESHOLD = 100;
	// private static final short TRESHOLD = 1;
	private static final short		TRESHOLD			= 5;
	
	private BasePropertyDataString	first;
	
	private BasePropertyDataString	last;
	
	private short					size;
	
	PropertiesStringDirectToHash(final BasePropertyDataString property1,
			final String name2,
			final BasePropertyDataString property2) {
		assert property1 != null : "NULL property";
		assert property1.name != null : "Property should be already assigned!";
		assert property2 != null : "NULL property";
		assert property2.name == null : "Property is already assigned!";
		property2.name = name2;
		this.first = property1;
		property1.prev = null;
		property1.next = property2;
		this.last = property2;
		property2.prev = property1;
		property2.next = null;
		this.size = 2;
	}
	
	@Override
	public BasePropertiesString add(final String name, final BasePropertyDataString property) {
		assert property != null : "NULL property";
		assert property.name == null : "Property is already assigned!";
		assert property.next == null : "Property is already assigned!";
		property.name = name;
		final BasePropertyDataString replaced = this.find( name );
		if (replaced == null) {
			if (++this.size >= PropertiesStringDirectToHash.TRESHOLD) {
				BasePropertiesString map = new PropertiesStringHashMap();
				BasePropertyDataString first = this.first;
				for (;;) {
					final BasePropertyDataString current = first;
					first = current.next;
					current.next = null;
					map = map.add( current.name, current );
					if (first == null) {
						return map.add( name, property );
					}
				}
			}
			this.last.next = property;
			property.prev = this.last;
			this.last = property;
		} else {
			final BasePropertyDataString next = replaced.next;
			if (next != null) {
				next.prev = property;
			}
			final BasePropertyDataString prev = replaced.prev;
			if (prev != null) {
				prev.next = property;
			}
			property.next = next;
			property.prev = prev;
		}
		return this;
	}
	
	@Override
	public BasePropertiesString delete(final String name) {
		final BasePropertyDataString removed = this.find( name );
		if (removed == null) {
			return this;
		}
		this.size--;
		if (removed == this.first) {
			if (removed == this.last) {
				assert false : "Shouldn't be here - should already switch to a single property itself!";
				return null;
			}
			this.first = removed.next;
			this.first.prev = null;
			if (this.first.next == null) {
				return this.first;
			}
			return this;
		}
		if (removed == this.last) {
			this.last = removed.prev;
			this.last.next = null;
			if (this.last.prev == null || this.last == this.first) {
				return this.last;
			}
			return this;
		}
		if (removed.next != null) {
			removed.next.prev = removed.prev;
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
		if (removed.prev != null) {
			removed.prev.next = removed.next;
			removed.prev = null;
		}
		return this;
	}
	
	@Override
	public BasePropertyDataString find(final String name) {
		BasePropertyDataString first = this.first;
		for (;;) {
			/**
			 * CODE2<code>
			</code>
			 */
			final String check = first.name;
			if (check == name || check.equals( name )) {
				return first;
			}
			/**
			 * CODE1<code>
			if (first.name.equals( name )) {
				return first;
			}
			</code>
			 */
			first = first.next;
			if (first == null) {
				return null;
			}
		}
	}
	
	@Override
	public boolean hasEnumerableProperties() {
		BasePropertyDataString first = this.first;
		for (;;) {
			if (first.isEnumerable( first.name )) {
				return true;
			}
			first = first.next;
			if (first == null) {
				return false;
			}
		}
	}
	
	@Override
	public Iterator<String> iteratorAll() {
		final BasePropertyDataString first = this.first;
		return new IteratorPropertiesAll<BasePropertyDataString, String>( first );
	}
	
	/**
	 * all properties in chain
	 */
	@Override
	public Iterator<String> iteratorEnumerable() {
		BasePropertyDataString first = this.first;
		for (;;) {
			if (first.isEnumerable( first.name )) {
				return new IteratorPropertiesEnumerableString( first );
			}
			first = first.next;
			if (first == null) {
				return BaseObject.ITERATOR_EMPTY;
			}
		}
	}
	
	@Override
	public int size() {
		return this.size;
	}
}
