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

final class ExecDirectString implements ExecDirect<String>, CharSequence {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8147435374323547706L;
	
	String						string;
	
	ExecDirectString() {
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
		return "string";
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
		return false;
	}
	
	@Override
	public boolean baseIsPrimitiveNumber() {
		return false;
	}
	
	@Override
	public boolean baseIsPrimitiveString() {
		return true;
	}
	
	@Override
	public BaseObject<?> basePrototype() {
		return BasePrimitiveString.PROTOTYPE;
	}
	
	@Override
	public void basePutAll(final BaseObject<?> value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return this.string.length() == 0
				? BaseObject.FALSE
				: BaseObject.TRUE;
	}
	
	@Override
	public BasePrimitiveNumber baseToInt32() {
		return Base.forString( this.string ).baseToInt32();
	}
	
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return Base.forString( this.string ).baseToInteger();
	}
	
	@Override
	public BasePrimitiveNumber baseToNumber() {
		return Base.forString( this.string ).baseToNumber();
	}
	
	@Override
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this;
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return Base.forString( this.string );
	}
	
	@Override
	public String baseValue() {
		return this.string;
	}
	
	@Override
	public char charAt(final int index) {
		return this.string.charAt( index );
	}
	
	@Override
	public double doubleValue() {
		try {
			return Double.parseDouble( this.string );
		} catch (final NumberFormatException e) {
			return Double.NaN;
		}
	}
	
	@Override
	public boolean equals(final Object obj) {
		return this == obj || obj instanceof CharSequence && this.string.contentEquals( ((CharSequence) obj) );
	}
	
	@Override
	public int hashCode() {
		return this.string.hashCode();
	}
	
	@Override
	public int intValue() {
		return (int) this.doubleValue();
	}
	
	@Override
	public int length() {
		return this.string.length();
	}
	
	public final ExecDirect<?> setValue(final String string) {
		this.string = string;
		return this;
	}
	
	@Override
	public String stringValue() {
		return this.string;
	}
	
	@Override
	public CharSequence subSequence(final int start, final int end) {
		return this.string.subSequence( start, end );
	}
	
	@Override
	public BaseObject<?> toDetachable() {
		return Base.forString( this.string );
	}
	
	@Override
	public String toString() {
		return this.string;
	}
	
}
