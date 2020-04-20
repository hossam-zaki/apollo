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
<h1>Apollo</h1>
<div class="form">
<h3>Welcome</h3>
<form method="POST" action="/loginDoctor">
	<input type="text" placeholder="username" name="username" id="username" required></input><br>
	<input type="password" placeholder="password" name="password" id="password" required></input><br> <br>
  <input class="button" type="submit" value="Sign In">
</form>
<h2>or</h2>
<form method="GET" action="/register">
  <input class="button" type="submit" value="Sign Up">
</form>
<br>
<br>
<#--  <form method="GET" action="/login">
  <input class="button" type="submit" value="login">
</form>  -->

</div>
</p>
</body>
</html>
