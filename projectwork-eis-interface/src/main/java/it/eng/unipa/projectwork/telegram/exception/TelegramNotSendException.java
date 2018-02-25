package it.eng.unipa.projectwork.telegram.exception;

public class TelegramNotSendException extends Exception {

	private static final long serialVersionUID = 1L;
	public TelegramNotSendException(Exception e) {
		super(e);
	}
}
