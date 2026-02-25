/*                                    
    * Copyright (c) 2009-2026 Marcilio Mendonca                                            
    * Licensed under the Apache License, Version 2.0 (the "License");                      
    * you may not use this file except in compliance with the License.                     
    */

package splot.services.extensions.fundp.utilities;

/** FeatureInViewCheckingResult keeps information about the visibility of a feature in a view.
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/
public class FeatureInViewCheckingResult {
	
	/** if the feature should be shown in a view, its value is true, otherwise is false */	
	public boolean show;
	
	/** if the feature should be shown and grayed in a view, its value is true, otherwise is false */	
	public boolean available;
	
	/** if the checking process is done successfully for this feature, its value is true, otherwise is false */	
	public boolean executionStatus;
	
	/** a list of errors occurred in the checking process  */	
	public String  errorList;
}
