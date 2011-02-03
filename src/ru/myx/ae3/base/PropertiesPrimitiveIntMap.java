/**
 * 
 */
package ru.myx.ae3.base;

import java.util.Iterator;

final class PropertiesPrimitiveIntMap extends HashMapPrimitiveInt<BasePropertyDataPrimitive> implements
		BasePropertiesPrimitive {
	private static final long			serialVersionUID	= 2888363574028818360L;
	
	private BasePropertyDataPrimitive	first;
	
	private BasePropertyDataPrimitive	last;
	
	PropertiesPrimitiveIntMap(final BasePropertyDataPrimitive first, final BasePropertyDataPrimitive last) {
		super( 16, 4.0f );
		this.first = first;
		this.last = last;
		for (BasePropertyDataPrimitive current = first;;) {
			this.put( System.identityHashCode( current.name ), current );
			current = current.next;
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
		final int code = System.identityHashCode( name );
		final BasePropertyDataPrimitive replaced = this.put( code, property );
		if (replaced == null) {
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
		return this.deleteImpl( this.remove( System.identityHashCode( name ) ) );
	}
	
	@Override
	public BasePropertiesPrimitive delete(final BasePropertyDataPrimitive name) {
		final BasePropertyDataPrimitive replaced = this.remove( System.identityHashCode( name.name ) );
		assert replaced == name;
		return this.deleteImpl( replaced );
	}
	
	private final BasePropertiesPrimitive deleteImpl(final BasePropertyDataPrimitive removed) {
		if (removed == null) {
			return this;
		}
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
		return this.get( System.identityHashCode( name ) );
	}
	
	@Override
	public final BaseObject<?> findAndGet(final BaseObject<?> instance, final BasePrimitive<?> name) {
		final BasePropertyDataPrimitive found = this.get( System.identityHashCode( name ) );
		return found == null
				? null
				: found.propertyGet( instance, name );
	}
	
	@Override
	public final boolean hasEnumerableProperties() {
		BasePropertyDataPrimitive first = this.first;
		for (;;) {
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
		BasePropertyDataPrimitive first = this.first;
		for (;;) {
			if (first.isEnumerable( first.name.toString() )) {
				return new IteratorPropertiesEnumerablePrimitive( first );
			}
			first = first.next;
			if (first == null) {
				return (Iterator<BasePrimitive<?>>) (Iterator<?>) BaseObject.ITERATOR_EMPTY;
			}
		}
	}
}
