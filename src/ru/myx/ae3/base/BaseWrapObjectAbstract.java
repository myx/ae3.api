package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * 
 * BaseWrapAbstract is intended for wrapping of non-base java objects.
 * 
 * The reason for it's existence is that it has reference implementation for
 * most of base object's methods.
 * 
 * 
 * Abstract 'wrapped java object' object WITH own properties by design, see
 * BaseWrapAbstract for one with NO own properties.
 * 
 * Only 2 abstract methods: baseValue & baseToString.
 * 
 * @author myx
 * 
 * @param <T>
 */
@ReflectionDisable
public abstract class BaseWrapObjectAbstract<T extends Object> extends BaseWrapAbstract<T> {
	/**
	 * 
	 */
	protected BasePropertiesString	properties	= null;
	
	/**
	 * @param prototype
	 */
	public BaseWrapObjectAbstract(final BaseObject<?> prototype) {
		super( prototype );
	}
	
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	@Override
	public BaseFunction<?> baseCall() {
		return null;
	}
	
	@Override
	public String baseClass() {
		return "Object";
	}
	
	/**
	 * overrides default iterator implementation.
	 */
	@Override
	public void baseClear() {
		this.properties = null;
	}
	
	@Override
	public BaseFunction<?> baseConstruct() {
		return null;
	}
	
	@Override
	public boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert name != null : "Name is NULL";
		assert value != null : "Shouldn't be NULL, use BaseObject.UNDEFINED or BaseObject.NULL instead";
		/**
		 * 1. Call the [[GetOwnProperty]] method of O with property name P.<br>
		 * 2. Get the [[Extensible]] property of O. <br>
		 * 3. If Result(1) is undefined and Result(2) is true, then<br>
		 * a. Create an own property named P of object O whose state is
		 * described by Desc.<br>
		 * b. Return true. <br>
		 * 4. If Result(1) is undefined and Result(2) is false, then<br>
		 * a. If Strict is true, then throw TypeError. <br>
		 * b. Else return false. <br>
		 * 
		 */
		final BaseProperty property = this.baseGetOwnProperty( name );
		final String nameString = name.toString();
		if (property == null) {
			if (this.baseIsExtensible()) {
				this.properties = this.properties == null
						? new BasePropertyHolderString( nameString, value, writable, enumerable, dynamic )
						: this.properties.add( nameString, new BasePropertyHolderString( value,
								writable,
								enumerable,
								dynamic ) );
				return true;
			}
			return false;
		}
		/**
		 * 5. If Result(1) is the same as Desc, then return true.
		 */
		// !!! ignore
		/**
		 * 6. If the [[Dynamic]] attribute of Result(1) is true, then<br>
		 * a. Alter the P property of O to have the state described by Desc. <br>
		 * b. Return true. <br>
		 */
		if (property.isDynamic( nameString )) {
			return property.propertySet( this, nameString, value, writable, enumerable, dynamic );
		}
		/**
		 * 7. If Result(1) or Desc is a PDesc, then<br>
		 * a. If Strict is true, then throw something. <br>
		 * b. Else return false. <br>
		 */
		// !!! skipped
		/**
		 * 8. Get the [[Writeable]] component of Result(1), which is a DDesc.<br>
		 * 9. If Result(8) is false, or if Result(1) and Desc differ in any
		 * component besides [[Value]], then<br>
		 * a. If Strict is true, then throw something. <br>
		 * b. Else return false. <br>
		 */
		if (!property.isWritable( nameString )
				|| writable != property.isWritable( nameString )
				|| enumerable != property.isEnumerable( nameString )
				|| dynamic != property.isDynamic( nameString )) {
			return false;
		}
		/**
		 * 10. Alter the [[Value]] field of the P property of O to be the same
		 * as the [[Value]] field of Desc, thereby altering the property to have
		 * the state described by Desc. <br>
		 */
		return property.propertySet( this, nameString, value, writable, enumerable, dynamic );
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert name != null : "Name is NULL";
		assert value != null : "Shouldn't be NULL, use BaseObject.UNDEFINED or BaseObject.NULL instead";
		/**
		 * 1. Call the [[GetOwnProperty]] method of O with property name P.<br>
		 * 2. Get the [[Extensible]] property of O. <br>
		 * 3. If Result(1) is undefined and Result(2) is true, then<br>
		 * a. Create an own property named P of object O whose state is
		 * described by Desc.<br>
		 * b. Return true. <br>
		 * 4. If Result(1) is undefined and Result(2) is false, then<br>
		 * a. If Strict is true, then throw TypeError. <br>
		 * b. Else return false. <br>
		 * 
		 */
		final BaseProperty property = this.baseGetOwnProperty( name );
		if (property == null) {
			if (this.baseIsExtensible()) {
				this.properties = this.properties == null
						? new BasePropertyHolderString( name, value, writable, enumerable, dynamic )
						: this.properties.add( name,
								new BasePropertyHolderString( value, writable, enumerable, dynamic ) );
				return true;
			}
			return false;
		}
		/**
		 * 5. If Result(1) is the same as Desc, then return true.
		 */
		// !!! ignore
		/**
		 * 6. If the [[Dynamic]] attribute of Result(1) is true, then<br>
		 * a. Alter the P property of O to have the state described by Desc. <br>
		 * b. Return true. <br>
		 */
		if (property.isDynamic( name )) {
			return property.propertySet( this, name, value, writable, enumerable, dynamic );
		}
		/**
		 * 7. If Result(1) or Desc is a PDesc, then<br>
		 * a. If Strict is true, then throw something. <br>
		 * b. Else return false. <br>
		 */
		// !!! skipped
		/**
		 * 8. Get the [[Writeable]] component of Result(1), which is a DDesc.<br>
		 * 9. If Result(8) is false, or if Result(1) and Desc differ in any
		 * component besides [[Value]], then<br>
		 * a. If Strict is true, then throw something. <br>
		 * b. Else return false. <br>
		 */
		if (!property.isWritable( name )
				|| writable != property.isWritable( name )
				|| enumerable != property.isEnumerable( name )
				|| dynamic != property.isDynamic( name )) {
			return false;
		}
		/**
		 * 10. Alter the [[Value]] field of the P property of O to be the same
		 * as the [[Value]] field of Desc, thereby altering the property to have
		 * the state described by Desc. <br>
		 */
		return property.propertySet( this, name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public boolean baseDelete(final String name) {
		final BasePropertiesString properties = this.properties;
		if (properties == null) {
			return true;
		}
		final BaseProperty property = properties.find( name );
		if (property == null) {
			return true;
		}
		if (property.isDynamic( name )) {
			this.properties = properties.delete( name );
			return true;
		}
		return false;
	}
	
	@Override
	public Iterator<String> baseGetOwnAllIterator() {
		return this.properties == null
				? BaseObject.ITERATOR_EMPTY
				: this.properties.iteratorAll();
	}
	
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return this.properties == null
				? BaseObject.ITERATOR_EMPTY
				: this.properties.iteratorEnumerable();
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		final BasePropertiesString properties = this.properties;
		return properties == null
				? null
				: properties.find( name.toString() );
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		final BasePropertiesString properties = this.properties;
		return properties == null
				? null
				: properties.find( name );
	}
	
	@Override
	public final boolean baseHasOwnProperties() {
		final BasePropertiesString properties = this.properties;
		return properties != null && properties.hasEnumerableProperties();
	}
	
	@Override
	public boolean baseIsExtensible() {
		return true;
	}
	
	/**
	 * @return string
	 */
	@Override
	public abstract BasePrimitiveString baseToString();
}
