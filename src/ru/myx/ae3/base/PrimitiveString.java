package ru.myx.ae3.base;

import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * @author myx
 * 
 */
@ReflectionDisable
class PrimitiveString extends BasePrimitiveString {
	
	private static final double parseLong(final String s, final int radix) throws NumberFormatException {
		final int max = s.length();
		
		if (max <= 0) {
			return Double.NEGATIVE_INFINITY;
		}
		{
			long result = 0;
			boolean negative = false;
			int i = 0;
			final long limit;
			final long multmin;
			int digit;
			if (s.charAt( 0 ) == '-') {
				negative = true;
				limit = Long.MIN_VALUE;
				i++;
			} else {
				limit = -Long.MAX_VALUE;
			}
			multmin = limit / radix;
			if (i < max) {
				digit = Character.digit( s.charAt( i++ ), radix );
				if (digit < 0) {
					return Double.NEGATIVE_INFINITY;
				}
				result = -digit;
			}
			while (i < max) {
				// Accumulating negatively avoids surprises near MAX_VALUE
				digit = Character.digit( s.charAt( i++ ), radix );
				if (digit < 0) {
					return Double.NEGATIVE_INFINITY;
				}
				if (result < multmin) {
					return Double.NEGATIVE_INFINITY;
				}
				result *= radix;
				if (result < limit + digit) {
					return Double.NEGATIVE_INFINITY;
				}
				result -= digit;
			}
			if (negative) {
				if (i > 1) {
					return result;
				}
				return Double.NEGATIVE_INFINITY;
			}
			return -result;
		}
	}
	
	private final String	value;
	
	PrimitiveString(final String value) {
		assert value != null : "Must not be NULL";
		assert value.length() != 0 : "Must not be empty";
		this.value = value;
	}
	
	/**
	 * The result is false if the argument is the empty string (its length is
	 * zero); otherwise the result is true.
	 * 
	 */
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
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
	 * The conversion of a string to a number value is similar overall to the
	 * determination of the number value for a numeric literal (section 7.8.3),
	 * but some of the details are different, so the process for converting a
	 * string numeric literal to a value of Number type is given here in full.
	 * This value is determined in two steps: first, a mathematical value (MV)
	 * is derived from the string numeric literal; second, this mathematical
	 * value is rounded as described below.
	 * <p>
	 * • The MV of StringNumericLiteral ::: [empty] is 0.
	 * <p>
	 * • The MV of StringNumericLiteral ::: StrWhiteSpace is 0.
	 * <p>
	 * • The MV of StringNumericLiteral ::: StrWhiteSpaceopt StrNumericLiteral
	 * StrWhiteSpaceopt is the MV of StrNumericLiteral, no matter whether white
	 * space is present or not.
	 * <p>
	 * • The MV of StrNumericLiteral ::: StrDecimalLiteral is the MV of
	 * StrDecimalLiteral.
	 * <p>
	 * • The MV of StrNumericLiteral ::: HexIntegerLiteral is the MV of
	 * HexIntegerLiteral.
	 * <p>
	 * • The MV of StrDecimalLiteral ::: StrUnsignedDecimalLiteral is the MV of
	 * StrUnsignedDecimalLiteral.
	 * <p>
	 * • The MV of StrDecimalLiteral::: + StrUnsignedDecimalLiteral is the MV of
	 * StrUnsignedDecimalLiteral.
	 * <p>
	 * • The MV of StrDecimalLiteral::: - StrUnsignedDecimalLiteral is the
	 * negative of the MV of StrUnsignedDecimalLiteral. (Note that if the MV of
	 * StrUnsignedDecimalLiteral is 0, the negative of this MV is also 0. The
	 * rounding rule described below handles the conversion of this sign less
	 * mathematical zero to a floating- point +0 or − −− −0 as appropriate.)
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral::: Infinity is 1010000 (a value so
	 * large that it will round to +∞ ∞∞ ∞).
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral::: DecimalDigits. is the MV of
	 * DecimalDigits.
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral::: DecimalDigits. DecimalDigits is
	 * the MV of the first DecimalDigits plus (the MV of the second
	 * DecimalDigits times 10−n), where n is the number of characters in the
	 * second DecimalDigits.
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral::: DecimalDigits. ExponentPart is
	 * the MV of DecimalDigits times 10e, where e is the MV of ExponentPart.
	 * <p>
	 * The MV of StrUnsignedDecimalLiteral::: DecimalDigits. DecimalDigits
	 * ExponentPart is (the MV of the first DecimalDigits plus (the MV of the
	 * second DecimalDigits times 10−n)) times 10e, where n is the number of
	 * characters in the second DecimalDigits and e is the MV of ExponentPart.
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral:::. DecimalDigits is the MV of
	 * DecimalDigits times 10−n, where n is the number of characters in
	 * DecimalDigits.
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral:::. DecimalDigits ExponentPart is
	 * the MV of DecimalDigits times 10e−n, where n is the number of characters
	 * in DecimalDigits and e is the MV of ExponentPart.
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral::: DecimalDigits is the MV of
	 * DecimalDigits.
	 * <p>
	 * • The MV of StrUnsignedDecimalLiteral::: DecimalDigits ExponentPart is
	 * the MV of DecimalDigits times 10e, where e is the MV of ExponentPart.
	 * <p>
	 * • The MV of DecimalDigits ::: DecimalDigit is the MV of DecimalDigit.
	 * <p>
	 * • The MV of DecimalDigits ::: DecimalDigits DecimalDigit is (the MV of
	 * DecimalDigits times 10) plus the MV of DecimalDigit.
	 * <p>
	 * • The MV of ExponentPart ::: ExponentIndicator SignedInteger is the MV of
	 * SignedInteger.
	 * <p>
	 * • The MV of SignedInteger ::: DecimalDigits is the MV of DecimalDigits.
	 * <p>
	 * • The MV of SignedInteger ::: + DecimalDigits is the MV of DecimalDigits.
	 * <p>
	 * • The MV of SignedInteger ::: - DecimalDigits is the negative of the MV
	 * of DecimalDigits.
	 * <p>
	 * • The MV of DecimalDigit ::: 0 or of HexDigit ::: 0 is 0.
	 * <p>
	 * • The MV of DecimalDigit ::: 1 or of HexDigit ::: 1 is 1.
	 * <p>
	 * • The MV of DecimalDigit ::: 2 or of HexDigit ::: 2 is 2.
	 * <p>
	 * • The MV of DecimalDigit ::: 3 or of HexDigit ::: 3 is 3.
	 * <p>
	 * • The MV of DecimalDigit ::: 4 or of HexDigit ::: 4 is 4.
	 * <p>
	 * • The MV of DecimalDigit ::: 5 or of HexDigit ::: 5 is 5.
	 * <p>
	 * • The MV of DecimalDigit ::: 6 or of HexDigit ::: 6 is 6.
	 * <p>
	 * • The MV of DecimalDigit ::: 7 or of HexDigit ::: 7 is 7.
	 * <p>
	 * • The MV of DecimalDigit ::: 8 or of HexDigit ::: 8 is 8.
	 * <p>
	 * • The MV of DecimalDigit ::: 9 or of HexDigit ::: 9 is 9.
	 * <p>
	 * • The MV of HexDigit ::: a or of HexDigit ::: A is 10.
	 * <p>
	 * • The MV of HexDigit ::: b or of HexDigit ::: B is 11.
	 * <p>
	 * • The MV of HexDigit ::: c or of HexDigit ::: C is 12.
	 * <p>
	 * • The MV of HexDigit ::: d or of HexDigit ::: D is 13.
	 * <p>
	 * • The MV of HexDigit ::: e or of HexDigit ::: E is 14.
	 * <p>
	 * • The MV of HexDigit ::: f or of HexDigit ::: F is 15.
	 * <p>
	 * • The MV of HexIntegerLiteral ::: 0x HexDigit is the MV of HexDigit.
	 * <p>
	 * • The MV of HexIntegerLiteral ::: 0X HexDigit is the MV of HexDigit.
	 * <p>
	 * • The MV of HexIntegerLiteral ::: HexIntegerLiteral HexDigit is (the MV
	 * of HexIntegerLiteral times 16) plus the MV of HexDigit.
	 * <p>
	 * <p>
	 * Once the exact MV for a string numeric literal has been determined, it is
	 * then rounded to a value of the Number type. If the MV is 0, then the
	 * rounded value is +0 unless the first non white space character in the
	 * string numeric literal is ‘-’, in which case the rounded value is −0.
	 * Otherwise, the rounded value must be the number value for the MV (in the
	 * sense defined in section 8.5), unless the literal includes a
	 * StrUnsignedDecimalLiteral and the literal has more than 20 significant
	 * digits, in which case the number value may be either the number value for
	 * the MV of a literal produced by replacing each significant digit after
	 * the 20th with a 0 digit or the number value for the MV of a literal
	 * produced by replacing each significant digit after the 20th with a 0
	 * digit and then incrementing the literal at the 20th digit position. A
	 * digit is significant if it is not part of an ExponentPart and
	 * <p>
	 * • it is not 0; or
	 * <p>
	 * • there is a nonzero digit to its left and there is a nonzero digit, not
	 * in the ExponentPart, to its right.
	 * 
	 * 
	 */
	@Override
	public BasePrimitiveNumber baseToNumber() {
		try {
			/**
			 * • The MV of StringNumericLiteral ::: [empty] is 0.
			 */
			// empty string is not an instance of this class ^^^
			/**
			 * • The MV of StringNumericLiteral ::: StrWhiteSpace is 0.
			 */
			// both replace and trim do not create new object unless necessary.
			final String trim = this.value.replace( '\t', ' ' ).trim();
			if (trim.length() == 0) {
				return BasePrimitiveNumber.ZERO;
			}
			// !!! wrong - see specification - make Convert.Any.toBaseNumber...
			try {
				return Base.forDouble( Double.parseDouble( trim.replace( ',', '.' ) ) );
			} catch (final NumberFormatException t) {
				// ignore
			}
			final int length = trim.length();
			if (length > 2) {
				if (trim.charAt( 0 ) == '0') {
					final char c = trim.charAt( 1 );
					if (c == 'x' || c == 'X') {
						final double val = PrimitiveString.parseLong( trim.substring( 2 ), 16 );
						if (val == Double.NEGATIVE_INFINITY) {
							return BasePrimitiveNumber.NAN;
						}
						return Base.forLong( (long) val );
					}
				}
			}
			return BasePrimitiveNumber.NAN;
		} catch (final NumberFormatException e) {
			return BasePrimitiveNumber.NAN;
		}
	}
	
	@Override
	public final String baseValue() {
		return this.value;
	}
	
	/**
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
	 * Type x is this and is an object of any type.
	 */
	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof BaseObject<?>)) {
			return o instanceof CharSequence && o.equals( this.value );
		}
		final BaseObject<?> object = (BaseObject<?>) o;
		if (object.baseIsPrimitive()) {
			if (object.baseIsPrimitiveString()) {
				return this.value.equals( object.baseToString().baseValue() );
			}
			if (object.baseIsPrimitiveNumber()) {
				return this.baseToNumber().equals( o );
			}
			if (object.baseIsPrimitiveBoolean()) {
				return this.baseToNumber().equals( object.baseToNumber() );
			}
			return false;
		}
		final Object primitive = object.baseToPrimitive( ToPrimitiveHint.STRING );
		assert primitive != null : "Should NOT be NULL!";
		assert primitive != object : "Should not be equal to itself, not a primitive, class="
				+ object.getClass().getName();
		return this.equals( primitive );
	}
	
	@Override
	public final boolean isEmpty() {
		return false;
	}
	
	@Override
	public int length() {
		return this.value.length();
	}
	
	@Override
	public final String stringValue() {
		return this.value;
	}
	
	@Override
	public final String toString() {
		return this.value;
	}
}
