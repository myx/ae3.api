/**
 * 
 */
package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * 4.3.2 Primitive Value
 * <p>
 * A primitive value is a member of one of the types Undefined, Null, Boolean,
 * Number, or String. A primitive value is a datum that is represented directly
 * at the lowest level of the language implementation.
 * <p>
 * 
 * 
 * Все примитивные объекты определены в этом пакеты, они должны наследовать этот
 * класс. Над всеми примитвными типамы определены алгоритмы выполнения операций
 * JavaScript, при столкновении с непримитивными объектами многие операции
 * предварительно явно преобразовывают в примитивный тип.
 * <p>
 * 
 * При этом все примитивные типы не имеют свойств и данный класс определяет
 * реализацию методов связанных с работой со свойствами объекта.
 * <p>
 * 
 * Класс не имеет публичного конструктора, так как создания наследников этого
 * класса вне пакета не предусмотрено.
 * <p>
 * 
 * Также определяется преобразование toString() как:
 * 
 * <pre>
 * public String toString() {
 * 	return this.baseToString().baseValue();
 * }
 * </pre>
 * 
 * @author myx
 * 
 * @param <T>
 */
@ReflectionDisable
abstract class BasePrimitiveAbstract<T> implements BasePrimitive<T> {
	/**
	 * non-public method
	 */
	BasePrimitiveAbstract() {
		//
	}
	
	/**
	 * Not a function
	 */
	@Override
	public final BaseFunction<?> baseCall() {
		return null;
	}
	
	/**
	 * override on every instance
	 */
	@Override
	public abstract String baseClass();
	
	/**
	 * <pre>
	 * final Iterator&lt;String&gt; iterator = this.baseGetOwnIterator();
	 * for (; iterator.hasNext();) {
	 * 	this.baseDelete( iterator.next() );
	 * }
	 * </pre>
	 */
	@Override
	public final void baseClear() {
		// ignore
	}
	
	/**
	 * Not a constructor
	 */
	@Override
	public final BaseFunction<?> baseConstruct() {
		return null;
	}
	
	@Override
	public final boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	/**
	 * No properties
	 */
	@Override
	public final boolean baseDelete(final String name) {
		return false;
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name, final BaseObject<?> defaultValue) {
		final BaseProperty property = Base.getProperty( this, name );
		if (property == null) {
			return defaultValue;
		}
		return property.propertyGet( this, name );
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
		final BaseObject<?> prototype = this.basePrototype();
		return prototype == null
				? BaseObject.ITERATOR_EMPTY
				: prototype.baseGetIterator();
	}
	
	/**
	 * No properties
	 */
	@Override
	public final Iterator<String> baseGetOwnAllIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	/**
	 * No properties
	 */
	@Override
	public final Iterator<String> baseGetOwnIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
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
	 * No properties
	 */
	@Override
	public final boolean baseHasOwnProperties() {
		return false;
	}
	
	/**
	 * No properties at all
	 */
	@Override
	public final boolean baseHasProperties() {
		return false;
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	/**
	 * Sure
	 * 
	 * it is not ok to use this method while knowing that this object is
	 * actually a primitive.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitive() {
		return true;
	}
	
	/**
	 * Read-Only
	 */
	@Override
	public void basePutAll(final BaseObject<?> value) {
		//
	}
	
	/**
	 * Already a primitive
	 * 
	 * it is not ok to use this method while knowing that this object is
	 * actually a number.
	 */
	@Deprecated
	@Override
	public final BasePrimitiveAbstract<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this;
	}
	
	/**
	 * @return string
	 */
	@Override
	public abstract BasePrimitiveString baseToString();
	
	@Override
	public abstract boolean equals(final Object o);
	
	@Override
	public abstract int hashCode();
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @return
	 */
	public final boolean propertySet(final BaseObject<?> instance, final String name, final double value) {
		return this.propertySet( instance, name, value, true, true, true );
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @return
	 */
	public final boolean propertySet(final BaseObject<?> instance, final String name, final long value) {
		return this.propertySet( instance, name, value, true, true, true );
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @return
	 */
	public final boolean propertySet(final BaseObject<?> instance, final String name, final String value) {
		return this.propertySet( instance, name, value, true, true, true );
	}
	
	/**
	 * @param instance
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	/**
	 * Yes - this way only
	 */
	@Override
	public String toString() {
		return this.baseToString().baseValue();
	}
	
}
