package speechToText;

import java.io.IOException;

public class RunDeepSpeech {

	//python3 mic_vad_streaming.py   -t trie --file 8455-210777-0068.wav
	public static void transcribe(String filename) {
	       Process process;
		try {
			StringBuilder str = new StringBuilder();
			str.append("src/main/java/speechToText/mic_vad_streaming.py ");
			str.append("--model src/main/java/speechToText/output_graph.pbmm ");
			str.append("--lm src/main/java/speechToText/lm.binary ");
			str.append("-t src/main/java/speechToText/trie ");
			str.append("--file ");
			str.append(filename);
			process = Runtime.getRuntime().exec(str.toString());
		} catch (IOException e) {
			System.err.println("ERROR: Error with executing the script");
		}
	}
}
