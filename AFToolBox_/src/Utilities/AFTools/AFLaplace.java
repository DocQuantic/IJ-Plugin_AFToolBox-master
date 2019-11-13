package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 24 oct. 2019 at 12:20:19 
 * 
 */
public class AFLaplace extends AFTool{
	
	private int[] kernel = {0, 1, 0, 1, -4, 1, 0, 1, 0};

	public AFLaplace(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {
		int[][] arrayInit = ip.getProcessor().getIntArray();
		ip.getProcessor().convolve3x3(kernel);
		int[][] arrayConv = ip.getProcessor().getIntArray();
		val = 0;
		
		for(int i=0; i<arrayConv.length; i++) {
			for(int j=0; j<arrayConv[0].length; j++) {
				val += arrayConv[i][j];
			}
		}
		
		ip.getProcessor().setIntArray(arrayInit);
	}

}
