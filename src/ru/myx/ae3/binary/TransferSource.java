package ru.myx.ae3.binary;

/**
 * Source is a type for an object whose task is to provide some number of bytes.
 * In general, the exact amount of data in source is unknown thus source can be
 * infinite.
 * <p>
 * There are two ways to perform non-blocking tasks with a source: <br>
 * - isReady(), isComplete(), isExhausted and nextXXX() methods provide generic
 * api. <br>
 * - connectTarget() method allows you to pass all exhaustion/absorbation
 * process to a source, witch MAY be able to do its job effictively.
 * <p>
 * In some cases source can back an immediately available number of bytes whose
 * size is known, then nextClonedBuffer() and isCloneable() methods can unleash
 * their power to gain fast access to a data.
 */
public interface TransferSource {
	
	/**
	 * Connects a target to this source. This method returns immediately. All
	 * data that becomes available in this source will be passed to an
	 * TransferTarget instance passed to this method. Target will be closed when
	 * this source is exhausted. Target will remain connected till this source
	 * is exhausted or target will indicate no more interest via <b>false </b>
	 * result of one of absorbation method calls. Throws IllegalStateException
	 * if this source already has a target connected to it.
	 * 
	 * Connection will be canceled by passing null target at any time.
	 * 
	 * @param target
	 * @return boolean
	 * @throws IllegalStateException
	 */
	boolean connectTarget(final TransferTarget target) throws IllegalStateException;
}
