<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <!-Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../css/base.css"> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap" rel="stylesheet">
    	<link rel="stylesheet" href="../../../css/newvisit.css"> 
  </head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/apollo/:${username}">Apollo</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/apollo/:${username}">home</a></li>
        <li><a href=${route}>register visit</a></li>
        <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">profile
          <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/apollo/account-details/:${username}">account information</a></li>
            <li><a href="/apollo">logout</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
<div class="input-group mb-3" style="margin-left: auto; margin-right: auto; width: 28%; margin-top: 6%">
    <div class="input-group-prepend">
      <span class="input-group-text" style="color:black; margin-left: 37%;" id="inputGroup-sizing-default"><strong>Enter Type of Visit</strong></span>
    </div>
    <input type="text" class="form-control" style="text-align:center; font-size: 115%;" placeholder="(i.e General Checkup)" aria-label="Enter Type of Visit (i.e Checkup)" aria-describedby="inputGroup-sizing-default">
</div>
<br>
<br>
<div>
  <h5><strong>Ready to Record Reasons for Visits?</strong> Say "Start reasons"</h5>
  <h5><strong>All done?</strong> Say "Stop reasons"</h5>
  <br>
  <h5><strong>Ready to Record Symptoms?</strong> Say "Start symptoms"</h5>
  <h5><strong>All done?</strong> Say "Stop symptoms"</h5>
</div>
<div style="margin-top: -5%;" class="jumbotron text-center">
      <div id="controls">
  	 <button id="recordButton"> Record  &nbsp<i class="fas fa-microphone"></i></button>
  	 <button id="pauseButton" disabled> Pause &nbsp<i class="fas fa-pause"></i></button>
  	 <button id="stopButton" disabled>Stop &nbsp<i class="fas fa-stop"></i></button>
    </div>
    <div id="formats"></div>
  	<p2><strong>Recordings:</strong></p2>
  	<ol id="recordingsList"></ol>
    <!-inserting these scripts at the end to be able to use all the elements in the DOM -->
  	<script src="../../../js/recorder.js"></script>
  	<script src="https://cdn.rawgit.com/mattdiamond/Recorderjs/08e7abd9/dist/recorder.js"></script>
    <script src="https://requirejs.org/docs/release/2.3.5/minified/require.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
</div>
<!-- <h4>Key Phrases Manual</h4>
<br>

<div class="container-fluid" style="text-align: center">
  <div class="row">
    <div class="row">
  <div class="col-md-6"><h5>To Register Reasons for Visit:</h5> -->
<!-- <br>
<p style="font-size: 175%">To start, say <span><p1>one</p1></span> of the following:</p>
<p style="text-indent: 1.5%;">"Start reasons"</p>
<p style="text-indent: 1.5%;">"Begin reasons"</p>
<p style="text-indent: 1.5%;">"What are your reasons"</p>
<p style="text-indent: 1.5%;">"What brings you here today"</p>
<p style="font-size: 175%">To end, say <span><p1>one</p1></span> of the following:</p>
<p style="text-indent: 1.5%;">"Stop reasons"</p>
<p style="text-indent: 1.5%;">"End reasons"</p>
<p style="text-indent: 1.5%;">"No more reasons"</p></div>
  <div class="col-md-6"><h5>To Register Symptoms:</h5>
<br>
<p style="font-size: 175%">To start, say <span><p1>one</p1></span> of the following:</p>
<p style="text-indent: 2.5%;">"Start symptoms"</p>
<p style="text-indent: 2.5%;">"Begin symptoms"</p>
<p style="text-indent: 2.5%;">"What are your symptoms"</p>
<p style="text-indent: 2.5%;">"What symptoms do you have"</p>
<p style="text-indent: 1.5%; font-size: 175%;">To end, say <span><p1>one</p1></span> of the following:</p>
<p style="text-indent: 2.5%;">"Stop symptoms"</p>
<p style="text-indent: 2.5%;">"End symptoms"</p>
<p style="text-indent: 2.5%;">"No more symptoms"</p>  </div>
</div>
  </div>
</div>

<br>
<h4>How to Use</h4>
<br>
<h5 style="text-align: left">To Start:</h5>
<p style="text-align: left">1. Click on the <span style="color: red">red</span> "Record" button.</p>
<p style="text-align: left">2. Start recording and use the Key phrases manual.</p>

<h5 style="text-align: left">To Pause:</h5>
<p style="text-align: left">3. Click on the <span style="color: red">red</span> "Pause" button, while recording. Click on the "Resume" button to resume recording.</p>
<h5 style="text-align: left">To End:</h5>
<p style="text-align: left">3. Click on the <span style="color: red">red</span> "Stop" button, while recording. Wait to be redirected to patient homepage.</p>
<br> -->


</body>
</html>
