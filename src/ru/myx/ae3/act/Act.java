/**
 * Created on 23.11.2002
 * 
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of file
 * comments go to Window>Preferences>Java>Code Generation.
 */
package ru.myx.ae3.act;

import ru.myx.ae3.AbstractSAPI;
import ru.myx.ae3.exec.ExecProcess;

/**
 * @author barachta
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class Act extends AbstractSAPI {
	private static final AbstractActImpl	ACT_IMPL;
	
	/**
	 * The peak load to be properly handled by a service unit. This number
	 * guaranteed to be the power of 2.<br>
	 * Source: 2 ^ 'ae2.tune.peakload_factor' <br>
	 * Default: 64 <br>
	 * Min: 16 <br>
	 * Max: 512
	 * <p>
	 * Allows static access to some parameters whose values are defaults or
	 * explicitly specified by a user and should be considered if possible.
	 */
	public static final int					PEAK_LOAD;
	
	/**
	 * Peak load divided by parallelism - the load to one thread when thread
	 * count for a module a calculated by parallelism value.
	 */
	public static final int					THREAD_LOAD;
	
	static {
		/**
		 * this block should go last
		 */
		{
			ACT_IMPL = AbstractSAPI.createObject( "ru.myx.ae3.act.ImplementAct" );
			assert Act.ACT_IMPL != null : "Act implementation is not accessible!";
			PEAK_LOAD = Act.ACT_IMPL.getPeakLoad();
			THREAD_LOAD = Act.ACT_IMPL.getThreadLoad();
		}
	}
	
	/**
	 * Schedules job for a delayed execution.
	 * <p>
	 * The main goal to be reached in a scheduler implementation is to minimize
	 * time spent while executing this method, so it is OK some real execution
	 * date lag in order to improve method efficiency.
	 * <p>
	 * 
	 * @param ctx
	 * @param job
	 * @param arg
	 * @param delay
	 * @param <A>
	 * @param <R>
	 */
	public static final <A, R> void later(
			final ExecProcess ctx,
			final ActFunction<A, R> job,
			final A arg,
			final long delay) {
		Act.ACT_IMPL.later( ctx, job, arg, delay );
	}
	
	/**
	 * Schedules job for a delayed execution.
	 * <p>
	 * The main goal to be reached in a scheduler implementation is to minimize
	 * time spent while executing this method, so it is OK some real execution
	 * date lag in order to improve method efficiency.
	 * <p>
	 * 
	 * @param ctx
	 * @param job
	 * @param delay
	 */
	public static final void later(final ExecProcess ctx, final Runnable job, final long delay) {
		Act.ACT_IMPL.later( ctx, ActFunction.FUNCTION_RUN_RUNNABLE, job, delay );
	}
	
	/**
	 * Launches a job for an execution.
	 * <p>
	 * The main goals to be reached in a launcher implementation are to improve
	 * overall system performance and to minimize thread launch delays. In any
	 * situation first goal is more crucial than second one, so there are some
	 * delays allowed in order to gain overall power in some heavy-load
	 * conditions.
	 * <p>
	 * 
	 * Launcher implementation may differ on every instance of system running
	 * and there are two primary reasons to use this method instead of
	 * <code>'new Thread(job).start();'</code>:
	 * <li>some special pooling of processing threads or smart scheduling may
	 * significally increase performance when needed.</li>
	 * <li>special types of thread objects can be used to significally improve
	 * performance of some currently choosen operations.</li>
	 * 
	 * @param ctx
	 * @param function
	 * @param arg
	 * @param <A>
	 * @param <R>
	 */
	public static final <A, R> void launchFunction(final ExecProcess ctx, final ActFunction<A, R> function, final A arg) {
		Act.ACT_IMPL.launchFunction( ctx, function, arg );
	}
	
	/**
	 * Launches a job for an execution.
	 * <p>
	 * The main goals to be reached in a launcher implementation are to improve
	 * overall system performance and to minimize thread launch delays. In any
	 * situation first goal is more crucial than second one, so there are some
	 * delays allowed in order to gain overall power in some heavy-load
	 * conditions.
	 * <p>
	 * 
	 * Launcher implementation may differ on every instance of system running
	 * and there are two primary reasons to use this method instead of
	 * <code>'new Thread(job).start();'</code>:
	 * <li>some special pooling of processing threads or smart scheduling may
	 * significally increase performance when needed.</li>
	 * <li>special types of thread objects can be used to significally improve
	 * performance of some currently choosen operations.</li>
	 * 
	 * @param ctx
	 * @param job
	 */
	public static final void launchRunnable(final ExecProcess ctx, final Runnable job) {
		Act.ACT_IMPL.launchFunction( ctx, ActFunction.FUNCTION_RUN_RUNNABLE, job );
	}
	
	/**
	 * @param ctx
	 * @param service
	 */
	public static final void launchService(final ExecProcess ctx, final ActService service) {
		Act.ACT_IMPL.launchService( ctx, service );
	}
	
	/**
	 * @param <A>
	 * @param <R>
	 * @param process
	 * @param job
	 * @param argument
	 * @return result
	 * @throws Throwable
	 */
	public static final <A, R> R run(final ExecProcess process, final ActFunction<A, R> job, final A argument)
			throws Throwable {
		return Act.ACT_IMPL.run( process, job, argument );
	}
	
	/**
	 * @param process
	 * @param job
	 * @return
	 */
	public static Throwable run(final ExecProcess process, final Runnable job) {
		try {
			Act.ACT_IMPL.run( process, ActFunction.FUNCTION_RUN_RUNNABLE, job );
			return null;
		} catch (final Throwable t) {
			return t;
		}
	}
	
	/**
	 * Launches a job for a low priority execution.
	 * <p>
	 * The main goal to be reached in a launcher implementation is to minimize
	 * time spent while executing this method, so it is OK if real thread launch
	 * is delayed a little in order to improve method efficiency.
	 * <p>
	 * 
	 * Launcher implementation may differ on every instance of system running
	 * and there are two primary reasons to use this method instead of
	 * <code>'new Thread(job).start();'</code>:
	 * <li>some special pooling of processing threads or smart scheduling may
	 * significally increase performance when needed.</li>
	 * <li>special types of thread objects can be used to significally improve
	 * performance of some currently chosen operations.</li>
	 * 
	 * @param ctx
	 * @param function
	 * @param arg
	 * @param <A>
	 * @param <R>
	 */
	public static final <A, R> void whenIdle(final ExecProcess ctx, final ActFunction<A, R> function, final A arg) {
		Act.ACT_IMPL.whenIdle( ctx, function, arg );
	}
	
	/**
	 * Launches a job for a low priority execution.
	 * <p>
	 * The main goal to be reached in a launcher implementation is to minimize
	 * time spent while executing this method, so it is OK if real thread launch
	 * is delayed a little in order to improve method efficiency.
	 * <p>
	 * 
	 * Launcher implementation may differ on every instance of system running
	 * and there are two primary reasons to use this method instead of
	 * <code>'new Thread(job).start();'</code>:
	 * <li>some special pooling of processing threads or smart scheduling may
	 * significally increase performance when needed.</li>
	 * <li>special types of thread objects can be used to significally improve
	 * performance of some currently choosen operations.</li>
	 * 
	 * @param ctx
	 * @param job
	 */
	public static final void whenIdle(final ExecProcess ctx, final Runnable job) {
		Act.ACT_IMPL.whenIdle( ctx, ActFunction.FUNCTION_RUN_RUNNABLE, job );
	}
	
	private Act() {
		// empty
	}
	
}
