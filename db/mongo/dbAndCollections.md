# DB & Collection Commands

## Database Commands
- show dbs				: show all dbs
- use mydb				: create mydb if not exist else use mydb
- db 					: print on which db you are with
- db.dropDatabase()		: drop database
- db.help()				: print list of commands
- db.stats()			: list db statistics
- db.serverStatus()		: print server status


## Collection Commands
- show collections						: show all collections unders db
- db.createCollection("mycollection")	: create collection under db
- db.mycollection.drop()				: drop a collection under current db
- db.mycollection.insert(document)		: insert document into a collection
- db.mycollection.find()				: get all the documents in a non-structured way.
- db.mycollection.find().pretty()		: get all the documents in a structured way.
- db.mycollection.findOne()				: get only one document.