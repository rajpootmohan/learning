------------------ CONNECT TO A DATABASE -----------------

sudo su postgres -c psql
psql -d postgres     // connect to postgres database

sudo /etc/init.d/postgresql reload
sudo service postgresql restart
sudo vi /etc/postgresql/9.3/main/pg_hba.conf

alter user postgres password 'postgres';
alter group postgres add user 'mohan';

\q               quit
\c dbname        connet to database
\c               from which db u r connected
\l               databases name
\d               tables name
\d tablename     describe table

------------------- SCHEMA LEVEL QUERY ----------------------

CREATE DATABASE dbname;   // create database from psql prompt
createdb dbname;          // create database from cmd prompt
DROP DATABASE [ IF EXISTS ] dbname // drop database if exists
dropdb dbname;           // drop db from cmd prompt
DROP TABLE table_name;    // drop table<s>





