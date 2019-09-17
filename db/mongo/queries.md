# Queries

## Find method
- db.mycollection.find(query, fields, limit, skip, batchSize, options)
- db.mycollection.find({}, {KEY:1})
- db.mycollection.find({}, {KEY:1}, 5)
- db.mycollection.find({}, {KEY:1}, 5, 3)
- db.mycollection.find().limit(NUMBER)
- db.mycollection.find().limit(NUMBER).skip(NUMBER)
- db.mycollection.find().limit(NUMBER).skip(NUMBER).sort({key: 1})		: 1 means asc, -1 desc


## Update 
- db.mycollection.update(SELECTION_CRITERIA, UPDATED_DATA)

### Example (updating only fist matched document)
db.mycollection.update({'title':'MongoDB Overview'},{$set:{'title':'New MongoDB Tutorial'}});

### Example (updation all matched documents)
db.mycollection.update({'title':'MongoDB Overview'},{$set:{'title':'New MongoDB Tutorial'}},{multi:true})

## Save
- db.mycollection.save({_id:ObjectId(), NEW_DATA})
-

## Remove
- db.mycollection.remove({})					: remove all
- db.mycollection.remove(DELETION_CRITERIA)		: remoing all matched documents
- db.mycollection.remove(DELETION_CRITERIA,1)	: remove fist matched documents

## RDBMS Where Clause Equivalents in MongoDB
To query the document on the basis of some condition, you can use following operations.

----------------------------------------------------------------------------------------------------------------------------------
|Operations				|Syntax					|Example											|RDBMS Equivalent             | 
----------------------------------------------------------------------------------------------------------------------------------|
|Equality				|{<key>:<value>}		|db.mycol.find({"by":"tutorials point"}).pretty()	|where by = 'tutorials point' |
|Less Than				|{<key>:{$lt:<value>}}	|db.mycol.find({"likes":{$lt:50}}).pretty()			|where likes < 50             |
|Less Than Equals		|{<key>:{$lte:<value>}}	|db.mycol.find({"likes":{$lte:50}}).pretty()		|where likes <= 50            |
|Greater Than			|{<key>:{$gt:<value>}}	|db.mycol.find({"likes":{$gt:50}}).pretty()			|where likes > 50             |
|Greater Than Equals	|{<key>:{$gte:<value>}}	|db.mycol.find({"likes":{$gte:50}}).pretty()		|where likes >= 50            |
|Not Equals				|{<key>:{$ne:<value>}}	|db.mycol.find({"likes":{$ne:50}}).pretty()			|where likes != 50            |
----------------------------------------------------------------------------------------------------------------------------------

## AND in MongoDB
db.mycollection.find(
   {
      $and: [
         {key1: value1}, {key2:value2}
      ]
   }
)

## OR in MongoDB
db.mycollection.find(
   {
      $or: [
         {key1: value1}, {key2:value2}
      ]
   }
)

## Using AND and OR Together
db.mycol.find(
	{
		"likes": {$gt:10},
	 	$or: [
	 		{"by": "tutorials point"},
   			{"title": "MongoDB Overview"}
   		]
   	}
)
