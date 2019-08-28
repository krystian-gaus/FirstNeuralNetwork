package model.layer.impl;

import model.function.impl.Identity;
import model.layer.AbstractLayer;

public class InputLayer extends AbstractLayer
{
	public InputLayer(int nodeCount)
	{
		super(nodeCount, new Identity());
	}

}
