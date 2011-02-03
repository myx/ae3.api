package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitive;
import ru.myx.ae3.base.BaseProperty;

final class ExecProcessArgumentsProperty implements BaseProperty {
	
	static final BaseProperty	INSTANCE	= new ExecProcessArgumentsProperty();
	
	private ExecProcessArgumentsProperty() {
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
		assert ExecProcess.PROPERTY_BASE_ARGUMENTS == name : "Only for 'arguments'!";
		final ExecProcess process = (ExecProcess) instance;
		final ExecArguments<?> original = process.fldArguments;
		final ExecArguments<?> arguments = original.execDetachable();
		if (arguments != original) {
			process.fldArguments = arguments;
		}
		return arguments;
	}
	
	@Override
	public BaseObject<?> propertyGet(final BaseObject<?> instance, final String name) {
		assert name.length() == 9 && "arguments".equals( name ) : "Only for 'arguments'!";
		final ExecProcess process = (ExecProcess) instance;
		final ExecArguments<?> original = process.fldArguments;
		final ExecArguments<?> arguments = original.execDetachable();
		if (arguments != original) {
			process.fldArguments = arguments;
		}
		return arguments;
	}
	
	@Override
	public BaseObject<?> propertyGetAndSet(final BaseObject<?> instance, final String name, final BaseObject<?> value) {
		assert name.length() == 9 && "arguments".equals( name ) : "Only for 'arguments'!";
		final ExecProcess process = (ExecProcess) instance;
		final ExecArguments<?> original = process.fldArguments;
		final ExecArguments<?> arguments = original.execDetachable();
		if (arguments != original) {
			process.fldArguments = arguments;
		}
		return arguments;
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
