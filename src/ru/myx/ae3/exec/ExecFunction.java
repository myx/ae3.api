/**
 * 
 */
package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;

/**
 * @author myx
 */
public interface ExecFunction {
	
	/**
	 * @return int
	 */
	int execAcceptableArgumentCount();
	
	/**
	 * Arguments should be taken from context, result (if any) should be stored
	 * in RR register.
	 * 
	 * Should not ever return state codes: RETURN, BREAK, CONTINUE
	 * 
	 * @param context
	 * @param arguments
	 * @return object
	 * @throws Exception
	 */
	ExecStateCode execCall(final ExecProcess context) throws Exception;
	
	/**
	 * A possibly empty List containing the identifier Strings of a Function’s
	 * FormalParameterList. Of the standard built-in ECMAScript objects, only
	 * Function objects implement [[FormalParameterList]].
	 * 
	 * @return List of Strings
	 */
	String[] execFormalParameters();
	
	/**
	 * @return boolean
	 */
	boolean execHasNamedArguments();
	
	/**
	 * @return boolean
	 */
	boolean execIsConstant();
	
	/**
	 * @return int
	 */
	int execMinimalArgumentCount();
	
	/**
	 * If not sure, return Object.class
	 * 
	 * @return
	 */
	Class<? extends Object> execResultClassJava();
	
	/**
	 * A scope chain that defines the environment in which a Function object is
	 * executed.
	 * 
	 * FROM ECMAScript specification:
	 * 
	 * A lexical environment that defines the environment in which a Function
	 * object is executed. Of the standard built-in ECMAScript objects, only
	 * Function objects implement [[Scope]].
	 * 
	 * @return Lexical Environment
	 */
	BaseObject<?> execScope();
}
