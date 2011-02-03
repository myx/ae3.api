package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitiveString;

/**
 * 
 * Lexical context scope interface
 * 
 * @author myx
 * 
 */
public interface ExecScope {
	
	/**
	 * Create a new mutable binding in an environment record. The String value N
	 * is the text of the bound name. If the optional Boolean argument D is true
	 * the binding is may be subsequently deleted.
	 * 
	 * @param name
	 *            property name
	 * @param value
	 *            initial value
	 * @param dynamic
	 */
	void contextCreateMutableBinding(final BasePrimitiveString name, final BaseObject<?> value, final boolean dynamic);
	
	/**
	 * Create a new mutable binding in an environment record. The String value N
	 * is the text of the bound name. If the optional Boolean argument D is true
	 * the binding is may be subsequently deleted.
	 * 
	 * @param name
	 *            property name
	 * @param value
	 *            initial value
	 * @param dynamic
	 */
	void contextCreateMutableBinding(final String name, final BaseObject<?> value, final boolean dynamic);
	
	/**
	 * DeleteBinding(N)
	 * 
	 * Delete a binding from an environment record. The String value N is the
	 * text of the bound name If a binding for N exists, remove the binding and
	 * return true. If the binding exists but cannot be removed return false. If
	 * the binding does not exist return true.
	 * 
	 * @param name
	 * @return
	 */
	boolean contextDeleteBinding(final String name);
	
	/**
	 * GetBindingValue(N,S)
	 * 
	 * Returns the value of an already existing binding from an environment
	 * record. The String value N is the text of the bound name. S is used to
	 * identify strict mode references. If S is true and the binding does not
	 * exist or is uninitialized throw a ReferenceError exception.
	 * 
	 * @param name
	 * @param strict
	 * @return
	 */
	BaseObject<?> contextGetBindingValue(final BasePrimitiveString name, final boolean strict);
	
	/**
	 * GetBindingValue(N,S)
	 * 
	 * Returns the value of an already existing binding from an environment
	 * record. The String value N is the text of the bound name. S is used to
	 * identify strict mode references. If S is true and the binding does not
	 * exist or is uninitialized throw a ReferenceError exception.
	 * 
	 * @param name
	 * @param strict
	 * @return
	 */
	BaseObject<?> contextGetBindingValue(final String name, final boolean strict);
	
	/**
	 * Determine if an environment record has a binding for an identifier.
	 * Return true if it does and false if it does not. The String value N is
	 * the text of the identifier.
	 * 
	 * @param name
	 * @return
	 */
	boolean contextHasBinding(final String name);
	
	/**
	 * ImplicitThisValue()
	 * 
	 * Returns the value to use as the this value on calls to function objects
	 * that are obtained as binding values from this environment record.
	 * 
	 * @return
	 */
	BaseObject<?> contextImplicitThisValue();
	
	/**
	 * SetMutableBinding(N, V, S)
	 * 
	 * Set the value of an already existing mutable binding in an environment
	 * record. The String value N is the text of the bound name. V is the value
	 * for the binding and may be a value of any ECMAScript language type. S is
	 * a Boolean flag. If S is true and the binding cannot be set throw a
	 * TypeError exception. S is used to identify strict mode references.
	 * 
	 * @param name
	 * @param value
	 * @param strict
	 * @return
	 */
	boolean contextSetMutableBinding(final BasePrimitiveString name, final BaseObject<?> value, boolean strict);
	
	/**
	 * SetMutableBinding(N, V, S)
	 * 
	 * Set the value of an already existing mutable binding in an environment
	 * record. The String value N is the text of the bound name. V is the value
	 * for the binding and may be a value of any ECMAScript language type. S is
	 * a Boolean flag. If S is true and the binding cannot be set throw a
	 * TypeError exception. S is used to identify strict mode references.
	 * 
	 * @param name
	 * @param value
	 * @param strict
	 * @return
	 */
	boolean contextSetMutableBinding(final String name, final BaseObject<?> value, boolean strict);
}
