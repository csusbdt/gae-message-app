<!DOCTYPE html>

<title>GAE Message App</title>

<c:if test="!emptyMessage error">
  <div>${errorMessage}</div>
</c:if>

<div>
<a href="/logout">Logout</a>
</div>