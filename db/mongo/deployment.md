# Stats at the time of deployment

## mongostat
- checks the status of all running mongod instances and return counters of database operations.
- These counters include inserts, queries, updates, deletes, and cursors. Command also shows when you’re hitting page faults, and showcase your lock percentage.
- This means that you're running low on memory, hitting write capacity or have some performance issue.

## mongotop
- mongotop 30 : The above example will return values every 30 seconds.
- This command tracks and reports the read and write activity of MongoDB instance on a collection basis.
- By default, mongotop returns information in each second, which you can change it accordingly.
- You should check that this read and write activity matches your application intention, and you’re not firing too many writes to the database at a time, reading too frequently from a disk, or are exceeding your working set size.
