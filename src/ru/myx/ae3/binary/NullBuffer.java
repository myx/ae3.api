/*
 * Created on 24.11.2005
 */
package ru.myx.ae3.binary;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import ru.myx.ae3.Engine;

final class NullBuffer implements TransferBuffer {
	private static final byte[]			DUMMY_ARRAY		= new byte[0];
	
	private static final InputStream	DUMMY_STREAM	= new NullInputStream();
	
	@Override
	public final void destroy() {
		// ignore
	}
	
	@Override
	public MessageDigest getMessageDigest() {
		return Engine.getMessageDigestInstance();
	}
	
	@Override
	public final boolean hasRemaining() {
		return false;
	}
	
	@Override
	public final boolean isDirectAbsolutely() {
		return false;
	}
	
	@Override
	public final boolean isSequence() {
		return false;
	}
	
	@Override
	public final int next() {
		return -1;
	}
	
	@Override
	public final int next(final byte[] buffer, final int offset, final int length) {
		return 0;
	}
	
	@Override
	public final TransferBuffer nextSequenceBuffer() {
		throw new UnsupportedOperationException( "Not a sequence!" );
	}
	
	@Override
	public final long remaining() {
		return 0;
	}
	
	@Override
	public final TransferCopier toCloneFactory() {
		return TransferCopier.NUL_COPIER;
	}
	
	@Override
	public final byte[] toDirectArray() {
		return NullBuffer.DUMMY_ARRAY;
	}
	
	@Override
	public final InputStream toInputStream() {
		return NullBuffer.DUMMY_STREAM;
	}
	
	@Override
	public final TransferBuffer toNioBuffer(final ByteBuffer target) {
		return null;
	}
	
	@Override
	public final String toString() {
		return "";
	}
	
	@Override
	public final String toString(final Charset charset) {
		return "";
	}
	
	@Override
	public final String toString(final String encoding) {
		return "";
	}
	
	@Override
	public final TransferBuffer toSubBuffer(final long start, final long end) {
		if (start != 0 || end != 0) {
			throw new IllegalArgumentException( "Indexes are out of bounds: start="
					+ start
					+ ", end="
					+ end
					+ ", length=0" );
		}
		return this;
	}
	
	@Override
	public MessageDigest updateMessageDigest(final MessageDigest digest) {
		return digest;
	}
}
