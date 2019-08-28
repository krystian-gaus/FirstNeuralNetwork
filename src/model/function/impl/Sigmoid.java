package model.function.impl;

import model.function.AbstractFunction;

public class Sigmoid extends AbstractFunction
{	
	@Override
	public double get(double x)
	{
		return 1 / (1 + Math.exp(-x));
	}
	
	@Override
	public double getDerivative(double x)
	{
		return this.get(x) * (1 - this.get(x));
	}

}
