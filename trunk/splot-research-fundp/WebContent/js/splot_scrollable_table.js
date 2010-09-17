var configurationSteps;   // steps table
var maxVisibleSteps = 100000; // maximum number of visible steps
var firstVisibleStepIndex = 1;  // index of first visible step on the 'steps' table
var stepsTableId = "confStepsTable";
var stepsTableStyleClass = "standardTableStyle";

/******************************************************************
* Remove All Visible Steps from Steps Table
******************************************************************/
function removeAllSteps() {
	stepsTable = document.getElementById(stepsTableId);
	countRows = stepsTable.rows.length;
	for( i = 0 ; i < countRows ; i++ ) {
		stepsTable.deleteRow(0);
	}
}

/******************************************************************
 * Show Visible Steps on Steps Table
 ******************************************************************/
function showVisibleSteps() {
	removeAllSteps();
	stepsTable = document.getElementById(stepsTableId);
	stepsTable.className = stepsTableStyleClass;
	for( row = 0 ; row < configurationSteps[0].length ; row++ ) {
		newTableRow = stepsTable.insertRow(row);
		insertRowElements(newTableRow, row);
	}
}

/******************************************************************
* Insert a Row on the Steps Table
******************************************************************/
function insertRowElements(newTableRow, rowIndex) {

	cellIndex = 0;
	
	newTableCell = newTableRow.insertCell(cellIndex++);
	newTableCell.style.whiteSpace = 'nowrap';
	newTableCell.innerHTML = configurationSteps[0][rowIndex];
	
	endIndex = firstVisibleStepIndex + maxVisibleSteps;
	if ( endIndex > configurationSteps.length ) {
		endIndex = configurationSteps.length;
	}  

	for( i = firstVisibleStepIndex ; i < endIndex ; i++ ) {
		cellSpan = document.createElement("span");
		cellSpan.innerHTML = configurationSteps[i][rowIndex];
		newTableCell = newTableRow.insertCell(cellIndex++);
		newTableCell.style.whiteSpace = 'nowrap';
		newTableCell.appendChild(cellSpan);
	}
}

/******************************************************************
* Scroll Steps Table to the Left or Right  
******************************************************************/
function scrollStepsTable(direction) {
	if ( (direction < 0 && firstVisibleStepIndex > 1) || (direction > 0 && ( firstVisibleStepIndex+maxVisibleSteps < configurationSteps.length )) ) {
		removeAllSteps();
		firstVisibleStepIndex += direction;
		showVisibleSteps();
	}
}