/**
 * 
 */
package ru.myx.ae3.base;

import java.util.IdentityHashMap;
import java.util.Iterator;

final class PropertiesPrimitiveHashMap extends IdentityHashMap<BasePrimitive<?>, BasePropertyDataPrimitive> implements
		BasePropertiesPrimitive {
	private static final long			serialVersionUID	= 2888363574028818360L;
	
	private BasePropertyDataPrimitive	first;
	
	private BasePropertyDataPrimitive	last;
	
	PropertiesPrimitiveHashMap(final BasePropertyDataPrimitive first) {
		super( 16 );
		for (BasePropertyDataPrimitive current = first;;) {
			final BasePropertyDataPrimitive next = current.next;
			current.next = null;
			this.add( current.name, current );
			current = next;
			if (current == null) {
				break;
			}
		}
	}
	
	@Override
	public final BasePropertiesPrimitive add(final BasePrimitive<?> name, final BasePropertyDataPrimitive property) {
		assert property != null : "NULL property";
		assert name != null : "NULL property name";
		assert property.name == null || property.name.equals( name ) : "Property is already assigned!";
		assert property.next == null : "Property is already assigned!";
		property.name = name;
		final BasePropertyDataPrimitive replaced = this.put( name, property );
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
	public final BasePropertiesPrimitive delete(final BasePrimitive<?> name) {
		return this.deleteImpl( this.remove( name ) );
	}
	
	@Override
	public BasePropertiesPrimitive delete(final BasePropertyDataPrimitive name) {
		final BasePropertyDataPrimitive replaced = this.remove( name.name );
		assert replaced == name;
		return this.deleteImpl( replaced );
	}
	
	private final BasePropertiesPrimitive deleteImpl(final BasePropertyDataPrimitive replaced) {
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
			final BasePropertyDataPrimitive property = this.values().iterator().next();
			property.next = null;
			return property;
		}
		return this;
	}
	
	@Override
	public final BasePropertyDataPrimitive find(final BasePrimitive<?> name) {
		return this.get( name );
	}
	
	@Override
	public final BaseObject<?> findAndGet(final BaseObject<?> instance, final BasePrimitive<?> name) {
		final BasePropertyDataPrimitive found = this.get( name );
		return found == null
				? null
				: found.propertyGet( instance, name );
	}
	
	@Override
	public final boolean hasEnumerableProperties() {
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
	public final Iterator<BasePrimitive<?>> iteratorAll() {
		return this.first == null
				? this.keySet().iterator()
				: new IteratorSequencePrimitive( this.iteratorEnumerable(), this.keySet().iterator() );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public final Iterator<BasePrimitive<?>> iteratorEnumerable() {
		final BasePropertyDataPrimitive first = this.first;
		if (first == null) {
			return (Iterator<BasePrimitive<?>>) (Iterator<?>) BaseObject.ITERATOR_EMPTY;
		}
		return new IteratorPropertiesAll<BasePropertyDataPrimitive, BasePrimitive<?>>( first );
	}
}
