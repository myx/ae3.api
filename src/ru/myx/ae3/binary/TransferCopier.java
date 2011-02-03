package ru.myx.ae3.binary;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ConcurrentModificationException;

import ru.myx.ae3.common.Value;

/**
 * An object whose duty is to provide any number of equal buffers.
 */
public interface TransferCopier extends Value<TransferCopier> {
	
	/**
	 * NUL COPIER instance - empty copier
	 */
	TransferCopier	NUL_COPIER	= new NullCopier();
	
	/**
	 * Returns true if another object is also a copier and have same length and
	 * same contents.
	 * 
	 * @param another
	 * @return
	 */
	@Override
	boolean equals(final Object another);
	
	/**
	 * Returns a checksum.
	 * 
	 * @return checksum
	 * @throws ConcurrentModificationException
	 *             when underlying data suddenly changed
	 */
	MessageDigest getMessageDigest() throws ConcurrentModificationException;
	
	/**
	 * @return length of copier contents
	 * @throws ConcurrentModificationException
	 *             when underlying data suddenly changed
	 */
	long length() throws ConcurrentModificationException;
	
	/**
	 * Returns a copy of copier contents in a newly created buffer.
	 * 
	 * @return buffer
	 * @throws ConcurrentModificationException
	 *             when underlying data suddenly changed
	 */
	TransferBuffer nextCopy() throws ConcurrentModificationException;
	
	/**
	 * Returns a copy of copier contents in a newly created InputStream.
	 * 
	 * @return buffer
	 * @throws IOException
	 * @throws ConcurrentModificationException
	 *             when underlying data suddenly changed
	 */
	InputStream nextInputStream() throws IOException, ConcurrentModificationException;
	
	/**
	 * Returns a text representation of copier contents. Any binary-to-character
	 * conversions (if any) are performed using system-default locale and
	 * encoding.
	 * 
	 * returns nextCopy().toString();
	 */
	@Override
	String toString();
	
	/**
	 * Returns a checksum.
	 * 
	 * @param digest
	 * @return checksum
	 * @throws ConcurrentModificationException
	 *             when underlying data suddenly changed
	 */
	MessageDigest updateMessageDigest(final MessageDigest digest) throws ConcurrentModificationException;
}
