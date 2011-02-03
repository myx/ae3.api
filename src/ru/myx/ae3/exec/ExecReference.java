/**
 * 
 */
package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.common.Holder;

/**
 * @author myx
 * 
 */
public interface ExecReference extends Holder<BaseObject<?>> {
	
	/**
	 * @return value
	 */
	@Override
	BaseObject<?> baseValue();
	
	/**
	 * @return boolean
	 */
	@Override
	boolean execCanSet();
	
	/**
	 * @param value
	 */
	@Override
	void execSet(final BaseObject<?> value);
}
