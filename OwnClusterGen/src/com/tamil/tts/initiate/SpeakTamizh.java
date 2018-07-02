package com.tamil.tts.initiate;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tamil.tts.builder.InitializeObjects;
import com.tamil.tts.cluster.ClusterSearch;
import com.tamil.tts.cluster.FrontClusterSearch;
import com.tamil.tts.constants.TamilTTSConstants;
import com.tamil.tts.dsp.filter.WavFilterFisher;
import com.tamil.tts.model.ResultModel;
import com.tamil.tts.utils.SynthesizeSpeech;
import com.tamil.tts.utils.TamilTTSUtils;



public class SpeakTamizh extends JFrame implements DocumentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JScrollPane jScrollPane1;
	private JTextArea textArea;
	private JButton startButton;
	private JButton stopButton;
	private JButton pauseButton;
	private JButton resumeButton;
	private static JSlider tempoSlider;
	private static JSlider gainSlider;
	static List<ResultModel> rlist = new ArrayList<ResultModel>();
	static ResultModel rmodel = null;
	static Map<String, String> map = new HashMap<String, String>();
	//Thread speakThread = null;

	private static enum Mode {
		INSERT, COMPLETION
	};

	private final List<String> words;
	private Mode mode = Mode.INSERT;

	public static void main(String[] args) throws InvocationTargetException, InterruptedException, SecurityException, IOException
	{
		System.setProperty("file.encoding", "UTF-8");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new SpeakTamizh().setVisible(true);
			}
		});
	}

	public SpeakTamizh() {
		super("தமிழ் பேசி");
		initComponents();
		Font font = new Font("Latha", Font.PLAIN, 13);

		textArea.getDocument().addDocumentListener(this);
		textArea.setFont(font);
		textArea.setText("அம்மா");
		words = new ArrayList<String>(5);
		words.add("spark");
		words.add("special");
		words.add("spectacles");
		words.add("spectacular");
		words.add("swing");
		this.setSize(800, 600);

	}

	private void initComponents() {
		Font font = new Font("Latha", Font.PLAIN, 13);

		jLabel1 = new JLabel("வாக்கியத்தை உள்ளிடுக அல்லது பதியுங்கள்..!");
		jLabel1.setFont(font);
		
		JLabel tempoLabel = new JLabel("படிக்கும் வேகத்தை மாற்று");
		tempoLabel.setFont(font);
		JLabel gainLabel = new JLabel("ஒலி அளவை மாற்று");
		gainLabel.setFont(font);

		textArea = new JTextArea();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		textArea.setColumns(20);
		textArea.setLineWrap(true);
		textArea.setRows(5);
		textArea.setWrapStyleWord(true);
		
		startButton = new JButton("படிக்கவும்");
		startButton.setVisible(true);
		startButton.setFont(font);
		startButton.setSize(100, 30);
		startButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//if(speakThread == null || !speakThread.isAlive())
				{
					String word = textArea.getSelectedText();
					if(word != null && word.length() > 0)
					{
						speakTamizh(word);
					}
					else
					{
						speakTamizh(textArea.getText());
					}
				}
			}			
		});
		
		stopButton = new JButton("நிறுத்தவும்");
		stopButton.setVisible(true);
		stopButton.setFont(font);
		stopButton.setSize(100, 30);
		stopButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}			
		});
		
		pauseButton = new JButton("தற்காலிகமாக நிறுத்தவும்");
		pauseButton.setVisible(true);
		pauseButton.setFont(font);
		pauseButton.setSize(100, 30);
		pauseButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}			
		});
		
		resumeButton = new JButton("நிறுத்தியதிலிருந்து தொடங்கு");
		resumeButton.setVisible(true);
		resumeButton.setFont(font);
		resumeButton.setSize(100, 30);
		resumeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}			
		});
		
		tempoSlider = new JSlider(20, 200);
		tempoSlider.setName("படிக்கும் வேகத்தை மாற்று");
		tempoSlider.setValue(100);
		tempoSlider.setPaintLabels(true);
		//tempoSlider.addChangeListener(parameterSettingChangedListener);
		
		gainSlider = new JSlider(0,30);
		gainSlider.setName("ஒலி அளவை மாற்று");
		gainSlider.setValue(TamilTTSConstants.INITIAL_GAIN);
		gainSlider.setPaintLabels(true);
		gainSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
			}
		});
		
		jScrollPane1 = new JScrollPane(textArea);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		// Create a parallel group for the horizontal axis
		ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		// Create a sequential and a parallel groups
		SequentialGroup h1 = layout.createSequentialGroup();
		ParallelGroup h2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
		// Add a scroll panel and a label to the parallel group h2
		h2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(startButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(stopButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(pauseButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(resumeButton, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(gainLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(gainSlider, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(tempoLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
		h2.addComponent(tempoSlider, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);


		// Add a container gap to the sequential group h1
		h1.addContainerGap();
		// Add the group h2 to the group h1
		h1.addGroup(h2);
		h1.addContainerGap();
		// Add the group h1 to hGroup
		hGroup.addGroup(Alignment.TRAILING, h1);
		// Create the horizontal group
		layout.setHorizontalGroup(hGroup);

		// Create a parallel group for the vertical axis
		ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		// Create a sequential group
		SequentialGroup v1 = layout.createSequentialGroup();
		// Add a container gap to the sequential group v1
		v1.addContainerGap();
		// Add a label to the sequential group v1
		v1.addComponent(jLabel1);
		v1.addComponent(startButton);
		v1.addComponent(stopButton);
		v1.addComponent(pauseButton);
		v1.addComponent(resumeButton);
		v1.addComponent(gainLabel);
		v1.addComponent(gainSlider);
		v1.addComponent(tempoLabel);
		v1.addComponent(tempoSlider);
		
		v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
		// Add scroll panel to the sequential group v1
		v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
		v1.addContainerGap();
		// Add the group v1 to vGroup
		vGroup.addGroup(v1);
		// Create the vertical group
		layout.setVerticalGroup(vGroup);
		pack();

	}

	public void speakTamizh(String paragraph) 
	{
		try
		{
			InitializeObjects.initializeTimeDataObjects();
			String currentLine = "";
			String[] wordArray = paragraph.split("\n");
			
			for(int i = 0; i < wordArray.length; i++)
			{
				currentLine = wordArray[i];
				try
				{
					synthesizeSpeech(currentLine);
				}
				catch(Exception exp)
				{
					System.out.println(currentLine);
					exp.printStackTrace();
				}
			}
			
			if(rlist.size() > 0)
			{
				SynthesizeSpeech.speak(rlist);
				WavFilterFisher.lowPassFilter();
			}
			
			play();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	public static void synthesizeSpeech(String line)
	{
		
		String[] words = line.split(" ");
		String word = null;
		List<String> letterList = null;
		boolean periodFlag = false;
		for(int ix =0; ix < words.length; ix++)
		{
			periodFlag = false;
			//System.out.println(words[ix]);
			word = words[ix];
			word = word.trim();
			if(!(word.trim().length() > 0))
			{
				continue;
			}
			if(word.contains(".") || word.contains("?")  || word.contains("!"))
			{
				periodFlag = true;				
			}
			
		    if(TamilTTSUtils.checkContains(TamilTTSConstants.ENGLISH_LETTERS, word) || TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், word))
		    {
			   continue;
		    }
			word = word.replace(".", "");
			word = word.replace(",", "");
			word = word.replace("?", "");
			word = word.replace("!", "");
			word = word.replace("\'", "");
			word = word.replace("\"", "");
			word = word.replace(".", "");
			word = word.replace("”", "");
			word = word.replace("“", "");
			word = word.replace("*", "");
			
			word = word.replace("‘", "");
			word = word.replace("’", "");
			word = word.replace("-", "");
			word = word.replace("\\", "");
			word = word.replace("/", "");
			word = word.replace(":", "");
			word = word.replace("…", "");
			word = word.replace("(", "");
			word = word.replace(")", "");
			word = word.replace(";", "");
			word = word.replace("–", "");
			
			if(word.equalsIgnoreCase(""))
			{
				continue;
			}
			letterList = TamilTTSUtils.getLetterList(word);
			letterList = applyGrammer(letterList);
			/*int bodyCounter = 0;
			for(int x = 0; x < letterList.size(); x++)
			{
				if(letterList.get(x).contains("்"))
				{
					//System.out.println("working");
					bodyCounter++;
				}
			}*/
			
			/*if(bodyCounter >= (letterList.size() / 2))
			{
				//System.out.println("Found: "+word);
				letterList = TamilTTSUtils.getNormalizedLetterList(letterList);
			}
			else
			{
				//System.out.println("Skipping: "+ word);
			}*/
			int verifyIndex = 0;
			List<String> bufferList = null;
			
			
			for(int i = letterList.size() ; i >= verifyIndex; i--)
			{
				String wordBuffer = TamilTTSUtils.getString(verifyIndex, i, letterList);
				bufferList = TamilTTSUtils.getLetterList(wordBuffer);
				
				
				//System.out.println(wordBuffer);
				
				/*if(bufferList.size() == 1 && !TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, wordBuffer) &&  !TamilTTSUtils.checkContains(TamilTTSConstants.NORTH_SOUND, wordBuffer)
						&& !TamilTTSUtils.checkContains(TamilTTSConstants.எண்கள், wordBuffer))
				{
					System.out.println("Size one");
					rmodel = new ResultModel();
					rmodel.setFileName(wordBuffer.trim());
					rmodel.setCompleteSound(true);
					rlist.add(rmodel);
					rmodel = null;
					
					if(i == letterList.size())
					{
						break; //Full word found
					}
					
					else
					{
						verifyIndex = i;
						i = letterList.size() + 1;
					}
					
				}
				else */
				{
					/*if(bufferList.size() <= 3 && !TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, wordBuffer))
					{
						System.out.println("**********************: "+ wordBuffer);
						rmodel = ClusterSearch.searchCluster(wordBuffer, InitializeObjects.twoThreeLetterModelList, false);
						//System.out.println("****************************************************************Successfully found...!");
					}*/
					
					if(rmodel == null)
					{
						/*if(bufferList.size() == 1  && !TamilTTSUtils.checkContains(TamilTTSConstants.NORTH_SOUND, wordBuffer))
						{
							rmodel = ClusterSearch.searchCluster(wordBuffer, InitializeObjects.twoThreeLetterModelList, false);
							System.out.println(wordBuffer);
						}
						else*/
						{
							

						}
						
						if(bufferList.size() <= 3)
						{
							rmodel = FrontClusterSearch.searchCluster(wordBuffer, InitializeObjects.clusterModelList, false);
							if(rmodel != null)
							{
								System.out.println("	 Front Found: "+ wordBuffer);
							}
						}
						if(rmodel == null)
						{
							rmodel = ClusterSearch.searchCluster(wordBuffer, InitializeObjects.clusterModelList, false);
							if(rmodel != null)
							{
								System.out.println("	Found: "+ wordBuffer);
							}
						}
					}
					
					/*if(rmodel == null)
					{
						
						if(bufferList.size() <= 2 && !TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, wordBuffer))
						{
							
							if(map.get(wordBuffer) == null)
							{
								//if(!TamilTTSUtils.checkContains(TamilTTSConstants.BODY_WORD, bufferList.get(0)))
								{
									//if(TamilTTSUtils.checkContains(TamilTTSConstants.NORTH_SOUND, wordBuffer))
									{
										map.put(wordBuffer, wordBuffer);
										//System.out.println(word);
										//System.out.println(wordBuffer);
									}
								}
								
							}
							
						}
						
					}
					*/
					if(rmodel != null)
					{
						//System.out.println("	Found: "+ wordBuffer);
						rlist.add(rmodel);
						rmodel = null;
						
						if(i == letterList.size())
						{
							break; //Full word found
						}
						
						else
						{
							verifyIndex = i;
							i = letterList.size() + 1;
						}
					}
					else if(i == verifyIndex)
					{
						verifyIndex++;
						if(verifyIndex >= letterList.size())
						{
							break;
						}
						i = letterList.size() + 1;
					}
					
				}
			}
			
			if(periodFlag)
			{
				rmodel = null;
				rmodel = new ResultModel();
				rmodel.setFileName("FULL_STOP");
				rmodel.setAudioPath(TamilTTSConstants.AUDIO_PATH);
				rlist.add(rmodel);
				periodFlag = false;
			}
			else
			{
				rmodel = null;
				rmodel = new ResultModel();
				rmodel.setFileName("SPACE");
				rmodel.setAudioPath(TamilTTSConstants.AUDIO_PATH);
				rlist.add(rmodel);
			}
			
			
			rmodel = null;
		}
		
		/*if(rlist.size() > 0)
		{
			SynthesizeSpeech.speak(rlist);
		}*/
		
		
	}
	
	public static List<String> applyGrammer(List<String> letterList)
	{
		List<String> grammerList = new ArrayList<String>();
		for(int i = 0; i < letterList.size(); i++)
		{
			String letter = letterList.get(i);
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("ட்") && letterList.get(i+1).contains("ட"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("ற்") && letterList.get(i+1).contains("ற"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("த்") && letterList.get(i+1).contains("த"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("ச்") && letterList.get(i+1).contains("ச"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			if(i < letterList.size() - 1 && letter.equalsIgnoreCase("க்") && letterList.get(i+1).contains("க"))
			{
				grammerList.add(letterList.get(i)+letterList.get(i+1));
				i++;
				continue;
			}
			
			grammerList.add(letter);
		}
		
		return grammerList;
	}
	
	public static void play()
	{
		try 
		{
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(TamilTTSConstants.OUTPUT_VOICE_PATH+TamilTTSConstants.FINAL_OUTPUT_VOICE_FILE_NAME));
			if (ais == null) {
				return;
			}
						
			AudioFormat aif = ais.getFormat();
			System.out.println("Sample Rate: "+aif.getSampleRate());
			
			Clip synthesizedVoice = AudioSystem.getClip();

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
	
	@Override
	public void insertUpdate(DocumentEvent ev) {
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
	}


	public static JSlider getTempoSlider() {
		return tempoSlider;
	}

	public static void setTempoSlider(JSlider tempoSlider) {
		SpeakTamizh.tempoSlider = tempoSlider;
	}

	public static JSlider getGainSlider() {
		return gainSlider;
	}

	public static void setGainSlider(JSlider gainSlider) {
		SpeakTamizh.gainSlider = gainSlider;
	}
	
}