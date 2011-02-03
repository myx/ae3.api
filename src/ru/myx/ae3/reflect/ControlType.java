/**
 * 
 */
package ru.myx.ae3.reflect;

import ru.myx.ae3.base.BaseObject;

/**
 * @author myx
 * @param <N>
 *            native
 * @param <J>
 *            java
 * 
 */
public interface ControlType<J extends Object, N extends BaseObject<?>> {
	/**
	 * @param object
	 * @return converted to T
	 */
	public J convertAnyJavaToJava(final Object object);
	
	/**
	 * @param object
	 * @return converted to J
	 */
	public J convertAnyNativeToJava(final BaseObject<?> object);
	
	/**
	 * @param object
	 * @return converted to T
	 */
	public N convertAnyNativeToNative(final BaseObject<?> object);
	
	/**
	 * @param object
	 * @return converted from J
	 */
	public N convertJavaToAnyNative(final J object);
	
	/**
	 * @return
	 */
	public Class<J> getJavaClass();
	
	/**
	 * @return string
	 */
	public String getTypeName();
	
	/**
	 * @param object
	 * @return
	 */
	public boolean isAnyJavaToJavaPerfect(final Object object);
	
	/**
	 * @param object
	 * @return
	 */
	public boolean isAnyJavaToJavaPossible(final Object object);
	
	/**
	 * @param object
	 * @return
	 */
	public boolean isAnyNativeToJavaPerfect(final BaseObject<?> object);
	
	/**
	 * @param object
	 * @return
	 */
	public boolean isAnyNativeToJavaPossible(final BaseObject<?> object);
}
