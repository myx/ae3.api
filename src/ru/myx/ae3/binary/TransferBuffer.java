package ru.myx.ae3.binary;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Some number of <strong>immediately</strong> available bytes whose count is
 * well known. There are number of methods available to read data from buffer.
 */
public interface TransferBuffer {
	/**
	 * NUL BUFFER instance - empty buffer
	 */
	TransferBuffer	NUL_BUFFER	= new NullBuffer();
	
	/**
	 * Releases anything associated with this buffer. Anyway the buffer will be
	 * destroyed in finalize method or when the end of buffer reached.
	 */
	void destroy();
	
	/**
	 * Returns a checksum.
	 * 
	 * @return checksum
	 */
	MessageDigest getMessageDigest();
	
	/**
	 * Tells whether this buffer has any remaining bytes. Should be equivalent
	 * to expression: "remaining() > 0".
	 * 
	 * @return boolean
	 */
	boolean hasRemaining();
	
	/**
	 * Returns true if this buffer is backed by a byte array or can be easily
	 * converted to it without any memory allocations, byte movements and so
	 * on..
	 * 
	 * @return boolean
	 */
	boolean isDirectAbsolutely();
	
	/**
	 * Returns true if this buffer represents a sequence of multiple buffers.
	 * 
	 * @return boolean
	 */
	boolean isSequence();
	
	/**
	 * Returns next byte from buffer.
	 * 
	 * @return int
	 */
	int next();
	
	/**
	 * Returns number of bytes actually written to buffer. Must write
	 * Math.min(remaining, length) bytes!
	 * 
	 * @param buffer
	 * @param offset
	 * @param length
	 * 
	 * @return int
	 */
	int next(final byte[] buffer, final int offset, final int length);
	
	/**
	 * Returns next buffer in sequence when isSequence() returns true.
	 * 
	 * @return buffer
	 */
	TransferBuffer nextSequenceBuffer();
	
	/**
	 * Returns an amount of unread bytes from this buffer.
	 * 
	 * @return int
	 */
	long remaining();
	
	/**
	 * Returns a buffer factory for cloning this original buffer with its
	 * current state. Current buffer becomes empty and useless after this
	 * operation.
	 * 
	 * @return copier
	 */
	TransferCopier toCloneFactory();
	
	/**
	 * Returns array of bytes <b>remaining </b> in buffer, buffer is empty after
	 * this method is returned.
	 * 
	 * @return byte array
	 */
	byte[] toDirectArray();
	
	/**
	 * Returns an InputStream of data remaining in buffer, buffer is empty after
	 * this method is returned.
	 * 
	 * @return input stream
	 */
	InputStream toInputStream();
	
	/**
	 * Transfers buffer contents to a specified nio buffer. attempts to write
	 * this buffer contents to a specified nio buffer the actual number of bytes
	 * written is minimum of number of bytes available to read from this buffer
	 * and number of bytes available to write in given nio buffer. Returns a
	 * transfer buffer holding remaining data or null when all data was
	 * transferred.
	 * 
	 * @param target
	 * @return buffer
	 * @throws IOException
	 */
	TransferBuffer toNioBuffer(final ByteBuffer target) throws IOException;
	
	/**
	 * Returns a string representation of buffer contents, buffer is empty after
	 * this method returns. Any binary-to-character conversions (if any) are
	 * performed using system-default locale and encoding.
	 */
	@Override
	String toString();
	
	/**
	 * Returns a string as if it was constructed via new String(toDirectArray(),
	 * charset), buffer is empty after this method is returned.
	 * 
	 * @param charset
	 * @return string
	 */
	String toString(final Charset charset);
	
	/**
	 * Returns a string as if it was constructed via new String(toDirectArray(),
	 * charset), buffer is empty after this method is returned.
	 * 
	 * @param encoding
	 * @return string
	 * @throws UnsupportedEncodingException
	 */
	String toString(final String encoding) throws UnsupportedEncodingException;
	
	/**
	 * Returns a buffer containing specified slice of this buffer. Current
	 * buffer becomes empty and useless after this operation.
	 * 
	 * @param start
	 * @param end
	 * @return buffer
	 * @throws IllegalArgumentException
	 *             when bounds are out of buffer.
	 */
	TransferBuffer toSubBuffer(final long start, final long end) throws IllegalArgumentException;
	
	/**
	 * Returns a checksum.
	 * 
	 * @param digest
	 * @return checksum
	 */
	MessageDigest updateMessageDigest(final MessageDigest digest);
}
