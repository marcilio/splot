package splot.services.handlers.analyses;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import splar.core.fm.FeatureModel;
import splar.core.fm.FeatureModelStatistics;
import splar.core.fm.XMLFeatureModel;
import splot.core.FreeMarkerHandler;
import splot.core.HandlerExecutionException;
import splot.services.handlers.analyses.charts.FMPieChartByCTCR;
import splot.services.handlers.analyses.charts.FMPieChartByFeatureType;
import splot.services.handlers.analyses.charts.FMPieChartBySize;
import splot.services.handlers.analyses.charts.FMPieChartBySource;
import freemarker.template.Configuration;
import freemarker.template.Template;


/*
 * Google charts for Feature Models in the Repository
 
by size
 * pie
 - up to 20 features
 - 21 - 50
 - 51 - 100
 - 101 - 200
 - 200 - 500
 - +500

http://chart.apis.google.com/chart?cht=p3&chd=t:10,20,30,40,50&chdl=30 models or 34|30 models or 34|30 models or 34|30 models or 34|30 models or 34%&chxl=0:|1 to 20 features|21 to 50 features|51 to 100 features|101 to 200 features|201 to 500 features&chbh=50,20,10&chtt=Size+of+Feature+Models&chts=000000,18&chs=600x200&chxt=x,y


by CTCR
 * pie
 - 0%
 - 1-10%
 - 11-20%
 - 21-20%
 - ...

by feature type (mandatory, optional, ...)
 * Pie

by source (extracted from papers x created online)

by author

by region
 - europe, north america, south america, africa, asia, not informed

by number of products
*/

public class DisplayRepositoryStatisticsHandler extends FreeMarkerHandler {

	public DisplayRepositoryStatisticsHandler(String handlerName, HttpServlet servlet, Configuration configuration, Template template) {
		super(handlerName, servlet, configuration, template);
	}
	
	public void buildModel(HttpServletRequest request, HttpServletResponse response, Map templateModel) throws HandlerExecutionException {

        try {

    		String modelsPath = getServlet().getInitParameter("modelsPath");
    		
    		File modelsDir = new File(modelsPath);		
			File models[] = modelsDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return (name.endsWith(".xml"));
				}
			});
					
			FMPieChartBySize chartBySize = new FMPieChartBySize(new int[] {20, 50, 100, 200, 500});
			FMPieChartByCTCR chartByCTCR = new FMPieChartByCTCR(new int[] {0, 10, 20, 30, 40, 50});
			FMPieChartByFeatureType chartByFeatType = new FMPieChartByFeatureType();
			FMPieChartBySource chartBySource = new FMPieChartBySource();
			for( File modelFile : models ) {				
				FeatureModel model = new XMLFeatureModel(modelFile.getAbsolutePath(), XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
				model.loadModel();
				FeatureModelStatistics stats = new FeatureModelStatistics(model);
				stats.update();
				chartBySize.process(stats);
				chartByCTCR.process(stats);
				chartByFeatType.process(stats);
				chartBySource.process(modelFile.getName());
			}
			
			// by size chart
			Hashtable tempHash = new Hashtable();
			tempHash.put("data", chartBySize.asGoogleChartDataString());
			tempHash.put("labels", chartBySize.asGoogleChartLabelString());
			tempHash.put("legend", chartBySize.asGoogleChartLegendString());		
			templateModel.put("fmChartBySize", tempHash);
			
			templateModel.put("countModels", chartBySize.countModels());
			templateModel.put("maxModelSize", chartBySize.getMaxSize());
			templateModel.put("minModelSize", chartBySize.getMinSize());
			templateModel.put("avgModelSize", chartBySize.getAvgSize());
			templateModel.put("meanModelSize", chartBySize.getMeanSize());
			templateModel.put("totalModelSize", chartBySize.getTotalSize());
				
			// by CTCR chart
			tempHash = new Hashtable();
			tempHash.put("data", chartByCTCR.asGoogleChartDataString());
			tempHash.put("labels", chartByCTCR.asGoogleChartLabelString());
			tempHash.put("legend", chartByCTCR.asGoogleChartLegendString());		
			templateModel.put("fmChartByCTCR", tempHash);
			
			templateModel.put("maxModelCTCR", chartByCTCR.getMaxCTCR());
			templateModel.put("minModelCTCR", chartByCTCR.getMinCTCR());
			templateModel.put("avgModelCTCR", chartByCTCR.getAvgCTCR());
			templateModel.put("meanModelCTCR", chartByCTCR.getMeanCTCR());

			// by feature type chart
			tempHash = new Hashtable();
			tempHash.put("data", chartByFeatType.asGoogleChartDataString());
			tempHash.put("labels", chartByFeatType.asGoogleChartLabelString());
			tempHash.put("legend", chartByFeatType.asGoogleChartLegendString());		
			templateModel.put("fmChartByFeatType", tempHash);
			
			// by feature model source
			tempHash = new Hashtable();
			tempHash.put("data", chartBySource.asGoogleChartDataString());
			tempHash.put("labels", chartBySource.asGoogleChartLabelString());
			tempHash.put("legend", chartBySource.asGoogleChartLegendString());		
			templateModel.put("fmChartBySource", tempHash);
				
		} catch (Exception e) {
			throw new HandlerExecutionException(e.getMessage()==null?"Problems locating/acessing feature model repository path":e.getMessage());
		}
	}

	private String[] extractURLs(String urls) {
//		return urls.split(";");
		return urls.split("\r\n");
	}
	
//	private List<Map<String,String>> listUserFeatureModels(String[] urlsList) {		
//		List<Map<String,String>> modelList = new LinkedList<Map<String,String>>();
//		for( String url : urlsList ) {
//			url.trim();
//			FeatureModel model = new XMLFeatureModel(url, XMLFeatureModel.USE_VARIABLE_NAME_AS_ID);
//			FeatureModelStatistics stats = new FeatureModelStatistics(model);
//			model.loadModel();
//			stats.update();
//			
//	        Map<String,String> modelData = new HashMap<String, String>();
//	        modelData.put("name", model.getName());
//	        modelData.put("features", String.valueOf(stats.countFeatures()));
//	        modelData.put("ecr", String.valueOf((int)(stats.getECRepresentativeness()*100)));
//	        if ( stats.getECRepresentativeness() == 0 ) {
//	        	modelData.put("clausedensity", "N/A");	
//	        }
//	        else {
//	        	DecimalFormat format = new DecimalFormat("0.0");
//	        	modelData.put("clausedensity", format.format(stats.getECClauseDensity()));
//	        }
//	        modelData.put("file", url);
//
//	        modelList.add(modelData);
//		}
//		return modelList;
//	}
	

	
}












