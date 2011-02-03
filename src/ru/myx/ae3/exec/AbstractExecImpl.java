package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;

abstract class AbstractExecImpl {
	public abstract ExecProcess createProcess(//
			ExecProcess parent,
			String title);
	
	public abstract <T extends BaseObject<?>> ExecProcessVariable<T> createProcessVariable(//
			String key,
			boolean inheritable);
	
	public abstract ExecProcess currentProcess();
	
	public abstract InheritableThreadLocal<ExecProcess> getAtLeastHolder();
	
	public abstract ExecProcess getRootProcess();
}
