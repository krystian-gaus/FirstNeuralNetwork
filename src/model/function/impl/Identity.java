package model.function.impl;

import model.function.AbstractFunction;

public class Identity extends AbstractFunction
{
	@Override
	public double get(double x)
	{
		return x;
	}

	@Override
	public double getDerivative(double x)
	{
		return 1;
	}

}
