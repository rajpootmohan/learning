# Aggregation

db.COLLECTION_NAME.aggregate(AGGREGATE_OPERATION)
```
db.mycol.aggregate([
	group :
		 {
		 	_id : "$by_user",
		 	num_tutorial :
		 		{
		 			$sum : 1
		 		}
		 	}
		 }
	])
```

## Aggregation Method

### $sum
Sums up the defined value from all documents in the collection.
db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : "$likes"}}}])

### $avg
Calculates the average of all given values from all documents in the collection.
db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$avg : "$likes"}}}])

### $min
Gets the minimum of the corresponding values from all documents in the collection.
db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$min : "$likes"}}}])

### $max
Gets the maximum of the corresponding values from all documents in the collection.
db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$max : "$likes"}}}])

### $push
Inserts the value to an array in the resulting document.
db.mycol.aggregate([{$group : {_id : "$by_user", url : {$push: "$url"}}}])

### $addToSet
Inserts the value to an array in the resulting document but does not create duplicates.
db.mycol.aggregate([{$group : {_id : "$by_user", url : {$addToSet : "$url"}}}])

### $first
Gets the first document from the source documents according to the grouping. Typically this makes only sense together with some previously applied “$sort”-stage.
db.mycol.aggregate([{$group : {_id : "$by_user", first_url : {$first : "$url"}}}])

### $last
Gets the last document from the source documents according to the grouping. Typically this makes only sense together with some previously applied “$sort”-stage.
db.mycol.aggregate([{$group : {_id : "$by_user", last_url : {$last : "$url"}}}])


## Pipeline Concept

