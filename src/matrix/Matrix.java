package matrix;

import helper.ExceptionHelper;

import java.util.ArrayList;
import java.util.List;

public class Matrix
{
	private List<List<Double>> data;
	private Dimension matrixDimension;
	
	public static Matrix getMatrixOfOnes(Dimension dimension)
	{
		Matrix result = new Matrix(dimension);
		
		for(int i = 1; i <= dimension.getRowCount(); i++)
		{
			for(int j = 1; j <= dimension.getColumnCount(); j++)
			{
				result.setEntry(new Position(i, j), 1);
			}	
		}
		
		return result;
	}
	
	public Matrix(List<List<Double>> data)
	{
		ExceptionHelper.isMatrixData(data, "data");
		
		this.data = data;
		this.matrixDimension = new Dimension(data.size(), data.get(0).size());
	}
	
	public Matrix(Dimension matrixDimension)
	{
		ExceptionHelper.isMatrixDimension(matrixDimension.getRowCount(), matrixDimension.getColumnCount());
		
		this.data = this.createZeroMatrix(matrixDimension.getRowCount(), matrixDimension.getColumnCount());
		this.matrixDimension = matrixDimension;
	}
	
	public double getEntry(Position position)
	{
		List<Double> row = this.data.get(position.getRowPosition() - 1);
		return row.get(position.getColumnPosition() - 1);
	}
	
	public Dimension getMatrixDimension()
	{
		return this.matrixDimension;
	}
	
	public List<Double> getRow(int rowCoordinate)
	{
		return this.data.get(rowCoordinate - 1);
	}
	
	public List<Double> getColumn(int columnCoordinate)
	{
		List<Double> column = new ArrayList<>();
		
		for(List<Double> row : this.data)
		{
			column.add(row.get(columnCoordinate - 1));
		}
		
		return column;
	}
	
	public void transpose()
	{
		List<List<Double>> columns = new ArrayList<>();
		for(int i = 1; i <= this.matrixDimension.getColumnCount(); i++)
		{
			columns.add(this.getColumn(i));		
		}
		this.data = columns;
		this.matrixDimension = new Dimension(this.matrixDimension.getColumnCount(), this.matrixDimension.getRowCount());
	}
	
	public Matrix getRowOrientatedVector()
	{
		Matrix result = null;
		
		if(this.matrixDimension.getRowCount() == 1)
		{
			result = this.getCopy();
		}
		else if(this.matrixDimension.getColumnCount() == 1)
		{
			result = this.getCopy();
			result.transpose();
		}
		
		return result;
	}
	
	public Matrix getColumnOrientatedVector()
	{
		Matrix result = null;
		
		if(this.matrixDimension.getColumnCount() == 1)
		{
			result = this.getCopy();
		}
		else if(this.matrixDimension.getRowCount() == 1)
		{
			result = this.getCopy();
			result.transpose();
		}
		
		return result;
	}
	
	public Matrix getCopy()
	{
		Matrix copy = new Matrix(this.matrixDimension);
		for(int i = 1; i <= copy.matrixDimension.getRowCount(); i++)
		{
			for(int j = 1; j <= copy.matrixDimension.getColumnCount(); j++)
			{
				double entry = this.getEntry(new Position(i, j));
				copy.setEntry(new Position(i, j), entry);
			}	
		}
		
		return copy;
	}
	
	public Matrix innerMultiply(Matrix factor)
	{
		ExceptionHelper.areMatrixDimensionsValidForInnerMultiplication(this, factor);
		
		Matrix result = new Matrix(this.getMatrixDimension());
		
		Matrix thisCopy = this.getCopy();
		Matrix factorCopy = factor.getCopy();
		
		for(int i = 1; i <= result.matrixDimension.getRowCount(); i++)
		{
			for(int j = 1; j <= result.matrixDimension.getColumnCount(); j++)
			{
				Position position = new Position(i, j);
				double entry = thisCopy.getEntry(position) * factorCopy.getEntry(position);
				result.setEntry(position, entry);
			}	
		}		
		
		return result;
	}
	
	public Matrix multiply(Matrix factor)
	{
		Matrix result;
		
		Matrix thisCopy = this.getCopy();
		Matrix factorCopy = factor.getCopy();
		
		if(this.isScalarMultiplication(thisCopy, factorCopy))
		{
			result = this.multiplyScalarWithMatrix(thisCopy, factorCopy);
		}
		else
		{
			ExceptionHelper.areMatrixDimensionsValidForMultiplication(this, factor);
			
	        List<List<Double>> newMatrixData = new ArrayList<>();	
			
			for(int i = 1; i <= thisCopy.matrixDimension.getRowCount(); i++)
			{
				List<Double> newMatrixRow = new ArrayList<>();
				
				for(int j = 1; j <= factorCopy.getMatrixDimension().getColumnCount(); j++)
				{
					List<Double> row = thisCopy.getRow(i);
					List<Double> column = factorCopy.getColumn(j);
					
					newMatrixRow.add(this.multiplyVectors(row, column));			
				}
				
				newMatrixData.add(newMatrixRow);
			}
			
			result =  new Matrix(newMatrixData);
		}
		
		return result;
	}
	
	private Matrix multiplyScalarWithMatrix(Matrix factor1, Matrix factor2)
	{
		double scalar;
		Matrix matrix;
		
		if(this.matrixDimension.getRowCount() == 1 && this.matrixDimension.getColumnCount() == 1)
		{
			scalar = factor1.getCopy().getEntry(new Position(1, 1));
			matrix = factor2.getCopy();
		}
		else
		{
			scalar = factor2.getCopy().getEntry(new Position(1, 1));
			matrix = factor1.getCopy();
		}
		
		for(int i = 1; i <= matrix.matrixDimension.getRowCount(); i++)
		{
			for(int j = 1; j <= matrix.matrixDimension.getColumnCount(); j++)
			{
				double entry = matrix.getEntry(new Position(i, j));
				matrix.setEntry(new Position(i, j), scalar * entry);
			}	
		}
		
		return matrix;
	}

	private boolean isScalarMultiplication(Matrix factor1, Matrix factor2)
	{
		boolean result;
		
		if((factor1.matrixDimension.getRowCount() == 1 && factor1.matrixDimension.getColumnCount() == 1)
				|| (factor2.matrixDimension.getRowCount() == 1 && factor2.matrixDimension.getColumnCount() == 1))
		{
			result = true;
		}
		else
		{
			result = false;
		}
		
		return result;
	}
	
	public Matrix plus(Matrix summand)
	{
		ExceptionHelper.areMatrixDimensionsValidForAddition(this.matrixDimension, summand.matrixDimension);
					
		Matrix result = new Matrix(this.matrixDimension);
		
		Matrix thisCopy = this.getCopy();
		Matrix summandCopy = summand.getCopy();
		
		for(int i = 1; i <= thisCopy.matrixDimension.getRowCount(); i++)
		{
			for(int j = 1; j <= thisCopy.matrixDimension.getColumnCount(); j++)
			{
				Position position = new Position(i, j);
				double entry = thisCopy.getEntry(position) + summandCopy.getEntry(position);
				result.setEntry(position, entry);
			}	
		}
		
		return result;
	}
	
	public Matrix minus(Matrix subtrahend)
	{
		ExceptionHelper.areMatrixDimensionsValidForAddition(this.matrixDimension, subtrahend.matrixDimension);
		
		Matrix result = new Matrix(this.matrixDimension);
		
		Matrix thisCopy = this.getCopy();
		Matrix subtrahendCopy = subtrahend.getCopy();
		
		for(int i = 1; i <= thisCopy.matrixDimension.getRowCount(); i++)
		{
			for(int j = 1; j <= thisCopy.matrixDimension.getColumnCount(); j++)
			{
				Position position = new Position(i, j);
				double entry = thisCopy.getEntry(position) - subtrahendCopy.getEntry(position);
				result.setEntry(position, entry);
			}	
		}
		
		return result;
	}

	public void setEntry(Position position, double value)
	{
		this.data.get(position.getRowPosition() - 1).set(position.getColumnPosition() - 1, value);
	}
	
	private double multiplyVectors(List<Double> vector1, List<Double> vector2)
	{
		double result = 0;
		for(int i = 0; i < vector1.size(); i++)
		{
			result += vector1.get(i) * vector2.get(i);
		}
		return result;
	}
	
	private List<List<Double>> createZeroMatrix(int m, int n)
	{
		List<List<Double>> matrix = new ArrayList<>();
		
		for(int i = 0; i < m; i++)
		{
			List<Double> row = new ArrayList<>();
			for(int j = 0; j < n; j++)
			{
				row.add((double) 0);
			}
			matrix.add(row);
		}
				
		return matrix;
	}
	

}
