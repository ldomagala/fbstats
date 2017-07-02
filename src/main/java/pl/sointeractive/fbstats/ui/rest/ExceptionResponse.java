package pl.sointeractive.fbstats.ui.rest;

public class ExceptionResponse
{
	private final String exceptionType;
	private final String exceptionMessage;

	public ExceptionResponse(String exceptionType, String exceptionMessage) {
		this.exceptionType = exceptionType;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionType() {
		return exceptionType;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
}
