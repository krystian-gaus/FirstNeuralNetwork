package matrix;

import helper.ExceptionHelper;

public class Dimension
{
	private int rowCount;
	private int columnCount;
	
	public Dimension(int rowCount, int columnCount)
	{
		ExceptionHelper.isMatrixDimension(rowCount, columnCount);
		
		this.rowCount = rowCount;
		this.columnCount = columnCount;
	}
	
	public int getRowCount()
	{
		return this.rowCount;
	}
	
	public int getColumnCount()
	{
		return this.columnCount;
	}

}
