package ru.myx.ae3;

import java.lang.reflect.Constructor;

/**
 * Major static APIs derived from this class (partially for the sake of
 * javaDoc's links)
 * 
 * @author myx
 * 
 */
public abstract class AbstractSAPI {
	
	/**
	 * @param <T>
	 * @param className
	 * @return
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	protected final static <T> T createObject(final String className) throws RuntimeException {
		try {
			final ClassLoader loader = Engine.class.getClassLoader();
			final Class<?> boot = loader.loadClass( className );
			// return (T) boot.newInstance();
			final Class<?>[] arguments = null;
			final Constructor<?> constructor = boot.getConstructor( arguments );
			constructor.setAccessible( true );
			return (T) constructor.newInstance( new Object[0] );
		} catch (final Exception e) {
			throw new RuntimeException( "default implementation is missing - please use ae3sys.jar", e );
		}
	}
	
}
