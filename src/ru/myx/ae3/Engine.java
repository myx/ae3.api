/**
 * 
 */
package ru.myx.ae3;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import ru.myx.ae3.base.BaseNativeObject;
import ru.myx.ae3.base.BaseObject;
import ru.myx.s4.StorageAPI;

/**
 * 
 * @author myx
 */
public final class Engine extends AbstractSAPI {
	/**
	 * 
	 */
	public static final Charset					CHARSET_ASCII;
	
	/**
	 * 
	 */
	public static final Charset					CHARSET_DEFAULT;
	
	/**
	 * 
	 */
	public static final Charset					CHARSET_ISO_8859_1;
	
	/**
	 * 
	 */
	public static final Charset					CHARSET_UTF16;
	
	/**
	 * 
	 */
	public static final Charset					CHARSET_UTF8;
	
	/**
	 * A Date object whose getTime() method returns current time. setTime()
	 * calls just ignored.
	 */
	public static final Date					CURRENT_TIME;
	
	private static final MessageDigest			DIGEST;
	
	/**
	 * this is internalized canonical name for US-ASCII character set
	 */
	public static final String					ENCODING_ASCII;
	
	/**
	 * 
	 */
	public static final String					ENCODING_DEFAULT;
	
	/**
	 * this is internalized canonical name for ISO-8859-1 character set
	 */
	public static final String					ENCODING_ISO_8859_1;
	
	/**
	 * this is internalized canonical name for UTF-16 character set
	 */
	public static final String					ENCODING_UTF16;
	
	/**
	 * this is internalized canonical name for UTF-8 character set
	 */
	public static final String					ENCODING_UTF8;
	
	/**
	 * "devel".equals(GROUP_NAME);
	 */
	public static final boolean					GROUP_DEVEL;
	
	/**
	 * "live".equals(GROUP_NAME);
	 */
	public static final boolean					GROUP_LIVE;
	
	/**
	 * Group type name, i.e. live
	 */
	public static final String					GROUP_NAME;
	
	/**
	 * "test".equals(GROUP_NAME);
	 */
	public static final boolean					GROUP_TEST;
	
	static final AbstractEngineImpl.GuidFactory	GUID_GENERATOR;
	
	/**
	 * toString method will always return new GUID
	 */
	public static final Object					GUID_PRODUCER;
	
	/**
	 * Instance host name, i.e. live.agava.myx.ru
	 */
	public static final String					HOST_NAME;
	
	/**
	 * The minimal number of asynchronous execution units to gain performance.
	 * This number guaranteed to be the power of 2.<br>
	 * Source: number of available processors <br>
	 * Allows static access to some parameters whose values are defaults or
	 * explicitly specified by a user and should be considered if possible.
	 */
	public static final int						PARALLELISM;
	
	/**
	 * 
	 */
	public static final File					PATH_CACHE;
	
	/**
	 * 
	 */
	public static final File					PATH_LOGS;
	
	/**
	 * 
	 */
	public static final File					PATH_PRIVATE;
	
	/**
	 * 
	 */
	public static final File					PATH_PROTECTED;
	
	/**
	 * 
	 */
	public static final File					PATH_PUBLIC;
	
	/**
	 * 
	 */
	public static final File					PATH_SHARED;
	
	/**
	 * 
	 */
	public static final File					PATH_TEMP;
	
	/**
	 * 
	 */
	public static final File					PATH_USER_DIR;
	
	/**
	 * 
	 */
	public static final File					PATH_USER_HOME;
	
	static final AbstractEngineImpl.RandFactory	RAND_GENERATOR;
	
	/**
	 * 
	 */
	public static final long					STARTED;
	
	static StorageAPI							STORAGE_PRIVATE	= null;
	
	static final AbstractEngineImpl.TimeFactory	TIME_RETRIEVER;
	
	/**
	 * GMT time zone
	 */
	public static final TimeZone				TIMEZONE_GMT	= TimeZone.getTimeZone( "GMT" );
	
	/**
	 * Version string
	 */
	public static final int						VERSION_NUMBER;
	
	/**
	 * Version string
	 */
	public static final String					VERSION_STRING;
	
	static {
		STARTED = System.currentTimeMillis();
		{
			final PrintStream output = new StdOutput( Engine.STARTED, new FileOutputStream( FileDescriptor.out ), true );
			System.setOut( output );
		}
		
		CHARSET_DEFAULT = Charset.defaultCharset();
		ENCODING_DEFAULT = Engine.CHARSET_DEFAULT.name();
		
		ENCODING_ASCII = "US-ASCII".intern();
		ENCODING_UTF8 = "UTF-8".intern();
		ENCODING_ISO_8859_1 = "ISO-8859-1".intern();
		ENCODING_UTF16 = "UTF-16".intern();
		
		CHARSET_ASCII = Charset.forName( Engine.ENCODING_ASCII );
		CHARSET_UTF8 = Charset.forName( Engine.ENCODING_UTF8 );
		CHARSET_ISO_8859_1 = Charset.forName( Engine.ENCODING_ISO_8859_1 );
		CHARSET_UTF16 = Charset.forName( Engine.ENCODING_UTF16 );
		
		GROUP_NAME = System.getProperty( "ru.myx.ae3.properties.groupname", "live" ).toLowerCase();
		GROUP_LIVE = "live".equals( Engine.GROUP_NAME );
		GROUP_TEST = "test".equals( Engine.GROUP_NAME );
		GROUP_DEVEL = "devel".equals( Engine.GROUP_NAME );
		
		PARALLELISM = Runtime.getRuntime().availableProcessors();
		
		try {
			DIGEST = (MessageDigest) MessageDigest.getInstance( "MD5" ).clone();
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException( "MD5 support is required!" );
		} catch (final CloneNotSupportedException e) {
			throw new RuntimeException( "MD5 support for cloning is required!" );
		}
		
		HOST_NAME = Engine.getHostName();
		
		{
			PATH_USER_DIR = new File( System.getProperty( "user.dir" ) );
		}
		{
			PATH_USER_HOME = new File( System.getProperty( "user.home" ) );
		}
		
		{
			final String pathDefault = Engine.PATH_USER_DIR.getAbsolutePath();
			PATH_PUBLIC = new File( System.getProperty( "ru.myx.ae3.properties.path.public", pathDefault ) )
					.getAbsoluteFile();
		}
		{
			final String pathDefault = new File( new File( Engine.PATH_USER_HOME, ".ae3" ), "protected" )
					.getAbsolutePath();
			PATH_PROTECTED = new File( System.getProperty( "ru.myx.ae3.properties.path.protected", pathDefault ) )
					.getAbsoluteFile();
		}
		{
			final String pathDefault = new File( new File( Engine.PATH_USER_HOME, ".ae3" ), "private" )
					.getAbsolutePath();
			PATH_PRIVATE = new File( System.getProperty( "ru.myx.ae3.properties.path.private", pathDefault ) )
					.getAbsoluteFile();
		}
		{
			final String pathDefault = new File( new File( Engine.PATH_USER_HOME, ".ae3" ), "shared" )
					.getAbsolutePath();
			PATH_SHARED = new File( System.getProperty( "ru.myx.ae3.properties.path.shared", pathDefault ) )
					.getAbsoluteFile();
		}
		{
			final String pathDefault = new File( Engine.PATH_PRIVATE, "cache" ).getAbsolutePath();
			PATH_CACHE = new File( System.getProperty( "ru.myx.ae3.properties.path.cache", pathDefault ) )
					.getAbsoluteFile();
		}
		{
			final String pathDefault = new File( Engine.PATH_PRIVATE, "logs" ).getAbsolutePath();
			PATH_LOGS = new File( System.getProperty( "ru.myx.ae3.properties.path.logs", pathDefault ) )
					.getAbsoluteFile();
		}
		{
			final String pathDefault = new File( Engine.PATH_PRIVATE, "temp" ).getAbsolutePath();
			PATH_TEMP = new File( System.getProperty( "ru.myx.ae3.properties.path.temp", pathDefault ) )
					.getAbsoluteFile();
		}
		
		Engine.PATH_PROTECTED.mkdirs();
		Engine.PATH_PRIVATE.mkdirs();
		Engine.PATH_SHARED.mkdirs();
		Engine.PATH_CACHE.mkdirs();
		Engine.PATH_LOGS.mkdirs();
		Engine.PATH_TEMP.mkdirs();
		
		System.setProperty( "java.io.tmpdir", Engine.PATH_TEMP.getAbsolutePath() );
		
		/**
		 * this block should go last
		 */
		{
			final AbstractEngineImpl impl = AbstractSAPI.createObject( "ru.myx.ae3.ImplementEngine" );
			GUID_GENERATOR = impl.GUID_GENERATOR;
			GUID_PRODUCER = Engine.GUID_GENERATOR.producer();
			RAND_GENERATOR = impl.RAND_GENERATOR;
			TIME_RETRIEVER = impl.TIME_RETRIEVER;
			CURRENT_TIME = Engine.TIME_RETRIEVER.date();
			
			Engine.GUID_GENERATOR.start();
			Engine.TIME_RETRIEVER.start();
			Engine.RAND_GENERATOR.start();
			
			VERSION_NUMBER = 678;
			
			final String version = impl.getVersion();
			VERSION_STRING = version == null
					? "678"
					: "678 (" + version + ')';
			
			impl.start();
		}
	}
	
	/**
	 * GUID is a sequence of characters used for a distinct identification of
	 * any object in a globally accessible, concurrently modifiable space for a
	 * long term life.
	 * <p>
	 * 
	 * General contract is: <br>
	 * <li>GUID length MUST NOT to be less than 16 characters and more than 32
	 * characters</li>
	 * <li>Every character in GUID sequence is either a digit, small english
	 * letter or an underline symbol '_'.</li>
	 * <li>There SHOULD NOT ever be two guid generations with the same guid as a
	 * result</li>
	 * 
	 * @return newly generated guid
	 */
	public static final String createGuid() {
		return Engine.GUID_GENERATOR.guid();
	}
	
	/**
	 * @param file
	 * @param environment
	 * @param folder
	 * @return
	 */
	public static final Process createProcess(final String file, final BaseNativeObject environment, final File folder) {
		return Engine.createProcess( file, (BaseObject<?>) environment, folder );
	}
	
	/**
	 * @param file
	 * @param environment
	 * @param folder
	 * @return
	 */
	public static final Process createProcess(final String file, final BaseObject<?> environment, final File folder) {
		final List<Exception> exceptions = new ArrayList<Exception>( 5 );
		{
			/**
			 * Try unix 1 way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "see", file );
			if (environment != null) {
				for (final Iterator<String> iterator = environment.baseGetIterator(); iterator.hasNext();) {
					final String key = iterator.next();
					builder.environment().put( key, environment.baseGetString( key, "" ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try unix 1 way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "xdg-open", file );
			if (environment != null) {
				for (final Iterator<String> iterator = environment.baseGetIterator(); iterator.hasNext();) {
					final String key = iterator.next();
					builder.environment().put( key, environment.baseGetString( key, "" ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try unix 2 way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "gnome-open", file );
			if (environment != null) {
				for (final Iterator<String> iterator = environment.baseGetIterator(); iterator.hasNext();) {
					final String key = iterator.next();
					builder.environment().put( key, environment.baseGetString( key, "" ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try normal mac way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "open", file );
			if (environment != null) {
				for (final Iterator<String> iterator = environment.baseGetIterator(); iterator.hasNext();) {
					final String key = iterator.next();
					builder.environment().put( key, environment.baseGetString( key, "" ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try windows way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "cmd", "/c", "start " + file.replace( " ", "\" \"" ) );
			if (environment != null) {
				for (final Iterator<String> iterator = environment.baseGetIterator(); iterator.hasNext();) {
					final String key = iterator.next();
					builder.environment().put( key, environment.baseGetString( key, "" ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try itself
			 */
			final ProcessBuilder builder = new ProcessBuilder( file );
			if (environment != null) {
				for (final Iterator<String> iterator = environment.baseGetIterator(); iterator.hasNext();) {
					final String key = iterator.next();
					builder.environment().put( key, environment.baseGetString( key, "" ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		System.err.println( "ERROR, was unable to start process, here are all errors: " + exceptions );
		return null;
	}
	
	/**
	 * @param file
	 * @param environment
	 * @param folder
	 * @return
	 */
	public static final Process createProcess(
			final String file,
			final Map<String, Object> environment,
			final File folder) {
		final List<Exception> exceptions = new ArrayList<Exception>( 5 );
		{
			/**
			 * Try unix 1 way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "see", file );
			if (environment != null) {
				for (final String key : environment.keySet()) {
					builder.environment().put( key, String.valueOf( environment.get( key ) ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try unix 1 way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "xdg-open", file );
			if (environment != null) {
				for (final String key : environment.keySet()) {
					builder.environment().put( key, String.valueOf( environment.get( key ) ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try unix 2 way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "gnome-open", file );
			if (environment != null) {
				for (final String key : environment.keySet()) {
					builder.environment().put( key, String.valueOf( environment.get( key ) ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try normal mac way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "open", file );
			if (environment != null) {
				for (final String key : environment.keySet()) {
					builder.environment().put( key, String.valueOf( environment.get( key ) ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try windows way
			 */
			final ProcessBuilder builder = new ProcessBuilder( "cmd", "/c", "start " + file.replace( " ", "\" \"" ) );
			if (environment != null) {
				for (final String key : environment.keySet()) {
					builder.environment().put( key, String.valueOf( environment.get( key ) ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		{
			/**
			 * Try itself
			 */
			final ProcessBuilder builder = new ProcessBuilder( file );
			if (environment != null) {
				for (final String key : environment.keySet()) {
					builder.environment().put( key, String.valueOf( environment.get( key ) ) );
				}
			}
			if (folder != null) {
				builder.directory( folder );
			}
			try {
				return builder.start();
			} catch (final Exception e) {
				exceptions.add( e );
			}
		}
		System.err.println( "ERROR, was unable to start process, here are all errors: " + exceptions );
		return null;
	}
	
	/**
	 * @return newly generated random number
	 */
	public static final int createRandom() {
		/**
		 * current rand generator is not doing proper distribution for 'long'
		 * type
		 */
		return (int) Engine.RAND_GENERATOR.rand();
	}
	
	/**
	 * Returns random integer value in a range 0..(max-1)
	 * 
	 * @param max
	 * @return int
	 */
	public static final int createRandom(final int max) {
		return (int) ((Engine.RAND_GENERATOR.rand() & 0x7FFFFFFFFFFFFFFFL) % max);
	}
	
	/**
	 * Returns current time. It's normal for the value returned by this method
	 * to be more discreet that real-time system clocks but in any case it
	 * should change at least once per second. This value is recommended to use
	 * in some statistics algorithms, cache expiration calculation and any other
	 * places where millisecond resolution is not significant.
	 * 
	 * @return
	 */
	public static final long fastTime() {
		return Engine.CURRENT_TIME.getTime();
	}
	
	private static final String getHostName() {
		final String parameter = System.getProperty( "ru.myx.ae3.properties.hostname", "" ).trim();
		if (parameter.length() > 0) {
			return parameter;
		}
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (final UnknownHostException e) {
			return null;
		}
	}
	
	/**
	 * @return digest
	 */
	public static final MessageDigest getMessageDigestInstance() {
		try {
			return (MessageDigest) Engine.DIGEST.clone();
		} catch (final CloneNotSupportedException e) {
			throw new RuntimeException( e );
		}
	}
	
	/**
	 * @return storage instance
	 */
	public static final StorageAPI getStoragePrivate() {
		return Engine.STORAGE_PRIVATE;
	}
	
	/**
	 * !!!! FUCKING REMOVE THIS CRAP
	 * 
	 * @param persistencePrivate
	 */
	public static void replacePrivateStorage(final StorageAPI persistencePrivate) {
		Engine.STORAGE_PRIVATE = persistencePrivate;
	}
	
	private Engine() {
		// empty
	}
}
