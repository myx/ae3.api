package ru.myx.ae3.base;

/**
 * 15.5.5.1 length
 * 
 * The number of characters in the String value represented by this String
 * object.
 * 
 * Once a String object is created, this property is unchanging. It has the
 * attributes { DontEnum, DontDelete, ReadOnly }.
 */
final class BasePropertyStringLength implements BaseProperty {
	
	@Override
	public boolean isDynamic(final String name) {
		return false;
	}
	
	@Override
	public boolean isEnumerable(final String name) {
		return false;
	}
	
	@Override
	public final boolean isProceduralSetter(final String name) {
		return false;
	}
	
	@Override
	public boolean isWritable(final String name) {
		return false;
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final BasePrimitive<?> name) {
		assert instance != null : "NULL java value!";
		return PrimitiveStringLengthValue.getLengthValue( instance.baseToString().length() );
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final String name) {
		assert instance != null : "NULL java value!";
		return PrimitiveStringLengthValue.getLengthValue( instance.baseToString().length() );
	}
	
	@Override
	public BaseObject<?> propertyGetAndSet(final BaseObject<?> instance, final String name, final BaseObject<?> value) {
		assert instance != null : "NULL java value!";
		return PrimitiveStringLengthValue.getLengthValue( instance.baseToString().length() );
	}
	
	@Override
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert instance != null : "NULL java value!";
		return false;
	}
}
