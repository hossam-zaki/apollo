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
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../css/base.css"> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/register.css">
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
      <a class="navbar-brand" href="/apollo">Apollo</a>
    </div>
  </div>
</nav>
<h3>Register<h3>
<h4 id= "error">${status}</h4>
<div class="form">
<form method="POST" action="/registerDoctor">
	<input type="text" placeholder="first name" name="first_name" id="first_name" pattern="[A-Za-z]+" title="Name can only contain letters" required></input><br>
	<input type="text" placeholder="middle name" name="middle_name" id="middle_name"></input><br>
	<input type="text" placeholder="last name" name="last_name" id="last_name" pattern="[A-Za-z]*" title="Last name can only contain letters" required ></input><br>
	<input type="text" placeholder="email" name="email" id="email" pattern="^.+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$" title="Valid email adress" required></input><br>
	<input type="text" placeholder="username" name="username" id="username" required></input><br>
	<input type="password" placeholder="password" name="password" id="password" pattern="(?=^.{6,255}$)((?=.*\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*" title="The password must match 3 of 4 conditions: 1.) at least 1 upper case character 2.) at least 1 lower case character 3.) at least 1 numerical character 4.) at least 1 special character " required></input><br>
	<input type="password" placeholder="retype password" name="passwordVali" id="passwordVali" required></input><br>
	<input type="text" placeholder="phone number" name="phone" id="phone" pattern="^([\+][0-9]{1,3}([ \.\-])?)?([\(]{1}[0-9]{3}[\)])?([0-9A-Z \.\-]{1,32})((x|ext|extension)?[0-9]{1,4}?)$" title="Valid phone number" required></input><br>
	<input type="text" placeholder="medical institution" name="institution" id="institution" required></input><br>
	<br>
  <input id ="button" class="button" type="submit" value="register">
</form>
</div>
</body>
</html>
