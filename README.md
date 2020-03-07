# cs0320 Term Project 2020

**Team Members:** Matan Gans, Matteo Lunghi, Mohammad Abouelafia, Hossam Zaki

**Team Strengths and Weaknesses:** \
Matan Gans\
	Strengths: 
     Algorithms, 
     Data processing and querying, 
     Good at explaining code and logic\
	Weaknesses:
     Prone to lack of patience under stress

Matteo Lunghi\
	Strengths: 
    Leadership, 
    Software design, 
    Algorithms and data structures\
	Weaknesses:
    Sometimes rushes into writing code too quickly.

Hossam Zaki\
	Strengths: 
    Collaboration 
    Strong work ethic
    Front-end design
    Well informed on biology and medicine topics \
	Weaknesses:
    Easily frustrated

Mohamad Abouelafia\
	Strengths: 
    Friendly and works well with others, Organized and likes planning, Good visualizer, Experience in imaging and computer vision\
	Weaknesses:
    Gets annoyed easily


**Project Idea(s):** 
### Idea 1: Futurosis (Currently Preferred Idea):
Overview: This app will allow users to pinpoint developing medical conditions and offer likely diagnoses based on the inputted symptoms. At the moment, when we don’t feel well and aren’t ready to make the trip to the doctor’s office just yet, our first step is the internet. However, a Google search is likely to freak you out, sending you to a WebMD result that can take an upset stomach and turn it into appendicitis, or create arthritis from a sore muscle. DiagnoseMe will allow a user to select a specific body part from our GUI, and then input specific symptoms that correspond to that body part. We will make use of big data to calculate likelihood of multiple diagnoses and give the user several options of what their symptoms may indicate, as well as a ranking of how likely it is. By using data and statistics, we hope to help users freak out a little less before going to the doctor when there is really not much to worry about.\
Requirements: GUI (makes it easier for users to specify what symptoms they have), database with lots of data\
Results from users: we asked around and our personal experiences were confirmed - WebMD usually freaks people out unnecessarily. One example story we heard is somebody had pain below the belly button, and WebMD only gave two options: the patient had either a kidney stone or appendicitis. This person went to sleep and was fine the next day.\
Challenges: finding good data, deciding how to rank factors and decide on likelihood of diagnoses, making an interactive GUI

TA Approval: Approved pending TA approval, no core algorithm yet besides "using big data". Also I worry that after using "big data" you may still get a result that freaks people out. There is a reason that more dangerous suggestions are presented first: you don't want to be liable if something is actually wrong


### Idea 2: MyRoutine (Title Tentative):
Managing your time is hard, especially when you have to juggle courses, clubs, and (hopefully) food and sleep. It’s easy enough to plug time blocks into your schedule when you know how long something will take (i.e. a lecture or a club meeting), but it gets trickier when you need to quantify the time you need for homework, studying for exams, long term projects, etc. We want to develop a program that will help users feel more productive in allocating their free time effectively.\
Requirements: nice front-end interactive schedule design, algorithm that takes work out of the user’s hands and develops a schedule for them (don’t want to make the user work too much)\
Results from users: we asked around if people would find this useful, and the the responses indicated that there is a need for this project - users feel unproductive and would be more secure in their productivity if they actually had a rigid schedule, but don’t actually end up making it on their own\
Challenges: Algorithm to fill free blocks of time with our suggestions for when a user should work on other tasks in the to-do list according to how much time they specify each should take (some sort of malloc-type functionality), interactive GUI with options for the user to play around with suggestions and make an optimal schedule.

TA Approval: Approved - good idea, just want to make sure that students can use it the way they want (specific preferences)

### Idea 3: Apollo (Title Tentative):

Overview: Doctors say that one of the activities on which they waste the most time on is working with the Electronic Health Record (EHR). For reference, see this article: https://www.acponline.org/system/files/documents/about_acp/chapters/ga/annals_of_medicine.pdf. This is a pressing issue, as doctors should focus their energy on diagnosing and treating their patients instead of sorting through and taking notes. Doctors also face higher risks of burnout due to the time they feel is wasted on the EHR. We propose to investigate exactly what doctors waste time on, and propose a solution to the issue. Note: we met with Professor Nelson earlier today to discuss our idea, and we were told to give you a heads up to Slack him about our project. Our project will contain a heavy research oriented component, where we study the challenges that doctors face in maintaining accurate records for their patients. Below, we propose an initial prototype, but note that our project will be tweaked by our research results.

Overview of our prototype:

The core of our application is a new interface for doctors to take notes during patient visits: our program will allow doctors to start a recording at the beginning of the visit, and our program will produce a transcript of the meeting.
The transcript will also be parsed and automatically fill in some section (the ones that our research finds to be possible and useful) of the EHR. Our parser will need to identify keywords to find the information needed to be filled into the form.
Doctors will be able to log into our portal and store these transcripts and EHR forms on our web app, which will organize information for each patient based on appointment date
We also believe that it is hard for doctors to recall exact information from past visits, especially if they want to remember information from a visit that occured a long time ago. To tackle this, we want to allow them to query all transcripts of their meetings, such that we can point them to the correct meeting and information they need. To do this efficiently, we will implement the Knuth-Morris-Prath algorithm for linear string search.


**Note:** You do not need to resubmit ideas for TA approval.

**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 13)_

**4-Way Checkpoint:** _(Schedule for on or before April 23)_

**Adversary Checkpoint:** _(Schedule for on or before April 29 once you are assigned an adversary TA)_

## How to Build and Run
_A necessary part of any README!_
