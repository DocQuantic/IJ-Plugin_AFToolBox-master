package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFAbsGradient v1, 23 oct. 2019 
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
public class AFAbsGradient extends AFTool{

	public AFAbsGradient(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		double currVal = 0;
		
		for(int i=0; i<array.length-1; i++) {
			for(int j=0; j<array[0].length; j++) {
				currVal = Math.abs(array[i+1][j]-array[i][j]);
				
				if(currVal>threshold) {
					val += currVal;
				}
			}
		}
	}
}
