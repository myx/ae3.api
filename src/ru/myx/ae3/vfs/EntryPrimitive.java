package ru.myx.ae3.vfs;

/**
 * @author myx
 * 
 */
public interface EntryPrimitive extends Entry {
	/**
	 * Works when isPrimitive() method returns TRUE.
	 * 
	 * Value is always immediately available, so return type is Object.
	 * 
	 * @return
	 */
	Object getPrimitiveValue();
}
