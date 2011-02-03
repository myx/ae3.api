package ru.myx.ae3.base;

/**
 * For saving special numbers to double cache.
 * 
 * @author myx
 * 
 */
final class StaticDouble extends CacheNumber {
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 6618715425427464816L;
	
	private final double				value;
	
	private final long					longBits;
	
	private final BasePrimitiveNumber	primitive;
	
	public StaticDouble(final double doubleValue, final BasePrimitiveNumber value) {
		this.value = doubleValue;
		this.longBits = Double.doubleToLongBits( doubleValue );
		this.primitive = value;
	}
	
	@Override
	public final int compareTo(final Number o) {
		final double d1 = this.value;
		final double d2 = o.doubleValue();
		if (d1 < d2) {
			return -1; // Neither val is NaN, thisVal is smaller
		}
		if (d1 > d2) {
			return 1; // Neither val is NaN, thisVal is larger
		}
		
		final long thisBits = this.longBits;
		final long anotherBits = Double.doubleToLongBits( d2 );
		
		return thisBits == anotherBits
				? 0
				: // Values are equal
				thisBits < anotherBits
						? -1
						: // (-0.0, 0.0) or (!NaN, NaN)
						1; // (0.0, -0.0) or (NaN, !NaN)
	}
	
	@Override
	public final double doubleValue() {
		return this.value;
	}
	
	@Override
	public final boolean equals(final Object obj) {
		return obj == this
				|| obj instanceof Number
				&& Double.doubleToLongBits( ((Number) obj).doubleValue() ) == this.longBits;
	}
	
	@Override
	public final float floatValue() {
		return (float) this.value;
	}
	
	/**
	 * Way to get a BaseNumber
	 * 
	 * @return
	 */
	@Override
	final BasePrimitiveNumber getPrimitive() {
		return this.primitive;
	}
	
	@Override
	public final int hashCode() {
		return this.primitive.hashCode();
	}
	
	@Override
	public final int intValue() {
		return (int) this.value;
	}
	
	@Override
	public final long longValue() {
		return (long) this.value;
	}
	
	@Override
	public final String toString() {
		return this.primitive.toString();
	}
}
