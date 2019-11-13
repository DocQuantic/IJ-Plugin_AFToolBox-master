package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFW3 v1, 8 nov. 2019 
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
public class AFW3 extends AFWavelet{

	public AFW3(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
	}
	
	@Override
	protected void runMethod() {		
		double[][] array = castIntToDouble(ip.getProcessor().getIntArray());
		val = 0;
		
		computeCoefficients(array);
		int width = cHH[0].length;
		int height = cHH.length;
		
		double muHL = mean(cHL);
		double muHH = mean(cHH);
		double muLH = mean(cLH);
		
		for(int i=0; i<width;i++) {
			for(int j=0; j<height; j++) {
				val += Math.pow(Math.abs(cHL[i][j])-muHL, 2) + Math.pow(Math.abs(cLH[i][j])-muLH, 2) + Math.pow(Math.abs(cHH[i][j])-muHH, 2);
			}
		}
		
		val /= width*height;
	}
	
	private double mean(double[][] array) {
		int width = array[0].length;
		int height = array.length;
		double mean = 0;
		
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				mean += array[i][j];
			}
		}
		
		mean /= width*height;
		
		return mean;
	}

}
