# ThamizhPesi is a Pure Java Based Tamil Text to Speech Application

To Setup and Run the application perform below steps

1. Download OwnClusterGen Folder to your local.
2. Open Eclipse IDE and import as an existing java project.
    To know and download eclipse check here, https://www.eclipse.org/ide/
3. Inside OwnClusterGen project you should have two folders  called
    a. AllTTSTimeData
    b. AllTTSVoiceData
  these folders are the Voice and Time data for the project.
4. Copy full path of the folder and configure it inside \OwnClusterGen\src\com\tamil\tts\constants\TamilTTSConstants.java
Below is the sample Configuration.
  String VOICE_PATH = "D:\\TamilTTSWork\\Documents\\work\\tts_voice\\AppData\\AllTTSVoiceData\\";
	String TIME_DATA_PATH ="D:\\TamilTTSWork\\Documents\\work\\tts_voice\\AppData\\AllTTSTimeData";
	String AUDIO_PATH = "D:\\TamilTTSWork\\Documents\\work\\tts_voice\\AppData\\AllTTSVoiceData\\";
5.  In the same TamilTTSConsstants.java file you should configure OutPut folder path.  Sample configuration is given below.
    String OUTPUT_VOICE_PATH = "C:/Users/Sureshkumar/Documents/work";
6. If you are Using Linux OS then you should change all backward double slashes "\\" to forward slash "/" in two java files.
    5.1. \OwnClusterGen\src\com\tamil\tts\constants\TamilTTSConstants.java
    5.2. \OwnClusterGen\src\com\tamil\tts\builder\InitializeObjects.java
  If you are Using windows machine then no need to do any changes.
7. Outside OwnClusterGen folder you should have tamil.txt file.  It is the input file.
   configure full path of that file inside OwnClusterGen\src\com\tamil\tts\initiate\InitiateSpeech.java
   Sample configuration is given below,
   BufferedReader reader = new BufferedReader(new FileReader(new File("D:\\TamilTTSWork\\tamil.txt")));
8. Now, Run the InitiateSpeech.java as a Java Application.  You should see the synthesized Voice output in your Output folder.
