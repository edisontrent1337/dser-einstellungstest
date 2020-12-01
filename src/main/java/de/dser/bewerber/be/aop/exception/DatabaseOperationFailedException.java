package de.dser.bewerber.be.aop.exception;

public class DatabaseOperationFailedException extends RuntimeException {
	
	private static final long serialVersionUID = 4731489279813411145L;
	
	public DatabaseOperationFailedException() {
	}

	public DatabaseOperationFailedException(String message) {
		super(message);
	}

}
