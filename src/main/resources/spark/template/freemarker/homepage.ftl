<#assign content>

<div class="top">
<h1>Apollo</h1>
</div>


<p>
<div class="form">
<h3>Login or Register</h3>
<form method="GET" action="/register">
  <input type="submit" value="register">
</form>
<form method="GET" action="/login">
  <input type="submit" value="login">
</form>
<form method="POST" action="/startRecording">
  <input type="submit" value="Start Recording">
</form>
</form>
<form method="POST" action="/endRecording">
  <input type="submit" value="End Recording">
</form>
</div>
</p>

</#assign>
<#include "main.ftl">