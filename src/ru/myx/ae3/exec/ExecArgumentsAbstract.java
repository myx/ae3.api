package ru.myx.ae3.exec;

import ru.myx.ae3.base.BaseArray;
import ru.myx.ae3.base.BaseArrayAdvanced;
import ru.myx.ae3.base.BaseArrayDynamic;
import ru.myx.ae3.base.BaseArrayWritable;
import ru.myx.ae3.base.BaseFunction;
import ru.myx.ae3.base.BaseHostAbstract;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.base.BaseProperty;

/**
 * @author myx
 * 
 * @param <T>
 */
public abstract class ExecArgumentsAbstract<T extends ExecArgumentsAbstract<?>> extends BaseHostAbstract<T> implements
		ExecArguments<T>, BaseArrayAdvanced<T, Object> {
	/**
	 * arguments.callee (for ExecArguments<?> instances)
	 */
	public static final BaseProperty	PROPERTY_CALLEE	= ExecArgumentsCalleeProperty.INSTANCE;
	
	/**
	 * arguments.length (for ExecArguments<?> instances)
	 */
	public static final BaseProperty	PROPERTY_LENGTH	= ExecArgumentsLengthProperty.INSTANCE;
	
	/**
	 * 
	 */
	protected BaseFunction<?>			callee;
	
	/**
	 * @param callee
	 */
	protected ExecArgumentsAbstract(final BaseFunction<?> callee) {
		this.callee = callee;
	}
	
	@Override
	public final BaseArray<T, Object> baseArray() {
		return this;
	}
	
	@Override
	public final BaseArrayAdvanced<T, Object> baseArrayAdvanced() {
		return this;
	}
	
	@Override
	public final BaseArrayDynamic<T, Object> baseArrayDynamic() {
		return null;
	}
	
	@Override
	public final BaseArrayWritable<T, Object> baseArrayWritable() {
		return null;
	}
	
	@Override
	public final String baseClass() {
		return "Arguments";
	}
	
	@Override
	public final boolean baseDelete(final String key) {
		return false;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		if (name == BasePrimitiveString.PROPERTY_BASE_LENGTH) {
			return ExecArgumentsLengthProperty.INSTANCE;
		}
		if (name == BasePrimitiveString.PROPERTY_BASE_CALLEE) {
			return ExecArgumentsCalleeProperty.INSTANCE;
		}
		return null;
	}
	
	@Override
	public BaseProperty baseGetOwnProperty(final String name) {
		if (name.length() == 6) {
			if ("length".equals( name )) {
				return ExecArgumentsLengthProperty.INSTANCE;
			}
			if ("callee".equals( name )) {
				return ExecArgumentsCalleeProperty.INSTANCE;
			}
		}
		return null;
	}
	
	@Override
	public final BaseFunction<?> execCallee() {
		return this.callee;
	}
	
}
