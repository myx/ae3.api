package ru.myx.ae3.binary;

import java.io.InputStream;

final class NullInputStream extends InputStream {
	
	@Override
	public int available() {
		return 0;
	}
	
	@Override
	public void close() {
		// ignore
	}
	
	@Override
	public void mark(final int readlimit) {
		// ignore
	}
	
	@Override
	public boolean markSupported() {
		return false;
	}
	
	@Override
	public int read() {
		return -1;
	}
	
	@Override
	public int read(final byte[] b) {
		return -1;
	}
	
	@Override
	public int read(final byte[] b, final int off, final int len) {
		return -1;
	}
	
	@Override
	public void reset() {
		// ignore
	}
	
	@Override
	public long skip(final long n) {
		return 0;
	}
	
}
