package ru.myx.ae3.act;

import ru.myx.ae3.exec.ExecProcess;

abstract class AbstractActImpl {
	public abstract int getPeakLoad();
	
	public abstract int getThreadLoad();
	
	public abstract <A, R> void later(//
			ExecProcess ctx,
			ActFunction<A, R> job,
			A arg,
			long delay);
	
	public abstract <A, R> void launchFunction(//
			ExecProcess ctx,
			ActFunction<A, R> function,
			A arg);
	
	public abstract void launchService(//
			ExecProcess ctx,
			ActService service);
	
	public abstract <A, R> R run(//
			ExecProcess process,
			ActFunction<A, R> job,
			A argument) throws Exception;
	
	public abstract <A, R> void whenIdle(//
			ExecProcess ctx,
			ActFunction<A, R> function,
			A arg);
}
