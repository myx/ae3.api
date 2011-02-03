package ru.myx.ae3.base;

import ru.myx.ae3.exec.ExecFunction;
import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 * @param <V>
 *            baseValue
 * 
 *            Both parameters meant to be one parameter, but java generics will
 *            fail actual implementations with 'cannot implement ...' when
 *            actually it is not the case.
 * 
 */
@ReflectionDisable
public interface BaseFunction<V> extends BaseObject<V> {
	/**
	 * 
	 */
	BaseObject<?>	PROTOTYPE			= new BaseNativeObject( BaseObject.PROTOTYPE );
	
	/**
	 * Equivalent to: function(){}
	 */
	BaseFunction<?>	RETURN_UNDEFINED	= new FunctionReturnUndefined();
	
	/**
	 * "prototype" property of function used to create new objects
	 * 
	 * @return
	 */
	BaseObject<?> baseConstructPrototype();
	
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
	boolean baseHasInstance(final BaseObject<?> value);
	
	/**
	 * @return underlying function implementation (java reflection method,
	 *         ActFunction, ExecFunctnio and so on)
	 */
	@Override
	V baseValue();
	
	/**
	 * @return execFunction to execute on virtual machine
	 */
	ExecFunction functionItself();
}
