<#assign content>

<div class="top">
<h1>Apollo</h1>
<h4>${status}</h4>
</div>
<p>
<div class="form">
<h3>login</h3>
<form method="POST" action="/registerDoctor">
	<label for="first_name">First Name:</label>
	<input type="text" name="first_name" id="first_name" pattern="[A-Za-z]+" title="Name can only contain letters" required></input><br>
	<label for="middle_name">Middle Name:</label>
	<input type="text" name="middle_name" id="middle_name"></input><br>
	<label for="last_name">Last Name:</label>
	<input type="text" name="last_name" id="last_name" pattern="[A-Za-z]*" title="Last name can only contain letters" required ></input><br>
	<label for="email">Email:</label>
	<input type="text" name="email" id="email" pattern="^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$" title="Valid email adress" required></input><br>
	<label for="username">Username:</label>
	<input type="text" name="username" id="username" required></input><br>
	<label for="password">Password:</label>
	<input type="password" name="password" id="password" pattern="(?=^.{6,255}$)((?=.*\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*" title="The password must match 3 of 4 conditions: 1.) at least 1 upper case character 2.) at least 1 lower case character 3.) at least 1 numerical character 4.) at least 1 special character " required></input><br>
    <label for="passwordVali">Retype Password:</label>
	<input type="password" name="passwordVali" id="passwordVali" required></input><br>
	<label for="phone">Phone Number:</label>
	<input type="text" name="phone" id="phone" pattern="^([\+][0-9]{1,3}([ \.\-])?)?([\(]{1}[0-9]{3}[\)])?([0-9A-Z \.\-]{1,32})((x|ext|extension)?[0-9]{1,4}?)$" title="Valid phone number" required></input><br>
	<label for="institution">Medical Institution:</label>
	<input type="text" name="institution" id="institution" required></input><br>
  <input type="submit" value="register">
</form>
</div>
</p>

</#assign>
<#include "main.ftl">