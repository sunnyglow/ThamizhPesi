package com.tamil.tts.dsp.filter;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.tamil.tts.constants.TamilTTSConstants;
import com.tamil.tts.dsp.filter.filter.FilterCharacteristicsType;
import com.tamil.tts.dsp.filter.filter.FilterPassType;
import com.tamil.tts.dsp.filter.filter.IirFilterAudioInputStreamFisher;




public class WavFilterFisher {

	public static void lowPassFilter() throws Exception 
	{
		String inputFileName = TamilTTSConstants.OUTPUT_VOICE_PATH + TamilTTSConstants.OUTPUT_VOICE_FILE_NAME;
		FilterPassType filterPassType = FilterPassType.valueOf(TamilTTSConstants.FILTER_TYPE);
		FilterCharacteristicsType filterCharacteristicsType = FilterCharacteristicsType.valueOf("butterworth");
		int filterOrder = Integer.valueOf("3");
		double ripple = Double.valueOf("-0.5");
		double fcf1 = Double.valueOf("300");
		double fcf2 = Double.valueOf("4000");
		String outputFileName = TamilTTSConstants.OUTPUT_VOICE_PATH + TamilTTSConstants.FINAL_OUTPUT_VOICE_FILE_NAME;

		filterWavFile(inputFileName, filterPassType, filterCharacteristicsType, filterOrder, ripple, fcf1, fcf2,
				outputFileName);
	}

	private static void filterWavFile(String inputFileName, FilterPassType filterPassType,
			FilterCharacteristicsType filterCharacteristicsType, int filterOrder, double ripple, double fcf1,
			double fcf2, String outputFileName) throws Exception 
	{
		File input = new File(inputFileName);
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(input);
		AudioInputStream filterStream = IirFilterAudioInputStreamFisher.getAudioInputStream(inputStream, filterPassType,
				filterCharacteristicsType, filterOrder, ripple, fcf1, fcf2);
		AudioSystem.write(filterStream, AudioFileFormat.Type.WAVE, new File(outputFileName));
		inputStream.close();
		//input.deleteOnExit();
		
	}
	
	
	
	public static AudioInputStream lowPassFilterStream(AudioInputStream ais) throws Exception 
	{
		FilterPassType filterPassType = FilterPassType.valueOf(TamilTTSConstants.FILTER_TYPE);
		FilterCharacteristicsType filterCharacteristicsType = FilterCharacteristicsType.valueOf("butterworth");
		int filterOrder = Integer.valueOf("4");
		double ripple = Double.valueOf("-0.5");
		double fcf1 = Double.valueOf("1000");
		double fcf2 = Double.valueOf("4000");
		AudioInputStream filterStream = IirFilterAudioInputStreamFisher.getAudioInputStream(ais, filterPassType,
				filterCharacteristicsType, filterOrder, ripple, fcf1, fcf2);
		return filterStream;
	}
}
