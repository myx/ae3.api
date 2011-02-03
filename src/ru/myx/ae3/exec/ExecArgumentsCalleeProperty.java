package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitive;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.base.BaseProperty;

final class ExecArgumentsCalleeProperty implements BaseProperty {
	
	static final BaseProperty	INSTANCE	= new ExecArgumentsCalleeProperty();
	
	private ExecArgumentsCalleeProperty() {
		//
	}
	
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
		assert name == BasePrimitiveString.PROPERTY_BASE_CALLEE : "Only for 'callee'!";
		return ((ExecArguments<?>) instance).execCallee();
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final String name) {
		assert name.length() == 6 && "callee".equals( name ) : "Only for 'callee'!";
		return ((ExecArguments<?>) instance).execCallee();
	}
	
	@Override
	public BaseObject<?> propertyGetAndSet(final BaseObject<?> instance, final String name, final BaseObject<?> value) {
		assert name.length() == 6 && "callee".equals( name ) : "Only for 'callee'!";
		return ((ExecArguments<?>) instance).execCallee();
	}
	
	@Override
	public boolean propertySet(
			final BaseObject<?> instance,
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
}
