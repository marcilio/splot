<#if !hasError> 

<#function genConstraintLiterals literals>
	<#assign output = ''>
	<#list literals as literal>
		<#assign output = output + '{"id" : "' + literal.literal_variable + '", "negated" : ' + literal.literal_negated +'}'>
		<#if literal_has_next>
			<#assign output = output + ",">
		</#if>
	</#list>
	<#return output>
</#function> 


<#function genFeatureDiagramHTML feature>
	<#assign output = '<div style="position: relative; left: 15px;" id="' + feature.feature_id + '">'>
	<#assign output = output + '  <span id="' + feature.feature_id +'_main">\n'>
	<#assign output = output + '  <img id="' + feature.feature_id + '_ecicon" onClick="expandCollapseTree(\'' + feature.feature_id + '\')"\n'>
	<#if (feature.feature_children?size > 0)>
		<#assign output = output + ' src="images/minus.jpg">'>
	<#else>
		<#assign output = output + ' src="images/blank.gif">'> 
	</#if>
	<#assign output = output + '<img id="' + feature.feature_id + '_type" src="images/' +feature.feature_type + '.gif">\n'>
	<#assign output = output + '<span id="' + feature.feature_id + '_name_container">'>
	<#assign output = output + '<span id="' + feature.feature_id + '_name" class="normalFeature"'>
	<#assign output = output + ' onmouseover="setMenuTarget(\'' + feature.feature_id + '\')"'>
	<#assign output = output + ' onmouseout="highlightFeature(\'' + feature.feature_id + '\',\'off\')"'>
	<#assign output = output + ' onclick="selectFeature(\'' + feature.feature_id + '\')">'>
	<#assign output = output + '' + feature.feature_name + '</span>'>
	<#assign output = output + '<span id="'+ feature.feature_id + '_analysis_result"></span>'>
	<#assign output = output + '</span></span>'>
	<#assign output = output + '\n<!-- beginning of ' + feature.feature_id + ' children -->'>
	<#assign output = output + '  <span id="' + feature.feature_id + '_children">'>

	<#list feature.feature_children as child>
		<#assign output = output + genFeatureDiagramHTML(child)>
	</#list>
	
	<#assign output = output + '</span></div>'>

	<#return output>
</#function> 

<#function genCrossTreeConstraintsHTML>

	<#assign output = '<table class="feature_model_edition_table1">'>
	<#assign output = output + ' <tr><td>'>
	<#assign output = output + ' <div id="cross_tree_constraints_wrapper">'>
	<#assign output = output + ' <div id="cross_tree_constraints">'>

	<#list constraints as constraint>
	
	  <#assign constraintId = constraint.constraint_name>
	  <#assign constraintEle = " <div id='" + constraintId + "' onmouseover='bindMenuToCrossTreeConstraint(\"" + constraintId + "\")'>">
	  <#assign constraintEle = constraintEle + "<img src='images/ctcbullet.gif'/>&nbsp;">
	  <#assign constraintEle = constraintEle + 	"<span id='" + constraintId + "_literals'>( ">
	
	  <#list constraint.constraint_literals as literal>
		<#assign featureId = literal.literal_variable>
		<#assign literalId = constraintId + "_literals" + featureId>
		<#assign literalEle = "<span id='" + literalId + "'>">
		<#assign negationEle = "<img id='" + literalId +"_negation'"> 
		<#if literal.literal_negated == "false"> 
		  <#assign negationEle = negationEle +" style='display: none'">
		</#if>
		<#assign negationEle = negationEle +" src='images/logic_not.gif'>">
		<#assign literalEle = literalEle + negationEle +  "<span id='" + literalId +"_variable'">
		<#assign literalEle = literalEle + "style='cursor:pointer;' onclick='changeLiteralPolarity(\"" + constraintId + "\"," + literal_index + ",\"" + literalId +"_negation\")' title='Left-click to change negation operator / right-click for menu access'>"> 
		<#assign literalEle = literalEle + literal.literal_feature_name + "</span>" +"</span>">
		<#assign constraintEle = constraintEle + literalEle>
		<#if literal_has_next>
		  <#assign constraintEle = constraintEle  + "<img src='images/logic_or.gif'/>">
		</#if>
	  </#list>
	  <#assign constraintEle = constraintEle + " )</span></div>\n">
	  <#assign output = output + constraintEle>
	</#list>
	
	<#assign output = output + '</div>'>
	<#assign output = output + '<p>&nbsp;</p><div id="new_constraint_link">'>
	<#assign output = output + '<a onclick="triggerCrossTreeConstraintDialog();" href="javascript:void(0)">Click to create a constraint</a>'>
	<#assign output = output + '</div></div></td></tr></table>'>
	
	<#return output>
</#function>
</#if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!--
Design by Free CSS Templates
http://www.freecsstemplates.org
Released for free under a Creative Commons Attribution 2.5 License

Name       : Compromise
Description: A two-column, fixed-width design with dark color scheme.
Version    : 1.0
Released   : 20081103

-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Welcome to the Software Product Lines Online Tools Homepage</title>

<link type="text/css" rel="stylesheet" href="splot.css"/>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dijit/themes/nihilo/nihilo.css"/>

<#if !hasError> 

<script type="text/javascript"
	 	src="http://ajax.googleapis.com/ajax/libs/dojo/1.3/dojo/dojo.xd.js" 
	 	djConfig="parseOnLoad: true, isDebug: false, dojoBlankHtmlUrl: 'blank.html'">
</script>
<script type="text/javascript" src="js/ajax.js"></script> 
<script type="text/javascript" src="js/json_utils.js"></script> 
<script type="text/javascript" src="js/utils.js"></script> 

<script type="text/javascript">

	dojo.require("dijit.form.Form");	
	dojo.require("dijit.form.Button");	
	dojo.require("dijit.form.TextBox");	
	dojo.require("dijit.Dialog");
	dojo.require("dijit.Menu");
	dojo.require("dojo.fx"); // for animate showing/hiding the Hint
	dojo.require("dojo.parser");


	var rootFeatureId;
	var currentlySelectedFeature;
	var featureModelData = {};
	/* 
	{ "_r" : 
		[	{"feature_id" : rootFeatureId}, 
		{"feature_name" : "Root Feature"}, 
		{"feature_type" : "root"}, 
		{"feature_description" : "The root feature"}, 
		{"count_children" : 0},
		{"parent_id" : ""},
		{"deleted" : false}, 
		{"feature_level" : 1}
		];
	}
	*/
	var crossTreeConstraints = {};
	/* Cross-tree constraints
		{ constraint_id: { "literals" : [ {"id":id ,"negated":true/false},...], "deleted" : true/false},
		  ...
		}		
	*/

	/******************************************************
	*  UNDO/REDO SUPPORT OPERATIONS
	*  The following operations are undoable/redoable
	*    - createFeature 
	*    - deleteFeature
	*******************************************************/

	// arrays for storing undo and redo actions
	var undoActions = [];
	var redoActions = [];
	var runningUNDOREDOAction = false; // signals to operations when the running operation is 	an UNDO/REDO action

	/* Each UNDO/REDO action is described by the following JSON structure
	 *   { description : "",       <- description of UNDO/REDO action
	 *     parameters : object,    <- parameters for UNDO/REDO function
	 *     action : function}       <- UNDO/REDO function to be called
	 */

	var menuTargetId;
	var featureIdCounter = 0;
	var countConstraints = 0; 
	var menuBoundConstraint;

	var realTimeAnalysisCountActionsTrigger = 0; 
	var realTimeAnalysisMaxActionsBeforeTriggering = 0; 
	var featureModelAnalysisData = {};
//	{
//		"isConsistent" : true/false, 
//		"hasDeadFeatures" : true/false,
//		"deadFeatures" : [ id1, id2, ... ],
//		"coreFeatures" : [ id1, id2, ... ],
//		"countValidConfigurations" : "100"
//	}
	
	/************************************************************************************************************
	*  FEATURE MODEL STATISTICS
	*************************************************************************************************************/
	var featureModelStats = {
			"statsCountFeatures" : 1,	
			"statsCountMandatory" : 0,	
			"statsCountOptional" : 0,	
			"statsCountXOR" : 0,	
			"statsCountOR" : 0,	
			"statsCountGrouped" : 0,	
			"statsCountConstraints" : 0,	
			"statsCTCR" : 0,	
			"statsDistinctCTCVars" : 0,	
			"statsCTCClauseDensity" : 0	
	};	

	var modelFileNameInRepository = '${model_filename_in_repository!""}';

	/******************************************************
	*  On Load
	*******************************************************/
	dojo.addOnLoad(function() {
		initialize();
	});


	/******************************************************
	*  Initialization: create/load feature model, cross-tree constraints, statistics, ...
	*******************************************************/
	function initialize() {
		//dojo.byId("featureNameInput").focus();
		//dojo.byId("featureNameInput").select();
		
		<#if new_model>
			// create new model
			rootFeatureId = "_r";
			featureModelData[rootFeatureId] = 
				[	{"feature_id" : rootFeatureId}, 
					{"feature_name" : "Type name for root feature"}, 
					{"feature_type" : "root"}, 
					{"feature_description" : "The root feature"}, 
					{"count_children" : 0},
					{"parent_id" : ""},
					{"deleted" : false}, 
					{"feature_level" : 1}
				]; 
		<#else>
			// load existing model
			rootFeatureId = '${root_feature.feature_id}';
			
			// Create Feature Diagram Structure
			<#list features as feature>
				featureModelData["${feature.feature_id}"] = 
					[	{"feature_id" : "${feature.feature_id}"}, 
						{"feature_name" : "${feature.feature_name}"}, 
						{"feature_type" : "${feature.feature_type}"}, 
						{"feature_description" : "Feature " + "${feature.feature_name}"}, 
						{"count_children" : ${feature.feature_children?size}},
						{"parent_id" : "${feature.feature_parent_id}"},
						{"deleted" : false}, 
						{"feature_level" : ${feature.feature_level}}
					];
			</#list>
			
			// dumpJSON(featureModelData);
						
			// Create CrossTree Constraints Structure
			<#list constraints as constraint>
				crossTreeConstraints["${constraint.constraint_name}"] = {
					"literals" : [ ${genConstraintLiterals(constraint.constraint_literals)} ], "deleted" : false
				};
			</#list>
			
			// dumpJSON(crossTreeConstraints);
			
	/* Cross-tree constraints
		{ constraint_id: { "literals" : [ {"id":id ,"negated":true/false},...], "deleted" : true/false},
		  ...
		}		
	*/
			
			// Statistics
			featureModelStats["statsCountFeatures"] = ${statsCountFeatures};	
			featureModelStats["statsCountMandatory"] = ${statsCountMandatory};	
			featureModelStats["statsCountOptional"] = ${statsCountOptional};	
			featureModelStats["statsCountXOR"] = ${statsCountXOR};	
			featureModelStats["statsCountOR"] = ${statsCountOR};	
			featureModelStats["statsCountGrouped"] = ${statsCountGrouped};	
			featureModelStats["statsCountConstraints"] = ${statsCountConstraints};	
			featureModelStats["statsCTCR"] = ${statsCTCR};	
			featureModelStats["statsDistinctCTCVars"] = ${statsDistinctCTCVars};	
			featureModelStats["statsCTCClauseDensity"] = ${statsCTCClauseDensity};
			
			featureIdCounter =  ${statsCountFeatures};
			countConstraints = ${statsCountConstraints}; 
			
		</#if>			
					
		currentlySelectedFeature = rootFeatureId;					
		updateFeatureModelStatistics('','','');
	}		
	
	/******************************************************
	*  Update feature model statistics
	*******************************************************/
	function updateFeatureModelStatistics( action, target, parameters ) {		
		if ( action == 'create' ) {
			var featureId = parameters.featureId;
			if ( target == 'feature' ) {
				if ( featureModelData[featureId][2].feature_type == 'mandatory' ) {
					featureModelStats.statsCountFeatures++;
					featureModelStats.statsCountMandatory++;
				}
				else if ( featureModelData[featureId][2].feature_type == 'optional' ) {
					featureModelStats.statsCountFeatures++;
					featureModelStats.statsCountOptional++;
				}
				else if ( featureModelData[featureId][2].feature_type == 'xor' ) {
					featureModelStats.statsCountXOR++;
				}
				else if ( featureModelData[featureId][2].feature_type == 'or' ) {
					featureModelStats.statsCountOR++;
				}
				else if ( featureModelData[featureId][2].feature_type == 'grouped' ) {
					featureModelStats.statsCountFeatures++;
					featureModelStats.statsCountGrouped++;
				}
			}
			else if ( target == 'constraint' ) {
				featureModelStats.statsCountConstraints++;
			}
		}
		else if ( action == 'delete' ) { 
			var featureId = parameters.featureId;
			if ( target == 'feature' ) {
				if ( featureModelData[featureId][2].feature_type == 'mandatory' ) {
					featureModelStats.statsCountFeatures--;
					featureModelStats.statsCountMandatory--;
				}
				else if ( featureModelData[featureId][2].feature_type == 'optional' ) {
					featureModelStats.statsCountFeatures--;
					featureModelStats.statsCountOptional--;
				}
				else if ( featureModelData[featureId][2].feature_type == 'xor' ) {
					featureModelStats.statsCountXOR--;
				}
				else if ( featureModelData[featureId][2].feature_type == 'or' ) {
					featureModelStats.statsCountOR--;
				}
				else if ( featureModelData[featureId][2].feature_type == 'grouped' ) {
					featureModelStats.statsCountFeatures--;
					featureModelStats.statsCountGrouped--;
				}
			}
			else if ( target == 'constraint' ) {
				featureModelStats.statsCountConstraints--;
			}			
		}
		else if ( action == 'change' ) { 
			if ( target == 'feature_type' ) {
				if ( parameters.old_type == 'mandatory' ) {
					featureModelStats.statsCountMandatory--;
					featureModelStats.statsCountOptional++;
				}
				else if ( parameters.old_type == 'optional' ) {
					featureModelStats.statsCountMandatory++;
					featureModelStats.statsCountOptional--;
				}
				else if ( parameters.old_type == 'xor' ) {
					featureModelStats.statsCountXOR--;
					featureModelStats.statsCountOR++;
				}
				else if ( parameters.old_type == 'or' ) {
					featureModelStats.statsCountXOR++;
					featureModelStats.statsCountOR--;
				}
			}
		}

		if ( target == 'feature' || target == 'constraint' ) {
			featureModelStats.statsDistinctCTCVars = findDistinctFeaturesInTheCrossTreeConstraints(true).length;
			if ( featureModelStats.statsCountConstraints > 0 ) { 
				featureModelStats.statsCTCClauseDensity = featureModelStats.statsCountConstraints / featureModelStats.statsDistinctCTCVars;
				featureModelStats.statsCTCR = featureModelStats.statsDistinctCTCVars / featureModelStats.statsCountFeatures;
			}
			else {
				featureModelStats.statsCTCClauseDensity = 0;
				featureModelStats.statsCTCR = 0; 
			}
		}
		
		for( statAttr in featureModelStats ) {
			if ( dojo.byId(statAttr) != null ) {
				dojo.byId(statAttr).innerHTML = featureModelStats[statAttr];
			}
		}
		// specific formatting
		dojo.byId("statsCTCR").innerHTML = featureModelStats["statsCTCR"].toFixed(2);
		dojo.byId("statsCTCClauseDensity").innerHTML = featureModelStats["statsCTCClauseDensity"].toFixed(2);		
	}

	
	/******************************************************
	*  Show/Hide Hint (animation)
	*******************************************************/
	function hideShowHint(option) {
		if (option == 'hide') {
			dojo.fx.wipeOut({node: "animHintPost",duration: 1000}).play();
			dojo.byId('instructions').style.display = 'inline';
		}
		else if (option == 'show') {
			dojo.byId('instructions').style.display = 'none';
			dojo.fx.wipeIn({node: "animHintPost",duration: 1000}).play();
		} 
		//document.getElementById("hintShortText").style.display = "block";
	}
	
	/******************************************************
	*  Debug JSON
	*******************************************************/
	function dumpJSON(data) {
		alert(JSON.stringify(data));
	}
	
	/******************************************************
	*  Debugging purposes: print all debugging info
	*******************************************************/
	function debugAll() {
		debugPrintUndoRedoLists();
		debugVisitFeatures();
		debugDeletedFeatures();
		debugCrossTreeConstraints();
	}

	/******************************************************
	*  Debugging purposes: print undo and redo action lists
	*******************************************************/
	function debugPrintUndoRedoLists() {
		dojo.byId('undoActions').innerHTML = "";
		dojo.byId('redoActions').innerHTML = "";
		for( i = 0 ; i < undoActions.length ; i++ ) {
			dojo.byId('undoActions').innerHTML += undoActions[i].description + ", ";
		}
		for( i = 0 ; i < redoActions.length ; i++ ) {
			dojo.byId('redoActions').innerHTML += redoActions[i].description + ", ";
		}
	}

	/******************************************************
	*  Debugging purposes: print visited features
	*******************************************************/
	function debugVisitFeatures() {
		dojo.byId('visitedFeatures').innerHTML = "";
		var visitedFeatures = [];
		visitFeatures(rootFeatureId, true, visitedFeatures);
		for( i = 0 ; i < visitedFeatures.length ; i++ ) {
			dojo.byId('visitedFeatures').innerHTML += featureModelData[visitedFeatures[i]][1].feature_name + ", ";
		}
	}
	
	/******************************************************
	*  Debugging purposes: print visited features
	*******************************************************/
	function debugDeletedFeatures() {
		dojo.byId('deletedFeatures').innerHTML = "";
		for( featureId in featureModelData ) {
			if ( featureModelData[featureId][6].deleted ) {
				dojo.byId('deletedFeatures').innerHTML += featureModelData[featureId][1].feature_name + ", ";
			}
		}
	}

	/******************************************************
	*  Debugging purposes: cross-tree constraints
	*******************************************************/
	function debugCrossTreeConstraints() {
		dojo.byId('crosstree_constraints').innerHTML = JSON.stringify(crossTreeConstraints); 
	}

	/******************************************************
	*  UNDO/REDO SUPPORT OPERATIONS
	*  The following operations are undoable/redoable
	*    - createFeature 
	*    - deleteFeature
	*******************************************************/
	
	/* Each UNDO/REDO action is described by the following JSON structure
	 *   { description : "",       <- description of UNDO/REDO action
	 *     parameters : object,    <- parameters for UNDO/REDO function
	 *     action : function}       <- UNDO/REDO function to be called
	 */
	
	/******************************************************
	*  Add UNDO Action
	*******************************************************/
	function addUndoAction(undoActionStructure, clearRedoList) {
		// adding an action to the UNDO list empties all REDO actions
		if ( clearRedoList ) {
			redoActions = [];
		}
		undoActions.push(undoActionStructure);
		updateMenuUndoRedoTitles();
	}
			
	/******************************************************
	*  Add REDO Action
	*******************************************************/
	function addRedoAction(redoActionStructure) {		
		redoActions.push(redoActionStructure);
		updateMenuUndoRedoTitles();
	}

	/******************************************************
	*  Undoes last action
	*******************************************************/
	function undo() {
		runningUNDOREDOAction = true;
		undoActionStructure = undoActions.pop();
		//alert("Undo " + undoActionStructure.description);
		undoActionStructure.action(undoActionStructure.description, undoActionStructure.parameters);
		updateMenuUndoRedoTitles();
		runningUNDOREDOAction = false;
	}

	/******************************************************
	*  Redoes last undone action
	*******************************************************/
	function redo() {
		runningUNDOREDOAction = true;		
		redoActionStructure = redoActions.pop();
		//alert("Redo " + redoActionStructure.description);
		redoActionStructure.action(redoActionStructure.description, redoActionStructure.parameters);		
		updateMenuUndoRedoTitles();
		runningUNDOREDOAction = false;		
	}

	/******************************************************
	*  Undo feature creation
	*******************************************************/
	function createFeatureUNDO(description, parameters) {
		// hide feature from feature diagram
		dojo.byId(parameters.feature_id).style.display = 'none';		
		// if it's a feature group a single grouped node has been created and must be deleted
		var visitedFeatures = [];
		visitFeatures(parameters.feature_id, true, visitedFeatures);
		for( i = 0 ; i < visitedFeatures.length ; i++ ) {
			featureModelData[visitedFeatures[i]][6].deleted = true;
		}		
		// mark feature as deleted
		featureModelData[parameters.feature_id][6].deleted = true;
		// deduct feature from parent's children counter
		//* featureModelData[getParentId(parameters.feature_id)][4].count_children--;
		updateChildrenCounter(getParentId(parameters.feature_id), -1);	
		// delete constraints from cross-tree constraints
		deleteAllCrossTreeConstraintsReferencingFeature(parameters.feature_id);
		// update statistics
		updateFeatureModelStatistics( 'delete', 'feature', { "featureId" : parameters.feature_id });				
		// creates REDO action
		addRedoAction(
				{  	"description" : "'creation of feature ' + featureModelData." + parameters.feature_id + "[1].feature_name",
				 	"parameters" : { "feature_id" : parameters.feature_id },
					"action" : createFeatureREDO
				});

		selectFeature(getParentId(parameters.feature_id));
	}

	/******************************************************
	*  Redo feature creation
	*******************************************************/
	function createFeatureREDO(description, parameters) {
		// display feature from feature diagram
		dojo.byId(parameters.feature_id).style.display = 'block';
		// unmark feature as deleted
		featureModelData[parameters.feature_id][6].deleted = false;
		// if it's a feature group a single grouped node has been created and must be recreated
		var visitedFeatures = [];
		visitFeatures(parameters.feature_id, false, visitedFeatures);
		for( i = 0 ; i < visitedFeatures.length ; i++ ) {
			featureModelData[visitedFeatures[i]][6].deleted = false;
		}		
		// deduct feature from parent's children counter
		//* featureModelData[getParentId(parameters.feature_id)][4].count_children++;
		updateChildrenCounter(getParentId(parameters.feature_id), +1);		
		// update statistics
		updateFeatureModelStatistics( 'create', 'feature', { "featureId" : parameters.feature_id } );				
		// ressurrects constraints from cross-tree constraints
		var constraints = findAllCrossTreeConstraintsReferencingFeature(parameters.feature_id, false);
		for( i = 0 ; i < constraints.length ;i++ ) {
			crossTreeConstraints[constraints[i]].deleted = false;
			dojo.byId(constraints[i]).style.display = 'block';
			// update statistics
			updateFeatureModelStatistics( 'create', 'constraint', {} );			
		}					
		// creates UNDO action
		addUndoAction(
				{  	"description" : "'creation of feature ' + featureModelData." + parameters.feature_id + "[1].feature_name",
				 	"parameters" : { "feature_id" : parameters.feature_id },
					"action" : createFeatureUNDO
				}, false);
		selectFeature(parameters.feature_id);
	}

	/******************************************************
	*  Undo feature deletion
	*******************************************************/
	function deleteFeatureUNDO(description, parameters) {
		// unmark all deleted features 
		for( i = 0 ; i < parameters.deleted_feature_ids.length ; i++ ) {
			featureModelData[parameters.deleted_feature_ids[i]][6].deleted = false;
			// update statistics
			updateFeatureModelStatistics( 'create', 'feature', { "featureId" : parameters.deleted_feature_ids[i] } );
			// ressurrects constraints from cross-tree constraints
			var constraints = findAllCrossTreeConstraintsReferencingFeature(parameters.deleted_feature_ids[i], false);
			for( j = 0 ; j < constraints.length ; j++ ) {
				crossTreeConstraints[constraints[j]].deleted = false;
				dojo.byId(constraints[j]).style.display = 'block';
				// update statistics
				updateFeatureModelStatistics( 'create', 'constraint', {} );			
			}
		}
		// displays root features and their subtrees in the feature model DOM structure
		// and updates parent's child feature counter
		selectFeatureId = parameters.root_feature_ids[0];
		for( i = 0 ; i < parameters.root_feature_ids.length ; i++ ) {
			//* featureModelData[getParentId(parameters.root_feature_ids[i])][4].count_children++;
			updateChildrenCounter(getParentId(parameters.root_feature_ids[i]), +1);
			dojo.byId(parameters.root_feature_ids[i]).style.display = 'block';
		}
		addRedoAction(
				{  	"description" : description,
				 	"parameters" : { 
				 		"root_feature_ids" : parameters.root_feature_ids,
				 		"deleted_feature_ids" : parameters.deleted_feature_ids 
				 	},
					"action" : deleteFeatureREDO
				});						
		selectFeature(selectFeatureId);
	}
	
	/******************************************************
	*  Redo feature deletion
	*******************************************************/
	function deleteFeatureREDO(description, parameters) {
		// remark features as deleted 
		for( i = 0 ; i < parameters.deleted_feature_ids.length ; i++ ) {
			featureModelData[parameters.deleted_feature_ids[i]][6].deleted = true;
			// update statistics
			updateFeatureModelStatistics( 'delete', 'feature', { "featureId" : parameters.deleted_feature_ids[i] } );
			// delete constraints from cross-tree constraints
			deleteAllCrossTreeConstraintsReferencingFeature(parameters.deleted_feature_ids[i]);			
		}
		// displays root features and their subtrees in the feature model DOM structure
		// and updates parent's child feature counter
		selectFeatureId = getParentId(parameters.root_feature_ids[0]);
		for( i = 0 ; i < parameters.root_feature_ids.length ; i++ ) {
			//* featureModelData[getParentId(parameters.root_feature_ids[i])][4].count_children--;
			updateChildrenCounter(getParentId(parameters.root_feature_ids[i]), -1)
			dojo.byId(parameters.root_feature_ids[i]).style.display = 'none';
		}
		addUndoAction(
				{  	"description" : description,
				 	"parameters" : { 
				 		"root_feature_ids" : parameters.root_feature_ids,
				 		"deleted_feature_ids" : parameters.deleted_feature_ids 
				 	},
					"action" : deleteFeatureUNDO
				}, false);						
		selectFeature(selectFeatureId);
	}
	
	/******************************************************
	*  Updates menu with appropriate undo/redo descriptions 
	*******************************************************/
	function updateMenuUndoRedoTitles() {
		for( i = 1 ; i < 7 ; i++ ) {
			if ( undoActions.length == 0 ) {
				dijit.byId('undo_id_'+i).attr('label', 'Undo');
				dijit.byId('undo_id_'+i).attr('disabled', true);
			}
			else {
				dijit.byId('undo_id_'+i).attr('label', 'Undo ' + eval(undoActions[undoActions.length-1].description));
				dijit.byId('undo_id_'+i).attr('disabled', false);
			}
			if ( redoActions.length == 0 ) {
				
				dijit.byId('redo_id_'+i).attr('label', 'Redo');
				dijit.byId('redo_id_'+i).attr('disabled', true);
			}
			else {
				dijit.byId('redo_id_'+i).attr('label', 'Redo ' + eval(redoActions[redoActions.length-1].description));
				dijit.byId('redo_id_'+i).attr('disabled', false);
			}
		}
	}	

	/******************************************************
	*  Expand/Collapse subtrees
	*******************************************************/
	function expandCollapseTree(featureId) {
		featureEle = dojo.byId(featureId + "_children");
		if ( featureEle.style.display == 'none' ) {
			featureEle.style.display = 'inline';
			dojo.byId(featureId + "_ecicon").src = "images/minus.jpg";
		}
		else {
			featureEle.style.display = 'none';
			dojo.byId(featureId + "_ecicon").src = "images/plus.jpg";
		}
	}	
	
	/******************************************************
	*  Expand/Collapse Feature Model Parts
	*******************************************************/
	function expandCollapseElement(partId) {
		if ( dojo.byId(partId).style.display == 'none' ) {			
			dojo.byId(partId).style.display = 'block';
		}
		else {
			dojo.byId(partId).style.display = 'none';
		}
	}	
	
	/******************************************************
	*  Updates feature child counter
	*******************************************************/
	function updateChildrenCounter(featureId, increment) {
		featureModelData[featureId][4].count_children += increment;		
		if ( featureModelData[featureId][4].count_children <= 0 ) {
			featureModelData[featureId][4].count_children = 0;
			dojo.byId(featureId + "_ecicon").src = "images/blank.gif";
		}
		else {
 			if ( dojo.byId(featureId + "_children").style.display == 'none' ) {
				dojo.byId(featureId + "_ecicon").src = "images/plus.jpg"; 
			}
			else {
				dojo.byId(featureId + "_ecicon").src = "images/minus.jpg"; 
			}
		}
	}

	/******************************************************
	*  Hide and Show and element on the page
	*******************************************************/
	function hideShowElement(elementId) {
		if( dojo.byId(elementId).style.display == 'none' ) {
			dojo.byId(elementId).style.display = 'inline';
		}
		else {
			dojo.byId(elementId).style.display = 'none';
		}
	}	
	
	/******************************************************
	*  Get the parent id of a given feature
	*******************************************************/
	function getParentId(featureId) {
		return featureModelData[featureId][5].parent_id;
	}	

	/******************************************************
	*  Check if it's the root feature
	*******************************************************/
	function isRoot(featureId) {
		return ( featureId == rootFeatureId ); 
	}	

	/******************************************************
	*  Check if it's a feature group
	*******************************************************/
	function isFeatureGroup(featureId) {
		return featureModelData[featureId][2].feature_type == 'xor' || featureModelData[featureId][2].feature_type == 'or';
	}	

	/******************************************************
	*  Check if feature is the last grouped feature in a group 
	*******************************************************/
	function isLastGroupedFeature(featureId) {
		if ( featureModelData[featureId][2].feature_type != 'grouped' ) {
			return false;
		}
		return (featureModelData[getParentId(featureId)][4].count_children == 1);
	}	
	
	/******************************************************
	*  Deletes feature (and subtree) currently selected 
	* in the feature diagram (variable menuTargetId) 
	*******************************************************/
	function deleteFeatureFromMenu() {
		deleteFeature(menuTargetId);
		onUserAction('delete','feature');
	}
	
	/******************************************************
	*  Deletes (logically) a feature and its subtree (UNDOABLE/REDOABLE OPERATION)
	*  - Parameters
	*    > deleteFeatureId: id of feature to be removed
	*  - Returns the id of the feature deleted 
	*******************************************************/
	function deleteFeature(deleteFeatureId) {

		// debug
		// dumpJSON(featureModelData);

		featureId = deleteFeatureId;
		// if feature is the last grouped feature in a group removes entire group
		if ( isLastGroupedFeature(featureId) ) {
			featureId = getParentId(featureId);
		} 
 
		var visitedFeatures = [];
		visitFeatures(featureId, true, visitedFeatures);
		for( i = 0 ; i < visitedFeatures.length ; i++ ) {
			// root cannot be deleted
			if ( !isRoot(visitedFeatures[i]) ) {
				featureModelData[visitedFeatures[i]][6].deleted = true;
				deleteAllCrossTreeConstraintsReferencingFeature(visitedFeatures[i]);
				// update statistics
				updateFeatureModelStatistics( 'delete', 'feature', { "featureId" : visitedFeatures[i] } );									
			}
		}
				
		if( isRoot(featureId) ) {
			// update parent's children counter
			featureModelData[rootFeatureId][4].count_children = 0;
			updateChildrenCounter(rootFeatureId, 0); 
			// remove children from feature model DOM structure
			childrenEle = dojo.byId(rootFeatureId+"_children");
			countChildren = childrenEle.childNodes.length;
			var deletedFeatures = [];
			for( i = 0 ; i < countChildren ; i++ ) {
				if ( typeof(childrenEle.childNodes[i]) != "undefined" && childrenEle.childNodes[i].tagName == 'DIV' ) {
					deletedFeatures[i] = childrenEle.childNodes[i].id;				
					currentNode = childrenEle.childNodes[i];				
					currentNode.style.display = 'none';
				}
			}			
			// creates undo structure
			addUndoAction(
			{  	"description" : "'deletion of all subtrees of root node'",
			 	"parameters" : { 
			 		"root_feature_ids" : deletedFeatures,
			 		"deleted_feature_ids" : visitedFeatures 
			 	},
				"action" : deleteFeatureUNDO
			}, true);			
			selectFeature(rootFeatureId);
		}
		else {
			// update parent's children counter
			currentNode = dojo.byId(featureId);
			parentId = currentNode.parentNode.parentNode.id;
			//*featureModelData[parentId][4].count_children--;
			updateChildrenCounter(parentId, -1);
			// remove node from feature model DOM structure
			currentNode.style.display = 'none';
			// creates undo structure
			addUndoAction(
			{  	"description" : "'deletion of feature ' + featureModelData." + featureId + "[1].feature_name + ' and its subtree'",
			 	"parameters" : { 
			 		"root_feature_ids" : [featureId],
			 		"deleted_feature_ids" : visitedFeatures 
			 	},
				"action" : deleteFeatureUNDO
			}, true);			
			selectFeature(parentId);		
		}

		// debug
		// dumpJSON(featureModelData);
		
		// one more action towards automatically trigerring automated analysis
		
		return featureId;
	}

	
	/****************************************************************
	* Visit the features of a given subtree in the feature diagram 
	*****************************************************************/
	function visitFeatures(featureId, skipDeleted, visitedFeatures) {
		var featObj = dojo.byId(featureId);
		if ( typeof(featObj) != "undefined" && featObj.tagName == 'DIV' ) {
			if ( !featureModelData[featureId][6].deleted || !skipDeleted ) {
				visitedFeatures.push(featureId);
				var featChildObj = dojo.byId(featureId + "_children");
				var countChildren = featChildObj.childNodes.length; 
				//dojo.query("div featureId + "_children"
				for( var i = 0 ; i < countChildren ; i++ ) {
					visitFeatures(featChildObj.childNodes[i].id, skipDeleted, visitedFeatures);
				}
			}
		}
	}
	
	/******************************************************
	*  Select feature
	*******************************************************/
	function selectFeature(featureId) {

		currentFeatureEle = dojo.byId(currentlySelectedFeature + "_name");
		if ( currentFeatureEle != null ) {
			currentFeatureEle.className = "normalFeature";
		}
		currentlySelectedFeature = featureId;
		currentFeatureEle = dojo.byId(currentlySelectedFeature + "_name");
		if ( currentFeatureEle != null ) {
			currentFeatureEle.className = "selectedFeatureEdition";
			showFeatureInformation(currentlySelectedFeature);
		}
	}

	/******************************************************
	*  Show feature information
	*******************************************************/
	function showFeatureInformation(elementId) {
		if (featureModelData[elementId][2].feature_type == "xor" || featureModelData[elementId][2].feature_type == "or") {
			dijit.byId("feature_name").attr("disabled", true);
		}
		else {
			dijit.byId("feature_name").attr("disabled", false);
		}
		for( i = 0 ; i < featureModelData[elementId].length ; i++ ) {
			dijit.byId("feature_information_form").attr("value", featureModelData[elementId][i]);
		}
	}

	/******************************************************
	*  Highlight feature as mouse passes over
	*******************************************************/
	function highlightFeature(featureId, state) {
		if ( featureId != currentlySelectedFeature ) {
			if ( state == 'on' ) {
				dojo.byId( featureId + "_name").className = 'mouseoverfeature';
			}
			else if ( state == 'off' ) {
				dojo.byId( featureId + "_name").className = 'normalFeature';
			}
		}
	}

	/******************************************************
	*  Notifies the user has taken an action (create/delete/clone feature/constraint)
	*******************************************************/
	function onUserAction(actionType, actionTarget) {
		updateAnalysisActionCounter(1);
		hideHighlightsInTheFeatureDiagram('deadcore');		
	}

	/******************************************************
	*  Menu: Identify which feature has been mouse hovered 
	* (targetId) and saves the corresponding menu object 
	*******************************************************/
	function setMenuTarget(targetId) {
		highlightFeature(targetId, 'on');
		menuTargetId = targetId;
		menuId = "popupMenuRoot";
		if ( featureModelData[targetId][2].feature_type == "mandatory" ) {
			menuId = "popupMenuMandatory";
		}
		else if ( featureModelData[targetId][2].feature_type == "optional" ) {
			menuId = "popupMenuOptional";
		}
		else if (featureModelData[targetId][2].feature_type == "xor" ) {
			menuId = "popupMenuXORGroup";
		}
		else if (featureModelData[targetId][2].feature_type == "or" ) {
			menuId = "popupMenuORGroup";
		}
		else if (featureModelData[targetId][2].feature_type == "grouped" ) {
			menuId = "popupMenuGrouped";
		}
		// debug
		//alert(targetId + '$' + featureModelData[targetId][2].feature_type + ': menu=' + menuId);
		dijit.byId(menuId).bindDomNode(dojo.byId(menuTargetId + "_name"));
	}
	
	/******************************************************
	* Makes a mandatory feature optional and vice-versa
	* Also makes a XOR group an OR group and vice-versa 
	*******************************************************/
	function changeFeatureType() {
		var oldType = featureModelData[menuTargetId][2].feature_type;
		if( featureModelData[menuTargetId][2].feature_type == "mandatory") {
			featureModelData[menuTargetId][2].feature_type = "optional";
			dojo.byId(menuTargetId+"_type").src = "images/optional.gif";				
		}
		else if( featureModelData[menuTargetId][2].feature_type == "optional") {
			featureModelData[menuTargetId][2].feature_type = "mandatory";
			dojo.byId(menuTargetId+"_type").src = "images/mandatory.gif";				
		}
		else if( featureModelData[menuTargetId][2].feature_type == "xor") {
			featureModelData[menuTargetId][2].feature_type = "or";
			featureModelData[menuTargetId][2].feature_name = "[1..*]";
			dojo.byId(menuTargetId+"_name").innerHTML = "[1..*]";
			dojo.byId(menuTargetId+"_type").src = "images/or.gif";				
		}
		else if( featureModelData[menuTargetId][2].feature_type == "or") {
			featureModelData[menuTargetId][2].feature_type = "xor";
			featureModelData[menuTargetId][2].feature_name = "[1..1]";
			dojo.byId(menuTargetId+"_name").innerHTML = "[1..1]";
			dojo.byId(menuTargetId+"_type").src = "images/xor.gif";				
		}
		selectFeature(menuTargetId);
		showFeatureInformation(menuTargetId);
		// update statistics
		updateFeatureModelStatistics( 'change', 'feature_type', {"old_type" : oldType, "new_type" : featureModelData[menuTargetId][2].feature_type } );				
	}

	/******************************************************
	*  Change feature name in the diagram and in the cross-tree constraints
	*******************************************************/
	function changeFeatureName(featureId, newFeatureName) {
		var featureName = trim(newFeatureName);
		if ( featureName.length == 0 ) {
			triggerNotificationDialog('error', 'SLOT Feature Model Editor', 'Feature name cannot be empty!')
			return false;
		}
		if ( !isFeatureNameValid(newFeatureName) ) {
			triggerNotificationDialog('error', 'SLOT Feature Model Editor', "Use only letters, digits, space and symbols '_' and '-' to name features!");
			return false;
		}
		featureModelData[featureId][1].feature_name = featureName;
		changeCrossTreeConstraintsVariableName(featureId, featureName);
		return true;
	}

	/******************************************************
	*  Checks if feature name is valid
	*******************************************************/ 
	function isFeatureNameValid(featureName) {
		var validchars = ' abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-';
		for( var i = featureName.length-1 ; i >= 0 ; i-- ) {
			if ( validchars.indexOf( featureName.charAt(i) ) == -1 ) {
				return false;
			} 
		}
		return true;	
	}
	
	/******************************************************
	*  Updates feature information based on user input data
	*******************************************************/
	function updateFeatureInformation() {
		// retrieve data from form
		featureId = dijit.byId("feature_id").attr("value");
		featureName = dijit.byId("feature_name").attr("value");		
		if ( changeFeatureName(featureId, featureName) ) {
			featureDescription = dijit.byId("feature_description").attr("value");
			featureModelData[featureId][3].feature_description = featureDescription;
			// update user interface
			dojo.byId(featureId + "_name").innerHTML = featureName;
			updateMenuUndoRedoTitles();
		}
		else {
			dijit.byId("feature_name").attr("value", featureModelData[featureId][1].feature_name);
		}
	}
	
	/******************************************************
	*  Creates a prefix for a given feature type
	*******************************************************/
	function getFeatureTypePrefix(featureType) {
		prefix = "man";
		if (featureType == "optional" ) {
			prefix = "opt";
		}
		else if (featureType == "xor" ) {
			prefix = "xor";
		}
		else if (featureType == "or" ) {
			prefix = "or";
		}
		else if (featureType == "grouped" ) {
			prefix = "grp";
		}
		return prefix;
	}
	
	/******************************************************
	*  Creates id for a new feature
	*******************************************************/
	function createNewFeatureElementId(parentId, featureType) {
		//* featureModelData[parentId][4].count_children++;
		updateChildrenCounter(parentId, 1);
		featureIdCounter++;
		return parentId + "_" + featureIdCounter;
	}
	
	/******************************************************
	*  Creates name for a new feature
	*******************************************************/
	function createNewFeatureName(featureType) {
		childName = "";
		if (featureType == "xor" ) {
			childName = "[1..1]";
		}
		else if (featureType == "or" ) {
			childName = "[1..*]";
		}
		else {
			childName = "Feature-" + featureIdCounter;
		}
		return childName;
	}

	/******************************************************
	*  Creates a new feature (UNDOABLE/REDOABLE OPERATION)
	*  - Parameters
	*    > featureType: type of feature (e.g. mandatory, ...)
	*    > relation: creates a "sibling" or "child"
	*  - Returns the id of the feature created
	*******************************************************/
	function createNewFeatureFromMenu(featureType, relation) {						
		createNewFeature(menuTargetId, featureType, relation, true);
		onUserAction('create','feature');
	}

	/******************************************************
	*  Apply user defined feature name to name feature 
	*******************************************************/
	function userDefinedFeatureName(inputObject, featureId, userDefinedFeatureName) {
	
		var oldFeatureName = featureModelData[featureId][1].feature_name;
		if ( changeFeatureName(featureId, userDefinedFeatureName) ) {
			featureModelData[featureId][1].feature_name = userDefinedFeatureName;
			featureNameEle = 
				'<span id="' + featureId + '_name"' +
					' class="normalFeature"' +
			  		' onmouseover="setMenuTarget(\'' + featureId + '\')"' +
			  		' onmouseout="highlightFeature(\'' + featureId + '\',\'off\')"' +
			  		' onclick="selectFeature(\'' + featureId + '\')">' + 
			  		userDefinedFeatureName + 
				'</span>';
			dojo.byId(featureId + "_name_container").innerHTML = featureNameEle;
			updateMenuUndoRedoTitles();		
			selectFeature(featureId);
		}
		else {
			inputObject.value = oldFeatureName;  
		}
	}

	/******************************************************
	*  Creates a new feature
	*  - Parameters
	*    > featureType: type of feature (e.g. mandatory, ...)
	*    > relation: creates a "sibling" or "child"
	*    > linkUndoActions: true -> links UNDO actions, false doesn't
	*  - Returns the id of the feature created
	*******************************************************/
	function createNewFeature(targetFeatureId, featureType, relation, createUndoAction) {

		parentId = targetFeatureId;
		if ( relation == 'sibling' ) {
			parentId = getParentId(targetFeatureId);
		}
		
		id = createNewFeatureElementId(parentId, featureType);
		name = createNewFeatureName(featureType);
		featureModelData[id] = [ 
		    {"feature_id" : id},
		    {"feature_name" : name},
		    {"feature_type" : featureType},
		    {"feature_description" : ""},
		    {"count_children" : 0},
		    {"parent_id" : parentId},
		    {"deleted" : false},
		    {"feature_level" : (featureModelData[parentId][7].feature_level+1)} 
		];
				
		// debug
		// dumpJSON(featureModelData);
		
		if (featureType == "xor" || featureType == "or") {
			newFeatureElement = 	
				'<div style="position: relative; left: 15px;" id="' + id + '">' + 
					'<span id="' + id +'_main">' +
						'<img id="' + id + '_ecicon" onClick="expandCollapseTree(\'' + id + '\')" src="images/blank.gif">' +
						'<img id="' + id + '_type" src="images/' +featureType + '.gif">' +
						'<span id="' + id + '_name_container">' +
							'<span id="' + id + '_name"' +
								' class="normalFeature"' +
						  		' onmouseover="setMenuTarget(\'' + id + '\')"' +
						  		' onmouseout="highlightFeature(\'' + id + '\',\'off\')"' +
						  		' onclick="selectFeature(\'' + id + '\')">' + 
						  		name + 
							'</span>' +
							'<span id="'+ id + '_analysis_result"></span>' +							
						'</span>' + 
					'</span>' +
			 		'<span id="' + id + '_children"></span>' +
				'</div>';					
		}
		else {
			newFeatureElement = 	
				'<div style="position: relative; left: 15px;" id="' + id + '">' + 
					'<span id="' + id +'_main">' +
						'<img id="' + id + '_ecicon" onClick="expandCollapseTree(\'' + id + '\')" src="images/blank.gif">' +
						'<img id="' + id + '_type" src="images/' +featureType + '.gif">' +
						'<span id="' + id + '_name_container">' +
						   '<input id="featureNameInput" onblur="userDefinedFeatureName(this, \'' + id + '\',this.value)" type="text" size="20" value="' + 
						   name + '"/>' +
						'</span>' + 
						'<span id="'+ id + '_analysis_result"></span>' +
					'</span>' +
			 		'<span id="' + id + '_children"></span>' +
				'</div>';
		}				

		// debug
		//alert(newFeatureElement);
			
		parentChildElement = dojo.byId(parentId + "_children");
		parentChildElement.innerHTML += newFeatureElement;
		selectFeature(id);

		// update statistics
		updateFeatureModelStatistics( 'create', 'feature', { "featureId" : id } );
		
		if ( createUndoAction ) {
			// creates undo structure
			addUndoAction(
			{  	"description" : "'creation of feature ' + featureModelData." + id + "[1].feature_name",
			 	"parameters" : { "feature_id" : id },
				"action" : createFeatureUNDO
			}, true);
		}
		
		// if creating a group enforces that at least one grouped feature is part of the group
		if (featureType == "xor" || featureType == "or") {
			createNewFeature(id, "grouped", "child", false);  // doesn't need to create and UNDO action for this grouped feature  
		}
		else {
			dojo.byId("featureNameInput").focus();
			dojo.byId("featureNameInput").select();
		}
		
		return id;
	}


	/******************************************************
	*  Filter the features in the dialog for creating a new cross-tree constraint
	*******************************************************/
	function filterFeaturesAtCTCDialog() {
		var filter = trim(dijit.byId("ctcFeatureFilter").attr("value"));
		for ( featureId in featureModelData ) {
			if ( !featureModelData[featureId][6].deleted && !isFeatureGroup(featureId) ) {
				var featureObj = dojo.byId('ctc_feature_' + featureId);
				if ( featureObj != null ) {
					var featureName = featureModelData[featureId][1].feature_name;
					if ( filter == '' || featureName.substring(0,filter.length) == filter ) {
						featureObj.style.display = 'block';
					}
					else {
						featureObj.style.display = 'none';
					}
				}				
			}
		}
	}

	/******************************************************
	*  Triggers the cross-tree constraint dialog as requested by user's menu click  
	*******************************************************/
	function triggerCrossTreeConstraintDialogFromMenu() {
		triggerCrossTreeConstraintDialog(menuBoundConstraint);
	}
	
	/******************************************************
	*  Triggers the cross-tree constraint dialog
	*******************************************************/
	function triggerCrossTreeConstraintDialog(constraintId) {
	
		var featuresListEle = '';		
		var visitedFeatures = [];
		visitFeatures(rootFeatureId, true, visitedFeatures);		
		for( i = 0 ; i < visitedFeatures.length ; i++  ) {
			var featureId = visitedFeatures[i];
			if ( !featureModelData[featureId][6].deleted && !isFeatureGroup(featureId) ) { 
				featuresListEle += 
					'<div id="ctc_feature_' + featureId + '">' +
					  '<input type="checkbox" id="checkbox_' + featureId + '" value="' + featureId + '"/>' +
					   featureModelData[featureId][1].feature_name +
					'</div>';
			}
		}
		dojo.byId('features_list').innerHTML = featuresListEle;
		
		if ( crossTreeConstraints[constraintId] != null ) {
			var literals = crossTreeConstraints[constraintId].literals;
			for( i = 0 ; i < literals.length ; i++ ) {
				dojo.byId('checkbox_' + literals[i].id).checked = true;
			}
		}
				
		dijit.byId('crossTreeConstraintEditionDialog').show();		
	}

	/******************************************************
	*  Change the polarity of a CNF literal 
	*******************************************************/
	function changeLiteralPolarity(constraintId, literalIndex, negationEleId) {
		crossTreeConstraints[constraintId].literals[literalIndex].negated = !crossTreeConstraints[constraintId].literals[literalIndex].negated;
		hideShowElement(negationEleId); 
	}
	
	/******************************************************
	*  Creates a cross-tree constraint from user input
	*******************************************************/
	function createNewCrossTreeConstraintFromDialog(arguments) {
		try {
			var variables = [];
			for( featureId in featureModelData ) {
				if ( !featureModelData[featureId][6].deleted && !isFeatureGroup(featureId) ) { 
					if ( dojo.byId("checkbox_" + featureId).checked ) {
						variables.push(featureId);
					}
				} 
			}		
			createNewCrossTreeConstraint(variables, null);
			onUserAction('create','constraint');
		}
		catch( err ) {
			//alert( err.description );
		}
	}
	
	/******************************************************
	*  Creates a cross-tree constraint
	*******************************************************/
	function createNewCrossTreeConstraint(variables, negatedList) {
	
		countConstraints++;
		var tmpCount = countConstraints;
		
		var constraintId = "constraint_" + tmpCount;
		// this loop prevents the new constraint from having the same ID of an existing constraint
		while( crossTreeConstraints[constraintId] ) {
			tmpCount++;
			constraintId = "constraint_" + tmpCount;			
		}

//		{ constraint_id: { "literals" : [ {"id":id ,"negated":true/false},...], "deleted" : true/false},
		
		var literals = [];
		for( i = 0 ; i < variables.length ; i++ ) {
			featureId = variables[i];
			if ( !featureModelData[featureId][6].deleted ) {
				if ( negatedList != null ) {
					literals.push({"id" : featureId, "negated" : negatedList[i]});
				}
				else {
					literals.push({"id" : featureId, "negated" : false});
				}
			}
		}
		
		if ( literals.length > 0 ) {
			crossTreeConstraints[constraintId] = { "literals" : literals, "deleted" : false };
			var constraintEle = 
				"<div id='" + constraintId + "' onmouseover='bindMenuToCrossTreeConstraint(\"" + constraintId + "\")'>" +
				"<img src='images/ctcbullet.gif'/>&nbsp;" +
				"<span id='" + constraintId + "_literals'>" +
				"( ";
			for( i = 0 ; i < crossTreeConstraints[constraintId].literals.length ; i++ ) {
				var featureId = crossTreeConstraints[constraintId].literals[i].id;
				var literalId = constraintId + "_literals" + featureId;
				var literalEle =				
					"<span id='" + literalId + "'>";

				negationEle = "<img id='" + literalId +"_negation' style='display: none' src='images/logic_not.gif'>";
				if ( negatedList != null ) {
					if ( negatedList[i] ) {  // variable is negated
						negationEle = "<img id='" + literalId +"_negation' src='images/logic_not.gif'>";
					}					
				}

				literalEle += negationEle +  
					"<span id='" + literalId +"_variable' style='cursor:pointer;' onclick='changeLiteralPolarity(\"" + constraintId + "\"," + i + ",\"" + literalId +"_negation\")' title='Left-click to change negation operator / right-click for menu access'>" + 
							featureModelData[featureId][1].feature_name +
						"</span>" +
					"</span>";  
					constraintEle += literalEle;
				if ( i < crossTreeConstraints[constraintId].literals.length-1 ) {
					constraintEle += "<img src='images/logic_or.gif'/>";
				}
			}
			constraintEle += " )</span></div>";
			dojo.byId('cross_tree_constraints').innerHTML += constraintEle;

			// update statistics
			updateFeatureModelStatistics( 'create', 'constraint', {} );

		}
		return constraintId; 
	}

	/******************************************************
	*  Binds cross-tree constraint menu to a given constraint 
	*******************************************************/
	function bindMenuToCrossTreeConstraint(constraintId) {
		menuBoundConstraint = constraintId;
		dijit.byId('crossTreeConstraintsMenu').bindDomNode(dojo.byId(constraintId));
	}

	/******************************************************
	*  Finds all cross-tree constraints referencing a given feature 
	*******************************************************/
	function findAllCrossTreeConstraintsReferencingFeature(featureId, skipsDeleted) {
		var constraints = [];
		for( constraintId in crossTreeConstraints ) {
			for( var i = 0 ; i < crossTreeConstraints[constraintId].literals.length ; i++ ) {
				if ( !skipsDeleted || !crossTreeConstraints[constraintId].deleted ) {
					if ( crossTreeConstraints[constraintId].literals[i].id == featureId ) {
						constraints.push(constraintId);
					}
				}
			}
		}
		return constraints;
	}

	/******************************************************
	*  Finds all distinct features in the cross-tree constraints
	*  and counts occurrences 
	*******************************************************/
	function findDistinctFeaturesInTheCrossTreeConstraints(skipsDeleted) {
		// { feature_id : occurences } 
		var features = [];
		// loop constraints
		for( constraintId in crossTreeConstraints ) {
			// loop literals of a constraint
			if ( !skipsDeleted || !crossTreeConstraints[constraintId].deleted ) {
				for( var i = 0 ; i < crossTreeConstraints[constraintId].literals.length ; i++ ) {
					var featureId = crossTreeConstraints[constraintId].literals[i].id;
					var found = false;
					for( var j = 0 ; j < features.length ; j++ ) {
						if ( featureId == features[j] ) {
							found = true;
							break;
						}
					}
					if ( !found ) {
						features.push(featureId);
					}							
				}
			}
		}
		return features;
	}


	/******************************************************
	*  Delete a cross-tree constraint based on menu action
	*******************************************************/
	function deleteCrossTreeConstraintFromMenu() {		
		deleteCrossTreeConstraint(menuBoundConstraint);
		onUserAction('delete','constraint');
	}

	/******************************************************
	*  Clone a cross-tree constraint based on menu action
	*******************************************************/
	function cloneCrossTreeConstraintFromMenu() {
		cloneCrossTreeConstraint(menuBoundConstraint);
		onUserAction('clone','constraint');
	} 
	
	/******************************************************
	*  Delete all cross-tree constraints referencing a given feature 
	*******************************************************/
	function deleteAllCrossTreeConstraintsReferencingFeature(featureId) {
		var constraints = findAllCrossTreeConstraintsReferencingFeature(featureId, true);
		for( var i = 0 ; i < constraints.length ; i++ ) {
			deleteCrossTreeConstraint(constraints[i]);
		}
		return constraints;
	}	

	/******************************************************
	*  Delete a cross-tree constraint (logically)
	*******************************************************/
	function deleteCrossTreeConstraint(constraintId) {
		//constraintObj = dojo.byId(constraintId);
		//constraintObj.parentNode.removeChild(constraintObj);
		crossTreeConstraints[constraintId].deleted = true;
		dojo.byId(constraintId).style.display = 'none';
		// update statistics
		updateFeatureModelStatistics( 'delete', 'constraint', {} );
	}

	/******************************************************
	*  Clone a cross-tree constraint 
	*******************************************************/
	function cloneCrossTreeConstraint(constraintId) {

		var variables = [];
		var negatedList = [];

		// { constraint_id: { "literals" : [ {"id":id ,"negated":true/false},...], "deleted" : true/false},
			
		for( i = 0 ; i < crossTreeConstraints[constraintId].literals.length ; i++ ) {
			variables[i] = crossTreeConstraints[constraintId].literals[i].id;
			negatedList[i] = crossTreeConstraints[constraintId].literals[i].negated;
		}
		
		createNewCrossTreeConstraint(variables, negatedList);
	}

	/******************************************************
	*  Clone a cross-tree constraint 
	*******************************************************/
	function changeCrossTreeConstraintsVariableName(featureId, newVariableName) {
		var constraints = findAllCrossTreeConstraintsReferencingFeature(featureId, false);
		for( i = 0 ; i < constraints.length ;i++ ) {
			dojo.byId(constraints[i] + "_literals" + featureId + "_variable").innerHTML = newVariableName;
		}
		return constraints;
	}
	
	/************************************************************************************************************
	*  COMMUNICATION WITH THE SERVER SIDE
	*************************************************************************************************************/
	
	/******************************************************
	*  Updates the counter for actions to automatically trigger analysis 
	*******************************************************/
	function updateAnalysisActionCounter(increment) {
		if ( realTimeAnalysisMaxActionsBeforeTriggering != 0 ) {
			realTimeAnalysisCountActionsTrigger += increment;
			realTimeAnalysisCountActionsTrigger = (realTimeAnalysisCountActionsTrigger > realTimeAnalysisMaxActionsBeforeTriggering) ? realTimeAnalysisMaxActionsBeforeTriggering : realTimeAnalysisCountActionsTrigger;
			dojo.byId('actionsLeftTowardsRunningAnalysisAutomatically').innerHTML = '<span class="orangeFont"><b>' + (realTimeAnalysisMaxActionsBeforeTriggering-realTimeAnalysisCountActionsTrigger) + ' action(s)</b> remaining for triggering analysis</span>';
			if ( realTimeAnalysisCountActionsTrigger >= realTimeAnalysisMaxActionsBeforeTriggering ) {
				runRealtimeAnalysis();
			}
		}
	}
	
	/******************************************************
	*  Resets the counter for actions to automatically trigger analysis 
	*******************************************************/
	function resetAnalysisActionCounter() {
		if ( realTimeAnalysisMaxActionsBeforeTriggering > 0 ) { 
			realTimeAnalysisCountActionsTrigger = 0;
			dojo.byId('actionsLeftTowardsRunningAnalysisAutomatically').innerHTML = '<span class="orangeFont"><b>' + realTimeAnalysisMaxActionsBeforeTriggering + ' action(s)</b> remaining for triggering analysis</span>';
		}
	}
	
	/******************************************************
	*  Sets the maximum number of actions before triggering analysis automatically 
	*******************************************************/
	function setRealTimeAnalysisMaxActionsBeforeTriggering(maxActions) {
		realTimeAnalysisMaxActionsBeforeTriggering = maxActions;
		if ( maxActions == 0 ) {
			realTimeAnalysisCountActionsTrigger = 0;
			dojo.byId('actionsLeftTowardsRunningAnalysisAutomatically').style.display = 'none';
		}
		else {
			dojo.byId('actionsLeftTowardsRunningAnalysisAutomatically').style.display = 'block';
			dojo.byId('actionsLeftTowardsRunningAnalysisAutomatically').innerHTML = '<span class="orangeFont"><b>' + maxActions + ' action(s)</b> remaining for triggering analysis</span>';
		}
	}
	
	/******************************************************
	*  Run Automated Analysis
	*******************************************************/
	function runRealtimeAnalysis() {

		var featureModelJSONAsString = encodeFeatureModelAsJSONString();
		dojo.byId('featureModelJSONString').innerHTML = featureModelJSONAsString; 

        //The parameters to pass to xhrPost, the message, and the url to send it to
        //Also, how to handle the return and callbacks.
        var xhrArgs = {
            url: "/SPLOT/SplotEditorServlet?action=realtime_analyses",
            sync : false,
            form: "featureModelSendToServerForm",
            handleAs: "json",
            headers: { "Content-Type": "application/x-www-form-urlencoded; charset=utf-8" },
            load: function(response, ioArgs) {
                closeNotificationDialog();
                resetAnalysisActionCounter();
            	featureModelAnalysisData = response;   // save analysis result
                if (response.isConsistent) {
                	dojo.byId('analysisIsConsistent').innerHTML = "Consistent";
                	dojo.byId('analysisIsConsistentMark').innerHTML = "<img src='images/checkmark.gif'/>";
                	if ( response.hasDeadFeatures ) {
                		dojo.byId('analysisHasDeadFeatures').innerHTML = '<span class="clickabletext" onclick="highlightCoreDeadFeaturesInTheFeatureDiagram(\'dead\');">' + response.deadFeatures.length + " feature(s)</span>";
                    	dojo.byId('analysisHasDeadFeaturesMark').innerHTML = "<img src='images/crossmark.gif'/>";
                		highlightCoreDeadFeaturesInTheFeatureDiagram('dead');
                	}
                	else {
                		dojo.byId('analysisHasDeadFeatures').innerHTML = "None";
                		dojo.byId('analysisHasDeadFeaturesMark').innerHTML = "<img src='images/checkmark.gif'/>";                	
                	}
            		dojo.byId('analysisCoreFeaturesMark').innerHTML = "<img src='images/checkmark.gif'/>";                	
            		dojo.byId('analysisCoreFeatures').innerHTML = '<span class="clickabletext" onclick="highlightCoreDeadFeaturesInTheFeatureDiagram(\'core\');">' + response.coreFeatures.length + " feature(s)</span>";
            		dojo.byId('analysisCountValidConfigurationsMark').innerHTML = "<img src='images/checkmark.gif'/>";                	
                	dojo.byId('analysisCountValidConfigurations').innerHTML = response.countValidConfigurations;
                }
                else {
                	dojo.byId('analysisIsConsistentMark').innerHTML = "<img src='images/crossmark.gif'/>";
                	dojo.byId('analysisIsConsistent').innerHTML = "Inconsistent";
                	dojo.byId('analysisHasDeadFeatures').innerHTML = "N/A";
                	dojo.byId('analysisCoreFeatures').innerHTML = "N/A";
                	dojo.byId('analysisCountValidConfigurations').innerHTML = "N/A";
            		dojo.byId('analysisCoreFeaturesMark').innerHTML = "";                	
            		dojo.byId('analysisHasDeadFeaturesMark').innerHTML = "";                	
                	dojo.byId('analysisHasDeadFeaturesMark').innerHTML = "";
            		dojo.byId('analysisCountValidConfigurationsMark').innerHTML = "";                	
                }
            },
            error: function(error) {
                resetAnalysisActionCounter();                
                closeNotificationDialog();                
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
            }
        }
        hideHighlightsInTheFeatureDiagram('deadcore');
        triggerNotificationDialog('wait', 'SLOT Feature Model Editor', 'Running analysis, please wait...')
        //Call the asynchronous xhrPost
        dojo.xhrPost(xhrArgs);        
	}

	/******************************************************
	*  Highlight core and dead features in the feature diagram 
	*******************************************************/
	function hideHighlightsInTheFeatureDiagram(type) {
		if ( featureModelAnalysisData != null ) {
			if ( type == 'dead' || type == 'deadcore') {
				if ( featureModelAnalysisData.deadFeatures != null ) {
					for( i = 0 ; i < featureModelAnalysisData.deadFeatures.length ; i++ ) {
							dojo.byId(featureModelAnalysisData.deadFeatures[i] + '_analysis_result').innerHTML = '';
						}
				}
			}
			if ( type == 'core' || type == 'deadcore') {
				if ( featureModelAnalysisData.coreFeatures != null ) {
					for( i = 0 ; i < featureModelAnalysisData.coreFeatures.length ; i++ ) {
						dojo.byId(featureModelAnalysisData.coreFeatures[i] + '_analysis_result').innerHTML = '';
					}
				}
			}
		} 
	}
	
	/******************************************************
	*  Highlight core and dead features in the feature diagram 
	*******************************************************/
//	{
//	"isConsistent" : true/false, 
//	"hasDeadFeatures" : true/false,
//	"deadFeatures" : [ id1, id2, ... ],
//	"coreFeatures" : [ id1, id2, ... ],
//	"countValidConfigurations" : "100"
//}
	function highlightCoreDeadFeaturesInTheFeatureDiagram(type) {
		if ( featureModelAnalysisData != null ) {
			var features = [];
			var comment = '';
			if ( type == 'dead' ) {
				features = featureModelAnalysisData.deadFeatures;
				comment = '<img style="cursor:pointer;vertical-align:bottom;" title="Click to hide dead features mark" onclick="hideHighlightsInTheFeatureDiagram(\'dead\')" height="16" width=120" src="images/dead_feature.gif"/>';
			}
			else if ( type == 'core' ) {
				features = featureModelAnalysisData.coreFeatures;
				comment = '<img style="cursor:pointer;vertical-align;bottom;" title="Click to hide core features mark" onclick="hideHighlightsInTheFeatureDiagram(\'core\')" height="16" width=120" src="images/core_feature.gif"/>';
			}
			for( i = 0 ; i < features.length ; i++ ) {
				dojo.byId(features[i] + '_analysis_result').innerHTML = comment;
			}
		} 
	}

	
	/******************************************************
	*  Encode feature model to send it to server 
	*******************************************************/
	function encodeFeatureModelAsJSONString() {

		/*
		JSON structure for the feature model
 		{	"feature_model_name" : "",
			"feature_diagram" : [[feature_id, feature_name, "feature_type", "parent_id"], ...],
			"crosstree_constraints" : {"constraint_1" : ['a','~b','c'], ... },
			"additional_information" : ... 				
		}
		*/
		
		var featureModelJSONString = '{ "feature_model_name" : "' + dijit.byId('fm_name').attr("value") + '", "feature_diagram" : [';

		/******************************************************************************
			FEATURE DIAGRAM (DFS traversal)
		******************************************************************************/
		
		var features = [];
		var visitedFeatures = [];
		visitFeatures(rootFeatureId, true, visitedFeatures);
		for( i = 0 ; i < visitedFeatures.length ; i++ ) {
			if ( !featureModelData[visitedFeatures[i]][6].deleted ) {
				featureModelJSONString += ( 
					'[' +
						'"' + visitedFeatures[i] + '",' +  
						'"' + featureModelData[visitedFeatures[i]][1].feature_name + '",' +
						'"' + featureModelData[visitedFeatures[i]][2].feature_type + '",' +
						'"' + featureModelData[visitedFeatures[i]][5].parent_id + '"' + 
					'],');
			}
		}
		featureModelJSONString += '[]],';


		/******************************************************************************
		CROSS-TREE CONSTRAINTS
		******************************************************************************/

		featureModelJSONString += '"crosstree_constraints" : {';
			
		var crosstree_constraints = [];
		for( constraintId in crossTreeConstraints ) {
			if ( !crossTreeConstraints[constraintId].deleted ) {
				featureModelJSONString += '"' + constraintId + '" : ';
				var literals = "[";
				for( var i = 0 ; i < crossTreeConstraints[constraintId].literals.length ; i++ ) {
					if ( crossTreeConstraints[constraintId].literals[i].negated ) {						
						literals +=  '"~' + crossTreeConstraints[constraintId].literals[i].id +'"';
					}
					else {
						literals += '"' + crossTreeConstraints[constraintId].literals[i].id +'"';
					}
					if ( i < crossTreeConstraints[constraintId].literals.length-1 ) {
						literals += ',';
					}
				}
				literals += '], ';
				featureModelJSONString += literals;				
			}			
		}
		featureModelJSONString += '"":[]}, ';

		/******************************************************************************
			ADDITIONAL INFORMATION
		******************************************************************************/
		featureModelJSONString += '"additional_information" : {';

		featureModelJSONString += '"fm_description" : "' + dijit.byId('fm_description').attr("value") + '",';
		featureModelJSONString += '"fm_author" : "' + dijit.byId('fm_author').attr("value") + '",';
		featureModelJSONString += '"fm_author_address" : "' + dijit.byId('fm_author_address').attr("value") + '",';
		featureModelJSONString += '"fm_author_email" : "' + dijit.byId('fm_author_email').attr("value") + '",';
		featureModelJSONString += '"fm_author_phone" : "' + dijit.byId('fm_author_phone').attr("value") + '",';
		featureModelJSONString += '"fm_author_website" : "' + dijit.byId('fm_author_website').attr("value") + '",';
		featureModelJSONString += '"fm_author_organization" : "' + dijit.byId('fm_author_organization').attr("value") + '",';
		featureModelJSONString += '"fm_author_department" : "' + dijit.byId('fm_author_department').attr("value") + '",';
		featureModelJSONString += '"fm_creation_date" : "' + dijit.byId('fm_creation_date').attr("value") + '",';
		featureModelJSONString += '"fm_publication" : "' + dijit.byId('fm_publication').attr("value") + '"';
		
		featureModelJSONString += "}}";
	
		return featureModelJSONString;
	}

	/******************************************************
	*  Export feature model to SXFM format 
	*******************************************************/
	function exportModel() {

		var featureModelJSONAsString = encodeFeatureModelAsJSONString();
		dojo.byId('featureModelJSONString').innerHTML = featureModelJSONAsString;
				 
        //The parameters to pass to xhrPost, the message, and the url to send it to
        //Also, how to handle the return and callbacks.
        var xhrArgs = {
            url: "/SPLOT/SplotEditorServlet?action=export_model",
            form: "featureModelSendToServerForm",
            handleAs: "json",
            headers: { "Content-Type": "application/x-www-form-urlencoded; charset=utf-8" },
            load: function(response, ioArgs) {
                // dumpJSON(response);
                closeNotificationDialog();                
				dojo.byId('topMessage1').innerHTML = 
					'<img src="images/important_icon.jpg"/>' +
					'Your model has been exported to SXFM format! <a target="_new" href="' + response.model_url + '">Click here</a> to open a <b>new page</b> containing your saved model.';
					dojo.byId('topMessage1').style.display = 'inline';
            },
            error: function(error) {
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
            }
        }
    	triggerNotificationDialog('wait', 'SPLOT Feature Model Editor', 'Exporting your model, please wait...');
        //Call the asynchronous xhrPost
        dojo.xhrPost(xhrArgs);        
	}

	/******************************************************
	*  Configure feature model 
	*******************************************************/
	function configureModel() {

		var featureModelJSONAsString = encodeFeatureModelAsJSONString();
		dojo.byId('featureModelJSONString').innerHTML = featureModelJSONAsString;
				 
        //The parameters to pass to xhrPost, the message, and the url to send it to
        //Also, how to handle the return and callbacks.
        var xhrArgs = {
            url: "/SPLOT/SplotEditorServlet?action=configure_model",
            form: "featureModelSendToServerForm",
            handleAs: "json",
            headers: { "Content-Type": "application/x-www-form-urlencoded; charset=utf-8" },
            load: function(response, ioArgs) {
                //dumpJSON(response);
                closeNotificationDialog();                
                var configURL = '/SPLOT/SplotConfigurationServlet?action=interactive_configuration_main&op=reset&userModels=&tmpModelPath=temp_models&selectedModels=' + response.model_filename;
                window.open(
                	configURL,
                	'SPLOT Interactive Configurator', 
                	'width=800,height=600,scrollbars=yes,toolbar=no,location=no,menubar=no,resizable=yes,directories=no,copyhistory=no'
                );
            },
            error: function(error) {
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
            }
        }
    	triggerNotificationDialog('wait', 'SPLOT Feature Model Editor', 'Opening SPLOT\'s configuration editor, please wait...');
        //Call the asynchronous xhrPost
        dojo.xhrPost(xhrArgs);        
	}
	
	/******************************************************
	*  Save feature model to repository 
	*******************************************************/
	function saveModelToRepository() {

		var featureModelJSONAsString = encodeFeatureModelAsJSONString();
		dojo.byId('featureModelJSONString').innerHTML = featureModelJSONAsString;
	
		var isNewModelInRepository = ( (modelFileNameInRepository.length == 0) ? "true" : "false" ); 
					 
        //The parameters to pass to xhrPost, the message, and the url to send it to
        //Also, how to handle the return and callbacks.
        var xhrArgs = {
            url: "/SPLOT/SplotEditorServlet?action=save_model_to_repository&new_model=" + isNewModelInRepository + "&model_filename_in_repository=" + modelFileNameInRepository,
            form: "featureModelSendToServerForm",
            handleAs: "json",
            headers: { "Content-Type": "application/x-www-form-urlencoded; charset=utf-8" },
            load: function(response, ioArgs) {
                // dumpJSON(response);
                closeNotificationDialog();
            	if ( response.error_saving_model ) {
            		triggerNotificationDialog('error', 'SPLOT Feature Model Editor', response.error_saving_model_message);
            	}
            	else {
	                if ( response.new_model ) {
						dojo.byId('topMessage2').innerHTML = 
							'<img src="images/important_icon.jpg"/>' +
							'Your model has been <b>added</b> to SPLOT\'s repository! <a target="_new" href="/SPLOT/feature_model_repository.html">Click here</a> to browse the repository in a <b>new page</b>.';
	                	triggerNotificationDialog('message', 'SPLOT Feature Model Editor', 'A <b>new entry</b> has been created for your model in the repository');
					}
					else {
						dojo.byId('topMessage2').innerHTML = 
							'<img src="images/important_icon.jpg"/>' +
							'Your model has been <b>updated</b> at SPLOT\'s repository! <a target="_new" href="/SPLOT/feature_model_repository.html">Click here</a> to browse the repository in a <b>new page</b>.';
						triggerNotificationDialog('message', 'SPLOT Feature Model Editor', 'Your model has been <b>updated</b> in the repository');					
					}
					dojo.byId('topMessage2').style.display = 'inline';
	                modelFileNameInRepository = response.model_filename;
	            }
            },
            error: function(error) {
                alert('Oops, SPLOT behaved like a bad boy :) If the error persists contact the SPLOT team.');
            }
        }
    	triggerNotificationDialog('wait', 'SPLOT Feature Model Editor', 'Saving your model in the repository, please wait...');
        //Call the asynchronous xhrPost
        dojo.xhrPost(xhrArgs);        
	}

	/******************************************************
	*  Close notification dialog 
	*******************************************************/
	function closeNotificationDialog() {
		dijit.byId('notificationDialog').hide();
	}
			
	/******************************************************
	*  Trigger notification dialog 
	*******************************************************/
	function triggerNotificationDialog(opType, title, message) {		
		dojo.byId('notificationDialogContent').innerHTML = message;
		if ( opType == 'wait' ) {
			dojo.byId('notificationDialogContentLoadingImage').style.display = 'block';
			dojo.byId('errorImage').style.display = 'none';
			dojo.byId('NotificationDialogOkButton').style.display = 'none';
		}
		else if ( opType == 'message' ) {
			dojo.byId('notificationDialogContentLoadingImage').style.display = 'none';
			dojo.byId('errorImage').style.display = 'none';
			dojo.byId('NotificationDialogOkButton').style.display = 'block';
		} 
		else if ( opType == 'error' ) {
			dojo.byId('errorImage').style.display = 'block';
			dojo.byId('notificationDialogContentLoadingImage').style.display = 'none';
			dojo.byId('NotificationDialogOkButton').style.display = 'block';
		} 
		dijit.byId('notificationDialog').attr('title', title);
		dijit.byId('notificationDialog').show();
	}
</script>

</#if>

</head>
<body class="nihilo">

<#if !hasError>

<!--  Notification Dialog -->
<div dojoType="dijit.Dialog"
	 style="display:none" 
	 id="notificationDialog" 
	 title=""
	 execute="">	
	 <div>
	 	<table border="0" width="100%" cellpadding="10">
	 	<tr><td align="center">
		 	<img id="errorImage" style="display:none" src="images/error_icon.jpg"/>
			<span id="notificationDialogContent"></span>
	 	</td></tr>
	 	<tr><td align="center">
	 		<img id="notificationDialogContentLoadingImage" style="display:none" src="images/loading.gif"/>
	 		<span id="NotificationDialogOkButton">
	    		<button dojoType="dijit.form.Button" type="button" onclick="dijit.byId('notificationDialog').hide();return false;">OK</button>
	    	</span>
	 	</td></tr>
	 	</table>
	</div>
</div>

<!--  Dojo/Dijit Menus -->

<div dojoType="dijit.Menu" id="popupMenuRoot" contextMenuForWindow="false" style="display: none;">
	<div dojoType="dijit.PopupMenuItem">
		<span>New Child Feature</span>
		<div dojoType="dijit.Menu" id="newChildFeatureSubmenu" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','child');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','child');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','child');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','child');">XOR group ([1..1])</div>
		</div>	
  	</div>
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="deleteFeatureFromMenu()">Delete Subtrees</div>    	
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="undo()" id="undo_id_1" disabled="true">Undo</div>    	
	<div dojoType="dijit.MenuItem" onClick="redo()" id="redo_id_1" disabled="true">Redo</div>    	
</div>

<div dojoType="dijit.Menu" id="popupMenuMandatory" contextMenuForWindow="false" style="display: none;">
	<div dojoType="dijit.PopupMenuItem">
		<span>New Child Feature</span>
		<div dojoType="dijit.Menu" id="popupMenuMandatoryChild" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','child');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','child');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','child');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','child');">XOR group ([1..1])</div>
		</div>	
  	</div>
	<div dojoType="dijit.PopupMenuItem">
		<span>New Sibling Feature</span>
		<div dojoType="dijit.Menu" id="popupMenuMandatoryOptionalSibling" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','sibling');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','sibling');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','sibling');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','sibling');">XOR group ([1..1])</div>
		</div>	
  	</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="changeFeatureType();">Make Optional</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="deleteFeatureFromMenu()">Delete Feature and Its Subtree</div>
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="undo()" id="undo_id_2" disable="true">Undo</div>    	
	<div dojoType="dijit.MenuItem" onClick="redo()" id="redo_id_2">Redo</div>    	
  	  
</div>

<div dojoType="dijit.Menu" id="popupMenuOptional" contextMenuForWindow="false" style="display: none;">
	<div dojoType="dijit.PopupMenuItem">
		<span>New Child Feature</span>
		<div dojoType="dijit.Menu" id="popupMenuOptionalChild" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','child');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','child');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','child');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','child');">XOR group ([1..1])</div>
		</div>	
  	</div>
	<div dojoType="dijit.PopupMenuItem">
		<span>New Sibling Feature</span>
		<div dojoType="dijit.Menu" id="popupMenuOptionalSibling" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','sibling');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','sibling');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','sibling');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','sibling');">XOR group ([1..1])</div>
		</div>	
  	</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="changeFeatureType();">Make Mandatory</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="deleteFeatureFromMenu()">Delete Feature and Its Subtree</div>  
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="undo()" id="undo_id_3" disable="true">Undo</div>    	
	<div dojoType="dijit.MenuItem" onClick="redo()" id="redo_id_3">Redo</div>    	
</div>

<div dojoType="dijit.Menu" id="popupMenuXORGroup" contextMenuForWindow="false" style="display: none;">
	<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('grouped','child');">New Child Feature</div>
	<div dojoType="dijit.PopupMenuItem">
		<span>New Sibling Feature</span>
		<div dojoType="dijit.Menu" id="popupMenuXORGroupSibling" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','sibling');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','sibling');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','sibling');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','sibling');">XOR group ([1..1])</div>
		</div>	
  	</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="changeFeatureType()">Make OR group</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="deleteFeatureFromMenu()">Delete Feature and Its Subtree</div>  
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="undo()" id="undo_id_4" disable="true">Undo</div>    	
	<div dojoType="dijit.MenuItem" onClick="redo()" id="redo_id_4">Redo</div>    	
</div>

<div dojoType="dijit.Menu" id="popupMenuORGroup" contextMenuForWindow="false" style="display: none;">
	<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('grouped', 'child');">New Child Feature</div>
	<div dojoType="dijit.PopupMenuItem">
		<span>New Sibling Feature</span>
		<div dojoType="dijit.Menu" id="popupMenuORGroupSibling" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','sibling');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','sibling');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','sibling');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','sibling');">XOR group ([1..1])</div>
		</div>	
  	</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="changeFeatureType()">Make XOR group</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="deleteFeatureFromMenu()">Delete Feature and Its Subtree</div>  
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="undo()" id="undo_id_5" disable="true">Undo</div>    	
	<div dojoType="dijit.MenuItem" onClick="redo()" id="redo_id_5">Redo</div>    	
</div>

<div dojoType="dijit.Menu" id="popupMenuGrouped" contextMenuForWindow="false" style="display: none;">
	<div dojoType="dijit.PopupMenuItem">
		<span>New Child Feature</span>
		<div dojoType="dijit.Menu" id="popupMenuGroupedChild" contextMenuForWindow="false" style="display: none;">
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('mandatory','child');">Mandatory</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('optional','child');">Optional</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('or','child');">OR group ([1..*])</div>
			<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('xor','child');">XOR group ([1..1])</div>
		</div>	
  	</div>
	<div dojoType="dijit.MenuItem" onClick="createNewFeatureFromMenu('grouped','sibling');">New Sibling</div>
  	<div dojoType="dijit.MenuSeparator"></div>
  	<div dojoType="dijit.MenuItem" onClick="deleteFeatureFromMenu()">Delete Feature and Its Subtree</div>  
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="undo()" id="undo_id_6" disabled="true">Undo</div>    	
	<div dojoType="dijit.MenuItem" onClick="redo()" id="redo_id_6" disabled="true">Redo</div>    	
</div>

<div dojoType="dijit.Dialog"
	 style="display:none" 
	 id="crossTreeConstraintEditionDialog" 
	 title="Cross-Tree Constraint Edition"
	 execute="createNewCrossTreeConstraintFromDialog(arguments[0])">	
	 <div>
		<p>Please, select the features for your constraint.</p>
		<p>Show only features starting with (case sensitive) <input dojoType="dijit.form.TextBox" type="text" id="ctcFeatureFilter" cols="10">
		<button dojoType="dijit.form.Button" onclick="filterFeaturesAtCTCDialog()">Filter</button>
		</p>
		<p><table border=0 width="100%">
		<tr><td class="ctcFeatureFiltering">
			<div id="features_list" style="width=200px; height=200px; overflow:auto;"></div>
		</td></tr>
		</table>
		</p>
	    <p>
	    	<button dojoType="dijit.form.Button" type="submit">Create Constraint</button>
	       	<button dojoType="dijit.form.Button" onclick="dijit.byId('crossTreeConstraintEditionDialog').hide();">Cancel</button>
	    </p>
	</div>
</div>

<div dojoType="dijit.Menu" id="crossTreeConstraintsMenu" contextMenuForWindow="false" style="display: none;">
	<div dojoType="dijit.MenuItem" onClick="triggerCrossTreeConstraintDialogFromMenu()">New Constraint</div>    	
	<div dojoType="dijit.MenuItem" onClick="cloneCrossTreeConstraintFromMenu()">Clone Constraint</div>    	
  	<div dojoType="dijit.MenuSeparator"></div>
	<div dojoType="dijit.MenuItem" onClick="deleteCrossTreeConstraintFromMenu()">Delete Constraint</div>    	
</div>

</#if>

<div id="header"><div id="logo"><img src="images/splot.jpg"/></div></div> 

<!-- end #header --> 
<div id="menu"> 
	<ul> 
		<li><a href="index.html">Home</a></li> 
		<li class="first"><a href="feature_model_edition.html">Feature Model Editor</a></li> 
		<li><a href="automated_analyses.html">Automated Analysis</a></li> 
		<li><a href="product_configuration.html">Product Configuration</a></li> 
		<li><a href="feature_model_repository.html">Feature Model Repository</a></li> 
		<li><a href="http://www.marcilio-mendonca.com/contact_splot.asp">Contact Us</a></li> 
	</ul> 
</div> 
<!-- end #menu --> 

<div id="wrapper"> 
<div class="btm"> 
	<div id="page"> 
		<div id="content">
		 
		 	<#if hasError>
		 	
				<div class="post"> 
					<div  class="entry">
						<p><span class="errorMessage">ERROR: ${errorMessage}.</span></p>
						<p><a href="javascript:history.back()">Back</a></p>
					</div>
				</div>						
		 	
		 	<#else>
		 
			<div id="instructions" class="hintBox" style="display:none;">
				<a onClick="hideShowHint('show')" href="javascript:void(0)">Show Instructions</a>
			</div>

			<div id="animHintPost" class="hintBox">
				<h1 class="title"><a title="Click to hide this Hint" onClick="hideShowHint('hide')" href="#">Instructions</a></h1>
				<ul>
				<#if new_model>
					<li>Start by typing a name for the <strong>root feature</strong> in the feature diagram below</li>
				<#else>
					<li>The feature model has been loaded and can now be edited and saved/updated in the repository</li>
				</#if>
					<li><strong>Left-click</strong> on feature's name to load feature data on the <b>Feature Information Table</b></li>
					<li><strong>Right-click</strong> on feature's name to edit feature diagram (e.g., create and delete features)</li>
					<li>Update feature diagram with Feature Information Table's data by pressing the <strong>Update Feature Model</strong> button</li>
					<li>Create cross-tree constraints as <a target="_new" href="http://en.wikipedia.org/wiki/Conjunctive_normal_form">CNF clauses</a> by using link <b><i>'click to create a constraint'</i></b></li>
					<li><strong>Left-click</strong> on variable names to create <b>negated literals</b> in the cross-tree constraints; click again to remove negation</li>
					<li><strong>Right-click</strong> anywhere in the constraint to access the cross-tree constraint menu</li>
					<li>Update the <strong>Additional Information section</strong> if necessary</li>
					<li>Only <b>consistent</b> models with <b>10+</b> features containing <b>no dead features</b> and with <b>mandatory additional information fields properly filled</b> will be added to the repository 
					<li>Remark 1: When a feature is renamed or deleted all cross-tree constraints referencing that feature are updated or deleted as well</li>
					<li>Remark 2: For <i>Feature-A</i> <b>requires</b> <i>Feature-B</i> use CNF clause: <img src="images/logic_not.gif"><i>Feature-A</i><img src="images/logic_or.gif"><i>Feature-B</i></li>
					<li>Remark 3: For <i>Feature-A</i> <b>excludes</b> <i>Feature-B</i> use CNF clause: <img src="images/logic_not.gif"/><i>Feature-A</i><img src="images/logic_or.gif"><img src="images/logic_not.gif"><i>Feature-B</i></li>
				</ul>
				<p><a onClick="hideShowHint('hide')" href="javascript:void(0)">Hide instructions</a></p>
			</div>

			<table width="100%" border="0">
			<tr>
				<td align="right" valign="bottom">
					<img onclick="configureModel()" title="Configure your feature model" style="cursor:pointer;" onmouseover="this.src='images/configure_model_icon_on.jpg'" onmouseout="this.src='images/configure_model_icon_off.jpg'" src="images/configure_model_icon_off.jpg"/>
					<img onclick="exportModel()" title="Export your feature model to the SXFM format" style="cursor:pointer;" onmouseover="this.src='images/export_to_sxfm_icon_on.jpg'" onmouseout="this.src='images/export_to_sxfm_icon_off.jpg'" src="images/export_to_sxfm_icon_off.jpg"/>
					<img onclick="saveModelToRepository()" title="Save your feature model to SPLOT's feature model repository" style="cursor:pointer;" onmouseover="this.src='images/save_model_to_repository_icon_on.jpg'" onmouseout="this.src='images/save_model_to_repository_icon_off.jpg'" src="images/save_model_to_repository_icon_off.jpg"/>
				</td>
			</tr>
			</table>
				
			<div class="post"> 
				<div  class="entry">
				
					<table border="0" width="100%">
						<tr><td><span class="hintBox2" style="display:none;" id="topMessage2"></span></td></tr>
						<tr><td><span class="hintBox2" style="display:none;" id="topMessage1"></span></td></tr>
						<tr><td><span class="hintBox2"><img src="images/important_icon.jpg"/>All information will be lost if you exit this page. Hence, make sure you export or save your model regularly.</span></td></tr>
					</table>					

					<table width=100% border=0 cellpadding=5 cellspacing=1>					
					
					<!--*********************************  
					     Left-hand side TABLES 
					**********************************-->
					
				 	<tr><td width="60%" align="left" valign="top">
				 	
				 		<table border=0 width=100% cellpadding=3 cellspacing=5>
					 		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('feature_diagram');">Feature Diagram</span></td></tr>
					 		<tr><td>
					 		
								<!--*********************************  
								     Feature Diagram 
								**********************************-->
					 			<table class="feature_model_edition_table1">
					 			<tr><td>
						 			<div id="feature_diagram">
						 			
									<!-- Feature Diagram -->
									<#if new_model>
										<div id="_r"> 
											<span id="_r_main">
												<img id="_r_ecicon" onclick="expandCollapseTree('_r')" src="images/blank.gif"/>
												<img id="_r_type" src="images/root.gif"/>
												<span id="_r_name_container">
										   			<input  id="featureNameInput" onblur="userDefinedFeatureName(this, '_r',this.value)" type="text" size="25" value="Type name for root feature"/>
										   		</span>
										   		<span id="_r_analysis_result"></span>
											</span>
									 		<span id="_r_children"></span>
								 		</div>
								 	<#else>
								 		${genFeatureDiagramHTML(root_feature)}
								 	</#if>
								 		
							 		</div>
							 	</td></tr>
							 	</table>
							 	
						 	</td></tr>

							<!--*********************************  
							     Cross-Tree Constraints 
							**********************************-->

					 		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('cross_tree_constraints_wrapper');">Cross-Tree Constraints</span></td></tr>
					 		<tr><td>
								
								<#if new_model>
						 			<table class="feature_model_edition_table1">
						 			<tr><td>
										<div id="cross_tree_constraints_wrapper">
								 			<div id="cross_tree_constraints"></div>
								 		<p>&nbsp;</p>
								 		<div id="new_constraint_link">
								 			<a onclick="triggerCrossTreeConstraintDialog();" href="javascript:void(0)">Click to create a constraint</a>
								 		</div>
									 	</div>
								 	</td></tr>
								 	</table>
								 <#else>
									${genCrossTreeConstraintsHTML()}
								 </#if>
							 	
					 		</td></tr>
					 		
							<!--*********************************  
								     Additional Information 
							**********************************-->
								
					 		<tr><td class="stylishTitle"><span title="Click to expand/collapse" onclick="expandCollapseElement('additional_information');">Additional Information</span></td></tr>
					 		<tr><td>					 			 
								<table border=0 id="additional_information" class="feature_model_edition_table1">
							    <tr>
							      <td align="left" ><label for="fm_name">Name your feature model: </label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_name" id="fm_name" value="${fm_name}"/> (*)</td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_description">Short description of feature model: </label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_description" id="fm_description" value="${fm_description}"/> (*)</td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_author">Primary Author: </label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_author" id="fm_author" value="${fm_author}"/> (*)</td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_author_address">Author's Address</label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_author_address" id="fm_author_address" value="${fm_author_address}"/></td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_author_email">Author's Email: </label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_author_email" id="fm_author_email" value="${fm_author_email}"/> (*)</td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_author_phone">Author's Phone Number: </label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_author_phone" id="fm_author_phone" value="${fm_author_phone}"/></td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_author_website">Author's Website</label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_author_website" id="fm_author_website" value="${fm_author_website}"/></td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_author_organization">Author's Organization</label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_author_organization" id="fm_author_organization" value="${fm_author_organization}"/> (*)</td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_author_department">Author's Organization Department</label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_author_department" id="fm_author_department" value="${fm_author_department}"/></td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_creation_date">Date model was created</label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_creation_date" id="fm_creation_date" value="${fm_creation_date}"/></td>
							    </tr>
							    <tr>
							      <td align="left" ><label for="fm_publication">Where was model published? (if applicable)</label></td>
							      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="fm_publication" id="fm_publication" value="${fm_publication}"/></td>
							    </tr>
							  	</table>
							  	<a href="javascript:void(0)"><b>(*)</b> Mandatory fields if you wish to add your model to SPLOT's feature model repository</a>								
					 		</td></tr>
					 	</table>
				 	</td>
				 	
					<!--*********************************  
					     Right-hand side TABLES 
					**********************************-->
				 	
				 	<td width="40%" align="right" valign="top">
				 	
			 			<table border=0 width=100% cellpadding=3 cellspacing=5>

						<!--*********************************  
						     Feature Information Table 
						**********************************-->

				 		<tr><td class="stylishTitle2"><span title="Click to expand/collapse" onclick="expandCollapseElement('feature_information_form');">Feature Information Table</span></td></tr>
				 		<tr><td>

				 			<form dojoType="dijit.form.Form" id="feature_information_form" jsId="feature_information_form" action="javascript:void(0)">				 	
							<table  class="feature_model_edition_table1">
						    <tr>
						      <td align="left" ><label for="feature_id">Id: </label></td>
						      <td align="left" ><input dojoType="dijit.form.TextBox" disabled="true" type="text" name="feature_id" id="feature_id"/></td>
						    </tr>
						    <tr>
						      <td align="left" ><label for="feature_name">Name: </label></td>
						      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="feature_name" id="feature_name"/></td>
						    </tr>
						    <tr>
						      <td align="left" ><label for="feature_description">Description: </label></td>
						      <td align="left" ><input dojoType="dijit.form.TextBox" type="text" name="feature_description" id="feature_description"/></td>
						    </tr>
						    <tr>
						      <td align="left" ><label for="feature_type">Type: </label></td>
						      <td align="left" ><input dojoType="dijit.form.TextBox" disabled="true" type="text" name="feature_type" id="feature_type"/></td>
						    </tr>
						    <tr>
						      <td align="left" ><label for="count_children">#Children: </label></td>
						      <td align="left" ><input dojoType="dijit.form.TextBox" disabled="true" type="text" name="count_children" id="count_children"/></td>
						    </tr>
						    <tr>
						    <tr>
						      <td align="left" ><label for="feature_level">Tree level: </label></td>
						      <td align="left" ><input dojoType="dijit.form.TextBox" disabled="true" type="text" name="feature_level" id="feature_level"/></td>
						    </tr>
						    <tr>
	     					  <td colspan="2" align="center"><button dojoType="dijit.form.Button" onClick="updateFeatureInformation();return false;" type="button">Update Feature Model</button></td>
	    					</tr>    
						  </table>
						  </form>
						</td></tr>
						
						 <!--*********************************  
						     Feature Model Statistics 
						**********************************-->

				 		<tr><td class="stylishTitle2"><span title="Click to expand/collapse" onclick="expandCollapseElement('reatime_statistics');">Feature Model Statistics</span></td></tr>
				 		<tr><td>

						  	<table  class="feature_model_edition_table1" id="reatime_statistics">
						    <tr>
						      <td align="left">#Features</td>
						      <td align="left"><span id="statsCountFeatures"></span></td>
						    </tr>
						    <tr>
						      <td align="left">#Mandatory</td>
						      <td align="left"><span id="statsCountMandatory"></span></td>
						    </tr>
						    <tr>
						      <td align="left">#Optional</td>
						      <td align="left"><span id="statsCountOptional"></span></td>
						    </tr>
						    <tr>
						      <td align="left">#XOR groups</td>
						      <td align="left"><span id="statsCountXOR"></span></td>
						    </tr>
						    <tr>
						      <td align="left">#OR groups</td>
						      <td align="left"><span id="statsCountOR"></span></td>
						    </tr>
						    <tr>
						      <td align="left">#Grouped</td>
						      <td align="left"><span id="statsCountGrouped"></span></td>
						    </tr>
						    <tr>
						      <td align="left">#Cross-Tree Constraints (CTC)</td>
						      <td align="left"><span id="statsCountConstraints"></span></td>
						    </tr>
						    <tr>
						      <td align="left" title="Number of variables in the CTC divided by the number of features in the Feature Diagram">CTCR (%)</td>
						      <td align="left"><span id="statsCTCR"></span></td>
						    </tr>
						    <tr>
						      <td align="left" title="Number of distinct variables in the cross-tree constraints">#CTC distinct vars</td>
						      <td align="left"><span id="statsDistinctCTCVars"></span></td>
						    </tr>
						    <tr>
						      <td align="left" title="Number of constraints divided by the number of variables in the CTC">CTC clause density</td>
						      <td align="left"><span id="statsCTCClauseDensity"></span></td>
						    </tr>
						  	</table>						  
						</td></tr>
						 
						 <!--*********************************  
						     Feature Model Analysis Table 
						**********************************-->

				 		<tr><td class="stylishTitle2"><span title="Click to expand/collapse" onclick="expandCollapseElement('realtime_analyses');">Feature Model Analysis</span></td></tr>
				 		<tr><td>

						  	<table  class="feature_model_edition_table1" id="realtime_analyses">
						    <tr>
						      <td align="left"><span id="analysisIsConsistentMark"></span></td>
						      <td align="left" title="Checks if feature model has at least one valid configuration">Consistency</td>
						      <td align="left" ><span id="analysisIsConsistent"></span></td>
						    </tr>
						    <tr>
						      <td align="left"><span id="analysisHasDeadFeaturesMark"></span></td>
						      <td align="left" title="Features that are never included in any valid configuration">Dead Features</td>
						      <td align="left" ><span id="analysisHasDeadFeatures"></span></td>
						    </tr>
						    <tr>
						      <td align="left"><span id="analysisCoreFeaturesMark"></span></td>
						      <td align="left" title="Features that are included in all valid configurations">Core Features</td>
						      <td align="left" ><span id="analysisCoreFeatures"></span></td>
						    </tr>
						    <tr>
						      <td align="left"><span id="analysisCountValidConfigurationsMark"></span></td>
						      <td align="left" title="Count the number of valid configurations">Valid Configurations</td>
						      <td align="left" ><span id="analysisCountValidConfigurations"></span></td>
						    </tr>
						    <tr>
						    <td colspan="3" align="center">
						    	<button dojoType="dijit.form.Button" onClick="runRealtimeAnalysis();return false;" type="button">Run Analysis</button>
						    </td>
						    </tr>
						   
						  	</table>
						  	Run Analysis every 
						  	  <select id="actionsForTriggeringAnalysisComboBox"
								      onchange="setRealTimeAnalysisMaxActionsBeforeTriggering(this.value)">				
						  		<option value="0">time I ask for</option>
						  		<option value="1">1 action</option>
						  		<option value="2">2 actions</option>
						  		<option value="5">5 actions</option>
						  		<option value="10">10 actions</option>
						  		<option value="20">20 actions</option>
						  	</select>
						  	<div id="actionsLeftTowardsRunningAnalysisAutomatically"></div>
						  	
						  		
						  
						</td></tr>
						</table>
						 
				 	</td></tr>
				 	</table>
					
					<p class="meta"></p>
					
					<!--  form that will hold feature model JSON string to be sent to the server -->
					<form id="featureModelSendToServerForm" style="display:none">
					  <textarea id="featureModelJSONString" name="featureModelJSONString"></textarea>
					</form>					

					<div style="display:none">
						<div onclick="debugAll();">DEBUG</div>
						UNDO:<div id="undoActions"></div>
						<p></p>
						REDO:<div id="redoActions"></div>
						<p></p>
						VISITED:<div id="visitedFeatures"></div>
						<p></p>
						DELETED:<div id="deletedFeatures"></div>
						<p></p>
						CONSTRAINTS:<div id="crosstree_constraints"></div>
					</div>
					
				</div>
			</div> <!-- post -->
			
			</#if>
			
		</div> <!-- content --> 
		<div style="clear: both;">&nbsp;</div> 
	</div> 
	<!-- end #page --> 
</div> 
</div> 

<div id="footer"> 
	<p><a href="http://gsd.uwaterloo.ca/">Generative Software Development Lab</a> / <a href="http://csg.uwaterloo.ca">Computer Systems Group</a>, University of Waterloo, Canada, 2009.</p> 
</div> 
<!-- end #footer --> 
	
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-1626595-6");
pageTracker._trackPageview();
} catch(err) {}

</script>
</body>
</html>


