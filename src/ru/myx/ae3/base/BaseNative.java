package ru.myx.ae3.base;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import ru.myx.ae3.exec.Exec;
import ru.myx.ae3.exec.ExecProcess;
import ru.myx.ae3.exec.ExecStateCode;
import ru.myx.ae3.reflect.ReflectionDisable;

/**
 * 4.3.6 Native Object
 * <p>
 * A native object is any object supplied by an ECMAScript implementation
 * independent of the host environment. Standard native objects are defined in
 * this specification. Some native objects are built-in; others may be
 * constructed during the course of execution of an ECMAScript program.
 * <p>
 * 
 * @author myx
 * @param <T>
 */
@ReflectionDisable
public abstract class BaseNative<T> extends BaseAbstract<T> {
	
	/**
	 * @param target
	 * @param name
	 * @param property
	 * @param writable
	 * @param enumerable
	 * @param dynamic
	 */
	public static void setOwnProperty(
			final BaseNative<?> target,
			final String name,
			final BaseProperty property,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		assert property != null : "NULL property";
		if (property instanceof BasePropertyHolderString) {
			target.baseDefine( name, ((BasePropertyHolderString) property).value, writable, enumerable, dynamic );
			return;
		}
		if (target.properties != null) {
			final BaseProperty existing = target.properties.find( name );
			if (existing != null) {
				if (!existing.isWritable( name ) && !existing.isDynamic( name )) {
					// ignore
					return;
				}
				target.properties = target.properties.delete( name );
			}
		}
		/**
		 * including BasePropertyFilter
		 */
		target.properties = target.properties == null
				? new BasePropertyFilterString( name, property, name, writable, enumerable, dynamic )
				: target.properties.add( name, new BasePropertyFilterString( property,
						name,
						writable,
						enumerable,
						dynamic ) );
	}
	
	BasePropertiesString	properties	= null;
	
	BaseObject<?>			prototype;
	
	/**
	 * 
	 * UNDEFINED replaced with BaseObject.PROTOTYPE Other primitive types are
	 * not accepted
	 * 
	 * @param prototype
	 *            null to specify no prototype or prototype otherwise
	 */
	BaseNative(final BaseObject<?> prototype) {
		assert prototype == null || !prototype.baseIsPrimitive() : "Primitive prototype: prototype=" + prototype;
		this.prototype = prototype == BaseObject.UNDEFINED
				? BaseObject.PROTOTYPE
				: prototype;
		assert this.checkPrototypeChainDuplicates() : "Duplicates in prototype chain!";
	}
	
	/**
	 * Returns a function associated with this object. Executes code associated
	 * with the object. Invoked via a function call expression. Objects that
	 * implement this internal method are called functions.
	 * 
	 * null result means that this object is not a function.
	 * 
	 * 13.2.1 [[Call]]
	 * <p>
	 * When the [[Call]] property for a Function object F is called, the
	 * following steps are taken:<br>
	 * 1. Establish a new execution context using F's FormalParameterList, the
	 * passed arguments list, and the this value as described in Section 10.2.3.
	 * <br>
	 * 2. Evaluate F's FunctionBody. <br>
	 * 3. Exit the execution context established in step 1, restoring the
	 * previous execution context.<br>
	 * 4. If Result(2).type is throw then throw Result(2).value. <br>
	 * 5. If Result(2).type is return then return Result(2).value. <br>
	 * 6. (Result(2).type must be normal.) Return undefined.
	 * <p>
	 * 
	 * 
	 * @return result
	 */
	@Override
	public BaseFunction<?> baseCall() {
		return null;
	}
	
	/**
	 * The value of the [[Class]] property is defined by this specification for
	 * every kind of built-in object. The value of the [[Class]] property of a
	 * host object may be any value, even a value used by a built-in object for
	 * its [[Class]] property. The value of a [[Class]] property is used
	 * internally to distinguish different kinds of built-in objects. Note that
	 * this specification does not provide any means for a program to access
	 * that value except through Object.prototype.toString (see 15.2.4.2).
	 * 
	 * @return class
	 */
	@Override
	public String baseClass() {
		return "Object";
	}
	
	@Override
	public BaseFunction<?> baseConstruct() {
		return null;
	}
	
	@Override
	public abstract boolean baseDefine(
			final BasePrimitiveString name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic);
	
	@Override
	public abstract boolean baseDefine(
			final String name,
			final BaseObject<?> value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic);
	
	@Override
	public final boolean baseDefine(
			final String name,
			final double value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.baseDefine( name, Base.forDouble( value ), writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final long value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.baseDefine( name, Base.forLong( value ), writable, enumerable, dynamic );
	}
	
	@Override
	public final boolean baseDefine(
			final String name,
			final String value,
			final boolean writable,
			final boolean enumerable,
			final boolean dynamic) {
		return this.baseDefine( name, Base.forString( value ), writable, enumerable, dynamic );
	}
	
	/**
	 * [[Delete]] (P) When the [[Delete]] method of O is called with property
	 * name P, the following steps are taken:<br>
	 * 1. If O doesn’t have a property with name P, return true.<br>
	 * 2. If the property has the DontDelete attribute, return false.<br>
	 * 3. Remove the property with name P from O.<br>
	 * 4. Return true.
	 * 
	 * @param name
	 * @return boolean
	 */
	@Override
	public abstract boolean baseDelete(final String name);
	
	/**
	 * Never returns NULL. Should return BaseObject.ITERATOR_EMPTY at least.
	 * 
	 * @return
	 */
	@Override
	public abstract Iterator<String> baseGetOwnIterator();
	
	@Override
	public abstract BaseProperty baseGetOwnProperty(final BasePrimitiveString name);
	
	@Override
	public abstract BaseProperty baseGetOwnProperty(final String name);
	
	/**
	 * The value of the [[Prototype]] property must be either an object or null,
	 * and every [[Prototype]] chain must have finite length (that is, starting
	 * from any object, recursively accessing the [[Prototype]] property must
	 * eventually lead to a null value). Whether or not a native object can have
	 * a host object as its [[Prototype]] depends on the implementation.
	 * 
	 * @return prototype object or null
	 */
	@Override
	public final BaseObject<?> basePrototype() {
		return this.prototype;
	}
	
	/**
	 * A scope chain that defines the environment in which a Function object is
	 * executed.
	 * 
	 * @return scope
	 */
	public BaseObject<?> baseScope() {
		return null;
	}
	
	/**
	 * Always TRUE
	 */
	@Override
	public BasePrimitiveBoolean baseToBoolean() {
		return BaseObject.TRUE;
	}
	
	/**
	 * 9.5 ToInt32: (Signed 32 Bit Integer)
	 * <p>
	 * The operator ToInt32 converts its argument to one of 232 integer values
	 * in the range −231 through 231−1, inclusive.
	 * <p>
	 * This operator functions as follows:<br>
	 * 1. Call ToNumber on the input argument.<br>
	 * 2. If Result(1) is NaN, +0, −0, +∞, or −∞, return +0.<br>
	 * 3. Compute sign(Result(1)) * floor(abs(Result(1))).<br>
	 * 4. Compute Result(3) modulo 232; that is, a finite integer value k of
	 * Number type with positive sign and less than 232 in magnitude such the
	 * mathematical difference of Result(3) and k is mathematically an integer
	 * multiple of 232. <br>
	 * 5. If Result(4) is greater than or equal to 231, return Result(4)− 232,
	 * otherwise return Result(4).
	 * <p>
	 * NOTE Given the above definition of ToInt32: <br>
	 * The ToInt32 operation is idempotent: if applied to a result that it
	 * produced, the second application leaves that value unchanged. <br>
	 * ToInt32(ToUint32(x)) is equal to ToInt32(x) for all values of x. (It is
	 * to preserve this latter property that +∞ and −∞ are mapped to +0.)
	 * <p>
	 * ToInt32 maps −0 to +0.
	 * <p>
	 * 
	 * @return number
	 */
	@Override
	public BasePrimitiveNumber baseToInt32() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToInt32();
	}
	
	/**
	 * 9.4 ToInteger
	 * <p>
	 * The operator ToInteger converts its argument to an integral numeric
	 * value. This operator functions as follows:<br>
	 * 1. Call ToNumber on the input argument. <br>
	 * 2. If Result(1) is NaN, return +0. <br>
	 * 3. If Result(1) is +0, −0, +∞, or −∞, return Result(1).<br>
	 * 4. Compute sign(Result(1)) * floor(abs(Result(1))).<br>
	 * 5. Return Result(4).
	 * <p>
	 * 
	 * @return number
	 */
	@Override
	public BasePrimitiveNumber baseToInteger() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToInteger();
	}
	
	/**
	 * For Object
	 * <p>
	 * Apply the following steps:<br>
	 * 1. Call ToPrimitive(input argument, hint Number).<br>
	 * 2. Call ToNumber(Result(1)). <br>
	 * 3. Return Result(2). <br>
	 * 
	 * @return number
	 */
	@Override
	public BasePrimitiveNumber baseToNumber() {
		return this.baseToPrimitive( ToPrimitiveHint.NUMBER ).baseToNumber();
	}
	
	/**
	 * [[DefaultValue]] (hint) When the [[DefaultValue]] method of O is called
	 * with hint String, the following steps are taken:<br>
	 * 1. Call the [[Get]] method of object O with argument "toString".<br>
	 * 2. If Result(1) is not an object, go to step 5.<br>
	 * 3. Call the [[Call]] method of Result(1), with O as the this value and an
	 * empty argument list.<br>
	 * 4. If Result(3) is a primitive value, return Result(3).<br>
	 * 5. Call the [[Get]] method of object O with argument "valueOf".<br>
	 * 6. If Result(5) is not an object, go to step 9.<br>
	 * 7. Call the [[Call]] method of Result(5), with O as the this value and an
	 * empty argument list.<br>
	 * 8. If Result(7) is a primitive value, return Result(7).<br>
	 * 9. Throw a TypeError exception.
	 * <p>
	 * When the [[DefaultValue]] method of O is called with hint Number, the
	 * following steps are taken:<br>
	 * 1. Call the [[Get]] method of object O with argument "valueOf".<br>
	 * 2. If Result(1) is not an object, go to step 5.<br>
	 * 3. Call the [[Call]] method of Result(1), with O as the this value and an
	 * empty argument list.<br>
	 * 4. If Result(3) is a primitive value, return Result(3).<br>
	 * 5. Call the [[Get]] method of object O with argument "toString".<br>
	 * 6. If Result(5) is not an object, go to step 9.<br>
	 * 7. Call the [[Call]] method of Result(5), with O as the this value and an
	 * empty argument list.<br>
	 * 8. If Result(7) is a primitive value, return Result(7).<br>
	 * 9. Throw a TypeError exception.<b> When the [[DefaultValue]] method of O
	 * is called with no hint, then it behaves as if the hint were Number,
	 * unless O is a Date object (see 15.9), in which case it behaves as if the
	 * hint were String. The above specification of [[DefaultValue]] for native
	 * objects can return only primitive values. If a host object implements its
	 * own [[DefaultValue]] method, it must ensure that its [[DefaultValue]]
	 * method can return only primitive values.
	 * <p>
	 * ToPrimitive The operator ToPrimitive takes a Value argument and an
	 * optional argument PreferredType. The operator ToPrimitive converts its
	 * value argument to a non-Object type. If an object is capable of
	 * converting to more than one primitive type, it may use the optional hint
	 * PreferredType to favour that type
	 * 
	 * @param hint
	 * @return EcmaPrimitive
	 */
	@Override
	public BasePrimitive<?> baseToPrimitive(final ToPrimitiveHint hint) {
		if (hint == null || hint == ToPrimitiveHint.NUMBER) {
			{
				final BaseObject<?> valueOf = this.baseGet( "valueOf" );
				assert valueOf != null : "Never returns java NULLs";
				final BaseFunction<?> call = valueOf.baseCall();
				if (call != null) {
					final ExecProcess ctx = Exec.currentProcess();
					final ExecStateCode code = ctx.vmCallV( call, this, true );
					if (code != null) {
						throw code == ExecStateCode.ERROR
								? new RuntimeException( ctx.vmGetResultImmediate().toString() )
								: new IllegalStateException( "Illegal state: " + code );
					}
					if (ctx.vmGetResultImmediate() != this) {
						return ctx.vmGetResultImmediate().baseToPrimitive( ToPrimitiveHint.NUMBER );
					}
				}
			}
			{
				final BaseObject<?> toString = this.baseGet( "toString" );
				assert toString != null : "Never returns java NULLs";
				final BaseFunction<?> call = toString.baseCall();
				if (call != null) {
					final ExecProcess ctx = Exec.currentProcess();
					final ExecStateCode code = ctx.vmCallV( call, this, false );
					if (code != null) {
						throw code == ExecStateCode.ERROR
								? new RuntimeException( ctx.vmGetResultImmediate().toString() )
								: new IllegalStateException( "Illegal state: " + code );
					}
					if (ctx.vmGetResultImmediate() != this) {
						return ctx.vmGetResultImmediate().baseToPrimitive( ToPrimitiveHint.NUMBER );
					}
				}
			}
		} else {
			{
				final BaseObject<?> toString = this.baseGet( "toString" );
				assert toString != null : "Never returns java NULLs";
				final BaseFunction<?> call = toString.baseCall();
				if (call != null) {
					final ExecProcess ctx = Exec.currentProcess();
					final ExecStateCode code = ctx.vmCallV( call, this, false );
					if (code != null) {
						throw code == ExecStateCode.ERROR
								? new RuntimeException( ctx.vmGetResultImmediate().toString() )
								: new IllegalStateException( "Illegal state: " + code );
					}
					if (ctx.vmGetResultImmediate() != this) {
						return ctx.vmGetResultImmediate().baseToPrimitive( ToPrimitiveHint.STRING );
					}
				}
			}
			{
				final BaseObject<?> valueOf = this.baseGet( "valueOf" );
				assert valueOf != null : "Never returns java NULLs";
				final BaseFunction<?> call = valueOf.baseCall();
				if (call != null) {
					final ExecProcess ctx = Exec.currentProcess();
					final ExecStateCode code = ctx.vmCallV( call, this, true );
					if (code != null) {
						throw code == ExecStateCode.ERROR
								? new RuntimeException( ctx.vmGetResultImmediate().toString() )
								: new IllegalStateException( "Illegal state: " + code );
					}
					if (ctx.vmGetResultImmediate() != this) {
						return ctx.vmGetResultImmediate().baseToPrimitive( ToPrimitiveHint.STRING );
					}
				}
			}
		}
		return Base.forString( "[Native: " + this.getClass().getName() + "]" );
	}
	
	/**
	 * @return string
	 */
	@Override
	public BasePrimitiveString baseToString() {
		final BaseObject<?> primitive = this.baseToPrimitive( ToPrimitiveHint.STRING );
		assert primitive != null : "baseToPrimitive shouldn't result java NULL, class=" + this.getClass().getName();
		return primitive.baseToString();
	}
	
	/**
	 * Internal state information associated with this object.
	 * 
	 * @return internal
	 */
	@Override
	public abstract T baseValue();
	
	private boolean checkPrototypeChainDuplicates() {
		final Set<Integer> set = new TreeSet<Integer>();
		for (BaseObject<?> object = this;;) {
			if (!set.add( new Integer( System.identityHashCode( object ) ) )) {
				return false;
			}
			if ((object = object.basePrototype()) == null) {
				return true;
			}
		}
	}
	
	@Override
	public abstract boolean equals(final Object o);
	
	@Override
	public abstract int hashCode();
	
	/**
	 * @param prototype
	 */
	protected final void internReplacePrototype(final BaseObject<?> prototype) {
		this.prototype = prototype;
	}
}
