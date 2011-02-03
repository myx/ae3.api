package ru.myx.ae3.common;

/**
 * @author myx
 * 
 * @param <T>
 */
public interface KeyFactory<T> {
	/**
	 * 
	 */
	public static final KeyFactory<String>	FACTORY_STRING	= new KeyFactoryString();
	
	/**
	 * @param key
	 * @return
	 */
	public T keyJavaBoolean(final boolean key);
	
	/**
	 * @param key
	 * @return
	 */
	public T keyJavaDouble(final double key);
	
	/**
	 * @param key
	 * @return
	 */
	public T keyJavaFloat(final float key);
	
	/**
	 * @param key
	 * @return
	 */
	public T keyJavaInt(final int key);
	
	/**
	 * @param key
	 * @return
	 */
	public T keyJavaLong(final long key);
	
	/**
	 * @param key
	 * @return
	 */
	public T keyJavaString(final String key);
}
