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
	<label for="first_name">First Name:</label>
	<textarea name="first_name" id="first_name"></textarea><br>
	<label for="middle_name">Middle Name:</label>
	<textarea name="middle_name" id="middle_name"></textarea><br>
	<label for="last_name">Last Name:</label>
	<textarea name="last_name" id="last_name"></textarea><br>
	<label for="email">Email:</label>
	<textarea name="email" id="email"></textarea><br>
	<label for="username">Username:</label>
	<textarea name="username" id="username"></textarea><br>
	<label for="password">Password:</label>
	<textarea name="password" id="password"></textarea><br>
	<label for="phone">Phone Number:</label>
	<textarea name="phone" id="phone"></textarea><br>
	<label for="institution">Medical Institution:</label>
	<textarea name="institution" id="institution"></textarea><br>
  <input type="submit" value="register">
</form>
</div>
</p>

</#assign>
<#include "main.ftl">