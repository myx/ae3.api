package ru.myx.ae3.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ru.myx.ae3.AbstractSAPI;
import ru.myx.ae3.common.KeyFactory;
import ru.myx.ae3.exec.ExecFunction;

/**
 * @author myx
 * 
 */
public abstract class Base extends AbstractSAPI {
	private static final AbstractBaseImpl		BASE_IMPL;
	
	private static final int					CACHE_INT_POSITIVE_SIZE			= 4 * 64 * 1024;
	
	private static final int					CACHE_INT_NEGATIVE_SIZE			= 8 * 1024;
	
	private static final short					CACHE_INT_NEGATIVE_MIN			= -Base.CACHE_INT_NEGATIVE_SIZE;
	
	private static final short					CACHE_INT_NEGATIVE_MIN_SHORT	= (short) -Base.CACHE_INT_NEGATIVE_SIZE;
	
	private static final BasePrimitiveNumber[]	CACHE_INT_POSITIVE				= new BasePrimitiveNumber[Base.CACHE_INT_POSITIVE_SIZE];
	
	private static final BasePrimitiveNumber[]	CACHE_INT_NEGATIVE				= new BasePrimitiveNumber[Base.CACHE_INT_NEGATIVE_SIZE];
	
	private static final BasePrimitiveString[]	CHARS							= new BasePrimitiveString[Character.MAX_VALUE + 1];
	
	private static final CacheIntegerImpl		CACHE_INT;
	
	private static final CacheDoubleImpl		CACHE_DBL;
	
	private static final CacheLongImpl			CACHE_LNG;
	
	private static final CacheStringImpl		CACHE_STR;
	
	static {
		/**
		 * this block should go last
		 */
		{
			BASE_IMPL = AbstractSAPI.createObject( "ru.myx.ae3.base.ImplementBase" );
		}
		{
			CACHE_INT = Base.BASE_IMPL.createCacheInteger();
			CACHE_LNG = Base.BASE_IMPL.createCacheLong();
			CACHE_DBL = Base.BASE_IMPL.createCacheDouble();
			CACHE_STR = Base.BASE_IMPL.createCacheString();
		}
		{
			new PrimitiveString( "pre-init string" );
		}
		{
			for (int i = Base.CACHE_INT_POSITIVE_SIZE - 1; i > 1; i--) {
				Base.CACHE_INT_POSITIVE[i] = new PrimitiveNumberTrueInteger( i );
			}
			Base.CACHE_INT_POSITIVE[1] = BasePrimitiveNumber.ONE;
			Base.CACHE_INT_POSITIVE[0] = BasePrimitiveNumber.ZERO;
			for (int i = Base.CACHE_INT_NEGATIVE_SIZE - 1; i > 0; i--) {
				Base.CACHE_INT_NEGATIVE[i] = new PrimitiveNumberTrueInteger( -i - 1 );
			}
			Base.CACHE_INT_NEGATIVE[0] = BasePrimitiveNumber.MONE;
			
			/**
			 * Just to be sure that byte range support is here.
			 */
			assert Math.round( Base.CACHE_INT_POSITIVE_SIZE ) >= Short.MAX_VALUE : "Required for full range positive short support";
			assert Math.round( Base.CACHE_INT_NEGATIVE_SIZE ) >= 255 : "Required for full range byte support";
			assert Math.round( Base.CACHE_INT_NEGATIVE_MIN ) <= -256 : "Required for full range byte support";
			assert Math.round( Base.CACHE_INT_NEGATIVE_MIN_SHORT ) <= -256 : "Required for full range byte support";
			
			/**
			 * fill 1-char length strings
			 */
			for (int i = Base.CHARS.length - 1; i >= 0; i--) {
				switch (i) {
				case '0':
					Base.CHARS['0'] = BasePrimitiveString.ZERO;
					continue;
				case '1':
					Base.CHARS['1'] = BasePrimitiveString.ONE;
					continue;
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					Base.CHARS[i] = new PrimitiveStringBaseInteger( String.valueOf( (char) i ), i - '0' );
					continue;
				default:
					Base.CHARS[i] = new PrimitiveStringBaseNaN( String.valueOf( (char) i ) );
				}
			}
			
			/**
			 * fill constants
			 */
			Base.initCachePutDouble( BasePrimitiveNumber.E );
			Base.initCachePutDouble( BasePrimitiveNumber.LN10 );
			Base.initCachePutDouble( BasePrimitiveNumber.LN2 );
			Base.initCachePutDouble( BasePrimitiveNumber.LOG10E );
			Base.initCachePutDouble( BasePrimitiveNumber.LOG2E );
			Base.initCachePutDouble( BasePrimitiveNumber.NAN );
			Base.initCachePutDouble( BasePrimitiveNumber.NINF );
			Base.initCachePutDouble( BasePrimitiveNumber.PI );
			Base.initCachePutDouble( BasePrimitiveNumber.MAX_VALUE );
			Base.initCachePutDouble( BasePrimitiveNumber.MIN_VALUE );
			Base.initCachePutDouble( BasePrimitiveNumber.PINF );
			Base.initCachePutDouble( BasePrimitiveNumber.SQRT1_2 );
			Base.initCachePutDouble( BasePrimitiveNumber.SQRT2 );
			
			Base.initCachePutString( BasePrimitiveString.EMPTY );
			Base.initCachePutString( BasePrimitiveString.FALSE );
			Base.initCachePutString( BasePrimitiveString.NAN );
			Base.initCachePutString( BasePrimitiveString.NINFINITY );
			Base.initCachePutString( BasePrimitiveString.NULL );
			Base.initCachePutString( BasePrimitiveString.PINFINITY );
			Base.initCachePutString( BasePrimitiveString.TRUE );
			Base.initCachePutString( BasePrimitiveString.UNDEFINED );
			Base.initCachePutString( BasePrimitiveString.ONE );
			Base.initCachePutString( BasePrimitiveString.ZERO );
			
			Base.initCachePutString( BasePrimitiveString.PROPERTY_BASE_LENGTH );
			Base.initCachePutString( BasePrimitiveString.PROPERTY_BASE_CALLEE );
			Base.initCachePutString( BasePrimitiveString.PROPERTY_BASE_PROTOTYPE );
		}
		{
			assert BasePrimitiveString.PROPERTY_BASE_LENGTH == Base.forString( "length" ) : "Should return the same instance!";
			assert BasePrimitiveString.PROPERTY_BASE_PROTOTYPE == Base.forString( "prototype" ) : "Should return the same instance!";
		}
	}
	
	static final void cacheRemoveDouble(final double value, final CacheDouble cacheDouble) {
		Base.CACHE_DBL.cacheRemove( value, cacheDouble );
	}
	
	static final void cacheRemoveInt(final int value, final CacheInteger cacheInteger) {
		Base.CACHE_INT.cacheRemove( value, cacheInteger );
	}
	
	static final void cacheRemoveLong(final long value, final CacheLong cacheLong) {
		Base.CACHE_LNG.cacheRemove( value, cacheLong );
	}
	
	static final void cacheRemoveString(final String value, final CacheString cacheString) {
		Base.CACHE_STR.cacheRemove( value, cacheString );
		
	}
	
	/**
	 * ECMA EQU comparison is not the same as ECMA.compare() == 0. {a:5} is not
	 * equal to {a:5}. It doesn't do any 'valueOf' and 'toString' calls.
	 * 
	 * 
	 * 
	 * <pre>
	 * 11.9.1 The Equals Operator ( == ) 
	 * The production EqualityExpression : EqualityExpression == RelationalExpression is evaluated as follows: 
	 * 1. Evaluate EqualityExpression. 
	 * 2. Call GetValue(Result(1)). 
	 * 3. Evaluate RelationalExpression. 
	 * 4. Call GetValue(Result(3)). 
	 * 5. Perform the comparison Result(4) == Result(2). (Section 11.9.3.) 
	 * 6. Return Result(5).
	 * </pre>
	 * 
	 * <pre>
	 * 11.9.3 The Abstract Equality Comparison Algorithm 
	 * The comparison x == y, where x and y are values, produces true or false. Such a comparison is performed as 
	 * follows: 
	 * 1. If Type(x) is different from Type(y), go to step 14. 
	 * 2. If Type(x) is Undefined, return true. 
	 * 3. If Type(x) is Null, return true. 
	 * 4. If Type(x) is not Number, go to step 11. 
	 * 5. If x is NaN, return false. 
	 * 6. If y is NaN, return false. 
	 * 7. If x is the same number value as y, return true. 
	 * 8. If x is +0 and y is −0, return true. 
	 * 9. If x is −0 and y is +0, return true. 
	 * 10. Return false. 
	 * 11. If Type(x) is String, then return true if x and y are exactly the same sequence of characters (same length and 
	 * same characters in corresponding positions). Otherwise, return false. 
	 * 12. If Type(x) is Boolean, return true if x and y are both true or both false. Otherwise, return false. 
	 * 13. Return true if x and y refer to the same object or if they refer to objects joined to each other (section 13.1.2). 
	 * Otherwise, return false. 
	 * 14. If x is null and y is undefined, return true. 
	 * 15. If x is undefined and y is null, return true. 
	 * 16. If Type(x) is Number and Type(y) is String, 
	 * return the result of the comparison x == ToNumber(y). 
	 * 17. If Type(x) is String and Type(y) is Number, 
	 * return the result of the comparison ToNumber(x) == y. 
	 * 18. If Type(x) is Boolean, return the result of the comparison ToNumber(x) == y. 
	 * 19. If Type(y) is Boolean, return the result of the comparison x == ToNumber(y). 
	 * 20. If Type(x) is either String or Number and Type(y) is Object, 
	 * return the result of the comparison x == ToPrimitive(y). 
	 * 21. If Type(x) is Object and Type(y) is either String or Number, 
	 * return the result of the comparison ToPrimitive(x) == y. 
	 * 22. Return false.
	 * </pre>
	 * 
	 * NOTE Given the above definition of equality:<br>
	 * String comparison can be forced by: &quot;&quot; + a ==&quot;&quot; +b.<br>
	 * Numeric comparison can be forced by: a - 0== b- 0. <br>
	 * Boolean comparison can be forced by: !a == !b.
	 * <p>
	 * The equality operators maintain the following invariants:<br>
	 * 1. A != B is equivalent to !(A == B). <br>
	 * 2. A == B is equivalent to B == A, except in the order of evaluation of A
	 * and B.<br>
	 * The equality operator is not always transitive. For example, there might
	 * be two distinct String objects, each representing the same string value;
	 * each String object would be considered equal to the string value by the
	 * == operator, but the two String objects would not be equal to each other.
	 * <p>
	 * Comparison of strings uses a simple equality test on sequences of code
	 * point value values. There is no attempt to use the more complex,
	 * semantically oriented definitions of character or string equality and
	 * collating order defined in the Unicode 2.0 specification. Therefore
	 * strings that are canonically equal according to the Unicode standard
	 * could test as unequal. In effect this algorithm assumes that both strings
	 * are already in normalised form.
	 * <p>
	 * 
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static final boolean compareEQU(final BaseObject<?> o1, final BaseObject<?> o2) {
		if (o1 == BasePrimitiveNumber.NAN || o2 == BasePrimitiveNumber.NAN) {
			return false;
		}
		if (o1 == o2) {
			return true;
		}
		
		{
			final boolean o1falseExact = o1 == null
					|| o1 == BaseObject.UNDEFINED
					|| o1 == BaseObject.NULL
					|| o1 == BaseObject.FALSE;
			
			final boolean o2falseExact = o2 == null
					|| o2 == BaseObject.UNDEFINED
					|| o2 == BaseObject.NULL
					|| o2 == BaseObject.FALSE;
			
			if (o1falseExact && o2falseExact) {
				return true;
			}
			
			if (o1falseExact) {
				return o2 != null && o2.baseToBoolean().booleanValue()
						? false
						: true;
			}
			if (o2falseExact) {
				return o1 != null && o1.baseToBoolean().booleanValue()
						? false
						: true;
			}
		}
		
		{
			final boolean o1trueExact = o1 == BaseObject.TRUE;
			final boolean o2trueExact = o2 == BaseObject.TRUE;
			
			if (o1trueExact && o2trueExact) {
				return true;
			}
			
			if (o1trueExact) {
				return o2 != null && o2.baseToBoolean().booleanValue()
						? true
						: false;
			}
			if (o2trueExact) {
				return o1 != null && o1.baseToBoolean().booleanValue()
						? true
						: false;
			}
		}
		
		{
			final boolean o1falseAlike = o1 == BasePrimitiveNumber.ZERO || o1 == BasePrimitiveString.EMPTY;
			final boolean o2falseAlike = o2 == BasePrimitiveNumber.ZERO || o2 == BasePrimitiveString.EMPTY;
			
			if (o1falseAlike && o2falseAlike) {
				return true;
			}
		}
		
		assert o1 != null && o2 != null : "should not get there ^^^^^";
		
		/**
		 * 11. If Type(x) is String, then return true if x and y are exactly the
		 * same sequence of characters (same length and same characters in
		 * corresponding positions). Otherwise, return false.
		 * 
		 * (for context/temporary objects)
		 * 
		 * already done earlier: <code>
		 * </code>
		 * 
		 * TODO: re-check, clean up, ugly and error-prone in the future
		 */
		if (o1.baseIsPrimitiveString() && o2.baseIsPrimitiveString()) {
			return o1.toString().equals( o2.toString() );
			// return o1.baseToString().baseValue()
			// .compareTo( o2.baseToPrimitive( ToPrimitiveHint.STRING
			// ).baseToString().baseValue() ) == 0;
		}
		/**
		 * Number vs Number, equals was earlier, but still need to compare.
		 * 
		 * (for context/temporary objects)
		 * 
		 * already done earlier: <code>
		 * </code>
		 * 
		 * TODO: re-check, clean up
		 */
		if (o1.baseIsPrimitiveNumber() && o2.baseIsPrimitiveNumber()) {
			return o1.baseToPrimitive( null ).doubleValue() == o2.baseToPrimitive( null ).doubleValue();
		}
		/**
		 * 16. If Type(x) is Number and Type(y) is String, return the result of
		 * the comparison x == ToNumber(y).
		 */
		if (o1.baseIsPrimitiveNumber() && o2.baseIsPrimitiveString()) {
			/**
			 * for fakes
			 */
			// return o1 == o2.baseToNumber();
			return o1.baseToPrimitive( null ).doubleValue() == o2.baseToPrimitive( null ).doubleValue();
		}
		/**
		 * 17. If Type(x) is String and Type(y) is Number, return the result of
		 * the comparison ToNumber(x) == y.
		 */
		if (o1.baseIsPrimitiveString() && o2.baseIsPrimitiveNumber()) {
			/**
			 * for fakes
			 */
			// return o1.baseToNumber() == o2;
			return o1.baseToPrimitive( null ).doubleValue() == o2.baseToPrimitive( null ).doubleValue();
		}
		if (!o2.baseIsPrimitive()) {
			/**
			 * 20. If Type(x) is either String or Number and Type(y) is Object,
			 * return the result of the comparison x == ToPrimitive(y).
			 */
			if (o1.baseIsPrimitiveString()) {
				return o1 == o2.baseToPrimitive( ToPrimitiveHint.STRING );
				// return ComparatorEcma.compareEQU( o1, o2.baseToPrimitive(
				// ToPrimitiveHint.STRING ) );
			}
			if (o1.baseIsPrimitiveNumber()) {
				return o1 == o2.baseToPrimitive( ToPrimitiveHint.NUMBER );
				// return ComparatorEcma.compareEQU( o1, o2.baseToPrimitive(
				// ToPrimitiveHint.NUMBER ) );
			}
		}
		if (!o1.baseIsPrimitive()) {
			/**
			 * 21. If Type(x) is Object and Type(y) is either String or Number,
			 * return the result of the comparison ToPrimitive(x) == y.
			 */
			if (o2.baseIsPrimitiveString()) {
				return o1.baseToPrimitive( ToPrimitiveHint.STRING ) == o2;
				// return ComparatorEcma.compareEQU( o1.baseToPrimitive(
				// ToPrimitiveHint.STRING ), o2 );
			}
			if (o2.baseIsPrimitiveNumber()) {
				return o1.baseToPrimitive( ToPrimitiveHint.NUMBER ) == o2;
				// return ComparatorEcma.compareEQU( o1.baseToPrimitive(
				// ToPrimitiveHint.NUMBER ), o2 );
			}
		}
		return false;
	}
	
	final static CacheIntegerImpl createCacheIntegerBucket() {
		return Base.BASE_IMPL.createCacheInteger();
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forArray(final BaseObject<?> object) {
		// ignore
	}
	
	/**
	 * @param <T>
	 * @param object
	 * @return primitive
	 */
	public static final <T> BaseArray<List<T>, T> forArray(final List<T> object) {
		return Base.BASE_IMPL.javaObjectToBaseArray( object );
	}
	
	/**
	 * @param <T>
	 * @param object
	 * @return primitive
	 */
	@SuppressWarnings("unchecked")
	public final static <T> BaseArray<List<T>, T> forArray(final T[] object) {
		return Base.BASE_IMPL.javaObjectToBaseArray( object == null
				? (List<T>) Collections.EMPTY_LIST
				: Arrays.asList( object ) );
	}
	
	/**
	 * @param value
	 * @return number
	 */
	public static final BasePrimitiveNumber forByte(final byte value) {
		return value < 0
				? Base.CACHE_INT_NEGATIVE[-value - 1]
				: Base.CACHE_INT_POSITIVE[value];
	}
	
	/**
	 * @param c
	 * @return
	 */
	public static final BasePrimitiveString forChar(final char c) {
		return Base.CHARS[c];
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param date
	 */
	@Deprecated
	public static final void forDate(final BaseDate date) {
		//
	}
	
	/**
	 * Will return same objects if it is an instance of BaseDate or will create
	 * BaseWrapDate wrapper to dynamically reflect changes in given Date object.
	 * 
	 * @param date
	 * @return date
	 */
	public static final BaseDate forDate(final Date date) {
		if (date instanceof BaseDate) {
			return (BaseDate) date;
		}
		return new BaseWrapDate( date );
	}
	
	/**
	 * Will simply create an instance of BaseDate
	 * 
	 * @param date
	 * @return date
	 */
	public static final BaseDate forDateMillis(final long date) {
		return new BaseDate( date );
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forDouble(final byte value) {
		//
	}
	
	/**
	 * @param x
	 * @return number
	 */
	public static final BasePrimitiveNumber forDouble(final double x) {
		if (x == (long) x) {
			final int intX = (int) x;
			if (intX == x) {
				return Base.forInteger( intX );
			}
			return Base.forLong( (long) x );
		}
		if (Double.isNaN( x )) {
			return BasePrimitiveNumber.NAN;
		}
		if (x == Double.NEGATIVE_INFINITY) {
			return BasePrimitiveNumber.NINF;
		}
		if (x == Double.POSITIVE_INFINITY) {
			return BasePrimitiveNumber.PINF;
		}
		return Base.CACHE_DBL.cacheGetCreate( x ).getPrimitive();
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forDouble(final int value) {
		//
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forDouble(final long value) {
		//
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forDouble(final short value) {
		//
	}
	
	/**
	 * Catch method
	 * 
	 * @param function
	 */
	@Deprecated
	public static final void forFunction(final BaseFunction<?> function) {
		//
	}
	
	/**
	 * @param function
	 * @return
	 */
	public static BaseFunction<?> forFunction(final ExecFunction function) {
		return Base.BASE_IMPL.javaNativeFunction( function );
	}
	
	/**
	 * @param value
	 * @return number
	 */
	public static final BasePrimitiveNumber forInteger(final int value) {
		return value >= 0
				? value < Base.CACHE_INT_POSITIVE_SIZE
						? Base.CACHE_INT_POSITIVE[value]
						: Base.CACHE_INT.cacheGetCreate( value ).getPrimitive()
				: value >= Base.CACHE_INT_NEGATIVE_MIN
						? Base.CACHE_INT_NEGATIVE[-value - 1]
						: Base.CACHE_INT.cacheGetCreate( value ).getPrimitive();
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forInteger(final short value) {
		//
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forLong(final byte value) {
		//
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forLong(final int value) {
		//
	}
	
	/**
	 * @param x
	 * @return number
	 */
	public static final BasePrimitiveNumber forLong(final long x) {
		{
			final int intX = (int) x;
			if (intX == x) {
				return Base.forInteger( intX );
			}
		}
		return Base.CACHE_LNG.cacheGetCreate( x ).getPrimitive();
	}
	
	/**
	 * CATCH METHOD
	 * 
	 * @param value
	 */
	public static final void forLong(final short value) {
		//
	}
	
	/**
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T forNull() {
		return (T) BaseObject.NULL;
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have String type already - user forNumber().
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forNumber(final Byte object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have String type already - user forNumber().
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forNumber(final Double object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have String type already - user forNumber().
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forNumber(final Float object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have String type already - user forNumber().
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forNumber(final Integer object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have String type already - user forNumber().
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forNumber(final Long object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have String type already - user forNumber().
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forNumber(final Short object) {
		//
	}
	
	/**
	 * @param <T>
	 * @param value
	 * @return number
	 */
	public static final <T extends Number> BaseObject<T> forNumber(final T value) {
		return Base.BASE_IMPL.javaNumberToBaseObjectNumber( value );
	}
	
	/**
	 * @param value
	 * @return number
	 */
	public static final BasePrimitiveNumber forShort(final short value) {
		return value >= 0
				? Base.CACHE_INT_POSITIVE[value]
				: value >= Base.CACHE_INT_NEGATIVE_MIN_SHORT
						? Base.CACHE_INT_NEGATIVE[-value - 1]
						: Base.CACHE_INT.cacheGetCreate( value ).getPrimitive();
	}
	
	/**
	 * @param value
	 * @return string
	 */
	public static final BasePrimitiveString forString(final String value) {
		if (value == null) {
			return BasePrimitiveString.EMPTY;
		}
		switch (value.length()) {
		case 0:
			return BasePrimitiveString.EMPTY;
		case 1:
			return Base.CHARS[value.charAt( 0 )];
		default:
			return Base.CACHE_STR.cacheGetCreate( value ).getPrimitive();
		}
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forThrowable(final BaseObject<?> object) {
		//
	}
	
	/**
	 * @param object
	 * @return primitive
	 */
	public static final BaseAbstractException forThrowable(final Throwable object) {
		return Base.BASE_IMPL.javaThrowableToBaseObject( object );
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final BaseObject<?> object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final Boolean object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final Date object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final ExecFunction object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation.
	 * 
	 * Use getArray
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final List<?> object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final Number object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation.
	 * 
	 * Use forArray
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final Object[] object) {
		//
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final String object) {
		//
	}
	
	/**
	 * @param <T>
	 * @param object
	 * @return primitive
	 */
	public static final <T> BaseObject<T> forUnknown(final T object) {
		return Base.BASE_IMPL.javaObjectToBaseObject( object );
	}
	
	/**
	 * Catch method for catching objects that are known before compilation to
	 * have BaseObject<?> type already.
	 * 
	 * @param object
	 */
	@Deprecated
	public final static void forUnknown(final Throwable object) {
		//
	}
	
	/**
	 * Does all the stuff with array/property access
	 * 
	 * @param object
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	public static final BaseObject<?> get(
			final BaseObject<?> object,
			final BaseObject<?> property,
			final BaseObject<?> defaultValue) {
		if (property instanceof BasePrimitive<?>) {
			if (property instanceof BasePrimitiveString) {
				return object.baseGet( (BasePrimitiveString) object, defaultValue );
			}
			if (property instanceof PrimitiveNumberIntegerAbstract) {
				final BaseArray<?, ?> array = object.baseArray();
				if (array != null) {
					return array.baseGet( ((BasePrimitive<?>) object).intValue(), defaultValue );
				}
			}
			/**
			 * support for fakes
			 */
			if (property.baseIsPrimitiveInteger()) {
				final BaseArray<?, ?> array = object.baseArray();
				if (array != null) {
					return array.baseGet( ((BasePrimitive<?>) object).intValue(), defaultValue );
				}
			}
			return object.baseGet( object.baseToString(), defaultValue );
		}
		{
			final BasePrimitive<?> primitive = property.baseToPrimitive( null );
			if (primitive instanceof PrimitiveNumberIntegerAbstract) {
				final BaseArray<?, ?> array = primitive.baseArray();
				if (array != null) {
					return array.baseGet( primitive.intValue(), defaultValue );
				}
			}
			return object.baseGet( primitive.baseToString(), defaultValue );
		}
	}
	
	/**
	 * Does all the stuff with array/property access
	 * 
	 * @param object
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	public static final BaseObject<?> get(
			final BaseObject<?> object,
			final BasePrimitive<?> property,
			final BaseObject<?> defaultValue) {
		if (property instanceof BasePrimitiveString) {
			return object.baseGet( (BasePrimitiveString) object, defaultValue );
		}
		/**
		 * with support for fakes
		 */
		if (property instanceof PrimitiveNumberIntegerAbstract || property.baseIsPrimitiveInteger()) {
			final BaseArray<?, ?> array = object.baseArray();
			if (array != null) {
				return array.baseGet( ((BasePrimitive<?>) object).intValue(), defaultValue );
			}
		}
		return object.baseGet( object.baseToString(), defaultValue );
	}
	
	/**
	 * @param object
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	public static final BaseObject<?> get(
			final BaseObject<?> object,
			final BasePrimitiveString property,
			final BaseObject<?> defaultValue) {
		return object.baseGet( property, defaultValue );
	}
	
	/**
	 * 
	 * Alias for: HasProperty(name) ? Get(name).value() : defaultValue
	 * 
	 * @param object
	 * @param name
	 * @param defaultValue
	 * @return object
	 */
	public static final Object getJava(final BaseObject<?> object, final String name, final Object defaultValue) {
		final BaseObject<?> result = object.baseGet( name, null );
		return result == null
				? defaultValue
				: result.baseValue();
	}
	
	/**
	 * @return
	 */
	public static final KeyFactory<String> getKeyFactory() {
		return KeyFactory.FACTORY_STRING;
	}
	
	/**
	 * 8.6.2.1.1 [[GetProperty]] (P)
	 * <p>
	 * When the [[GetProperty]] method of O is called with property name P, the
	 * following steps are taken:<br>
	 * 1. Call the [[GetOwnProperty]] method of O with property name P. <br>
	 * 2. If Result(1) is not undefined, return Result(1). <br>
	 * 3. If the [[Prototype]] of O is null, return undefined. <br>
	 * 4. Call the [[GetProperty]] method of [[Prototype]] with property name P.
	 * <br>
	 * 5. Return Result(4). <br>
	 * 
	 * @param object
	 * @param name
	 * @return
	 */
	public static final BaseProperty getProperty(final BaseObject<?> object, final BasePrimitiveString name) {
		final BaseProperty property = object.baseGetOwnProperty( name );
		if (property != null) {
			return property;
		}
		final BaseObject<?> prototype = object.basePrototype();
		assert prototype != object : "prototype should not be equal to this instance, class="
				+ object.getClass().getName();
		return prototype == null
				? null
				: Base.getProperty( prototype, name );
	}
	
	/**
	 * 8.6.2.1.1 [[GetProperty]] (P)
	 * <p>
	 * When the [[GetProperty]] method of O is called with property name P, the
	 * following steps are taken:<br>
	 * 1. Call the [[GetOwnProperty]] method of O with property name P. <br>
	 * 2. If Result(1) is not undefined, return Result(1). <br>
	 * 3. If the [[Prototype]] of O is null, return undefined. <br>
	 * 4. Call the [[GetProperty]] method of [[Prototype]] with property name P.
	 * <br>
	 * 5. Return Result(4). <br>
	 * 
	 * @param object
	 * @param name
	 * @param stop
	 * @return
	 */
	public static final BaseProperty getProperty(
			final BaseObject<?> object,
			final BasePrimitiveString name,
			final BaseObject<?> stop) {
		final BaseProperty property = object.baseGetOwnProperty( name );
		if (property != null) {
			return property;
		}
		final BaseObject<?> prototype = object.basePrototype();
		assert prototype != object : "prototype should not be equal to this instance, class="
				+ object.getClass().getName();
		return prototype == null || prototype == stop
				? null
				: Base.getProperty( prototype, name, stop );
	}
	
	/**
	 * 8.6.2.1.1 [[GetProperty]] (P)
	 * <p>
	 * When the [[GetProperty]] method of O is called with property name P, the
	 * following steps are taken:<br>
	 * 1. Call the [[GetOwnProperty]] method of O with property name P. <br>
	 * 2. If Result(1) is not undefined, return Result(1). <br>
	 * 3. If the [[Prototype]] of O is null, return undefined. <br>
	 * 4. Call the [[GetProperty]] method of [[Prototype]] with property name P.
	 * <br>
	 * 5. Return Result(4). <br>
	 * 
	 * @param object
	 * @param name
	 * @return
	 */
	public static final BaseProperty getProperty(final BaseObject<?> object, final String name) {
		final BaseProperty property = object.baseGetOwnProperty( name );
		if (property != null) {
			return property;
		}
		final BaseObject<?> prototype = object.basePrototype();
		assert prototype != object : "prototype should not be equal to this instance, class="
				+ object.getClass().getName();
		return prototype == null
				? null
				: Base.getProperty( prototype, name );
	}
	
	/**
	 * 8.6.2.1.1 [[GetProperty]] (P)
	 * <p>
	 * When the [[GetProperty]] method of O is called with property name P, the
	 * following steps are taken:<br>
	 * 1. Call the [[GetOwnProperty]] method of O with property name P. <br>
	 * 2. If Result(1) is not undefined, return Result(1). <br>
	 * 3. If the [[Prototype]] of O is null, return undefined. <br>
	 * 4. Call the [[GetProperty]] method of [[Prototype]] with property name P.
	 * <br>
	 * 5. Return Result(4). <br>
	 * 
	 * @param object
	 * @param name
	 * @param stop
	 * @return
	 */
	public static final BaseProperty getProperty(final BaseObject<?> object, final String name, final BaseObject<?> stop) {
		final BaseProperty property = object.baseGetOwnProperty( name );
		if (property != null) {
			return property;
		}
		final BaseObject<?> prototype = object.basePrototype();
		assert prototype != object : "prototype should not be equal to this instance, class="
				+ object.getClass().getName();
		return prototype == null || prototype == stop
				? null
				: Base.getProperty( prototype, name, stop );
	}
	
	/**
	 * [[HasProperty]] (P) When the [[HasProperty]] method of O is called with
	 * property name P, the following steps are taken:<br>
	 * 1. If O has a property with name P, return true.<br>
	 * 2. If the [[Prototype]] of O is null, return false.<br>
	 * 3. Call the [[HasProperty]] method of [[Prototype]] with property name P.<br>
	 * 4. Return Result(3).
	 * 
	 * @param object
	 * @param name
	 * @return boolean
	 */
	public static final boolean hasProperty(final BaseObject<?> object, final BasePrimitiveString name) {
		return object.baseGet( name, null ) != null;
	}
	
	/**
	 * [[HasProperty]] (P) When the [[HasProperty]] method of O is called with
	 * property name P, the following steps are taken:<br>
	 * 1. If O has a property with name P, return true.<br>
	 * 2. If the [[Prototype]] of O is null, return false.<br>
	 * 3. Call the [[HasProperty]] method of [[Prototype]] with property name P.<br>
	 * 4. Return Result(3).
	 * 
	 * @param object
	 * @param name
	 * @return boolean
	 */
	public static final boolean hasProperty(final BaseObject<?> object, final String name) {
		return Base.getProperty( object, name ) != null;
	}
	
	private static final void initCachePutDouble(final BasePrimitiveNumber value) {
		final double doubleValue = value.doubleValue();
		assert doubleValue != value.longValue() : "Use CacheInteger or CacheLong instead!";
		Base.CACHE_DBL.cachePutInternal( doubleValue, new StaticDouble( doubleValue, value ) );
	}
	
	private static final void initCachePutString(final BasePrimitiveString value) {
		final String string = value.baseValue();
		Base.CACHE_STR.cachePutInternal( string, new CacheString( string, value ) );
	}
	
	/**
	 * @param primary
	 * @param secondary
	 * @return
	 */
	public static final Iterator<String> joinPropertyIterators(
			final Iterator<String> primary,
			final Iterator<String> secondary) {
		assert primary != null : "Iterator is null, use BaseObject.ITERATOR_EMPTY!";
		assert secondary != null : "Iterator is null, use BaseObject.ITERATOR_EMPTY!";
		if (primary == BaseObject.ITERATOR_EMPTY) {
			return secondary;
		}
		if (secondary == BaseObject.ITERATOR_EMPTY) {
			return primary;
		}
		return new IteratorSequenceString( primary, secondary );
	}
	
	/**
	 * Short cut for 'put(o,name,value.true,true,true)'
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @return
	 */
	public static final boolean put(final BaseObject<?> object, final String name, final BaseObject<?> value) {
		return Base.put( object, name, value, true, true, true );
	}
	
	/**
	 * <pre>
	 * 8.6.2.2 [[Put]] (P, V, Strict) 
	 * For brevity, the changes caused by [[Put]] are described below in a self contained manner. But [[Put]] 
	 * must not be able to cause and state transitions that wouldn‘t be allowed by [[SetOwnProperty]]. 
	 * When the [[Put]] method of O is called with property P and value V, the following steps are taken: 
	 *  
	 * 1. Call the [[GetOwnProperty]] method of O with name P. 
	 * 2. If Result(1) is a DDesc, then 
	 * 		a. Get the [[Writeable]] component of Result(1). 
	 * 		b. If Result(2a) is true, then  
	 * 			i. Set the [[Value]] component of property P of O to V. 
	 * 			ii. Return true. 
	 * 		c. If Strict is true, then throw TypeError. 
	 * 		d. Else return false. 
	 * 3. Call the [[GetProperty]] method of O with name P. 
	 * 4. If Result(3) is a ProceduralDescription, then 
	 * 		a. Get the [[Setter]] component of Result(3). 
	 * 		b. If Result(4a) is undefined, then 
	 * 			i. If Strict is true, then throw TypeError. 
	 * 			ii. Else return false. 
	 * 		e. Call Result(4a) as a method on O with argument V. 
	 * 		f. Return true. 
	 * 5. Get the [[Extensible]] property of object O. 
	 * 6. If Result(5) is false, then. 
	 * 		a. If Strict is true, then throw TypeError. 
	 * 		b. Else return false. 
	 * 7. Create an own property P on object O whose state is 
	 * 		a. [[Value]]: V, 
	 * 		b. [[Writeable]]: true, 
	 * 		c. [[Enumerable]]: true, 
	 * 		d. [[Dynamic]]: true. 
	 * 8. Return true.
	 * </pre>
	 * 
	 * Note, however, that if O is an Array object, it has a more elaborate
	 * [[Put]] method (15.4.5.1).
	 * 
	 * <p>
	 * In any implementation should behave exactly as put(name, value, writable,
	 * enumerable, dynamic)
	 * </p>
	 * 
	 * In scripting, implicit store object specifier (i.e. "b = 5" as opposed to
	 * "a.b = 5") should use this method.
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public static final boolean put(
			final BaseObject<?> object,
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		{
			/**
			 * 1. Call the [[GetOwnProperty]] method of O with name P.
			 */
			final BaseProperty property = object.baseGetOwnProperty( name );
			/**
			 * 2. If Result(1) is a DDesc, then
			 */
			if (property != null) {
				/**
				 * a. Get the [[Writeable]] component of Result(1).<br>
				 * b. If Result(2a) is true, then
				 */
				if (property.isWritable( name )) {
					/**
					 * i. Set the [[Value]] component of property P of O to V.<br>
					 * ii. Return true.
					 */
					return property.propertySet( object, name, value, writable, enumerable, dynamic );
				}
				/**
				 * c. If Strict is true, then throw TypeError.<br>
				 * d. Else return false.
				 */
				return false;
			}
		}
		{
			final BaseObject<?> prototype = object.basePrototype();
			if (prototype != null) {
				/**
				 * 3. Call the [[GetProperty]] method of O with name P.
				 */
				final BaseProperty property = Base.getProperty( object, name );
				/**
				 * 4. If Result(3) is a ProceduralDescription, then<br>
				 */
				if (property != null && property.isProceduralSetter( name )) {
					/**
					 * a. Get the [[Setter]] component of Result(3).<br>
					 * b. If Result(4a) is undefined, then <br>
					 * i. If Strict is true, then throw TypeError.<br>
					 * ii. Else return false. <br>
					 * e. Call Result(4a) as a method on O with argument V.<br>
					 * f. Return true.<br>
					 */
					return property.propertySet( object, name, value, writable, enumerable, dynamic );
				}
			}
		}
		/**
		 * 5. Get the [[Extensible]] property of object O.<br>
		 * 6. If Result(5) is false, then.
		 */
		if (!object.baseIsExtensible()) {
			return false;
		}
		/**
		 * 7. Create an own property P on object O whose state is<br>
		 * a. [[Value]]: V, <br>
		 * b. [[Writeable]]: true, <br>
		 * c. [[Enumerable]]: true, <br>
		 * d. [[Dynamic]]: true. <br>
		 */
		return object.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	/**
	 * <pre>
	 * put( name, value
	 * 		? BaseObject.TRUE
	 * 		: BaseObject.FALSE, true, true, true );
	 * </pre>
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @return
	 */
	public static final boolean put(final BaseObject<?> object, final String name, final boolean value) {
		return Base.put( object, name, value
				? BaseObject.TRUE
				: BaseObject.FALSE, true, true, true );
	}
	
	/**
	 * <p>
	 * <b> Alias for: set(name, value, true, true, true) </b>
	 * </p>
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @return
	 */
	public static final boolean put(final BaseObject<?> object, final String name, final double value) {
		return Base.put( object, name, value, true, true, true );
	}
	
	/**
	 * <pre>
	 * 8.6.2.2 [[Put]] (P, V, Strict) 
	 * For brevity, the changes caused by [[Put]] are described below in a self contained manner. But [[Put]] 
	 * must not be able to cause and state transitions that wouldn‘t be allowed by [[SetOwnProperty]]. 
	 * When the [[Put]] method of O is called with property P and value V, the following steps are taken: 
	 *  
	 * 1. Call the [[GetOwnProperty]] method of O with name P. 
	 * 2. If Result(1) is a DDesc, then 
	 * 		a. Get the [[Writeable]] component of Result(1). 
	 * 		b. If Result(2a) is true, then  
	 * 			i. Set the [[Value]] component of property P of O to V. 
	 * 			ii. Return true. 
	 * 		c. If Strict is true, then throw TypeError. 
	 * 		d. Else return false. 
	 * 3. Call the [[GetProperty]] method of O with name P. 
	 * 4. If Result(3) is a ProceduralDescription, then 
	 * 		a. Get the [[Setter]] component of Result(3). 
	 * 		b. If Result(4a) is undefined, then 
	 * 			i. If Strict is true, then throw TypeError. 
	 * 			ii. Else return false. 
	 * 		e. Call Result(4a) as a method on O with argument V. 
	 * 		f. Return true. 
	 * 5. Get the [[Extensible]] property of object O. 
	 * 6. If Result(5) is false, then. 
	 * 		a. If Strict is true, then throw TypeError. 
	 * 		b. Else return false. 
	 * 7. Create an own property P on object O whose state is 
	 * 		a. [[Value]]: V, 
	 * 		b. [[Writeable]]: true, 
	 * 		c. [[Enumerable]]: true, 
	 * 		d. [[Dynamic]]: true. 
	 * 8. Return true.
	 * </pre>
	 * 
	 * Note, however, that if O is an Array object, it has a more elaborate
	 * [[Put]] method (15.4.5.1).
	 * 
	 * <p>
	 * In any implementation should behave exactly as put(name, value, writable,
	 * enumerable, dynamic)
	 * </p>
	 * 
	 * In scripting, implicit store object specifier (i.e. "b = 5" as opposed to
	 * "a.b = 5") should use this method.
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public static final boolean put(
			final BaseObject<?> object,
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		{
			/**
			 * 1. Call the [[GetOwnProperty]] method of O with name P.
			 */
			final BaseProperty property = object.baseGetOwnProperty( name );
			/**
			 * 2. If Result(1) is a DDesc, then
			 */
			if (property != null) {
				/**
				 * a. Get the [[Writeable]] component of Result(1).<br>
				 * b. If Result(2a) is true, then
				 */
				if (property.isWritable( name )) {
					/**
					 * i. Set the [[Value]] component of property P of O to V.<br>
					 * ii. Return true.
					 */
					return property.propertySet( object, name, Base.forDouble( value ), writable, enumerable, dynamic );
				}
				/**
				 * c. If Strict is true, then throw TypeError.<br>
				 * d. Else return false.
				 */
				return false;
			}
		}
		{
			final BaseObject<?> prototype = object.basePrototype();
			if (prototype != null) {
				/**
				 * 3. Call the [[GetProperty]] method of O with name P.
				 */
				final BaseProperty property = Base.getProperty( object, name );
				/**
				 * 4. If Result(3) is a ProceduralDescription, then<br>
				 */
				if (property != null && property.isProceduralSetter( name )) {
					/**
					 * a. Get the [[Setter]] component of Result(3).<br>
					 * b. If Result(4a) is undefined, then <br>
					 * i. If Strict is true, then throw TypeError.<br>
					 * ii. Else return false. <br>
					 * e. Call Result(4a) as a method on O with argument V.<br>
					 * f. Return true.<br>
					 */
					return property.propertySet( object, name, Base.forDouble( value ), writable, enumerable, dynamic );
				}
			}
		}
		/**
		 * 5. Get the [[Extensible]] property of object O.<br>
		 * 6. If Result(5) is false, then.
		 */
		if (!object.baseIsExtensible()) {
			return false;
		}
		/**
		 * 7. Create an own property P on object O whose state is<br>
		 * a. [[Value]]: V, <br>
		 * b. [[Writeable]]: true, <br>
		 * c. [[Enumerable]]: true, <br>
		 * d. [[Dynamic]]: true. <br>
		 */
		return object.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	/**
	 * <p>
	 * <b> Alias for: set(name, value, true, true, true) </b>
	 * </p>
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @return
	 */
	public static boolean put(final BaseObject<?> object, final String name, final long value) {
		return Base.put( object, name, value, true, true, true );
	}
	
	/**
	 * <pre>
	 * 8.6.2.2 [[Put]] (P, V, Strict) 
	 * For brevity, the changes caused by [[Put]] are described below in a self contained manner. But [[Put]] 
	 * must not be able to cause and state transitions that wouldn‘t be allowed by [[SetOwnProperty]]. 
	 * When the [[Put]] method of O is called with property P and value V, the following steps are taken: 
	 *  
	 * 1. Call the [[GetOwnProperty]] method of O with name P. 
	 * 2. If Result(1) is a DDesc, then 
	 * 		a. Get the [[Writeable]] component of Result(1). 
	 * 		b. If Result(2a) is true, then  
	 * 			i. Set the [[Value]] component of property P of O to V. 
	 * 			ii. Return true. 
	 * 		c. If Strict is true, then throw TypeError. 
	 * 		d. Else return false. 
	 * 3. Call the [[GetProperty]] method of O with name P. 
	 * 4. If Result(3) is a ProceduralDescription, then 
	 * 		a. Get the [[Setter]] component of Result(3). 
	 * 		b. If Result(4a) is undefined, then 
	 * 			i. If Strict is true, then throw TypeError. 
	 * 			ii. Else return false. 
	 * 		e. Call Result(4a) as a method on O with argument V. 
	 * 		f. Return true. 
	 * 5. Get the [[Extensible]] property of object O. 
	 * 6. If Result(5) is false, then. 
	 * 		a. If Strict is true, then throw TypeError. 
	 * 		b. Else return false. 
	 * 7. Create an own property P on object O whose state is 
	 * 		a. [[Value]]: V, 
	 * 		b. [[Writeable]]: true, 
	 * 		c. [[Enumerable]]: true, 
	 * 		d. [[Dynamic]]: true. 
	 * 8. Return true.
	 * </pre>
	 * 
	 * Note, however, that if O is an Array object, it has a more elaborate
	 * [[Put]] method (15.4.5.1).
	 * 
	 * <p>
	 * In any implementation should behave exactly as put(name, value, writable,
	 * enumerable, dynamic)
	 * </p>
	 * 
	 * In scripting, implicit store object specifier (i.e. "b = 5" as opposed to
	 * "a.b = 5") should use this method.
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public static boolean put(
			final BaseObject<?> object,
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		{
			/**
			 * 1. Call the [[GetOwnProperty]] method of O with name P.
			 */
			final BaseProperty property = object.baseGetOwnProperty( name );
			/**
			 * 2. If Result(1) is a DDesc, then
			 */
			if (property != null) {
				/**
				 * a. Get the [[Writeable]] component of Result(1).<br>
				 * b. If Result(2a) is true, then
				 */
				if (property.isWritable( name )) {
					/**
					 * i. Set the [[Value]] component of property P of O to V.<br>
					 * ii. Return true.
					 */
					return property.propertySet( object, name, Base.forLong( value ), writable, enumerable, dynamic );
				}
				/**
				 * c. If Strict is true, then throw TypeError.<br>
				 * d. Else return false.
				 */
				return false;
			}
		}
		{
			final BaseObject<?> prototype = object.basePrototype();
			if (prototype != null) {
				/**
				 * 3. Call the [[GetProperty]] method of O with name P.
				 */
				final BaseProperty property = Base.getProperty( object, name );
				/**
				 * 4. If Result(3) is a ProceduralDescription, then<br>
				 */
				if (property != null && property.isProceduralSetter( name )) {
					/**
					 * a. Get the [[Setter]] component of Result(3).<br>
					 * b. If Result(4a) is undefined, then <br>
					 * i. If Strict is true, then throw TypeError.<br>
					 * ii. Else return false. <br>
					 * e. Call Result(4a) as a method on O with argument V.<br>
					 * f. Return true.<br>
					 */
					return property.propertySet( object, name, Base.forLong( value ), writable, enumerable, dynamic );
				}
			}
		}
		/**
		 * 5. Get the [[Extensible]] property of object O.<br>
		 * 6. If Result(5) is false, then.
		 */
		if (!object.baseIsExtensible()) {
			return false;
		}
		/**
		 * 7. Create an own property P on object O whose state is<br>
		 * a. [[Value]]: V, <br>
		 * b. [[Writeable]]: true, <br>
		 * c. [[Enumerable]]: true, <br>
		 * d. [[Dynamic]]: true. <br>
		 */
		return object.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	/**
	 * <p>
	 * <b> Alias for: set(name, value, true, true, true) </b>
	 * </p>
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @return
	 */
	public static boolean put(final BaseObject<?> object, final String name, final String value) {
		return Base.put( object, name, value, true, true, true );
	}
	
	/**
	 * <pre>
	 * 8.6.2.2 [[Put]] (P, V, Strict)
	 *  
	 * For brevity, the changes caused by [[Put]] are described below in a self contained manner. But [[Put]] 
	 * must not be able to cause and state transitions that wouldn‘t be allowed by [[SetOwnProperty]]. 
	 * When the [[Put]] method of O is called with property P and value V, the following steps are taken:
	 *  
	 * 1. Call the [[GetOwnProperty]] method of O with name P. 
	 * 2. If Result(1) is a DDesc, then 
	 * 		a. Get the [[Writeable]] component of Result(1). 
	 * 		b. If Result(2a) is true, then  
	 * 			i. Set the [[Value]] component of property P of O to V. 
	 * 			ii. Return true. 
	 * 		c. If Strict is true, then throw TypeError. 
	 * 		d. Else return false. 
	 * 3. Call the [[GetProperty]] method of O with name P. 
	 * 4. If Result(3) is a ProceduralDescription, then 
	 * 		a. Get the [[Setter]] component of Result(3). 
	 * 		b. If Result(4a) is undefined, then 
	 * 			i. If Strict is true, then throw TypeError. 
	 * 			ii. Else return false. 
	 * 		e. Call Result(4a) as a method on O with argument V. 
	 * 		f. Return true. 
	 * 5. Get the [[Extensible]] property of object O. 
	 * 6. If Result(5) is false, then. 
	 * 		a. If Strict is true, then throw TypeError. 
	 * 		b. Else return false. 
	 * 7. Create an own property P on object O whose state is 
	 * 		a. [[Value]]: V, 
	 * 		b. [[Writeable]]: true, 
	 * 		c. [[Enumerable]]: true, 
	 * 		d. [[Dynamic]]: true. 
	 * 8. Return true.
	 * </pre>
	 * 
	 * Note, however, that if O is an Array object, it has a more elaborate
	 * [[Put]] method (15.4.5.1).
	 * 
	 * <p>
	 * In any implementation should behave exactly as put(name, value, writable,
	 * enumerable, dynamic)
	 * </p>
	 * 
	 * In scripting, implicit store object specifier (i.e. "b = 5" as opposed to
	 * "a.b = 5") should use this method.
	 * 
	 * @param object
	 * @param name
	 * @param value
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 * @return
	 */
	public static boolean put(
			final BaseObject<?> object,
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		{
			/**
			 * 1. Call the [[GetOwnProperty]] method of O with name P.
			 */
			final BaseProperty property = object.baseGetOwnProperty( name );
			/**
			 * 2. If Result(1) is a DDesc, then
			 */
			if (property != null) {
				/**
				 * a. Get the [[Writeable]] component of Result(1).<br>
				 * b. If Result(2a) is true, then
				 */
				if (property.isWritable( name )) {
					/**
					 * i. Set the [[Value]] component of property P of O to V.<br>
					 * ii. Return true.
					 */
					return property.propertySet( object, name, Base.forString( value ), writable, enumerable, dynamic );
				}
				/**
				 * c. If Strict is true, then throw TypeError.<br>
				 * d. Else return false.
				 */
				return false;
			}
		}
		{
			final BaseObject<?> prototype = object.basePrototype();
			if (prototype != null) {
				/**
				 * 3. Call the [[GetProperty]] method of O with name P.
				 */
				final BaseProperty property = Base.getProperty( object, name );
				/**
				 * 4. If Result(3) is a ProceduralDescription, then<br>
				 */
				if (property != null && property.isProceduralSetter( name )) {
					/**
					 * a. Get the [[Setter]] component of Result(3).<br>
					 * b. If Result(4a) is undefined, then <br>
					 * i. If Strict is true, then throw TypeError.<br>
					 * ii. Else return false. <br>
					 * e. Call Result(4a) as a method on O with argument V.<br>
					 * f. Return true.<br>
					 */
					return property.propertySet( object, name, Base.forString( value ), writable, enumerable, dynamic );
				}
			}
		}
		/**
		 * 5. Get the [[Extensible]] property of object O.<br>
		 * 6. If Result(5) is false, then.
		 */
		if (!object.baseIsExtensible()) {
			return false;
		}
		/**
		 * 7. Create an own property P on object O whose state is<br>
		 * a. [[Value]]: V, <br>
		 * b. [[Writeable]]: true, <br>
		 * c. [[Enumerable]]: true, <br>
		 * d. [[Dynamic]]: true. <br>
		 */
		return object.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	private Base() {
		// ignore
	}
	
}
