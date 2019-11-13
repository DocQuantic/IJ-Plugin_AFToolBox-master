package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.process.ImageStatistics;

/**
*
*  AFRange v1, 21 oct. 2019 
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
 * This class inherits from AFTool class and implements range algorithm.
 * @author William Magrini @ Bordeaux Imaging Center
 * 
 */
public class AFRange extends AFTool {

	/**
	 * Creates a new AFRange.
	 * @param ip the input ImagePlus containing the stack to analyze.
	 * @param rt the input ResultsTable to fill with results.
	 * @param threshold a threshold value that can be used for calculation (int).
	 */
	public AFRange(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}

	/**
	 * Computes the focus value by finding the the difference between highest and lowest pixel values.
	 */
	@Override
	public void runMethod() {
		ImageStatistics is = ip.getStatistics();
		
		double max = is.max;
		double min = is.min;
		
		val = max-min;
	}
}
