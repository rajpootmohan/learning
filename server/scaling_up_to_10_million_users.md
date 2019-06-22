# Scaling up to 10 million users

## 1 User
![One User architecture](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/1_user.png)

## More than 1 User

### Database Options
- Self Managed
	- Amazon EC2
- Fully Managed
	- Amazon RDS
	- Amazon DynamoDB
	- Amazon RedShift
	- Amazon Aurora

![More than One User](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/1_%3E_user.png)

### Registration 
- Amazon Cognito
	- User Pool: Add sign-up and sign-in with a fully managed user directory
	- Federated Identities: Managed authenticated and guest user's access to your AWS resources
		- Login through Twitter
		- Login through Google
		- Login through Facebook
		- etc

![Amazon Cognito](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/amazon_cognito.png)

## Greater than 100 Users
![Greater than 100 Users](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/100_%3E_users.png)

## Greater than 1 K Users
![Greater than 1000 Users](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/1000_%3E_users.png)

### Load Balancers
- Application Load Balancer
	- Highly Available
	- 1 ~ 65535
	- Health Checks
	- Session stickiness
	- Monitoring / Logging
	- Content based routing
	- Container based apps
	- Web Sockets
	- HTTP/2
- Network Load Balancer
	- Layer 4
	- Extreme performance
	- Ultra low latency
	- Elastic IP
- Classic Load Balancer

## Greater than 10 K Users
![Greater than 10 K Users](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/10k_%3E_users.png)

![Shift some load around using CDN](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/shift%20some%20load%20around%20using%20cdn.png)

![Shift some load around using Cache](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/shift%20some%20load%20around%20using%20cache.png)

![Shift some load around using DynamoDB](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/shift%20some%20load%20around%20using%20dynamo%20DB.png)

### Amazon DynamoDB
- Managed No SQL database
- Fast, predictable performance
- Fully distribured, fault tolerant
- JSON support
- Items up to 400 Kb
- Time to live
- AWS application auto scaling
- Streams and Triggers

### Auto Scaling

## Greater than 500 K Users
![Greater than 500 K Users](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/500k_%3E_users.png)

### Monitoring and Logging
- Monitoring, metrics, and logging
	- If you can't build internally, outsourced it.
- What are customer saying?
- Try to squeeze as much performace out of each service/component.
- Amazon cloudWatch

### Further Improvements
- Breaking apart our web/app layer
- Monolithic architecture to Service Oriented Architecture
- Server less web application
- Micro-service architecture

## Greater than 1 Million
![Greate than 1 M users](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/scaling_upto_10_million_users/1M_%3E_users.png)

- Reaching a million and above is going to require some bit of all the previous things
	- Multi AZ
	- Elastic Load Balancing between Tiers
	- Auto Scaling
	- Service Oriented Architecture (SOA)
	- Serving content smartly (Amazon S3/ cloudFront)
	- Caching off DB
	- Moving state off tiers that auto scale

## Users 5 Million to 10 Million

### Database Issues
- Federation: splitting into multiple dbs based on function 
- Sharding: splitting one dataset up across multiple hosts
- Moving some funtionality to other types of DBs (NoSQL, Graphs)


## Users more than 10 Million
- More fine tuning of your application
- More SOA of features/functionality
- Going from multi-AZ to multi-region
- Possibly start to build custom solutions
- Deep analysis of your entire stack
- Amazon EC2 container service
- AWS lambda


