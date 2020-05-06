# README

  

## Apollo

  

#### Table of contents

* [Introduction](#introduction)

* [Building and Running the Program](#building-and-running-the-program)

* [Running Tests](#running-tests)

* [Known Bugs and Checkstyle Errors](#known-bugs-and-checkstyle-errors)

* [Next Steps](#next-steps)

#### Introduction

Welcome! Apollo is a web-based note-taking application for doctors. Our primary inspiration for this project was a New Yorker article entitled “Why Doctors Hate Their Computers,” which brought up a large issue in the healthcare industry. Starting with this article, we as a group sought out to research the problem further, and discovered that the use of electronic health records, which was meant to improve quality of care and access to information, was actually causing doctors to spend endless hours on their computers due to the inefficiency of transcribing notes. The aggravation involved with tedious tasks on their computers is leading doctors to burnout as they question the meaning of their jobs.

With Apollo, we attempt to take steps toward solving this problem, by implementing speech-to-text transcription as well as text summarization to assist doctors in the note-taking process. We believe that our application will have the following effects:

1.  Doctors will spend less time in front of our computers, as visit transcripts are summarized quickly before their eyes. Apollo reduces the extra hassle involved of parsing through notes after a visit and trying to paraphrase heaps of scattered information into the EHR
    
2.  The patient-doctor interaction will be enriched as doctors are not required to type notes heavily during visits. Optimizing the note-taking process will lead to stronger relationships between doctors and their patients, allowing for more spontaneous, personable, and caring conversations. We think that Apollo could be especially utilized in fields like psychiatry, where these humane interactions are of utmost importance
    
3.  And finally, reducing the time doctors are required to spend on their computers will help doctors rediscover their passion, as they can put their efforts back into providing the best healthcare possible for their patients rather than tiring themselves out with mundane tasks involving outdated technology.
    

Technology should not be a burden, especially in the healthcare industry. Our app is meant to enhance the reasons why health records were automated in the first place: quality, safety, and efficiency.


#### Building and Running the Program

To build the program, call `mvn package` from the terminal within the directory, and run the REPL in the terminal using `./run`
where you can type in built-in commands and see program output to the terminal. ***Note that tests will fail if you don't delete the local apollo.sqlite3 database between subsequent builds, so either delete the database or run `mvn -Dmaven.test.skip=true package` from the terminal.
To run the entire web interface in your browser, run with the `--gui` tag.

To install the necessary packages necessary for speech-to-text to work locally, run `pip install -r requirements.txt` in the terminal. Also, make sure the shebang at the top of the speechtotext.py to fit your local Python version. 

Deloyed on Heroku via deploy branch:
Link: https://apollohealth.herokuapp.com/

#### Running Tests

To run unit tests, build the program with `mvn package`. ***Note that tests will fail if you don't delete the local apollo.sqlite3 database between subsequent builds, so either delete the database or run `mvn -Dmaven.test.skip=true package` from the terminal.
To run all of our system tests, run the command `./cs32-test tests/*` from the project directory in the terminal. To run individual system tests, use `./cs32-test tests/<name of test>`

#### Known Bugs and Checkstyle Errors

 None that we know of :)

#### Next Steps
Where do we see this project going? 
We think Apollo can be extended for real-world use by expanding upon our dictionary of categorized symptoms that the symptom recognizes, as well as adding to the accepted transcript phrases that need to be said by doctors, to increase ease of use and help doctors make the best use of our system! 
We also considered scaling up our note-taking capabilities with the aid of Natural Language Processing.
A further extension of this project would be finding a way to connect it to EHR software, so that patient data can be shared between both interfaces.
