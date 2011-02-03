/*
 * Created on 22.03.2006
 */
package ru.myx.ae3.status;

/**
 * @author myx
 * 
 */
public abstract class StatusProvider implements StatusFiller {
	/**
	 * @return providers
	 */
	public StatusProvider[] childProviders() {
		return null;
	}
	
	/**
	 * @return status
	 */
	public final StatusInfo getStatus() {
		final StatusInfo result = new StatusInfo( this.statusDescription() );
		this.statusFill( result );
		result.childProviders = this.childProviders();
		return result;
	}
	
	/**
	 * @return string
	 */
	public abstract String statusDescription();
	
	@Override
	public abstract void statusFill(final StatusInfo status);
	
	/**
	 * @return string
	 */
	public abstract String statusName();
	
	@Override
	public final String toString() {
		final String s = this.statusName();
		return s + (s.length() < 8
				? "\t\t"
				: "\t") + this.statusDescription();
	}
}
