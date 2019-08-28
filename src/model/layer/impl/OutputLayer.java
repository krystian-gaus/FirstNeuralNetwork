package model.layer.impl;

import matrix.Matrix;
import model.function.AbstractFunction;
import model.function.impl.Sigmoid;
import model.layer.AbstractLayer;

public class OutputLayer extends AbstractLayer
{
	public OutputLayer(int nodeCount)
	{
		super(nodeCount, new Sigmoid());
	}
	
	public OutputLayer(int nodeCount, AbstractFunction activationFunction)
	{
		super(nodeCount, activationFunction);
	}
	
	@Override
	public void updateOutputError(Matrix outputErrorOfNextLayer)
	{
		this.nodesOutputError = outputErrorOfNextLayer.getColumnOrientatedVector();	
	}
	
}
