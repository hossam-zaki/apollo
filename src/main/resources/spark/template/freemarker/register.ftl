<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/html5bp.css">
    <link rel="stylesheet" href="css/homepage.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <script src="js/jquery-2.1.1.js"></script>
  </head>
<body> 

<div class="top">
<h1>Apollo</h1>
</div>
<h3>Register</h3>
<h4>${status}</h4>
<div class="form">
<form method="POST" action="/registerDoctor">
	<input type="text" placeholder="first name" name="first_name" id="first_name" pattern="[A-Za-z]+" title="Name can only contain letters" required></input><br>
	<input type="text" placeholder="middle name" name="middle_name" id="middle_name"></input><br>
	<input type="text" placeholder="last name" name="last_name" id="last_name" pattern="[A-Za-z]*" title="Last name can only contain letters" required ></input><br>
	<input type="text" placeholder="email" name="email" id="email" pattern="^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$" title="Valid email adress" required></input><br>
	<input type="text" placeholder="username" name="username" id="username" required></input><br>
	<input type="password" placeholder="password" name="password" id="password" pattern="(?=^.{6,255}$)((?=.*\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*" title="The password must match 3 of 4 conditions: 1.) at least 1 upper case character 2.) at least 1 lower case character 3.) at least 1 numerical character 4.) at least 1 special character " required></input><br>
	<input type="password" placeholder="retype password" name="passwordVali" id="passwordVali" required></input><br>
	<input type="text" placeholder="phone number" name="phone" id="phone" pattern="^([\+][0-9]{1,3}([ \.\-])?)?([\(]{1}[0-9]{3}[\)])?([0-9A-Z \.\-]{1,32})((x|ext|extension)?[0-9]{1,4}?)$" title="Valid phone number" required></input><br>
	<input type="text" placeholder="medical institution" name="institution" id="institution" required></input><br>
	<br>
  <input class="button" type="submit" value="register">
</form>
</div>

</body>
</html>