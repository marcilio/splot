package splot.services.handlers.analyses.charts;

import java.text.DecimalFormat;
import java.util.Arrays;

import splar.core.fm.FeatureModelStatistics;

public class FMPieChartBySource {

	private int countModels;
	private int data[];   // data[i] = number of models with range[i-1] < CTCR <= range[i]
	
	public FMPieChartBySource() {
		this.countModels = 0;
		this.data = new int[2];
		Arrays.fill(this.data, 0);
	}
	
	public void process( String featureModelName ) {
		countModels++;
		if ( featureModelName.startsWith("model_") ) {  // SPLOT's editor
			data[0]++;
		}
		else {
			data[1]++;
		}
	}
	
	public String asGoogleChartDataString() {
		StringBuffer buffer = new StringBuffer();
		for( int i = 0 ; i < data.length ; i++ ) {
			buffer.append(String.valueOf(data[i]));
			if ( i < data.length-1 ) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
		
	public String asGoogleChartLegendString() {
		DecimalFormat format = new DecimalFormat("##0.0");
		StringBuffer buffer = new StringBuffer();
		for( int i = 0 ; i < data.length ; i++ ) {
			float percentage = (data[i]*1f/countModels)*100;
			buffer.append(String.valueOf(data[i]) +" models or " + format.format(percentage) + "%");
			if ( i < data.length-1 ) {
				buffer.append("|");
			}
		}
		return buffer.toString();
	}
	
	public String asGoogleChartLabelString() {
		DecimalFormat format = new DecimalFormat("##0.0");
		return  "Created w/ SPLOT's Editor (" + format.format((data[0]*1f/countModels)*100) +  "%)" +
				"| Extracted from publications (" + format.format((data[1]*1f/countModels)*100) + "%)";
	}

}
