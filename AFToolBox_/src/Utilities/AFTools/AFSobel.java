package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 24 oct. 2019 at 10:31:23 
 * 
 */
public class AFSobel extends AFTool{
	
	private ImagePlus ipTmp = null;

	public AFSobel(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
		ipTmp = ip.duplicate();
	}
	
	@Override
	protected void runMethod() {
		ip.getProcessor().findEdges();
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				val += array[i][j];
			}
		}

		ipTmp.setSlice(ip.getSlice());
		ipTmp.copy();
		ip.paste();
	}

}
