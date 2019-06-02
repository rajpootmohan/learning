# How to test a running distributed system

## Netflix used below services:

### CHAOS MONKEY ###
Chaos Monkey, a tool that randomly disables our production instances to make sure
we can survive this common type of failure without any customer impact.

### LATENCY MONKEY ###
Latency Monkey induces artificial delays in our RESTful client-server communication layer to simulate service degradation and measures if upstream services
respond appropriately.

### CONFORMITY MONKEY ###
Conformity Monkey finds instances that don’t adhere to best-practices and
shuts them down. For example, we know that if we find instances that don’t belong to an
auto-scaling group, that’s trouble waiting to happen. We shut them down to give the service
owner the opportunity to re-launch them properly.

### DOCTOR MONKEY ###
Doctor Monkey taps into health checks that run on each instance as well as
monitors other external signs of health (e.g. CPU load) to detect unhealthy instances.

### JANITOR MONKEY ###
Janitor Monkey ensures that our cloud environment is running free of 
clutter and waste. It searches for unused resources and disposes of them.

### SECURITY MONKEY ###
Security Monkey is an extension of Conformity Monkey. It finds security
violations or vulnerabilities, such as improperly configured AWS security groups, and
terminates the offending instances. It also ensures that all our SSL and DRM certificates
are valid and are not coming up for renewal.

### 10-18 MONKEY ###
10–18 Monkey (short for Localization-Internationalization, or l10n-i18n)
detects configuration and run time problems in instances serving customers in multiple
geographic regions, using different languages and character sets.

### CHAOS MONKEY ###
Chaos Gorilla is similar to Chaos Monkey, but simulates an outage of an
entire Amazon availability zone. We want to verify that our services automatically re-balance
to the functional availability zones without user-visible impact or manual intervention.

