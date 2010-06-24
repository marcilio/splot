package splot.core;

import java.util.Map;

public class HandlerExecutionException extends Exception {
	
	public HandlerExecutionException(String errorMessage) {
		super(errorMessage);
	}

	public HandlerExecutionException(String errorMessage, Throwable throwable ) {
		super(errorMessage, throwable);
	}	

}
