<#assign content>

<div class="top">
<h1>TIMDB</h1>
<h2>Input File</h2>
<h4>File must be uploaded in order to run queries</h4>
<form method="POST" action="/timdbUpload">
<label for="file">Enter path to file here: </label><br>
<textarea name="file" id="file"></textarea><br>
<input type="submit">
</form>
<h3>Results</h3>
<h4>${output}</h4>
</div>


<p>
<div class="form">
<h3>Connect Actors</h3>
<form method="POST" action="/timdbConnect">
	<label for="k">Enter First Actor</label>
	<textarea name="first_actor" id="first_actor"></textarea><br>
	<label for="x_neighbors_coords">Enter Last Actor</label>
	<textarea name="last_actor" id="last_actor"></textarea><br>
  <input type="submit">
</form>
</div>
</p>

</#assign>
<#include "main.ftl">