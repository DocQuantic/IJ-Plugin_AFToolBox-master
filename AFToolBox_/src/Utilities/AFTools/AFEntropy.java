package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 24 oct. 2019 at 10:12:47 
 * 
 */
public class AFEntropy extends AFTool{
	
	private double surf;

	public AFEntropy(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
		surf=width*height;
	}
	
	@Override
	protected void runMethod() {
		int[] histogram = ip.getProcessor().getHistogram();
		val = 0;
		
		for(int i=0; i<histogram.length; i++) {
			if(histogram[i]!=0) {
				double p = histogram[i]/surf;
				
				val += p*log2(p);
			}
		}
	}
	
	private double log2(double num) {
		num = Math.log(num)/Math.log(2.0);
		return num;
	}

}

