package software_masters.planner_networking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface Language 
{
	
	//void writeFile(String language, ArrayList<String> keys, ArrayList<String> newWords);
	
	void setKeyWords(ArrayList<String> keys, ArrayList<String> newWords) throws FileNotFoundException;
	
	void setKeys();
	
	void setWords();
	
}
