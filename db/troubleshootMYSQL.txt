1. Change in mysqld.cnf
	#slow_query_log         = 1
	#slow_query_log_file    = /var/log/mysql/mysql-slow.log
	#long_query_time = 2
	#log-queries-not-using-indexes

2. If mysql is binding to only local machine
	bind-address            = 127.0.0.1

3. Fix corrupted tables in mysql
	A. MyISAM storage engine:
		CHECK TABLE table_name;
		REPAIR TABLE table_name;
	B. InnoDB
		enable InnoDB's force_recovery option. You can do this by editing the mysqld.cnf file.
		innodb_force_recovery=1

