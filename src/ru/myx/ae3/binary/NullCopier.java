/*
 * Created on 24.11.2005
 */
package ru.myx.ae3.binary;

import java.io.InputStream;
import java.security.MessageDigest;

import ru.myx.ae3.Engine;

final class NullCopier implements TransferCopier {
	
	@Override
	public TransferCopier baseValue() {
		return this;
	}
	
	@Override
	public boolean equals(final Object obj) {
		return obj != null && obj instanceof TransferCopier && ((TransferCopier) obj).length() == 0;
	}
	
	@Override
	public MessageDigest getMessageDigest() {
		return Engine.getMessageDigestInstance();
	}
	
	@Override
	public final long length() {
		return 0;
	}
	
	@Override
	public TransferBuffer nextCopy() {
		return TransferBuffer.NUL_BUFFER;
	}
	
	@Override
	public InputStream nextInputStream() {
		return this.nextCopy().toInputStream();
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	@Override
	public MessageDigest updateMessageDigest(final MessageDigest digest) {
		return digest;
	}
}
