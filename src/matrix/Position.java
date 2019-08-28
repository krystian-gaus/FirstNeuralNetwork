package matrix;

public class Position
{
	int rowPosition;
	int columnPosition;
	
	public Position(int i, int j)
	{
		this.rowPosition = i;
		this.columnPosition = j;
	}
	
	public int getRowPosition()
	{
		return this.rowPosition;
	}
	
	public int getColumnPosition()
	{
		return this.columnPosition;
	}

}
