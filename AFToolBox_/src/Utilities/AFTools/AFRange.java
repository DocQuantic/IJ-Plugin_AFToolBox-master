package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.process.ImageStatistics;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 21 oct. 2019 at 12:40:12 
 * 
 */
public class AFRange extends AFTool {

	public AFRange(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}

	@Override
	public void runMethod() {
		ImageStatistics is = ip.getStatistics();
		
		double max = is.max;
		double min = is.min;
		
		val = max-min;
	}
}
