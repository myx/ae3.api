package ru.myx.ae3.base;

import ru.myx.ae3.exec.Exec;
import ru.myx.ae3.exec.ExecFunction;
import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 */
@ReflectionDisable
public abstract class BaseFunctionExecAbstract extends BaseFunctionAbstract implements ExecFunction {
	
	/**
	 * 
	 */
	protected BaseFunctionExecAbstract() {
		super();
	}
	
	@Override
	public String[] execFormalParameters() {
		return null;
	}
	
	@Override
	public BaseObject<?> execScope() {
		return Exec.currentProcess().r5GV;
	}
	
	/**
	 * Returns this
	 */
	@Override
	public ExecFunction functionItself() {
		return this;
	}
	
}
