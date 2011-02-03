package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.exec.ExecFunction;
import ru.myx.ae3.exec.ExecProcess;
import ru.myx.ae3.exec.ExecStateCode;

final class PrimitiveStringLengthMethod extends BaseAbstract<ExecFunction> implements BaseFunction<ExecFunction>,
		ExecFunction {
	PrimitiveStringLengthMethod() {
		//
	}
	
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	@Override
	public BaseFunction<?> baseCall() {
		return this;
	}
	
	@Override
	public String baseClass() {
		return "function";
	}
	
	@Override
	public BaseFunction<?> baseConstruct() {
		return null;
	}
	
	@Override
	public BaseObject<?> baseConstructPrototype() {
		return null;
	}
	
	@Override
	public boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return false;
	}
	
	@Override
	public boolean baseDelete(final String name) {
		return false;
	}
	
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		return null;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		return null;
	}
	
	@Override
	public boolean baseHasInstance(final BaseObject<?> value) {
		return false;
	}
	
	@Override
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	@Override
	public BaseObject<?> basePrototype() {
		return BaseObject.PROTOTYPE;
	}
	
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return this.baseToNumber().baseToBoolean();
	}
	
	@Override
	public BasePrimitiveNumber baseToInt32() {
		return this.baseToNumber().baseToInt32();
	}
	
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return this.baseToNumber().baseToInteger();
	}
	
	@Override
	public BasePrimitiveNumber baseToNumber() {
		return BasePrimitiveNumber.NAN;
	}
	
	@Override
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this.baseToNumber();
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return this.baseToNumber().baseToString();
	}
	
	@Override
	public ExecFunction baseValue() {
		return this;
	}
	
	@Override
	public int execAcceptableArgumentCount() {
		return 0;
	}
	
	@Override
	public ExecStateCode execCall(final ExecProcess ctx) {
		assert ctx != null : "NULL context";
		assert ctx.r4RT != null && ctx.r4RT != BaseObject.UNDEFINED : "Not a static method!";
		return ctx.vmSetResult( ctx.r4RT.baseToString().length() );
	}
	
	@Override
	public String[] execFormalParameters() {
		return null;
	}
	
	@Override
	public boolean execHasNamedArguments() {
		return false;
	}
	
	@Override
	public boolean execIsConstant() {
		return true;
	}
	
	@Override
	public int execMinimalArgumentCount() {
		return 0;
	}
	
	@Override
	public Class<? extends Number> execResultClassJava() {
		return Number.class;
	}
	
	@Override
	public BaseObject<?> execScope() {
		return null;
	}
	
	@Override
	public ExecFunction functionItself() {
		return this;
	}
	
}
