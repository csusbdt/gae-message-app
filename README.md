## Overview

This is an example Java GAE application.  

This application lets logged-in users post a single message.  The messages of all users are listed in a single page.  Users can only create or delete their own message.

## Learning Objectives

- See how JSP is used to generate HTML pages.
- See how JSP is kept from direct browser access by being hidden in the WEB-INF folder.
- See how the JSP Expression Language is used.
- See how jQuery and jQuery-ui can be used in a Web application.
- See how to use Ajax with a single HTML file to provide multiple views.
- See how GAE authentication works.
- See how to use the GAE datastore. 
- See how to protect Web applications against XSS and CSRF attacks.

## Video Walk-through

I recorded 2 videos that explain this example application.

- [GAE Message App Video, part 1](http://www.youtube.com/watch?v=WI6rsfhvqqM)
- [GAE Message App Video, part 2](http://www.youtube.com/watch?v=mi3iZtVuBZg)

## Security Features

The application is protected against both cross-site scripting (XSS) and cross-site reference forgery (CSRF) attacks.  To better understand how the protection mechanisms work, you need to understand [the Same origin policy](http://en.wikipedia.org/wiki/Same_origin_policy), which browsers adhere to.  

I used the following two web pages to devise a solution to the CSRF attacks.

- [This Stackoverflow post](http://stackoverflow.com/a/908348/754381) provides a CSRF solution that does not require datastore writes, which makes the solution less expensive than a solution based on sessions, which require datastore writes in GAE.
- [This blog entry](http://erlend.oftedal.no/blog/?blogid=118) describes a technique to handle CSRF in jQuery Ajax applications.

## CSRF Protection Test

The following form sends a delete-message request to the message application running on localhost. The delete should fail because the CSRF token is not sent.

````
<form action="http://localhost:8888/delete-message" method="post">
  <input type="submit" value="Click here to win $100" />
</form>
````

<form action="http://localhost:8888/delete-message" method="post">
  <input type="submit" value="Click here to win $100" />
</form>

To run the test, make sure you are first logged into the message app,
and then in a separate browser tab, open this web page and click the button to win $100. Check that your message has not been deleted.

To see what happens when secret CSRF tokens are not used, comment out the code that rejects requests when the user's secret token is not presented.  Click the button to win $100 and verify that the malicious form deletes your message.

