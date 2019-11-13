package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 23 oct. 2019 at 15:51:24 
 * 
 */
public class AFPower extends AFTool{

	public AFPower(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {	
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				if(array[i][j]>=threshold) {
					val += Math.pow(array[i][j], 2);
				}
			}
		}
	}
}
