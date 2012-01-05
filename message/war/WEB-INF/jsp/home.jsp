<!DOCTYPE html>

<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" /> 
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>

<script>
$(document).ready(function() {
  $.ajax({
    url: '/get-users',
    type: 'POST',
    success: function(data) {
      $('#links').html(data);
    },
    error: function(xhr, textStatus, errorThrown) {
      alert(errorThrown);
    }
  });
});
</script>

<title>GAE Message App</title>

<c:if test="!emptyMessage error">
  <div>${errorMessage}</div>
</c:if>

<div id="links"></div>

<div>
<a href="/logout">Logout</a>
</div>