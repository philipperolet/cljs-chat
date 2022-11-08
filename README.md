# cljs-chat

A simple Javascript chat component. The in Clojurescript

It looks like this:

![Image of the chat component](resources/screenshot.png)

## Requirements
- Bootstrap v4

## Usage
### Setup
- Download the javascript and copy it to your website's resources dir
```
curl https://github.com/philipperolet/cljs-chat/blob/main/resources/public/js/cljs-chat.js
curl https://github.com/philipperolet/cljs-chat/blob/main/resources/public/css/cljs-chat.css
```

- Include JS and CSS in HTML
```
<html>
<head>
<link href="PATH/TO/cljs-chat.css" rel="stylesheet">
...
</body>
<script src="PATH/TO/cljs-chat.js" type="text/javascript"></script>
...
</html>
```

- Add the chat component where desired in the HTML (once per app) with this line
```
<div id="cljs-chat" class="container-fluid"></div>
```
### Messages
You can: 
- programatically post messages to users "you" and "me";
- access the full message history;

```
mzero.web.chat.send_message("you", "Hello!");
mzero.web.chat.send_message("me", "Hi, what's up?");
```
You can access all messages like this:
There is a callback

### Sytle customization
The CSS is very short (< 30 lines) and uncompressed. It can easily be changed

## About
