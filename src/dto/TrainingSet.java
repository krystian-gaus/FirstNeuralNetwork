package dto;

import helper.ExceptionHelper;

import java.util.List;

public class TrainingSet
{
	private List<TrainingSample> trainingSamples;
	
	public TrainingSet(List<TrainingSample> trainingSamples)
	{
		ExceptionHelper.isNullOrEmpty(trainingSamples, "Training samples");
		ExceptionHelper.haveDifferentDimensions(trainingSamples);
		
		this.trainingSamples = trainingSamples;
	}
	
	public List<TrainingSample> getTrainingSamples()
	{
		return this.trainingSamples;
	}

}
