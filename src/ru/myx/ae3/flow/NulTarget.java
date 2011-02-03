/**
 * 
 */
package ru.myx.ae3.flow;

final class NulTarget implements ObjectTarget<Object> {
	@Override
	public final boolean absorb(final Object object) {
		return true;
	}
	
	@Override
	public final Class<?> accepts() {
		return Object.class;
	}
	
	@Override
	public final void close() {
		// ignore
	}
}
