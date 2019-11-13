package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
 * 
 */

/**
 * @author William Magrini @ Bordeaux Imaging Center
 * Created 21 oct. 2019 at 12:04:33 
 * 
 */
public class AFTool {
	
	public double[] vals;
	public double Min = 0;
	public double Max = 0;
	public int idxMax = 0;
	
	protected ImagePlus ip = null;
	protected ResultsTable rt = null;
	protected int width = 0;
	protected int height = 0;
	protected int NSlices = 0;
	protected int threshold = 0;
	protected double val = 0;
	
	
	
	public AFTool(ImagePlus ip, ResultsTable rt, int threshold) {
		this.ip = ip;
		this.rt = rt;
		this.threshold = threshold;
		this.width = ip.getWidth();
		this.height = ip.getHeight();
		this.NSlices = ip.getNSlices();
		this.vals = new double[this.NSlices];
	}

	public void run() {
		for(int i=0; i<NSlices; i++) {
			rt.incrementCounter();
			ip.setSlice(i+1);
			
			runMethod();
			vals[i] = val;
			
			if(i==0) {
				Min = val;
				Max = val;
			}else {
				getMinMax(val);
				
				if(val==Max) {
					idxMax = i;
				}
			}
			
			updateResults(i, val);
		}
	}
	
	private void getMinMax(double val) {
		Max = Math.max(Max, val);
		Min = Math.min(Min, val);
	}
	
	private void updateResults(int idx, double val) {
		rt.addValue("Slice", idx);
		rt.addValue("Focus", val);
	}
	
	protected double[][] castIntToDouble(int[][] array){
		double[][] arrayD = new double[array.length][array[0].length];
		
		for(int i=0; i<arrayD.length; i++) {
			for(int j=0; j<arrayD[0].length; j++) {
				arrayD[i][j] = array[i][j];
			}
		}
		
		return arrayD;
	}
	
	protected void runMethod() {}
}
