package splot.services.handlers.analyses.charts;

import java.text.DecimalFormat;
import java.util.Arrays;

import splar.core.fm.FeatureModelStatistics;

public class FMPieChartByFeatureType {

	private int countFeatures;
	private int data[];   // data[i] = number of models with range[i-1] < CTCR <= range[i]
	
	public FMPieChartByFeatureType() {
		this.countFeatures = 0;
		this.data = new int[5];
		Arrays.fill(this.data, 0);
	}
	
	public void process( FeatureModelStatistics stats ) {
		countFeatures += stats.countFeatures();  // group nodes skipped (not to be confused with 'grouped' nodes)
		data[0]++; // root features
		data[1] += stats.countMandatory();
		data[2] += stats.countOptional();
		data[3] += stats.countGrouped11();
		data[4] += stats.countGrouped1n();
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
			float percentage = (data[i]*1f/countFeatures)*100;
			buffer.append(String.valueOf(data[i]) +" features or " + format.format(percentage) + "%");
			if ( i < data.length-1 ) {
				buffer.append("|");
			}
		}
		return buffer.toString();
	}
	
	public String asGoogleChartLabelString() {
		DecimalFormat format = new DecimalFormat("##0.0");
		return  "Root (" + format.format((data[0]*1f/countFeatures)*100) +  "%)" +
				"|Mandatory (" + format.format((data[1]*1f/countFeatures)*100) +  "%)" +
				"|Optional (" +format.format((data[2]*1f/countFeatures)*100) +  "%)" +
				"|XOR (" + format.format((data[3]*1f/countFeatures)*100) +  "%)" +
				"|OR (" + format.format((data[4]*1f/countFeatures)*100) + "%)";
	}

}
