package speechToText;

import java.io.IOException;

public class RunDeepSpeech {

	//python3 mic_vad_streaming.py   -t trie --file 8455-210777-0068.wav
	public static void transcribe(String filename) {
	       Process process;
		try {
			StringBuilder str = new StringBuilder("--file ");
			str.append(filename);
			
			process = Runtime.getRuntime().exec("src/main/java/speechToText/mic_vad_streaming.py " +
					"--model src/main/java/speechToText/output_graph.pbmm "+ "--lm src/main/java/speechToText/lm.binary "+
					"-t src/main/java/speechToText/trie "+ str.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
