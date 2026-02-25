/*                                    
    * Copyright (c) 2009-2026 Marcilio Mendonca                                            
    * Licensed under the Apache License, Version 2.0 (the "License");                      
    * you may not use this file except in compliance with the License.                     
    */

package splot.services.extensions.fundp.utilities;

/** EvaluationResult is used to keep information about XPath evaluation. 
* 
* @author  PReCISE (research center of the University of FUNDP)
* @version 0.1
*/

public class  EvaluationResult{
	/** a comma-separated list of nodes, resulted from XPath evaluation */	
	public String nodesList;

	/** the error, raised in XPath evaluation */	
	public String  error;
}
