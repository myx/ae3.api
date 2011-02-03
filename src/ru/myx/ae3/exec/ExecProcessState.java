package ru.myx.ae3.exec;

/**
 * @author myx
 * 
 */
public enum ExecProcessState {
	/**
	 * Inactive
	 */
	INA,
	/**
	 * Running
	 */
	RUN,
	/**
	 * Doing a call
	 */
	CLL,
	/**
	 * Got a frame
	 */
	FRM,
	/**
	 * Waiting notification
	 */
	WNT,
	/**
	 * Waiting input
	 */
	WIN,
	/**
	 * 
	 */
	;
}
