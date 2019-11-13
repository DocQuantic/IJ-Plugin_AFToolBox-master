package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFContent v1, 23 oct. 2019 
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
public class AFContent extends AFTool{

	public AFContent(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {		
		int[][] array = ip.getProcessor().getIntArray();
		array = thresholdArray(array);
		ip.getProcessor().setIntArray(array);
		int[] histogram = ip.getProcessor().getHistogram();
		val = 0;
		
		for(int i=1; i<histogram.length; i++) {
			
			val += i*histogram[i];
		}
	}
	
	private int[][] thresholdArray(int[][] array){
		int[][] output = new int[height][width];
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				if(array[i][j]<threshold) {
					output[i][j]=0;
				}else {
					output[i][j] = array[i][j];
				}
			}
		}
		
		return output;
	}

}
