/*
 * Created on 11.12.2005
 */
package ru.myx.ae3.act;

final class FunctionRunRunnable implements ActFunction<Runnable, Void> {
	@Override
	public final Void execute(final Runnable arg) {
		arg.run();
		return null;
	}
	
	@Override
	public final String toString() {
		return "RUNNABLE";
	}
}
