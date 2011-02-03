package ru.myx.ae3.exec;

/**
 * @author myx
 * 
 */
public enum InstructionResult {
	/**
	 * Throws or returns
	 */
	NEVER {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case NEVER:
				return NEVER;
			default:
				return resultType;
			}
		}
	},
	/**
	 * exactly undefined
	 */
	UNDEFINED {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case UNDEFINED:
				return UNDEFINED;
			default:
				return OBJECT;
			}
		}
	},
	/**
	 * exactly null
	 */
	NULL {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case NULL:
				return NULL;
			default:
				return OBJECT;
			}
		}
	},
	/**
	 * boolean type
	 */
	BOOLEAN {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case BOOLEAN:
				return BOOLEAN;
			default:
				return OBJECT;
			}
		}
	},
	/**
	 * int32
	 */
	INTEGER {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case INTEGER:
				return INTEGER;
			case NUMBER:
				return NUMBER;
			default:
				return OBJECT;
			}
		}
	},
	/**
	 * any number
	 */
	NUMBER {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case INTEGER:
			case NUMBER:
				return NUMBER;
			default:
				return OBJECT;
			}
		}
	},
	/**
	 * exactly string
	 */
	STRING {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case STRING:
				return STRING;
			default:
				return OBJECT;
			}
		}
	},
	/**
	 * any object
	 */
	OBJECT {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			return OBJECT;
		}
	},
	/**
	 * array type object
	 */
	ARRAY {
		@Override
		public InstructionResult merge(final InstructionResult resultType) {
			switch (resultType) {
			case ARRAY:
				return ARRAY;
			default:
				return OBJECT;
			}
		}
	};
	
	/**
	 * Common mixed type (like for ||, && and ?: operators variable results)
	 * 
	 * @param resultType
	 * @return
	 */
	public abstract InstructionResult merge(InstructionResult resultType);
}
