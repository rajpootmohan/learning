# Designing​ ​Instagram

## 1. ​Requirements

### Functional​ ​Requirements
1. Users​ ​should​ ​be​ ​able​ ​to​ ​upload/download/view​ ​photos.
2. Users ​​can ​​perform ​​searches ​​based ​​on ​​photo/video​​titles.
3. Users​​ can​ ​follow​ ​other ​​users.
4. The ​​system ​​should ​​be ​​able ​​to ​​generate ​​and ​​display ​​a ​​user’s ​​timeline ​​consisting ​​of ​​top​ ​photos​ ​from​​ all​ ​the ​​people ​​the​ ​user ​​follows.

### Non-functional​ ​Requirements
1. Our​ ​service​ ​needs​ ​to​ ​be​ ​highly​ ​available.
2. The​​ acceptable​ ​latency​ ​of ​​the ​​system​​ is ​​200ms​ ​for​​ timeline ​​generation.
3. Consistency​ ​can​ ​take​ ​a​ ​hit​​(in​​ the ​​interest​ ​of​ ​availability),​ ​if​​ a​ ​user ​​doesn’t ​​see​ ​a ​​photo ​​for ​​a​ ​while, ​​it ​​should ​​be ​​fine.
4. The​​ system​​ should​ ​be​ ​highly ​​reliable,​ ​any​ ​photo/video ​​uploaded ​​should​ ​not ​​be ​​lost.

## 2. ​Some​ ​Design​ ​Considerations
1. Practically​ ​users​ ​can​ ​upload​ ​as​ ​many​ ​photos​ ​as​ ​they​ ​like.​ ​Efficient​ ​management​ ​of​ ​storage​ ​should​ ​be​ ​a​ ​crucial​ ​factor​ ​while designing​ ​this​ ​system.
2. Low ​​latency​ ​is ​​expected​ ​while​ ​reading​ ​images.
3. Data ​​should ​​be ​​100% ​​reliable. ​​If ​​a ​​user ​​uploads ​​an ​​image, ​​the ​​system​ ​will​ ​guarantee ​​that ​​it ​​will ​​never ​​be ​​lost.

## 3. Capacity​ ​Estimation​ ​and​ ​Constraints
1. Let’s​ ​assume​ ​we​ ​have​ ​300M​ ​total​ ​users,​ ​with​ ​1M​ ​daily​ ​active​ ​users.
2. 2M​ ​new​ ​photos​ ​every​ ​day,​ ​23​ ​new​ ​photos​ ​every​ ​second.
3. Average​ ​photo​ ​file​ ​size​ ​=>​ ​200KB
4. Total​ ​space​ ​required​ ​for​ ​1​ ​day​ ​of​ ​photos. `2M​ ​*​ ​200KB​ ​=>​ ​400​ ​GB`
5. Total​ ​space​ ​required​ ​for​ ​5​ ​years: `400GB​ ​*​ ​365​ ​(days​ ​a​ ​year)​ ​*​ ​5​ ​(years)​ ​~=​ ​712​ ​TB`

## 4. High​ ​Level​ ​System​ ​Design
At​ ​a​ ​high-level,​ ​we​ ​need​ ​to​ ​support​ ​two​ ​scenarios,​ ​one​ ​to​ ​upload​ ​photos​ ​and​ ​the​ ​other​ ​to​ ​view/search​ ​photos.​ ​Our​ ​service​ ​would​ ​need some​ ​block​ ​storage​ ​servers​ ​to​ ​store​ ​photos​ ​and​ ​also​ ​some​ ​database​ ​servers​ ​to​ ​store​ ​metadata​ ​information​ ​about​ ​the​ ​photos.

## 5. Database​ ​Schema
```
Photo (
  PhotoId: int primary key,	
  UserId: int,
  PhotoPath: varchar(256),
  PhotoLatitude: int,
  PhotoLongitude: int,
  UserLatitude: int,
  UserLongitude: int,
  creationDate: datetime,
);
User (
  UserId: int primary key,
  Name: varchar(32),
  Email: varchar(32),
  CreationDate: datetime,
  LastLogin: datetime
);
UserFollow (
  UserId1: int,
  UserId2: int
);
```
1. One​ ​simple​ ​approach​ ​for​ ​storing​ ​the​ ​above​ ​schema​ ​would​ ​be​ ​to​ ​use​ ​an​ ​RDBMS​ ​like​ ​MySQL​ ​since​ ​we​ ​require​ ​joins.​ ​But​ ​relational databases​ ​come​ ​with​ ​their​ ​challenges,​ ​especially​ ​when​ ​we​ ​need​ ​to​ ​scale​ ​them.​ ​For​ ​details,​ ​please​ ​take​ ​a​ ​look​ ​at​ ​​SQL​ ​vs.​ ​NoSQL​.
2. We​ ​can​ ​store​ ​photos​ ​in​ ​a​ ​distributed​ ​file​ ​storage​ ​like​ H​​DFS​​ ​or​ ​​S3​.
3. We​ ​can​ ​store​ ​the​ ​above​ ​schema​ ​in​ ​a​ ​distributed​ ​key-value​ ​store​ ​to​ ​enjoy​ ​benefits​ ​offered​ ​by​ ​NoSQL.​ ​All​ ​the​ ​metadata​ ​related​ ​to​ ​photos can​ ​go​ ​to​ ​a​ ​table,​ ​where​ ​the​ ​‘key’​ ​would​ ​be​ ​the​ ​‘PhotoID’​ ​and​ ​the​ ​‘value’​ ​would​ ​be​ ​an​ ​object​ ​containing​ ​PhotoLocation,​ ​UserLocation, CreationTimestamp,​ ​etc.
4. We​ ​also​ ​need​ ​to​ ​store​ ​relationships​ ​between​ ​users​ ​and​ ​photos,​ ​to​ ​know​ ​who​ ​owns​ ​which​ ​photo.​ ​Another​ ​relationship​ ​we​ ​would​ ​need​ ​to store ​​is ​​the​ ​list​ ​of​ ​people ​​a ​​user ​​follows. ​​For​ ​both ​​of ​​these​ ​tables,​ ​we ​​can ​​use ​​a​​wide-column ​​data store ​​like ​Cassandra​. ​​For ​​the ‘UserPhoto’​ ​table,​ ​the​ ​‘key’​ ​would​ ​be​ ​‘UserID’​ ​and​ ​the​ ​‘value’​ ​would​ ​be​ ​the​ ​list​ ​of​ ​‘PhotoIDs’​ ​the​ ​user​ ​owns,​ ​stored​ ​in​ ​different​ ​columns. We​ ​will​ ​have​ ​a​ ​similar​ ​scheme​ ​for​ ​the​ ​‘UserFollow’​ ​table.

## 6. Component Design
1. Writes​ ​or​ ​photo​ ​uploads​ ​could​ ​be​ ​slow​ ​as​ ​they​ ​have​ ​to​ ​go​ ​to​ ​the​ ​disk,​ ​whereas​ ​reads​ ​could​ ​be​ ​faster​ ​if​ ​they​ ​are​ ​being​ ​served​ ​from​ ​cache.
2. Uploading​ ​users​ ​can​ ​consume​ ​all​ ​the​ ​connections,​ ​as​ ​uploading​ ​would​ ​be​ ​a​ ​slower​ ​process.​ ​This​ ​means​ ​reads​ ​cannot​ ​be​ ​served​ ​if​ ​the system​ ​gets​ ​busy​ ​with​ ​all​ ​the​ ​write​ ​requests.​ ​To​ ​handle​ ​this​ ​bottleneck​ ​we​ ​can​ ​split​ ​out​ ​read​ ​and​ ​writes​ ​into​ ​separate​ ​services.
3. Separating​ ​image​ ​read​ ​and​ ​write​ ​requests​ ​will​ ​also​ ​allow​ ​us​ ​to​ ​scale​ ​or​ ​optimize​ ​each​ ​of​ ​them​ ​independently.

## 7. Reliability​ ​and​ ​Redundancy
1. Losing​ ​files​ ​is​ ​not​ ​an​ ​option​ ​for​ ​our​ ​service.​ ​Therefore,​ ​we​ ​will​ ​store​ ​multiple​ ​copies​ ​of​ ​each​ ​file,​ ​so​ ​that​ ​if​ ​one​ ​storage​ ​server​ ​dies,​ ​we can​ ​retrieve​ ​the​ ​image​ ​from​ ​the​ ​other​ ​copy​ ​present​ ​on​ ​a​ ​different​ ​storage​ ​server.
2. If​ ​only​ ​one​ ​instance​ ​of​ ​a​ ​service​ ​is​ ​required​ ​to​ ​be​ ​running​ ​at​ ​any​ ​point,​ ​we​ ​can​ ​run​ ​a​ ​redundant​ ​secondary​ ​copy​ ​of​ ​the​ ​service​ ​that​ ​is not​ ​serving​ ​any​ ​traffic​ ​but​ ​whenever​ ​primary​ ​has​ ​any​ ​problem​ ​it​ ​can​ ​take​ ​control​ ​after​ ​the​ ​failover.
3. Creating​ ​redundancy​ ​in​ ​a​ ​system​ ​can​ ​remove​ ​single​ ​points​ ​of​ ​failure​ ​and​ ​provide​ ​a​ ​backup​ ​or​ ​spare​ ​functionality​ ​if​ ​needed​ ​in​ ​a​ ​crisis.

## 8. Data​ ​Sharding

### 1. Partitioning​ ​based​ ​on​ ​UserID
1. Let’s​ ​assume​ ​we​ ​shard​ ​based​ ​on​ ​the​ ​UserID​ ​so​ ​that​ ​we​ ​can​ ​keep​ ​all​ ​photos​ ​of​ ​a​ ​user​ ​on​ ​the​ ​same
shard.​ ​If​ ​one​ ​DB​ ​shard​ ​is​ ​4TB,​ ​we​ ​will​ ​have​ ​712/4​ ​=>​ ​178​ ​shards.​ ​Let’s​ ​assume​ ​for​ ​future​ ​growths​ ​we​ ​keep​ ​200​ ​shards.
2. So​ ​we​ ​will​ ​find​ ​the​ ​shard​ ​number​ ​by​ ​UserID​ ​%​ ​200​ ​and​ ​then​ ​store​ ​the​ ​data​ ​there.​ ​To​ ​uniquely​ ​identify​ ​any​ ​photo​ ​in​ ​our​ ​system,​ ​we​ ​can append​ ​shard​ ​number​ ​with​ ​each​ ​PhotoID.

#### What​ ​are​ ​different​ ​issues​ ​with​ ​this​ ​partitioning​ ​scheme?
1. How​ ​would​ ​we​ ​handle​ ​hot​ ​users?​ ​Several​ ​people​ ​follow​ ​such​ ​hot​ ​users,​ ​and​ ​any​ ​photo​ ​they​ ​upload​ ​is​ ​seen​ ​by​ ​a​ ​lot​ ​of​ ​other people.
2. Some​​ users​ ​will ​​have​​ a​​ lot ​​of ​​photos​ ​compared ​​to ​​others, ​​thus ​​making ​​a ​​non-uniform ​​distribution​ ​of ​​storage.
3. What ​​if​ ​we​ ​can not​​ store ​​all ​​pictures ​​of​ ​a​​ user ​​on ​​one ​​shard?​ ​If ​​we​ ​distribute​ ​photos​ ​of ​​a ​​user ​​on to​​ multiple ​​shards,​ ​will ​​it​ ​cause higher​ ​latencies?
4. Storing ​​all ​​pictures ​​of ​​a ​​user ​​on​ ​one ​​shard ​​can ​​cause ​​issues ​​like​ ​unavailability​ ​of​ ​all​ ​of​ ​the ​​user’s​ ​data​ ​if ​​that ​​shard​​ is​ ​down​ ​or higher​ ​latency​ ​if​ ​it​ ​is​ ​serving​ ​high​ ​load​ ​etc.

### 2. ​Partitioning​ ​based​ ​on​ ​PhotoID
​If​ ​we​ ​can​ ​generate​ ​unique​ ​PhotoIDs​ ​first​ ​and​ ​then​ ​find​ ​shard​ ​number​ ​through​ ​PhotoID​ ​%​ ​200,​ ​this can​ ​solve​ ​above​ ​problems.​ ​We​ ​would​ ​not​ ​need​ ​to​ ​append​ ​ShardID​ ​with​ ​PhotoID​ ​in​ ​this​ ​case​ ​as​ ​PhotoID​ ​will​ ​itself​ ​be​ ​unique throughout​ ​the​ ​system.

#### How can we generate photo Ids?
[https://github.com/rajpootmohan/learning/blob/master/designQuestions/random_id_generation.md#how-to-get-distributed-unique-primary-keys](https://github.com/rajpootmohan/learning/blob/master/designQuestions/random_id_generation.md#how-to-get-distributed-unique-primary-keys)

## 9. Ranking​ ​and​ ​Timeline​ ​Generation
To​ ​create​ ​the​ ​timeline​ ​for​ ​any​ ​given​ ​user,​ ​we​ ​need​ ​to​ ​fetch​ ​the​ ​latest,​ ​most​ ​popular​ ​and​ ​relevant​ ​photos​ ​of​ ​other​ ​people​ ​the​ ​user follows.
1. ​Our​ ​application​ ​server​ ​will​ ​first​ ​get​ ​a​ ​list​ ​of​ ​people the​ ​user​ ​follows​ ​and​ ​then​ ​fetches​ ​metadata​ ​info​ ​of​ ​latest​ ​100​ ​photos​ ​from​ ​each​ ​user.
2. the​ ​server​ ​will​ ​submit​ ​all​ ​these photos​ ​to​ ​our​ ​ranking​ ​algorithm​ ​which​ ​will​ ​determine​ ​the​ ​top​ ​100​ ​photos​ ​(based​ ​on​ ​recency,​ ​likeness,​ ​etc.)​ ​to​ ​be​ ​returned​ ​to​ ​the​ ​user.
3. A​ ​possible​ ​problem​ ​with​ ​this​ ​approach​ ​would​ ​be​ ​higher​ ​latency,​ ​as​ ​we​ ​have​ ​to​ ​query​ ​multiple​ ​tables​ ​and​ ​perform sorting/merging/ranking​ ​on​ ​the​ ​results.​

### Pre-generating​ ​the​ ​timeline: 
1. We​ ​can​ ​have​ ​dedicated​ ​servers​ ​that​ ​are​ ​continuously​ ​generating​ ​users’​ ​timelines​ ​and​ ​storing​ ​them​ ​in​ ​a ‘UserTimeline’​ ​table.​ ​So​ ​whenever​ ​any​ ​user​ ​needs​ ​the​ ​latest​ ​photos​ ​for​ ​their​ ​timeline,​ ​we​ ​will​ ​simply​ ​query​ ​this​ ​table​ ​and​ ​return​ ​the results​ ​to​ ​the​ ​user.
2. Whenever​ ​these​ ​servers​ ​need​ ​to​ ​generate​ ​the​ ​timeline​ ​of​ ​a​ ​user,​ ​they​ ​will​ ​first​ ​query​ ​the​ ​UserTimeline​ ​table​ ​to​ ​see​ ​what​ ​was​ ​the​ ​last time​ ​the​ ​timeline​ ​was​ ​generated​ ​for​ ​that​ ​user.​ ​Then,​ ​new​ ​timeline​ ​data​ ​will​ ​be​ ​generated​ ​from​ ​that​ ​time​ ​onwards​.

## 10. Timeline​ ​Creation​ ​with​ ​Sharded​ ​Data
1. To​ ​create​ ​the​ ​timeline​ ​for​ ​any​ ​given​ ​user,​ ​one​ ​of​ ​the​ ​most​ ​important​ ​requirements​ ​is​ ​to​ ​fetch​ ​latest​ ​photos​ ​from​ ​all​ ​people​ ​the​ ​user follows.​ ​For​ ​this,​ ​we​ ​need​ ​to​ ​have​ ​a​ ​mechanism​ ​to​ ​sort​ ​photos​ ​on​ ​their​ ​time​ ​of​ ​creation.​ ​This​ ​can​ ​be​ ​done​ ​efficiently​ ​if​ ​we​ ​can​ ​make photo​ ​creation​ ​time​ ​part​ ​of​ ​the​ ​PhotoID.​ ​Since​ ​we​ ​will​ ​have​ ​a​ ​primary​ ​index​ ​on​ ​PhotoID,​ ​it​ ​will​ ​be​ ​quite​ ​quick​ ​to​ ​find​ ​latest​ ​PhotoIDs.
2. We​ ​can​ ​use​ ​epoch​ ​time​ ​for​ ​this.​ ​Let’s​ ​say​ ​our​ ​PhotoID​ ​will​ ​have​ ​two​ ​parts;​ ​the​ ​first​ ​part​ ​will​ ​be​ ​representing​ ​epoch​ ​seconds​ ​and​ ​the second​ ​part​ ​will​ ​be​ ​an​ ​auto-incrementing​ ​sequence.​ ​So​ ​to​ ​make​ ​a​ ​new​ ​PhotoID,​ ​we​ ​can​ ​take​ ​the​ ​current​ ​epoch​ ​time​ ​and​ ​append​ ​an auto​ ​incrementing​ ​ID​ ​from​ ​our​ ​key​ ​generating​ ​DB.​ ​We​ ​can​ ​figure​ ​out​ ​shard​ ​number​ ​from​ ​this​ ​PhotoID​ ​(​ ​PhotoID​ ​%​ ​200)​ ​and​ ​store​ ​the photo​ ​there.
3. __What​ ​could​ ​be​ ​the​ ​size​ ​of​ ​our​ ​PhotoID?​__ ​ ​Let’s​ ​say​ ​our​ ​epoch​ ​time​ ​starts​ ​today,​ ​how​ ​many​ ​bits​ ​we​ ​would​ ​need​ ​to​ ​store​ ​the​ ​number​ ​of seconds​ ​for​ ​next​ ​50​ ​years?
86400​ ​sec/day​ ​*​ ​365​ ​(days​ ​a​ ​year)​ ​*​ ​50​ ​(years)​ ​=>​ ​1.6​ ​billion​ ​seconds
We​ ​would​ ​need​ ​31​ ​bits​ ​to​ ​store​ ​this​ ​number.​ ​Since​ ​on​ ​the​ ​average,​ ​we​ ​are​ ​expecting​ ​23​ ​new​ ​photos​ ​per​ ​second;​ ​we​ ​can​ ​allocate​ ​9​ ​bits​ ​to store​ ​auto​ ​incremented​ ​sequence.​ ​So​ ​every​ ​second​ ​we​ ​can​ ​store​ ​(2^9​ ​=>​ ​512)​ ​new​ ​photos.​ ​We​ ​can​ ​reset​ ​our​ ​auto​ ​incrementing sequence​ ​every​ ​second.

## 11. Cache​ ​and​ ​Load​ ​balancing
1. To​ ​serve​ ​globally​ ​distributed​ ​users,​ ​our​ ​service​ ​needs​ ​a​ ​massive-scale​ ​photo​ ​delivery​ ​system.​ ​Our​ ​service​ ​should​ ​push​ ​its​ ​content​ ​closer to​ ​the​ ​user​ ​using​ ​a​ ​large​ ​number​ ​of​ ​geographically​ ​distributed​ ​photo​ ​cache​ ​servers​ ​and​ ​use​ ​CDNs​
2. We​ ​can​ ​introduce​ ​a​ ​cache​ ​for​ ​metadata​ ​servers​ ​to​ ​cache​ ​hot​ ​database​ ​rows.​ ​We​ ​can​ ​use​ ​Memcache​ ​to​ ​cache​ ​the​ ​data​ ​and​ ​Application servers​ ​before​ ​hitting​ ​database​ ​can​ ​quickly​ ​check​ ​if​ ​the​ ​cache​ ​has​ ​desired​ ​rows.​ ​Least​ ​Recently​ ​Used​ ​(LRU)​ ​can​ ​be​ ​a​ ​reasonable​ ​cache eviction​ ​policy​ ​for​ ​our​ ​system.​ ​Under​ ​this​ ​policy,​ ​we​ ​discard​ ​the​ ​least​ ​recently​ ​viewed​ ​row​ ​first.
3. __How​ ​can​ ​we​ ​build​ ​more​ ​intelligent​ ​cache?__​​ ​If​ ​we​ ​go​ ​with​ ​80-20​ ​rule,​ ​i.e.,​ ​20%​ ​of​ ​daily​ ​read​ ​volume​ ​for​ ​photos​ ​is​ ​generating​ ​80%​ ​of traffic​ ​which​ ​means​ ​that​ ​certain​ ​photos​ ​are​ ​so​ ​popular​ ​that​ ​the​ ​majority​ ​of​ ​people​ ​reads​ ​them.​ ​This​ ​dictates​ ​we​ ​can​ ​try​ ​caching​ ​20%​ ​of daily​ ​read​ ​volume​ ​of​ ​photos​ ​and​ ​metadata.