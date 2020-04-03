<#assign content>

<div class="top">
<h1>Apollo</h1>
<h4>${status}</h4>
</div>


<p>
<div class="form">
<h3>Login</h3>
<form method="POST" action="/loginDoctor">
	<label for="username">Username:</label>
	<input type="text" name="username" id="username" required></input><br>
	<label for="password">Password:</label>
	<input type="password" name="password" id="password" required></input><br>
  <input type="submit" value="login">
</form>
</div>
</p>

</#assign>
<#include "main.ftl">