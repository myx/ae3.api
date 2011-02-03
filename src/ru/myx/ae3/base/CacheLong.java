package ru.myx.ae3.base;

final class CacheLong extends CacheNumber implements CacheQueueEntry {
	
	final long																value;
	
	CacheLong																next;
	
	private CacheReference<CacheLong, PrimitiveNumberTrueLong>				object;
	
	CacheLong																prev;
	
	private static final long												serialVersionUID	= 3569412318170056271L;
	
	private static final CacheReference<CacheLong, PrimitiveNumberTrueLong>	EMPTY_REFERENCE		= new CacheReference<CacheLong, PrimitiveNumberTrueLong>( null,
																										null,
																										null );
	
	CacheLong(final long longValue) {
		this.value = longValue;
		this.object = CacheLong.EMPTY_REFERENCE;
	}
	
	/**
	 * Needed in case you wanna try putting special predefined values in cache
	 * 
	 * @param value
	 * @param number
	 */
	CacheLong(final long value, final PrimitiveNumberTrueLong number) {
		this.value = value;
		this.object = new CacheReference<CacheLong, PrimitiveNumberTrueLong>( this, number, CacheQueue.QUEUE_LONG );
	}
	
	@Override
	public final void cleanup() {
		if (this.object.cache == this) {
			Base.cacheRemoveLong( this.value, this );
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
		return obj == this || obj instanceof Number && ((Number) obj).doubleValue() == this.value;
	}
	
	@Override
	public final float floatValue() {
		return this.value;
	}
	
	@Override
	final PrimitiveNumberTrueLong getPrimitive() {
		PrimitiveNumberTrueLong primitive = this.object.get();
		if (primitive != null) {
			return primitive;
		}
		synchronized (this) {
			primitive = this.object.get();
			if (primitive != null) {
				return primitive;
			}
			primitive = new PrimitiveNumberTrueLong( this );
			this.object = new CacheReference<CacheLong, PrimitiveNumberTrueLong>( this,
					primitive,
					CacheQueue.QUEUE_LONG );
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
		return this.value;
	}
	
	@Override
	public final String toString() {
		return String.valueOf( this.value );
	}
	
}
