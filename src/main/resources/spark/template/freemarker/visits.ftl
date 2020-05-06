<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->
  <link rel="stylesheet" href="../../css/../normalize.css">
	<link rel="stylesheet" href="../../../css/base.css"> 
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../../css/visits.css"> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap" rel="stylesheet">
    <script src="../../../js/jquery-2.1.1.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
    <link rel="stylesheet" href="../../../css/visits.css"> 
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
  <h3>${name}'s Visits</h3>
  <h5>Search visits using keywords or dates!</h5>
</div>
<br>

<div id="reportrange" style="color:black; background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc; width: 15%; margin-left: 10%; margin-top: -4%">
    <i style="color:black" class="fa fa-calendar"></i>&nbsp;
    <span style="color:black">search for visits by date</span>
<script style="color:black" type="text/javascript" class="ranger">
$(function() {
    var start = moment().subtract(29, 'days');
    var end = moment();
    $('#reportrange').daterangepicker({
        startDate: start,
        endDate: end,
    });
    $('#reportrange').on('apply.daterangepicker', function(ev, res) {
      var xhttp = new XMLHttpRequest();
      var url = "${route2}?startDate=" + res.startDate._d + "&endDate=" + res.endDate._d;
      window.location = url;
      console.log(res.startDate._d, res.endDate._d);
      console.log("${route2}?startDate=" + res.startDate._d + "&endDate=" + res.endDate._d);
  });
});
</script>
</div>
<div style="text-align:center; color:black">

</div>
<div>
  <form class="form-inline">
    <div class="input-group">
      <input type="text" class="form-control" size="75" placeholder="search transcripts for keywords" name="searched" id="searched" required>
      <div method="GET" action="${route2}" class="input-group-btn">
        <button style="color:black; margin-top:21%" type="submit" class="btn"><i class="fa fa-search"></i></button>
      </div>
    </div>
  </form>
</div>
${visits}
</body>
</html>
