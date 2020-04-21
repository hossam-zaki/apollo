<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/../normalize.css">
    <link rel="stylesheet" href="../../../css/html5bp.css">
	<link rel="stylesheet" href="../../../css/base.css">     <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <script src="js/jquery-2.1.1.js"></script>
  </head>
<body>
<h1>Apollo</h1>
<hr>
<h3>Hello Doctor ${docName}.</h3>
<form method="GET" action=${route}>
  <input class="button" type="submit" value="Register New Patient">
</form>
<div class="wrap">
   <div class="search">
      <input type="text" class="searchTerm" placeholder="Search by Patient Name">
      <button type="submit" class="searchButton">
        <i class="fa fa-search"></i>
     </button>
   </div>
</div>
<!-- <div class="folder" style="color: black; margin-left: 50%; ">
  <i class="far fa-folder"></i>
</div> -->
</body>
</html>
