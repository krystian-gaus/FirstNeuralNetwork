package model;

import helper.ExceptionHelper;

import java.util.ArrayList;
import java.util.List;

import dto.TrainingSample;
import dto.TrainingSet;

import matrix.Dimension;
import matrix.Matrix;
import matrix.Position;
import matrix.WeightMatrixCreator;
import model.layer.AbstractLayer;
import model.layer.impl.HiddenLayer;
import model.layer.impl.InputLayer;
import model.layer.impl.OutputLayer;

public class Model
{
	private InputLayer inputLayer;
	private List<HiddenLayer> hiddenLayers;
	private OutputLayer outputLayer;
	private double learningRate;
	private double errorRate;
	
	public Model(InputLayer inputLayer, List<HiddenLayer> hiddenLayers, OutputLayer outputLayer, double learningRate, double errorRate)
	{
		ExceptionHelper.isRate(learningRate, "Learning Rate");
		ExceptionHelper.isRate(errorRate, "Error Rate");
		ExceptionHelper.isNullOrEmpty(hiddenLayers, "Hidden Layers");
		
		this.inputLayer = inputLayer;
		this.hiddenLayers = hiddenLayers;
		this.outputLayer = outputLayer;
		this.learningRate = learningRate;
		this.errorRate = errorRate;
		
		this.initializeInputWeight();
		this.initializeHiddenLayerWeights();
		this.initializeOutputWeight();
	}
	
	public void train(TrainingSet trainingSet, int epochs)
	{
		ExceptionHelper.isNotPositive(epochs, "epochs");
		
		for(int i = 0; i < epochs; i++)
		{
			for(TrainingSample sample : trainingSet.getTrainingSamples())
			{
				this.train(sample);
			}
		}
	}
	
	public List<Double> generateOutputOf(List<Double> input)
	{
		Matrix outputOfInputLayer = this.getOutputOfInputLayer(input);
		Matrix outputOfHiddenLayers = this.getOutputOfHiddenLayers(outputOfInputLayer);
		Matrix outputOfOutputLayer = this.getOutputOfOutputLayer(outputOfHiddenLayers);
		
		return outputOfOutputLayer.getColumn(1);
	}
	
	private void train(TrainingSample sample)
	{
		List<Double> input = sample.getInput();
		List<Double> outputDebit = sample.getOutputDebit();
		
		int iterationsTaken = 0;
		int iterationsMax = 1000000;
		List<Double> output = this.generateOutputOf(input);
		while(this.isErrorBiggerThanOrEqualToErrorRate(output, outputDebit) && iterationsTaken <= iterationsMax)
		{
			this.updateErrors(outputDebit);
			this.updateWeightMatrices();
			output = this.generateOutputOf(input);
			iterationsTaken++;
		}
		System.out.println("iterations taken: " + iterationsTaken);	
	}
	
	private boolean isErrorBiggerThanOrEqualToErrorRate(List<Double> output, List<Double> debit)
	{
		boolean result = false;
		
		for(int i = 0; i < output.size(); i++)
		{
			if(Math.abs(output.get(i) - debit.get(i)) >= this.errorRate)
			{
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	private void updateErrors(List<Double> debit)
	{
		this.updateOutputLayerError(debit);
		this.updateHiddenLayerErrors();
	}
	
	private void updateWeightMatrices()
	{
		int hiddenLayersCount = this.hiddenLayers.size();
		HiddenLayer lastHiddenLayer = this.hiddenLayers.get(hiddenLayersCount - 1);		
		this.updateWeightMatrix(lastHiddenLayer, this.outputLayer);
		
		for(int i = hiddenLayersCount - 2; i >= 0; i--)
		{
			HiddenLayer hiddenLayer = this.hiddenLayers.get(i);
			HiddenLayer nextHiddenLayer = this.hiddenLayers.get(i + 1);	
			this.updateWeightMatrix(hiddenLayer, nextHiddenLayer);
		}
		
		HiddenLayer firstHiddenLayer = this.hiddenLayers.get(0);
		this.updateWeightMatrix(this.inputLayer, firstHiddenLayer);
	}

	private void updateHiddenLayerErrors()
	{
		int hiddenLayersCount = this.hiddenLayers.size();
		HiddenLayer lastHiddenLayer = this.hiddenLayers.get(hiddenLayersCount - 1);
		lastHiddenLayer.updateOutputError(this.outputLayer.getNodesOutputError());
		
		for(int i = hiddenLayersCount - 2; i >= 0; i--)
		{
			HiddenLayer hiddenLayer = this.hiddenLayers.get(i);
			hiddenLayer.updateOutputError(this.hiddenLayers.get(i + 1).getNodesOutputError());		
		}	
	}

	private void updateOutputLayerError(List<Double> debit)
	{
		List<List<Double>> debitMatrixRepresentation = new ArrayList<>();
		debitMatrixRepresentation.add(debit);
		Matrix debitMatrix = (new Matrix(debitMatrixRepresentation)).getColumnOrientatedVector();	
		Matrix actualMatrix = this.outputLayer.getActivatedNodesOutput().getColumnOrientatedVector();
		Matrix outputErrorOfOutputLayer = debitMatrix.minus(actualMatrix);
		
		this.outputLayer.updateOutputError(outputErrorOfOutputLayer);
	}
	
	private void updateWeightMatrix(AbstractLayer layerToUpdate, AbstractLayer nextLayer)
	{
		Matrix outputErrorOfNextLayer = nextLayer.getNodesOutputError();
		layerToUpdate.updateWeightMatrix(outputErrorOfNextLayer, this.learningRate);
	}
	
	private Matrix getOutputOfOutputLayer(Matrix input)
	{
		return this.outputLayer.getWeightedOutputOf(input);
	}

	private Matrix getOutputOfHiddenLayers(Matrix input)
	{
		Matrix outputOfPreviousLayer = input;
		
		for(HiddenLayer hiddenLayer : this.hiddenLayers)
		{
			outputOfPreviousLayer = hiddenLayer.getWeightedOutputOf(outputOfPreviousLayer);
		}
		
		// now it is the output of the last layer
		return outputOfPreviousLayer;
	}

	private Matrix getOutputOfInputLayer(List<Double> input)
	{
		List<List<Double>> matrixRepresentation = new ArrayList<>();
		matrixRepresentation.add(input);
		Matrix inputMatrix = new Matrix(matrixRepresentation);
		
		return this.inputLayer.getWeightedOutputOf(inputMatrix);		
	}

	private void initializeHiddenLayerWeights()
	{
		for(int i = 0; i < this.hiddenLayers.size() - 1; i++)
		{
			HiddenLayer hiddenLayer = this.hiddenLayers.get(i);	
			HiddenLayer nextHiddenLayer = this.hiddenLayers.get(i + 1);
			Dimension dimension = new Dimension(nextHiddenLayer.getNodeCount(), hiddenLayer.getNodeCount());
			Matrix weightMatrix = WeightMatrixCreator.createWeightMatrix(dimension);	
			hiddenLayer.initializeWeightMatrix(weightMatrix);
		}
		
		HiddenLayer lastHiddenLayer = this.hiddenLayers.get(this.hiddenLayers.size() - 1);
		Dimension dimension = new Dimension(this.outputLayer.getNodeCount(), lastHiddenLayer.getNodeCount());
		Matrix weightMatrix = WeightMatrixCreator.createWeightMatrix(dimension);
		lastHiddenLayer.initializeWeightMatrix(weightMatrix);
	}

	private void initializeInputWeight()
	{
		HiddenLayer firstHiddenLayer = this.hiddenLayers.get(0);	
		Dimension dimension = new Dimension(firstHiddenLayer.getNodeCount(), this.inputLayer.getNodeCount());
		Matrix weightMatrix = WeightMatrixCreator.createWeightMatrix(dimension);
		this.inputLayer.initializeWeightMatrix(weightMatrix);
	}
	
	private void initializeOutputWeight()
	{
		Matrix weightMatrix = new Matrix(new Dimension(1, 1));
		weightMatrix.setEntry(new Position(1, 1), (double) 1);
		this.outputLayer.initializeWeightMatrix(weightMatrix);
	}

}
