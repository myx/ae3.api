package ru.myx.ae3.base;

import java.util.Iterator;

import ru.myx.ae3.exec.ExecFunction;
import ru.myx.ae3.exec.ExecProcess;
import ru.myx.ae3.exec.ExecStateCode;

/**
 * The sole need for this class is in that we support both 'a'.length and
 * 'a'.length() access types
 * 
 * @author myx
 * 
 */
final class PrimitiveStringLengthValue extends BaseAbstract<Number> implements ExecFunction {
	private static final PrimitiveStringLengthValue[]	CACHE;
	static {
		CACHE = new PrimitiveStringLengthValue[128];
		for (int i = 0; i < 128; i++) {
			PrimitiveStringLengthValue.CACHE[i] = new PrimitiveStringLengthValue( i );
		}
	}
	
	private static final BaseFunction<?>				LENGTH_FUNCTION	= new PrimitiveStringLengthMethod();
	
	static final PrimitiveStringLengthValue getLengthValue(final int length) {
		assert length >= 0 : "Negative string length?";
		if (length < 128) {
			return PrimitiveStringLengthValue.CACHE[length];
		}
		return new PrimitiveStringLengthValue( length );
	}
	
	private final BasePrimitiveNumber	value;
	
	private PrimitiveStringLengthValue(final int length) {
		this.value = Base.forInteger( length );
	}
	
	@Override
	public BaseFunction<?> baseCall() {
		return PrimitiveStringLengthValue.LENGTH_FUNCTION;
	}
	
	@Override
	public String baseClass() {
		return "number";
	}
	
	@Override
	public BaseFunction<?> baseConstruct() {
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
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	@Override
	public BaseObject<?> basePrototype() {
		return BasePrimitiveNumber.PROTOTYPE;
	}
	
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return this.value.baseToBoolean();
	}
	
	@Override
	public BasePrimitiveNumber baseToInt32() {
		return this.value.baseToInt32();
	}
	
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return this.value.baseToInteger();
	}
	
	@Override
	public BasePrimitiveNumber baseToNumber() {
		return this.value;
	}
	
	@Override
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this.value;
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return this.value.baseToString();
	}
	
	@Override
	public Number baseValue() {
		return this.value.baseValue();
	}
	
	@Override
	public int execAcceptableArgumentCount() {
		return PrimitiveStringLengthValue.LENGTH_FUNCTION.functionItself().execAcceptableArgumentCount();
	}
	
	@Override
	public ExecStateCode execCall(final ExecProcess context) throws Exception {
		return PrimitiveStringLengthValue.LENGTH_FUNCTION.functionItself().execCall( context );
	}
	
	@Override
	public String[] execFormalParameters() {
		return null;
	}
	
	@Override
	public boolean execHasNamedArguments() {
		return PrimitiveStringLengthValue.LENGTH_FUNCTION.functionItself().execHasNamedArguments();
	}
	
	@Override
	public boolean execIsConstant() {
		return PrimitiveStringLengthValue.LENGTH_FUNCTION.functionItself().execIsConstant();
	}
	
	@Override
	public int execMinimalArgumentCount() {
		return PrimitiveStringLengthValue.LENGTH_FUNCTION.functionItself().execMinimalArgumentCount();
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
	public String toString() {
		return this.value.toString();
	}
	
}
