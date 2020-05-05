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
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;500&display=swap" rel="stylesheet">
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
      <a class="navbar-brand" style="font-weight: 300;" href="/apollo/:${username}">Apollo</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav navbar-right">
        <li style="font-weight: 300;"><a href="/apollo/:${username}">home</a></li>
        <li style="font-weight: 300;"><a href=${route}>back to patient</a></li>
        <li class="dropdown" style="font-weight: 300;">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">profile
          <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a style="font-weight: 300;" href="/apollo/account-details/:${username}">account information</a></li>
            <li><a style="font-weight: 300;" href="/apollo">logout</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>

<div class="jumbotron text-center">
  <h3 style="font-weight: 300">Records for the visit on ${date}.</h3>
</div>
<div class="row" style="margin-left:10%;">
  <div class="col-xs-6" >
  <h4><strong>Patient Details</strong></h4>
    <p class="details">
    <strong>Name: </strong>${name} <br>
    <strong>Date of Birth:</strong> ${dob} <br>
    <strong>Email:</strong> ${number} <br>
    <strong>Phone Number:</strong> ${email}
    </p></div>
  <div class="col-xs-6"> <div style="text-align:center; margin-left: -35%;"> <audio controls><source src="../../../../../audio/${audio}" type="audio/wav"></audio></div> </div>
</div>
<div class="row" style="margin-top: 1.5%; margin-left:10%;">
  <div class="col-xs-6 col-sm-4"> <div style="margin-left: 5%;"><h4>Visit Summary</h4></div>
  <br>
  <div class="summary" style="margin-left: 15%;">${summary}</div>  </div>
  <div class="col-xs-6 col-sm-8">
   <button class="btn btn-primary" id="drop" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample" style="min-width: 150px">View Transcript</button>
    <div class="collapse" id="collapseExample">
        <div class="card card-body">
          <div class="transcript">${transcript}</div>
        </div>
      </div>
  </div>
</div>

<script>

$(document).ready(function() {

$('#drop').click(function() {
  $(this).toggleClass( "active" );
  if ($(this).hasClass("active")) {
    $(this).text("Hide");
  } else {
    $(this).text("View Transcript");
  }
});
});
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
