package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFTool v1, 21 oct. 2019 
   William Magrini, w.magrini at yahoo.fr
   
   Copyright (C) 2019 William Magrini
 
   License:
   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version.
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
*/

/**
 * @author William Magrini @ Bordeaux Imaging Center
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
