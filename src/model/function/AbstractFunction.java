package model.function;

import matrix.Matrix;
import matrix.Position;

public abstract class AbstractFunction implements Function
{
	@Override
	public Matrix get(Matrix matrix)
	{
		Matrix result = matrix.getCopy();
		
		for(int i = 1; i <= result.getMatrixDimension().getRowCount(); i++)
		{
			for(int j = 1; j <= result.getMatrixDimension().getColumnCount(); j++)
			{
				Position position = new Position(i, j);
				double entry = result.getEntry(position);			
				result.setEntry(position, this.get(entry));
			}
		}
		
		return result;
	}
	
	@Override
	public Matrix getDerivative(Matrix matrix)
	{
		Matrix result = matrix.getCopy();
		
		for(int i = 1; i <= result.getMatrixDimension().getRowCount(); i++)
		{
			for(int j = 1; j <= result.getMatrixDimension().getColumnCount(); j++)
			{
				Position position = new Position(i, j);
				double entry = result.getEntry(position);			
				result.setEntry(position, this.getDerivative(entry));
			}
		}
		
		return result;
	}

}
