/*
 * Created on 19.04.2006
 */
package ru.myx.ae3.flow;

import ru.myx.ae3.act.Act;
import ru.myx.ae3.act.ActFunction;

final class NulSource implements ObjectSource<Object> {
	private static final ActFunction<ObjectTarget<?>, Object>	SOURCE_CLOSER_FUNCTION	= new SourceCloserFunction();
	
	@Override
	public final void connectTarget(final ObjectTarget<Object> target) {
		Act.launchFunction( null, NulSource.SOURCE_CLOSER_FUNCTION, target );
	}
	
	@Override
	public final boolean isExhausted() {
		return true;
	}
	
	@Override
	public final boolean isReady() {
		return false;
	}
	
	@Override
	public final Object next() {
		return null;
	}
}
