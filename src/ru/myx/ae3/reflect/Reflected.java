package ru.myx.ae3.reflect;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.exec.ExecProcess;

/**
 * @author myx
 * 
 */
public interface Reflected {
	/**
	 * Constructor key
	 */
	public static final BasePrimitiveString	KEY_CONSTRUCTOR_BASE	= Base.forString( "<new>" );
	
	/**
	 * Constructor key
	 */
	public static final String				KEY_CONSTRUCTOR_JAVA	= Reflected.KEY_CONSTRUCTOR_BASE.toString();
	
	/**
	 * @param ctx
	 * @param name
	 * @param instance
	 * @return reference
	 */
	// public boolean evalFieldExists(final ExecProcess ctx, final String name,
	// final BaseObject<?> instance);
	
	/**
	 * @param ctx
	 * @param instance
	 * @return reference
	 */
	// public Iterator<String> evalFieldIterator(final ExecProcess ctx, final
	// BaseObject<?> instance);
	
	/**
	 * @param ctx
	 * @param name
	 * @param instance
	 * @return reference
	 */
	// public boolean evalFieldWritable(final ExecProcess ctx, final String
	// name, final BaseObject<?> instance);
	
	/**
	 * @return identity or null
	 */
	public String getGuid();
	
	/**
	 * @param ctx
	 * @param instance
	 * @param name
	 * @return reference
	 */
	public Object reflectReadJava(final ExecProcess ctx, final Object instance, final String name);
	
	/**
	 * @param ctx
	 * @param instance
	 * @param name
	 * @return reference
	 */
	public BaseObject<?> reflectReadNative(final ExecProcess ctx, final BaseObject<?> instance, final String name);
	
	/**
	 * This method can return NULL to indicate that write failed
	 * 
	 * @param ctx
	 * @param instance
	 * @param name
	 * @param value
	 * @return true on success, false otherwise, exception on unexpected error
	 */
	public boolean reflectWriteJava(final ExecProcess ctx, final Object instance, final String name, final Object value);
	
	/**
	 * This method can return NULL to indicate that write failed
	 * 
	 * @param ctx
	 * @param instance
	 * @param name
	 * @param value
	 * @return true on success, false otherwise, exception on unexpected error
	 */
	public boolean reflectWriteNative(
			final ExecProcess ctx,
			final BaseObject<?> instance,
			final String name,
			final BaseObject<?> value);
}
