package ru.myx.ae3.exec;

import java.util.Iterator;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseArray;
import ru.myx.ae3.base.BaseFunction;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitive;
import ru.myx.ae3.base.BasePrimitiveBoolean;
import ru.myx.ae3.base.BasePrimitiveNumber;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.base.BaseProperty;
import ru.myx.ae3.base.ToPrimitiveHint;

final class ExecDirectNumber extends Number implements ExecDirect<Number> {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8147435374323547706L;
	
	double						number;
	
	ExecDirectNumber() {
		//
	}
	
	@Override
	public BaseArray<? extends Object, ? extends Object> baseArray() {
		return null;
	}
	
	@Override
	public BaseFunction<?> baseCall() {
		return null;
	}
	
	@Override
	public String baseClass() {
		return "number";
	}
	
	@Override
	public void baseClear() {
		//
	}
	
	@Override
	public BaseFunction<?> baseConstruct() {
		// TODO Auto-generated method stub
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
	public BaseObject<?> baseGet(final BasePrimitiveString name, final BaseObject<?> defaultValue) {
		return null;
	}
	
	@Override
	public BaseObject<?> baseGet(final String name) {
		return null;
	}
	
	@Override
	public BaseObject<?> baseGet(final String name, final BaseObject<?> defaultValue) {
		return null;
	}
	
	@Override
	public boolean baseGetBoolean(final String name, final boolean defaultValue) {
		return false;
	}
	
	@Override
	public double baseGetDouble(final String name, final double defaultValue) {
		return 0;
	}
	
	@Override
	public long baseGetInteger(final String name, final long defaultValue) {
		return 0;
	}
	
	@Override
	public Iterator<String> baseGetIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public Iterator<String> baseGetOwnAllIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return null;
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
	public String baseGetString(final String name, final String defaultValue) {
		return null;
	}
	
	@Override
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	@Override
	public boolean baseHasProperties() {
		return false;
	}
	
	@Override
	public boolean baseIsExtensible() {
		return false;
	}
	
	@Override
	public boolean baseIsPrimitive() {
		return true;
	}
	
	@Override
	public boolean baseIsPrimitiveBoolean() {
		return false;
	}
	
	@Override
	public boolean baseIsPrimitiveInteger() {
		return this.number == (long) this.number;
	}
	
	@Override
	public boolean baseIsPrimitiveNumber() {
		return true;
	}
	
	@Override
	public boolean baseIsPrimitiveString() {
		return false;
	}
	
	@Override
	public BaseObject<?> basePrototype() {
		return BasePrimitiveNumber.PROTOTYPE;
	}
	
	@Override
	public final void basePutAll(final BaseObject<?> value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return this.number == 0 || Double.isNaN( this.number )
				? BaseObject.FALSE
				: BaseObject.TRUE;
	}
	
	@Override
	public final BasePrimitiveNumber baseToInt32() {
		return Base.forInteger( (int) (long) this.number );
	}
	
	@Override
	public final BasePrimitiveNumber baseToInteger() {
		return Base.forLong( (long) this.number );
	}
	
	@Override
	public final BasePrimitiveNumber baseToNumber() {
		return Base.forDouble( this.number );
	}
	
	@Override
	public final BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return Double.isNaN( this.number )
				? BasePrimitiveNumber.NAN
				: this;
	}
	
	@Override
	public final BasePrimitiveString baseToString() {
		return this.baseToNumber().baseToString();
	}
	
	@Override
	public final Number baseValue() {
		return this;
	}
	
	@Override
	public final double doubleValue() {
		return this.number;
	}
	
	@Override
	public final float floatValue() {
		return (float) this.number;
	}
	
	@Override
	public final int intValue() {
		return (int) (long) this.number;
	}
	
	@Override
	public final long longValue() {
		return (long) this.number;
	}
	
	public final ExecDirect<?> setValue(final double number) {
		this.number = number;
		return this;
	}
	
	@Override
	public String stringValue() {
		return this.number == (long) this.number
				? String.valueOf( (long) this.number )
				: String.valueOf( this.number );
	}
	
	@Override
	public BaseObject<?> toDetachable() {
		return Base.forDouble( this.number );
	}
	
	@Override
	public String toString() {
		return this.number == (long) this.number
				? String.valueOf( (long) this.number )
				: String.valueOf( this.number );
	}
	
}
