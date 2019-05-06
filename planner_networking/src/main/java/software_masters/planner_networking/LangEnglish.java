package software_masters.planner_networking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class LangEnglish implements Language 
{
	ArrayList<String> keys;
	ArrayList<String> newWords;
	Properties prop;

	public LangEnglish()
	{
		keys = new ArrayList<String>();
		newWords = new ArrayList<String>();
		try 
		{
			start();
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}


	@Override
	public void start() throws FileNotFoundException
	{
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
			ip.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		FileOutputStream out = new FileOutputStream("src/main/java/prop/en.properties");
		System.out.println("out: "+out);
		
		for (int i = 0; i < keys.size(); i++)
		{
			prop.setProperty(keys.get(i), newWords.get(i));
		}
		try {
			prop.store(out, null);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
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

	
	public static void main(String[] args) throws FileNotFoundException 
	{
		LangEnglish e = new LangEnglish();
		String newW = e.getNewWord("editingTools.text");
		System.out.println("new: "+newW);
	

	}



	@Override
	public Properties getProp() 
	{
		return prop;
	}



	@Override
	public String getNewWord(String keyWord) 
	{
		String word = prop.getProperty(keyWord);
		System.out.println("word: "+word);
		return word;
	}
}
