package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 23 oct. 2019 at 16:02:42 
 * 
 */
public class AFAbsGradient extends AFTool{

	public AFAbsGradient(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		double currVal = 0;
		
		for(int i=0; i<array.length-1; i++) {
			for(int j=0; j<array[0].length; j++) {
				currVal = Math.abs(array[i+1][j]-array[i][j]);
				
				if(currVal>threshold) {
					val += currVal;
				}
			}
		}
	}
}
