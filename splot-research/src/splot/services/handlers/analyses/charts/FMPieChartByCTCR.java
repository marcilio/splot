package splot.services.handlers.analyses.charts;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelStatistics;

public class FMPieChartByCTCR {

	private int countModels;
	private int ranges[];
	private int data[];   // data[i] = number of models with range[i-1] < CTCR <= range[i]
	
	private LinkedList<FeatureModelStatistics> ctcrs;
	private int maxCTCR = Integer.MIN_VALUE;
	private int minCTCR = Integer.MAX_VALUE;
	private int totalCTCR = 0;

	public FMPieChartByCTCR(int ranges[]) {
		this.countModels = 0;
		this.ranges = ranges;
		this.data = new int[ranges.length+1];
		Arrays.fill(this.data, 0);
		ctcrs = new LinkedList<FeatureModelStatistics>();
	}
	
	public void process( FeatureModelStatistics stats ) {
		FeatureModel model = stats.getFeatureModel();
		int ctcr = (int)(stats.getECRepresentativeness()*100);
		totalCTCR += ctcr;
		countModels++;
		if ( ctcr > maxCTCR ) maxCTCR = ctcr; 
		if ( ctcr < minCTCR ) minCTCR = ctcr; 
		ctcrs.add(stats);
		for( int i = 0 ; i <= ranges.length ; i++ ) {
			if ( i < ranges.length ) {
				if ( ctcr <= ranges[i] ) {
					data[i]++;
					break;
				}
			}
			else {
				data[i]++;
			}
		}
	}
	
	
	public int countModels() {
		return countModels;
	}
	
	public int getMaxCTCR() {
		return maxCTCR;
	}
	
	public int getMinCTCR() {
		return minCTCR;
	}
	
	public int getAvgCTCR() {
		return (int)(totalCTCR/countModels);
	}
	
	public int getMeanCTCR() {
		FeatureModelStatistics array[] = ctcrs.toArray(new FeatureModelStatistics[0]);
		Arrays.sort(array, new Comparator<FeatureModelStatistics>() {
			 public int compare(FeatureModelStatistics m1, FeatureModelStatistics m2) {
				 double d1 = m1.getECRepresentativeness();
				 double d2 = m2.getECRepresentativeness();
				 if ( d1 > d2 ) {
					 return 1;
				 }
				 if ( d1 < d2 ) {
					 return -1;
				 }
				 return 0;
			 }
		});

		// if there's and odd number of models pick the one in the middle as the mean
		if ( countModels%2 != 0 ) {
			int index = (countModels-1)/2;
			return (int)(100*array[index].getECRepresentativeness()); 
		}
		// if it's even get the two in the middle and compute average as the mean
		int index1 = countModels/2;
		int index2 = index1-1;
		return (int)(100*(array[index1].getECRepresentativeness() + array[index2].getECRepresentativeness()) / 2); 
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
	
	public String asGoogleChartLabelString() {
		StringBuffer buffer = new StringBuffer();
		for( int i = 0 ; i <= ranges.length ; i++ ) {
			if ( i == 0 ) {
				buffer.append("0% (no extra contraints)");
			}
			else if ( i == ranges.length ){
				buffer.append(String.valueOf(ranges[ranges.length-1]+1) + "% or more");
			}
			else {
				buffer.append(String.valueOf(ranges[i-1]+1) +"% to " + String.valueOf(ranges[i]) + "%");
			}
			if ( i < ranges.length ) {
				buffer.append("|");
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
}
