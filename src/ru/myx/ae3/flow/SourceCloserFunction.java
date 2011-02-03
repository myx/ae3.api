/*
 * Created on 19.04.2006
 */
package ru.myx.ae3.flow;

import ru.myx.ae3.act.ActFunction;

final class SourceCloserFunction implements ActFunction<ObjectTarget<?>, Object> {
	@Override
	public final Object execute(final ObjectTarget<?> o) {
		o.close();
		return null;
	}
}
