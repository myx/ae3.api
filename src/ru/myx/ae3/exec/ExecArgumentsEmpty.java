/**
 * 
 */
package ru.myx.ae3.exec;

import java.util.Iterator;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseArrayAdvanced;
import ru.myx.ae3.base.BaseFunction;
import ru.myx.ae3.base.BaseNativeArray;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.help.Format;
import ru.myx.ae3.status.StatusInfo;

/**
 * @author myx
 * 
 */
final class ExecArgumentsEmpty extends ExecArgumentsAbstract<ExecArgumentsEmpty> {
	public static final ExecArgumentsEmpty	INSTANCE	= new ExecArgumentsEmpty();
	
	private static int						COUNT		= 0;
	
	static final void statusFill(final StatusInfo data) {
		data.put( "ARGS: 'Empty' count", Format.Compact.toDecimal( ExecArgumentsEmpty.COUNT ) );
	}
	
	/**
	 * special singleton case
	 * 
	 * @param callee
	 */
	private ExecArgumentsEmpty() {
		super( null );
		ExecArgumentsEmpty.COUNT++;
	}
	
	ExecArgumentsEmpty(final BaseFunction<?> callee) {
		super( callee );
		assert callee != null : "Use ExecArgumentsEmpty.INSTANCE";
		ExecArgumentsEmpty.COUNT++;
	}
	
	@Override
	public boolean baseContains(final BaseObject<?> value) {
		return false;
	}
	
	@Override
	public BaseArrayAdvanced<?, ?> baseDefaultSlice(final int start, final int end) {
		return new BaseNativeArray<Object>();
	}
	
	@Override
	public final BaseObject<?> baseGet(final int i, final BaseObject<?> defaultValue) {
		return defaultValue;
	}
	
	@Override
	public Iterator<String> baseGetOwnIterator() {
		return BaseObject.ITERATOR_EMPTY;
	}
	
	@Override
	public boolean baseHasOwnProperties() {
		return false;
	}
	
	@Override
	public Iterator<? extends BaseObject<?>> baseIterator() {
		return BaseArrayAdvanced.ITERATOR_EMPTY;
	}
	
	@Override
	public BasePrimitiveString baseToString() {
		return Base.forString( "[ARGUMENTS: arg1]" );
	}
	
	@Override
	public final ExecArguments<?> execDetachable() {
		return this;
	}
	
	@Override
	public final Object get(final int i) {
		return null;
	}
	
	@Override
	public boolean hasNamedArguments() {
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public final int length() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "[arguments Empty]";
	}
}
