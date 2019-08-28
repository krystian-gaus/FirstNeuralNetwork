import java.util.ArrayList;
import java.util.List;


import dao.TrainingSetExtractor;
import dto.TrainingSample;
import dto.TrainingSet;

import model.Model;
import model.layer.impl.HiddenLayer;
import model.layer.impl.InputLayer;
import model.layer.impl.OutputLayer;

public class MainClass
{
	public static void main(String[] args)
	{		
		TrainingSetExtractor extractor = new TrainingSetExtractor();
		TrainingSet trainingSet = extractor.getTrainingSet("/home/gausigaus/Desktop/Twitter/SimpleTestData/identityTest.csv", ",", 0, true, 0, true);
		
        ///////////////////////////
        // CREATE MODEL
        ///////////////////////////
			
		InputLayer inputLayer = new InputLayer(trainingSet.getTrainingSamples().get(0).getInputDimension());
		
		List<HiddenLayer> hiddenLayers = new ArrayList<>();
		hiddenLayers.add(new HiddenLayer(10));
		hiddenLayers.add(new HiddenLayer(40));
		hiddenLayers.add(new HiddenLayer(20));

		OutputLayer ouputLayer = new OutputLayer(2);
		
		Model model = new Model(inputLayer, hiddenLayers, ouputLayer, (double) 0.1, (double) 0.2);
		
		model.train(trainingSet, 100);
		
		
		List<Double> inputTest1 = new ArrayList<>();
		inputTest1.add((double) 40);
		inputTest1.add((double) 80);
		
		List<Double> inputTest2 = new ArrayList<>();
		inputTest2.add((double) 80);
		inputTest2.add((double) 40);
		


		
		System.out.println("Test - expected [1, 0] - actual " + model.generateOutputOf(inputTest1));
		System.out.println("Test - expected [0, 1] - actual " + model.generateOutputOf(inputTest2));
		
		/*
		
		///////////////////////////
		// CREATE TRAINING SAMPLES
		///////////////////////////
		
		List<TrainingSample> trainingSamples = new ArrayList<>();
		
		List<Double> input = new ArrayList<>();
		input.add((double) 2);
		input.add((double) 3);
		
		List<Double> debit = new ArrayList<>();
		debit.add((double) 1);
		debit.add((double) 0);
		
		trainingSamples.add(new TrainingSample(input, debit));
	
		input = new ArrayList<>();
		input.add((double) 300);
		input.add((double) 6);
		
		debit = new ArrayList<>();
		debit.add((double) 0);
		debit.add((double) 1);
		
		trainingSamples.add(new TrainingSample(input, debit));
		
		input = new ArrayList<>();
		input.add((double) 7);
		input.add((double) 400);
		
		debit = new ArrayList<>();
		debit.add((double) 1);
		debit.add((double) 0);
		
		trainingSamples.add(new TrainingSample(input, debit));
		
		input = new ArrayList<>();
		input.add((double) 14);
		input.add((double) 2);
		
		debit = new ArrayList<>();
		debit.add((double) 0);
		debit.add((double) 1);
		
		trainingSamples.add(new TrainingSample(input, debit));
		
		input = new ArrayList<>();
		input.add((double) 500);
		input.add((double) 250);
		
		debit = new ArrayList<>();
		debit.add((double) 0);
		debit.add((double) 1);
		
		trainingSamples.add(new TrainingSample(input, debit));
	
		input = new ArrayList<>();
		input.add((double) 300);
		input.add((double) 600);
		
		debit = new ArrayList<>();
		debit.add((double) 1);
		debit.add((double) 0);
		
		trainingSamples.add(new TrainingSample(input, debit));
		
		input = new ArrayList<>();
		input.add((double) 300);
		input.add((double) 200);
		
		debit = new ArrayList<>();
		debit.add((double) 0);
		debit.add((double) 1);
		
		trainingSamples.add(new TrainingSample(input, debit));
		
		TrainingSet trainingSet = new TrainingSet(trainingSamples);
		
	
        ///////////////////////////
        // TRAINING
        ///////////////////////////
		
		model.train(trainingSet);
		
		
		///////////////////////////
        // TEST
        ///////////////////////////		
		
		List<Double> inputTest1 = new ArrayList<>();
		inputTest1.add((double) 70);
		inputTest1.add((double) 400);
		
		List<Double> inputTest2 = new ArrayList<>();
		inputTest2.add((double) 12);
		inputTest2.add((double) 6);	
		
		System.out.println("Test - expected [1, 0] - actual " + model.generateOutputOf(inputTest1));
		System.out.println("Test - expected [0, 1] - actual " + model.generateOutputOf(inputTest2));
		
		*/

		//System.out.println("done");	
	}
	
}
