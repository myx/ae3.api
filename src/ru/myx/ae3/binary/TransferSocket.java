package ru.myx.ae3.binary;

import java.io.Closeable;

/**
 * Socket is a pair of source and target which both make you able to read and
 * write data from/to a channel.
 */
public interface TransferSocket extends Closeable {
	/**
     * 
     */
	void abort();
	
	/**
     * 
     */
	@Override
	void close();
	
	/**
	 * Returns a stringual representation of a peer's address identifying a peer
	 * within current source.
	 * 
	 * @return string
	 */
	String getAddress();
	
	/**
	 * Returns a stringual representation of a peer's address identifying a peer
	 * within a whole lots of different sources by mixing <i>transport-type-name
	 * </i> and <i>transport-local-identity </i>.
	 * 
	 * @return string
	 */
	String getIdentity();
	
	/**
	 * Returns a source instance to read data from this socket.
	 * 
	 * @return source
	 */
	TransferSource getSource();
	
	/**
	 * Returns a target instance to write data to this socket.
	 * 
	 * @return target
	 */
	TransferTarget getTarget();
	
	/**
	 * @return description
	 */
	TransferDescription getTransferDescription();
	
	/**
	 * @return boolean
	 */
	boolean isOpen();
	
	/**
	 * @param description
	 * @return description
	 */
	TransferDescription setTransferDescription(final TransferDescription description);
}
