package ru.myx.ae3.exec;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import ru.myx.ae3.base.Base;
import ru.myx.ae3.base.BaseAbstract;
import ru.myx.ae3.base.BaseArray;
import ru.myx.ae3.base.BaseArrayAdvanced;
import ru.myx.ae3.base.BaseArrayDynamic;
import ru.myx.ae3.base.BaseArrayWritable;
import ru.myx.ae3.base.BaseFunction;
import ru.myx.ae3.base.BaseIdentityObject;
import ru.myx.ae3.base.BaseObject;
import ru.myx.ae3.base.BasePrimitive;
import ru.myx.ae3.base.BasePrimitiveBoolean;
import ru.myx.ae3.base.BasePrimitiveNumber;
import ru.myx.ae3.base.BasePrimitiveString;
import ru.myx.ae3.base.BaseProperty;
import ru.myx.ae3.base.ToPrimitiveHint;
import ru.myx.ae3.reflect.Reflect;
import ru.myx.ae3.reflect.Reflected;
import ru.myx.ae3.report.Report;
import ru.myx.util.IteratorSingle;

/**
 * @author myx
 * 
 */
public abstract class ExecProcess extends BaseAbstract<ExecProcess> implements ExecScope, ExecArguments<ExecProcess>,
		BaseArrayAdvanced<ExecProcess, Object> {
	/**
	 * no Object.prototype properties here
	 */
	public static final BaseObject<?>		GLOBAL					= new BaseIdentityObject( null );
	
	static final ExecArguments<?>			INITIAL					= ExecArgumentsEmpty.INSTANCE;
	
	/**
	 * 
	 */
	public static final BasePrimitiveString	PROPERTY_BASE_ARGUMENTS	= Base.forString( "arguments" );
	
	/**
	 * 
	 */
	protected static final int				STACK_INITIAL			= 128;
	
	/**
	 * call arguments
	 */
	ExecArguments<?>						fldArguments;
	
	/**
	 * code part of executable
	 */
	Instruction[]							fldCode;
	
	/**
	 * 
	 */
	Object									fldDebug;
	
	private final ExecDirectNumber			fldNumber;
	
	private final ExecDirectInteger			fldInteger;
	
	private final ExecDirectString			fldString;
	
	/**
	 * current output target
	 */
	BaseFunction<?>							fldOutput;
	
	/**
	 * 
	 */
	BaseObject<?>[]							fldStack;
	
	/**
	 * Current process state, initial state is INACTIVE
	 */
	ExecProcessState						fldState;
	
	/**
	 * 
	 */
	final ExecProcess						parent;
	
	/**
	 * 
	 */
	public BaseObject<?>					r0R0;
	
	/**
	 * 
	 */
	public BaseObject<?>					r1R1;
	
	/**
	 * 
	 */
	public BaseObject<?>					r2R2;
	
	/**
	 * 
	 */
	public BaseObject<?>					r3R3;
	
	/**
	 * 
	 */
	public BaseObject<?>					r4RT;
	
	/**
	 * 
	 */
	public BaseObject<?>					r5GV;
	
	/**
	 * 
	 */
	public BaseObject<?>					r6FV;
	
	/**
	 * 
	 */
	BaseObject<?>							r7RR;
	
	/**
	 * 
	 */
	public int								r8IP					= 0;
	
	/**
	 * 
	 */
	public int								r9IL					= 0;
	
	/**
	 * 
	 */
	public int								rASP					= 0;
	
	/**
	 * 
	 */
	public int								rBSB					= 0;
	
	/**
	 * 
	 */
	public int								rCBT					= -1;
	
	/**
	 * 
	 */
	public int								rDCT					= -1;
	
	/**
	 * 
	 */
	public int								rEET					= -1;
	
	/**
	 * 
	 */
	public int								rFI0					= 0;
	
	ExecTracer								tracer;
	
	/**
	 * @param parent
	 * 
	 */
	protected ExecProcess(final ExecProcess parent) {
		this.parent = parent;
		this.fldStack = new BaseObject<?>[ExecProcess.STACK_INITIAL];
		this.fldState = ExecProcessState.INA;
		this.fldArguments = ExecProcess.INITIAL;
		this.fldNumber = new ExecDirectNumber();
		this.fldInteger = new ExecDirectInteger();
		this.fldString = new ExecDirectString();
	}
	
	/**
	 * for CALLO. Copy one from stack.
	 * 
	 * @param callee
	 * 
	 * @param constant
	 * @return arguments
	 */
	protected abstract ExecArguments<?> argumentConstant(final BaseFunction<?> callee, final BaseObject<?> constant);
	
	/**
	 * for CALLS. Copy some from stack.
	 * 
	 * @param callee
	 * 
	 * @param size
	 * @return arguments
	 */
	protected abstract ExecArguments<?> argumentsCopy(final BaseFunction<?> callee, final int size);
	
	/**
	 * for CALLM. Copy some from stack, use description.
	 * 
	 * @param callee
	 * 
	 * @param size
	 * @param desc
	 * @return arguments
	 */
	protected abstract ExecArguments<?> argumentsCopyMap(
			final BaseFunction<?> callee,
			final int size,
			final NamedArgumentsMapper desc);
	
	/**
	 * for CALLO and CALLS. Use (/Map) some on stack.
	 * 
	 * @param callee
	 * 
	 * @param size
	 * @return arguments
	 */
	protected abstract ExecArguments<?> argumentsCtx(final BaseFunction<?> callee, final int size);
	
	/**
	 * for CALLM. Use (/Map) some on stack, use description.
	 * 
	 * @param callee
	 * 
	 * @param size
	 * @param desc
	 * @return arguments
	 */
	protected abstract ExecArguments<?> argumentsCtx(
			final BaseFunction<?> callee,
			final int size,
			final NamedArgumentsMapper desc);
	
	/**
	 * for CALLS. Copy some from stack.
	 * 
	 * @param callee
	 * 
	 * @return arguments
	 */
	protected abstract ExecArguments<?> argumentsEmpty(final BaseFunction<?> callee);
	
	/**
	 * Not an array. But, function arguments for a call accessible via this
	 * method as an array.
	 */
	@Override
	public final BaseArray<?, ?> baseArray() {
		return this.fldArguments;
	}
	
	@Override
	public final BaseArrayAdvanced<? extends ExecProcess, ? extends Object> baseArrayAdvanced() {
		return this;
	}
	
	@Override
	public final BaseArrayDynamic<? extends ExecProcess, ? extends Object> baseArrayDynamic() {
		return null;
	}
	
	@Override
	public final BaseArrayWritable<? extends ExecProcess, ? extends Object> baseArrayWritable() {
		return null;
	}
	
	@Override
	public final BaseFunction<?> baseCall() {
		return this.r6FV.baseCall();
	}
	
	@Override
	public final String baseClass() {
		return "Process";
	}
	
	@Override
	public final BaseFunction<?> baseConstruct() {
		return this.r6FV.baseConstruct();
	}
	
	@Override
	public final boolean baseContains(final BaseObject<?> value) {
		return this.fldArguments.baseArrayAdvanced().baseContains( value );
	}
	
	@Override
	public final BaseArrayAdvanced<?, ?> baseDefaultSlice(final int start, final int end) {
		return this.fldArguments.baseArrayAdvanced().baseDefaultSlice( start, end );
	}
	
	@Override
	public final boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.r6FV.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.r6FV.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.r6FV.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.r6FV.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.r6FV.baseDefine( name, value, writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDelete(final String name) {
		return this.r6FV.baseDelete( name );
	}
	
	@Override
	public final BaseObject<?> baseGet(final int index, final BaseObject<?> defaultValue) {
		return this.fldArguments.baseGet( index, defaultValue );
	}
	
	@Override
	public final Iterator<String> baseGetOwnAllIterator() {
		final Iterator<String> locals = this.r6FV.baseGetOwnAllIterator();
		assert locals != null : "Use: BaseObject.ITERATOR_EMPTY";
		return this.fldArguments != null
				? Base.joinPropertyIterators( locals, new IteratorSingle<String>( "arguments" ) )
				: locals;
	}
	
	@Override
	public final Iterator<String> baseGetOwnIterator() {
		return this.r6FV.baseGetIterator();
	}
	
	@Override
	public final BaseProperty baseGetOwnProperty(final BasePrimitiveString name) {
		{
			/**
			 * not OWN
			 */
			for (BaseObject<?> object = this.r6FV;;) {
				final BaseProperty property = object.baseGetOwnProperty( name );
				if (property != null) {
					return property;
				}
				object = object.basePrototype();
				if (object == null) {
					break;
				}
			}
		}
		{
			/**
			 * fake
			 */
			if (ExecProcess.PROPERTY_BASE_ARGUMENTS == name) {
				return ExecProcessArgumentsProperty.INSTANCE;
			}
		}
		return null;
	}
	
	@Override
	public final BaseProperty baseGetOwnProperty(final String name) {
		{
			/**
			 * not OWN
			 */
			for (BaseObject<?> object = this.r6FV;;) {
				final BaseProperty property = object.baseGetOwnProperty( name );
				if (property != null) {
					return property;
				}
				object = object.basePrototype();
				if (object == null) {
					break;
				}
			}
		}
		{
			/**
			 * fake
			 */
			if (name.length() == 9 && "arguments".equals( name )) {
				return ExecProcessArgumentsProperty.INSTANCE;
			}
		}
		return null;
	}
	
	@Override
	public final boolean baseHasOwnProperties() {
		return this.r6FV.baseHasProperties();
	}
	
	@Override
	public final boolean baseIsExtensible() {
		return this.r6FV.baseIsExtensible();
	}
	
	@Override
	public final Iterator<? extends BaseObject<?>> baseIterator() {
		return this.fldArguments.baseArrayAdvanced().baseIterator();
	}
	
	@Override
	public final BaseObject<?> basePrototype() {
		return this.parent;
	}
	
	@Override
	public final BasePrimitiveBoolean baseToBoolean() {
		return this.r6FV.baseToBoolean();
	}
	
	@Override
	public final BasePrimitiveNumber baseToInt32() {
		return this.r6FV.baseToInt32();
	}
	
	@Override
	public final BasePrimitiveNumber baseToInteger() {
		return this.r6FV.baseToInteger();
	}
	
	@Override
	public final BasePrimitiveNumber baseToNumber() {
		return this.r6FV.baseToNumber();
	}
	
	@Override
	public final BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		return this.r6FV.baseToPrimitive( hint );
	}
	
	@Override
	public final BasePrimitiveString baseToString() {
		return this.r6FV.baseToString();
	}
	
	@Override
	public final ExecProcess baseValue() {
		return this;
	}
	
	@Override
	public final void contextCreateMutableBinding(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean dynamic) {
		this.r6FV.baseDefine( name, value, true, true, dynamic );
	}
	
	@Override
	public final void contextCreateMutableBinding(final String name, final BaseObject<?> value, final boolean dynamic) {
		this.r6FV.baseDefine( name, value, true, true, dynamic );
	}
	
	@Override
	public final boolean contextDeleteBinding(final String name) {
		BaseProperty property = null;
		BaseObject<?> source = this.r6FV;
		for (;;) {
			property = source.baseGetOwnProperty( name );
			if (property != null) {
				/**
				 * Property found, setting it's value
				 */
				return source.baseDelete( name );
			}
			/**
			 * not going upper than GV for DELETE
			 */
			if (source == this.r5GV) {
				/**
				 * kinda success
				 */
				return true;
			}
			assert source.basePrototype() != source : "prototype should not be equal to this instance, class="
					+ source.getClass().getName();
			source = source.basePrototype();
			/**
			 * Oops - not found
			 */
			if (source == null) {
				/**
				 * kinda success
				 */
				return true;
			}
		}
	}
	
	/**
	 * Setups new context with actual arguments being bind.
	 * 
	 * Should return ExecStateCode but do not fool yourself, there would be
	 * always null. It never fails, that's why actual return is void.
	 * 
	 * @param arguments
	 *            NULL value is special, it causes system to ignore arguments
	 *            totally regardless of actual arguments passed.
	 */
	public final void contextExecFARGS(final BasePrimitiveString[] arguments) {
		this.vmCreateScope();
		if (arguments == null) {
			return;
		}
		final ExecArguments<?> list = this.fldArguments;
		final int sizea = arguments.length;
		if (list == null) {
			for (int position = sizea - 1; position >= 0; position--) {
				this.contextCreateMutableBinding( arguments[position], //
						BaseObject.UNDEFINED,
						false );
			}
		} else {
			if (list.hasNamedArguments()) {
				for (int position = sizea - 1; position >= 0; position--) {
					final BasePrimitiveString key = arguments[position];
					this.contextCreateMutableBinding( key, //
							list.baseGet( key, BaseObject.UNDEFINED ),
							false );
				}
			} else {
				final int sizel = list.length();
				if (sizel <= sizea) {
					for (int position = sizel - 1; position >= 0; position--) {
						this.contextCreateMutableBinding( arguments[position], //
								list.baseGet( position, BaseObject.UNDEFINED ),
								false );
					}
					for (int position = sizea - 1; position >= sizel; position--) {
						this.contextCreateMutableBinding( arguments[position], //
								BaseObject.UNDEFINED,
								false );
					}
				} else {
					for (int position = sizea - 1; position >= 0; position--) {
						this.contextCreateMutableBinding( arguments[position], //
								list.baseGet( position, BaseObject.UNDEFINED ),
								false );
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * Should return ExecStateCode but do not fool yourself, there would be
	 * always null. It never fails, that's why actual return is void.
	 * 
	 * @param message
	 */
	public final void contextExecFDEBUG(final Object message) {
		assert this.tracer == null || this.tracer.traceDebug( message );
		this.fldDebug = message;
	}
	
	/**
	 * @return value of internal 'arguments' register
	 */
	public final ExecArguments<?> contextGetArguments() {
		return this.fldArguments;
	}
	
	@Override
	public final BaseObject<?> contextGetBindingValue(final BasePrimitiveString name, final boolean strict) {
		for (ExecProcess process = this;;) {
			BaseObject<?> source = process.r6FV;
			/**
			 * from r6FV to r5GV or BaseObject.PROTOTYPE when r6FV does not
			 * contain r5GV in its prototype chain.
			 */
			for (;;) {
				/**
				 * not going upper than every GV for GET, not including GV.
				 */
				if (source == process.r5GV) {
					break;
				}
				final BaseProperty property = source.baseGetOwnProperty( name );
				if (property != null) {
					/**
					 * Property found, setting it's value
					 */
					return property.propertyGet( source, name );
				}
				final BaseObject<?> prototype = source.basePrototype();
				assert prototype != source : "prototype should not be equal to this instance, class="
						+ source.getClass().getName();
				source = prototype;
				/**
				 * Not including BaseObject.PROTOTYPE
				 */
				if (source == BaseObject.PROTOTYPE) {
					break;
				}
				/**
				 * Oops - not found
				 */
				if (source == null) {
					break;
				}
			}
			/**
			 * fake 'arguments'
			 */
			{
				/**
				 * allows to access arguments of call here (dynamically)
				 */
				if (process == this && name == ExecProcess.PROPERTY_BASE_ARGUMENTS) {
					final ExecArguments<?> original = this.fldArguments;
					if (original != null) {
						final ExecArguments<?> arguments = original.execDetachable();
						if (arguments != original) {
							this.fldArguments = arguments;
						}
						return arguments;
					}
				}
			}
			/**
			 * from r5GV to BaseObject.PROTOTYPE;
			 */
			source = process.r5GV;
			for (;;) {
				final BaseProperty property = source.baseGetOwnProperty( name );
				if (property != null) {
					/**
					 * Property found, setting it's value
					 */
					return property.propertyGet( source, name );
				}
				final BaseObject<?> prototype = source.basePrototype();
				assert prototype != source : "prototype should not be equal to this instance, class="
						+ source.getClass().getName();
				source = prototype;
				/**
				 * Not including BaseObject.PROTOTYPE
				 */
				if (source == BaseObject.PROTOTYPE) {
					break;
				}
				/**
				 * Not including ExecProcess.GLOBAL
				 */
				if (source == ExecProcess.GLOBAL) {
					break;
				}
				/**
				 * Oops - not found
				 */
				if (source == null) {
					break;
				}
			}
			/**
			 * switch to parent process
			 */
			{
				assert process != process.parent : "prototype should not be equal to this instance, class="
						+ process.getClass().getName();
				process = process.parent;
				if (process == null) {
					break;
				}
			}
		}
		{
			/**
			 * kinda only the GLOBAL object left to check.
			 */
			final BaseObject<?> value = ExecProcess.GLOBAL.baseGet( name, null );
			if (value != null) {
				/**
				 * Property found, setting it's value
				 */
				return value;
			}
			if (strict) {
				throw new RuntimeException( "TypeError" );
			}
			return BaseObject.UNDEFINED;
		}
		
		/**
		 * OLD CODE:<code>
		final BaseProperty property = this.r6FV.baseGetProperty( name );
		if (property == null) {
			if (this.parent == null) {
				if (strict) {
					throw new RuntimeException( "TypeError" );
				}
				return BaseObject.UNDEFINED;
			}
			return this.parent.contextGetBindingValue( name, strict );
		}
		return property.propertyGet( this.r6FV, name );
		</code>
		 */
	}
	
	@Override
	public final BaseObject<?> contextGetBindingValue(final String name, final boolean strict) {
		for (ExecProcess process = this;;) {
			BaseObject<?> source = process.r6FV;
			/**
			 * from r6FV to r5GV or BaseObject.PROTOTYPE when r6FV does not
			 * contain r5GV in its prototype chain.
			 */
			for (;;) {
				/**
				 * not going upper than every GV for GET, not including GV.
				 */
				if (source == process.r5GV) {
					break;
				}
				final BaseProperty property = source.baseGetOwnProperty( name );
				if (property != null) {
					/**
					 * Property found, setting it's value
					 */
					return property.propertyGet( source, name );
				}
				final BaseObject<?> prototype = source.basePrototype();
				assert prototype != source : "prototype should not be equal to this instance, class="
						+ source.getClass().getName();
				source = prototype;
				/**
				 * Not including BaseObject.PROTOTYPE
				 */
				if (source == BaseObject.PROTOTYPE) {
					break;
				}
				/**
				 * Oops - not found
				 */
				if (source == null) {
					break;
				}
			}
			/**
			 * fake 'arguments'
			 */
			{
				/**
				 * allows to access arguments of call here (dynamically)
				 */
				if (process == this && name.length() == 9 && "arguments".equals( name )) {
					assert this.fldArguments != null : "NULL java object!";
					final ExecArguments<?> original = this.fldArguments;
					final ExecArguments<?> arguments = original.execDetachable();
					if (arguments != original) {
						this.fldArguments = arguments;
					}
					return arguments;
				}
			}
			/**
			 * from r5GV to BaseObject.PROTOTYPE;
			 */
			source = process.r5GV;
			for (;;) {
				final BaseProperty property = source.baseGetOwnProperty( name );
				if (property != null) {
					/**
					 * Property found, setting it's value
					 */
					return property.propertyGet( source, name );
				}
				final BaseObject<?> prototype = source.basePrototype();
				assert prototype != source : "prototype should not be equal to this instance, class="
						+ source.getClass().getName();
				source = prototype;
				/**
				 * Not including BaseObject.PROTOTYPE
				 */
				if (source == BaseObject.PROTOTYPE) {
					break;
				}
				/**
				 * Not including ExecProcess.GLOBAL
				 */
				if (source == ExecProcess.GLOBAL) {
					break;
				}
				/**
				 * Oops - not found
				 */
				if (source == null) {
					break;
				}
			}
			/**
			 * switch to parent process
			 */
			{
				assert process != process.parent : "prototype should not be equal to this instance, class="
						+ process.getClass().getName();
				process = process.parent;
				if (process == null) {
					break;
				}
			}
		}
		{
			/**
			 * kinda only the GLOBAL object left to check.
			 */
			final BaseObject<?> value = ExecProcess.GLOBAL.baseGet( name, null );
			if (value != null) {
				/**
				 * Property found, setting it's value
				 */
				return value;
			}
			if (strict) {
				throw new RuntimeException( "TypeError" );
			}
			return BaseObject.UNDEFINED;
		}
		
		/**
		 * OLD CODE:<code>
		final BaseProperty property = this.r6FV.baseGetProperty( name );
		if (property == null) {
			if (this.parent == null) {
				if (strict) {
					throw new RuntimeException( "TypeError" );
				}
				return BaseObject.UNDEFINED;
			}
			return this.parent.contextGetBindingValue( name, strict );
		}
		return property.propertyGet( this.r6FV, name );
		</code>
		 */
	}
	
	/**
	 * @return value of internal 'debug' register
	 */
	public final Object contextGetDebug() {
		return this.fldDebug;
	}
	
	@Override
	public final boolean contextHasBinding(final String name) {
		return Base.hasProperty( this.r6FV, name );
	}
	
	@Override
	public final BaseObject<?> contextImplicitThisValue() {
		return this.r5GV;
	}
	
	/**
	 * DIFFERS: strict = false would add new binding!
	 * 
	 * 
	 * 10.2.1.1.3 SetMutableBinding (N,V,S)
	 * 
	 * The concrete Environment Record method SetMutableBinding for declarative
	 * environment records attempts to change the bound value of the current
	 * binding of the identifier whose name is the value of the argument N to
	 * the value of argument V. A binding for N must already exist. If the
	 * binding is an immutable binding, a TypeError is always thrown. The S
	 * argument is ignored because strict mode does not change the meaning of
	 * setting bindings in declarative environment records.
	 * 
	 * 1. Let envRec be the declarative environment record for which the method
	 * was invoked.<br>
	 * 2. Assert: envRec must have a binding for N.<br>
	 * 3. If the binding for N in envRec is a mutable binding, change its bound
	 * value to V.<br>
	 * 4. Else this must be an attempt to change the value of an immutable
	 * binding so throw a TypeError exception.
	 */
	@Override
	public final boolean contextSetMutableBinding(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean strict) {
		BaseProperty property = null;
		BaseObject<?> source = this.r6FV;
		for (;;) {
			property = source.baseGetOwnProperty( name );
			if (property != null) {
				/**
				 * Property found, setting it's value
				 */
				return property.propertySet( source, name.toString(), value, true, true, true );
			}
			/**
			 * not going upper than GV for SET
			 */
			if (source == this.r5GV) {
				break;
			}
			assert source.basePrototype() != source : "prototype should not be equal to this instance, class="
					+ source.getClass().getName();
			source = source.basePrototype();
			/**
			 * Oops - not found
			 */
			if (source == null) {
				break;
			}
		}
		/**
		 * special CM condition %)
		 */
		if (value == BaseObject.UNDEFINED) {
			if (source != null) {
				/**
				 * Not own! Could have it somewhere upper, have to override
				 * then.
				 */
				property = source.baseGetOwnProperty( name );
				if (property != null) {
					return this.r6FV.baseDefine( name, value, true, true, true );
				}
			}
			return true;
		}
		if (strict) {
			throw new RuntimeException( "TypeError: no such binding" );
		}
		// System.err.println( ">>> >>> write to undeclared: name=" + name );
		/**
		 * source should be GV if GV is defined cause search goes till GV, it
		 * will not be GV if chain is broken, in this case we'll use FV
		 */
		return (source != this.r5GV || source == null
				/**
				 * Use frame itself
				 */
				? this.r6FV
				/**
				 * source should be GV and not NULL
				 */
				: source) //
				.baseDefine( name, value, true, true, true );
	}
	
	/**
	 * DIFFERS: strict = false would add new binding!
	 * 
	 * 
	 * 10.2.1.1.3 SetMutableBinding (N,V,S)
	 * 
	 * The concrete Environment Record method SetMutableBinding for declarative
	 * environment records attempts to change the bound value of the current
	 * binding of the identifier whose name is the value of the argument N to
	 * the value of argument V. A binding for N must already exist. If the
	 * binding is an immutable binding, a TypeError is always thrown. The S
	 * argument is ignored because strict mode does not change the meaning of
	 * setting bindings in declarative environment records.
	 * 
	 * 1. Let envRec be the declarative environment record for which the method
	 * was invoked.<br>
	 * 2. Assert: envRec must have a binding for N.<br>
	 * 3. If the binding for N in envRec is a mutable binding, change its bound
	 * value to V.<br>
	 * 4. Else this must be an attempt to change the value of an immutable
	 * binding so throw a TypeError exception.
	 */
	@Override
	public final boolean contextSetMutableBinding(final String name, final BaseObject<?> value, final boolean strict) {
		BaseProperty property = null;
		BaseObject<?> source = this.r6FV;
		for (;;) {
			property = source.baseGetOwnProperty( name );
			if (property != null) {
				/**
				 * Property found, setting it's value
				 */
				return property.propertySet( source, name, value, true, true, true );
			}
			/**
			 * not going upper than GV for SET
			 */
			if (source == this.r5GV) {
				break;
			}
			assert source.basePrototype() != source : "prototype should not be equal to this instance, class="
					+ source.getClass().getName();
			source = source.basePrototype();
			/**
			 * Oops - not found
			 */
			if (source == null) {
				break;
			}
		}
		/**
		 * special CM condition %)
		 */
		if (value == BaseObject.UNDEFINED) {
			if (source != null) {
				/**
				 * Not own! Could have it somewhere upper, have to override
				 * then.
				 */
				property = source.baseGetOwnProperty( name );
				if (property != null) {
					return this.r6FV.baseDefine( name, value, true, true, true );
				}
			}
			return true;
		}
		if (strict) {
			throw new RuntimeException( "TypeError: no such binding" );
		}
		// System.err.println( ">>> >>> write to undeclared: name=" + name );
		/**
		 * source should be GV if GV is defined cause search goes till GV, it
		 * will not be GV if chain is broken, in this case we'll use FV
		 */
		return (source != this.r5GV || source == null
				/**
				 * Use frame itself
				 */
				? this.r6FV
				/**
				 * source should be GV and not NULL
				 */
				: source) //
				.baseDefine( name, value, true, true, true );
	}
	
	/**
	 * @param name
	 * @param value
	 * @param strict
	 */
	public final void contextSetMutableBinding(final String name, final double value, final boolean strict) {
		this.contextSetMutableBinding( name, Base.forDouble( value ), strict );
	}
	
	/**
	 * @param name
	 * @param value
	 * @param strict
	 */
	public final void contextSetMutableBinding(final String name, final long value, final boolean strict) {
		this.contextSetMutableBinding( name, Base.forLong( value ), strict );
	}
	
	/**
	 * @param name
	 * @param value
	 * @param strict
	 */
	@Deprecated
	public final void contextSetMutableBinding(final String name, final Object value, final boolean strict) {
		this.contextSetMutableBinding( name, Base.forUnknown( value ), strict );
	}
	
	/**
	 * @param name
	 * @param value
	 * @param strict
	 */
	public final void contextSetMutableBinding(final String name, final String value, final boolean strict) {
		this.contextSetMutableBinding( name, Base.forString( value ), strict );
	}
	
	/**
	 * @param pw
	 */
	public final void dump(final PrintStream pw) {
		final StringBuilder sb = new StringBuilder( 1024 );
		this.dump( sb );
		pw.println( sb.toString() );
	}
	
	/**
	 * @param sb
	 */
	public abstract void dump(final StringBuilder sb);
	
	@Override
	public final BaseFunction<?> execCallee() {
		return this.fldArguments.execCallee();
	}
	
	@Override
	public final ExecArguments<?> execDetachable() {
		return this.fldArguments = this.fldArguments.execDetachable();
	}
	
	/**
	 * @param object
	 * @return
	 */
	public final ExecStateCode execOutput(final BaseObject<?> object) {
		if (object == null) {
			return null;
		}
		final BaseFunction<?> output = this.fldOutput;
		if (output == null) {
			return null;
		}
		final BaseObject<?> r7RR = this.r7RR;
		try {
			return this.vmCallO( output, BaseObject.UNDEFINED, object, false );
		} finally {
			this.r7RR = r7RR;
		}
	}
	
	/**
	 * null outputs are welcome. Check OutputBuilder class.
	 * 
	 * @param output
	 * @return previous output
	 */
	public final BaseFunction<?> execOutputReplace(final BaseFunction<?> output) {
		try {
			return this.fldOutput;
		} finally {
			this.fldOutput = output;
		}
	}
	
	/**
	 * @param instructions
	 * @param start
	 * @param stop
	 * @return
	 * @throws Exception
	 */
	public abstract BaseObject<?> execute(final Instruction[] instructions, final int start, final int stop)
			throws Exception;
	
	@Override
	public final Object get(final int i) {
		return this.fldArguments.get( i );
	}
	
	/**
	 * @return chain
	 */
	public final ExecProcess getParentProcess() {
		return this.parent;
	}
	
	/**
	 * @return current process state
	 */
	public final ExecProcessState getProcessState() {
		return this.fldState;
	}
	
	@Override
	public boolean hasNamedArguments() {
		return this.fldArguments.hasNamedArguments();
	}
	
	@Override
	public final boolean isEmpty() {
		return this.fldArguments.isEmpty();
	}
	
	/**
	 * @param <T>
	 * @param instance
	 * @param name
	 * @param arguments
	 * @param soft
	 *            will return value if not a function, call otherwise
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public final <T> T javaCall(
			final BaseObject<?> instance,
			final String name,
			final ExecArguments<?> arguments,
			final boolean soft) throws Exception {
		assert instance != null : "NULL java value";
		final BaseObject<?> property = instance.baseGet( name );
		assert property != null : "NULL java value";
		final BaseFunction<?> candidate = property.baseCall();
		if (candidate == null) {
			if (soft) {
				return (T) property.baseValue();
			}
			throw new IllegalArgumentException( "No "
					+ name
					+ " method detected, candidateClass="
					+ property.getClass().getName() );
		}
		final ExecFunction function = candidate.functionItself();
		if (function == null) {
			if (soft) {
				return (T) property.baseValue();
			}
			throw new IllegalArgumentException( "Not a function: key="
					+ name
					+ ", class="
					+ instance.getClass().getName()
					+ ", candidateClass="
					+ candidate.getClass().getName() );
		}
		this.vmFrameEntry( -1 );
		final int rBSB = this.rBSB;
		this.r4RT = instance;
		this.fldArguments = arguments;
		{
			final BaseObject<?> scope = function.execScope();
			if (scope != null) {
				this.r6FV = this.r5GV = scope;
			}
		}
		try {
			// runImpl(fldCode);
			final ExecStateCode code = function.execCall( this );
			if (code == null) {
				assert this.rBSB == rBSB : "stack base broken in javaCall: SB(1)=" + this.rBSB + ", SB(2)=" + rBSB;
				assert this.r7RR != null : "NULL java value, functionClass=" + function.getClass().getName();
				return (T) this.r7RR.baseValue();
			}
			if (code == ExecStateCode.ERROR) {
				if (this.r7RR instanceof Exception) {
					throw (Exception) this.r7RR;
				}
				if (this.r7RR instanceof Error) {
					throw (Error) this.r7RR;
				}
				if (this.r7RR instanceof Throwable) {
					throw new RuntimeException( "While performing call: instance="
							+ instance
							+ ", name="
							+ name
							+ ", function="
							+ function
							+ ", error="
							+ this.r7RR, (Throwable) this.r7RR );
				}
				throw new RuntimeException( "While performing call: instance="
						+ instance
						+ ", name="
						+ name
						+ ", function="
						+ function
						+ ", error="
						+ this.r7RR );
			}
			throw new IllegalStateException( "Unexpected state, While performing call: instance="
					+ instance
					+ ", name="
					+ name
					+ ", function="
					+ function
					+ ", error="
					+ this.r7RR
					+ ", state="
					+ code
					+ "!" );
		} finally {
			this.rBSB = rBSB;
			this.vmFrameLeave();
		}
	}
	
	/**
	 * @param <T>
	 * @param instance
	 * @param name
	 * @param function
	 * @param arguments
	 * @param soft
	 *            will return value if not a function, call otherwise
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public final <T> T javaCall(
			final BaseObject<?> instance,
			final String name,
			final ExecFunction function,
			final ExecArguments<?> arguments,
			final boolean soft) throws Exception {
		assert instance != null : "NULL java value";
		if (function == null) {
			if (soft) {
				return null;
			}
			throw new IllegalArgumentException( "Not a function: key="
					+ name
					+ ", class="
					+ instance.getClass().getName() );
		}
		this.vmFrameEntry( -1 );
		final int rBSB = this.rBSB;
		this.r4RT = instance;
		this.fldArguments = arguments;
		{
			final BaseObject<?> scope = function.execScope();
			if (scope != null) {
				this.r6FV = this.r5GV = scope;
			}
		}
		try {
			// runImpl(fldCode);
			final ExecStateCode code = function.execCall( this );
			if (code == null) {
				assert this.rBSB == rBSB : "stack base broken in javaCall: SB(1)=" + this.rBSB + ", SB(2)=" + rBSB;
				return (T) this.r7RR;
			}
			if (code == ExecStateCode.ERROR) {
				if (this.r7RR instanceof Exception) {
					throw (Exception) this.r7RR;
				}
				if (this.r7RR instanceof Error) {
					throw (Error) this.r7RR;
				}
				if (this.r7RR instanceof Throwable) {
					throw new RuntimeException( "While performing call: instance="
							+ instance
							+ ", name="
							+ name
							+ ", function="
							+ function
							+ ", error="
							+ this.r7RR, (Throwable) this.r7RR );
				}
				throw new RuntimeException( "While performing call: instance="
						+ instance
						+ ", name="
						+ name
						+ ", function="
						+ function
						+ ", error="
						+ this.r7RR );
			}
			throw new IllegalStateException( "Unexpected state, While performing call: instance="
					+ instance
					+ ", name="
					+ name
					+ ", function="
					+ function
					+ ", error="
					+ this.r7RR
					+ ", state="
					+ code
					+ "!" );
		} finally {
			this.rBSB = rBSB;
			this.vmFrameLeave();
		}
	}
	
	/**
	 * @param <T>
	 * @param instance
	 * @param name
	 * @param arguments
	 * @param soft
	 *            will return value if not a function, call otherwise
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public final <T> T javaCall(
			final Object instance,
			final String name,
			final ExecArguments<?> arguments,
			final boolean soft) throws Exception {
		assert instance != null : "NULL java value";
		final Object property = (instance instanceof Reflected
				? (Reflected) instance
				: Reflect.classToReflected( instance.getClass() )).reflectReadJava( this, instance, name );
		if (!(property instanceof ExecFunction)) {
			throw new IllegalArgumentException( "No " + name + " method detected!" );
		}
		final ExecFunction function = (ExecFunction) property;
		this.vmFrameEntry( -1 );
		final int rBSB = this.rBSB;
		/**
		 * sorry %)
		 */
		this.r4RT = Base.forUnknown( instance );
		this.fldArguments = arguments;
		{
			final BaseObject<?> scope = function.execScope();
			if (scope != null) {
				this.r6FV = this.r5GV = scope;
			}
		}
		try {
			// runImpl(fldCode);
			final ExecStateCode code = function.execCall( this );
			if (code == null) {
				assert this.rBSB == rBSB : "stack base broken in javaCall: SB(1)=" + this.rBSB + ", SB(2)=" + rBSB;
				return (T) this.r7RR;
			}
			if (code == ExecStateCode.ERROR) {
				if (this.r7RR instanceof Exception) {
					throw (Exception) this.r7RR;
				}
				if (this.r7RR instanceof Error) {
					throw (Error) this.r7RR;
				}
				if (this.r7RR instanceof Throwable) {
					throw new RuntimeException( "While performing call: instance="
							+ instance
							+ ", name="
							+ name
							+ ", function="
							+ function
							+ ", error="
							+ this.r7RR, (Throwable) this.r7RR );
				}
				throw new RuntimeException( "While performing call: instance="
						+ instance
						+ ", name="
						+ name
						+ ", function="
						+ function
						+ ", error="
						+ this.r7RR );
			}
			throw new IllegalStateException( "Unexpected state, While performing call: instance="
					+ instance
					+ ", name="
					+ name
					+ ", function="
					+ function
					+ ", error="
					+ this.r7RR
					+ ", state="
					+ code
					+ "!" );
		} finally {
			this.rBSB = rBSB;
			this.vmFrameLeave();
		}
	}
	
	/**
	 * @param <T>
	 * @param instance
	 * @param name
	 * @param soft
	 *            will return value if not a function, call otherwise
	 * @return
	 * @throws Exception
	 */
	public final <T> T javaCallV(final BaseObject<?> instance, final String name, final boolean soft) throws Exception {
		return this.javaCall( instance, name, this.argumentsEmpty( null ), soft );
	}
	
	/**
	 * @param <T>
	 * @param instance
	 * @param name
	 * @param function
	 * @param soft
	 *            will return value if not a function, call otherwise
	 * @return
	 * @throws Exception
	 */
	public final <T> T javaCallV(
			final BaseObject<?> instance,
			final String name,
			final ExecFunction function,
			final boolean soft) throws Exception {
		return this.javaCall( instance, name, function, this.argumentsEmpty( null ), soft );
	}
	
	/**
	 * @param <T>
	 * @param instance
	 * @param name
	 * @param soft
	 *            will return value if not a function, call otherwise
	 * @return
	 * @throws Exception
	 */
	public final <T> T javaCallV(final Object instance, final String name, final boolean soft) throws Exception {
		return this.javaCall( instance, name, this.argumentsEmpty( null ), soft );
	}
	
	@Override
	public final int length() {
		return this.fldArguments.length();
	}
	
	/**
	 * @param o
	 * @return
	 */
	public abstract ExecStateCode raise(final String o);
	
	/**
	 * @param o
	 * @return
	 */
	public abstract ExecStateCode raise(final Throwable o);
	
	/**
	 * @param title
	 * @throws IllegalArgumentException
	 */
	public abstract void replaceInfo(final String title) throws IllegalArgumentException;
	
	/**
	 * @param tracer
	 * @return
	 */
	public final ExecTracer setTracer(final ExecTracer tracer) {
		try {
			return this.tracer;
		} finally {
			this.tracer = tracer;
		}
		
	}
	
	/**
	 * @param function
	 * @param thisValue
	 * @param arguments
	 * @param detachable
	 * @return
	 */
	public final ExecStateCode vmCall(
			final ExecFunction function,
			final BaseObject<?> thisValue,
			final ExecArguments<?> arguments,
			final boolean detachable) {
		assert arguments != null : "NULL arguments, use ExecArguments.EMPTY instead";
		assert this.tracer == null || this.tracer.traceCall( function, thisValue, arguments );
		if (arguments.length() < function.execMinimalArgumentCount()) {
			return this.raise( "Function requires minimum of "
					+ function.execMinimalArgumentCount()
					+ " arguments, got "
					+ arguments.length()
					+ " in this call!" );
		}
		return this.vmCallImpl( function, thisValue, arguments, detachable );
	}
	
	/**
	 * @param function
	 * @param thisValue
	 * @param arguments
	 * @param detachable
	 * @return
	 */
	private final ExecStateCode vmCallImpl(
			final ExecFunction function,
			final BaseObject<?> thisValue,
			final ExecArguments<?> arguments,
			final boolean detachable) {
		this.vmFrameEntry( -1 );
		/**
		 * for assertions only
		 */
		final int rBSB = this.rBSB;
		final ExecProcessState state = this.fldState;
		this.r4RT = thisValue;
		// new Error( ">>> >> vmCall arguments=" + arguments
		// ).printStackTrace();
		this.fldArguments = arguments;
		{
			final BaseObject<?> scope = function.execScope();
			if (scope != null) {
				// System.out.println( ">>> >> vmCall scope=" + scope );
				this.r6FV = this.r5GV = scope;
			}
		}
		this.fldState = ExecProcessState.CLL;
		try {
			final ExecStateCode code = function.execCall( this );
			assert code == null || code.isValidForCall() || function instanceof ExecFunctionUnsafe : "Illegal state code for function: "
					+ code
					+ ", function class: "
					+ function.getClass()
					+ " use ExecFunctionUnsafe interface to allow this functionality";
			assert this.rBSB == rBSB : "stack base broken in vmCall: SB(1)="
					+ this.rBSB
					+ ", SB(2)="
					+ rBSB
					+ ", fnClass="
					+ function.getClass().getName();
			if (detachable && this.r7RR instanceof ExecDirect<?>) {
				this.r7RR = ((ExecDirect<?>) this.r7RR).toDetachable();
			}
			return code;
		} catch (final ExecNonMaskedException e) {
			throw e;
		} catch (final Error e) {
			Report.exception( "EXEC-PROCESS", "Error in vmCall", e );
			throw e;
		} catch (final InvocationTargetException e) {
			final Throwable t = e.getCause();
			Report.exception( "EXEC-PROCESS", "InvocationTargetException in vmCall", t );
			if (t instanceof ExecNonMaskedException) {
				throw (ExecNonMaskedException) t;
			}
			if (t instanceof Error) {
				throw (Error) t;
			}
			this.r7RR = Base.forThrowable( t );
			return ExecStateCode.ERROR;
		} catch (final Throwable t) {
			Report.exception( "EXEC-PROCESS", "Throwable in vmCall", t );
			this.r7RR = Base.forThrowable( t );
			return ExecStateCode.ERROR;
		} finally {
			this.fldState = state;
			/**
			 * not really needed
			 */
			// this.rBSB = rBSB;
			this.vmFrameLeave();
		}
	}
	
	/**
	 * @param call
	 * @param thisValue
	 * @param argument
	 * @param detachable
	 * @return
	 */
	public final ExecStateCode vmCallO(
			final BaseFunction<?> call,
			final BaseObject<?> thisValue,
			final BaseObject<?> argument,
			final boolean detachable) {
		if (call == null) {
			return this.raise( "Not a function!" );
		}
		final ExecFunction function = call.functionItself();
		if (function == null) {
			return this.raise( "NULL function: class=" + call.getClass() );
		}
		if (1 < function.execMinimalArgumentCount()) {
			return this.raise( "Function requires minimum of "
					+ function.execMinimalArgumentCount()
					+ " arguments, got "
					+ 1
					+ " in this call!" );
		}
		return this.vmCallImpl( function, thisValue, this.argumentConstant( call, argument ), detachable );
	}
	
	/**
	 * 
	 * ARGUMENTS ON STACK
	 * 
	 * @param call
	 * @param thisValue
	 * @param count
	 *            number of arguments on stack
	 * @param detachable
	 *            TODO
	 * @return
	 */
	public final ExecStateCode vmCallS(
			final BaseFunction<?> call,
			final BaseObject<?> thisValue,
			final int count,
			final boolean detachable) {
		if (call == null) {
			return this.raise( "Not a function!" );
		}
		final ExecFunction function = call.functionItself();
		if (function == null) {
			return this.raise( "NULL function: class=" + call.getClass() );
		}
		if (count < function.execMinimalArgumentCount()) {
			return this.raise( "Function requires minimum of "
					+ function.execMinimalArgumentCount()
					+ " arguments, got "
					+ count
					+ " in this call!" );
		}
		final ExecArguments<?> arguments;
		if (count == 0) {
			arguments = this.argumentsEmpty( call );
		} else {
			arguments = this.argumentsCopy( call, count );
			/**
			 * have to clean up
			 */
			final BaseObject<?>[] stack = this.fldStack;
			final int rASP = this.rASP;
			for (int i = count; i > 0; i--) {
				stack[rASP - i] = null;
			}
			this.rASP -= count;
		}
		return this.vmCallImpl( function, thisValue, arguments, detachable );
	}
	
	/**
	 * @param call
	 * @param thisValue
	 * @param detachable
	 *            TODO
	 * @return
	 */
	public final ExecStateCode vmCallV(
			final BaseFunction<?> call,
			final BaseObject<?> thisValue,
			final boolean detachable) {
		if (call == null) {
			return this.raise( "Not a function!" );
		}
		final ExecFunction function = call.functionItself();
		if (function == null) {
			return this.raise( "NULL function: class=" + call.getClass() );
		}
		if (0 < function.execMinimalArgumentCount()) {
			return this.raise( "Function requires minimum of "
					+ function.execMinimalArgumentCount()
					+ " arguments, got "
					+ 0
					+ " in this call!" );
		}
		return this.vmCallImpl( function, thisValue, this.argumentsEmpty( call ), detachable );
	}
	
	/**
	 * @return
	 */
	public final BaseObject<?> vmCreateScope() {
		return this.r6FV = new BaseIdentityObject( this.r6FV );
	}
	
	/**
	 * @param process
	 * @return
	 */
	public final BaseObject<?> vmCreateScope(final ExecProcess process) {
		return this.r6FV = this.r5GV = new BaseIdentityObject( process.r6FV );
	}
	
	/**
	 * @param suspected
	 * @return
	 */
	public final BaseObject<?> vmEnsureDetached(final BaseObject<?> suspected) {
		return suspected instanceof ExecDirect
				? ((ExecDirect<?>) suspected).toDetachable()
				: suspected;
	}
	
	/**
	 * @param leaveAddress
	 *            frame leave address or -1
	 * @return
	 */
	public abstract ExecStateCode vmFrameEntry(final int leaveAddress);
	
	/**
	 * 
	 * @return int
	 */
	public abstract ExecStateCode vmFrameLeave();
	
	/**
	 * Detachable RR
	 * 
	 * Maybe: detach(BaseObject<?>)?
	 * 
	 * @return
	 */
	public final BaseObject<?> vmGetResultDetachable() {
		return this.r7RR instanceof ExecDirect<?>
				? this.r7RR = ((ExecDirect<?>) this.r7RR).toDetachable()
				: this.r7RR;
	}
	
	/**
	 * RR as is
	 * 
	 * @return
	 */
	public final BaseObject<?> vmGetResultImmediate() {
		return this.r7RR;
	}
	
	/**
	 * <code>this.fldStack[pointer - 1]</code>
	 * 
	 * @return topmost stack object
	 */
	public abstract BaseObject<?> vmPeek();
	
	/**
	 * <code>this.fldStack[pointer - 1 - more]</code>
	 * 
	 * @param more
	 * @return stack object
	 */
	public abstract BaseObject<?> vmPeek(int more);
	
	/**
	 * @return topmost stack object
	 */
	public abstract BaseObject<?> vmPop();
	
	/**
	 * @param value
	 */
	public abstract void vmPush(BaseObject<?> value);
	
	/**
	 * @param value
	 */
	public final void vmPush(final double value) {
		this.vmPush( Base.forDouble( value ) );
	}
	
	/**
	 * @param value
	 */
	public final void vmPush(final int value) {
		this.vmPush( Base.forInteger( value ) );
	}
	
	/**
	 * @param value
	 */
	public final void vmPush(final long value) {
		this.vmPush( Base.forLong( value ) );
	}
	
	/**
	 * @param value
	 */
	@Deprecated
	public final void vmPush(final Object value) {
		this.vmPush( Base.forUnknown( value ) );
	}
	
	/**
	 * @param value
	 */
	public final void vmPush(final String value) {
		this.vmPush( Base.forString( value ) );
	}
	
	/**
	 * Just run a set of instructions. No new frame, no stack constraints but:<br>
	 * 1) cannot go above initial stack base.<br>
	 * 2) upon return should have initial stack base position.<br>
	 * 
	 * @param instructions
	 * @param start
	 * @param stop
	 * @return
	 * @throws Exception
	 */
	public abstract ExecStateCode vmRun(final Instruction[] instructions, final int start, final int stop)
			throws Exception;
	
	/**
	 * set topmost stack object
	 * 
	 * @param o
	 */
	public abstract void vmSet(final BaseObject<?> o);
	
	/**
	 * set topmost stack object
	 * 
	 * @param o
	 */
	public final void vmSet(final double o) {
		this.vmSet( Base.forDouble( o ) );
	}
	
	/**
	 * set topmost stack object
	 * 
	 * @param o
	 */
	public final void vmSet(final int o) {
		this.vmSet( Base.forInteger( o ) );
	}
	
	/**
	 * set topmost stack object
	 * 
	 * @param o
	 */
	public final void vmSet(final long o) {
		this.vmSet( Base.forLong( o ) );
	}
	
	/**
	 * @param o
	 */
	@Deprecated
	public final void vmSet(final Object o) {
		this.vmSet( Base.forUnknown( o ) );
	}
	
	/**
	 * set topmost stack object
	 * 
	 * @param o
	 */
	public final void vmSet(final String o) {
		this.vmSet( Base.forString( o ) );
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final BaseObject<?> r7RR) {
		this.r7RR = r7RR;
		return null;
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final BasePrimitiveBoolean r7RR) {
		this.r7RR = r7RR;
		return null;
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final BasePrimitiveNumber r7RR) {
		this.r7RR = r7RR;
		return null;
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final BasePrimitiveString r7RR) {
		this.r7RR = r7RR;
		return null;
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final boolean r7RR) {
		this.r7RR = r7RR
				? BaseObject.TRUE
				: BaseObject.FALSE;
		return null;
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final double r7RR) {
		this.r7RR = this.fldNumber.setValue( r7RR );
		return null;
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final int r7RR) {
		this.r7RR = this.fldInteger.setValue( r7RR );
		return null;
	}
	
	/**
	 * @param r7RR
	 * @return
	 */
	public final ExecStateCode vmSetResult(final String r7RR) {
		this.r7RR = this.fldString.setValue( r7RR );
		return null;
	}
}
