# Reverse Proxy

## What is reverse proxy?
- A reverse proxy is a web server that centralizes internal services and provides unified interfaces to the public.
- Requests from clients are forwarded to a server that can fulfill it before the reverse proxy returns the server's response to the client.
- __Increased security__ Hide information about backend servers, blacklist IPs, limit number of connections per client
- __Increased scalability and flexibility__ Clients only see the reverse proxy's IP, allowing you to scale servers or change their configuration
- __SSL termination__ Decrypt incoming requests and encrypt server responses so backend servers do not have to perform these potentially expensive operations
- __Compression__ Compress server responses
- __Caching__ Return the response for cached requests
- __Static content__ Serve static content directly
	- HTML/CSS/JS
	- Photos
	- Videos
	- Etc

## Load balancer vs reverse proxy
- Deploying a load balancer is useful when you have multiple servers. Often, load balancers route traffic to a set of servers serving the same function.
- Reverse proxies can be useful even with just one web server or application server.
- Solutions such as NGINX and HAProxy can support both layer 7 reverse proxying and load balancing.

## Disadvantage
- Introducing a reverse proxy results in increased complexity.
- A single reverse proxy is a single point of failure, configuring multiple reverse proxies (ie a failover) further increases complexity.

