package ru.myx.ae3.reflect;

import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitiveBoolean;
import ru.myx.ae3.base.BasePrimitiveNumber;
import ru.myx.ae3.base.BasePrimitiveString;

abstract class AbstractReflectImpl {
	public abstract BaseObject<?> classToBasePrototype(final Class<?> cls);
	
	public abstract ControlType<?, BaseObject<?>> classToControlType(final Class<?> cls);
	
	public abstract Reflected classToReflected(final Class<?> cls);
	
	public abstract ControlType<BaseObject<?>, BaseObject<?>> getControlTypeExactBaseObject();
	
	public abstract ControlType<Boolean, BasePrimitiveBoolean> getControlTypeExactBoolean();
	
	public abstract ControlType<Byte, BasePrimitiveNumber> getControlTypeExactByte();
	
	public abstract ControlType<Character, BasePrimitiveString> getControlTypeExactChar();
	
	public abstract ControlType<Double, BasePrimitiveNumber> getControlTypeExactDouble();
	
	public abstract ControlType<Float, BasePrimitiveNumber> getControlTypeExactFloat();
	
	public abstract ControlType<Integer, BasePrimitiveNumber> getControlTypeExactInteger();
	
	public abstract ControlType<Long, BasePrimitiveNumber> getControlTypeExactLong();
	
	public abstract ControlType<Number, BaseObject<Number>> getControlTypeExactNumber();
	
	public abstract ControlType<Object, BaseObject<?>> getControlTypeExactObject();
	
	public abstract ControlType<Object[], BaseObject<?>> getControlTypeExactObjectArray();
	
	public abstract ControlType<Short, BasePrimitiveNumber> getControlTypeExactShort();
	
	public abstract ControlType<String, BasePrimitiveString> getControlTypeExactString();
	
	public abstract ControlType<Void, BaseObject<?>> getControlTypeExactVoid();
}
