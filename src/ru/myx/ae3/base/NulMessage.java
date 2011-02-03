/*
 * Created on 22.03.2006
 */
package ru.myx.ae3.base;

import java.io.File;
import java.security.MessageDigest;
import java.util.Iterator;

import ru.myx.ae3.Engine;
import ru.myx.ae3.binary.TransferBuffer;
import ru.myx.ae3.binary.TransferSource;
import ru.myx.ae3.reflect.ReflectionDisable;

@ReflectionDisable
final class NulMessage extends BaseHostAbstract<NulMessage> implements BaseMessage<NulMessage> {
	@Override
	public boolean baseDelete(final String name) {
		return false;
	}
	
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		return null;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		return null;
	}
	
	@Override
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	/**
	 * Return FALSE
	 */
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.FALSE;
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return Base.forString( "[NULL MESSAGE]" );
	}
	
	@Override
	public final BaseNativeObject getAttributes() {
		return null;
	}
	
	@Override
	public final TransferBuffer getBinary() {
		return null;
	}
	
	@Override
	public final long getDate() {
		return 0;
	}
	
	@Override
	public final File getFile() {
		return null;
	}
	
	@Override
	public MessageDigest getMessageDigest() {
		final MessageDigest digest = Engine.getMessageDigestInstance();
		return digest;
	}
	
	@Override
	public final Object getObject() {
		return null;
	}
	
	@Override
	public final Class<?> getObjectClass() {
		return null;
	}
	
	@Override
	public final String getOwner() {
		return "[NONE]";
	}
	
	@Override
	public long getProcess() {
		return 0;
	}
	
	@Override
	public final String getProtocolName() {
		return "NONE";
	}
	
	@Override
	public final String getProtocolVariant() {
		return "NONE/0.9";
	}
	
	@Override
	public final BaseMessage<?>[] getSequence() {
		return null;
	}
	
	public final TransferSource getSource() {
		return null;
	}
	
	@Override
	public final String getSourceAddress() {
		return "NUL";
	}
	
	@Override
	public final String getSourceAddressExact() {
		return null;
	}
	
	@Override
	public final String getSubject() {
		return null;
	}
	
	@Override
	public final String getTarget() {
		return "NUL";
	}
	
	@Override
	public final String getTargetExact() {
		return null;
	}
	
	@Override
	public final String getText() {
		return null;
	}
	
	@Override
	public final String getTitle() {
		return "[NONE]";
	}
	
	public final boolean hasAttributes() {
		return false;
	}
	
	@Override
	public final boolean isBinary() {
		return false;
	}
	
	@Override
	public final boolean isEmpty() {
		return true;
	}
	
	@Override
	public final boolean isFile() {
		return false;
	}
	
	@Override
	public final boolean isObject() {
		return false;
	}
	
	@Override
	public final boolean isSequence() {
		return false;
	}
	
	@Override
	public final boolean isSource() {
		return false;
	}
	
	@Override
	public final boolean isTextual() {
		return false;
	}
	
	@Override
	public final BaseMessage<?> toBinaryMessage() {
		return this;
	}
	
	@Override
	public final BaseMessage<?> toCharacterMessage() {
		return this;
	}
}
