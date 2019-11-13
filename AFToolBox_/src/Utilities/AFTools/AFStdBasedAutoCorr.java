package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 24 oct. 2019 at 09:59:57 
 * 
 */
public class AFStdBasedAutoCorr extends AFTool {
	
	private int surf;

	public AFStdBasedAutoCorr(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
		surf = ip.getWidth()*ip.getHeight();
	}
	
	@Override
	protected void runMethod() {
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		double mu = ip.getProcessor().getStatistics().mean;
		
		for(int i=0; i<array.length-1; i++) {
			for(int j=0; j<array[0].length; j++) {
				val += array[i][j]*array[i+1][j];
			}
		}
		
		val -= surf*Math.pow(mu, 2);
	}

}
