package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFEnergyLaplace v1, 24 oct. 2019 
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
 * This class inherits from AFTool class and implements energy Laplace algorithm.
 * @author William Magrini @ Bordeaux Imaging Center
 * 
 */
public class AFEnergyLaplace extends AFTool{
	
	/**Stores the filter kernel**/
	private int[] kernel = {-1, -4, -1, -4, 20, -4, -1, -4, -1};

	/**
	 * Creates a new AFAutoCorr.
	 * @param ip the input ImagePlus containing the stack to analyze.
	 * @param rt the input ResultsTable to fill with results.
	 * @param threshold a threshold value that can be used for calculation (int).
	 */
	public AFEnergyLaplace(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	/**
	 * Computes the focus value by convolving with the kernel {{-1, -4, -1}, {-4, 20, -4}, {-1, -4, -1}} to compute the second derivative. The final output is the sum of the squares of the convolution results.
	 */
	@Override
	protected void runMethod() {
		int[][] arrayInit = ip.getProcessor().getIntArray();
		ip.getProcessor().convolve3x3(kernel);
		int[][] arrayConv = ip.getProcessor().getIntArray();
		val = 0;
		
		for(int i=0; i<arrayConv.length; i++) {
			for(int j=0; j<arrayConv[0].length; j++) {
				val += arrayConv[i][j];
			}
		}

		ip.getProcessor().setIntArray(arrayInit);
	}
}
