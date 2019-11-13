package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 8 nov. 2019 at 12:36:39 
 * 
 */
public class AFW1 extends AFWavelet{

	public AFW1(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {		
		double[][] array = castIntToDouble(ip.getProcessor().getIntArray());
		val = 0;
		
		computeCoefficients(array);
		int width = cHH[0].length;
		int height = cHH.length;
		
		for(int i=0; i<width;i++) {
			for(int j=0; j<height; j++) {
				val += Math.abs(cHL[i][j])+Math.abs(cLH[i][j])+Math.abs(cHH[i][j]);
			}
		}
	}

}
