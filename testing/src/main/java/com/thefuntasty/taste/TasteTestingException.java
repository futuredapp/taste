package com.thefuntasty.taste;

public class TasteTestingException extends RuntimeException {
	public TasteTestingException(String message) {
		super(message);
	}

	public TasteTestingException(String message, Throwable cause) {
		super(message, cause);
	}

	public TasteTestingException(Throwable cause) {
		super(cause);
	}
}
