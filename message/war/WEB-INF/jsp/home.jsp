<!DOCTYPE html>

<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>

<script>
var nickname = '${user.nickname}';
var userMessageExists = ${userMessageExists};

function createMessageView() {
  $('#list-messages-body').hide();
  $('#create-message-body').show();
  $('#button1').html('Save').off('click').on('click', createMessage);
  $('#button2').html('Cancel').off('click').on('click', listMessagesView);
  $('#button3').html('Logout').off('click').on('click', logout);
}

function listMessagesView() {
  $('#create-message-body').hide();
  $('#list-messages-body').show();
  if (userMessageExists) {
    $('#button1').html('Delete Message').off('click').on('click', deleteMessage);
  } else {
    $('#button1').html('Create Message').off('click').on('click', createMessageView);
  }
  $('#button2').html('Logout').off('click').on('click', logout);
  $('#button3').off('click').hide();
}

function logout() {
  window.location.replace('/logout');
}

function createMessage() {
  userMessageExists = true;
  $.ajax({
    url: '/create-message',
    dataType: 'json',
    type: 'POST',
    data: { 'text': $('#text').val() },
    success: function(data) {
      appendMessageList(data);
      listMessagesView();
    },
    error: function(xhr, textStatus, errorThrown) {
      alert(errorThrown);
    }
  });
}

function deleteMessage() {
  $.ajax({
    url: '/delete-message',
    dataType: 'json',
    type: 'POST',
    success: function(data) {
      listMessagesView();         
    },
    error: function(xhr, textStatus, errorThrown) {
      alert(errorThrown);
    }
  });
}

function appendMessageList(message) {
  $('#messages').append('<li><a href="msg/' + message.id + '">' + message.nickname + '</a></li>');
}

$(document).ready(function() {
  var height = '40px';
  var width = '180px';
  $('#button1').button().css('height', height).css('width', width);
  $('#button2').button().css('height', height).css('width', width);
  $('#button3').button().css('height', height).css('width', width);
  $.ajax({
    url: '/get-message-list',
    dataType: 'json',
    type: 'POST',
    success: function(data) {
      var i = 0;
      for (; i < data.length; ++i) {
        appendMessageList(data[i]);
      }
      listMessagesView();         
    },
    error: function(xhr, textStatus, errorThrown) {
      alert(errorThrown);
    }
  });
});
</script>

<title>GAE Message App</title>

<div id="menu">
 <button id="button1"></button>
 <button id="button2"></button>
 <button id="button3"></button>
</div>

<div id="list-messages-body">
 <ul id="messages"></ul>
</div>

<div id="create-message-body">
 <textarea id="text" rows="3" cols="20">Hello there.</textarea>
</div>
