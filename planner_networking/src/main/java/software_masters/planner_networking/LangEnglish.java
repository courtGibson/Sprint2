package software_masters.planner_networking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class LangEnglish implements Language 
{
	ArrayList<String> keys;
	ArrayList<String> newWords;
	Properties prop;

	public LangEnglish() throws FileNotFoundException 
	{
		keys = new ArrayList<String>();
		newWords = new ArrayList<String>();
		setKeys();
		setWords();
		setKeyWords(keys, newWords);
	}



	@Override
	public void setKeyWords(ArrayList<String> keys, ArrayList<String> newWords) throws FileNotFoundException
	{
		prop = new Properties();
		FileInputStream ip = new FileInputStream("src/main/java/prop/en.properties");
		
		try 
		{
			prop.load(ip);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < keys.size(); i++)
		{
			prop.setProperty(keys.get(i), newWords.get(i));
		}
		
	}




	@Override
	public void setKeys() 
	{
		try {
				File file = new File("src/main/java/prop/keys.txt");
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				//StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = bufferedReader.readLine()) != null) 
				{
					System.out.println(line);
					keys.add(line);
				}
				fileReader.close();

		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

		
	}

	@Override
	public void setWords()
	{
		try {
				File file = new File("src/main/java/prop/en.txt");
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				//StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = bufferedReader.readLine()) != null) 
				{
					newWords.add(line);
				}
				fileReader.close();

			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		
	}
	
	
	public Properties getProp() 
	{
		return prop;
	}

	
	public static void main(String[] args) throws FileNotFoundException 
	{
		LangEnglish e = new LangEnglish();
		
	

	}
}
