package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 24 oct. 2019 at 12:36:40 
 * 
 */
public class AFEnergyLaplace extends AFTool{
	
	private int[] kernel = {-1, -4, -1, -4, 20, -4, -1, -4, -1};

	public AFEnergyLaplace(ImagePlus ip, ResultsTable rt, int threshold) {
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
