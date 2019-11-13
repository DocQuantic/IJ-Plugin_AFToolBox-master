package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 23 oct. 2019 at 16:30:49 
 * 
 */
public class AFBrennerGradient extends AFTool{

	public AFBrennerGradient(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		double currVal = 0;
		
		for(int i=0; i<array.length-2; i++) {
			for(int j=0; j<array[0].length; j++) {
				currVal = Math.pow(array[i+2][j]-array[i][j], 2);
				if(currVal>=threshold) {
					val += currVal;
				}
			}
		}
	}

}
