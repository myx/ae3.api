package ru.myx.ae3.common;

final class KeyFactoryString implements KeyFactory<String> {
	
	/**
	 * @param key
	 * @return
	 */
	@Override
	public final String keyJavaBoolean(final boolean key) {
		return key
				? "true"
				: "false";
	}
	
	/**
	 * @param key
	 * @return
	 */
	@Override
	public final String keyJavaDouble(final double key) {
		return key == (long) key
				? Long.toString( (long) key )
				: Double.toString( key );
	}
	
	/**
	 * @param key
	 * @return
	 */
	@Override
	public final String keyJavaFloat(final float key) {
		return key == (long) key
				? Long.toString( (long) key )
				: Float.toString( key );
	}
	
	/**
	 * @param key
	 * @return
	 */
	@Override
	public final String keyJavaInt(final int key) {
		switch (key) {
		case 0:
			return "0";
		case 1:
			return "1";
		case 2:
			return "2";
		case -1:
			return "-1";
		case Integer.MIN_VALUE:
			return "-2147483648";
		default:
			return Integer.toString( key );
		}
	}
	
	/**
	 * @param key
	 * @return
	 */
	@Override
	public final String keyJavaLong(final long key) {
		return Long.toString( key );
	}
	
	/**
	 * @param key
	 * @return
	 */
	@Override
	public final String keyJavaString(final String key) {
		return key;
	}
}
