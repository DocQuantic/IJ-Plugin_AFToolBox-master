package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFW2 v1, 8 nov. 2019 
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
 * This class inherits from AFWavelet class and implements wavelet algorithm W2.
 * @author William Magrini @ Bordeaux Imaging Center
 * 
 */
public class AFW2 extends AFWavelet{

	/**
	 * Creates a new AFW2.
	 * @param ip the input ImagePlus containing the stack to analyze.
	 * @param rt the input ResultsTable to fill with results.
	 * @param threshold a threshold value that can be used for calculation (int).
	 */
	public AFW2(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	/**
	 * Computes the focus value by summing the variances in the HL, LH and HH regions.
	 * The mean values in each region are computed from absolute values.
	 */
	@Override
	protected void runMethod() {		
		double[][] array = castIntToDouble(ip.getProcessor().getIntArray());
		val = 0;
		
		computeCoefficients(array);
		int width = cHH[0].length;
		int height = cHH.length;
		
		double muHL = meanAbs(cHL);
		double muHH = meanAbs(cHH);
		double muLH = meanAbs(cLH);
		
		for(int i=0; i<height;i++) {
			for(int j=0; j<width; j++) {
				val += Math.pow(Math.abs(cHL[i][j])-muHL, 2) + Math.pow(Math.abs(cLH[i][j])-muLH, 2) + Math.pow(Math.abs(cHH[i][j])-muHH, 2);
			}
		}
		
		val /= width*height;
	}
	
	/**
	 * Computes the average value of the absolute value of an array.
	 * @param array is the input (double[][])
	 * @return mean(abs(array)).
	 */
	private double meanAbs(double[][] array) {
		int width = array[0].length;
		int height = array.length;
		double mean = 0;
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				mean += Math.abs(array[i][j]);
			}
		}
		
		mean /= width*height;
		
		return mean;
	}
}
