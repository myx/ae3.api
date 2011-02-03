/*
 * Created on 17.12.2004
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ru.myx.ae3.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import ru.myx.ae3.AbstractSAPI;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitiveBoolean;
import ru.myx.ae3.base.BasePrimitiveNumber;
import ru.myx.ae3.base.BasePrimitiveString;

/**
 * @author myx
 * 
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Reflect extends AbstractSAPI {
	private static final AbstractReflectImpl						REFLECT_IMPL;
	
	/**
	 * Boolean type
	 */
	public static final ControlType<Boolean, BasePrimitiveBoolean>	CONTROL_TYPE_BOOLEAN;
	
	/**
	 * Default type
	 */
	public static final ControlType<BaseObject<?>, BaseObject<?>>	CONTROL_TYPE_EXACT_BASE_OBJECT;
	
	/**
	 * Numeric type
	 */
	public static final ControlType<Double, BasePrimitiveNumber>	CONTROL_TYPE_DOUBLE;
	
	/**
	 * Float type
	 */
	public static final ControlType<Float, BasePrimitiveNumber>		CONTROL_TYPE_FLOAT;
	
	/**
	 * Short type
	 */
	public static final ControlType<Short, BasePrimitiveNumber>		CONTROL_TYPE_SHORT;
	
	/**
	 * Int type
	 */
	public static final ControlType<Integer, BasePrimitiveNumber>	CONTROL_TYPE_INTEGER;
	
	/**
	 * Byte type
	 */
	public static final ControlType<Byte, BasePrimitiveNumber>		CONTROL_TYPE_BYTE;
	
	/**
	 * Character type
	 */
	public static final ControlType<Character, BasePrimitiveString>	CONTROL_TYPE_CHAR;
	
	/**
	 * Integer type
	 */
	public static final ControlType<Long, BasePrimitiveNumber>		CONTROL_TYPE_LONG;
	
	/**
	 * Numeric type
	 */
	public static final ControlType<Number, BaseObject<Number>>		CONTROL_TYPE_NUMBER;
	
	/**
	 * Object type
	 */
	public static final ControlType<Object, BaseObject<?>>			CONTROL_TYPE_EXACT_OBJECT;
	
	/**
	 * Object[] type
	 */
	public static final ControlType<Object[], BaseObject<?>>		CONTROL_TYPE_EXACT_OBJECT_ARRAY;
	
	/**
	 * String type
	 */
	public static final ControlType<String, BasePrimitiveString>	CONTROL_TYPE_STRING;
	
	/**
	 * Void type
	 */
	public static final ControlType<Void, BaseObject<?>>			CONTROL_TYPE_VOID;
	
	private static final Integer[]									INT_CACHE;
	
	private static final int										INT_CACHE_SIZE	= 32768;
	
	private static final Long[]										LONG_CACHE;
	
	private static final int										LONG_CACHE_SIZE	= 16384;
	
	static {
		INT_CACHE = new Integer[Reflect.INT_CACHE_SIZE];
		LONG_CACHE = new Long[Reflect.LONG_CACHE_SIZE];
		for (int i = Reflect.INT_CACHE_SIZE - 1; i >= 0; i--) {
			Reflect.INT_CACHE[i] = new Integer( i );
		}
		for (int i = Reflect.LONG_CACHE_SIZE - 1; i >= 0; i--) {
			Reflect.LONG_CACHE[i] = new Long( i );
		}
		/**
		 * this block should go last
		 */
		{
			REFLECT_IMPL = AbstractSAPI.createObject( "ru.myx.ae3.reflect.ImplementReflect" );
			assert Reflect.REFLECT_IMPL != null : "Reflect implementation is not accessible!";
			CONTROL_TYPE_BOOLEAN = Reflect.REFLECT_IMPL.getControlTypeExactBoolean();
			CONTROL_TYPE_EXACT_BASE_OBJECT = Reflect.REFLECT_IMPL.getControlTypeExactBaseObject();
			CONTROL_TYPE_BYTE = Reflect.REFLECT_IMPL.getControlTypeExactByte();
			CONTROL_TYPE_CHAR = Reflect.REFLECT_IMPL.getControlTypeExactChar();
			CONTROL_TYPE_DOUBLE = Reflect.REFLECT_IMPL.getControlTypeExactDouble();
			CONTROL_TYPE_FLOAT = Reflect.REFLECT_IMPL.getControlTypeExactFloat();
			CONTROL_TYPE_SHORT = Reflect.REFLECT_IMPL.getControlTypeExactShort();
			CONTROL_TYPE_INTEGER = Reflect.REFLECT_IMPL.getControlTypeExactInteger();
			CONTROL_TYPE_LONG = Reflect.REFLECT_IMPL.getControlTypeExactLong();
			CONTROL_TYPE_NUMBER = Reflect.REFLECT_IMPL.getControlTypeExactNumber();
			CONTROL_TYPE_EXACT_OBJECT = Reflect.REFLECT_IMPL.getControlTypeExactObject();
			CONTROL_TYPE_EXACT_OBJECT_ARRAY = Reflect.REFLECT_IMPL.getControlTypeExactObjectArray();
			CONTROL_TYPE_STRING = Reflect.REFLECT_IMPL.getControlTypeExactString();
			CONTROL_TYPE_VOID = Reflect.REFLECT_IMPL.getControlTypeExactVoid();
		}
		/**
		 * initialize, optional
		 */
		Reflect.classToBasePrototype( Object.class );
	}
	
	/**
	 * @param cls
	 * @return provider
	 */
	public static final BaseObject<?> classToBasePrototype(final Class<?> cls) {
		return Reflect.REFLECT_IMPL.classToBasePrototype( cls );
	}
	
	/**
	 * @param cls
	 * @return provider
	 */
	public static final Reflected classToReflected(final Class<?> cls) {
		return Reflect.REFLECT_IMPL.classToReflected( cls );
	}
	
	/**
	 * Example: public final static String name(int a, long b, double c)
	 * name#MSS#ild public void addr(Date d, Enumeration e, boolean f)
	 * addr#MIV#OOB public final int numb() numb#MIi#
	 * 
	 * @param method
	 * @return string
	 */
	public static final String describeConstructor(final Constructor<?> method) {
		final StringBuilder result = new StringBuilder( 64 ).append( Reflected.KEY_CONSTRUCTOR_JAVA ).append( '#' );
		Reflect.dumpClassString( result, method.getDeclaringClass() );
		result.append( '#' ).append( ((method.getModifiers() & Modifier.FINAL) != 0
				? 'M'
				: 'M') ).append( ((method.getModifiers() & Modifier.STATIC) != 0
				? 'S'
				: 'I') ).append( '#' );
		final Class<?>[] params = method.getParameterTypes();
		for (final Class<?> element : params) {
			Reflect.dumpClassString( result, element );
		}
		return result.toString();
	}
	
	/**
	 * Example: public final static String name name#CSS public String addr
	 * addr#VIS public final int numb numb#CIi
	 * 
	 * @param field
	 * @return string
	 */
	public static final String describeField(final Field field) {
		return field.getName() + '#' + ((field.getModifiers() & Modifier.FINAL) != 0
				? 'C'
				: 'V') + ((field.getModifiers() & Modifier.STATIC) != 0
				? 'S'
				: 'I') + Reflect.getClassLetter( field.getType() );
	}
	
	/**
	 * @param o
	 * @return string array
	 */
	public static final String[] describeFields(final Object o) {
		if (o == null) {
			return null;
		}
		final Class<?> cls = o.getClass();
		final Field[] fields = cls.getFields();
		final String[] result = new String[fields.length];
		for (int i = fields.length - 1; i >= 0; i--) {
			result[i] = Reflect.describeField( fields[i] );
		}
		return result;
	}
	
	/**
	 * @param o
	 * @return string array
	 */
	public static final String[] describeMembers(final Object o) {
		if (o == null) {
			return null;
		}
		final String[] fields = Reflect.describeFields( o );
		final String[] methds = Reflect.describeMethods( o );
		final String[] result = new String[fields.length + methds.length];
		System.arraycopy( fields, 0, result, 0, fields.length );
		System.arraycopy( methds, 0, result, fields.length, methds.length );
		return result;
	}
	
	/**
	 * Example: public final static String name(int a, long b, double c)
	 * name#MSS#ild public void addr(Date d, Enumeration e, boolean f)
	 * addr#MIV#OOB public final int numb() numb#MIi#
	 * 
	 * @param method
	 * @return string
	 */
	public static final String describeMethod(final Method method) {
		return Reflect.describeMethodInexact( method );
	}
	
	/**
	 * Example:
	 * <p>
	 * public final static String name(int a, long b, double c)<br>
	 * name#class#MSS#ild
	 * <p>
	 * public void addr(Date d, Enumeration e, boolean f)<br>
	 * addr#class#MIV#OOB
	 * <p>
	 * public final int numb()<br>
	 * numb#class#MIi#
	 * 
	 * @param method
	 * @return string
	 */
	public static final String describeMethodExact(final Method method) {
		final StringBuilder result = new StringBuilder( 64 ).append( method.getName() ).append( '#' );
		Reflect.dumpClassString( result, method.getDeclaringClass() );
		result.append( '#' ).append( ((method.getModifiers() & Modifier.FINAL) != 0
				? 'M'
				: 'M') ).append( ((method.getModifiers() & Modifier.STATIC) != 0
				? 'S'
				: 'I') );
		Reflect.dumpClassString( result, method.getReturnType() );
		result.append( '#' );
		final Class<?>[] params = method.getParameterTypes();
		for (final Class<?> element : params) {
			Reflect.dumpClassString( result, element );
		}
		return result.toString();
	}
	
	/**
	 * Example:
	 * <p>
	 * public final static String name(int a, long b, double c)<br>
	 * name#MSS#ild
	 * <p>
	 * public void addr(Date d, Enumeration e, boolean f)<br>
	 * addr#MIV#OOB
	 * <p>
	 * public final int numb()<br>
	 * numb#MIi#
	 * 
	 * @param method
	 * @return string
	 */
	public static final String describeMethodInexact(final Method method) {
		final StringBuilder result = new StringBuilder( 64 ).append( method.getName() ).append( '#' )
				.append( ((method.getModifiers() & Modifier.FINAL) != 0
						? 'M'
						: 'M') ).append( ((method.getModifiers() & Modifier.STATIC) != 0
						? 'S'
						: 'I') );
		Reflect.dumpClassString( result, method.getReturnType() );
		result.append( '#' );
		final Class<?>[] params = method.getParameterTypes();
		for (final Class<?> element : params) {
			Reflect.dumpClassString( result, element );
		}
		return result.toString();
	}
	
	/**
	 * @param o
	 * @return string array
	 */
	public static final String[] describeMethods(final Object o) {
		if (o == null) {
			return null;
		}
		final Class<?> cls = o.getClass();
		final Method[] methods = cls.getMethods();
		final String[] result = new String[methods.length];
		for (int i = methods.length - 1; i >= 0; i--) {
			result[i] = Reflect.describeMethod( methods[i] );
		}
		return result;
	}
	
	private static final void dumpClassString(final StringBuilder builder, final Class<?> cls) {
		final char classLetter = Reflect.getClassLetter( cls );
		builder.append( classLetter );
		if (classLetter == 'J') {
			builder.append( cls.getName() ).append( ';' );
		} else if (classLetter == '[') {
			Reflect.dumpClassString( builder, cls.getComponentType() );
		}
	}
	
	/**
	 * @param cls
	 * @return char-type
	 */
	public static final char getClassLetter(final Class<?> cls) {
		return cls == void.class
				? 'v'
				: cls == Void.class
						? 'V'
						: cls == boolean.class
								? 'b'
								: cls == Boolean.class
										? 'B'
										: cls == byte.class
												? 'o'
												: cls == Byte.class
														? 'O'
														: cls == int.class
																? 'i'
																: cls == Integer.class
																		? 'I'
																		: cls == long.class
																				? 'l'
																				: cls == Long.class
																						? 'L'
																						: cls == float.class
																								? 'f'
																								: cls == Float.class
																										? 'F'
																										: cls == double.class
																												? 'd'
																												: cls == Double.class
																														? 'D'
																														: cls == String.class
																																? 'S'
																																: cls == Object.class
																																		? 'A'
																																		: cls == BaseObject.class
																																				? 'a'
																																				: Object[].class
																																						.isAssignableFrom( cls )
																																						? '['
																																						: Number.class
																																								.isAssignableFrom( cls )
																																								? 'N'
																																								: 'J';
	}
	
	/**
	 * @param cls
	 * @return
	 */
	public static final String getClassString(final Class<?> cls) {
		final StringBuilder builder = new StringBuilder();
		Reflect.dumpClassString( builder, cls );
		return builder.toString();
	}
	
	/**
	 * @param cls
	 * @return converter
	 */
	public static final ControlType<? extends Object, ? extends BaseObject<?>> getConverter(final Class<?> cls) {
		if (cls == int.class || cls == Integer.class) {
			return Reflect.CONTROL_TYPE_INTEGER;
		}
		if (cls == long.class || cls == Long.class) {
			return Reflect.CONTROL_TYPE_LONG;
		}
		if (cls == String.class) {
			return Reflect.CONTROL_TYPE_STRING;
		}
		if (cls == float.class || cls == Float.class) {
			return Reflect.CONTROL_TYPE_FLOAT;
		}
		if (cls == double.class || cls == Double.class) {
			return Reflect.CONTROL_TYPE_DOUBLE;
		}
		if (cls == Number.class) {
			return Reflect.CONTROL_TYPE_NUMBER;
		}
		if (cls == boolean.class || cls == Boolean.class) {
			return Reflect.CONTROL_TYPE_BOOLEAN;
		}
		if (cls == BaseObject.class) {
			return Reflect.CONTROL_TYPE_EXACT_BASE_OBJECT;
		}
		if (cls == Object.class) {
			return Reflect.CONTROL_TYPE_EXACT_OBJECT;
		}
		if (cls == Object[].class) {
			return Reflect.CONTROL_TYPE_EXACT_OBJECT_ARRAY;
		}
		if (cls == short.class || cls == Short.class) {
			return Reflect.CONTROL_TYPE_SHORT;
		}
		if (cls == byte.class || cls == Byte.class) {
			return Reflect.CONTROL_TYPE_BYTE;
		}
		if (cls == Void.class || cls == void.class) {
			return Reflect.CONTROL_TYPE_VOID;
		}
		if (cls == Character.class || cls == char.class) {
			return Reflect.CONTROL_TYPE_CHAR;
		}
		/**
		 * TODO: isn't it easier to put em all to map? faster?
		 */
		return Reflect.REFLECT_IMPL.classToControlType( cls );
	}
	
	/**
	 * @param i
	 * @return integer
	 */
	public static final Integer getInteger(final int i) {
		return i < Reflect.INT_CACHE_SIZE && i >= 0
				? Reflect.INT_CACHE[i]
				: new Integer( i );
	}
	
	/**
	 * @param i
	 * @return long
	 */
	public static final Long getLong(final long i) {
		return i < Reflect.LONG_CACHE_SIZE && i >= 0
				? Reflect.LONG_CACHE[(int) i]
				: new Long( i );
	}
	
	/**
	 * Have no return type in signature, used internally in reflector.
	 * 
	 * Example:
	 * <p>
	 * public final static String name(int a, long b, double c)<br>
	 * name#MSS#ild
	 * <p>
	 * public void addr(Date d, Enumeration e, boolean f)<br>
	 * addr#MIV#OOB
	 * <p>
	 * public final int numb()<br>
	 * numb#MIi#
	 * 
	 * @param method
	 * @return string
	 */
	public static final String methodKey(final Method method) {
		final boolean instance = (method.getModifiers() & Modifier.STATIC) == 0;
		final StringBuilder result = new StringBuilder( 32 ).append( method.getName() )//
				.append( method.getReturnType().equals( Void.class )
						? instance
								? "#MIV#"
								: "#MSV#"
						: instance
								? "#MIA#"
								: "#MSA#" );
		final Class<?>[] params = method.getParameterTypes();
		for (final Class<?> element : params) {
			Reflect.dumpClassString( result, element );
		}
		return result.toString();
	}
	
	private Reflect() {
		// empty
	}
	
}
