package ru.myx.ae3.base;

final class CacheDouble extends CacheNumber implements CacheQueueEntry {
	
	private final double													value;
	
	private CacheReference<CacheDouble, PrimitiveNumberTrue>				object;
	
	private static final long												serialVersionUID	= 3569418418170056271L;
	
	private static final CacheReference<CacheDouble, PrimitiveNumberTrue>	EMPTY_REFERENCE		= new CacheReference<CacheDouble, PrimitiveNumberTrue>( null,
																										null,
																										null );
	
	CacheDouble(final double value) {
		assert value != (long) value;
		this.value = value;
		this.object = CacheDouble.EMPTY_REFERENCE;
	}
	
	/**
	 * Needed in case you wanna try putting special predefined values in cache
	 * 
	 * @param value
	 * @param number
	 */
	CacheDouble(final double value, final PrimitiveNumberTrue number) {
		assert value != (long) value;
		this.value = value;
		this.object = new CacheReference<CacheDouble, PrimitiveNumberTrue>( this, number, CacheQueue.QUEUE_DOUBLE );
	}
	
	@Override
	public final void cleanup() {
		if (this.object.cache == this) {
			Base.cacheRemoveDouble( this.value, this );
		}
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
		
		final long thisBits = Double.doubleToLongBits( d1 );
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
				&& Double.doubleToLongBits( ((Number) obj).doubleValue() ) == Double.doubleToLongBits( this.value );
	}
	
	@Override
	public final float floatValue() {
		return (float) this.value;
	}
	
	@Override
	final PrimitiveNumberTrue getPrimitive() {
		PrimitiveNumberTrue primitive = this.object.get();
		if (primitive != null) {
			return primitive;
		}
		synchronized (this) {
			primitive = this.object.get();
			if (primitive != null) {
				return primitive;
			}
			primitive = new PrimitiveNumberTrue( this );
			this.object = new CacheReference<CacheDouble, PrimitiveNumberTrue>( this,
					primitive,
					CacheQueue.QUEUE_DOUBLE );
		}
		return primitive;
	}
	
	@Override
	public final int hashCode() {
		return this.getPrimitive().hashCode();
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
		return this.getPrimitive().toString();
	}
	
}
