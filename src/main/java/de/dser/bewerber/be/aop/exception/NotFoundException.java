package de.dser.bewerber.be.aop.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -6594611929497893774L;

	public NotFoundException() {
	}
	
	public NotFoundException(String message) {
		super(message);
	}

}
