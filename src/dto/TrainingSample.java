package dto;

import helper.ExceptionHelper;

import java.util.List;

public class TrainingSample
{
	private List<Double> input;
	private List<Double> outputDebit;
	private int inputDimension;
	private int outputDebitDimension;
	
	public TrainingSample(List<Double> input, List<Double> outputDebit)
	{
		ExceptionHelper.isNullOrEmpty(input, "Input");
		ExceptionHelper.isNullOrEmpty(outputDebit, "Output debit");
		
		this.input = input;
		this.outputDebit = outputDebit;
		this.inputDimension = input.size();
		this.outputDebitDimension = outputDebit.size();
	}

	public List<Double> getInput()
	{
		return this.input;
	}
	
	public List<Double> getOutputDebit()
	{
		return this.outputDebit;
	}
	
	public int getInputDimension()
	{
		return this.inputDimension;
	}
	
	public int getOutputDebitDimension()
	{
		return this.outputDebitDimension;
	}
	
}
