package helper;

import java.util.List;

import dto.TrainingSample;

import matrix.Dimension;
import matrix.Matrix;
import model.layer.impl.HiddenLayer;


public class ExceptionHelper
{
	public static void isMatrixData(List<List<Double>> data, String description)
	{		
		if(data == null || data.isEmpty())
		{
			throw new IllegalStateException(description + " null or empty");
		}
		
		for(List<Double> row : data)
		{
			if (row == null || row.isEmpty())
			{
				throw new IllegalStateException("At least one " + description + " element is null or empty");
			}		
		}
		
		for(List<Double> row : data)
		{
			if(row.size() != data.get(0).size())
			{
				throw new IllegalStateException("The row sizes of " + description + " differ");
			}
		}		
	}
	
	public static void isMatrixDimension(int m, int n)
	{
		if(m < 1 || n < 1)
		{
			throw new IllegalStateException("The (" + m + " x " + n + ") tupel is not a matrix dimension");
		}
	}
	
	public static void areMatrixDimensionsValidForMultiplication(Matrix factor1, Matrix factor2)
	{
		if(factor1.getMatrixDimension().getColumnCount() != factor2.getMatrixDimension().getRowCount())
		{
			throw new IllegalStateException("Matrix dimensions are not valid for multiplication");
		}
	}
	
	public static void areMatrixDimensionsValidForInnerMultiplication(Matrix factor1, Matrix factor2)
	{	
		if((factor1.getMatrixDimension().getColumnCount() != factor2.getMatrixDimension().getColumnCount())
				|| (factor1.getMatrixDimension().getRowCount() != factor2.getMatrixDimension().getRowCount()))
		{
			throw new IllegalStateException("Matrix dimensions are not valid for inner multiplication");
		}
		
		isNotAVector(factor1);
	}
	
	public static void isNotAVector(Matrix vector)
	{
		if(vector.getMatrixDimension().getColumnCount() != 1 && vector.getMatrixDimension().getRowCount() != 1)
		{
			throw new IllegalStateException("Matrix is not a vector");
		}
		
	}
	
	public static void isNotPositive(int i, String description)
	{
		if(i < 1)
		{
			throw new IllegalStateException("The " + description + " is not positive");
		}		
	}
	
	public static void isRate(double error, String description)
	{
		if(!(0 < error && error < 1))
		{
			throw new IllegalStateException(description + " is not a rate");
		}
	}
	
	public static <T> void isNullOrEmpty(List<T> list, String description)
	{
		if(list == null || list.isEmpty())
		{
			throw new IllegalStateException("The " + description + " is null or empty");
		}
	}
	
	public static <T> void isNull(T object, String description)
	{
		if(object == null)
		{
			throw new IllegalStateException("The " + description + " is null");
		}
	}
	
	public static void areMatrixDimensionsValidForAddition(Dimension dimension1, Dimension dimension2)
	{
		if(dimension1.getRowCount() != dimension1.getRowCount() || dimension1.getColumnCount() != dimension1.getColumnCount())
		{
			throw new IllegalStateException("Matrix dimensions are not equal");
		}
	}
	
	public static void haveDifferentDimensions(List<TrainingSample> trainingSamples)
	{
		int inputDimension = trainingSamples.get(0).getInputDimension();
		int outputDebitDimension = trainingSamples.get(0).getOutputDebitDimension();
		
		for(TrainingSample sample : trainingSamples)
		{
			if(sample.getInputDimension() != inputDimension || sample.getOutputDebitDimension() != outputDebitDimension)
			{
				throw new IllegalStateException("Training sample dimensions are not equal");
			}	
		}
	}

}
