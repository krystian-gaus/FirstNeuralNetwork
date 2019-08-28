package model.layer;

import matrix.Dimension;
import matrix.Matrix;
import matrix.Position;
import model.function.AbstractFunction;
import helper.ExceptionHelper;

public abstract class AbstractLayer
{
	private int nodeCount;
	private AbstractFunction activationFunction;
	protected Matrix weightMatrix;
	private Matrix activatedNodesOutput;
	protected Matrix nodesOutputError;
	
	public AbstractLayer(int nodeCount, AbstractFunction activationFunction)
	{
		ExceptionHelper.isNotPositive(nodeCount, "Node Count");
		ExceptionHelper.isNull(activationFunction, "Activation Function");
		
		this.nodeCount = nodeCount;
		this.activationFunction = activationFunction;
		this.weightMatrix = null;
		this.activatedNodesOutput = null;
		this.nodesOutputError = null;
	}
	
	public int getNodeCount()
	{
		return this.nodeCount;
	}
	
	public Matrix getNodesOutput(Matrix vector)
	{
		Matrix activatedVector = this.activationFunction.get(vector.getColumnOrientatedVector());
		this.activatedNodesOutput = activatedVector;
		return activatedVector;
	}
	
	public Matrix getWeightedOutputOf(Matrix vector)
	{
		Matrix activatedVector = this.getNodesOutput(vector);
		return this.weightMatrix.multiply(activatedVector);
	}
	
	public Matrix getDerivedNodesOutputOf(Matrix vector)
	{
		return this.activationFunction.getDerivative(vector.getColumnOrientatedVector());
	}
	
	public Matrix getDerivedWeightedOutputOf(Matrix vector)
	{
		Matrix activatedVector = this.getDerivedNodesOutputOf(vector);
		return this.weightMatrix.multiply(activatedVector);
	}
	
	public void initializeWeightMatrix(Matrix weightMatrix)
	{
		if (this.weightMatrix == null)
		{
			this.weightMatrix = weightMatrix;
		}
	}
	
	public Matrix getWeightMatrix()
	{
		return this.weightMatrix.getCopy();
	}
	
	public Matrix getActivatedNodesOutput()
	{
		return this.activatedNodesOutput.getCopy();
	}
	
	public Matrix getNodesOutputError()
	{
		return this.nodesOutputError.getCopy();
	}
	
	public void updateWeightMatrix(Matrix outputErrorOfNextLayer, double learningRate)
	{
		Matrix learningRateMatrix = new Matrix(new Dimension(1, 1));
		learningRateMatrix.setEntry(new Position(1, 1), learningRate);
		
		Matrix E = outputErrorOfNextLayer.getColumnOrientatedVector();
		Matrix W = this.weightMatrix.getCopy();
		Matrix O = this.activatedNodesOutput.getColumnOrientatedVector();
		Matrix O_T = this.activatedNodesOutput.getRowOrientatedVector();
		
		// errorMatrix = - <E|sig'(W * O)> * O^T, where < | > is the inner product
		// we don't need the minus here because of the formula
		// W_new = W_old - learningRate * errorMatrix
		// just use '+' instead of '-'
		Matrix errorMatrix = (E.innerMultiply(this.activationFunction.getDerivative(W.multiply(O)))).multiply(O_T);
		this.weightMatrix = W.plus(learningRateMatrix.multiply(errorMatrix));
	}
	
	public void updateOutputError(Matrix outputErrorOfNextLayer)
	{
		// implemented in specific layers
	}
}
