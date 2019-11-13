package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.process.ImageStatistics;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 23 oct. 2019 at 16:43:14 
 * 
 */
public class AFVariance extends AFTool{
	
	private int surf = 0;

	public AFVariance(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
		surf = ip.getWidth() * ip.getHeight();
	}
	
	@Override
	protected void runMethod() {
		ImageStatistics is = ip.getProcessor().getStatistics();
		double mu = is.mean;
		int[][] array = ip.getProcessor().getIntArray();
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				val += Math.pow(array[i][j]-mu, 2);
			}
		}
		val = val/surf;
	}

}
