package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.common.Value;
import ru.myx.ae3.exec.ExecFunction;
import ru.myx.ae3.reflect.ReflectionDisable;
import ru.myx.util.IteratorSingle;

/**
 * @author myx
 * 
 */
@ReflectionDisable
public abstract class BaseFunctionAbstract extends BaseWrapObjectAbstract<ExecFunction> implements
		BaseFunction<ExecFunction> {
	/**
	 * 
	 */
	public static final BasePrimitiveString	PROTOTYPE_PROPERTY_BASE_NAME	= BasePrimitiveString.PROPERTY_BASE_PROTOTYPE;
	
	/**
	 * 
	 */
	public static final String				PROTOTYPE_PROPERTY_JAVA_NAME;
	
	/**
	 * 
	 */
	public static final int					PROTOTYPE_PROPERTY_LENGTH;
	
	static {
		assert BaseFunctionAbstract.PROTOTYPE_PROPERTY_BASE_NAME == Base.forString( "prototype" ) : "Should return the same instance!";
		PROTOTYPE_PROPERTY_JAVA_NAME = BaseFunctionAbstract.PROTOTYPE_PROPERTY_BASE_NAME.baseValue();
		PROTOTYPE_PROPERTY_LENGTH = "prototype".length();
	}
	
	/**
	 * 
	 */
	protected BaseFunctionAbstract() {
		super( BaseFunction.PROTOTYPE );
	}
	
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	@Override
	public BaseFunction<?> baseCall() {
		return this;
	}
	
	@Override
	public String baseClass() {
		return "function";
	}
	
	@Override
	public BaseFunction<?> baseConstruct() {
		return this;
	}
	
	@Override
	public BaseObject<?> baseConstructPrototype() {
		final BaseFunction<?> constructor = this.baseConstruct();
		if (constructor == null) {
			/**
			 * instead of TypeError
			 */
			return null;
		}
		if (constructor != this) {
			{
				final BaseObject<?> prototype = constructor.baseConstructPrototype();
				if (prototype != null && !(prototype instanceof BasePrimitive)) {
					return prototype;
				}
			}
			{
				final BaseObject<?> prototype = this.baseGet( BaseFunctionAbstract.PROTOTYPE_PROPERTY_BASE_NAME, null );
				if (prototype != null && !(prototype instanceof BasePrimitive)) {
					return prototype;
				}
			}
		}
		{
			final BaseObject<?> prototype = constructor.baseGet( BaseFunctionAbstract.PROTOTYPE_PROPERTY_BASE_NAME,
					null );
			if (prototype != null && !(prototype instanceof BasePrimitive)) {
				return prototype;
			}
		}
		/**
		 * instead of error
		 */
		return null;
	}
	
	@Override
	public boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		/**
		 * in hope of reducing the amount of created objects. not all functions
		 * have their properties accessed, except constructors and ones getting
		 * in error messages.
		 */
		if (this.properties == null) {
			synchronized (this) {
				if (this.properties == null) {
					if (BaseFunctionAbstract.PROTOTYPE_PROPERTY_BASE_NAME == name) {
						this.properties = new BasePropertyHolderString( "prototype", value, writable, enumerable, false );
						return true;
					}
					this.properties = new BasePropertyHolderString( "prototype",
							new BaseNativeObject(),
							true,
							true,
							false );
				}
			}
		}
		return super.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		/**
		 * in hope of reducing the amount of created objects. not all functions
		 * have their properties accessed, except constructors and ones getting
		 * in error messages.
		 */
		if (this.properties == null) {
			synchronized (this) {
				if (this.properties == null) {
					if ("prototype".equals( name )) {
						this.properties = new BasePropertyHolderString( "prototype", value, writable, enumerable, false );
						return true;
					}
					this.properties = new BasePropertyHolderString( "prototype",
							new BaseNativeObject(),
							true,
							true,
							false );
				}
			}
		}
		return super.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public Iterator<String> baseGetOwnAllIterator() {
		/**
		 * in hope of reducing the amount of created objects. not all functions
		 * have their properties accessed, except constructors and ones getting
		 * in error messages.
		 */
		if (this.properties == null) {
			return new IteratorSingle<String>( "prototype" );
		}
		return super.baseGetOwnAllIterator();
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		/**
		 * in hope of reducing the amount of created objects. not all functions
		 * have their properties accessed, except constructors and ones getting
		 * in error messages.
		 */
		if (this.properties == null && BaseFunctionAbstract.PROTOTYPE_PROPERTY_BASE_NAME == name) {
			synchronized (this) {
				if (this.properties == null) {
					this.properties = new BasePropertyHolderString( name.toString(),
							new BaseIdentityObject(),
							true,
							true,
							false );
				}
			}
		}
		return super.baseGetOwnProperty( name );
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		/**
		 * in hope of reducing the amount of created objects. not all functions
		 * have their properties accessed, except constructors and ones getting
		 * in error messages.
		 */
		if (this.properties == null && "prototype".equals( name )) {
			synchronized (this) {
				if (this.properties == null) {
					this.properties = new BasePropertyHolderString( "prototype",
							new BaseNativeObject(),
							true,
							true,
							false );
				}
			}
		}
		return super.baseGetOwnProperty( name );
	}
	
	/**
	 * Returns a boolean value indicating whether Value delegates behaviour to
	 * this object. Of the native ECMAScript objects, only Function objects
	 * implement [[HasInstance]].
	 * 
	 * 15.3.5.3 [[HasInstance]] (V)
	 * 
	 * <pre>
	 * Assume F is a Function object. 
	 * When the [[HasInstance]] internal method of F is called with value V, the following steps are taken:
	 * 1.	If V is not an object, return false. 
	 * 2.	Let O be the result of calling the [[Get]] internal method of F with property name "prototype". 
	 * 3.	If Type(O) is not Object, throw a TypeError exception. 
	 * 4.	Repeat
	 * 		a.	Let V be the value of the [[Prototype]] internal property of V.
	 * 		b.	If V is null, return false. 
	 * 		c.	If O and V refer to the same object, return true.
	 * </pre>
	 * 
	 * Function objects created using Function.prototype.bind have a different
	 * implementation of [[HasInstance]] defined in 15.3.4.5.3.
	 * 
	 * @param value
	 * @return boolean
	 */
	@Override
	public final boolean baseHasInstance(final BaseObject<?> value) {
		final BaseObject<?> prototype = this.baseConstructPrototype();
		if (prototype == null) {
			/**
			 * instead of TypeError
			 */
			return false;
		}
		for (BaseObject<?> check = value.basePrototype(); check != null;) {
			if (check == prototype) {
				return true;
			}
			check = check.basePrototype();
		}
		return false;
	}
	
	@Override
	public final ExecFunction baseValue() {
		return this.functionItself();
	}
	
	@Override
	public boolean equals(final Object o) {
		return o == this || o instanceof Value<?> && this.equals( ((Value<?>) o).baseValue() );
	}
	
	@Override
	public int hashCode() {
		return System.identityHashCode( this );
	}
	
	@Override
	public String toString() {
		return "[Function: " + this.baseValue().getClass().getName() + ']';
	}
	
}
