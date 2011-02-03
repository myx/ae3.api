/**
 * 
 */
package ru.myx.ae3.know;

import java.util.Date;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitiveBoolean;
import ru.myx.ae3.base.BasePrimitiveNull;
import ru.myx.ae3.base.BasePrimitiveNumber;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.base.BasePrimitiveUndefined;
import ru.myx.ae3.binary.TransferCopier;
import ru.myx.ae3.binary.WrapCopier;

/**
 * @author myx
 * 
 *         Supports following data:
 * 
 *         0) INVALID/UNDEFINED value (Read)
 * 
 *         1) NULL value (Read/Write)
 * 
 *         2) 64bit integer value (Read/Write)
 * 
 *         3) 64bit floating value (Read/Write)
 * 
 *         4) 64bit integer date value (Read/Write)
 * 
 *         5) 64bit floating date value (Read/Write)
 * 
 *         6) Strings, EMPTY, 1-54 for ASCII, 1-48 chars length for 8bit, 1-24
 *         chars length for UNICODE (Read/Write)
 * 
 *         7) Bytes, 0-48 bytes
 * 
 *         8) External (large) data by checksum (Read/Write)
 * 
 *         9) External data by reference (Read/Write)
 * 
 *         10) Special structure/control data (internal)
 */
enum GuidType {
	/**
	 * NULL value
	 */
	NULL {
		@Override
		final BasePrimitiveNull getBaseObject(final byte[] bytes) {
			return BaseObject.NULL;
		}
		
		@Override
		final Object getJavaObject(final byte[] bytes) {
			return null;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
	},
	/**
	 * Contains value of boolean FALSE
	 */
	BOOLEAN_FALSE {
		@Override
		final BasePrimitiveBoolean getBaseObject(final byte[] bytes) {
			return BaseObject.FALSE;
		}
		
		@Override
		final Boolean getJavaObject(final byte[] bytes) {
			return Boolean.FALSE;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
	},
	/**
	 * Contains value of NotANumber
	 */
	NUMBER_NAN {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return BasePrimitiveNumber.NAN;
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return BasePrimitiveNumber.NAN.baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
		
		/**
		 * It's funny but it is still a number %)
		 */
		@Override
		public final boolean isNumber() {
			return true;
		}
	},
	/**
	 * Contains value of negative infinity
	 */
	NUMBER_NEGATIVE_INFINITY {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return BasePrimitiveNumber.NINF;
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return BasePrimitiveNumber.NINF.baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_64BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		/**
		 * 64bit long doesn't equal to any double value! And anyway never cached
		 */
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return new Long( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return ((long) bytes[1] << 56)
					+ ((long) (bytes[2] & 255) << 48)
					+ ((long) (bytes[3] & 255) << 40)
					+ ((long) (bytes[4] & 255) << 32)
					+ ((long) (bytes[5] & 255) << 24)
					+ ((bytes[6] & 255) << 16)
					+ ((bytes[7] & 255) << 8)
					+ ((bytes[8] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 8;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_56BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		/**
		 * 56bit long doesn't equal to any double value! And anyway never cached
		 */
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return new Long( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return 0xFF00000000000000L
					+ ((long) (bytes[1] & 255) << 48)
					+ ((long) (bytes[2] & 255) << 40)
					+ ((long) (bytes[3] & 255) << 32)
					+ ((long) (bytes[4] & 255) << 24)
					+ ((bytes[5] & 255) << 16)
					+ ((bytes[6] & 255) << 8)
					+ ((bytes[7] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 7;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_48BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		/**
		 * Anyway never cached
		 */
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return new Long( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return 0xFFFF000000000000L
					+ ((long) (bytes[1] & 255) << 40)
					+ ((long) (bytes[2] & 255) << 32)
					+ ((long) (bytes[3] & 255) << 24)
					+ ((bytes[4] & 255) << 16)
					+ ((bytes[5] & 255) << 8)
					+ ((bytes[6] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 6;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_40BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		/**
		 * Anyway never cached
		 */
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return new Long( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return 0xFFFFFF0000000000L
					+ ((long) (bytes[1] & 255) << 32)
					+ ((long) (bytes[2] & 255) << 24)
					+ ((bytes[3] & 255) << 16)
					+ ((bytes[4] & 255) << 8)
					+ ((bytes[5] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 5;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_32BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return 0xFFFFFFFF00000000L
					+ ((long) (bytes[1] & 255) << 24)
					+ ((bytes[2] & 255) << 16)
					+ ((bytes[3] & 255) << 8)
					+ ((bytes[4] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 4;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_24BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return 0xFFFFFFFFFF000000L + ((bytes[1] & 255) << 16) + ((bytes[2] & 255) << 8) + ((bytes[3] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 3;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_16BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return 0xFFFFFFFFFFFF0000L + ((bytes[1] & 255) << 8) + ((bytes[2] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 2;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_NEGATIVE_INT_8BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return 0xFFFFFFFFFFFFFF00L + (bytes[1] & 255);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 1;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * Contains value of -1,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
	 */
	NUMBER_CONSTANT_0BIT {
		@Override
		int count() {
			return 22;
		}
		
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (bytes[0] & 0xFF) - this.startIndex - 1 );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (bytes[0] & 0xFF) - this.startIndex - 1 ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return (bytes[0] & 0xFF) - this.startIndex - 1;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_8BIT {
		@Override
		int count() {
			return 16;
		}
		
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP8BIT + (bytes[1] & 255) + 0x100L * ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 1;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_16BIT {
		@Override
		int count() {
			return 16;
		}
		
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP16BIT
					+ ((bytes[1] & 255) << 8)
					+ ((bytes[2] & 255) << 0)
					+ 0x10000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 2;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_24BIT {
		@Override
		int count() {
			return 8;
		}
		
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP24BIT
					+ ((bytes[1] & 255) << 16)
					+ ((bytes[2] & 255) << 8)
					+ ((bytes[3] & 255) << 0)
					+ 0x1000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 3;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_32BIT {
		@Override
		int count() {
			return 4;
		}
		
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forInteger( (int) this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP32BIT
					+ ((long) (bytes[1] & 255) << 24)
					+ ((bytes[2] & 255) << 16)
					+ ((bytes[3] & 255) << 8)
					+ ((bytes[4] & 255) << 0)
					+ 0x100000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 4;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_40BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP40BIT
					+ ((long) (bytes[1] & 255) << 32)
					+ ((long) (bytes[2] & 255) << 24)
					+ ((bytes[3] & 255) << 16)
					+ ((bytes[4] & 255) << 8)
					+ ((bytes[5] & 255) << 0)
					+ 0x10000000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 5;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_48BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) ).baseValue();
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP48BIT
					+ ((long) (bytes[1] & 255) << 40)
					+ ((long) (bytes[2] & 255) << 32)
					+ ((long) (bytes[3] & 255) << 24)
					+ ((bytes[4] & 255) << 16)
					+ ((bytes[5] & 255) << 8)
					+ ((bytes[6] & 255) << 0)
					+ 0x1000000000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 6;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_56BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		/**
		 * 56bit long doesn't equal to any double value! And anyway never
		 * cached.
		 */
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return new Long( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP56BIT
					+ ((long) (bytes[1] & 255) << 48)
					+ ((long) (bytes[2] & 255) << 40)
					+ ((long) (bytes[3] & 255) << 32)
					+ ((long) (bytes[4] & 255) << 24)
					+ ((bytes[5] & 255) << 16)
					+ ((bytes[6] & 255) << 8)
					+ ((bytes[7] & 255) << 0)
					+ 0x100000000000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 7;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_INT_64BIT {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forLong( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		/**
		 * 64bit long doesn't equal to any double value! And anyway never
		 * cached.
		 */
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return new Long( this.getJavaPrimitiveNumberInteger( bytes ) );
		}
		
		@Override
		final long getJavaPrimitiveNumberInteger(final byte[] bytes) {
			return GuidType.startINP64BIT
					+ ((long) bytes[1] << 56)
					+ ((long) (bytes[2] & 255) << 48)
					+ ((long) (bytes[3] & 255) << 40)
					+ ((long) (bytes[4] & 255) << 32)
					+ ((long) (bytes[5] & 255) << 24)
					+ ((bytes[6] & 255) << 16)
					+ ((bytes[7] & 255) << 8)
					+ ((bytes[8] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 8;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
		
		@Override
		public final boolean isNumberInteger() {
			return true;
		}
	},
	/**
	 * as negative to be comparable!
	 */
	NUMBER_NEGATIVE_NOT_INTEGER {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forDouble( Double.longBitsToDouble( 0xFFFFFFFFFFFFFFFFL
					^ ((long) bytes[1] << 56)
					+ ((long) (bytes[2] & 255) << 48)
					+ ((long) (bytes[3] & 255) << 40)
					+ ((long) (bytes[4] & 255) << 32)
					+ ((long) (bytes[5] & 255) << 24)
					+ ((bytes[6] & 255) << 16)
					+ ((bytes[7] & 255) << 8)
					+ ((bytes[8] & 255) << 0) ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return this.getBaseObject( bytes ).baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 8;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
	},
	/**
	 * 
	 */
	NUMBER_POSITIVE_NOT_INTEGER {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return Base.forDouble( Double.longBitsToDouble( ((long) bytes[1] << 56)
					+ ((long) (bytes[2] & 255) << 48)
					+ ((long) (bytes[3] & 255) << 40)
					+ ((long) (bytes[4] & 255) << 32)
					+ ((long) (bytes[5] & 255) << 24)
					+ ((bytes[6] & 255) << 16)
					+ ((bytes[7] & 255) << 8)
					+ ((bytes[8] & 255) << 0) ) );
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return this.getBaseObject( bytes ).baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 8;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
	},
	/**
	 * Contains value of positive infinity
	 */
	NUMBER_POSITIVE_INFINITY {
		@Override
		final BasePrimitiveNumber getBaseObject(final byte[] bytes) {
			return BasePrimitiveNumber.PINF;
		}
		
		@Override
		final Number getJavaObject(final byte[] bytes) {
			return BasePrimitiveNumber.PINF.baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
		
		@Override
		public final boolean isNumber() {
			return true;
		}
	},
	/**
	 * 
	 */
	DATE_NEGATIVE_INT_64BIT {
		@Override
		final BaseObject<Date> getBaseObject(final byte[] bytes) {
			return Base.forDateMillis( (((long) bytes[1] << 56)
					+ ((long) (bytes[2] & 255) << 48)
					+ ((long) (bytes[3] & 255) << 40)
					+ ((long) (bytes[4] & 255) << 32)
					+ ((long) (bytes[5] & 255) << 24)
					+ ((bytes[6] & 255) << 16)
					+ ((bytes[7] & 255) << 8) + ((bytes[8] & 255) << 0)) );
		}
		
		@Override
		final Date getJavaObject(final byte[] bytes) {
			return this.getBaseObject( bytes ).baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 8;
		}
	},
	/**
	 * Contains date 0 value
	 */
	DATE_ZERO {
		private final BaseObject<Date>	BASE_ZERO_DATE	= Base.forDateMillis( 0L );
		
		private final Date				JAVA_ZERO_DATE	= this.BASE_ZERO_DATE.baseValue();
		
		@Override
		final BaseObject<Date> getBaseObject(final byte[] bytes) {
			return this.BASE_ZERO_DATE;
		}
		
		@Override
		final Date getJavaObject(final byte[] bytes) {
			return this.JAVA_ZERO_DATE;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
	},
	/**
	 * 
	 */
	DATE_POSITIVE_INT_40BIT { // till Nov 2004, Sep 2039, Jul 2074
		@Override
		int count() {
			return 3;
		}
		
		@Override
		final BaseObject<Date> getBaseObject(final byte[] bytes) {
			final long value = GuidType.startDATE40BIT
					+ ((long) (bytes[1] & 255) << 32)
					+ ((long) (bytes[2] & 255) << 24)
					+ ((bytes[3] & 255) << 16)
					+ ((bytes[4] & 255) << 8)
					+ ((bytes[5] & 255) << 0)
					+ 0x10000000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
			return Base.forDateMillis( value );
		}
		
		@Override
		final Date getJavaObject(final byte[] bytes) {
			return this.getBaseObject( bytes ).baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 5;
		}
	},
	/**
	 * 
	 */
	DATE_POSITIVE_INT_64BIT {
		@Override
		final BaseObject<Date> getBaseObject(final byte[] bytes) {
			final long value = GuidType.startDATE64BIT
					+ ((long) bytes[1] << 56)
					+ ((long) (bytes[2] & 255) << 48)
					+ ((long) (bytes[3] & 255) << 40)
					+ ((long) (bytes[4] & 255) << 32)
					+ ((long) (bytes[5] & 255) << 24)
					+ ((bytes[6] & 255) << 16)
					+ ((bytes[7] & 255) << 8)
					+ ((bytes[8] & 255) << 0);
			return Base.forDateMillis( value );
		}
		
		@Override
		final Date getJavaObject(final byte[] bytes) {
			return this.getBaseObject( bytes ).baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 8;
		}
	},
	/**
	 * Contains value of boolean TRUE
	 */
	BOOLEAN_TRUE {
		@Override
		final BasePrimitiveBoolean getBaseObject(final byte[] bytes) {
			return BaseObject.TRUE;
		}
		
		@Override
		final Boolean getJavaObject(final byte[] bytes) {
			return Boolean.TRUE;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
	},
	/**
	 * Contains value of an empty string
	 */
	STRING_EMPTY {
		
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return BasePrimitiveString.EMPTY;
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			return BasePrimitiveString.EMPTY.baseValue();
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
	},
	/**
	 * ASCII (7-bit) string of less than 54 characters.
	 * 
	 * Format: guid-type byte, then length of string in first byte.
	 */
	STRING_ASCII {
		
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			final byte length = bytes[1];
			final char[] buffer = new char[length];
			for (int src = 2, tgt = 0;; src += 7) {
				buffer[tgt++] = (char) (/* 00000000000000000000000000000 */(bytes[src + 0] & 0xFE) >> 1);
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (((bytes[src + 0] & 0x01) << 6) + ((bytes[src + 1] & 0xFC) >> 2));
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (((bytes[src + 1] & 0x03) << 5) + ((bytes[src + 2] & 0xF8) >> 3));
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (((bytes[src + 2] & 0x07) << 4) + ((bytes[src + 3] & 0xF0) >> 4));
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (((bytes[src + 3] & 0x0F) << 3) + ((bytes[src + 4] & 0xE0) >> 5));
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (((bytes[src + 4] & 0x1F) << 2) + ((bytes[src + 5] & 0xC0) >> 6));
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (((bytes[src + 5] & 0x3F) << 1) + ((bytes[src + 6] & 0x80) >> 7));
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (bytes[src + 6] & 0x7F /* 000000000000000000000000000000000 */);
				if (tgt == length) {
					break;
				}
			}
			return new String( buffer, 0, length );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return ((byte2 & 0xFF) * 7 + 7) / 8;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
		
		@Override
		boolean requiresSubByte() {
			return true;
		}
	},
	/**
	 * ASCII (7-bit) string of 54 characters.
	 * 
	 * Format: guid-type byte, then 48 bytes of ASCII string encoded in 7 bit
	 * for each character.
	 */
	STRING_ASCII54 {
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			final byte length = 54;
			final char[] buffer = new char[length];
			for (int src = 1, tgt = 0;; src += 7) {
				buffer[tgt++] = (char) (/* 00000000000000000000000000000 */(bytes[src + 0] & 0xFE) >> 1);
				buffer[tgt++] = (char) (((bytes[src + 0] & 0x01) << 6) + ((bytes[src + 1] & 0xFC) >> 2));
				buffer[tgt++] = (char) (((bytes[src + 1] & 0x03) << 5) + ((bytes[src + 2] & 0xF8) >> 3));
				buffer[tgt++] = (char) (((bytes[src + 2] & 0x07) << 4) + ((bytes[src + 3] & 0xF0) >> 4));
				buffer[tgt++] = (char) (((bytes[src + 3] & 0x0F) << 3) + ((bytes[src + 4] & 0xE0) >> 5));
				buffer[tgt++] = (char) (((bytes[src + 4] & 0x1F) << 2) + ((bytes[src + 5] & 0xC0) >> 6));
				if (tgt == length) {
					break;
				}
				buffer[tgt++] = (char) (((bytes[src + 5] & 0x3F) << 1) + ((bytes[src + 6] & 0x80) >> 7));
				buffer[tgt++] = (char) (bytes[src + 6] & 0x7F /* 000000000000000000000000000000000 */);
			}
			return new String( buffer, 0, 54 );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
	},
	/**
	 * 8bit string of less than 48 characters.
	 * 
	 * Format: guid-type byte, then length of string in first byte, length of
	 * string equals to length of data
	 */
	STRING_8BIT {
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			final byte length = bytes[1];
			// it's just faster for this trivial encoding
			final char[] buffer = new char[length];
			for (int i = length - 1, j = length + 1; i >= 0; i--, j--) {
				buffer[i] = (char) (bytes[j] & 0xFF);
			}
			return new String( buffer, 0, length );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return byte2 & 0xFF;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
		
		@Override
		boolean requiresSubByte() {
			return true;
		}
	},
	/**
	 * 8-bit string of 48 characters.
	 * 
	 * Format: guid-type byte, then 48 bytes of ASCII string.
	 */
	STRING_8BIT48 {
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			// it's just faster for this trivial encoding
			final char[] buffer = new char[48];
			for (int i = 48; i > 0; i--) {
				buffer[i - 1] = (char) (bytes[i] & 0xFF);
			}
			return new String( buffer, 0, 48 );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
	},
	/**
	 * UTF-16 string of less than 24 characters.
	 * 
	 * Format: guid-type byte, then length of string in first byte value.
	 */
	STRING_UNICODE {
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			final byte length = bytes[1];
			// it's just faster for this trivial encoding
			final char[] buffer = new char[length];
			for (int i = length - 1, j = (length << 1) + 1; i >= 0; i--) {
				buffer[i] = (char) ((bytes[j--] & 0xFF) + ((bytes[j--] & 0xFF) << 8));
			}
			return new String( buffer, 0, length );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return (byte2 & 0xFF) << 1;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
		
		@Override
		boolean requiresSubByte() {
			return true;
		}
	},
	/**
	 * UTF-16 string of 24 characters
	 */
	STRING_UNICODE24 {
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			// it's just faster for this trivial encoding
			final char[] buffer = new char[24];
			for (int i = 23, j = 48; i >= 0; i--) {
				buffer[i] = (char) ((bytes[j--] & 0xFF) + ((bytes[j--] & 0xFF) << 8));
			}
			return new String( buffer, 0, 24 );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
	},
	/**
	 * UTF8 string of less than 48 bytes.
	 * 
	 * Format: guid-type byte, then length of string in first byte, length of
	 * data
	 */
	STRING_UTF8 {
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			// it's just faster for this trivial encoding
			final int length = bytes[1] + 2;
			int chars = 0;
			// cannot be longer than bytes
			final char[] buffer = new char[length - 2];
			for (int pos = 2; pos < length;) {
				final int c = bytes[pos++] & 0xff;
				if ((c & 0x80) == 0) {
					/* 0xxxxxxx */
					buffer[chars++] = (char) c;
					continue;
				}
				if ((c & 0xE0) == 0xC0) {
					/* 110x xxxx 10xx xxxx */
					final int char2 = bytes[pos++] & 0xff;
					if ((char2 & 0xC0) != 0x80) {
						throw new IllegalArgumentException( "malformed input around byte " + pos );
					}
					buffer[chars++] = (char) ((c & 0x1F) << 6 | char2 & 0x3F);
					continue;
				}
				if ((c & 0xF0) == 0xE0) {
					/* 1110 xxxx 10xx xxxx 10xx xxxx */
					final int char2 = bytes[pos++] & 0xff;
					final int char3 = bytes[pos++] & 0xff;
					if ((char2 & 0xC0) != 0x80 || (char3 & 0xC0) != 0x80) {
						throw new IllegalArgumentException( "malformed input around byte " + (pos - 1) );
					}
					buffer[chars++] = (char) ((c & 0x0F) << 12 | (char2 & 0x3F) << 6 | (char3 & 0x3F) << 0);
					continue;
				}
				{
					/* 10xx xxxx, 1111 xxxx */
					throw new IllegalArgumentException( "malformed input around byte " + pos );
				}
			}
			return new String( buffer, 0, chars );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return byte2 & 0xFF;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
		
		@Override
		boolean requiresSubByte() {
			return true;
		}
	},
	/**
	 * UTF8 string of 48 bytes.
	 * 
	 * Format: guid-type byte, then 48 bytes of UTF8 string.
	 */
	STRING_UTF8_48 {
		@Override
		final BasePrimitiveString getBaseObject(final byte[] bytes) {
			return Base.forString( this.getJavaObject( bytes ) );
		}
		
		@Override
		final String getJavaObject(final byte[] bytes) {
			// it's just faster for this trivial encoding
			int chars = 0;
			// cannot be longer than bytes
			final char[] buffer = new char[48];
			for (int pos = 1; pos < 49;) {
				final int c = bytes[pos++] & 0xff;
				if ((c & 0x80) == 0) {
					/* 0xxxxxxx */
					buffer[chars++] = (char) c;
					continue;
				}
				if ((c & 0xE0) == 0xC0) {
					/* 110x xxxx 10xx xxxx */
					final int char2 = bytes[pos++] & 0xff;
					if ((char2 & 0xC0) != 0x80) {
						throw new IllegalArgumentException( "malformed input around byte " + pos );
					}
					buffer[chars++] = (char) ((c & 0x1F) << 6 | char2 & 0x3F);
					continue;
				}
				if ((c & 0xF0) == 0xE0) {
					/* 1110 xxxx 10xx xxxx 10xx xxxx */
					final int char2 = bytes[pos++] & 0xff;
					final int char3 = bytes[pos++] & 0xff;
					if ((char2 & 0xC0) != 0x80 || (char3 & 0xC0) != 0x80) {
						throw new IllegalArgumentException( "malformed input around byte " + (pos - 1) );
					}
					buffer[chars++] = (char) ((c & 0x0F) << 12 | (char2 & 0x3F) << 6 | (char3 & 0x3F) << 0);
					continue;
				}
				{
					/* 10xx xxxx, 1111 xxxx */
					throw new IllegalArgumentException( "malformed input around byte " + pos );
				}
			}
			return new String( buffer, 0, chars );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		public final boolean isString() {
			return true;
		}
	},
	/**
	 * Contains value of an empty string
	 */
	BYTES_EMPTY {
		@Override
		final BaseObject<TransferCopier> getBaseObject(final byte[] bytes) {
			return Base.forUnknown( TransferCopier.NUL_COPIER );
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return 0;
		}
		
		@Override
		final TransferCopier getJavaObject(final byte[] bytes) {
			return TransferCopier.NUL_COPIER;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
	},
	/**
	 * Byte array less than 48 bytes length. (length in first byte)
	 */
	BYTES {
		@Override
		final BaseObject<TransferCopier> getBaseObject(final byte[] bytes) {
			return Base.forUnknown( this.getJavaObject( bytes ) );
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return bytes[1] & 0xFF;
		}
		
		@Override
		final TransferCopier getJavaObject(final byte[] bytes) {
			return new WrapCopier( bytes, 2, bytes[1] & 0xFF );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return byte2 & 0xFF;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		boolean requiresSubByte() {
			return true;
		}
	},
	/**
	 * 48 bytes.
	 * 
	 * Format: guid-type byte, then 48 bytes.
	 */
	BYTES48 {
		@Override
		final BaseObject<TransferCopier> getBaseObject(final byte[] bytes) {
			return Base.forUnknown( this.getJavaObject( bytes ) );
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return 48;
		}
		
		@Override
		final TransferCopier getJavaObject(final byte[] bytes) {
			return new WrapCopier( bytes, 1, 48 );
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
	},
	/**
	 * For all immutable data binary with length stored as 1 byte,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_8BIT_SZ {
		@Override
		int count() {
			return 16;
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC8BIT + (bytes[1] & 0xFF) + 0x100L * ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For all immutable data binary with length stored as 2 bytes,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_16BIT_SZ {
		@Override
		int count() {
			return 16;
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC16BIT
					+ ((bytes[1] & 255) << 8)
					+ ((bytes[2] & 255) << 0)
					+ 0x10000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For all immutable data binary with length stored as 3 bytes,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_24BIT_SZ {
		@Override
		int count() {
			return 8;
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC24BIT
					+ ((bytes[1] & 255) << 16)
					+ ((bytes[2] & 255) << 8)
					+ ((bytes[3] & 255) << 0)
					+ 0x1000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For all immutable data binary with length stored as 4 bytes,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_32BIT_SZ {
		@Override
		int count() {
			return 4;
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC32BIT
					+ ((long) (bytes[1] & 255) << 24)
					+ ((bytes[2] & 255) << 16)
					+ ((bytes[3] & 255) << 8)
					+ ((bytes[4] & 255) << 0)
					+ 0x100000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For all immutable data binary with length stored as 5 bytes,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_40BIT_SZ {
		@Override
		int count() {
			return 2;
		}
		
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC40BIT
					+ ((long) (bytes[1] & 255) << 32)
					+ ((long) (bytes[2] & 255) << 24)
					+ ((bytes[3] & 255) << 16)
					+ ((bytes[4] & 255) << 8)
					+ ((bytes[5] & 255) << 0)
					+ 0x10000000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For all immutable data binary with length stored as 6 bytes,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_48BIT_SZ {
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC48BIT
					+ ((long) (bytes[1] & 255) << 40)
					+ ((long) (bytes[2] & 255) << 32)
					+ ((long) (bytes[3] & 255) << 24)
					+ ((bytes[4] & 255) << 16)
					+ ((bytes[5] & 255) << 8)
					+ ((bytes[6] & 255) << 0)
					+ 0x1000000000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For all immutable data binary with length stored as 7 bytes,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_56BIT_SZ {
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC56BIT
					+ ((long) (bytes[1] & 255) << 48)
					+ ((long) (bytes[2] & 255) << 40)
					+ ((long) (bytes[3] & 255) << 32)
					+ ((long) (bytes[4] & 255) << 24)
					+ ((bytes[5] & 255) << 16)
					+ ((bytes[6] & 255) << 8)
					+ ((bytes[7] & 255) << 0)
					+ 0x100000000000000L
					* ((bytes[0] & 0xFF) - this.startIndex);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For all immutable data binary with length stored as 8 bytes,
	 * whirlpool-based.
	 * 
	 * Format: guid-type byte, then 48 bytes of checksum.
	 */
	CRC384_64BIT_SZ {
		@Override
		long getBinaryLength(final byte[] bytes) {
			return GuidType.startCRC64BIT
					+ ((long) bytes[1] << 56)
					+ ((long) (bytes[2] & 255) << 48)
					+ ((long) (bytes[3] & 255) << 40)
					+ ((long) (bytes[4] & 255) << 32)
					+ ((long) (bytes[5] & 255) << 24)
					+ ((bytes[6] & 255) << 16)
					+ ((bytes[7] & 255) << 8)
					+ ((bytes[8] & 255) << 0);
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		boolean isBinary() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For objects. Kind of persistent object identifier.
	 * 
	 * In storage intended to contain CRC_XXX or GUID_XXX or primitive guid.
	 * 
	 * Format: guid-type byte, then 23 bytes of random data.
	 */
	GUID184 {
		@Override
		int count() {
			return 16;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 23;
		}
		
		@Override
		public final boolean isCollection() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * For objects. Kind of persistent object identifier.
	 * 
	 * In storage intended to contain CRC_XXX or GUID_XXX or primitive guid.
	 * 
	 * Format: guid-type byte, then 48 bytes of random data.
	 */
	GUID384 {
		@Override
		int count() {
			return 2;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		public final boolean isCollection() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * acm instance identity
	 */
	GUID_INSTANCE_IDENTITY {
		@Override
		int getLeastSize(final int byte2) {
			return 48;
		}
		
		@Override
		public final boolean isCollection() {
			return true;
		}
		
		@Override
		public final boolean isInline() {
			return false;
		}
	},
	/**
	 * UNDEFINED value
	 */
	UNDEFINED {
		@Override
		final BasePrimitiveUndefined getBaseObject(final byte[] bytes) {
			return BaseObject.UNDEFINED;
		}
		
		@Override
		final Object getJavaObject(final byte[] bytes) {
			return null;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
	},
	/**
	 * INVALID value - any invalid guid should take this guid type after been
	 * read from parameter, and should throw error if read from stream, just
	 * because stream will become corrupted since real guid length is unknown.
	 */
	INVALID {
		@Override
		final BasePrimitiveUndefined getBaseObject(final byte[] bytes) {
			return BaseObject.UNDEFINED;
		}
		
		@Override
		final Object getJavaObject(final byte[] bytes) {
			return null;
		}
		
		@Override
		int getLeastSize(final int byte2) {
			return 0;
		}
	},
	;
	
	static final long		startINP8BIT	= NUMBER_CONSTANT_0BIT.count() - 1;
	
	static final long		startINP16BIT	= GuidType.startINP8BIT + 0x100L * NUMBER_POSITIVE_INT_8BIT.count();
	
	static final long		startINP24BIT	= GuidType.startINP16BIT + 0x10000L * NUMBER_POSITIVE_INT_16BIT.count();
	
	static final long		startINP32BIT	= GuidType.startINP24BIT + 0x1000000L * NUMBER_POSITIVE_INT_24BIT.count();
	
	static final long		startINP40BIT	= GuidType.startINP32BIT + 0x100000000L * NUMBER_POSITIVE_INT_32BIT.count();
	
	static final long		startINP48BIT	= GuidType.startINP40BIT
													+ 0x10000000000L
													* NUMBER_POSITIVE_INT_40BIT.count();
	
	static final long		startINP56BIT	= GuidType.startINP48BIT
													+ 0x1000000000000L
													* NUMBER_POSITIVE_INT_48BIT.count();
	
	static final long		startINP64BIT	= GuidType.startINP56BIT
													+ 0x100000000000000L
													* NUMBER_POSITIVE_INT_56BIT.count();
	
	static final long		startDATE40BIT	= 1;
	
	static final long		startDATE64BIT	= GuidType.startDATE40BIT
													+ 0x10000000000L
													* DATE_POSITIVE_INT_40BIT.count();
	
	static final long		startCRC8BIT	= 48;
	
	static final long		startCRC16BIT	= GuidType.startCRC8BIT + 0x100L * CRC384_8BIT_SZ.count();
	
	static final long		startCRC24BIT	= GuidType.startCRC16BIT + 0x10000L * CRC384_16BIT_SZ.count();
	
	static final long		startCRC32BIT	= GuidType.startCRC24BIT + 0x1000000L * CRC384_24BIT_SZ.count();
	
	static final long		startCRC40BIT	= GuidType.startCRC32BIT + 0x100000000L * CRC384_32BIT_SZ.count();
	
	static final long		startCRC48BIT	= GuidType.startCRC40BIT + 0x10000000000L * CRC384_40BIT_SZ.count();
	
	static final long		startCRC56BIT	= GuidType.startCRC48BIT + 0x1000000000000L * CRC384_48BIT_SZ.count();
	
	static final long		startCRC64BIT	= GuidType.startCRC56BIT + 0x100000000000000L * CRC384_56BIT_SZ.count();
	
	// java implementation (.values() method) is MUCH slower: allocates array,
	// copies, collects.
	static final GuidType[]	ALL				= GuidType.values();
	
	public static final byte[] createCRC(final TransferCopier binary, final long length) {
		final WhirlpoolDigest digest = new WhirlpoolDigest();
		binary.updateMessageDigest( digest );
		return GuidType.createCRC( digest, length );
	}
	
	static final byte[] createCRC(final WhirlpoolDigest digest, final long length) {
		final byte[] bytes = new byte[49];
		digest.guidSet48( bytes );
		if (length < GuidType.startCRC16BIT) {
			final long value = length - GuidType.startCRC8BIT;
			bytes[0] = (byte) (GuidType.CRC384_8BIT_SZ.startIndex + value / 0x100L);
			bytes[1] = (byte) value;
			return bytes;
		}
		if (length < GuidType.startCRC24BIT) {
			final long value = length - GuidType.startCRC16BIT;
			bytes[0] = (byte) (GuidType.CRC384_16BIT_SZ.startIndex + value / 0x10000L);
			bytes[1] = (byte) (value >> 8);
			bytes[2] = (byte) value;
			return bytes;
		}
		if (length < GuidType.startCRC32BIT) {
			final long value = length - GuidType.startCRC24BIT;
			bytes[0] = (byte) (GuidType.CRC384_24BIT_SZ.startIndex + value / 0x1000000L);
			bytes[1] = (byte) (value >> 16);
			bytes[2] = (byte) (value >> 8);
			bytes[3] = (byte) value;
			return bytes;
		}
		if (length < GuidType.startCRC40BIT) {
			final long value = length - GuidType.startCRC32BIT;
			bytes[0] = (byte) (GuidType.CRC384_32BIT_SZ.startIndex + value / 0x100000000L);
			bytes[1] = (byte) (value >> 24);
			bytes[2] = (byte) (value >> 16);
			bytes[3] = (byte) (value >> 8);
			bytes[4] = (byte) value;
			return bytes;
		}
		if (length < GuidType.startCRC48BIT) {
			final long value = length - GuidType.startCRC40BIT;
			bytes[0] = (byte) (GuidType.CRC384_40BIT_SZ.startIndex + value / 0x10000000000L);
			bytes[1] = (byte) (value >> 32);
			bytes[2] = (byte) (value >> 24);
			bytes[3] = (byte) (value >> 16);
			bytes[4] = (byte) (value >> 8);
			bytes[5] = (byte) value;
			return bytes;
		}
		if (length < GuidType.startCRC56BIT) {
			final long value = length - GuidType.startCRC48BIT;
			bytes[0] = (byte) (GuidType.CRC384_48BIT_SZ.startIndex + value / 0x1000000000000L);
			bytes[1] = (byte) (value >> 40);
			bytes[2] = (byte) (value >> 32);
			bytes[3] = (byte) (value >> 24);
			bytes[4] = (byte) (value >> 16);
			bytes[5] = (byte) (value >> 8);
			bytes[6] = (byte) value;
			return bytes;
		}
		if (length < GuidType.startCRC64BIT) {
			final long value = length - GuidType.startCRC56BIT;
			bytes[0] = (byte) (GuidType.CRC384_56BIT_SZ.startIndex + value / 0x100000000000000L);
			bytes[1] = (byte) (value >> 48);
			bytes[2] = (byte) (value >> 40);
			bytes[3] = (byte) (value >> 32);
			bytes[4] = (byte) (value >> 24);
			bytes[5] = (byte) (value >> 16);
			bytes[6] = (byte) (value >> 8);
			bytes[7] = (byte) value;
			return bytes;
		}
		{
			final long value = length - GuidType.startCRC64BIT;
			bytes[0] = (byte) GuidType.CRC384_56BIT_SZ.startIndex;
			bytes[1] = (byte) (value >> 56);
			bytes[2] = (byte) (value >> 48);
			bytes[3] = (byte) (value >> 40);
			bytes[4] = (byte) (value >> 32);
			bytes[5] = (byte) (value >> 24);
			bytes[6] = (byte) (value >> 16);
			bytes[7] = (byte) (value >> 8);
			bytes[8] = (byte) value;
			return bytes;
		}
	}
	
	public static byte[] createDATE(final long date) {
		if (date < GuidType.startDATE64BIT) {
			final long value = date - GuidType.startCRC40BIT;
			final byte[] bytes = new byte[GuidType.DATE_POSITIVE_INT_40BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.DATE_POSITIVE_INT_40BIT.startIndex + value / 0x10000000000L);
			bytes[1] = (byte) (value >> 24);
			bytes[2] = (byte) (value >> 16);
			bytes[3] = (byte) (value >> 8);
			bytes[4] = (byte) value;
			return bytes;
		}
		{
			final long value = date - GuidType.startCRC64BIT;
			final byte[] bytes = new byte[GuidType.DATE_POSITIVE_INT_64BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) GuidType.CRC384_56BIT_SZ.startIndex;
			bytes[1] = (byte) (value >> 56);
			bytes[2] = (byte) (value >> 48);
			bytes[3] = (byte) (value >> 40);
			bytes[4] = (byte) (value >> 32);
			bytes[5] = (byte) (value >> 24);
			bytes[6] = (byte) (value >> 16);
			bytes[7] = (byte) (value >> 8);
			bytes[8] = (byte) value;
			return bytes;
		}
	}
	
	static final byte[] createINTP(final long integer) {
		if (integer < GuidType.startINP8BIT) {
			final long value = integer + 1;
			final byte[] bytes = new byte[GuidType.NUMBER_CONSTANT_0BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_CONSTANT_0BIT.startIndex + value);
			return bytes;
		}
		if (integer < GuidType.startINP16BIT) {
			final long value = integer - GuidType.startINP8BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_8BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_POSITIVE_INT_8BIT.startIndex + value / 0x100L);
			bytes[1] = (byte) value;
			return bytes;
		}
		if (integer < GuidType.startINP24BIT) {
			final long value = integer - GuidType.startINP16BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_16BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_POSITIVE_INT_16BIT.startIndex + value / 0x10000L);
			bytes[1] = (byte) (value >> 8);
			bytes[2] = (byte) value;
			return bytes;
		}
		if (integer < GuidType.startINP32BIT) {
			final long value = integer - GuidType.startINP24BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_24BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_POSITIVE_INT_24BIT.startIndex + value / 0x1000000L);
			bytes[1] = (byte) (value >> 16);
			bytes[2] = (byte) (value >> 8);
			bytes[3] = (byte) value;
			return bytes;
		}
		if (integer < GuidType.startINP40BIT) {
			final long value = integer - GuidType.startINP32BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_32BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_POSITIVE_INT_32BIT.startIndex + value / 0x100000000L);
			bytes[1] = (byte) (value >> 24);
			bytes[2] = (byte) (value >> 16);
			bytes[3] = (byte) (value >> 8);
			bytes[4] = (byte) value;
			return bytes;
		}
		if (integer < GuidType.startINP48BIT) {
			final long value = integer - GuidType.startINP40BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_40BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_POSITIVE_INT_40BIT.startIndex + value / 0x10000000000L);
			bytes[1] = (byte) (value >> 32);
			bytes[2] = (byte) (value >> 24);
			bytes[3] = (byte) (value >> 16);
			bytes[4] = (byte) (value >> 8);
			bytes[5] = (byte) value;
			return bytes;
		}
		if (integer < GuidType.startINP56BIT) {
			final long value = integer - GuidType.startINP48BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_48BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_POSITIVE_INT_48BIT.startIndex + value / 0x1000000000000L);
			bytes[1] = (byte) (value >> 40);
			bytes[2] = (byte) (value >> 32);
			bytes[3] = (byte) (value >> 24);
			bytes[4] = (byte) (value >> 16);
			bytes[5] = (byte) (value >> 8);
			bytes[6] = (byte) value;
			return bytes;
		}
		if (integer < GuidType.startINP64BIT) {
			final long value = integer - GuidType.startINP56BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_56BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) (GuidType.NUMBER_POSITIVE_INT_56BIT.startIndex + value / 0x100000000000000L);
			bytes[1] = (byte) (value >> 48);
			bytes[2] = (byte) (value >> 40);
			bytes[3] = (byte) (value >> 32);
			bytes[4] = (byte) (value >> 24);
			bytes[5] = (byte) (value >> 16);
			bytes[6] = (byte) (value >> 8);
			bytes[7] = (byte) value;
			return bytes;
		}
		{
			final long value = integer - GuidType.startINP64BIT;
			final byte[] bytes = new byte[GuidType.NUMBER_POSITIVE_INT_64BIT.getLeastSize( 0 ) + 1];
			bytes[0] = (byte) GuidType.NUMBER_POSITIVE_INT_64BIT.startIndex;
			bytes[1] = (byte) (value >> 56);
			bytes[2] = (byte) (value >> 48);
			bytes[3] = (byte) (value >> 40);
			bytes[4] = (byte) (value >> 32);
			bytes[5] = (byte) (value >> 24);
			bytes[6] = (byte) (value >> 16);
			bytes[7] = (byte) (value >> 8);
			bytes[8] = (byte) value;
			return bytes;
		}
	}
	
	/**
	 * @param guidTypeByte
	 * @return
	 */
	public static final boolean isInlineTypeForOrdinal(final int guidTypeByte) {
		return GuidType.ALL[guidTypeByte].isInline();
	}
	
	short	startIndex;
	
	int count() {
		return 1;
	}
	
	BaseObject<?> getBaseObject(final byte[] bytes) {
		if (this.isInline()) {
			throw new AbstractMethodError( "Should be overriden! type=" + this + ", byte=" + (bytes[0] & 0xFF) );
		}
		throw new IllegalAccessError( "Not an inline type! type=" + this );
	}
	
	long getBinaryLength(final byte[] bytes) {
		if (this.isInline() && this.isNumberInteger()) {
			throw new AbstractMethodError( "Should be overriden! type=" + this + ", byte=" + (bytes[0] & 0xFF) );
		}
		throw new IllegalAccessError( "Not a binary type! type=" + this );
	}
	
	Object getJavaObject(final byte[] bytes) {
		if (this.isInline()) {
			throw new AbstractMethodError( "Should be overriden! type=" + this + ", byte=" + (bytes[0] & 0xFF) );
		}
		throw new IllegalAccessError( "Not an inline type! type=" + this );
	}
	
	long getJavaPrimitiveNumberInteger(final byte[] bytes) {
		if (this.isInline() && this.isNumberInteger()) {
			throw new AbstractMethodError( "Should be overriden! type=" + this + ", byte=" + (bytes[0] & 0xFF) );
		}
		throw new IllegalAccessError( "Not an integer type! type=" + this );
	}
	
	abstract int getLeastSize(int byte2);
	
	boolean isBinary() {
		return false;
	}
	
	boolean isCollection() {
		return false;
	}
	
	boolean isInline() {
		return true;
	}
	
	boolean isNumber() {
		return false;
	}
	
	boolean isNumberInteger() {
		return false;
	}
	
	boolean isString() {
		return false;
	}
	
	boolean requiresSubByte() {
		return false;
	}
}
