/**
 * 
 */
package ru.myx.ae3.base;

/**
 * @author myx
 * 
 */
class CacheString implements CacheQueueEntry {
	
	private static final long									serialVersionUID	= 3385532496929251221L;
	
	final String												value;
	
	final int													hash;
	
	CacheString													next;
	
	private CacheReference<CacheString, BasePrimitiveString>	object;
	
	CacheString(final String value) {
		this.value = value;
		this.hash = value.hashCode();
		this.object = null;
	}
	
	CacheString(final String value, final BasePrimitiveString number) {
		this.value = value;
		this.hash = value.hashCode();
		this.object = new CacheReference<CacheString, BasePrimitiveString>( this, //
				number,
				CacheQueue.QUEUE_STRING );
	}
	
	@Override
	public final void cleanup() {
		if (this.object.cache.object == this.object) {
			Base.cacheRemoveString( this.value, this );
		}
	}
	
	final BasePrimitiveString getPrimitive() {
		{
			final BasePrimitiveString primitive = this.object == null
					? null
					: this.object.get();
			if (primitive != null) {
				return primitive;
			}
		}
		synchronized (this) {
			{
				final BasePrimitiveString primitive = this.object == null
						? null
						: this.object.get();
				if (primitive != null) {
					return primitive;
				}
			}
			final BasePrimitiveString primitive = new PrimitiveString( this.value );
			this.object = new CacheReference<CacheString, BasePrimitiveString>( this,
					primitive,
					CacheQueue.QUEUE_STRING );
			return primitive;
		}
	}
	
	@Override
	public final int hashCode() {
		return this.value.hashCode();
	}
	
	@Override
	public final String toString() {
		return this.value;
	}
}
