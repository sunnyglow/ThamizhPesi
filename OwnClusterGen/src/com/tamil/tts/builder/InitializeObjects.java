package com.tamil.tts.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tamil.tts.cluster.ClusterSearch;
import com.tamil.tts.constants.TamilTTSConstants;
import com.tamil.tts.model.TimeDataModel;
import com.tamil.tts.search.trie.MapTrie;
import com.tamil.tts.search.trie.SortedMapTrie;

public class InitializeObjects {
	
	public static List<TimeDataModel> clusterModelList = new ArrayList<TimeDataModel>();
	public static List<TimeDataModel> twoThreeLetterModelList = new ArrayList<TimeDataModel>();
	public static final MapTrie<String> mapTrie = new SortedMapTrie<>();
	public static Map<String, TimeDataModel> clusterMap = new HashMap<String, TimeDataModel>();
	
	public static void main(String[] args) {
		initializeTimeDataObjects(clusterModelList, TamilTTSConstants.TIME_DATA_PATH, TamilTTSConstants.AUDIO_PATH);
		//initializeTimeDataObjects(twoThreeLetterModelList, TamilTTSConstants.TWO_THREE_LETTER_PATH, TamilTTSConstants.TWO_THREE_LETTER_AUDIO_PATH);
	}
	
	public static void initializeTimeDataObjects()
	{
		initializeTimeDataObjects(clusterModelList, TamilTTSConstants.TIME_DATA_PATH, TamilTTSConstants.AUDIO_PATH);
		//initializeTimeDataObjects(twoThreeLetterModelList, TamilTTSConstants.TWO_THREE_LETTER_PATH, TamilTTSConstants.TWO_THREE_LETTER_AUDIO_PATH);
	}
	public static void initializeTimeDataObjects(List<TimeDataModel> modelList, String path, String audioPath)
	{
		TimeDataModel model = null;
		
		
		try 
		{
			BufferedReader reader = null;
			
			File files = new File(path);
			File file[] = files.listFiles();
			for(File actualFile:file)
			{
				if(actualFile.isDirectory())
				{
					continue;
				}
				String str= actualFile.getAbsolutePath();
				str = str.substring(str.lastIndexOf('\\')+1);
				String fileName = str.substring(0, str.lastIndexOf('.'));
				reader = new BufferedReader(new FileReader(actualFile));
			
				String line = null;
				model = new TimeDataModel();
				model.setFileName(fileName);
				model.setAudioPath(audioPath);
				boolean firstLine = true;
				while((line = reader.readLine()) != null)
				{
					line = line.trim();
					
					if(firstLine)
					{
						String[] wordArray = line.split(" ");
						
						for(int x = 0 ; x < wordArray.length; x++)
						{
							if(wordArray[x].trim().equalsIgnoreCase(""))
							{
								continue;
							}
							mapTrie.insert(wordArray[x], fileName);
						}
						model.setSentence(line);
						firstLine = false;
						continue;
					}
					String[] buffer = line.split(",");
					model.getLetterList().add(buffer[0]);
					model.getStartTime().add(Integer.parseInt(buffer[1]));
					model.getEndTime().add(Integer.parseInt(buffer[2]));
				}
				modelList.add(model);
				clusterMap.put(fileName, model);
				model = null;
				reader.close();
				reader = null;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}
