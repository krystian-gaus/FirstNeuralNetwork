package model.function;

import matrix.Matrix;

public interface Function
{
	double get(double x);
	
	double getDerivative(double x);
	
    Matrix get(Matrix matrix);
	
	Matrix getDerivative(Matrix matrix);

}
