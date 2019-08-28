package model.layer.impl;

import matrix.Dimension;
import matrix.Matrix;
import matrix.Position;
import model.function.AbstractFunction;
import model.function.impl.Sigmoid;
import model.layer.AbstractLayer;

public class HiddenLayer extends AbstractLayer
{
	public HiddenLayer(int nodeCount)
	{
		super(nodeCount, new Sigmoid());
	}
	
	public HiddenLayer(int nodeCount, AbstractFunction activationFunction)
	{
		super(nodeCount, activationFunction);
	}
	
	@Override
	public void updateOutputError(Matrix outputErrorOfNextLayer)
	{
		Matrix W = this.weightMatrix.getCopy();
		Dimension dimension = W.getMatrixDimension();
		Matrix vectorOfOnes = Matrix.getMatrixOfOnes(new Dimension(dimension.getColumnCount(), 1));
		Matrix W_rowSum = W.multiply(vectorOfOnes);
		
		Matrix E = outputErrorOfNextLayer.getColumnOrientatedVector();		
		Matrix W_rate = new Matrix(dimension);
		
		for(int i = 1; i <= dimension.getRowCount(); i++)
		{
			for(int j = 1; j <= dimension.getColumnCount(); j++)
			{
				Position position = new Position(i, j);
				W_rate.setEntry(position, W.getEntry(position) / W_rowSum.getEntry(new Position(i, 1)));
			}	
		}
		
		W_rate.transpose();
		this.nodesOutputError = W_rate.multiply(E);
	}
	
}
