package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFEntropy v1, 24 oct. 2019 
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
 * This class inherits from AFTool class and implements entropy algorithm.
 * @author William Magrini @ Bordeaux Imaging Center
 * 
 */
public class AFEntropy extends AFTool{

	/**
	 * Creates a new AFEntropy.
	 * @param ip the input ImagePlus containing the stack to analyze.
	 * @param rt the input ResultsTable to fill with results.
	 * @param threshold a threshold value that can be used for calculation (int).
	 */
	public AFEntropy(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	/**
	 * Computes the focus value by calculating the information contained in an image.
	 */
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
	
	/**
	 * Computes the base 2 logarithm of a value.
	 * @param num is the input number (double).
	 * @return the base 2 logarithm of the input.
	 */
	private double log2(double num) {
		num = Math.log(num)/Math.log(2.0);
		return num;
	}
}

