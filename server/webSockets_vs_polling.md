# Long-polling vs WebSockets vs Server-Sent Events(SSEs)


## AJAX Polling
* Polling is a standard technique used by the majority of AJAX applications. The basic idea is that the client
repeatedly polls (or requests) a server for data. The client makes a request and waits for the server to respond with data. If no data is available an empty response is returned.
    1. Client opens a connection and reqeusts data from the server using regular HTTP.
    2. The requested webpage sends requests to the server at regular intervals (e.g., 0.5s).
* Problem with polling is that the client has to keep asking the server for any new data. As a result
a lot of responses are empty creating HTTP overhead.

## Long Polling
* The client requests information from the server exactly as in normal polling, but with the expectation that
the server may not respond immediately. That's why this technique is sometimes referred to as __Hanging GET__.
	1. If the server does not have any data available for the client, instead of sending empty response, the server holds the request and waits until some data becomes available.
	2. Once the data becomes available, a full response is sent to the client.
* The basic life cycle of an application using HTTP Long-Polling is as follows:
	1. The clients make an initial request using regular HTTP and then waits for a response.
	2. The server delays its response until an update is available, or until a timeout has occurred.
	3. When an update is available, the server sends a full response to the client.
	4. Each Long-Poll request has a timeout. The client has to reconnect periodically after the connection is
	closed due to timeouts.

## WebSocket
* WebSocket provides full duplex communication channels over a single TCP connection. It provides a persistent connection between a client and server that both parties can use to start sending data at any time. The client establishes a WebSocket connection through a process known as the WebSocket handshake. If the process succeeds, then the server and client can exchange data in both directions at any time.

## Server-Sent Events (SSEs)
* Under SSEs the client establishes a persistent and long-term connection with the server. The server uses this connection to send data to a client. If the client wants to send data to the server, it would require the use of another technology/protocol to do so.
	1. Client requests data from a server using regular HTTP.
	2. The requested webpage opens a connection to the server. 
	3. The server sends the data to the client whenever there's new information available.
* SSEs are best when we need real-time traffic from the server to the client or if the server is generating data in a loop and will be sending multiple events to the client.

# How to choose among SSR, WebSocket and Polling?
* After the long and exhaustive client and server implementations, it looks like SSE is the final answer to our problem for data delivery. There are some issues with it as well but it can be solved. A few simple examples of applications that could make use of Server-Sent Events:
    1. A real-time chart of streaming stock prices
	2. Real-time news coverage of an important event (posting links, tweets, and images)
	3. A live Github / Twitter dashboard wall fed by Twitter’s streaming API
	4. A monitor for server statistics like uptime, health, and running processes.
* However, SSE is not just a viable alternative to the other methods for delivering fast updates. Each one dominates over others in a few specific scenarios like in our case where SSR proved to be an ideal solution. Consider a scenario like MMO (Massive Multiplayer Online) Games that need a huge amount of messages from both ends of the connection. In such a case, WebSockets dominates SSR.
* If your use case requires displaying real-time market news, market data, chat applications, etc., like in our case relying on HTTP/2 + SSE will provide you with an efficient bi-directional communication channel while reaping the benefits from staying in the HTTP world.
