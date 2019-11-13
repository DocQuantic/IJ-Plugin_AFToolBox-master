package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.process.ImageStatistics;

/**
*
*  AFVariance v1, 23 oct. 2019 
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
 * This class inherits from AFTool class and implements variance algorithm.
 * @author William Magrini @ Bordeaux Imaging Center
 * 
 */
public class AFVariance extends AFTool{
	
	/**
	 * Creates a new AFVariance.
	 * @param ip the input ImagePlus containing the stack to analyze.
	 * @param rt the input ResultsTable to fill with results.
	 * @param threshold a threshold value that can be used for calculation (int).
	 */
	public AFVariance(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	/**
	 * Computes the focus value by summing the squarred variance of each pixel.
	 */
	@Override
	protected void runMethod() {
		ImageStatistics is = ip.getProcessor().getStatistics();
		double mu = is.mean;
		int[][] array = ip.getProcessor().getIntArray();
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				val += Math.pow(array[i][j]-mu, 2);
			}
		}
		val = val/surf;
	}
}
