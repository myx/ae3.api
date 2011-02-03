/*
 * Created on 12.03.2006
 */
package ru.myx.ae3.binary;

import java.io.InputStream;
import java.security.MessageDigest;

import ru.myx.ae3.Engine;

/**
 * @author myx
 * 
 */
public final class WrapCopier implements TransferCopier {
	private final byte[]	bytes;
	
	private final int		offset;
	
	private final int		length;
	
	/**
	 * @param bytes
	 */
	public WrapCopier(final byte[] bytes) {
		this.bytes = bytes;
		this.offset = 0;
		this.length = bytes.length;
	}
	
	/**
	 * @param bytes
	 * @param offset
	 * @param length
	 */
	public WrapCopier(final byte[] bytes, final int offset, final int length) {
		this.bytes = bytes;
		this.offset = offset;
		this.length = length;
	}
	
	@Override
	public TransferCopier baseValue() {
		return this;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof TransferCopier) || ((TransferCopier) obj).length() != this.length) {
			return false;
		}
		final TransferBuffer buffer = ((TransferCopier) obj).nextCopy();
		for (int i = this.offset, l = this.length; l > 0; l--, i++) {
			if ((this.bytes[i] & 0xFF) != buffer.next()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public MessageDigest getMessageDigest() {
		final MessageDigest digest = Engine.getMessageDigestInstance();
		digest.update( this.bytes, this.offset, this.length );
		return digest;
	}
	
	@Override
	public final long length() {
		return this.length;
	}
	
	@Override
	public final TransferBuffer nextCopy() {
		return new WrapBuffer( this.bytes, this.offset, this.length );
	}
	
	@Override
	public InputStream nextInputStream() {
		return new WrapInputStream( this.bytes, this.offset, this.length );
	}
	
	/**
	 * returns nextCopy().toString();
	 */
	@Override
	public String toString() {
		return this.nextCopy().toString();
	}
	
	@Override
	public MessageDigest updateMessageDigest(final MessageDigest digest) {
		digest.update( this.bytes, this.offset, this.length );
		return digest;
	}
}
