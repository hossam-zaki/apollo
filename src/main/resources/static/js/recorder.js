//webkitURL is deprecated but nevertheless
URL = window.URL || window.webkitURL;

var gumStream; //stream from getUserMedia()
var rec; //Recorder.js object
var input; //MediaStreamAudioSourceNode we'll be recording

// shim for AudioContext when it's not avb.
var AudioContext = window.AudioContext || window.webkitAudioContext;
var audioContext //audio context to help us record

var recordButton = document.getElementById("recordButton");
var stopButton = document.getElementById("stopButton");
var pauseButton = document.getElementById("pauseButton");

//add events to those 2 buttons
recordButton.addEventListener("click", startRecording);
stopButton.addEventListener("click", stopRecording);
pauseButton.addEventListener("click", pauseRecording);

function startRecording() {

    /*
    	Simple constraints object, for more advanced audio features see
    	https://addpipe.com/blog/audio-constraints-getusermedia/
    */

    var constraints = { audio: true, video: false }

    /*
    	Disable the record button until we get a success or fail from getUserMedia()
	*/

    recordButton.disabled = true;
    stopButton.disabled = false;
    pauseButton.disabled = false

    /*
    	We're using the standard promise based getUserMedia()
    	https://developer.mozilla.org/en-US/docs/Web/API/MediaDevices/getUserMedia
	*/

    navigator.mediaDevices.getUserMedia(constraints).then(function(stream) {
        console.log("getUserMedia() success, stream created, initializing Recorder.js ...");

        /*
        	create an audio context after getUserMedia is called
        	sampleRate might change after getUserMedia is called, like it does on macOS when recording through AirPods
        	the sampleRate defaults to the one set in your OS for your playback device
        */
        audioContext = new AudioContext();

        //update the format
        document.getElementById("formats").innerHTML = "Format: 1 channel pcm @ " + audioContext.sampleRate / 1000 + "kHz"

        /*  assign to gumStream for later use  */
        gumStream = stream;

        /* use the stream */
        input = audioContext.createMediaStreamSource(stream);

        /*
        	Create the Recorder object and configure to record mono sound (1 channel)
        	Recording 2 channels  will double the file size
        */
        rec = new Recorder(input, { numChannels: 1 })

        //start the recording process
        rec.record()


    }).catch(function(err) {
        //enable the record button if getUserMedia() fails
        recordButton.disabled = false;
        stopButton.disabled = true;
        pauseButton.disabled = true
    });
}

function pauseRecording() {
    if (rec.recording) {
        //pause
        rec.stop();
        pauseButton.innerHTML = "Resume &nbsp<i class='fas fa-microphone'>";
    } else {
        //resume
        rec.record()
        pauseButton.innerHTML = "Pause &nbsp<i class='fas fa-pause'>";

    }
}

function stopRecording() {

    //disable the stop button, enable the record too allow for new recordings
    stopButton.disabled = true;
    recordButton.disabled = false;
    pauseButton.disabled = true;

    //reset button just in case the recording is stopped while paused
    pauseButton.innerHTML = "Pause &nbsp<i class='fas fa-pause'>";

    //tell the recorder to stop the recording
    rec.stop();

    //stop microphone access
    gumStream.getAudioTracks()[0].stop();

    //create the wav blob and pass it on to createDownloadLink
    rec.exportWAV(createDownloadLink);
}

function createDownloadLink(blob) {

    var url = URL.createObjectURL(blob);
    var au = document.createElement('audio');
    var li = document.createElement('li');
    var link = document.createElement('a');

    //name of .wav file to use during upload and download (without extendion)
    var tzoffset = (new Date()).getTimezoneOffset() * 60000; //offset in milliseconds
    var str = window.location.href;
    var list = [];
    var link1 = "";
    boolean = false;
    for (var i = 0; i < str.length; i++) {
        if (str.charAt(i) == ':') {
            boolean = true;
        }
        if (str.charAt(i) == '/' && boolean) {
            list.push(link1);
            link1 = "";
            boolean = false;
        }
        if (boolean) {
            link1 += str.charAt(i);
        }
    }
    var filename = (new Date(Date.now() - tzoffset)).toISOString().slice(0, -1) + list[list.length - 1].slice(1, );

    //add controls to the <audio> element
    au.controls = true;
    au.src = url;

    //save to disk link
    link.href = url;
    link.download = filename + ".wav"; //download forces the browser to donwload the file using the  filename

    //add the new audio element to li
    li.appendChild(au);

    //add the save to disk link to li
    li.appendChild(link);

    //upload link
    var upload = document.createElement('a');
    upload.href = "#";
    upload.innerHTML = "register visit using this recording.";
    upload.addEventListener("click", function(event) {
        var xhr = new XMLHttpRequest();
        var fd = new FormData();
        var typeMeeting = document.getElementById("VisitType").value;
        fd.append("audio_data", blob, filename);
        fd.append("typeMeeting", blob, typeMeeting);
        var req = jQuery.ajax({
            url: '/send/' + list[list.length - 2] + '/' + list[list.length - 1],
            method: 'POST',
            data: fd, // sends fields with filename mimetype etc
            processData: false, // don't let jquery process the data
            contentType: false // let xhr set the content type
        });
        // jQuery is promise A++ compatible and is the todays norms of doing things
        req.then(function(response) {
            window.location.href = "../../patientBase/" + list[list.length - 2] + "/" + list[list.length - 1];
        }, function(xhr) {
            console.error('failed to fetch xhr', xhr)
        })
    })
    li.appendChild(document.createTextNode(" ")) //add a space in between
    li.appendChild(upload) //add the upload link to li

    //add the li element to the ol
    recordingsList.appendChild(li);
}