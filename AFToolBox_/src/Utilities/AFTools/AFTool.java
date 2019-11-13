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
 * This class is a mother class implementing the basic methods to find the best focus value of a Z-Stack.
 * The focus value calculation is performed in a protected method. Each sub-class will overwrite this method to implement a calculation method based on :
 * Sun, Duthaler, Nelson - 2004 - "Autofocusing in computer microscopy selecting the optimal focus algorithm".
 * @author William Magrini @ Bordeaux Imaging Center
 */
public class AFTool {
	
	/**Stores the focus values for each slice**/
	public double[] vals;
	/**Stores the min focus value**/
	public double Min = 0;
	/**Stores the max focus value**/
	public double Max = 0;
	/**Stores the index of the slice with best focus value (max)**/
	public int idxMax = 0;
	
	/**Stores the original ImagePlus**/
	protected ImagePlus ip = null;
	/**Stores the ResultsTable**/
	protected ResultsTable rt = null;
	/**Stores the width of the ImagePlus**/
	protected int width = 0;
	/**Stores the height of the ImagePlus**/
	protected int height = 0;
	/**Stores the surface of the image**/
	protected int surf = 0;
	/**Stores the number of slices in the ImagePlus**/
	protected int NSlices = 0;
	/**Stores the threshold value selected by the user**/
	protected int threshold = 0;
	/**Stores the focus value of the current slice**/
	protected double val = 0;
	
	/**
	 * Creates a new AFTool.
	 * @param ip the input ImagePlus containing the stack to analyze.
	 * @param rt the input ResultsTable to fill with results.
	 * @param threshold a threshold value that can be used for calculation (int).
	 */
	public AFTool(ImagePlus ip, ResultsTable rt, int threshold) {
		this.ip = ip;
		this.rt = rt;
		this.threshold = threshold;
		this.width = ip.getWidth();
		this.height = ip.getHeight();
		this.NSlices = ip.getNSlices();
		this.vals = new double[this.NSlices];
		this.surf = this.width*this.height;
	}

	/**
	 * Browses the ImagePlus, apply a method to calculate the focus value, find the min and max values and updates the ResultsTable.
	 */
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
	
	/**
	 * Compares the input value to the stored min and max values.
	 * @param val is the input value to compare (double).
	 */
	private void getMinMax(double val) {
		Max = Math.max(Max, val);
		Min = Math.min(Min, val);
	}
	
	/**
	 * Fills the ResultsTable with slice index and focus value.
	 * @param idx is the slice index (int).
	 * @param val is the focus value (double).
	 */
	private void updateResults(int idx, double val) {
		rt.addValue("Slice", idx);
		rt.addValue("Focus", val);
	}
	
	/**
	 * Casts a 2d double array to a 2d int array.
	 * @param array is the int array to cast (int[][]).
	 * @return a double array.
	 */
	protected double[][] castIntToDouble(int[][] array){
		double[][] arrayD = new double[array.length][array[0].length];
		
		for(int i=0; i<arrayD.length; i++) {
			for(int j=0; j<arrayD[0].length; j++) {
				arrayD[i][j] = array[i][j];
			}
		}
		
		return arrayD;
	}
	
	/**
	 * Computes the focus value of the current slice.
	 */
	protected void runMethod() {}
}
