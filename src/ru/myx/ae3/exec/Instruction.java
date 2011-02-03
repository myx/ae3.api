/*
 * Created on 05.05.2006
 */
package ru.myx.ae3.exec;

import java.util.Iterator;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseAbstract;
import ru.myx.ae3.base.BaseArray;
import ru.myx.ae3.base.BaseFunction;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitive;
import ru.myx.ae3.base.BasePrimitiveBoolean;
import ru.myx.ae3.base.BasePrimitiveNumber;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.base.BaseProperty;
import ru.myx.ae3.base.ToPrimitiveHint;

/**
 * @author myx
 */
public abstract class Instruction extends BaseAbstract<Instruction> {
	
	private static final String pad(final String name, final int length) {
		int add = length - name.length();
		if (add < 0) {
			return name.substring( 0, length );
		}
		if (add <= 0) {
			return name;
		}
		final StringBuilder builder = new StringBuilder( length );
		builder.append( name );
		for (; add > 0; add--) {
			builder.append( ' ' );
		}
		return builder.toString();
	}
	
	/**
	 * @param constant
	 * @return
	 */
	protected static final String padCONSTANT(final int constant) {
		return Instruction.pad( String.valueOf( constant ), 12 );
	}
	
	/**
	 * @param name
	 * @return
	 */
	protected static final String padOPCODE(final String name) {
		return Instruction.pad( name, 12 );
	}
	
	/**
	 * @param state
	 * @return
	 */
	protected static final String padSTATE(final ExecStateCode state) {
		return Instruction.pad( String.valueOf( state ), 5 );
	}
	
	@Override
	public BaseArray<?, ?> baseArray() {
		return null;
	}
	
	@Override
	public BaseFunction<?> baseCall() {
		return null;
	}
	
	@Override
	public String baseClass() {
		return "Instruction";
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
		return null;
	}
	
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
	}
	
	@Override
	public BasePrimitiveNumber baseToInt32() {
		return BasePrimitiveNumber.NAN;
	}
	
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return BasePrimitiveNumber.NAN;
	}
	
	@Override
	public BasePrimitiveNumber baseToNumber() {
		return BasePrimitiveNumber.NAN;
	}
	
	@Override
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this.baseToString();
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return Base.forString( this.toString() );
	}
	
	@Override
	public Instruction baseValue() {
		return this;
	}
	
	/**
	 * null - instruction finished normally.
	 * 
	 * @param process
	 * @return state code
	 * @throws Exception
	 */
	public abstract ExecStateCode execCall(final ExecProcess process) throws Exception;
	
	/**
	 * @return
	 */
	public int getConstant() {
		return 0;
	}
	
	/**
	 * The number of arguments needed in stack to fill in arguments.
	 * 
	 * @return int
	 */
	public abstract int getOperandCount();
	
	/**
	 * The number of results this instruction will push into the stack.
	 * 
	 * @return int
	 */
	public abstract int getResultCount();
	
	/**
	 * Only for good code dumps. Doesn't affect execution and compilation at
	 * all.
	 * 
	 * @return
	 */
	public boolean isRelativeAddressInConstant() {
		return false;
	}
	
	/**
	 * return true when RR is saved to stack but could be replaced by
	 * NoStackPush with target set to RR register. False by default.
	 * 
	 * @return boolean
	 */
	public boolean isStackPush() {
		return false;
	}
	
	/**
	 * Replace instruction to produce no result. Returns null when no
	 * replacement available. Null by default.
	 * 
	 * May return this if replacement modified given object.
	 * 
	 * @param state
	 * 
	 * @return instruction
	 */
	public Instruction replaceNextForState(final ExecStateCode state) {
		assert state != null && state != ExecStateCode.NEXT : "wrong state: " + state;
		return null;
	}
	
	/**
	 * Replace instruction to produce no result. Returns null when no
	 * replacement available. Null by default.
	 * 
	 * May return this if replacement modified given object.
	 * 
	 * @return instruction
	 */
	public Instruction replaceStoreForOutput() {
		return null;
	}
	
	/**
	 * Replace instruction to produce no result. Returns null when no
	 * replacement available. Null by default.
	 * 
	 * May return this if replacement modified given object.
	 * 
	 * @return instruction
	 */
	public Instruction replaceStoreNoStackPush() {
		return null;
	}
	
	/**
	 * String of code to be materializable.
	 * 
	 * @return string
	 */
	public abstract String toCode();
	
	/**
	 * String representation for instruction. Returns toCode() by default.
	 * 
	 * @return string
	 */
	@Override
	public String toString() {
		return this.toCode();
	}
}
