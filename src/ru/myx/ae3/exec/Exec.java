/**
 * 
 */
package ru.myx.ae3.exec;

import ru.myx.ae3.AbstractSAPI;
import ru.myx.ae3.base.BaseObject;

/**
 * @author myx
 * 
 */
public class Exec extends AbstractSAPI {
	private static final AbstractExecImpl					EXEC_IMPL;
	
	public static final InheritableThreadLocal<ExecProcess>	AT_LEAST;
	
	static {
		/**
		 * this block should go last
		 */
		{
			EXEC_IMPL = AbstractSAPI.createObject( "ru.myx.ae3.exec.ImplementExec" );
			assert Exec.EXEC_IMPL != null : "Exec implementation is not accessible!";
			AT_LEAST = Exec.EXEC_IMPL.getAtLeastHolder();
		}
	}
	
	/**
	 * for variables new empty BaseNaiveObject will be created with prototype
	 * based on current context's variables
	 * 
	 * @param parent
	 *            [prototype] when NULL - parent will be used for prototype,
	 *            when parent is NULL then current process will be used.
	 * @param title
	 * @return
	 */
	public static final ExecProcess createProcess(final ExecProcess parent, final String title) {
		return Exec.EXEC_IMPL.createProcess( parent, title );
	}
	
	/**
	 * for variables new empty BaseNaiveObject will be created with prototype
	 * based on current context's variables
	 * 
	 * @param parent
	 *            [prototype] when NULL - parent will be used for prototype,
	 *            when parent is NULL then current process will be used.
	 * @param title
	 * @param arguments
	 * @return
	 */
	public static final ExecProcess createProcess(
			final ExecProcess parent,
			final String title,
			final ExecArguments<?> arguments) {
		final ExecProcess result = Exec.EXEC_IMPL.createProcess( parent, title );
		result.fldArguments = arguments;
		return result;
	}
	
	/**
	 * Creates process variable (either inheritable or local). These variables
	 * use thread context to bind so it goes in java way (as contrary to js way,
	 * where on function call scope stack is bind to function declaration
	 * context).
	 * 
	 * That's why it is a good place to allocate such things as 'server',
	 * 'request', 'session', etc or your own context objects with this things
	 * accessible as fields.
	 * 
	 * Yes - this variables have to be BaseObjects explicitly, this provides
	 * fastest, wrapper-less access. Use BaseHostAbstract if you don't care.
	 * 
	 * @param <T>
	 * @param key
	 * @param inheritable
	 * @return
	 */
	public static final <T extends BaseObject<?>> ExecProcessVariable<T> createProcessVariable(
			final String key,
			final boolean inheritable) {
		return Exec.EXEC_IMPL.createProcessVariable( key, inheritable );
	}
	
	/**
	 * @return context
	 */
	public static final ExecProcess currentProcess() {
		return Exec.EXEC_IMPL.currentProcess();
	}
	
	/**
	 * @return root process
	 */
	public static final ExecProcess getRootProcess() {
		return Exec.EXEC_IMPL.getRootProcess();
	}
}
