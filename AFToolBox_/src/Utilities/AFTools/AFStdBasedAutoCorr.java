package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFStdBasedAutoCorr v1, 24 oct. 2019 
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
public class AFStdBasedAutoCorr extends AFTool {
	
	private int surf;

	public AFStdBasedAutoCorr(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
		surf = ip.getWidth()*ip.getHeight();
	}
	
	@Override
	protected void runMethod() {
		int[][] array = ip.getProcessor().getIntArray();
		val = 0;
		double mu = ip.getProcessor().getStatistics().mean;
		
		for(int i=0; i<array.length-1; i++) {
			for(int j=0; j<array[0].length; j++) {
				val += array[i][j]*array[i+1][j];
			}
		}
		
		val -= surf*Math.pow(mu, 2);
	}

}
