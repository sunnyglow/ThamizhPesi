package com.tamil.tts.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.WaveFile;

import com.tamil.tts.constants.TamilTTSConstants;
import com.tamil.tts.dsp.filter.WavFilterFisher;
import com.tamil.tts.model.ResultModel;

import biz.source_code.dsp.sound.AudioIo;
import biz.source_code.dsp.sound.AudioIo.AudioSignal;


public class SynthesizeSpeech 
{
	static int counter = 0;
	static int storeCounter = 0;

	
	static boolean pathIdentified = false;
	public static void speak(List<ResultModel> modelList)
	{
		pathIdentified = true;
		
		long frameLength = 0 ;
		boolean append = false;
		AudioInputStream clip1 = null;
		ResultModel model = null;
		
		/*if(pathIdentified)
		{
			return;
		}*/
			for(int i = 0; i < modelList.size(); i++)
			{
				counter++;
				try
				{
				model = modelList.get(i);
				String fileName = model.getFileName();
				String fullPath = model.getAudioPath()+model.getFileName()+".wav";
				storeCounter++;
				//System.out.println("FileName: "+ fileName);
				if (clip1 == null) 
				{
					/*if(model.isCompleteSound())
					{
						clip1 = AudioSystem.getAudioInputStream(new File(TamilTTSConstants.SOUL_BODY_PATH+model.getFileName()+".wav"));
					}
					else */if(fileName.equalsIgnoreCase("SPACE"))
					{
						//System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
						//clip2 = AudioSystem.getAudioInputStream(new File(fullPath));
						clip1 = introduceSpace("SPACE");
					}
					else if(fileName.equalsIgnoreCase("FULL_STOP"))
					{
						clip1 = introduceSpace("FULL_STOP");
					}
					else
					{
						//clip1 = AudioSystem.getAudioInputStream(new File(fullPath));
						clip1 = getStream(fullPath, model.getStartTime(), model.getEndTime());
						//model.setStartTime(forwardSilenceRemoval(fullPath, model.getStartTime()));
						//model.setEndTime(backwardSilenceRemoval(fullPath, model.getEndTime()));
						//clip1 = optimalTrim(clip1, model.getStartTime(), model.getEndTime());
						
					}
					
					/*File file = new File("C:\\Users\\Sureshkumar\\Documents\\work\\silenceTrimTest\\"+model.getWord()+".wav");
					FileOutputStream fileOutpu = new FileOutputStream(file, false); 
					AudioSystem.write(clip1, AudioFileFormat.Type.WAVE, fileOutpu);
					clip1.close();
					clip1 = null;
					fileOutpu.close();*/
					continue;
				}
				AudioInputStream clip2 = null;
				
				if(fileName.equalsIgnoreCase("SPACE"))
				{
					//System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
					//clip2 = AudioSystem.getAudioInputStream(new File(fullPath));
					clip2 = introduceSpace("SPACE");
				}
				else if(fileName.equalsIgnoreCase("FULL_STOP"))
				{
					clip2 = introduceSpace("FULL_STOP");
				}
				else
				{
					/*if(model.isCompleteSound())
					{
						clip2 = AudioSystem.getAudioInputStream(new File(TamilTTSConstants.SOUL_BODY_PATH+model.getFileName()+".wav"));
					}
					else*/{
						clip2 = getStream(fullPath, model.getStartTime(), model.getEndTime());
						//clip2 = AudioSystem.getAudioInputStream(new File(fullPath));
						//model.setStartTime(forwardSilenceRemoval(fullPath, model.getStartTime()));
						//model.setEndTime(backwardSilenceRemoval(fullPath, model.getEndTime()));
						//clip2 = optimalTrim(clip2, model.getStartTime(), model.getEndTime());
					}
				}

				AudioInputStream appendedFiles = new AudioInputStream(new SequenceInputStream(clip1, clip2),
						clip1.getFormat(), clip1.getFrameLength() + clip2.getFrameLength());
				clip1 = appendedFiles;
				
				if(counter >= TamilTTSConstants.FILE_WRITE_BUFFER)
				{
					counter = 0;
					frameLength += appendedFiles.getFrameLength();
					mergeFile(appendedFiles, append);
					append = true;
					clip1 = null;
				}
				else
				{
					clip1 = appendedFiles;
				}
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
		}
		try
		{
			if(clip1 != null)
			{
				frameLength += clip1.getFrameLength();
				mergeFile(clip1, append);
			}
			
			if(pathIdentified)
			{
				setFinalFrameLength(frameLength);
				deleteBufferFile();
				//play();
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		
		/*
		try
		{
			File file = new File(TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.OUTPUT_VOICE_FILE_NAME);
			FileOutputStream fileOutpu = new FileOutputStream(file, false); 
			AudioSystem.write(clip1, AudioFileFormat.Type.WAVE, fileOutpu);
			clip1 = null;
		}
		catch(Exception exp)
		{
			
		}*/
		/*if(clip1 != null)
		{
			play(clip1);
		}*/
			
	}
	
	public static AudioInputStream getStream(String inputFileName, int start, int end)
	{
		AudioInputStream clip = null;
		AudioSignal signal = null;
		try 
		{
			
			clip = AudioSystem.getAudioInputStream(new File(inputFileName));
			
			int startPos = calculatePosition(clip, start );
			int endPos =  calculatePosition(clip, end - start);
			clip.close();
			clip = null;
			signal = AudioIo.loadWavFile(inputFileName);
			clip = AudioIo.getAudioStream(signal, startPos, endPos);
			return clip;
			//return WavFilterFisher.lowPassFilterStream(clip);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	public static void setFinalFrameLength(long finalFrameLength) throws UnsupportedAudioFileException, IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		File file = new File(TamilTTSConstants.OUTPUT_VOICE_PATH);	
		if(!file.exists()){
			return;
		}
		String path = TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.OUTPUT_BUFFER_FILE_NAME;
		file = new File(path);
		if(!file.exists())
		{
			file.createNewFile();
		}
		AudioInputStream clip1 = AudioSystem.getAudioInputStream(file);
		
		Field f = clip1.getClass().getDeclaredField("frameLength"); // NoSuchFieldException
		f.setAccessible(true);
		f.setLong(clip1, finalFrameLength);
		
		String fileName = TamilTTSConstants.VOICE_PATH + TamilTTSConstants.SYMBOL + TamilTTSConstants.SILENT;
		AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(fileName));
		
		AudioInputStream appendedFiles = new AudioInputStream(new SequenceInputStream(clip1, clip2),	clip1.getFormat(), finalFrameLength);
		AudioSystem.write(appendedFiles,AudioFileFormat.Type.WAVE, new File(TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.OUTPUT_VOICE_FILE_NAME));
		clip1.close();
		clip1 = null;
		Path source = Paths.get(path);
		source.toFile().deleteOnExit();
	}
	
	public static void mergeFile(AudioInputStream clip, boolean append) throws IOException
	{
	
		File file = new File(TamilTTSConstants.OUTPUT_VOICE_PATH);	
		if(!file.exists()){file.mkdir();}
		
		file = new File(TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.OUTPUT_BUFFER_FILE_NAME);
		if(!file.exists())
		{
			file.createNewFile();
		}
		FileOutputStream fileOutpu = new FileOutputStream(file, append); // true to append
		
		//System.out.println("Sample Rate: "+aif.getSampleRate());
		
		AudioSystem.write(clip, AudioFileFormat.Type.WAVE, fileOutpu);
		
		fileOutpu.close();
		file = null;
	}
	
	public static void deleteBufferFile()
	{
		File file = new File(TamilTTSConstants.OUTPUT_VOICE_PATH);	
		if(!file.exists()){
			return;
		}
		
		file = new File(TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.OUTPUT_BUFFER_FILE_NAME);
		file.delete();
	}
	
	public static void mergeFilestest()
	{
		String path = "C:\\Users\\Sureshkumar\\Documents\\work\\outputNormalized\\";
		AudioInputStream clip1 = null;
		try
		{
			for(int i = 0; ;i++)
			{
				String filePath = path+i+".wav";
				File file = new File(filePath);
				
				if(!file.exists())
				{
					break;
				}
				if (clip1 == null) 
				{
					clip1 = AudioSystem.getAudioInputStream(new File(filePath));
					continue;
				}
				
				AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(filePath));
				AudioInputStream appendedFiles = new AudioInputStream(new SequenceInputStream(clip1, clip2),
						clip1.getFormat(), clip1.getFrameLength() + clip2.getFrameLength());
				clip1 = appendedFiles;
			}
			
			File file = new File(TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.OUTPUT_VOICE_FILE_NAME);
			FileOutputStream fileOutpu = new FileOutputStream(file, false); 
			AudioSystem.write(clip1, AudioFileFormat.Type.WAVE, fileOutpu);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
		
	public static void adjustAudio()
	{
		try {
			
			String path = "C:\\Users\\Sureshkumar\\Documents\\work\\silenceTrimTest\\";
			
			try
			{
				for(int i = 0; ;i++)
				{
					String fileName = i+".wav";
					String inputPath = path+fileName;
					File file = new File(inputPath);
					
					if(!file.exists())
					{
						break;
					}
					//AdjustDurationandSound.openFile(inputPath, true, fileName); 
				}
			}
			catch(Exception exp)
			{
				exp.printStackTrace();
			}
			
			
		}
		catch(Throwable e) {
			System.err.println("\n\nERROR: " + e.getMessage() + "\n");
			e.printStackTrace(System.err);
		}
	}
	
	public static int forwardSilenceRemoval(String path, int startMilliSecond) throws Exception
	{
		
		AudioInputStream  ais = AudioSystem.getAudioInputStream(new File(path));
		WaveFile wav = new WaveFile(ais);
		int i = (int) getFrameLength(startMilliSecond, ais);
		int time = startMilliSecond;
		try
		{
			//for (int i =  (int) wav.getFramesCount() - 5; i >= 0 ; i-=16)
			for (; i <= (int) wav.getFramesCount() - 5 ; i+=16)
			{
			    double amplitude = wav.getSampleInt(i);

			    if(amplitude > 60000) {
			    	continue;
			    }
			    
			    if(amplitude > 200)
			    {
			    	 time = getMilliSecond(i, ais);
			    	 if((time - startMilliSecond) > 10)
			    	 {
			    		// System.out.println(time - startMilliSecond);
			    	 }
			    	 /*//ais = optimalTrim(ais, 0, time);
			    	 ais = forwardTrim(ais,time);*/
			    	 break;
			    }
			}
		}
		finally
		{
			ais.close();
		}
		return time;
		
	}
	
	public static int backwardSilenceRemoval(String path, int endMilliSecond) throws Exception
	{
		
		AudioInputStream  ais = AudioSystem.getAudioInputStream(new File(path));
		WaveFile wav = new WaveFile(ais);
		int i = (int) getFrameLength(endMilliSecond, ais);
		int time = endMilliSecond;
		try
		{
			//for (int i =  (int) wav.getFramesCount() - 5; i >= 0 ; i-=16)
			for (; i >= 0 ; i-=16)
			{
			    double amplitude = wav.getSampleInt(i);

			    if(amplitude > 60000) {
			    	continue;
			    }
			    
			    if(amplitude > 500)
			    {
			    	 time = getMilliSecond(i, ais);
			    	 if((endMilliSecond - time) > 10)
			    	 {
			    		// System.out.println(endMilliSecond - time);
			    	 }
			    	 /*//ais = optimalTrim(ais, 0, time);
			    	 ais = forwardTrim(ais,time);*/
			    	 break;
			    }
			}
		}
		finally
		{
			ais.close();
		}
		return time;
		
	}
	
	public static int getMilliSecond(long frameLength, AudioInputStream clip) {
		float milliSecond = (frameLength * 1000) / clip.getFormat().getFrameRate();
		
		return Math.round(milliSecond);
	}
	
	public static AudioInputStream introduceSpace(String pauseType)
	{
		long pause = 0;
		if(pauseType.equalsIgnoreCase("SPACE"))
		{
			pause = TamilTTSConstants.SPACE;
		}
		else if(pauseType.equalsIgnoreCase("FULL_STOP"))
		{
			pause = TamilTTSConstants.FULL_STOP;
		}
			 
		String fileName = TamilTTSConstants.VOICE_PATH + TamilTTSConstants.SYMBOL + TamilTTSConstants.SILENT;
		AudioInputStream clip2 = null;
		long frameLength = 0;
		try
		{
			clip2 = AudioSystem.getAudioInputStream(new File(fileName));
			frameLength = getFrameLength(pause, clip2);
			
			Field f = clip2.getClass().getDeclaredField("frameLength"); // NoSuchFieldException
			f.setAccessible(true);
			f.setLong(clip2, frameLength);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return clip2;
	}
	
	public static long getFrameLength(long milliSecond, AudioInputStream clip) {
		long frameLength = (long) ((milliSecond * clip.getFormat().getFrameRate()) / 1000);
		return frameLength;
	}

	public static long calculateByte(AudioInputStream clip, long milliSecond)
	{
		long bitsPerSecond = (long) (clip.getFormat().getFrameRate() * 16 * clip.getFormat().getChannels());
		long bytesPerSecond = bitsPerSecond / 8;
		
		return ((bytesPerSecond / 1000) * milliSecond);
	}
	
	public static int calculatePosition(AudioInputStream clip, long milliSecond)
	{
		float bitsPerSecond = (clip.getFormat().getFrameRate() * 8 * clip.getFormat().getChannels());
		float bytesPerSecond = bitsPerSecond / 8;
		return Math.round(bytesPerSecond * milliSecond / 1000);
	}
	
	/*public static void main(String args[])
	{
		try {
			AudioInputStream clip = AudioSystem.getAudioInputStream(new File("C:\\Users\\Sureshkumar\\Documents\\work\\VoiceWork\\AudacityRecorded\\1852-1921.wav"));
			System.out.println(calculateByte(clip, 512000));
			//clip = optimalTrim(clip, 2000, 3000);
			//play(clip);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}*/
	
	public static void play(AudioInputStream ais)
	{
		try 
		{
			if (ais == null) {
				return;
			}
						
			/*Field f = aif.getClass().getDeclaredField("sampleRate"); // NoSuchFieldException
			f.setAccessible(true);
			f.setLong(aif, 16500);*/
			
			Clip synthesizedVoice = AudioSystem.getClip(null);

			synthesizedVoice.open(ais);

			synthesizedVoice.start();
			

			while (!synthesizedVoice.isRunning())
				Thread.sleep(10);
			while (synthesizedVoice.isRunning())
				Thread.sleep(10);

			synthesizedVoice.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static AudioInputStream forwardTrim(AudioInputStream clip, long startMilliSecond) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		
		clip.skip(calculateByte(clip, startMilliSecond));	
		return clip;
	}
	
	public static AudioInputStream optimalTrim(AudioInputStream clip, long startMilliSecond, long endMilliSecond) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		//String path = "C:\\Users\\Sureshkumar\\Documents\\work\\silenceTrimTest\\";
		//path = path + counter+".wav";
		//counter++;
		clip.skip(calculateByte(clip, startMilliSecond));
		
		Field f = clip.getClass().getDeclaredField("frameLength"); // NoSuchFieldException
		f.setAccessible(true);
		f.setLong(clip, getFrameLength(endMilliSecond, clip));
		
		//AudioSystem.write(clip,AudioFileFormat.Type.WAVE, new File(path));
		
		return clip;
	}
	
	
	
	public static long getTimeInMilliSecond(AudioInputStream clip) {
		long durationInMillis = (long) (1000 * clip.getFrameLength() / clip.getFormat().getFrameRate());
		return durationInMillis;
	}
}
