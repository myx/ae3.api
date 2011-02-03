package ru.myx.ae3.know;

import java.io.InputStream;

/**
 * Modified ByteArrayInputStream: integers changed to shorts to fit into 8 byte
 * boundary. Fucking synchronized - removed - why they putting them in such
 * places forcing me to make classes that 'already' invented?
 * 
 * @author myx
 * 
 */
final class GuidInputStream extends InputStream {
	
	/**
	 * An array of bytes that was provided by the creator of the stream.
	 * Elements <code>buf[0]</code> through <code>buf[count-1]</code> are the
	 * only bytes that can ever be read from the stream; element
	 * <code>buf[pos]</code> is the next byte to be read.
	 */
	private final byte[]	buf;
	
	/**
	 * The index of the next character to read from the input stream buffer.
	 * This value should always be nonnegative and not larger than the value of
	 * <code>count</code>. The next byte to be read from the input stream buffer
	 * will be <code>buf[pos]</code>.
	 */
	private short			pos;
	
	/**
	 * The currently marked position in the stream. ByteArrayInputStream objects
	 * are marked at position zero by default when constructed. They may be
	 * marked at another position within the buffer by the <code>mark()</code>
	 * method. The current buffer position is set to this point by the
	 * <code>reset()</code> method.
	 * <p>
	 * If no mark has been set, then the value of mark is the offset passed to
	 * the constructor (or 0 if the offset was not supplied).
	 * 
	 * @since JDK1.1
	 */
	private short			mark	= 0;
	
	/**
	 * The index one greater than the last valid character in the input stream
	 * buffer. This value should always be nonnegative and not larger than the
	 * length of <code>buf</code>. It is one greater than the position of the
	 * last byte within <code>buf</code> that can ever be read from the input
	 * stream buffer.
	 */
	private final short		count;
	
	/**
	 * Creates a <code>ByteArrayInputStream</code> so that it uses
	 * <code>buf</code> as its buffer array. The buffer array is not copied. The
	 * initial value of <code>pos</code> is <code>0</code> and the initial value
	 * of <code>count</code> is the length of <code>buf</code>.
	 * 
	 * @param buf
	 *            the input buffer.
	 */
	GuidInputStream(final byte buf[]) {
		this.buf = buf;
		this.pos = 0;
		this.count = (short) buf.length;
	}
	
	/**
	 * Creates <code>ByteArrayInputStream</code> that uses <code>buf</code> as
	 * its buffer array. The initial value of <code>pos</code> is
	 * <code>offset</code> and the initial value of <code>count</code> is the
	 * minimum of <code>offset+length</code> and <code>buf.length</code>. The
	 * buffer array is not copied. The buffer's mark is set to the specified
	 * offset.
	 * 
	 * @param buf
	 *            the input buffer.
	 * @param offset
	 *            the offset in the buffer of the first byte to read.
	 * @param length
	 *            the maximum number of bytes to read from the buffer.
	 */
	GuidInputStream(final byte buf[], final int offset, final int length) {
		this.buf = buf;
		this.pos = (short) offset;
		this.count = (short) Math.min( offset + length, buf.length );
		this.mark = (short) offset;
	}
	
	/**
	 * Returns the number of bytes that can be read from this input stream
	 * without blocking. The value returned is <code>count&nbsp;- pos</code>,
	 * which is the number of bytes remaining to be read from the input buffer.
	 * 
	 * @return the number of bytes that can be read from the input stream
	 *         without blocking.
	 */
	@Override
	public int available() {
		return this.count - this.pos;
	}
	
	/**
	 * Closing a <tt>ByteArrayInputStream</tt> has no effect. The methods in
	 * this class can be called after the stream has been closed without
	 * generating an <tt>IOException</tt>.
	 * <p>
	 */
	@Override
	public void close() {
		// ignore
	}
	
	/**
	 * Set the current marked position in the stream. ByteArrayInputStream
	 * objects are marked at position zero by default when constructed. They may
	 * be marked at another position within the buffer by this method.
	 * <p>
	 * If no mark has been set, then the value of the mark is the offset passed
	 * to the constructor (or 0 if the offset was not supplied).
	 * 
	 * <p>
	 * Note: The <code>readAheadLimit</code> for this class has no meaning.
	 * 
	 * @since JDK1.1
	 */
	@Override
	public void mark(final int readAheadLimit) {
		this.mark = this.pos;
	}
	
	/**
	 * Tests if this <code>InputStream</code> supports mark/reset. The
	 * <code>markSupported</code> method of <code>ByteArrayInputStream</code>
	 * always returns <code>true</code>.
	 * 
	 * @since JDK1.1
	 */
	@Override
	public boolean markSupported() {
		return true;
	}
	
	/**
	 * Reads the next byte of data from this input stream. The value byte is
	 * returned as an <code>int</code> in the range <code>0</code> to
	 * <code>255</code>. If no byte is available because the end of the stream
	 * has been reached, the value <code>-1</code> is returned.
	 * <p>
	 * This <code>read</code> method cannot block.
	 * 
	 * @return the next byte of data, or <code>-1</code> if the end of the
	 *         stream has been reached.
	 */
	@Override
	public int read() {
		return this.pos < this.count
				? this.buf[this.pos++] & 0xff
				: -1;
	}
	
	/**
	 * Reads up to <code>len</code> bytes of data into an array of bytes from
	 * this input stream. If <code>pos</code> equals <code>count</code>, then
	 * <code>-1</code> is returned to indicate end of file. Otherwise, the
	 * number <code>k</code> of bytes read is equal to the smaller of
	 * <code>len</code> and <code>count-pos</code>. If <code>k</code> is
	 * positive, then bytes <code>buf[pos]</code> through
	 * <code>buf[pos+k-1]</code> are copied into <code>b[off]</code> through
	 * <code>b[off+k-1]</code> in the manner performed by
	 * <code>System.arraycopy</code>. The value <code>k</code> is added into
	 * <code>pos</code> and <code>k</code> is returned.
	 * <p>
	 * This <code>read</code> method cannot block.
	 * 
	 * @param b
	 *            the buffer into which the data is read.
	 * @param off
	 *            the start offset of the data.
	 * @param len
	 *            the maximum number of bytes read.
	 * @return the total number of bytes read into the buffer, or
	 *         <code>-1</code> if there is no more data because the end of the
	 *         stream has been reached.
	 */
	@Override
	public int read(final byte b[], final int off, int len) {
		if (b == null) {
			throw new NullPointerException();
		} else if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (this.pos >= this.count) {
			return -1;
		}
		if (this.pos + len > this.count) {
			len = this.count - this.pos;
		}
		if (len <= 0) {
			return 0;
		}
		System.arraycopy( this.buf, this.pos, b, off, len );
		this.pos += len;
		return len;
	}
	
	/**
	 * Resets the buffer to the marked position. The marked position is 0 unless
	 * another position was marked or an offset was specified in the
	 * constructor.
	 */
	@Override
	public void reset() {
		this.pos = this.mark;
	}
	
	/**
	 * Skips <code>n</code> bytes of input from this input stream. Fewer bytes
	 * might be skipped if the end of the input stream is reached. The actual
	 * number <code>k</code> of bytes to be skipped is equal to the smaller of
	 * <code>n</code> and <code>count-pos</code>. The value <code>k</code> is
	 * added into <code>pos</code> and <code>k</code> is returned.
	 * 
	 * @param n
	 *            the number of bytes to be skipped.
	 * @return the actual number of bytes skipped.
	 */
	@Override
	public long skip(long n) {
		if (this.pos + n > this.count) {
			n = this.count - this.pos;
		}
		if (n < 0) {
			return 0;
		}
		this.pos += n;
		return n;
	}
	
}
