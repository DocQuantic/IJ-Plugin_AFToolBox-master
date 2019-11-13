import Utilities.AFTools.*;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.gui.Plot;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;

/**
*
*  AF_ToolBox v1, 21 oct. 2019 
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
public class AF_Toolbox implements PlugIn{
	
	
	private ResultsTable rt = new ResultsTable();
	private ImagePlus ip = null;
	private double min = 0;
	private double max = 0;
	private int bestSlice = 1;
	private String[] methods = {
			"Absolute Gradient",
			"Squarred Gradient",
			"Brenner Gradient",
			"Sobel",
			"Laplace",
			"Energy Laplace",
			"Wavelet W1",
			"Wavelet W2",
			"Wavelet W3",
			"Variance",
			"Normalized Variance",
			"Auto Correlation",
			"Std Based Auto Correlation",
			"Range",
			"Entropy",
			"Content",
			"Pixel Count",
			"Power"};

	@Override
	public void run(String arg) {
		ip = WindowManager.getCurrentImage();
		if(ip==null) {
			IJ.error("Error", "No opened image.");
		}else {
			ip.setSlice(1);
			displayGUI();
		}
	}
	
	public void runAF(String mode, int threshold) {
		AFTool tool = selectMode(mode, threshold);
		tool.run();
		analyzeResults(tool);
		showResults(tool.vals);
	}
	
	private void analyzeResults(AFTool tool) {
		min = tool.Min;
		max = tool.Max;
		bestSlice = tool.idxMax+1;
	}
	
	private void showResults(double[] results) {
		Plot plot = new Plot("Focus", "Slice", "Value");
		double[] slice = new double[ip.getNSlices()];
		
		for(int i=0; i<slice.length; i++) {
			slice[i] = i;
		}
		
		plot.setLimits(0,  slice.length-1, min, max);
		plot.addPoints(slice,  results, Plot.LINE);
		plot.draw();
		plot.getImagePlus().show();
		
		rt.show("Results");
		
		ip.setSlice(bestSlice);
	}
	
	private void displayGUI() {
		GenericDialog gd = new GenericDialog("Focus Toolbox");
		gd.addChoice("Focus_mode", methods, methods[0]);
		gd.addNumericField("Threshold", 0.0, 0);
		
		gd.showDialog();
		
		if(gd.wasOKed()) {
			String mode = gd.getNextChoice();
			int threshold = (int) gd.getNextNumber();
			runAF(mode, threshold);
		}
	}
	
	private AFTool selectMode(String mode, int threshold) {
		AFTool tool = null;
		
		switch(mode){
		case "Range":
			tool = new AFRange(ip, rt, threshold);
			break;
		case "Content":
			tool = new AFContent(ip, rt, threshold);
			break;
		case "Pixel Count":
			tool = new AFCount(ip, rt, threshold);
			break;
		case "Power":
			tool = new AFPower(ip, rt, threshold);
			break;
		case "Absolute Gradient":
			tool = new AFAbsGradient(ip, rt, threshold);
			break;
		case "Squarred Gradient":
			tool = new AFSqrGradient(ip, rt, threshold);
			break;
		case "Brenner Gradient":
			tool = new AFBrennerGradient(ip, rt, threshold);
			break;
		case "Variance":
			tool = new AFVariance(ip, rt, threshold);
			break;
		case "Normalized Variance":
			tool = new AFNormVariance(ip, rt, threshold);
			break;
		case "Auto Correlation":
			tool = new AFAutoCorr(ip, rt, threshold);
			break;
		case "Std Based Auto Correlation":
			tool = new AFStdBasedAutoCorr(ip, rt, threshold);
			break;
		case "Entropy":
			tool = new AFEntropy(ip, rt, threshold);
			break;
		case "Sobel":
			tool = new AFSobel(ip, rt, threshold);
			break;
		case "Laplace":
			tool = new AFLaplace(ip, rt, threshold);
			break;
		case "Energy Laplace":
			tool = new AFEnergyLaplace(ip, rt, threshold);
			break;
		case "Wavelet W1":
			tool = new AFW1(ip, rt, threshold);
			break;
		case "Wavelet W2":
			tool = new AFW2(ip, rt, threshold);
			break;
		case "Wavelet W3":
			tool = new AFW3(ip, rt, threshold);
			break;
		}
		
		return tool;
	}
}
