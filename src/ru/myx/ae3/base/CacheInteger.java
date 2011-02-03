package ru.myx.ae3.base;

final class CacheInteger extends CacheNumber implements CacheQueueEntry {
	
	final int																		value;
	
	CacheInteger																	next;
	
	private CacheReference<CacheInteger, PrimitiveNumberTrueInteger>				object;
	
	CacheInteger																	prev;
	
	private static final long														serialVersionUID	= 7049992074129738712L;
	
	private static final CacheReference<CacheInteger, PrimitiveNumberTrueInteger>	EMPTY_REFERENCE		= new CacheReference<CacheInteger, PrimitiveNumberTrueInteger>( null,
																												null,
																												null );
	
	protected CacheInteger(final int value) {
		this.value = value;
		this.object = CacheInteger.EMPTY_REFERENCE;
	}
	
	CacheInteger(final int value, final PrimitiveNumberTrueInteger number) {
		this.value = value;
		this.object = new CacheReference<CacheInteger, PrimitiveNumberTrueInteger>( this,
				number,
				CacheQueue.QUEUE_INTEGER );
	}
	
	@Override
	public final void cleanup() {
		if (this.object.cache == this) {
			Base.cacheRemoveInt( this.value, this );
		}
	}
	
	@Override
	public final int compareTo(final Number o) {
		final int i1 = this.value;
		final double d2 = o.doubleValue();
		if (i1 < d2) {
			return -1; // Neither val is NaN, thisVal is smaller
		}
		if (i1 > d2) {
			return 1; // Neither val is NaN, thisVal is larger
		}
		
		return i1 == d2
				// NaN?
				? 0
				: -1;
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
	
	/**
	 * Way to get a BaseNumber
	 * 
	 * @return
	 */
	@Override
	final PrimitiveNumberTrueInteger getPrimitive() {
		PrimitiveNumberTrueInteger primitive = this.object.get();
		if (primitive != null) {
			return primitive;
		}
		synchronized (this) {
			primitive = this.object.get();
			if (primitive != null) {
				return primitive;
			}
			primitive = new PrimitiveNumberTrueInteger( this );
			this.object = new CacheReference<CacheInteger, PrimitiveNumberTrueInteger>( this,
					primitive,
					CacheQueue.QUEUE_INTEGER );
		}
		return primitive;
	}
	
	@Override
	public final int hashCode() {
		return this.value;
	}
	
	@Override
	public final int intValue() {
		return this.value;
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
