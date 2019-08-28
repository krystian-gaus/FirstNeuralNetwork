package matrix;

import java.util.ArrayList;
import java.util.List;


public class WeightMatrixCreator
{ 	
	public static Matrix createWeightMatrix(Dimension dimension)
	{
		List<List<Double>> data = new ArrayList<>();
			
		for(int i = 0; i < dimension.getRowCount(); i++)
		{
			List<Double> dataRow = new ArrayList<>();
			
			for(int j = 0; j < dimension.getColumnCount(); j++)
			{
				dataRow.add(getBoundedRandomNumber(dimension));
			}
			
			data.add(dataRow);
		}	
		
		return new Matrix(data);
	}
	
	private static double getBoundedRandomNumber(Dimension dimension)
	{
		double boundary = 1 / Math.sqrt(dimension.getRowCount());
		
		double result = 0;
		
		while(result == 0)
		{
			result = (Math.random() * 2 * boundary) - boundary;
		}
				
		return result;		
	}

}
