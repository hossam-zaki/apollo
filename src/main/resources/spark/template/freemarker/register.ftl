<#assign content>

<div class="top">
<h1>Apollo</h1>
<h2>Input File</h2>
<h4>File must be uploaded in order to run queries</h4>
<h3>Results</h3>
<h4>${output}</h4>
</div>


<p>
<div class="form">
<h3>Connect Actors</h3>
<form method="POST" action="/registerDoctor">
	<label for="k">First Name:</label>
	<textarea name="first_actor" id="first_actor"></textarea><br>
	<label for="x_neighbors_coords">Enter Last Actor</label>
	<textarea name="last_actor" id="last_actor"></textarea><br>
  <input type="submit">
</form>
</div>
</p>

</#assign>
<#include "main.ftl">