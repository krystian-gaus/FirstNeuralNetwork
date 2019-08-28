package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader
{
	public static List<String[]> readFromFile(String filePath, String separator, int headerCount, boolean ignoreHeaders, int footerCount, boolean ignoreFooters)
    {
        String csvFile = filePath;
        BufferedReader reader = null;
        String line = "";
        List<String[]> lines = new ArrayList<>();

        try
        {
            reader = new BufferedReader(new FileReader(csvFile));
            while ((line = reader.readLine()) != null)
            {
            	lines.add(line.split(separator));
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        removeUnwantedElements(lines, headerCount, ignoreHeaders, footerCount, ignoreFooters);
        
        return lines;
    }

	private static void removeUnwantedElements(List<String[]> lines, int headerCount,
			boolean ignoreHeaders, int footerCount, boolean ignoreFooters)
	{
		if(ignoreHeaders && headerCount > 0)
		{
			for(int i = 0; i < headerCount; i++)
			{
				lines.remove(0);
			}		
		}
		
		if(ignoreFooters & footerCount > 0)
		{
			int decrementedSize = lines.size() - 1;
			for(int i = decrementedSize; i > decrementedSize - footerCount; i--)
			{
				lines.remove(i);
			}		
		}
	}

}
