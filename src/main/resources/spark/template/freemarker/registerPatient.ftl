<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../../css/homepage.css">
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
  </nav
<h1>Apollo</h1>
<hr>
</div>
<p>
<div class="form">
<h3 style="margin-top: -3%">Patient Registration</h3>
<form method="POST" action="/apollo/registerPatient/addPatient/:${username}">
	<input type="text" placeholder="first name" name="first_name" id="first_name" pattern="[A-Za-z]+" title="Name can only contain letters" required></input><br>
	<input type="text" placeholder="middle name" name="middle_name" id="middle_name"></input><br>
	<input type="text" placeholder="last name" name="last_name" id="last_name" pattern="[A-Za-z]*" title="Last name can only contain letters" required ></input><br>
	<input type="text" placeholder="date of birth" name="dob" id="dob" pattern="((?:0[1-9])|(?:1[0-2]))\/((?:0[0-9])|(?:[1-2][0-9])|(?:3[0-1]))\/(\d{4})" title="Valid date of birth" required></input><br>
	<input type="text" placeholder="email" name="email" id="email" pattern="^.+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$" title="Valid email adress" required></input><br>
	<input type="text" placeholder="phone" name="phone" id="phone" pattern="^([\+][0-9]{1,3}([ \.\-])?)?([\(]{1}[0-9]{3}[\)])?([0-9A-Z \.\-]{1,32})((x|ext|extension)?[0-9]{1,4}?)$" title="Valid phone number" required></input><br>
	<input type="text" placeholder="emergency phone number" name="emergency contact phone" id="emergency contact phone" required></input><br>
  <br>
  <input class="button" type="submit" placeholder="register" value="register">
</form>
</div>
</p>
