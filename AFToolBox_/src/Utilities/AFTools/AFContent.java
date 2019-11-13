package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 23 oct. 2019 at 15:40:58 
 * 
 */
public class AFContent extends AFTool{

	public AFContent(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {		
		int[][] array = ip.getProcessor().getIntArray();
		array = thresholdArray(array);
		ip.getProcessor().setIntArray(array);
		int[] histogram = ip.getProcessor().getHistogram();
		val = 0;
		
		for(int i=1; i<histogram.length; i++) {
			
			val += i*histogram[i];
		}
	}
	
	private int[][] thresholdArray(int[][] array){
		int[][] output = new int[height][width];
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(array[i][j]<threshold) {
					output[i][j]=0;
				}else {
					output[i][j] = array[i][j];
				}
			}
		}
		
		return output;
	}

}
