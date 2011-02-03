/**
 * 
 */
package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * Correct property support.
 * 
 * @author myx
 * @param <T>
 * 
 */
@ReflectionDisable
public abstract class BaseAbstract<T> implements BaseObject<T> {
	
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	/**
	 * <pre>
	 * final Iterator&lt;String&gt; iterator = this.baseGetOwnIterator();
	 * for (; iterator.hasNext();) {
	 * 	this.baseDelete( iterator.next() );
	 * }
	 * </pre>
	 */
	@Override
	public void baseClear() {
		final Iterator<String> iterator = this.baseGetOwnIterator();
		for (; iterator.hasNext();) {
			this.baseDelete( iterator.next() );
		}
	}
	
	@Override
	public BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		assert name != null : "NULL java object as a property name";
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = this.basePrototype(); object != null; object = object.basePrototype()) {
			final BaseProperty property = object.baseGetOwnProperty( name );
			if (property != null) {
				return property.propertyGet( this, name );
			}
		}
		return defaultValue;
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name) {
		assert name != null : "NULL java object as a property name";
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = this.basePrototype(); object != null; object = object.basePrototype()) {
			final BaseProperty property = object.baseGetOwnProperty( name );
			if (property != null) {
				return property.propertyGet( this, name );
			}
		}
		return BaseObject.UNDEFINED;
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name, final BaseObject<?> defaultValue) {
		assert name != null : "NULL java object as a property name";
		for (final BaseProperty property = this.baseGetOwnProperty( name ); property != null;) {
			return property.propertyGet( this, name );
		}
		for (BaseObject<?> object = this.basePrototype(); object != null; object = object.basePrototype()) {
			final BaseProperty property = object.baseGetOwnProperty( name );
			if (property != null) {
				return property.propertyGet( this, name );
			}
		}
		return defaultValue;
	}
	
	/**
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final boolean baseGetBoolean(final String name, final boolean defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToBoolean() == BaseObject.TRUE;
	}
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toBoolean() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final double baseGetDouble(final String name, final double defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToNumber().doubleValue();
	}
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toBoolean() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final long baseGetInteger(final String name, final long defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToInteger().longValue();
	}
	
	/**
	 * Never returns NULL. Should return BaseObject.ITERATOR_EMPTY at least.
	 * <p>
	 * Properties of the object being enumerated may be deleted during
	 * enumeration. If a property that has not yet been visited during
	 * enumeration is deleted, then it will not be visited. If new properties
	 * are added to the object being enumerated during enumeration, the newly
	 * added properties are not guaranteed to be visited in the active
	 * enumeration.
	 * <p>
	 * Enumerating the properties of an object includes enumerating properties
	 * of its prototype, and the prototype of the prototype, and so on,
	 * recursively; but a property of a prototype is not enumerated if it is
	 * ―shadowed because some previous object in the prototype chain has a
	 * property with the same name. Deleted: The mechanics of enumerating the
	 * properties (step 5 in the first algorithm, step 6 in the second) is
	 * implementation dependent. The order of enumeration is defined by the
	 * object.
	 * 
	 * @return
	 */
	@Override
	public final Iterator<String> baseGetIterator() {
		final Iterator<String> iterator = this.baseGetOwnIterator();
		assert iterator != null : "NULL iterator: use BaseObject.ITERATOR_EMPTY";
		final BaseObject<?> prototype = this.basePrototype();
		if (prototype == null) {
			return iterator;
		}
		if (iterator == BaseObject.ITERATOR_EMPTY) {
			return prototype.baseGetIterator();
		}
		assert iterator.hasNext() : "Should not be empty, class=" + this.getClass() + "!";
		return new IteratorHierarchy( iterator, prototype );
	}
	
	@Override
	public Iterator<String> baseGetOwnAllIterator() {
		return this.baseGetOwnIterator();
	}
	
	@Override
	public abstract Iterator<String> baseGetOwnIterator();
	
	/**
	 * Alias for: HasProperty(name) ? Get(name).toBoolean() : defaultValue
	 * 
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	@Override
	public final String baseGetString(final String name, final String defaultValue) {
		final BaseObject<?> object = this.baseGet( name );
		assert object != null : "NULL java object";
		return object == BaseObject.UNDEFINED
				? defaultValue
				: object.baseToString().baseValue();
	}
	
	/**
	 * Called by baseHasProperty.
	 * <p>
	 * By default:
	 * 
	 * <pre>
	 * this.baseGetOwnProperty( name ) != null
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	// public boolean baseHasOwnProperty(final String name) {
	// return this.baseGetOwnProperty( name ) != null;
	// }
	
	/**
	 * equivalent to: return this.hasOwnProperties || this.prototype &&
	 * this.prototype.hasProperties
	 */
	@Override
	public final boolean baseHasProperties() {
		final boolean ownProperties = this.baseHasOwnProperties();
		if (ownProperties) {
			return true;
		}
		final BaseObject<?> prototype = this.basePrototype();
		return prototype == null
				? false
				: prototype.baseHasProperties();
	}
	
	@Override
	public final boolean baseIsPrimitive() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitiveBoolean() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitiveInteger() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitiveNumber() {
		return false;
	}
	
	@Override
	public final boolean baseIsPrimitiveString() {
		return false;
	}
	
	@Override
	public void basePutAll(final BaseObject<?> value) {
		if (value == null || value.baseIsPrimitive()) {
			return;
		}
		for (final Iterator<String> iterator = value.baseGetIterator(); iterator.hasNext();) {
			final String key = iterator.next();
			this.baseDefine( key, value.baseGet( key ), true, true, true );
		}
	}
}
