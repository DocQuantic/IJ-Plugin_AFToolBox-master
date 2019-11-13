package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 24 oct. 2019 at 09:50:19 
 * 
 */
public class AFAutoCorr extends AFTool{

	public AFAutoCorr(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		
		
		for(int i=0; i<array.length-2; i++) {
			for(int j=0; j<array[0].length; j++) {
				val += array[i][j]*array[i+1][j]-array[i][j]*array[i+2][j];
			}
		}
	}

}
