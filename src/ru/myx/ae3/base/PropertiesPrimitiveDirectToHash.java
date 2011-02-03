/**
 * 
 */
package ru.myx.ae3.base;

import java.util.Iterator;

final class PropertiesPrimitiveDirectToHash implements BasePropertiesPrimitive {
	private static final long			serialVersionUID	= 2888363574028818360L;
	
	/**
	 * Normally 5
	 */
	// private static final short TRESHOLD = 100;
	// private static final short TRESHOLD = 1;
	private static final short			TRESHOLD			= 8;
	
	private BasePropertyDataPrimitive	first;
	
	private BasePropertyDataPrimitive	last;
	
	private short						size;
	
	PropertiesPrimitiveDirectToHash(final BasePropertyDataPrimitive property1,
			final BasePrimitive<?> name2,
			final BasePropertyDataPrimitive property2) {
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
	public final BasePropertiesPrimitive add(final BasePrimitive<?> name, final BasePropertyDataPrimitive property) {
		assert property != null : "NULL property";
		assert property.name == null : "Property is already assigned!";
		assert property.next == null : "Property is already assigned!";
		property.name = name;
		final BasePropertyDataPrimitive replaced = this.find( name );
		if (replaced == null) {
			if (++this.size >= PropertiesPrimitiveDirectToHash.TRESHOLD) {
				this.last.next = property;
				property.prev = this.last;
				this.last = property;
				return new PropertiesPrimitiveIntMap( this.first, this.last );
				/**
				 * compare <code>
				return new PropertiesPrimitiveHashMap( this.first );
				 * </code>
				 */
			}
			this.last.next = property;
			property.prev = this.last;
			this.last = property;
		} else {
			final BasePropertyDataPrimitive next = replaced.next;
			if (next != null) {
				next.prev = property;
			}
			final BasePropertyDataPrimitive prev = replaced.prev;
			if (prev != null) {
				prev.next = property;
			}
			property.next = next;
			property.prev = prev;
		}
		return this;
	}
	
	@Override
	public final BasePropertiesPrimitive delete(final BasePrimitive<?> name) {
		return this.delete( this.find( name ) );
	}
	
	@Override
	public BasePropertiesPrimitive delete(final BasePropertyDataPrimitive removed) {
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
	public final BasePropertyDataPrimitive find(final BasePrimitive<?> name) {
		for (BasePropertyDataPrimitive first = this.first;;) {
			if (first.name == name) {
				return first;
			}
			first = first.next;
			if (first == null) {
				return null;
			}
		}
	}
	
	@Override
	public final BaseObject<?> findAndGet(final BaseObject<?> instance, final BasePrimitive<?> name) {
		for (BasePropertyDataPrimitive first = this.first;;) {
			if (first.name == name) {
				return first.propertyGet( instance, name );
			}
			first = first.next;
			if (first == null) {
				return null;
			}
		}
	}
	
	@Override
	public final boolean hasEnumerableProperties() {
		for (BasePropertyDataPrimitive first = this.first;;) {
			if (first.isEnumerable( first.name.toString() )) {
				return true;
			}
			first = first.next;
			if (first == null) {
				return false;
			}
		}
	}
	
	@Override
	public final Iterator<BasePrimitive<?>> iteratorAll() {
		final BasePropertyDataPrimitive first = this.first;
		return new IteratorPropertiesAll<BasePropertyDataPrimitive, BasePrimitive<?>>( first );
	}
	
	/**
	 * all properties in chain
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final Iterator<BasePrimitive<?>> iteratorEnumerable() {
		for (BasePropertyDataPrimitive first = this.first;;) {
			if (first.isEnumerable( first.name.toString() )) {
				return new IteratorPropertiesEnumerablePrimitive( first );
			}
			first = first.next;
			if (first == null) {
				return (Iterator<BasePrimitive<?>>) (Iterator<?>) BaseObject.ITERATOR_EMPTY;
			}
		}
	}
	
	@Override
	public final int size() {
		return this.size;
	}
}
