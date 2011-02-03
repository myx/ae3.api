/*
 * Created on 24.11.2005
 */
package ru.myx.ae3.binary;

import java.io.OutputStream;

final class NullCollector extends OutputStream implements TransferCollector {
	@Override
	public void close() {
		// ignore
	}
	
	@Override
	public void flush() {
		// do nothing
	}
	
	@Override
	public OutputStream getOutputStream() {
		return this;
	}
	
	@Override
	public TransferTarget getTarget() {
		return TransferTarget.NUL_TARGET;
	}
	
	@Override
	public void reset() {
		// ignore
	}
	
	@Override
	public void startChunking(final int minChunk, final int maxChunk) throws IllegalStateException,
			IllegalArgumentException {
		// ignore
	}
	
	@Override
	public void stopChunking() throws IllegalStateException {
		// ignore
	}
	
	@Override
	public TransferBuffer toBuffer() {
		return TransferBuffer.NUL_BUFFER;
	}
	
	@Override
	public TransferCopier toCloneFactory() {
		return TransferCopier.NUL_COPIER;
	}
	
	@Override
	public void write(final byte[] b) {
		// do nothing
	}
	
	@Override
	public void write(final byte[] b, final int off, final int len) {
		// do nothing
	}
	
	@Override
	public void write(final int b) {
		// do nothing
	}
}
