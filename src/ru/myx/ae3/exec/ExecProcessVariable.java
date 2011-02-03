package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.common.Holder;

/**
 * @author myx
 * 
 * @param <T>
 */
public final class ExecProcessVariable<T extends BaseObject<?>> implements Holder<T> {
	private final int		stackIndex;
	
	private final boolean	inheritable;
	
	ExecProcessVariable(final int stackIndex, final boolean inheritable) {
		assert stackIndex >= 0 : "Out of reserved stack space while registering process variable";
		this.stackIndex = stackIndex;
		this.inheritable = inheritable;
	}
	
	@Override
	public T baseValue() {
		return this.baseValue( Exec.currentProcess() );
	}
	
	/**
	 * @param process
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T baseValue(final ExecProcess process) {
		assert process != null : "Process must not be NULL";
		final T value = (T) process.fldStack[this.stackIndex];
		if (value != null || !this.inheritable) {
			return value;
		}
		final ExecProcess parent = process.parent;
		return parent == null
				? null
				: this.baseValue( parent );
	}
	
	@Override
	public boolean execCanSet() {
		return true;
	}
	
	@Override
	public boolean execCompareAndSet(final T compare, final T value) {
		final ExecProcess process = Exec.currentProcess();
		assert process != null : "Process must not be NULL";
		synchronized (this) {
			if (process.fldStack[this.stackIndex] == compare) {
				process.fldStack[this.stackIndex] = value;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param process
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T execGetAndSet(final ExecProcess process, final T value) {
		assert process != null : "Process must not be NULL";
		try {
			return (T) process.fldStack[this.stackIndex];
		} finally {
			process.fldStack[this.stackIndex] = value;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T execGetAndSet(final T value) {
		final ExecProcess process = Exec.currentProcess();
		assert process != null : "Process must not be NULL";
		try {
			return (T) process.fldStack[this.stackIndex];
		} finally {
			process.fldStack[this.stackIndex] = value;
		}
	}
	
	/**
	 * @param process
	 * @param value
	 */
	public void execSet(final ExecProcess process, final T value) {
		assert process != null : "Process must not be NULL";
		process.fldStack[this.stackIndex] = value;
	}
	
	@Override
	public void execSet(final T value) {
		final ExecProcess process = Exec.currentProcess();
		assert process != null : "Process must not be NULL";
		process.fldStack[this.stackIndex] = value;
	}
}
