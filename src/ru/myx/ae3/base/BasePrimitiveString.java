/**
 * 
 */
package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 */
@ReflectionDisable
public abstract class BasePrimitiveString extends BasePrimitiveAbstract<String> implements CharSequence,
		BaseArray<String, String> {
	/**
	 * 
	 */
	public static final BasePrimitiveString	EMPTY					= new PrimitiveStringEmpty();
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	FALSE					= new PrimitiveStringBaseNaN( "false" );
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	NAN						= new PrimitiveStringBaseNaN( "NaN" );
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	NINFINITY				= new PrimitiveStringBaseNumber( "-Infinity",
																			Double.NEGATIVE_INFINITY );
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	NULL					= new PrimitiveStringBaseNaN( "null" );
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	ONE						= new PrimitiveStringOne();
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	PINFINITY				= new PrimitiveStringBaseNumber( "Infinity",
																			Double.POSITIVE_INFINITY );
	
	/**
	 * 
	 */
	public static final BaseObject<?>		PROTOTYPE;
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	TRUE					= new PrimitiveStringBaseNaN( "true" );
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	UNDEFINED				= new PrimitiveStringBaseNaN( "undefined" );
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	ZERO					= new PrimitiveStringZero();
	
	private static final int				PROPERTY_LEN_LENGTH;
	
	/**
	 * ...and it actually will hold java-name, keeping it in memory and
	 * increasing probability of == check success, especially when 'length'
	 * string comes from VM execution.
	 */
	public static final BasePrimitiveString	PROPERTY_BASE_LENGTH	= new PrimitiveStringBaseNaN( "length" );
	
	/**
	 * ...and it actually will hold java-name, keeping it in memory and
	 * increasing probability of == check success, especially when 'length'
	 * string comes from VM execution.
	 */
	public static final BasePrimitiveString	PROPERTY_BASE_CALLEE	= new PrimitiveStringBaseNaN( "callee" );
	
	/**
	 * ...and it actually will hold java-name, keeping it in memory and
	 * increasing probability of == check success, especially when 'length'
	 * string comes from VM execution.
	 */
	public static final BasePrimitiveString	PROPERTY_BASE_PROTOTYPE	= new PrimitiveStringBaseNaN( "prototype" );
	
	/**
	 * 
	 */
	public static final String				PROPERTY_JAVA_LENGTH;
	
	/**
	 * 
	 */
	public static final String				PROPERTY_JAVA_CALLEE;
	
	/**
	 * 
	 */
	public static final String				PROPERTY_JAVA_PROTOTYPE;
	
	/**
	 * 
	 */
	private static final BaseProperty		LENGTH_PROPERTY;
	
	static {
		PROPERTY_JAVA_LENGTH = BasePrimitiveString.PROPERTY_BASE_LENGTH.baseValue();
		PROPERTY_JAVA_CALLEE = BasePrimitiveString.PROPERTY_BASE_CALLEE.baseValue();
		PROPERTY_JAVA_PROTOTYPE = BasePrimitiveString.PROPERTY_BASE_PROTOTYPE.baseValue();
		PROPERTY_LEN_LENGTH = "length".length();
		
		LENGTH_PROPERTY = new BasePropertyStringLength();
		
		PROTOTYPE = new BaseNativeObject( BaseObject.PROTOTYPE );
		
	}
	
	BasePrimitiveString() {
		//
	}
	
	@Override
	public final BasePrimitiveString baseArray() {
		return this;
	}
	
	@Override
	public final BaseArrayAdvanced<? extends String, ? extends String> baseArrayAdvanced() {
		return null;
	}
	
	@Override
	public final BaseArrayDynamic<? extends String, ? extends String> baseArrayDynamic() {
		return null;
	}
	
	@Override
	public final BaseArrayWritable<? extends String, ? extends String> baseArrayWritable() {
		return null;
	}
	
	@Override
	public final String baseClass() {
		return "string";
	}
	
	/**
	 * Allows array-like character access
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public final BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		if (name == BasePrimitiveString.PROPERTY_BASE_LENGTH) {
			return PrimitiveStringLengthValue.getLengthValue( this.baseValue().length() );
		}
		/**
		 * Have no other own properties!
		 */
		/**
		 * <code>
		return BasePrimitiveString.PROTOTYPE.baseGet( name, defaultValue );
		 * </code>
		 */
		for (BaseObject<?> object = BasePrimitiveString.PROTOTYPE;;) {
			final BaseProperty property = object.baseGetOwnProperty( name );
			if (property != null) {
				return property.propertyGet( this, name );
			}
			object = object.basePrototype();
			if (object == null) {
				return defaultValue;
			}
		}
	}
	
	@Override
	public final BaseObject<?> baseGet(final int index, final BaseObject<?> defaultValue) {
		if (index < 0) {
			return defaultValue;
		}
		final String string = this.stringValue();
		if (index >= string.length()) {
			return defaultValue;
		}
		return Base.forChar( string.charAt( index ) );
	}
	
	@Override
	public final BaseObject<?> baseGet(final String name) {
		if (name == BasePrimitiveString.PROPERTY_JAVA_LENGTH
				|| name.length() == BasePrimitiveString.PROPERTY_LEN_LENGTH
				&& BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name )) {
			return PrimitiveStringLengthValue.getLengthValue( this.baseValue().length() );
		}
		/**
		 * Have no other own properties!
		 */
		return BasePrimitiveString.PROTOTYPE.baseGet( name );
	}
	
	/**
	 * 15.5.5.1 length
	 * 
	 * The number of characters in the String value represented by this String
	 * object.
	 * 
	 * Once a String object is created, this property is unchanging. It has the
	 * attributes { DontEnum, DontDelete, ReadOnly }.
	 */
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		if (name == BasePrimitiveString.PROPERTY_BASE_LENGTH) {
			return BasePrimitiveString.LENGTH_PROPERTY;
		}
		return null;
	}
	
	/**
	 * 15.5.5.1 length
	 * 
	 * The number of characters in the String value represented by this String
	 * object.
	 * 
	 * Once a String object is created, this property is unchanging. It has the
	 * attributes { DontEnum, DontDelete, ReadOnly }.
	 */
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		if (BasePrimitiveString.PROPERTY_JAVA_LENGTH.equals( name )) {
			return BasePrimitiveString.LENGTH_PROPERTY;
		}
		return null;
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a string.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitiveBoolean() {
		return false;
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a string.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitiveInteger() {
		return false;
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a string.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitiveNumber() {
		return false;
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a string.
	 */
	@Deprecated
	@Override
	public final boolean baseIsPrimitiveString() {
		return true;
	}
	
	@Override
	public final BaseObject<?> basePrototype() {
		return BasePrimitiveString.PROTOTYPE;
	}
	
	@Override
	public BasePrimitiveNumber baseToInt32() {
		return this.baseToNumber().baseToInt32();
	}
	
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return this.baseToNumber().baseToInteger();
	}
	
	/**
	 * it is not ok to use this method while knowing that this object is
	 * actually a string.
	 */
	@Deprecated
	@Override
	public final BasePrimitiveString baseToString() {
		return this;
	}
	
	@Override
	public char charAt(final int index) {
		return this.baseValue().charAt( index );
	}
	
	@Override
	public double doubleValue() {
		return this.baseToNumber().doubleValue();
	}
	
	@Override
	public String get(final int index) {
		if (index < 0) {
			return null;
		}
		final String string = this.stringValue();
		if (index >= string.length()) {
			return null;
		}
		return String.valueOf( string.charAt( index ) );
	}
	
	@Override
	public int hashCode() {
		return this.baseValue().hashCode();
	}
	
	@Override
	public int intValue() {
		return this.baseToNumber().intValue();
	}
	
	@Override
	public abstract boolean isEmpty();
	
	/**
	 * String length in characters
	 * 
	 * @return
	 */
	@Override
	public abstract int length();
	
	/**
	 * @param searchValue
	 * @param replaceValue
	 * @return
	 */
	public BasePrimitiveString replace(final CharSequence searchValue, final CharSequence replaceValue) {
		return Base.forString( this.baseValue().replace( searchValue, replaceValue ) );
	}
	
	@Override
	public CharSequence subSequence(final int startIndex, final int endIndex) {
		return Base.forString( this.baseValue().substring( startIndex, endIndex ) );
	}
	
	/**
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public final BasePrimitiveString substring(final int startIndex, final int endIndex) {
		return Base.forString( this.baseValue().substring( startIndex, endIndex ) );
	}
	
	/**
	 * Must override java default
	 */
	@Override
	public abstract String toString();
}
