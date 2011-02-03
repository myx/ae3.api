package ru.myx.ae3.base;

import ru.myx.ae3.exec.ExecProcess;
import ru.myx.ae3.exec.ExecStateCode;

final class FunctionReturnUndefined extends BaseFunctionExecAbstract {
	
	private static final BasePrimitiveString	TO_STRING	= Base.forString( "function(){}" );
	
	@Override
	public BasePrimitiveString baseToString() {
		return FunctionReturnUndefined.TO_STRING;
	}
	
	@Override
	public int execAcceptableArgumentCount() {
		return 0;
	}
	
	@Override
	public ExecStateCode execCall(final ExecProcess context) {
		context.vmSetResult( BaseObject.UNDEFINED );
		return null;
	}
	
	@Override
	public boolean execHasNamedArguments() {
		return false;
	}
	
	@Override
	public boolean execIsConstant() {
		return true;
	}
	
	@Override
	public int execMinimalArgumentCount() {
		return 0;
	}
	
	@Override
	public Class<? extends Object> execResultClassJava() {
		return Object.class;
	}
	
	@Override
	public String toString() {
		return "[function ReturnUndefined]";
	}
	
}
