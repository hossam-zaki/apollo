<!DOCTYPE html>
<html lang="en">
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
    <link rel="stylesheet" href="../../../../../css/singleVisit.css"> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
        <li><a href=${route}>back to patient</a></li>
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
  <h3>Records for the visit on ${date}.</h3>
</div>
<div class="audioStuff">
<audio controls>
  <source src="../../../../../audio/${audio}" type="audio/wav">
</audio>
</div>
<h4>Patient Details</h4>
<div style="color:black; margin-left: 8%;" class="details">
  <b>Name:</b> ${name} <br>
  Date of Birth: ${dob} <br>
  Email: ${number} <br>
  Phone Number: ${email}
</div>
<br>
<div class="row">
  <div class="column left">
  <h4 style="margin-left: 22%;">Visit Summary</h4>
  <div class="summary">${summary}</div>  
  </div>
<div class="column right">
  <button class="collapsible"><h4>View Visit Transcript:</h4></button>
  <div class="content">
  <div class="transcript">${transcript}</div>
</div>
</div>
</div>

<script>
var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight){
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    } 
  });
}
</script>
</body>
</html>
