#!/Library/Frameworks/Python.framework/Versions/3.7/bin/python3

import speech_recognition as sr

# obtain path to "english.wav" in the same folder as this script
import os
from os import path
import sys
import argparse
from argparse import ArgumentParser

def runSpeechToText(audioFile):
    AUDIO_FILE = path.join(path.dirname(path.realpath(__file__)), "../../../../" + audioFile)
    # AUDIO_FILE = path.join(path.dirname(path.realpath(__file__)), "french.aiff")
    # AUDIO_FILE = path.join(path.dirname(path.realpath(__file__)), "chinese.flac")

    # use the audio file as the audio source
    r = sr.Recognizer()
    with sr.AudioFile(AUDIO_FILE) as source:
        audio = r.record(source)  # read the entire audio file

    # recognize speech using Sphinx
    # try:
    #     print("Sphinx thinks you said " + r.recognize_sphinx(audio))
    # except sr.UnknownValueError:
    #     print("Sphinx could not understand audio")
    # except sr.RequestError as e:
    #     print("Sphinx error; {0}".format(e))

    # recognize speech using Google Speech Recognition
    try:
        # for testing purposes, we're just using the default API key
        # to use another API key, use `r.recognize_google(audio, key="GOOGLE_SPEECH_RECOGNITION_API_KEY")`
        # instead of `r.recognize_google(audio)`
        with open("data/transcripts/test.txt", "w+") as f:
            f.write(r.recognize_google(audio))
            print("Google Speech Recognition thinks you said " + r.recognize_google(audio))
    except sr.UnknownValueError:
        print("Google Speech Recognition could not understand audio")
    except sr.RequestError as e:
        print("Could not request results from Google Speech Recognition service; {0}".format(e))
    os.remove(audioFile[32:-4] + ".txt")
if __name__ == "__main__":
    parser = ArgumentParser()
    parser.add_argument('--audio',type=str,required=True)
    config = parser.parse_args()
    runSpeechToText(config.audio)
