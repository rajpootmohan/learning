## Load Balancer

### What is Load Balancer?
- Load balancers distribute incoming client requests to computing resources such as application servers and databases.
- the load balancer returns the response from the computing resource to the appropriate client.
- Load balancers can be implemented with hardware (expensive) or with software such as HAProxy.
- Load balancers are effective at:
	- Preventing requests from going to unhealthy servers
	- Preventing overloading resources
	- Helping eliminate single points of failure
- Additional benefits include:
	- SSL termination
		- Decrypt incoming requests and encrypt server responses so backend servers do not have to perform these potentially expensive operations
		- Removes the need to install X.509 certificates on each server
	- Session persistence
		- Issue cookies and route a specific client's requests to same instance if the web apps do not keep track of sessions
- To protect against failures, it's common to set up multiple load balancers, either in active-passive or active-active mode
- Load balancers can route traffic based on various metrics, including:
	- Random
	- Least loaded
	- Session/cookies
	- Round robin or weighted round robin
	- Layer 4
	- Layer 7

### Layer 4 load balancing
- Layer 4 load balancers look at info at the transport layer to decide how to distribute requests.
- Generally, this involves the source, destination IP addresses, and ports in the header, but not the contents of the packet.
- Layer 4 load balancers forward network packets to and from the upstream server, performing Network Address Translation (NAT).
- layer 4 load balancing requires less time and computing resources than Layer 7
- AWS ELB works on this layer.
- Sticky sessions (cookies)

### Layer 7 load balancing
- Layer 7 load balancers look at the application layer to decide how to distribute requests.
- This can involve contents of the header, message, and cookies.
- Layer 7 load balancers terminates network traffic, reads the message, makes a load-balancing decision, then opens a connection to the selected server
- At Layer 7, a load balancer has application awareness and can use this additional application information to make more complex and informed load balancing decisions.
- AWS ALB works on this layer. 

### Disadvantage
- The load balancer can become a performance bottleneck if it does not have enough resources or if it is not configured properly.
- Introducing a load balancer to help eliminate single points of failure results in increased complexity.
- A single load balancer is a single point of failure, configuring multiple load balancers further increases complexity.

