/*
 * Created on 12.05.2006
 */
package ru.myx.ae3.exec;


/**
 * @author myx
 * 
 */
public enum ExecStateCode {
	/**
	 * 000
	 */
	RETURN {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			return this;
		}
		
		@Override
		public boolean isExecutionExit() {
			return true;
		}
		
		@Override
		public boolean isExecutionNext() {
			return false;
		}
		
		@Override
		public boolean isValidForCall() {
			return false;
		}
	},
	/**
	 * 001
	 */
	NEXT {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			return null;
		}
		
		@Override
		public boolean isExecutionExit() {
			return false;
		}
		
		@Override
		public boolean isExecutionNext() {
			return true;
		}
		
		@Override
		public boolean isValidForCall() {
			return true;
		}
	},
	/**
	 * 010
	 */
	BREAK {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			final int rCBT = ctx.rCBT;
			if (rCBT != -1) {
				ctx.r8IP = rCBT - 1;
				return null;
			}
			return this;
		}
		
		@Override
		public boolean isExecutionExit() {
			return false;
		}
		
		@Override
		public boolean isExecutionNext() {
			return false;
		}
		
		@Override
		public boolean isValidForCall() {
			return false;
		}
	},
	/**
	 * 011
	 */
	CONTINUE {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			final int rDCT = ctx.rDCT;
			if (rDCT != -1) {
				ctx.r8IP = rDCT - 1;
				return null;
			}
			return this;
		}
		
		@Override
		public boolean isExecutionExit() {
			return false;
		}
		
		@Override
		public boolean isExecutionNext() {
			return false;
		}
		
		@Override
		public boolean isValidForCall() {
			return false;
		}
	},
	/**
	 * 100 ?
	 */
	CALL {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			return this;
		}
		
		@Override
		public boolean isExecutionExit() {
			return false;
		}
		
		@Override
		public boolean isExecutionNext() {
			return true;
		}
		
		@Override
		public boolean isValidForCall() {
			return true;
		}
	},
	/**
	 * 101
	 */
	RESERVED_101 {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			return ctx.raise( "state code 101 is reserved!" );
		}
		
		@Override
		public boolean isExecutionExit() {
			return true;
		}
		
		@Override
		public boolean isExecutionNext() {
			return false;
		}
		
		@Override
		public boolean isValidForCall() {
			return false;
		}
	},
	/**
	 * 110
	 */
	RESERVED_110 {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			return ctx.raise( "state code 110 is reserved!" );
		}
		
		@Override
		public boolean isExecutionExit() {
			return true;
		}
		
		@Override
		public boolean isExecutionNext() {
			return false;
		}
		
		@Override
		public boolean isValidForCall() {
			return false;
		}
	},
	/**
	 * 111
	 */
	ERROR {
		@Override
		public ExecStateCode execute(final ExecProcess ctx) {
			return this;
		}
		
		@Override
		public boolean isExecutionExit() {
			return true;
		}
		
		@Override
		public boolean isExecutionNext() {
			return false;
		}
		
		@Override
		public boolean isValidForCall() {
			return true;
		}
	},
	/**
	 * 
	 */
	;
	
	private static final ExecStateCode[]	ALL	= ExecStateCode.values();
	
	/**
	 * @return
	 */
	public static final int valueCount() {
		return ExecStateCode.ALL.length;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public static final ExecStateCode valueForIndex(final int index) {
		return ExecStateCode.ALL[index];
	}
	
	/**
	 * @param ctx
	 * @return null if done in-line or this when should be handled by VM
	 */
	public abstract ExecStateCode execute(final ExecProcess ctx);
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isExecutionExit();
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isExecutionNext();
	
	/**
	 * Return's true for valid exit state codes for function calls by
	 * context.vmCall method
	 * 
	 * To use invalid return codes function should implement ExecFunctionUnsafe
	 * interface.
	 * 
	 * @return
	 */
	public abstract boolean isValidForCall();
}
