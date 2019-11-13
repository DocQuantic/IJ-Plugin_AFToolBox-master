package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 8 nov. 2019 at 18:05:43 
 * 
 */
public class AFCount extends AFTool{

	public AFCount(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {	
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				if(array[i][j]<=threshold) {
					val ++;
				}
			}
		}
	}

}
