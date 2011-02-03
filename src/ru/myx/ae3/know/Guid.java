/**
 * 
 */
package ru.myx.ae3.know;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ru.myx.ae3.Engine;
import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitiveNumber;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.binary.TransferBuffer;
import ru.myx.ae3.binary.TransferCopier;
import ru.myx.ae3.binary.TransferTarget;
import ru.myx.ae3.binary.WrapCopier;

/**
 * @author myx
 * 
 */
public class Guid implements Comparable<Object> {
	/**
	 * NULL GUID
	 */
	public static final Guid						GUID_NULL;
	
	/**
	 * UNDEFINED GUID
	 */
	public static final Guid						GUID_UNDEFINED;
	
	/**
	 * EMPTY STRING GUID
	 */
	public static final Guid						GUID_STRING_EMPTY;
	
	/**
	 * EMPTY BINARY GUID
	 */
	public static final Guid						GUID_BYTES_EMPTY;
	
	/**
	 * ZERO DATE GUID
	 */
	public static final Guid						GUID_DATE_ZERO;
	
	/**
	 * ZERO GUID
	 */
	public static final Guid						GUID_NUMBER_ZERO;
	
	/**
	 * TRUE GUID
	 */
	public static final Guid						GUID_BOOLEAN_TRUE;
	
	/**
	 * FALSE GUID
	 */
	public static final Guid						GUID_BOOLEAN_FALSE;
	
	/**
	 * NEGATIVE INFINITY GUID
	 */
	public static final Guid						GUID_NEGATIVE_INFINITY;
	
	/**
	 * POSITIVE INFINITY GUID
	 */
	public static final Guid						GUID_POSITIVE_INFINITY;
	
	/**
	 * NAN VALUE GUID
	 */
	public static final Guid						GUID_NUMBER_NAN;
	
	/**
	 * POSITIVE ONE (+1) GUID
	 */
	public static final Guid						GUID_NUMBER_PONE;
	
	/**
	 * NEGATIVE ONE (-1) GUID
	 */
	public static final Guid						GUID_NUMBER_MONE;
	
	/**
	 * 
	 */
	public static final int							MAX_LENGTH				= 49;
	
	/**
	 * Maximal length of string produced by toHex() method.
	 */
	public static final int							MAX_LENGTH_HEX			= (int) Math
																					.ceil( Guid.MAX_LENGTH / 4.0 * 8 );
	
	/**
	 * Maximal length of string produced by toKey() method.
	 */
	public static final int							MAX_LENGTH_KEY			= (int) Math
																					.ceil( Guid.MAX_LENGTH / 5.0 * 8 );
	
	/**
	 * Maximal length of string produced by toString() method.
	 */
	public static final int							MAX_LENGTH_STRING		= (int) Math
																					.ceil( Guid.MAX_LENGTH / 6.0 * 8 );
	
	/**
	 * 
	 */
	private static final long						serialVersionUID		= 5738603133695660413L;
	
	private static final Random						RANDOM					= new Random();
	
	private static final char[]						XLAT64					= {
																			// for
			// all
			'A',
			'B',
			'C',
			'D',
			'E',
			'F',
			'G',
			'H',
			'I',
			'J',
			'K',
			'L',
			'M',
			'N',
			'O',
			'P',
			'Q',
			'R',
			'S',
			'T',
			'U',
			'V',
			'W',
			'X',
			'Y',
			'Z',
			'a',
			'b',
			'c',
			'd',
			'e',
			'f',
			'g',
			'h',
			'i',
			'j',
			'k',
			'l',
			'm',
			'n',
			'o',
			'p',
			'q',
			'r',
			's',
			't',
			'u',
			'v',
			'w',
			'x',
			'y',
			'z',
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9',
			'-',
			'_',
			'=',// eof
			
																			};
	
	private static final char[]						XLAT32					= {
																			// for
			// all
			'A',
			'B',
			'C',
			'D',
			'E',
			'F',
			'G',
			'H',
			'I',
			'J',
			'K',
			'L',
			'M',
			'N',
			'O',
			'P',
			'Q',
			'R',
			'S',
			'T',
			'U',
			'V',
			'W',
			'X',
			'Y',
			'Z',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'=',// eof
			
																			};
	
	private static final char[]						XLAT16					= {
																			// for
			// all
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9',
			'a',
			'b',
			'c',
			'd',
			'e',
			'f',
			'=',// eof
			
																			};
	
	private static final int[]						XLATDE64;
	
	private static final int[]						XLATDE32;
	
	private static final int[]						XLATDE16;
	
	private static final GuidType[]					GUID_TYPES_FOR_TYPE_BYTE;
	
	private static final Guid[]						GUID_SIMPLE_FOR_TYPE_BYTE;
	
	private static final Map<BaseObject<?>, Guid>	GUID_SIMPLE_FOR_PRIMITIVE;
	
	static {
		/**
		 * Just because Engine must be initialized before some base facilities
		 * (and GUID is one of them).
		 */
		Engine.createGuid();
		
		XLATDE64 = new int[256];
		for (int i = Guid.XLAT64.length - 1; i >= 0; i--) {
			Guid.XLATDE64[Guid.XLAT64[i]] = i;
		}
		Guid.XLATDE64['$'] = Guid.XLATDE64['='];
		
		XLATDE32 = new int[256];
		for (int i = Guid.XLAT32.length - 1; i >= 0; i--) {
			Guid.XLATDE32[Guid.XLAT32[i]] = i;
		}
		Guid.XLATDE32['$'] = Guid.XLATDE32['='];
		
		XLATDE16 = new int[256];
		for (int i = Guid.XLAT16.length - 1; i >= 0; i--) {
			Guid.XLATDE16[Guid.XLAT16[i]] = i;
		}
		Guid.XLATDE16['$'] = Guid.XLATDE16['='];
	}
	
	static {
		
		{
			GUID_TYPES_FOR_TYPE_BYTE = new GuidType[256];
			short start = 0;
			for (final GuidType type : GuidType.ALL) {
				type.startIndex = start;
				for (int i = type.count(); i > 0; i--) {
					Guid.GUID_TYPES_FOR_TYPE_BYTE[start++] = type;
				}
			}
			assert start < 256;
			for (int i = start; i < 256; i++) {
				Guid.GUID_TYPES_FOR_TYPE_BYTE[i] = GuidType.INVALID;
			}
		}
		
		{
			GUID_SIMPLE_FOR_TYPE_BYTE = new Guid[256];
			GUID_SIMPLE_FOR_PRIMITIVE = new HashMap<BaseObject<?>, Guid>();
			
			for (int i = 255; i >= 0; i--) {
				final GuidType type = Guid.GUID_TYPES_FOR_TYPE_BYTE[i];
				if (!type.requiresSubByte() && type.isInline() && type.getLeastSize( 0 ) == 0) {
					final byte[] bytes = new byte[] { (byte) i };
					final BaseObject<?> baseValue = type.getBaseObject( bytes );
					assert baseValue != null : "NULL java value!";
					assert type.getBaseObject( bytes ) != null : "NULL java value";
					assert type.getBaseObject( bytes ).equals( baseValue ) : "Should be equal ("
							+ type.getBaseObject( bytes )
							+ ", "
							+ baseValue
							+ ", class="
							+ baseValue.getClass().getName()
							+ ")!";
					assert type.getBaseObject( bytes ).hashCode() == baseValue.hashCode() : "Hash code should be equal!";
					final Object javaValue = type.getJavaObject( bytes );
					final int hashCode = Guid.hashCode( bytes, 0, 1 );
					final Guid guid = new Guid( bytes ) {
						@Override
						public BaseObject<?> getInlineBaseValue() {
							return baseValue;
						}
						
						@Override
						public Object getInlineValue() {
							return javaValue;
						}
						
						@Override
						public int hashCode() {
							return hashCode;
						}
						
						@Override
						public boolean isInline() {
							return true;
						}
						
						@Override
						public boolean isValid() {
							return type != GuidType.INVALID;
						}
					};
					Guid.GUID_SIMPLE_FOR_TYPE_BYTE[i] = guid;
					Guid.GUID_SIMPLE_FOR_PRIMITIVE.put( baseValue, guid );
				}
			}
		}
		
		GUID_NULL = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BaseObject.NULL );
		
		GUID_UNDEFINED = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BaseObject.UNDEFINED );
		
		GUID_DATE_ZERO = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( Base.forDateMillis( 0 ) );
		
		GUID_NUMBER_NAN = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BasePrimitiveNumber.NAN );
		GUID_NUMBER_MONE = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BasePrimitiveNumber.MONE );
		GUID_NUMBER_ZERO = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BasePrimitiveNumber.ZERO );
		GUID_NUMBER_PONE = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BasePrimitiveNumber.ONE );
		
		GUID_BOOLEAN_FALSE = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BaseObject.FALSE );
		GUID_BOOLEAN_TRUE = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BaseObject.TRUE );
		
		GUID_STRING_EMPTY = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BasePrimitiveString.EMPTY );
		
		GUID_BYTES_EMPTY = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( Base.forUnknown( TransferCopier.NUL_COPIER ) );
		
		GUID_NEGATIVE_INFINITY = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BasePrimitiveNumber.NINF );
		GUID_POSITIVE_INFINITY = Guid.GUID_SIMPLE_FOR_PRIMITIVE.get( BasePrimitiveNumber.PINF );
	}
	
	private static final double						PARTITIONING_SUBSTRACT	= Integer.MIN_VALUE;
	
	private static final double						PARTITIONING_DIVIDE		= (double) Integer.MAX_VALUE
																					- (double) Integer.MIN_VALUE;
	
	/**
	 * Creates new guid
	 * 
	 * @return guid
	 */
	public static final Guid createGuid184() {
		final GuidType type = GuidType.GUID184;
		final byte[] bytes = new byte[type.getLeastSize( 0 ) + 1];
		Guid.RANDOM.nextBytes( bytes );
		bytes[0] = (byte) (type.startIndex + (bytes[0] & 0xFF) % type.count());
		return new Guid( bytes );
	}
	
	/**
	 * Creates new guid
	 * 
	 * @return guid
	 */
	public static final Guid createGuid384() {
		final GuidType type = GuidType.GUID384;
		final byte[] bytes = new byte[type.getLeastSize( 0 ) + 1];
		Guid.RANDOM.nextBytes( bytes );
		bytes[0] = (byte) (type.startIndex + (bytes[0] & 0xFF) % type.count());
		return new Guid( bytes );
	}
	
	private static final void fillBytesFromString(
			final String guidString,
			final byte[] guidBytes,
			final int offset,
			final int limit) {
		int target = offset;
		int source = 0;
		int left = limit;
		for (;;) {
			final char char1 = guidString.charAt( source++ );
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE64[char1] & 0x3F) << 2);
				break;
			}
			final char char2 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE64[char1] & 0x3F) << 2) + ((Guid.XLATDE64[char2] & 0x3F) >> 4));
			if (--left == 0) {
				break;
			}
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE64[char2] & 0x0F) << 4);
				break;
			}
			final char char3 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE64[char2] & 0x0F) << 4) + ((Guid.XLATDE64[char3] & 0x3F) >> 2));
			if (--left == 0) {
				break;
			}
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE64[char3] & 0x03) << 6);
				break;
			}
			final char char4 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE64[char3] & 0x03) << 6) + ((Guid.XLATDE64[char4] & 0x3F) >> 0));
			if (--left == 0) {
				break;
			}
			if (source == guidString.length()) {
				break;
			}
		}
	}
	
	/**
	 * @param binary
	 * @return guid or NULL if binary should be stored externally!
	 */
	public static final Guid forBinary(final TransferCopier binary) {
		if (binary == null) {
			return Guid.GUID_NULL;
		}
		final long length = binary.length();
		if (length > 48) {
			return new Guid( GuidType.createCRC( binary, length ) );
		}
		if (length == 0) {
			return Guid.GUID_BYTES_EMPTY;
		}
		if (length == 48) {
			final byte[] bytes = new byte[49];
			binary.nextCopy().next( bytes, 1, 48 );
			bytes[0] = (byte) GuidType.BYTES48.startIndex;
			return new Guid( bytes );
		}
		final byte[] bytes = new byte[2 + (int) length];
		binary.nextCopy().next( bytes, 2, (int) length );
		bytes[0] = (byte) GuidType.BYTES.startIndex;
		bytes[1] = (byte) length;
		return new Guid( bytes );
	}
	
	/**
	 * @param identifier
	 * @param instance
	 * @return guid
	 */
	public static final Guid forInstanceIdentifier(final String identifier, final String instance) {
		if (identifier.length() >= 32) {
			throw new IllegalArgumentException( "Identifier is too long!" );
		}
		if (instance.length() >= 32) {
			throw new IllegalArgumentException( "Instance is too long!" );
		}
		final GuidType type = GuidType.GUID_INSTANCE_IDENTITY;
		final byte[] bytes = new byte[type.getLeastSize( 0 ) + 1];
		bytes[0] = (byte) type.startIndex;
		Guid.fillBytesFromString( identifier, bytes, 1, 24 );
		Guid.fillBytesFromString( instance, bytes, 25, 24 );
		return new Guid( bytes );
	}
	
	/**
	 * @param value
	 * @return guid
	 */
	public static final Guid forJavaDate(final Date value) {
		return Guid.forJavaDateMillis( value.getTime() );
	}
	
	/**
	 * @param value
	 * @return guid
	 */
	public static final Guid forJavaDateMillis(final long value) {
		if (value == 0) {
			return Guid.GUID_DATE_ZERO;
		}
		if (value > 0) {
			return new Guid( GuidType.createDATE( value ) );
		}
		final byte[] bytes = new byte[9];
		bytes[0] = (byte) GuidType.DATE_NEGATIVE_INT_64BIT.startIndex;
		bytes[1] = (byte) (value >> 56);
		bytes[2] = (byte) (value >> 48);
		bytes[3] = (byte) (value >> 40);
		bytes[4] = (byte) (value >> 32);
		bytes[5] = (byte) (value >> 24);
		bytes[6] = (byte) (value >> 16);
		bytes[7] = (byte) (value >> 8);
		bytes[8] = (byte) (value >> 0);
		return new Guid( bytes );
	}
	
	/**
	 * @param value
	 * @return guid
	 */
	public static final Guid forJavaDouble(final double value) {
		if (Double.isNaN( value )) {
			return Guid.GUID_NUMBER_NAN;
		}
		if (value == (long) value) {
			return Guid.forJavaLong( (long) value );
		}
		if (value == Double.NEGATIVE_INFINITY) {
			return Guid.GUID_NEGATIVE_INFINITY;
		}
		if (value == Double.POSITIVE_INFINITY) {
			return Guid.GUID_POSITIVE_INFINITY;
		}
		final byte[] bytes = new byte[9];
		final long bits;
		if (value < 0) {
			bits = Double.doubleToLongBits( value ) ^ 0xFFFFFFFFFFFFFFFFL;
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_NOT_INTEGER.startIndex;
		} else {
			bits = Double.doubleToLongBits( value );
			bytes[0] = (byte) GuidType.NUMBER_POSITIVE_NOT_INTEGER.startIndex;
		}
		bytes[1] = (byte) (bits >> 56);
		bytes[2] = (byte) (bits >> 48);
		bytes[3] = (byte) (bits >> 40);
		bytes[4] = (byte) (bits >> 32);
		bytes[5] = (byte) (bits >> 24);
		bytes[6] = (byte) (bits >> 16);
		bytes[7] = (byte) (bits >> 8);
		bytes[8] = (byte) (bits >> 0);
		return new Guid( bytes );
	}
	
	/**
	 * @param value
	 * @return guid
	 */
	public static final Guid forJavaInteger(final int value) {
		if (value == 0) {
			return Guid.GUID_NUMBER_ZERO;
		}
		if (value > 0) {
			if (value == 1) {
				return Guid.GUID_NUMBER_PONE;
			}
			return new Guid( GuidType.createINTP( value ) );
		}
		if (value == -1) {
			return Guid.GUID_NUMBER_MONE;
		}
		if ((0xFFFFFF00 & value) == 0xFFFFFF00) {
			final byte[] bytes = new byte[2];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_8BIT.startIndex;
			bytes[1] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFFFF0000 & value) == 0xFFFF0000) {
			final byte[] bytes = new byte[3];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_16BIT.startIndex;
			bytes[1] = (byte) (value >> 8);
			bytes[2] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFF000000 & value) == 0xFF000000) {
			final byte[] bytes = new byte[4];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_24BIT.startIndex;
			bytes[1] = (byte) (value >> 16);
			bytes[2] = (byte) (value >> 8);
			bytes[3] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		final byte[] bytes = new byte[5];
		bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_32BIT.startIndex;
		bytes[1] = (byte) (value >> 24);
		bytes[2] = (byte) (value >> 16);
		bytes[3] = (byte) (value >> 8);
		bytes[4] = (byte) (value >> 0);
		return new Guid( bytes );
	}
	
	/**
	 * @param value
	 * @return guid
	 */
	public static final Guid forJavaLong(final long value) {
		if (value == 0L) {
			return Guid.GUID_NUMBER_ZERO;
		}
		if (value > 0L) {
			if (value == 1L) {
				return Guid.GUID_NUMBER_PONE;
			}
			return new Guid( GuidType.createINTP( value ) );
		}
		if (value == -1) {
			return Guid.GUID_NUMBER_MONE;
		}
		if ((0xFFFFFFFFFFFFFF00L & value) == 0xFFFFFFFFFFFFFF00L) {
			final byte[] bytes = new byte[2];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_8BIT.startIndex;
			bytes[1] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFFFFFFFFFFFF0000L & value) == 0xFFFFFFFFFFFF0000L) {
			final byte[] bytes = new byte[3];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_16BIT.startIndex;
			bytes[1] = (byte) (value >> 8);
			bytes[2] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFFFFFFFFFF000000L & value) == 0xFFFFFFFFFF000000L) {
			final byte[] bytes = new byte[4];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_24BIT.startIndex;
			bytes[1] = (byte) (value >> 16);
			bytes[2] = (byte) (value >> 8);
			bytes[3] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFFFFFFFF00000000L & value) == 0xFFFFFFFF00000000L) {
			final byte[] bytes = new byte[5];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_32BIT.startIndex;
			bytes[1] = (byte) (value >> 24);
			bytes[2] = (byte) (value >> 16);
			bytes[3] = (byte) (value >> 8);
			bytes[4] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFFFFFF0000000000L & value) == 0xFFFFFF0000000000L) {
			final byte[] bytes = new byte[6];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_40BIT.startIndex;
			bytes[1] = (byte) (value >> 32);
			bytes[2] = (byte) (value >> 24);
			bytes[3] = (byte) (value >> 16);
			bytes[4] = (byte) (value >> 8);
			bytes[5] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFFFF000000000000L & value) == 0xFFFF000000000000L) {
			final byte[] bytes = new byte[7];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_48BIT.startIndex;
			bytes[1] = (byte) (value >> 40);
			bytes[2] = (byte) (value >> 32);
			bytes[3] = (byte) (value >> 24);
			bytes[4] = (byte) (value >> 16);
			bytes[5] = (byte) (value >> 8);
			bytes[6] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		if ((0xFF00000000000000L & value) == 0xFF00000000000000L) {
			final byte[] bytes = new byte[8];
			bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_56BIT.startIndex;
			bytes[1] = (byte) (value >> 48);
			bytes[2] = (byte) (value >> 40);
			bytes[3] = (byte) (value >> 32);
			bytes[4] = (byte) (value >> 24);
			bytes[5] = (byte) (value >> 16);
			bytes[6] = (byte) (value >> 8);
			bytes[7] = (byte) (value >> 0);
			return new Guid( bytes );
		}
		final byte[] bytes = new byte[9];
		bytes[0] = (byte) GuidType.NUMBER_NEGATIVE_INT_64BIT.startIndex;
		bytes[1] = (byte) (value >> 56);
		bytes[2] = (byte) (value >> 48);
		bytes[3] = (byte) (value >> 40);
		bytes[4] = (byte) (value >> 32);
		bytes[5] = (byte) (value >> 24);
		bytes[6] = (byte) (value >> 16);
		bytes[7] = (byte) (value >> 8);
		bytes[8] = (byte) (value >> 0);
		return new Guid( bytes );
	}
	
	/**
	 * @param value
	 * @return guid or NULL if string should be stored externally!
	 */
	public static final Guid forString(final String value) {
		if (value == null) {
			return Guid.GUID_NULL;
		}
		final int length = value.length();
		if (length == 0) {
			return Guid.GUID_STRING_EMPTY;
		}
		if (length <= 54) {
			// try inline
			int position = 0;
			// try 7bit
			for (;;) {
				final char c = value.charAt( position );
				if ((c & 0x7F) != c) {
					break;
				}
				++position;
				if (position == length) {
					final byte[] bytes;
					final int shift;
					if (length == 54) {
						bytes = new byte[49];
						bytes[0] = (byte) GuidType.STRING_ASCII54.startIndex;
						shift = 1;
					} else {
						bytes = new byte[2 + (length * 7 + 7) / 8];
						bytes[0] = (byte) GuidType.STRING_ASCII.startIndex;
						bytes[1] = (byte) length;
						shift = 2;
					}
					for (int src = 0, tgt = shift;; tgt += 7) {
						final char ch1 = value.charAt( src++ );
						if (src == length) {
							bytes[tgt + 0] = (byte) ((ch1 & 0x7F) << 1);
							break;
						}
						final char ch2 = value.charAt( src++ );
						bytes[tgt + 0] = (byte) (((ch1 & 0x7F) << 1) + ((ch2 & 0x40) >> 6));
						if (src == length) {
							bytes[tgt + 1] = (byte) ((ch2 & 0x3F) << 2);
							break;
						}
						final char ch3 = value.charAt( src++ );
						bytes[tgt + 1] = (byte) (((ch2 & 0x3F) << 2) + ((ch3 & 0x60) >> 5));
						if (src == length) {
							bytes[tgt + 2] = (byte) ((ch3 & 0x1F) << 3);
							break;
						}
						final char ch4 = value.charAt( src++ );
						bytes[tgt + 2] = (byte) (((ch3 & 0x1F) << 3) + ((ch4 & 0x70) >> 4));
						if (src == length) {
							bytes[tgt + 3] = (byte) ((ch4 & 0x0F) << 4);
							break;
						}
						final char ch5 = value.charAt( src++ );
						bytes[tgt + 3] = (byte) (((ch4 & 0x0F) << 4) + ((ch5 & 0x78) >> 3));
						if (src == length) {
							bytes[tgt + 4] = (byte) ((ch5 & 0x07) << 5);
							break;
						}
						final char ch6 = value.charAt( src++ );
						bytes[tgt + 4] = (byte) (((ch5 & 0x07) << 5) + ((ch6 & 0x7C) >> 2));
						if (src == length) {
							bytes[tgt + 5] = (byte) ((ch6 & 0x03) << 6);
							break;
						}
						final char ch7 = value.charAt( src++ );
						bytes[tgt + 5] = (byte) (((ch6 & 0x03) << 6) + ((ch7 & 0x7E) >> 1));
						if (src == length) {
							bytes[tgt + 6] = (byte) ((ch7 & 0x01) << 7);
							break;
						}
						final char ch8 = value.charAt( src++ );
						bytes[tgt + 6] = (byte) (((ch7 & 0x01) << 7) + ((ch8 & 0x7F) >> 0));
						if (src == length) {
							break;
						}
					}
					return new Guid( bytes );
				}
			}
			if (length <= 48) {
				int utfLength = position;
				// try 8bit
				for (;;) {
					final char c = value.charAt( position );
					if ((c & 0xFF) != c) {
						break;
					}
					++position;
					if (position == length) {
						final byte[] bytes;
						final int shift;
						if (length == 48) {
							bytes = new byte[49];
							bytes[0] = (byte) GuidType.STRING_8BIT48.startIndex;
							shift = 1;
						} else {
							bytes = new byte[2 + length];
							bytes[0] = (byte) GuidType.STRING_8BIT.startIndex;
							bytes[1] = (byte) length;
							shift = 2;
						}
						for (int tgt = shift, src = 0; src < length; src++) {
							bytes[tgt++] = (byte) (value.charAt( src ) & 0xFF);
						}
						return new Guid( bytes );
					}
				}
				// count utf8
				for (int pos = utfLength;;) {
					final char c = value.charAt( pos++ );
					if (c >= 0x0001 && c <= 0x007F) {
						utfLength++;
					} else if (c > 0x07FF) {
						utfLength += 3;
					} else {
						utfLength += 2;
					}
					if (pos == length) {
						if (utfLength < length * 2) {
							// then utf8
							final byte[] bytes;
							final int shift;
							if (utfLength == 48) {
								bytes = new byte[49];
								bytes[0] = (byte) GuidType.STRING_UTF8_48.startIndex;
								shift = 1;
							} else {
								bytes = new byte[2 + utfLength];
								bytes[0] = (byte) GuidType.STRING_UTF8.startIndex;
								bytes[1] = (byte) utfLength;
								shift = 2;
							}
							for (int tgt = shift, src = 0; src < length; src++) {
								final char ch = value.charAt( src );
								if (ch >= 0x0001 && ch <= 0x007F) {
									bytes[tgt++] = (byte) ch;
									continue;
								}
								if (ch > 0x07FF) {
									bytes[tgt++] = (byte) (0xE0 | ch >> 12 & 0x0F);
									bytes[tgt++] = (byte) (0x80 | ch >> 6 & 0x3F);
									bytes[tgt++] = (byte) (0x80 | ch >> 0 & 0x3F);
									continue;
								}
								bytes[tgt++] = (byte) (0xC0 | ch >> 6 & 0x1F);
								bytes[tgt++] = (byte) (0x80 | ch >> 0 & 0x3F);
							}
							return new Guid( bytes );
						}
						break;
					}
				}
				if (length <= 24) {
					// then unicode
					final byte[] bytes;
					final int shift;
					if (length == 24) {
						bytes = new byte[49];
						bytes[0] = (byte) GuidType.STRING_UNICODE24.startIndex;
						shift = 1;
					} else {
						bytes = new byte[2 + length * 2];
						bytes[0] = (byte) GuidType.STRING_UNICODE.startIndex;
						bytes[1] = (byte) length;
						shift = 2;
					}
					for (int tgt = shift, src = 0; src < length; src++) {
						final char ch = value.charAt( src );
						bytes[tgt++] = (byte) (ch >> 8 & 0xFF);
						bytes[tgt++] = (byte) (ch & 0xFF);
					}
					return new Guid( bytes );
				}
			}
		}
		return null;
	}
	
	/**
	 * Creates inline GUID for arbitrary java object, since not any java object
	 * can be stored as GUIDs this method will return NULL when given object is
	 * not available as inline GUID value.
	 * 
	 * Note - this method doesn't perform checks for externalizable object - use
	 * specific methods for this purpose.
	 * 
	 * @param object
	 * @return guid
	 */
	public static final Guid forUnknown(final BaseObject<?> object) {
		if (object == null || object == BaseObject.NULL) {
			return Guid.GUID_NULL;
		}
		if (object.baseIsPrimitive()) {
			if (object == BaseObject.UNDEFINED) {
				return Guid.GUID_UNDEFINED;
			}
			if (object.baseIsPrimitiveInteger()) {
				return Guid.forJavaLong( object.baseToInteger().longValue() );
			}
			if (object.baseIsPrimitiveNumber()) {
				return Guid.forJavaDouble( object.baseToNumber().doubleValue() );
			}
			if (object.baseIsPrimitiveString()) {
				return Guid.forString( object.toString() );
			}
			if (object.baseIsPrimitiveBoolean()) {
				return object == BaseObject.TRUE
						? Guid.GUID_BOOLEAN_TRUE
						: Guid.GUID_BOOLEAN_FALSE;
			}
		}
		if (object instanceof Date) {
			return Guid.forJavaDateMillis( ((Date) object).getTime() );
		}
		if (object instanceof TransferCopier) {
			return Guid.forBinary( (TransferCopier) object );
		}
		if (object instanceof TransferBuffer) {
			return Guid.forBinary( ((TransferBuffer) object).toCloneFactory() );
		}
		final Object value = object.baseValue();
		return value == object || value == null
				? null
				: value instanceof BaseObject<?>
						? Guid.forUnknown( (BaseObject<?>) value )
						: Guid.forUnknown( value );
	}
	
	/**
	 * Creates inline GUID for arbitrary java object, since not any java object
	 * can be stored as GUIDs this method will return NULL when given object is
	 * not available as inline GUID value.
	 * 
	 * Note - this method doesn't perform checks for externalizable object - use
	 * specific methods for this purpose.
	 * 
	 * @param value
	 * @return guid
	 */
	public static final Guid forUnknown(final Object value) {
		if (value instanceof BaseObject<?>) {
			return Guid.forUnknown( (BaseObject<?>) value );
		}
		if (value == null) {
			return Guid.GUID_NULL;
		}
		if (value instanceof Number) {
			if (value instanceof Long) {
				return Guid.forJavaLong( ((Number) value).longValue() );
			}
			return Guid.forJavaDouble( ((Number) value).doubleValue() );
		}
		if (value instanceof Date) {
			return Guid.forJavaDateMillis( ((Date) value).getTime() );
		}
		if (value instanceof String) {
			return Guid.forString( (String) value );
		}
		if (value instanceof TransferCopier) {
			return Guid.forBinary( (TransferCopier) value );
		}
		if (value instanceof TransferBuffer) {
			return Guid.forBinary( ((TransferBuffer) value).toCloneFactory() );
		}
		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue()
					? Guid.GUID_BOOLEAN_TRUE
					: Guid.GUID_BOOLEAN_FALSE;
		}
		if (value instanceof byte[]) {
			return Guid.forBinary( new WrapCopier( (byte[]) value ) );
		}
		return null;
	}
	
	/**
	 * @param guidString
	 * @return guid
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final Guid fromBase64(final String guidString) throws IllegalArgumentException, NullPointerException {
		if (guidString == null || guidString.length() == 0) {
			return Guid.GUID_NULL;
		}
		final byte[] guidBytes = new byte[Guid.MAX_LENGTH];
		Guid.fillBytesFromString( guidString, guidBytes, 0, Guid.MAX_LENGTH );
		return Guid.readGuid( guidBytes );
	}
	
	/**
	 * @param guidString
	 * @return guid
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final Guid fromHex(final String guidString) throws IllegalArgumentException, NullPointerException {
		if (guidString == null || guidString.length() == 0) {
			return Guid.GUID_NULL;
		}
		final byte[] guidBytes = new byte[Guid.MAX_LENGTH];
		int target = 0;
		int source = 0;
		for (; target < Guid.MAX_LENGTH;) {
			final char char1 = guidString.charAt( source++ );
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE16[char1] & 0x0F) << 4);
				break;
			}
			if (target >= Guid.MAX_LENGTH) {
				break;
			}
			final char char2 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE16[char1] & 0x0F) << 4) + ((Guid.XLATDE16[char2] & 0x0F) << 0));
			if (target >= Guid.MAX_LENGTH) {
				break;
			}
			if (source == guidString.length()) {
				break;
			}
		}
		return Guid.readGuid( guidBytes );
	}
	
	/**
	 * @param guidString
	 * @return guid
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final Guid fromName(final String guidString) throws IllegalArgumentException, NullPointerException {
		if (guidString == null || guidString.length() == 0) {
			return Guid.GUID_NULL;
		}
		final byte[] guidBytes = new byte[Guid.MAX_LENGTH];
		int target = 0;
		int source = 0;
		for (; target < Guid.MAX_LENGTH;) {
			final char char1 = guidString.charAt( source++ );
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE32[char1] & 0x1F) << 3);
				break;
			}
			final char char2 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE32[char1] & 0x1F) << 3) + ((Guid.XLATDE32[char2] & 0x1F) >> 2));
			if (target >= Guid.MAX_LENGTH) {
				break;
			}
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE32[char2] & 0x03) << 6);
				break;
			}
			final char char3 = guidString.charAt( source++ );
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) (((Guid.XLATDE32[char2] & 0x03) << 6) + ((Guid.XLATDE32[char3] & 0x1F) << 1));
				break;
			}
			final char char4 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE32[char2] & 0x03) << 6) + ((Guid.XLATDE32[char3] & 0x1F) << 1) + ((Guid.XLATDE32[char4] & 0x10) >> 4));
			if (target >= Guid.MAX_LENGTH) {
				break;
			}
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE32[char4] & 0x0F) << 4);
				break;
			}
			final char char5 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE32[char4] & 0x0F) << 4) + ((Guid.XLATDE32[char5] & 0x1E) >> 1));
			if (target >= Guid.MAX_LENGTH) {
				break;
			}
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE32[char5] & 0x01) << 7);
				break;
			}
			final char char6 = guidString.charAt( source++ );
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) (((Guid.XLATDE32[char5] & 0x01) << 7) + ((Guid.XLATDE32[char6] & 0x1F) << 2));
				break;
			}
			final char char7 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE32[char5] & 0x01) << 7) + ((Guid.XLATDE32[char6] & 0x1F) << 2) + ((Guid.XLATDE32[char7] & 0x18) >> 3));
			if (target >= Guid.MAX_LENGTH) {
				break;
			}
			if (source == guidString.length()) {
				guidBytes[target++] = (byte) ((Guid.XLATDE32[char7] & 0x07) << 5);
				break;
			}
			final char char8 = guidString.charAt( source++ );
			guidBytes[target++] = (byte) (((Guid.XLATDE32[char7] & 0x07) << 5) + ((Guid.XLATDE32[char8] & 0x1F) << 0));
			if (target >= Guid.MAX_LENGTH) {
				break;
			}
			if (source == guidString.length()) {
				break;
			}
		}
		return Guid.readGuid( guidBytes );
	}
	
	private static final int hashCode(final byte[] bytes, final int offset, final int length) {
		int hashCode = 0;
		for (int i = length - 1; i >= 0; i--) {
			final int x = bytes[offset + i] & 0xFF;
			hashCode = 2147483543 * hashCode + 342283 * (x + (x << 8));
		}
		return hashCode;
	}
	
	/**
	 * @param guidBytes
	 * @param offset
	 * @param guid
	 * @return
	 */
	public static final int readCompare(final byte[] guidBytes, final int offset, final Guid guid) {
		if (guidBytes == null) {
			return guid == Guid.GUID_NULL
					? 0
					: -1;
		}
		final byte[] bytes2 = guid.bytes;
		for (int i = bytes2.length, source = 0; i > 0; source++, i--) {
			final int diff = (guidBytes[source + offset] & 0xFF) - (bytes2[source] & 0xFF);
			if (diff < 0) {
				return -1;
			}
			if (diff > 0) {
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * @param guidBytes
	 * @param offset
	 * @param guid
	 * @return
	 */
	public static final boolean readEquals(final byte[] guidBytes, final int offset, final Guid guid) {
		if (guidBytes == null) {
			return guid == Guid.GUID_NULL;
		}
		final byte[] bytes1 = guid.bytes;
		for (int i = bytes1.length, source = 0; i > 0; source++, i--) {
			if (bytes1[source] != guidBytes[offset + source]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param guidBytes
	 * @return guid
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final Guid readGuid(final byte[] guidBytes) throws IllegalArgumentException, NullPointerException {
		if (guidBytes == null || guidBytes.length == 0) {
			return Guid.GUID_NULL;
		}
		final int ordinal = guidBytes[0] & 0xFF;
		if (ordinal == 0) {
			return Guid.GUID_NULL;
		}
		final GuidType type = Guid.GUID_TYPES_FOR_TYPE_BYTE[ordinal];
		if (type.requiresSubByte()) {
			final int byte2 = guidBytes[1] & 0xFF;
			final int length = type.getLeastSize( byte2 );
			final byte[] bytes = new byte[length + 2];
			System.arraycopy( guidBytes, 0, bytes, 0, length + 2 );
			return new Guid( bytes );
		}
		final int length = type.getLeastSize( 0 );
		if (length > 0) {
			final byte[] bytes = new byte[length + 1];
			System.arraycopy( guidBytes, 0, bytes, 0, length + 1 );
			return new Guid( bytes );
		}
		final Guid simple = Guid.GUID_SIMPLE_FOR_TYPE_BYTE[ordinal];
		if (simple != null) {
			return simple;
		}
		final byte[] bytes = { (byte) ordinal };
		return new Guid( bytes );
	}
	
	/**
	 * @param guidBytes
	 * @param offset
	 * @return guid
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final Guid readGuid(final byte[] guidBytes, final int offset) throws IllegalArgumentException,
			NullPointerException {
		if (guidBytes == null || guidBytes.length <= offset + 0) {
			return Guid.GUID_NULL;
		}
		final int ordinal = guidBytes[offset + 0] & 0xFF;
		if (ordinal == 0) {
			return Guid.GUID_NULL;
		}
		final GuidType type = Guid.GUID_TYPES_FOR_TYPE_BYTE[ordinal];
		if (type.requiresSubByte()) {
			final int byte2 = guidBytes[offset + 1] & 0xFF;
			final int length = type.getLeastSize( byte2 );
			final byte[] bytes = new byte[length + 2];
			if (length > 0) {
				System.arraycopy( guidBytes, offset + 0, bytes, 0, length + 2 );
			} else {
				bytes[0] = (byte) ordinal;
				bytes[1] = (byte) byte2;
			}
			return new Guid( bytes );
		}
		final int length = type.getLeastSize( 0 );
		if (length > 0) {
			final byte[] bytes = new byte[length + 1];
			System.arraycopy( guidBytes, offset + 0, bytes, 0, length + 1 );
			return new Guid( bytes );
		}
		final Guid simple = Guid.GUID_SIMPLE_FOR_TYPE_BYTE[ordinal];
		if (simple != null) {
			return simple;
		}
		final byte[] bytes = { (byte) ordinal };
		return new Guid( bytes );
	}
	
	/**
	 * Reads guid from input stream
	 * 
	 * @param input
	 * @return guid
	 * @throws IOException
	 */
	public static final Guid readGuid(final DataInput input) throws IOException {
		final int firstByte = input.readByte();
		if (firstByte == 0) {
			return Guid.GUID_NULL;
		}
		final int ordinal = firstByte & 0xFF;
		if (ordinal == 0) {
			return Guid.GUID_NULL;
		}
		final GuidType type = Guid.GUID_TYPES_FOR_TYPE_BYTE[ordinal];
		if (type.requiresSubByte()) {
			final int byte2 = input.readByte() & 0xFF;
			final int length = type.getLeastSize( byte2 );
			final byte[] bytes = new byte[length + 2];
			bytes[0] = (byte) ordinal;
			bytes[1] = (byte) byte2;
			if (length > 0) {
				input.readFully( bytes, 2, length );
			}
			return new Guid( bytes );
		}
		final int length = type.getLeastSize( 0 );
		if (length > 0) {
			final byte[] bytes = new byte[length + 1];
			bytes[0] = (byte) ordinal;
			input.readFully( bytes, 1, length );
			return new Guid( bytes );
		}
		final Guid simple = Guid.GUID_SIMPLE_FOR_TYPE_BYTE[ordinal];
		if (simple != null) {
			return simple;
		}
		final byte[] bytes = { (byte) ordinal };
		return new Guid( bytes );
	}
	
	/**
	 * Reads guid from input stream
	 * 
	 * @param input
	 * @return guid
	 * @throws IOException
	 */
	public static final Guid readGuid(final InputStream input) throws IOException {
		final int firstByte = input.read();
		if (firstByte == 0) {
			return Guid.GUID_NULL;
		}
		if (firstByte == -1) {
			throw new IOException( "unexpected end of stream!" );
		}
		final int ordinal = firstByte & 0xFF;
		if (ordinal == 0) {
			return Guid.GUID_NULL;
		}
		final GuidType type = Guid.GUID_TYPES_FOR_TYPE_BYTE[ordinal];
		if (type.requiresSubByte()) {
			final int secondByte = input.read();
			if (secondByte == -1) {
				throw new IOException( "unexpected end of stream!" );
			}
			final int byte2 = secondByte & 0xFF;
			final int length = type.getLeastSize( byte2 );
			final byte[] bytes = new byte[length + 2];
			bytes[0] = (byte) ordinal;
			bytes[1] = (byte) byte2;
			for (int index = 2, left = length; left > 0;) {
				final int read = input.read( bytes, index, left );
				if (read <= 0) {
					throw new IOException( "unexpected end of stream!" );
				}
				left -= read;
				index += read;
			}
			return new Guid( bytes );
		}
		final int length = type.getLeastSize( 0 );
		if (length > 0) {
			final byte[] bytes = new byte[length + 1];
			bytes[0] = (byte) ordinal;
			for (int index = 1, left = length; left > 0;) {
				final int read = input.read( bytes, index, left );
				if (read <= 0) {
					throw new IOException( "unexpected end of stream!" );
				}
				left -= read;
				index += read;
			}
			return new Guid( bytes );
		}
		final Guid simple = Guid.GUID_SIMPLE_FOR_TYPE_BYTE[ordinal];
		if (simple != null) {
			return simple;
		}
		final byte[] bytes = { (byte) ordinal };
		return new Guid( bytes );
	}
	
	/**
	 * @param guidBytes
	 * @param offset
	 * @return byte count or -1
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final int readGuidByteCount(final byte[] guidBytes, final int offset)
			throws IllegalArgumentException, NullPointerException {
		if (guidBytes == null || guidBytes.length <= offset + 0) {
			return 0;
		}
		final int ordinal = guidBytes[offset + 0] & 0xFF;
		if (ordinal == 0) {
			return 1;
		}
		final GuidType type = Guid.GUID_TYPES_FOR_TYPE_BYTE[ordinal];
		if (type.requiresSubByte()) {
			final int byte2 = guidBytes[offset + 1] & 0xFF;
			return type.getLeastSize( byte2 ) + 2;
		}
		return type.getLeastSize( 0 ) + 1;
	}
	
	/**
	 * @param guidBytes
	 * @param offset
	 * @return hashCode for GUID stored in bytes
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final int readGuidHashCode(final byte[] guidBytes, final int offset) throws IllegalArgumentException,
			NullPointerException {
		if (guidBytes == null || guidBytes.length <= offset + 0) {
			// NULL GUID
			return 0;
		}
		final int ordinal = guidBytes[offset + 0] & 0xFF;
		if (ordinal == 0) {
			// NULL GUID
			return 0;
		}
		final GuidType type = Guid.GUID_TYPES_FOR_TYPE_BYTE[ordinal];
		if (type.requiresSubByte()) {
			final int byte2 = guidBytes[offset + 1] & 0xFF;
			return Guid.hashCode( guidBytes, offset, type.getLeastSize( byte2 ) + 2 );
		}
		return Guid.hashCode( guidBytes, offset, type.getLeastSize( 0 ) + 1 );
	}
	
	/**
	 * @param guidBytes
	 * @param offset
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 */
	public static final boolean readGuidIsInline(final byte[] guidBytes, final int offset)
			throws IllegalArgumentException, NullPointerException {
		if (guidBytes == null || guidBytes.length <= offset + 0) {
			// NULL GUID
			return true;
		}
		final int ordinal = guidBytes[offset + 0] & 0xFF;
		if (ordinal == 0) {
			// NULL GUID
			return true;
		}
		return Guid.GUID_TYPES_FOR_TYPE_BYTE[ordinal].isInline();
	}
	
	/**
	 * @param guid
	 * @param output
	 * @param offset
	 * @return amount bytes written
	 * @throws IllegalArgumentException
	 *             when any argument equals to null
	 */
	public static final int writeGuid(final Guid guid, final byte[] output, final int offset)
			throws IllegalArgumentException {
		if (output == null) {
			throw new IllegalArgumentException( "output == null" );
		}
		if (guid == null) {
			output[offset] = 0;
			return 1;
		}
		final GuidType type = guid.getType();
		if (type.requiresSubByte()) {
			final int byte2 = guid.bytes[1] & 0xFF;
			final int length = type.getLeastSize( byte2 ) + 2;
			System.arraycopy( guid.bytes, 0, output, offset, length );
			return length;
		}
		final int length = type.getLeastSize( 0 ) + 1;
		if (guid.bytes.length != length) {
			throw new RuntimeException( guid.getType() + ", " + guid.bytes.length + ", " + length );
		}
		System.arraycopy( guid.bytes, 0, output, offset, length );
		return length;
	}
	
	/**
	 * @param guid
	 * @param output
	 * @throws IllegalArgumentException
	 *             when any argument equals to null
	 * @throws IOException
	 */
	public static final void writeGuid(final Guid guid, final DataOutput output) throws IllegalArgumentException,
			IOException {
		if (output == null) {
			throw new IllegalArgumentException( "output == null" );
		}
		if (guid == null) {
			output.write( 0 );
			return;
		}
		final GuidType type = guid.getType();
		if (type.requiresSubByte()) {
			final int byte2 = guid.bytes[1] & 0xFF;
			final int length = type.getLeastSize( byte2 );
			if (guid.bytes.length != length + 2) {
				throw new RuntimeException( guid.getType() + ", " + guid.bytes.length + ", " + (length + 2) );
			}
			output.write( guid.bytes, 0, length + 2 );
			return;
		}
		final int length = type.getLeastSize( 0 );
		if (guid.bytes.length != length + 1) {
			throw new RuntimeException( guid.getType() + ", " + guid.bytes.length + ", " + (length + 1) );
		}
		output.write( guid.bytes, 0, length + 1 );
	}
	
	/**
	 * @param guid
	 * @param output
	 * @throws IllegalArgumentException
	 *             when any argument equals to null
	 * @throws IOException
	 */
	public static final void writeGuid(final Guid guid, final OutputStream output) throws IllegalArgumentException,
			IOException {
		if (output == null) {
			throw new IllegalArgumentException( "output == null" );
		}
		if (guid == null) {
			output.write( 0 );
			return;
		}
		final GuidType type = guid.getType();
		if (type.requiresSubByte()) {
			final int byte2 = guid.bytes[1] & 0xFF;
			final int length = type.getLeastSize( byte2 );
			if (guid.bytes.length != length + 2) {
				throw new RuntimeException( guid.getType() + ", " + guid.bytes.length + ", " + (length + 2) );
			}
			output.write( guid.bytes, 0, length + 2 );
			return;
		}
		final int length = type.getLeastSize( 0 );
		if (guid.bytes.length != length + 1) {
			throw new RuntimeException( guid.getType() + ", " + guid.bytes.length + ", " + (length + 1) );
		}
		output.write( guid.bytes, 0, length + 1 );
	}
	
	/**
	 * @param guid
	 * @param output
	 * @throws IllegalArgumentException
	 *             when any argument equals to null
	 */
	public static final void writeGuid(final Guid guid, final TransferTarget output) throws IllegalArgumentException {
		if (output == null) {
			throw new IllegalArgumentException( "output == null" );
		}
		if (guid == null) {
			output.absorb( 0 );
			return;
		}
		final GuidType type = guid.getType();
		if (type.requiresSubByte()) {
			final int byte2 = guid.bytes[1] & 0xFF;
			final int length = type.getLeastSize( byte2 );
			if (guid.bytes.length != length + 2) {
				throw new RuntimeException( guid.getType() + ", " + guid.bytes.length + ", " + (length + 2) );
			}
			output.absorbArray( guid.bytes, 0, length + 2 );
			return;
		}
		final int length = type.getLeastSize( 0 );
		if (guid.bytes.length != length + 1) {
			throw new RuntimeException( guid.getType() + ", " + guid.bytes.length + ", " + (length + 1) );
		}
		output.absorbArray( guid.bytes, 0, length + 1 );
	}
	
	/**
	 * @param guid
	 * @return
	 */
	public static int writeGuidByteCount(final Guid guid) {
		if (guid == null) {
			return 0;
		}
		final GuidType type = guid.getType();
		if (type.requiresSubByte()) {
			return type.getLeastSize( guid.bytes[1] & 0xFF ) + 2;
		}
		return type.getLeastSize( 0 ) + 1;
	}
	
	private final byte[]	bytes;
	
	Guid(final byte[] bytes) {
		this.bytes = bytes;
	}
	
	@Override
	public int compareTo(final Object o) {
		if (o == this) {
			return 0;
		}
		if (o == null || o == Guid.GUID_NULL || o == Guid.GUID_UNDEFINED) {
			return 1;
		}
		if (o instanceof Guid) {
			final byte[] bytes1 = this.bytes;
			final byte[] bytes2 = ((Guid) o).bytes;
			int source = 0;
			for (int i = Math.min( bytes1.length, bytes2.length ); i > 0; i--, source++) {
				final int diff = (bytes1[source] & 0xFF) - (bytes2[source] & 0xFF);
				if (diff == 0) {
					continue;
				}
				return diff < 0
						? -1
						: 1;
			}
			if (source < bytes1.length) {
				return 1;
			}
			if (source < bytes2.length) {
				return -1;
			}
			return 0;
		}
		return -1;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj == Guid.GUID_NULL || obj == Guid.GUID_UNDEFINED) {
			return false;
		}
		if (obj instanceof Guid) {
			final Guid guid = (Guid) obj;
			final byte[] bytes1 = this.bytes;
			final byte[] bytes2 = guid.bytes;
			final int length = bytes1.length;
			if (length != bytes2.length) {
				return false;
			}
			if (length == 0) {
				return true;
			}
			if (bytes1[0] != bytes2[0]) {
				return false;
			}
			for (int i = length - 1; i > 0; i--) {
				if (bytes1[i] != bytes2[i]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Returns binary content length for this GUID. If isBinary method returns
	 * TRUE - getBinaryLength will always succeed.
	 * 
	 * @return object
	 * @throws IllegalAccessError
	 *             only and always when GUID is not in-line GUID
	 */
	public long getBinaryLength() throws IllegalAccessError {
		return this.getType().getBinaryLength( this.bytes );
	}
	
	/**
	 * Returns in-line value of this GUID as java object if available. If
	 * isInline method returns TRUE - getInlineValue will always succeed.
	 * 
	 * @return object
	 * @throws IllegalAccessError
	 *             only and always when GUID is not in-line GUID
	 */
	public BaseObject<?> getInlineBaseValue() throws IllegalAccessError {
		return this.getType().getBaseObject( this.bytes );
	}
	
	/**
	 * Returns in-line value of this GUID as java object if available. If
	 * isInline method returns TRUE - getInlineValue will always succeed.
	 * 
	 * @return object
	 * @throws IllegalAccessError
	 *             only and always when GUID is not in-line GUID
	 */
	public Object getInlineValue() throws IllegalAccessError {
		return this.getType().getJavaObject( this.bytes );
	}
	
	/**
	 * Returns partitioning parameter for this GUID. It's a value between 0 and
	 * 1 indication position on virtual circle.
	 * 
	 * @return 0..1 value
	 */
	public double getPartitioningParameter() {
		return (this.hashCode() - Guid.PARTITIONING_SUBSTRACT) / Guid.PARTITIONING_DIVIDE;
	}
	
	/**
	 * @return type
	 */
	public GuidType getType() {
		return this.bytes == null
				? GuidType.NULL
				: Guid.GUID_TYPES_FOR_TYPE_BYTE[this.bytes[0] & 0xFF];
	}
	
	/**
	 * Hashcode is not cached to make object smaller (objects aligned by 8 byte
	 * boundary)
	 */
	@Override
	public int hashCode() {
		return Guid.hashCode( this.bytes, 0, this.bytes.length );
	}
	
	/**
	 * Indicates this given GUID is a binary GUID and you can use
	 * getBinaryLength() method to get binary length. This normally applies to
	 * GUID_TYPE_BYTES_XXX and GUID_TYPE_CRC_XXX types.
	 * 
	 * @return
	 */
	public boolean isBinary() {
		return this.getType().isBinary();
	}
	
	/**
	 * Indicates that given GUID is a collection GUID.
	 * 
	 * @return
	 */
	public boolean isCollection() {
		return this.getType().isCollection();
	}
	
	/**
	 * Indicates this given GUID is not an 'inline' GUID and you can use
	 * getBinaryLength() method to get binary length. This normally applies to
	 * GUID_TYPE_BYTES_XXX and GUID_TYPE_CRC_XXX types.
	 * 
	 * @return
	 */
	public boolean isExternal() {
		return !this.getType().isInline();
	}
	
	/**
	 * Indicates that value is a 184-bit randomness GUID.
	 * 
	 * @return boolean
	 */
	public boolean isGuid184() {
		return this.getType() == GuidType.GUID184;
	}
	
	/**
	 * Indicates that value is a 384-bit randomness GUID.
	 * 
	 * @return boolean
	 */
	public boolean isGuid384() {
		return this.getType() == GuidType.GUID384;
	}
	
	/**
	 * Indicates that value is either 184-bit or 384-bit randomness GUID.
	 * 
	 * @return boolean
	 */
	public boolean isGuidXXX() {
		return this.getType() == GuidType.GUID384 || this.getType() == GuidType.GUID184;
	}
	
	/**
	 * Indicates that value associated with this GUID is contained within this
	 * GUID and can be accessed by getInlineValue() method.
	 * 
	 * @return boolean
	 */
	public boolean isInline() {
		return this.getType().isInline();
	}
	
	/**
	 * Indicates that value associated with this GUID is contained within this
	 * GUID, is instance of java.lang.Number and can be accessed by
	 * getInlineValue() method.
	 * 
	 * @return boolean
	 */
	public boolean isInlineNumber() {
		return this.getType().isNumber();
	}
	
	/**
	 * Indicates that value associated with this GUID is contained within this
	 * GUID, is instance of java.lang.Number, has integer value
	 * [Long.MIN_VALUE..Long.MAX_VALUE] and can be accessed by getInlineValue()
	 * method.
	 * 
	 * @return boolean
	 */
	public boolean isInlineNumberInteger() {
		return this.getType().isNumberInteger();
	}
	
	/**
	 * Indicates that value associated with this GUID is contained within this
	 * GUID, is instance of java.lang.String and can be accessed by
	 * getInlineValue() method.
	 * 
	 * @return boolean
	 */
	public boolean isInlineString() {
		return this.getType().isString();
	}
	
	/**
	 * Indicates this this GUID is valid. This GUID should be of valid type with
	 * data valid for this type, and creation of GUID for this data (in case of
	 * inline type) should produce same or equal GUID.
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		final GuidType type = this.getType();
		if (type == GuidType.INVALID) {
			return false;
		}
		if (!type.isInline()) {
			return true;
		}
		final Guid other = Guid.forUnknown( this.getInlineValue() );
		return this == other || this.equals( other );
	}
	
	/**
	 * To keep simple guid instances unique
	 * 
	 * @return
	 * @throws ObjectStreamException
	 */
	Object readResolve() {
		final byte[] bytes = this.bytes;
		if (bytes == null || bytes.length == 0) {
			return Guid.GUID_NULL;
		}
		final Guid simple = Guid.GUID_SIMPLE_FOR_TYPE_BYTE[bytes[0] & 0xFF];
		if (simple != null) {
			return simple;
		}
		return this;
	}
	
	/**
	 * base64
	 * 
	 * @return string
	 */
	public String toBase64() {
		final byte[] bytes = this.bytes;
		if (bytes == null || bytes.length == 0) {
			return "";
		}
		final char[] result = new char[(int) Math.ceil( bytes.length / 6.0 * 8 )];
		int target = 0;
		int source = 0;
		for (int left = bytes.length;;) {
			final byte item1 = bytes[source++];
			result[target++] = Guid.XLAT64[(item1 & 0x00FC) >> 2];
			if (--left == 0) {
				result[target++] = Guid.XLAT64[((item1 & 0x0003) << 4)];
				break;
			}
			final byte item2 = bytes[source++];
			result[target++] = Guid.XLAT64[((item1 & 0x0003) << 4) + ((item2 & 0x00F0) >> 4)];
			if (--left == 0) {
				result[target++] = Guid.XLAT64[((item2 & 0x000F) << 2)];
				break;
			}
			final byte item3 = bytes[source++];
			result[target++] = Guid.XLAT64[((item2 & 0x000F) << 2) + ((item3 & 0x00C0) >> 6)];
			result[target++] = Guid.XLAT64[(item3 & 0x003F)];
			if (--left == 0) {
				break;
			}
		}
		return new String( result, 0, target );
	}
	
	/**
	 * @return
	 */
	public byte[] toBytes() {
		return this.bytes.clone();
	}
	
	/**
	 * base16
	 * 
	 * @return string
	 */
	public String toHex() {
		final byte[] bytes = this.bytes;
		if (bytes == null || bytes.length == 0) {
			return "";
		}
		final char[] result = new char[bytes.length << 1];
		int target = 0;
		int source = 0;
		for (int i = bytes.length - 1; i >= 0; i--) {
			final byte current = bytes[source++];
			result[target++] = Guid.XLAT16[(current & 0xF0) >> 4];
			result[target++] = Guid.XLAT16[(current & 0x0F) >> 0];
		}
		return new String( result, 0, target );
	}
	
	/**
	 * @return
	 */
	public InputStream toInputStream() {
		return new GuidInputStream( this.bytes );
	}
	
	/**
	 * base32
	 * 
	 * @return string
	 */
	
	public String toName() {
		final byte[] bytes = this.bytes;
		if (bytes == null || bytes.length == 0) {
			return "";
		}
		final char[] result = new char[(int) Math.ceil( bytes.length / 5.0 * 8 )];
		int target = 0;
		int source = 0;
		for (int left = bytes.length;;) {
			final byte item1 = bytes[source++];
			result[target++] = Guid.XLAT32[(item1 & 0x00F8) >> 3];
			if (--left == 0) {
				result[target++] = Guid.XLAT32[((item1 & 0x0007) << 2)];
				break;
			}
			final byte item2 = bytes[source++];
			result[target++] = Guid.XLAT32[((item1 & 0x0007) << 2) + ((item2 & 0x00c0) >> 6)];
			result[target++] = Guid.XLAT32[(item2 & 0x0003e) >> 1];
			if (--left == 0) {
				result[target++] = Guid.XLAT32[((item2 & 0x0001) << 4)];
				break;
			}
			final byte item3 = bytes[source++];
			result[target++] = Guid.XLAT32[((item2 & 0x0001) << 4) + ((item3 & 0x00f0) >> 4)];
			if (--left == 0) {
				result[target++] = Guid.XLAT32[((item3 & 0x000f) << 1)];
				break;
			}
			final byte item4 = bytes[source++];
			result[target++] = Guid.XLAT32[((item3 & 0x000f) << 1) + ((item4 & 0x0080) >> 7)];
			result[target++] = Guid.XLAT32[(item4 & 0x007c) >> 2];
			if (--left == 0) {
				result[target++] = Guid.XLAT32[((item4 & 0x0003) << 3)];
				break;
			}
			final byte item5 = bytes[source++];
			result[target++] = Guid.XLAT32[((item4 & 0x0003) << 3) + ((item5 & 0x00e0) >> 5)];
			result[target++] = Guid.XLAT32[item5 & 0x001f];
			if (--left == 0) {
				break;
			}
		}
		return new String( result, 0, target );
	}
	
	/**
	 * 
	 * @return this.toBase64();
	 */
	@Override
	public String toString() {
		return this.toBase64();
	}
}
