<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../css/base.css"> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
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
<div class="jumbotron text-center">
      <div id="controls">
  	 <button id="recordButton"> Record  &nbsp<i class="fas fa-microphone"></i></button>
  	 <button id="pauseButton" disabled> Pause &nbsp<i class="fas fa-pause"></i></button>
  	 <button id="stopButton" disabled>Stop &nbsp<i class="fas fa-stop"></i></button>
    </div>
    <div id="formats"></div>
  	<p><strong>Recordings:</strong></p>
  	<ol id="recordingsList"></ol>
    <!-- inserting these scripts at the end to be able to use all the elements in the DOM -->
  	<script src="../../../js/recorder.js"></script>
  	<script src="https://cdn.rawgit.com/mattdiamond/Recorderjs/08e7abd9/dist/recorder.js"></script>
    <script src="https://requirejs.org/docs/release/2.3.5/minified/require.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
</div>
<br>
<!-- <div class="folder" style="color: black; margin-left: 50%; ">
  <i class="far fa-folder"></i>
</div> -->
</body>
</html>
