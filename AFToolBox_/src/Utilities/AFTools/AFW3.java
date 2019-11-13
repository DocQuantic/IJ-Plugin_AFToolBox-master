package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 8 nov. 2019 at 12:56:50 
 * 
 */
public class AFW3 extends AFWavelet{

	public AFW3(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {		
		double[][] array = castIntToDouble(ip.getProcessor().getIntArray());
		val = 0;
		
		computeCoefficients(array);
		int width = cHH[0].length;
		int height = cHH.length;
		
		double muHL = mean(cHL);
		double muHH = mean(cHH);
		double muLH = mean(cLH);
		
		for(int i=0; i<width;i++) {
			for(int j=0; j<height; j++) {
				val += Math.pow(Math.abs(cHL[i][j])-muHL, 2) + Math.pow(Math.abs(cLH[i][j])-muLH, 2) + Math.pow(Math.abs(cHH[i][j])-muHH, 2);
			}
		}
		
		val /= width*height;
	}
	
	private double mean(double[][] array) {
		int width = array[0].length;
		int height = array.length;
		double mean = 0;
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				mean += array[i][j];
			}
		}
		
		mean /= width*height;
		
		return mean;
	}

}
