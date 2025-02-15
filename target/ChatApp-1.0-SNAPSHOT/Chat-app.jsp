

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat-app</title>
        <script>
            let socket; 

            function connect() {
                socket = new WebSocket("ws://localhost:8080/ChatApp/chat");

                socket.onopen = function () {
                    console.log("connected");
                    document.getElementById("status").innerText = "Connected";
                };

                socket.onmessage = function (event) {
                    let messages = document.getElementById("messages");
                    messages.innerHTML += "<p>Server: " + event.data + "</p>";
                };

                socket.onclose = function () {
                    console.log("disconnected");
                    document.getElementById("status").innerHTML = "Disconnected";
                };
            }

            function sendMessage() {
                let message = document.getElementById("messageInput").value;
                if (socket && socket.readyState === WebSocket.OPEN) {
                    socket.send(message);
                } else {
                    console.error("WebSocket is not connected.");
                }
            }
        </script>
    </head>
    <body onload="connect()">
        <h2>--AI chat--</h2>
        <p>Status: <span id="status">Connecting...</span></p>
        <input type="text" id="messageInput" placeholder="Enter message">
        <button onclick="sendMessage()">Send</button>
        <div id="messages"></div>

    </body>
</html>
