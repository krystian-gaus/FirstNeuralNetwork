package dao;

import java.util.ArrayList;
import java.util.List;

import dto.TrainingSample;
import dto.TrainingSet;


public class TrainingSetExtractor
{
	public TrainingSet getTrainingSet(String filePath, String separator)
	{
		return this.getTrainingSet(filePath, separator, 0, false, 0, false);
	}
	
	public TrainingSet getTrainingSet(String filePath, String separator, int headerCount, boolean ignoreHeaders)
	{
		return this.getTrainingSet(filePath, separator, headerCount, ignoreHeaders, 0, false);
	}
	
	public TrainingSet getTrainingSet(String filePath, String separator, int headerCount, boolean ignoreHeaders, int footerCount, boolean ignoreFooters)
	{
		List<TrainingSample> trainingSamples = new ArrayList<>();
		List<String[]> splittedLines = CsvReader.readFromFile(filePath, separator, headerCount, ignoreHeaders, footerCount, ignoreFooters);
	    for(String[] splittedLine : splittedLines)
	    {
	    	trainingSamples.add(this.getTrainingSample(splittedLine));
	    }
	    return new TrainingSet(trainingSamples);
	}

    private TrainingSample getTrainingSample(String[] splittedLine)
    {
    	List<Double> input = new ArrayList<>();
		for(int i = 0; i < splittedLine.length - 1; i++)
		{
			input.add(Double.parseDouble(splittedLine[i]));
		}
		
		List<Double> outputDebit = this.getOutputDebit(splittedLine[splittedLine.length - 1]);
		
		return new TrainingSample(input, outputDebit);
	}

	private List<Double> getOutputDebit(String string)
	{
		List<Double> result = new ArrayList<>();
		
		int label = Integer.parseInt(string);
		if(label == 1) // pass
		{
			result.add((double) 1); // pass
			result.add((double) 0); // fail
		}
		else if (label == 0) // fail
		{
			result.add((double) 0); // pass
			result.add((double) 1); // fail
		}
		else
		{
			throw new IllegalStateException();
		}
		
		return result;
	}

	

}
