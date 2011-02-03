package ru.myx.ae3.base;

import ru.myx.ae3.act.ActFunction;
import ru.myx.ae3.exec.Exec;
import ru.myx.ae3.exec.ExecFunction;
import ru.myx.ae3.exec.ExecProcess;
import ru.myx.ae3.exec.ExecStateCode;
import ru.myx.ae3.reflect.ControlType;
import ru.myx.ae3.reflect.Reflect;
import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * @param <A>
 * 
 * @param <R>
 */
@ReflectionDisable
public abstract class BaseFunctionActAbstract<A, R> extends BaseFunctionAbstract implements ActFunction<A, R>,
		ExecFunction {
	
	private final ControlType<A, ?>	argumentConverter;
	
	private final ControlType<R, ?>	resultConverter;
	
	/**
	 * @param argumentClass
	 * @param resultClass
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected BaseFunctionActAbstract(final Class<? extends A> argumentClass, final Class<? super R> resultClass) {
		super();
		this.argumentConverter = (ControlType<A, ?>) Reflect.getConverter( argumentClass );
		this.resultConverter = (ControlType<R, ?>) Reflect.getConverter( resultClass );
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return Base.forString( this.toString() );
	}
	
	@Override
	public int execAcceptableArgumentCount() {
		return 1;
	}
	
	@Override
	public ExecStateCode execCall(final ExecProcess context) throws Exception {
		final A argument = this.argumentConverter.convertAnyNativeToJava( context.baseGet( 0, BaseObject.UNDEFINED ) );
		context.vmSetResult( this.resultConverter.convertJavaToAnyNative( this.execute( argument ) ) );
		return null;
	}
	
	@Override
	public String[] execFormalParameters() {
		return null;
	}
	
	@Override
	public boolean execHasNamedArguments() {
		return false;
	}
	
	@Override
	public boolean execIsConstant() {
		return false;
	}
	
	@Override
	public int execMinimalArgumentCount() {
		return 0;
	}
	
	@Override
	public Class<R> execResultClassJava() {
		return this.resultConverter.getJavaClass();
	}
	
	@Override
	public BaseObject<?> execScope() {
		return Exec.currentProcess().r5GV;
	}
	
	@Override
	public abstract R execute(final A arg) throws Exception;
	
	@Override
	public ExecFunction functionItself() {
		return this;
	}
}
