package ru.myx.ae3.binary;

import java.io.Closeable;
import java.nio.ByteBuffer;

import ru.myx.ae3.act.ActFunction;
import ru.myx.ae3.exec.ExecProcess;

/**
 * An object for non-blocking binary data consumption. It is assumed that if a
 * target is backing some blocking operation it should enqueue absorbation
 * requests and process them somehow in background.
 */
public interface TransferTarget extends Closeable {
	/**
	 * NUL TARGET instance - dummy target. It is important to use this one -
	 * since implementations can easily check for equality and perform some
	 * shortcuts while rendering something.
	 */
	TransferTarget	NUL_TARGET	= new NullTarget();
	
	/**
	 * immediately
	 */
	void abort();
	
	/**
	 * @param i
	 * @return boolean
	 */
	boolean absorb(int i);
	
	/**
	 * @param array
	 * @param off
	 * @param len
	 * @return boolean
	 */
	boolean absorbArray(byte[] array, int off, int len);
	
	/**
	 * @param buffer
	 * @return boolean
	 */
	boolean absorbBuffer(TransferBuffer buffer);
	
	/**
	 * @param buffer
	 * @return boolean
	 */
	boolean absorbNio(ByteBuffer buffer);
	
	/**
	 * in order*
	 * 
	 * FIXME: doc * Enqueues a 'close' action on completion. This action have to
	 * be launched when currently absorbed data is sent to underlying medium or
	 * when transfer is interrupted. When no data available in buffers launch
	 * should happen immediately. In any cases the action will be launched in
	 * other thread, not to block current process.
	 */
	@Override
	void close();
	
	/**
	 * in order*
	 * 
	 * FIXME: doc * Enqueues an action to launch on completion. This action have
	 * to be launched when currently absorbed data is sent to underlying medium
	 * or when transfer is interrupted. When no data available in buffers launch
	 * should happen immediately. In any cases the action will be launched in
	 * other thread, not to block current process.
	 * 
	 * When no delayed buffering used in this target, acts like: <code>
	 * public boolean enqueueAction( Act.Context ctx, Act.Function function, Object argument) { 
	 * Act.launch(ctx, function, argument); 
	 * 	return true; 
	 * }
	 * </code>
	 * 
	 * @param ctx
	 * @param function
	 * @param argument
	 * @param <A>
	 * @param <R>
	 * @return boolean
	 */
	<A, R> boolean enqueueAction(ExecProcess ctx, ActFunction<A, R> function, A argument);
	
	/**
	 * Forces all data enqueued to a target to be flushed further.
	 */
	void force();
}
