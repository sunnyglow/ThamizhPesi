package com.tamil.tts.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tamil.tts.constants.TamilTTSConstants;




public class TamilTTSUtils 
{
	public static Map<String, String> sameSound = new HashMap<String, String>();

	static
	{
	/*	sameSound.put("ண", "ந");
		sameSound.put("ணா", "நா");
		sameSound.put("ணி", "நி");
		sameSound.put("ணீ", "நீ");
		sameSound.put("ணு", "நு");
		sameSound.put("ணூ", "நூ");
		sameSound.put("ணெ", "நெ");
		sameSound.put("ணே", "நே");
		sameSound.put("ணை", "நை");
		sameSound.put("ணொ", "நொ");
		sameSound.put("ணோ", "நோ");
		sameSound.put("ணௌ", "நௌ");*/
		
		sameSound.put("ந", "ண");
		sameSound.put("நா", "ணா");
		sameSound.put("நி", "ணி");
		sameSound.put("நீ", "ணீ");
		sameSound.put("நு", "ணு");
		sameSound.put("நூ", "ணூ");
		sameSound.put("நெ", "ணெ");
		sameSound.put("நே", "ணே");
		sameSound.put("நை", "ணை");
		sameSound.put("நொ", "ணொ");
		sameSound.put("நோ", "ணோ");
		sameSound.put("நௌ", "ணௌ");
		
		sameSound.put("ன", "ண");
		sameSound.put("னா", "ணா");
		sameSound.put("னி", "ணி");
		sameSound.put("னீ", "ணீ");
		sameSound.put("னு", "ணு");
		sameSound.put("னூ", "ணூ");
		sameSound.put("னெ", "ணெ");
		sameSound.put("னே", "ணே");
		sameSound.put("னை", "ணை");
		sameSound.put("னொ", "ணொ");
		sameSound.put("னோ", "ணோ");
		sameSound.put("னௌ", "ணௌ");
		
		sameSound.put("ண", "ன");
		sameSound.put("ணா", "னா");
		sameSound.put("ணி", "னி");
		sameSound.put("ணீ", "னீ");
		sameSound.put("ணு", "னு");
		sameSound.put("ணூ", "னூ");
		sameSound.put("ணெ", "னெ");
		sameSound.put("ணே", "னே");
		sameSound.put("ணை", "னை");
		sameSound.put("ணொ", "னொ");
		sameSound.put("ணோ", "னோ");
		sameSound.put("ணௌ", "னௌ");
		
		/*sameSound.put("ந", "ன");
		sameSound.put("நா", "னா");
		sameSound.put("நி", "னி");
		sameSound.put("நீ", "னீ");
		sameSound.put("நு", "னு");
		sameSound.put("நூ", "னூ");
		sameSound.put("நெ", "னெ");
		sameSound.put("நே", "னே");
		sameSound.put("நை", "னை");
		sameSound.put("நொ", "னொ");
		sameSound.put("நோ", "னோ");
		sameSound.put("நௌ", "னௌ");*/
		
		/*sameSound.put("ன", "ந");
		sameSound.put("னா", "நா");
		sameSound.put("னி", "நி");
		sameSound.put("னீ", "நீ");
		sameSound.put("னு", "நு");
		sameSound.put("னூ", "நூ");
		sameSound.put("னெ", "நெ");
		sameSound.put("னே", "நே");
		sameSound.put("னை", "நை");
		sameSound.put("னொ", "நொ");
		sameSound.put("னோ", "நோ");
		sameSound.put("னௌ", "நௌ");*/

		
		sameSound.put("ற", "ர");
		sameSound.put("றா", "ரா");
		sameSound.put("றி", "ரி");
		sameSound.put("றீ", "ரீ");
		sameSound.put("று", "ரு");
		sameSound.put("றூ", "ரூ");
		sameSound.put("றெ", "ரெ");
		sameSound.put("றே", "ரே");
		sameSound.put("றை", "ரை");
		sameSound.put("றொ", "ரொ");
		sameSound.put("றோ", "ரோ");
		sameSound.put("றௌ", "ரௌ");

		sameSound.put("ர", "ற");
		sameSound.put("ரா", "றா");
		sameSound.put("ரி", "றி");
		sameSound.put("ரீ", "றீ");
		sameSound.put("ரு", "று");
		sameSound.put("ரூ", "றூ");
		sameSound.put("ரெ", "றெ");
		sameSound.put("ரே", "றே");
		sameSound.put("ரை", "றை");
		sameSound.put("ரொ", "றொ");
		sameSound.put("ரோ", "றோ");
		sameSound.put("ரௌ", "றௌ");

		sameSound.put("ல", "ள");
		sameSound.put("லா", "ளா");
		sameSound.put("லி", "ளி");
		sameSound.put("லீ", "ளீ");
		sameSound.put("லு", "ளு");
		sameSound.put("லூ", "ளூ");
		sameSound.put("லெ", "ளெ");
		sameSound.put("லே", "ளே");
		sameSound.put("லை", "ளை");
		sameSound.put("லொ", "ளொ");
		sameSound.put("லோ", "ளோ");
		sameSound.put("லௌ", "ளௌ");

		sameSound.put("ள", "ல");
		sameSound.put("ளா", "லா");
		sameSound.put("ளி", "லி");
		sameSound.put("ளீ", "லீ");
		sameSound.put("ளு", "லு");
		sameSound.put("ளூ", "லூ");
		sameSound.put("ளெ", "லெ");
		sameSound.put("ளே", "லே");
		sameSound.put("ளை", "லை");
		sameSound.put("ளொ", "லொ");
		sameSound.put("ளோ", "லோ");
		sameSound.put("ளௌ", "லௌ");

	}

		
	  public static boolean checkPresence(String[] escapeWordArray, String word)
		{
			for(String value: escapeWordArray)
			{
				if(value.trim().equalsIgnoreCase(word))
				{
					return true;
				}
			}
			return false;
		}
	   
	   public static boolean findPresence(String[] escapeWordArray, String word)
	   {
		   for(String value: escapeWordArray)
			{
				if(word.contains(value.trim()))
				{
					return true;
				}
			}
			return false;
	   }
	   
	   public static List<String> getLetterList(String word)
	   {
		   word = word.trim();
		   char[] character = word.toCharArray();
		   int i = 0;
		   boolean abbrevation = false;
		   List<String> lettersList = new ArrayList<String>();
		   
		   if(character.length <= 0){return null;}
		   
		   if(checkPresence(TamilTTSConstants.உயிர்_எழுத்துக்கள், String.valueOf(character[i])))
		   {
			   lettersList.add(word.substring(i, i+1));
			   i=1;
			   
		   }
		   for(; i < character.length; i++)
		   {
			    
			   if(character[i] == ')' || character[i] == '(' || character[i] == '”' || character[i] =='“' || character[i] == '‘' || character[i] == '’' || character[i] == '-' || character[i] == '`'
					   || character[i] == '\'')
			   {
				   continue;
			   }
			   if((character[i] == '.' && i == character.length - 1) || character[i] == '\n')
			   {
			   		
			   	
			   		continue;
			   }
			   
			   if(character[i] == '.' )
			   {
			   	   continue;
			   }
			   
			   if(character[i] == '?')
			   {
			   		
			   		continue;
			   }
			   if(String.valueOf(character[i]).trim().equals(""))
			   {
				   continue;
			   }
			   
			   if(String.valueOf(character[i]).trim().equals(","))
			   {
				   
				   continue;
			   }
			   if(String.valueOf(character[i]).trim().equals(";"))
			   {
				  
				   continue;
			   }
			   
			   if(String.valueOf(character[i]).trim().equals("!"))
			   {
				  
				   continue;
			   }
			   
			  /* if(i <= character.length - 2 && "ெ".equalsIgnoreCase(String.valueOf(character[i])))
			   {
				   if("ள".equalsIgnoreCase(String.valueOf(character[i+1])))
				   {
					   lettersList.add(word.substring(i, i+2));
				   }
				   i+=1;
				   continue;
			   }*/
			   
			   if(i <= character.length - 2 && checkPresence(TamilTTSConstants.உதவி_எழுத்துக்கள், String.valueOf(character[i+1])))
			   {
				   lettersList.add(word.substring(i, i+2));
				   i+=1;
				   continue;
				   
			   }
			   else if(i <= character.length - 2 && checkPresence(TamilTTSConstants.சிறப்பு_எழுத்துக்கள், String.valueOf(character[i])))
			   {
				   if(i <= character.length - 3 && checkPresence(TamilTTSConstants.உதவி_எழுத்துக்கள், String.valueOf(character[i+2])))
				   {
					   lettersList.add(word.substring(i, i+3));
					   i+=2;
					   continue;
					   
				   }
				   lettersList.add(word.substring(i, i+2));
				   i+=1;
				   continue;
			   }
			   
			 
			   lettersList.add(word.substring(i,i+1));
		   }
		   
		   return lettersList;
	   }
	   
	   public static List<String> applyTamilGrammer(List<String> letterList)
	   {
		   List<String> subList = new ArrayList<String>();
		   for(int i = 0; i < letterList.size(); i++)
		   {
			   if( i > 0 && checkPresence(TamilTTSConstants.ற_வரிசை, getLetterList(letterList.get(i)).get(0)))
			   {
				   subList = getLetterList(letterList.get(i-1));
				   
				   if(i > 0 && subList.get(subList.size()-1).contains("ற்"))
				   {
					   String word = letterList.get(i-1);
					   if(!word.contains("_")){
						   letterList.set(i-1, "_"+word);
					   }
				   }
			   }
			   
			   
			   if(i > 0 && checkContains(TamilTTSConstants.த_வரிசை, getLetterList(letterList.get(i)).get(0)) && !getLetterList(letterList.get(i)).get(0).equals("த்"))
			   {
				   subList = getLetterList(letterList.get(i-1));
				   
				   if(!(i > 0 && subList.get(subList.size()-1).contains("த்")))
				   {
					   String word = letterList.get(i);
					   if(!word.contains("_")){
						   letterList.set(i, "_"+word);
					   }
				   }
			   }
			   
			   if(i > 0 && checkContains(TamilTTSConstants.ச_வரிசை, getLetterList(letterList.get(i)).get(0)))
			   {
				   subList = getLetterList(letterList.get(i-1));
				   
				   if(i > 0 && (subList.get(subList.size()-1).contains("ச்") || subList.get(subList.size()-1).contains("ஞ்") ||  subList.get(subList.size()-1).contains("ற்")))
				   {
					   String word = letterList.get(i);
					   if(!word.contains("_")){
						   letterList.set(i, "_"+word);
					   }
				   }
			   }
			   
			   if(i > 0 && checkContains(TamilTTSConstants.க_வரிசை, getLetterList(letterList.get(i)).get(0)))
			   {
				   subList = getLetterList(letterList.get(i-1));
				   
				   if(i > 0 && subList.get(subList.size()-1).contains("ங்") || (!"ப".contains(subList.get(subList.size()-1)) && !subList.get(subList.size()-1).contains("க்")  && !subList.get(subList.size()-1).contains("ற்") && !subList.get(subList.size()-1).contains("ட்")))
				   {
					   String word = letterList.get(i);
					   if(!word.contains("_")){
						   letterList.set(i, "_"+word);
					   }
				   }
			   }
			   
			   if(i > 0 && checkContains(TamilTTSConstants.ட_வரிசை, getLetterList(letterList.get(i)).get(0)))
			   {
				   subList = getLetterList(letterList.get(i-1));
				   
				   if((i > 0 && subList.get(subList.size()-1).contains("ட்")))
				   {
					   String word = letterList.get(i);
					   if(!word.contains("_")){
						   letterList.set(i, "_"+word);
					   }
				   }
			   }
		   }
		   return letterList;
	   }
	   
	   public static List<String> getNormalizedLetterList(List<String> letterList)
	   {
		   if(letterList == null){return null;}
		   List<String> normalizedLetterList = new ArrayList<String>();
		   for(int i = 0; i < letterList.size(); i++)
		   {
			   if(checkPresence(TamilTTSConstants.மெய்_எழுத்துக்கள், letterList.get(i)))
			   {
				   normalizedLetterList.add(letterList.get(i));
				   continue;
			   }
			   if(i < letterList.size() -1 && !checkPresence(TamilTTSConstants.வடமொழி_எழுத்துக்கள், letterList.get(i)) && checkPresence(TamilTTSConstants.மெய்_எழுத்துக்கள், letterList.get(i+1)))
			   {
				   	   
				  	   normalizedLetterList.add(letterList.get(i)+letterList.get(i+1));
					   i++;
			   }
			   else
			   {
				   normalizedLetterList.add(letterList.get(i));
			   }
		   }
		   
		   return normalizedLetterList;
	   }
	   public static Map<String, List<String>> getPhonoticsMap(String sentence)
	   {
		   if(sentence == null || sentence.length() <= 0){
			   //////log.info("No Word to process...!");
			   return null;
		   }
			   
		   String[] wordArray = null;
		   
		   if(sentence.split(" ").length > sentence.split("\n").length)
		   {
			   wordArray = sentence.split(" ");
		   }
		   else
		   {
			   wordArray = sentence.split("\n");
		   }
		   Map<String, List<String>> phonoticsMap = new HashMap<String, List<String>>();
		   Map<String, String> tempMap = new HashMap<String, String>();
		   List<String> letterList = new ArrayList<String>();
		   List<String> pathList = new ArrayList<String>();
		   List<String> fileNameList = new ArrayList<String>();
		   
		   boolean found = false;
		   int wordCount = 0;
		   File file = null;
		   
		   for(String words: wordArray)
		   {
			   //log.info("=====================================================================================");

			   
			   wordCount++;
			   int verifyIndex = 0;
			   if(words.equals(""))
			   {
				   continue;
			   }
			   String numberSynthesized = null;
			   /*if(TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், words))
			   {
				   numberSynthesized = NumberSynthesizer.getInstance().checkForNumber(words);
			   }
			   
			   if(numberSynthesized != null && numberSynthesized.length() > 0)
			   {
				   words = numberSynthesized;
			   }*/
			   
			   String wordInnerSplit[] = words.split(" ");
			   for(String word: wordInnerSplit)
			   {
				   if(word.equals(""))
				   {
					   continue;
				   }
				   //log.info("Processing Word/Sentence: "+ wordInnerSplit);
				   letterList = getLetterList(word);
				   letterList = getNormalizedLetterList(letterList);
				   //letterList = applyTamilGrammer(letterList);
				   
				   //log.info("Word Splitted as:"+ letterList);
				   found = false;
				   if(letterList == null)
				   {
					   continue;
				   }
				   for(int i = letterList.size() ; i >= verifyIndex; i--)
				   {
					   
					   if(checkPresence(TamilTTSConstants.NUMBER_SOUNDS, word))
						{
							file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.NUMBER_WORDS+word.trim()+TamilTTSConstants.FILE_EXTENSION);
						    if(file.exists())
						    {
						       //log.info("***************************************************File found in NUMBER WORDS folder: "+ word.trim());
								//log.info("Found: "+ word.trim());
								pathList.add(file.getAbsolutePath());
								fileNameList.add(word);
								break;
						    }
						    
						}
						
					   if(letterList.size() >= 4)
						{
							file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.COMMON_WORDS+word.trim()+TamilTTSConstants.FILE_EXTENSION);
						    if(file.exists())
						    {
						       //log.info("************************************************File found in COMON WORDS letter folder: "+ word.trim());
								//log.info("Found: "+ word.trim());
								pathList.add(file.getAbsolutePath());
								fileNameList.add(word);
								break;
						    }
						    
						}
						
						
						if(letterList.size() == 3)
						{
							file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.THREE_LETTER_WORD+word.trim()+TamilTTSConstants.FILE_EXTENSION);
						    if(file.exists())
						    {
						    	
						       //log.info("**************************************File found in 3 letter folder: "+ word.trim());
								//log.info("Found: "+ word.trim());
								pathList.add(file.getAbsolutePath());
								fileNameList.add(word);
								break;
						    }
						    
						}
					   
					   file = searchDirectory(TamilTTSConstants.VOICE_PATH, getString(verifyIndex, i, letterList));
					   
					   if(file != null)
					   {
						   found = true;
						   if(i == letterList.size())
						   {
							   ////log.info("Found File: "+getString(verifyIndex, i, letterList));
							   //phonoticsMap.put(getString(verifyIndex, i, letterList), file.getAbsolutePath());
							   pathList.add(file.getAbsolutePath());
							   fileNameList.add(getString(verifyIndex, i, letterList));
							   break;
						   }
						   else
						   {
							   ////log.info("Found File: "+getString(verifyIndex, i, letterList));
							   //phonoticsMap.put(getString(verifyIndex, i, letterList), file.getAbsolutePath());
							   pathList.add(file.getAbsolutePath());
							   fileNameList.add(getString(verifyIndex, i, letterList));
							   verifyIndex = i;
							   i = letterList.size()+1;
						   }
					   }
					   
					   if(i == verifyIndex){
						   verifyIndex++;
						   i = letterList.size()+1;
						   file = searchDirectory(TamilTTSConstants.VOICE_PATH, "SPACE");
						   if(file != null){
							   pathList.add(file.getAbsolutePath());
							   fileNameList.add("SPACE");
						   }
					   }
				   }
				   file = searchDirectory(TamilTTSConstants.VOICE_PATH, "SPACE");
				   if(file != null){
					   pathList.add(file.getAbsolutePath());
					   fileNameList.add("SPACE");
				   }
				   /*if(wordCount >= TamilTTSConstants.MAX_WORD_CAPACITY)
				   {
					  break;
				   }*/
			   }
		   }
		   ////log.info(fileNameList.toString());
		   phonoticsMap.put("PATH", pathList);
		   phonoticsMap.put("FILE_NAME", fileNameList);
		   return phonoticsMap;
	   }
	   
	   public static String getString(int start, int end, List<String> wordList)
	   {
		   String word = "";
		   if(start == end)
		   {
			   return wordList.get(start);
		   }
		   for(int i = start; i < end; i++)
		   {
			   word += wordList.get(i);
		   }
		   
		   return word.trim();
	   }
	   public static String getWord(List<String> letterList)
	   {
		   String str="";
		   
		   for(int i=0; i < letterList.size(); i++)
		   {
			   str += letterList.get(i);
		   }
		   
		   return str.trim();
	   }
	   
		public static boolean checkContains(String[] escapeWordArray, String word)
		{
			for(String value: escapeWordArray)
			{
				if(word.contains(value))
				{
					return true;
				}
			}
			return false;
		}
		
		public static String getContains(String[] escapeWordArray, String word)
		{
			for(String value: escapeWordArray)
			{
				if(word.contains(value))
				{
					return value;
				}
			}
			return null;
		}
		
		public static File searchDirectory(String path, String fileName) 
		{
			List<String> letterList = getLetterList(fileName);
			

			if(letterList == null){return null;}
			
			File file = null;
			
			if(letterList.size() > 1)
			{
				file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.END_WORDS+fileName.replace("_", "")+TamilTTSConstants.FILE_EXTENSION);
			    if(file.exists())
			    {
			       //log.info("***************************************************File found in END WORDS folder: "+ fileName);
					//log.info("Found: "+ fileName);
				   return file;
			    }
			    
			}
			
			if(letterList.size() >= 4)
			{
				file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.COMMON_WORDS+fileName.replace("_", "")+TamilTTSConstants.FILE_EXTENSION);
			    if(file.exists())
			    {
			       //log.info("************************************************File found in COMON WORDS letter folder: "+ fileName);
					//log.info("Found: "+ fileName);
				   return file;
			    }
			    
			}
			
			
			if(letterList.size() == 3)
			{
				file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.THREE_LETTER_WORD+fileName.replace("_", "")+TamilTTSConstants.FILE_EXTENSION);
			    if(file.exists())
			    {
			    	
			       //log.info("**************************************File found in 3 letter folder: "+ fileName);
					//log.info("Found: "+ fileName);
				   return file;
			    }
			    
			}
			
			if(fileName.contains("_") && letterList.size() <= 2)
			{
				if(letterList.size() == 1 || checkContains(TamilTTSConstants.மெய்_எழுத்துக்கள், fileName))
				{	
					file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.SPECIAL_SOUND+fileName+TamilTTSConstants.FILE_EXTENSION);
					if(file.exists())
				    {
					   return file;
				    }
				}
			}
			
			fileName = fileName.replace("_", "");
			
			if(fileName.equalsIgnoreCase("SPACE"))
			{
				file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.SYMBOL+fileName+TamilTTSConstants.FILE_EXTENSION);
				if(file.exists())
			    {
				   return file;
			    }  
			}
			
			
			if(letterList.size() == 2)
			{
				file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.TWO_LETTER_WORD+fileName+TamilTTSConstants.FILE_EXTENSION);
			    if(file.exists())
			    {
				       //log.info("File found in 2 letter folder: "+ fileName);
						//log.info("Found: "+ fileName);

				   return file;
			    }
			    else if(getContains(TamilTTSConstants.sameSound, fileName) != null)
			    {
			    	String word = getContains(TamilTTSConstants.sameSound, fileName);
			    	file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.TWO_LETTER_WORD+fileName.replace(word, sameSound.get(word))+TamilTTSConstants.FILE_EXTENSION);
			    	
			    	if(file.exists())
				    {
			    	   //log.info("Found in SameSound: "+ fileName);
					   return file;
				    }  
			    	/*else if(!checkContains(TamilTTSConstants.மெய்_எழுத்துக்கள், fileName))
			    	{
					    log.info("File not found in Same sound also: "+ fileName.replace(word, sameSound.get(word)));
			    	}*/
			    }
			    if(!checkContains(TamilTTSConstants.மெய்_எழுத்துக்கள், fileName))
			    {
			    	//log.info(""+ fileName);
			    }
		    	file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.SOUL_BODY_BODY+fileName+TamilTTSConstants.FILE_EXTENSION);
		    	if(file.exists())
			    {
				   return file;
			    }
			}
			
			if(letterList.size() == 1)
			{
				file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.SOUL_BODY+fileName+TamilTTSConstants.FILE_EXTENSION);
			    if(file.exists())
			    {
					   //log.info("Found in 1 Letter");
						//log.info("Found: "+ fileName);
				   return file;

			    }
			    else
			    {
			    	file = new File(TamilTTSConstants.VOICE_PATH+TamilTTSConstants.SOUL+fileName+TamilTTSConstants.FILE_EXTENSION);
			    	if(file.exists())
				    {
						   //log.info("Found in 1 Letter");
							//log.info("Found: "+ fileName);
					   return file;
				    }
			    }
			}
			return null;
	
		}
		
		private static void copyFileUsingStream(String fileName, String fullPath) throws IOException 
		 {
			   	String dest = "C:\\Users\\Sureshkumar\\Documents\\completeVoiceData\\output\\"+fileName+".wav";
			    InputStream is = null;
			    OutputStream os = null;
			    try {
			        is = new FileInputStream(fullPath);
			        os = new FileOutputStream(dest);
			        byte[] buffer = new byte[1024];
			        int length;
			        while ((length = is.read(buffer)) > 0) {
			            os.write(buffer, 0, length);
			        }
			    } 
			    catch(Exception e)
			    {
			    	e.printStackTrace();
			    }
			    finally {
			        is.close();
			        os.close();
			    }
		}
}
