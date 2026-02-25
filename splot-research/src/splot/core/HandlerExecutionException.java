/*                                    
    * Copyright (c) 2009-2026 Marcilio Mendonca                                            
    * Licensed under the Apache License, Version 2.0 (the "License");                      
    * you may not use this file except in compliance with the License.                     
    */

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
