package com.train.exception;

public class TrainException extends Exception {

	private static final long serialVersionUID = 1L;

	public TrainException() {
		super();
	}

	public TrainException(String message) {
		super(message);
	}

	public TrainException(Throwable cause) {
		super(cause);
		cause.printStackTrace();

	}
}
