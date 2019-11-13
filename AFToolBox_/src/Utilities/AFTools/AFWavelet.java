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
 * This class inherits from AFTool class and implements a wavelet-based algorithms for focus value computation.
 * This algorithm uses the Daubechies D6 wavelet filter, applying both high pass and low pass filtering to an image. The resultant image is divided into four subimages: LL, HL, LH and HH.
 * It performs 2D Fast Wavelet Transform (FWT) algorithm with Daubechies 6 wavelets: https://www.mathworks.com/help/wavelet/ug/fast-wavelet-transform-fwt-algorithm.html
 * @author William Magrini @ Bordeaux Imaging Center
 */
public class AFWavelet extends AFTool{

	/**Stores the first decomposition step along rows with high pass filter**/
	protected double[][] cH = null;
	/**Stores the first decomposition step along rows with low pass filter**/
	protected double[][] cL = null;
	/**Stores the coefficients values for row and column high pass filter**/
	protected double[][] cHH = null;
	/**Stores the coefficients values for row high pass and column low pass filter**/
	protected double[][] cHL = null;
	/**Stores the coefficients values for row low pass and column high pass filter**/
	protected double[][] cLH = null;
	/**Stores the Daubechies 6 wavelet low pass decomposition coefficients**/
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
	/**Stores the Daubechies 6 wavelet high pass decomposition coefficients**/
	private double[] DecomHiDB6 = new double[12];

	/**
	 * Creates a new AFWavelet and builds the DecomHiDB6 vector.
	 * @param ip the input ImagePlus containing the stack to analyze.
	 * @param rt the input ResultsTable to fill with results.
	 * @param threshold a threshold value that can be used for calculation (int).
	 */
	public AFWavelet(ImagePlus ip, ResultsTable rt, int threshold) {
		super(ip, rt, threshold);
		buildOrthonormalSpace();
	}
	
	/**
	 * Computes all the coefficients values that will be needed for focus calculation.
	 * @param array is the input image array (double[][])
	 */
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

	/**
	 * Performs a 1D FWT on an image.
	 * @param array is the input image to filter (double[][]).
	 * @param HiLo "Hi"=High pass filter; "Lo"=Low pass filter (String).
	 * @return the result of the filtering process.
	 */
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
	
	/**
	 * Builds the decomposition high pass wavelet coefficients from the decomposition low pass one.
	 */
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
	
	/**
	 * Performs a 1D convolution.
	 * @param A is the first vector to convolve (double[]).
	 * @param B is the second vector to convolve (double[]).
	 * @return the result of the convolution in the form of a vector.
	 */
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
	
	/**
	 * Rotates an array clockwise.
	 * @param array is the array to rotate (double[][]).
	 * @return the rotated array.
	 */
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
	
	/**
	 * Rotates an array counter-clockwise.
	 * @param array is the array to rotate (double[][]).
	 * @return the rotated array.
	 */
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
