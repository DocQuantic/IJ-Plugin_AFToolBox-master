package Utilities.AFTools;

import ij.ImagePlus;
import ij.measure.ResultsTable;

/**
*
*  AFWavelet v1, 24 oct. 2019 
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
public class AFWavelet extends AFTool{

	protected double[][] cH = null;
	protected double[][] cL = null;
	protected double[][] cHH = null;
	protected double[][] cHL = null;
	protected double[][] cLH = null;
	private double[] DecomLoDB6 = {-0.00107730108499558,
			0.004777257511010651,
			0.0005538422009938016,
			-0.031582039318031156,
			0.02752286553001629,
			0.09750160558707936,
			-0.12976686756709563,
			-0.22626469396516913,
			0.3152503517092432,
			0.7511339080215775,
			0.4946238903983854,
			0.11154074335008017};
	private double[] DecomHiDB6 = new double[12];

	public AFWavelet(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
		buildOrthonormalSpace();
	}
	
	protected void computeCoefficients(double[][] array) {
		cH = waveletDecom(array, "Hi");
		cL = waveletDecom(array, "Lo");

		cHH = waveletDecom(rotateArrayCW(cH), "Hi");
		cHH = rotateArrayCCW(cHH);
		cHL = waveletDecom(rotateArrayCW(cH), "Lo");
		cHL = rotateArrayCCW(cHL);
		cLH = waveletDecom(rotateArrayCW(cL), "Hi");
		cLH = rotateArrayCCW(cLH);
	}

	private double[][] waveletDecom(double[][] array, String HiLo){
		double[] wavelet = new double[DecomHiDB6.length];
		int wavelength = wavelet.length;
		int width = array[0].length;
		int height = array.length;
		
		double[][] output = new double[height][(int) Math.floor((width+wavelength-1)/2)];
		
		if(HiLo=="Hi") {
			wavelet = DecomHiDB6;
		}else if(HiLo=="Lo") {
			wavelet = DecomLoDB6;
		}
		
		int outputIdx = 0;
		for(int i=0; i<height; i++) {
			outputIdx = 0;
			double[] lineConv = conv(array[i], wavelet);
			for(int j=0; j<lineConv.length-1; j++) {
				if(j%2==0) {
					output[i][outputIdx] = lineConv[j];
					outputIdx++;
				}
			}
		}
		
		return output;
	}
	
	private double[] conv(double[] A, double[] B) {
		int m = A.length;
		int n = B.length;
		
		double[] w = new double[m+n-1];
		
		for(int k=0; k<w.length; k++) {
			double w0 = 0;
			for(int j=Math.max(0, k+1-n); j<=Math.min(k,  m-1); j++) {
				w0 += A[j]*B[k-j];
			}
			w[k]=w0;
		}
		
		return w;
	}
	
	private void buildOrthonormalSpace() {
		int wavelength = DecomLoDB6.length;
		for(int i=0; i<wavelength; i++) {
			if(i%2==0) {
				DecomHiDB6[i] = -DecomLoDB6[(wavelength-1)-i];
			}else {
				DecomHiDB6[i] = DecomLoDB6[(wavelength-1)-i];
			}
		}
	}
	
	private double[][] rotateArrayCW(double[][] array){
		int width = array[0].length;
		int height = array.length;
		double[][] rotArray = new double[width][height];
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				rotArray[j][i] = array[height-1-i][j];
			}
		}
		
		return rotArray;
	}
	
	private double[][] rotateArrayCCW(double[][] array){
		int width = array[0].length;
		int height = array.length;
		double[][] rotArray = new double[width][height];
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				rotArray[j][i] = array[i][width-1-j];
			}
		}
		
		return rotArray;
	}
}
