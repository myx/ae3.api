package ru.myx.ae3.binary;

import java.io.Closeable;
import java.io.OutputStream;

/**
 * A special type of object allowing non-blocking bufferisation between
 * absorbation made using Target or OutputStream and retrieval via Source
 * methods. Any collector implementation have to be safe for at least 2-Thread
 * multithreading when one thread writes to a collector and another thread is
 * reading.
 * 
 * Idea is to collect in memory ot disk dynamically generated data and to
 * collect references on statically available data.
 */
public interface TransferCollector extends Closeable /* extends TransferSource */{
	/**
	 * NUL COLLECTOR instance - dummy collector. It is important to use this one
	 * - since implementations can easily check for equality and perform some
	 * shortcuts while rendering something.
	 */
	TransferCollector	NUL_COLLECTOR	= new NullCollector();
	
	/**
	 * No writes are allowed to a collector after calling this method.
	 */
	@Override
	void close();
	
	/**
	 * Returns an output stream object to fill collector.
	 * 
	 * @return output stream
	 */
	OutputStream getOutputStream();
	
	/**
	 * Returns a transfer target object to fill collector.
	 * 
	 * @return target
	 */
	TransferTarget getTarget();
	
	/**
	 * This method should reset any collector in any state to an clean, empty,
	 * opened and ready to absorb condition.
	 */
	void reset();
	
	/**
	 * Activates the 'chunking' state on this collector, any data written to a
	 * collector will be divided into chunks whose maximal size is specified as
	 * maxChunk argument. minChank argument should be used as a hint to create
	 * some buffers while collecting. Collected data can be read in form of
	 * <i>chunked-encoding </i> (as specified in MIME protocol).
	 * 
	 * @param minChunk
	 * @param maxChunk
	 * @throws IllegalStateException
	 * @throws IllegalArgumentException
	 */
	void startChunking(final int minChunk, final int maxChunk) throws IllegalStateException, IllegalArgumentException;
	
	/**
	 * Forces collector to exit it's 'chunking' state and write an exit sequence
	 * for a chunked-encoded data. This method should be called automatically
	 * when invoking collector's close() method while in 'chunking' state.
	 * 
	 * @throws IllegalStateException
	 */
	void stopChunking() throws IllegalStateException;
	
	/**
	 * Returns whole contents of <b>closed </b> collector as a buffer, collector
	 * is reset and ready to absorb new data after this method is returned.
	 * 
	 * @return buffer
	 */
	TransferBuffer toBuffer();
	
	/**
	 * Returns a factory whose nextSource() method always returns a Buffer
	 * object with the same contents as in this <b>whole </b> Source. After
	 * execution of this method source should be in exhausted state, collector
	 * is reset and ready to absorb new data after this method is returned
	 * 
	 * @return copier
	 */
	TransferCopier toCloneFactory();
}
