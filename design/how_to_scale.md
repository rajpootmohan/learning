# How to Scale Solution

## 1. Scalability  
- what it would take to make a web service massively scalable?

## 2. Clone
- Public servers of a scalable web service are hidden behind a load balancer.  This load balancer evenly   distributes load (requests from your users) onto your group/cluster of application servers.

## 3. Database
- First path is to stick with MySQL and keep the “beast” running. Hire a database administrator (DBA,) tell him to do master-slave replication (read from slaves, write to master) and upgrade your master server by adding RAM, RAM and more RAM.
- Second path means to denormalize right from the beginning and include no more Joins in any database query. You can stay with MySQL, and use it like a NoSQL database, or you can switch to a better and easier to scale NoSQL database like MongoDB or CouchDB.

## 4. Cache
- With “cache” I always mean in-memory caches like Memcached or Redis.
- There are 2 patterns of caching your data. An old one and a new one:
	1. Cached Database Queries
    2. Cached Objects

## 5. Asynchronism
- This pre-computing of overall general data can extremely improve websites and web apps and makes them very scalable and performant. Just imagine the scalability of your website if the script would upload these pre-rendered HTML pages to AWS S3 or Cloudfront or another Content Delivery Network! Your website would be super responsive and could handle millions of visitors per hour!
- A user comes to your website and starts a very computing intensive task which would take several minutes to finish. So the frontend of your website sends a job onto a job queue and immediately signals back to the user: your job is in work, please continue to the browse the page. The job queue is constantly checked by a bunch of workers for new jobs. If there is a new job then the worker does the job and after some minutes sends a signal that the job was done. The frontend, which constantly checks for new “job is done” - signals, sees that the job was done and informs the user about it. I know, that was a very simplified example.

## 6. References
- [https://www.lecloud.net/tagged/scalability](https://www.lecloud.net/tagged/scalability)