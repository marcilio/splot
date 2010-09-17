package splot.services.handlers.analyses.charts;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import splar.core.fm.FeatureModelStatistics;

public class FMPieChartBySize {

	private int countModels;
	private int ranges[];
	private int data[];   // data[i] = number of models with range[i-1] < size <= range[i]
	
	private LinkedList<FeatureModelStatistics> sizes;
	private int maxSize = Integer.MIN_VALUE;
	private int minSize = Integer.MAX_VALUE;
	private int totalSize = 0;

	public FMPieChartBySize(int ranges[]) {
		this.countModels = 0;
		this.ranges = ranges;
		this.data = new int[ranges.length+1];
		Arrays.fill(this.data, 0);
		sizes = new LinkedList<FeatureModelStatistics>();
	}
	
	public void process( FeatureModelStatistics stats ) {
//		FeatureModel model = stats.getFeatureModel();
		countModels++;
		int modelSize = stats.countFeatures();
		totalSize += modelSize;
		if ( modelSize > maxSize ) maxSize = modelSize; 
		if ( modelSize < minSize ) minSize = modelSize; 
		sizes.add(stats);
		for( int i = 0 ; i < ranges.length ; i++ ) {
			if ( i < ranges.length ) {
				if ( modelSize <= ranges[i] ) {
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
	
	public int getTotalSize() {
		return totalSize;
	}
	
	public int getMaxSize() {
		return maxSize;
	}
	
	public int getMinSize() {
		return minSize;
	}
	
	public int getAvgSize() {
		return (int)(totalSize/countModels);
	}
	
	public int getMeanSize() {
		FeatureModelStatistics array[] = sizes.toArray(new FeatureModelStatistics[0]);
		Arrays.sort(array, new Comparator<FeatureModelStatistics>() {
			 public int compare(FeatureModelStatistics m1, FeatureModelStatistics m2) {
				 int d1 = m1.countFeatures();
				 int d2 = m2.countFeatures();
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
			return array[index].countFeatures(); 
		}
		// if it's even get the two in the middle and compute average as the mean
		int index1 = countModels/2;
		int index2 = index1-1;
		return (array[index1].countFeatures() + array[index2] .countFeatures()) / 2; 
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
				buffer.append("1 to " + String.valueOf(ranges[i]) + " features");
			}
			else if ( i == ranges.length ){
				buffer.append(String.valueOf(ranges[ranges.length-1]+1) + "+ features");
			}
			else {
				buffer.append(String.valueOf(ranges[i-1]+1) +" to " + String.valueOf(ranges[i]) + " features");
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
