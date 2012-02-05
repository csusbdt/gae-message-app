<!DOCTYPE html>

<link type="text/css" href="/css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.16.custom.min.js"></script>

<script>
var csrfToken = '${csrfToken}';

var view = {
  userMessageExists: ${userMessageExists},
  
  init: function() {
    var height = '40px';
    var width = '180px';
    $('#button1').button().css('height', height).css('width', width);
    $('#button2').button().css('height', height).css('width', width);
    $('#button3').button().css('height', height).css('width', width);
  },

  listMessages: function() {
    $('#create-message-body').hide();
    $('#list-messages-body').show();
    if (this.userMessageExists) {
      $('#button1').html('Delete Message').off('click').on('click', controller.deleteMessage);
    } else {
      $('#button1').html('Create Message').off('click').on('click', view.createMessage);
    }
    $('#button2').html('Logout').off('click').on('click', controller.logout);
    $('#button3').off('click').hide();
  },

  createMessage: function() {
    $('#list-messages-body').hide();
    $('#create-message-body').show();
    $('#button1').html('Save').off('click').on('click', controller.createMessage);
    $('#button2').html('Cancel').off('click').on('click', view.listMessages);
    $('#button3').html('Logout').off('click').on('click', controller.logout);
  }
}

var controller = {

  init: function() {
    $.ajaxSetup({
      beforeSend: function(jqXHR, settings) {
        jqXHR.setRequestHeader('X-CSRF-Token', csrfToken);
      }
    });
    $.ajax({
      url: '/get-message-list',
      dataType: 'json',
      type: 'POST',
      success: function(data) {
        controller.setMessageList(data);
        view.listMessages();
      },
      error: function(xhr, textStatus, errorThrown) {
        alert(errorThrown);
      }
    });
  },

  logout: function() {
    window.location.replace('/logout');
  },

  setMessageList: function(messageList) {
    $('#messages').empty();
    for (var i = 0; i < messageList.length; ++i) {
      $('#messages').append(
        '<li><h2>' + 
        messageList[i].nickname + 
        '</h2><p>' + 
        messageList[i].text + 
        '</p></li>'
      );
    }
  },

  createMessage: function() {
    view.userMessageExists = true;
    $.ajax({
      url: '/create-message',
      dataType: 'json',
      type: 'POST',
      data: { 'text': $('#text').val() },
      success: function(data) {
        controller.setMessageList(data);
        view.listMessages();
      },
      error: function(xhr, textStatus, errorThrown) {
        alert(errorThrown);
      }
    });
  },

  deleteMessage: function() {
    view.userMessageExists = false;
    $.ajax({
      url: '/delete-message',
      dataType: 'json',
      type: 'POST',
      success: function(data) {
        controller.setMessageList(data);
        view.listMessages();         
      },
      error: function(xhr, textStatus, errorThrown) {
        alert(errorThrown);
      }
    });
  } 
}

$(document).ready(function() {
  view.init();
  controller.init();
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
 <textarea id="text" rows="5" cols="80"></textarea>
</div>
