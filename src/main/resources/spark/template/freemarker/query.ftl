<#assign content>

<div class="top">
<h1>Stars</h1>
<h2>Input File</h2>
<h4>File must be uploaded in order to run queries</h4>
<form method="POST" action="/upload">
<label for="file">Enter path to file here: </label><br>
<textarea name="file" id="file"></textarea><br>
<input type="submit">
</form>
<h3>Results</h3>
<h4>${output}</h4>
</div>

<table style="width:100%">
<tr>
<th>
<h3>Search neighbors by coordinate</h3>
<p>
<form method="POST" action="/neighborsCoords">
	<label for="k">Enter number of neighbors</label>
	<textarea name="k_neighbors_coords" id="k_neighbors_coords"></textarea><br>
	<label for="x_neighbors_coords">Enter x coordinate</label>
	<textarea name="x_neighbors_coords" id="x_neighbors_coords"></textarea><br>
	<label for="y_neighbors_coords">Enter y coordinate</label>
	<textarea name="y_neighbors_coords" id="y_neighbors_coords"></textarea><br>
	<label for="z_neighbors_coords">Enter z coordinate</label>
	<textarea name="z_neighbors_coords" id="z_neighbors_coords"></textarea><br>
	<br>
  <input type="submit">
</form>
</th>
<th>
<h3>Search Neighbors by Name</h3>
<form method="POST" action="/neighborsName">
	<label for="k_neighbors_name">Enter number of neighbors</label>
	<textarea name="k_neighbors_name" id="k_neighbors_name"></textarea><br>
	<label for="name_neighbors_name">Enter name of Star</label>
	<textarea name="name_neighbors_name" id="name_neighbors_name"></textarea><br>
	<br>
  <input type="submit">
</form>
</p>
</th>
</tr>
<tr>
<th>
<h3>Search Radius by Coordinate</h3>
<p>
<form method="POST" action="/radiusCoords">
	<label for="k">Enter Radius</label>
	<textarea name="k_radius_coords" id="k_radius_coords"></textarea><br>
	<label for="x_radius_coords">Enter x coordinate</label>
	<textarea name="x_radius_coords" id="x_radius_coords"></textarea><br>
	<label for="y_radius_coords">Enter y coordinate</label>
	<textarea name="y_radius_coords" id="y_radius_coords"></textarea><br>
	<label for="z_radius_coords">Enter z coordinate</label>
	<textarea name="z_radius_coords" id="z_radius_coords"></textarea><br>
	<br>
  <input type="submit">
</form>
</th>

<th>
<h3>Search Radius by Name</h3>
<p>
<form method="POST" action="/radiusName">
	<label for="k_radius_name">Enter Radius</label>
	<textarea name="r_radius_name" id="r_radius_name"></textarea><br>
	<label for="x_radius_name">Enter name of Star</label>
	<textarea name="name_radius_name" id="name_radius_name"></textarea><br>
  <input type="submit">
</form>
</th>
</tr>
</table>
</p>
</#assign>
<#include "main.ftl">