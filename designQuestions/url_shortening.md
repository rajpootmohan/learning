# Designing ​a​ ​URL​ ​Shortening​ ​service​ ​like​ ​TinyURL

## 1. Requirements

### Functional​ ​Requirements:
1. Given​ ​a​ ​URL,​ ​our​ ​service​ ​should​ ​generate​ ​a​ ​shorter​ ​and​ ​unique​ ​alias​ ​of​ ​it.
2. When​​ users ​​access ​​a ​​shorter ​​URL, ​​our ​​service ​​should ​​redirect ​​them ​​to ​​the ​​original ​​link.
3. Users​​ should​​ optionally​​ be​​ able​​ to​​ pick​​ a ​​custom​​ alias ​​for ​​their ​​URL.
4. Links ​​will ​​expire ​​after ​​a ​​specific ​​timespan ​​automatically;​ ​users ​​should​ ​also​ ​be ​​able ​​to ​​specify ​​expiration ​​time.

### Non-Functional​ ​Requirements:
1. The​ ​system​ ​should​ ​be​ ​highly​ ​available.​ ​This​ ​is​ ​required​ ​because​ ​if​ ​our​ ​service​ ​is​ ​down,​ ​all​ ​the​ ​URL​ ​redirections​ ​will​ ​start​ ​failing.
2. URL ​​redirection ​​should ​​happen ​​in ​​real-time ​​with​ ​minimum ​​latency.
3. Shortened ​​links ​​should ​​not ​​be​ ​guessable ​​( not ​​predictable).

### Extended​ ​Requirements:
1. Analytics,​ ​e.g.,​ ​how​ ​many​ ​times​ ​a​ ​redirection​ ​happened?
2. Our​​ service ​​should ​​also ​​be ​​accessible ​​through ​​REST ​​APIs ​​by ​​other​ ​services.


## 2. Capacity​ ​Estimation​ ​and​ ​Constraints
Our​ ​system​ ​would​ ​be​ ​read-heavy;​ ​there​ ​would​ ​be​ ​lots​ ​of​ ​redirection​ ​requests​ ​compared​ ​to​ ​new​ ​URL​ ​shortenings.​ ​Let’s​ ​assume​ ​100:1 ratio​ ​between​ ​read​ ​and​ ​write.

### 1. Traffic​ ​estimates:
If​ ​we​ ​assume​ ​that​ ​we​ ​would​ ​have​ ​500M​ ​new​ ​URLs​ ​shortenings​ ​per​ ​month,​ ​we​ ​can​ ​expect​ ​(100​ ​\*​ ​500M​ ​=>​ ​50B) redirections​ ​during​ ​the​ ​same​ ​time.​ ​What​ ​would​ ​be​ ​Queries​ ​Per​ ​Second​ ​(QPS)​ ​for​ ​our​ ​system?

1. New​ ​URLs​ ​shortenings​ ​per​ ​second:
`500​ ​million​ ​/​ ​(30​ ​days​ ​*​ ​24​ ​hours​ ​*​ ​3600​ ​seconds)​ ​~=​ ​200​ ​URLs/s`
2. URLs​ ​redirections​ ​per​ ​second:
`50​ ​billion​ ​/​ ​(30​ ​days​ ​*​ ​24​ ​hours​ ​*​ ​3600​ ​sec)​ ​~=​ ​19K/s`

### 2. Storage​ ​estimates:
Since​ ​we​ ​expect​ ​to​ ​have​ ​500M​ ​new​ ​URLs​ ​every​ ​month​ ​and​ ​if​ ​we​ ​would​ ​be​ ​keeping​ ​these​ ​objects​ ​for​ ​five​ ​years.

1. total​ ​number​ ​of​ ​objects​ ​we​ ​will​ ​be​ ​storing​ ​would​ ​be​ ​30​ ​billion.  
`500​ ​million​ ​*​ ​5​ ​years​ ​*​ ​12​ ​months​ ​=​ ​30​ ​billion`
2. Total Storage:  
`30​ ​billion​ ​*​ ​500​ ​bytes​ ​=​ ​15​ ​TB`

### 3. Bandwidth​ ​estimates:
1. For​ ​write​ ​requests,​ ​since​ ​every​ ​second​ ​we​ ​expect​ ​200​ ​new​ ​URLs,​ ​total​ ​incoming​ ​data​ ​for​ ​our​ ​service​ ​would​ ​be
100KB​ ​per​ ​second.
`200​ ​*​ ​500​ ​bytes​ ​=​ ​100​ ​KB/s`
2. For​ ​read​ ​requests,​ ​since​ ​every​ ​second​ ​we​ ​expect​ ​~19K​ ​URLs​ ​redirections,​ ​total​ ​outgoing​ ​data​ ​for​ ​our​ ​service​ ​would​ ​be​ ​9MB​ ​per​ ​second.
`19K​ ​*​ ​500​ ​bytes​ ​~=​ ​9​ ​MB/s`

### 4. Memory​ ​estimates:​​
If​ ​we​ ​want​ ​to​ ​cache​ ​some​ ​of​ ​the​ ​hot​ ​URLs​ ​that​ ​are​ ​frequently​ ​accessed,​ ​how​ ​much​ ​memory​ ​would​ ​we​ ​need​ ​to store​ ​them?​ ​If​ ​we​ ​follow​ ​the​ ​80-20​ ​rule,​ ​meaning​ ​20%​ ​of​ ​URLs​ ​generating​ ​80%​ ​of​ ​traffic,​ ​we​ ​would​ ​like​ ​to​ ​cache​ ​these​ ​20%​ ​hot​ ​URLs.

1. Since​ ​we​ ​have​ ​19K​ ​requests​ ​per​ ​second,​ ​we​ ​would​ ​be​ ​getting​ ​1.7billion​ ​requests​ ​per​ ​day.
`19K​ ​*​ ​3600​ ​seconds​ ​*​ ​24​ ​hours​ ​~=​ ​1.7​ ​billion`
2. To​ ​cache​ ​20%​ ​of​ ​these​ ​requests,​ ​we​ ​would​ ​need​ ​170GB​ ​of​ ​memory.
`0.2​ ​*​ ​1.7​ ​billion​ ​*​ ​500​ ​bytes​ ​~=​ ​170GB`

## 3. ​System​ ​APIs
We​ ​can​ ​have​ ​SOAP​ ​or​ ​REST​ ​APIs​ ​to​ ​expose​ ​the​ ​functionality​ ​of​ ​our​ ​service.​ ​Following​ ​could​ ​be​ ​the​ ​definitions​ ​of​ ​the​ ​APIs​ ​for​ ​creating and​ ​deleting​ ​URLs:
`creatURL(api_dev_key,​ ​original_url,​ ​custom_alias​=​None​ ​user_name​=​None,​ ​expire_date​=​None)`
`deleteURL(api_dev_key,​ ​url_key)`

## 4. Database​ ​Design

### Observations
1. We​ ​need​ ​to​ ​store​ ​billions​ ​of​ ​records.
2. Each​​ object ​​we​​ are ​​going ​​to ​​store​ ​is​ ​small​​ ( less​ ​than​ ​1K ).
3. There ​​are ​​no ​​relationships ​​between ​​records, ​​except ​​if ​​we ​​want ​​to ​​store ​​which​ ​user​ ​created​​ what​ ​URL.
4. Our ​​service​ ​is ​​read-heavy.

### Database Schema
We​ ​would​ ​need​ ​two​ ​tables
```
URL (
  hash varchar(16) primary key,
  origingalURL varchar(512),
  creationDate: datetime,
  expirationDate: datetime,
  userId: int
);
User (
  UserId: int primary key,
  Name: varchar(32),
  Email: varchar(32),
  CreationDate: datetime,
  LastLogin: datetime
);
```

### What​ ​kind​ ​of​ ​database​ ​should​ ​we​ ​use?​​
Since​ ​we​ ​are​ ​likely​ ​going​ ​to​ ​store​ ​billions​ ​of​ ​rows​ ​and​ ​we​ ​don’t​ ​need​ ​to​ ​use​ ​relationships between​ ​objects​ ​–​ ​a​ ​NoSQL​ ​key-value​ ​store​ ​like​ ​Dynamo​ ​or​ ​Cassandra​ ​is​ ​a​ ​better​ ​choice,​ ​which​ ​would​ ​also​ ​be​ ​easier​ ​to​ ​scale. If​​ we​ ​choose​ ​NoSQL,​​ we​ ​can not​ ​store​ ​UserID ​​in ​​the ​​URL ​​table ​​(as ​​there ​​are ​​no ​​foreign ​​keys​​ in NoSQL),​ ​for​ ​that​ ​we​ ​would​ ​need​ ​a​ ​third​ ​table​ ​which​ ​will​ ​store​ ​the​ ​mapping​ ​between​ ​URL​ ​and​ ​the​ ​user.

## 5. Basic​ ​System​ ​Design​ ​and​ ​Algorithm

### 1. Encoding​ ​actual​ ​URL
1. We​ ​can​ ​compute​ ​a​ ​unique​ ​hash​ ​(e.g.,​ ​​MD5​​ ​or​ ​​SHA256​,​ ​etc.)​ ​of​ ​the​ ​given​ ​URL.​
2. Using​ ​base64​ ​encoding,​ ​a​ ​6​ ​letter​ ​long​ ​key​ ​would​ ​result​ ​in​ ​64^6​ ​~=​ ​68.7​ ​billion​ ​possible​ ​strings.

#### Issues in this solution:
1. If​ ​multiple​ ​users​ ​enter​ ​the​ ​same​ ​URL,​ ​they​ ​can​ ​get​ ​the​ ​same​ ​shortened​ ​URL,​ ​which​ ​is​ ​not​ ​acceptable.
2. What​​ if ​​parts ​​of ​​the ​​URL​ ​are​ ​URL-encoded?​​e.g.,​​​https://github.com/search?q=user:rajpootmohan+learning, ​​and
https://github.com/search?q=user%3Arajpootmohan+learning ​are​ ​identical​ ​except​ ​for​ ​the​ ​URL​ ​encoding.

#### Workaround​ ​for​ ​the​ ​issues:​​ 
1. We​ ​can​ ​append​ ​an​ ​increasing​ ​sequence​ ​number​ ​to​ ​each​ ​input​ ​URL​ ​to​ ​make​ ​it​ ​unique​ ​and​ ​then​ ​generate a​ ​hash​ ​of​ ​it.​ ​We​ ​don’t​ ​need​ ​to​ ​store​ ​this​ ​sequence​ ​number​ ​in​ ​the​ ​databases,​ ​though.​ ​Possible​ ​problems​ ​with​ ​this​ ​approach​ ​could​ ​be how​ ​big​ ​this​ ​sequence​ ​number​ ​would​ ​be,​ ​can​ ​it​ ​overflow?​ ​Appending​ ​an​ ​increasing​ ​sequence​ ​number​ ​will​ ​impact​ ​the​ ​performance​ ​of the​ ​service​ ​too.
2. Another​ ​solution​ ​could​ ​be,​ ​to​ ​append​ ​user​ ​id​ ​(which​ ​should​ ​be​ ​unique)​ ​to​ ​the​ ​input​ ​URL.​ ​However,​ ​if​ ​the​ ​user​ ​has​ ​not​ ​signed​ ​in,​ ​we can​ ​ask​ ​the​ ​user​ ​to​ ​choose​ ​a​ ​uniqueness​ ​key.​ ​Even​ ​after​ ​this​ ​if​ ​we​ ​have​ ​a​ ​conflict,​ ​we​ ​have​ ​to​ ​keep​ ​generating​ ​a​ ​key​ ​until​ ​we​ ​get​ ​a unique​ ​one.

### 2. Generating Keys offline
We​ ​can​ ​have​ ​a​ ​standalone​ ​Key​ ​Generation​ ​Service​ ​(KGS)​ ​that​ ​generates​ ​random​ ​six​ ​letter​ ​strings​ ​beforehand​ ​and​ ​stores​ ​them​ ​in​ ​a database​ ​(let’s​ ​call​ ​it​ ​key-DB).​ ​Whenever​ ​we​ ​want​ ​to​ ​shorten​ ​a​ ​URL,​ ​we​ ​will​ ​just​ ​take​ ​one​ ​of​ ​the​ ​already​ ​generated​ ​keys​ ​and​ ​use​ ​it.​ ​This approach​ ​will​ ​make​ ​things​ ​quite​ ​simple​ ​and​ ​fast​ ​since​ ​we​ ​will​ ​not​ ​be​ ​encoding​ ​the​ ​URL​ ​or​ ​worrying​ ​about​ ​duplications​ ​or​ ​collisions. KGS​ ​will​ ​make​ ​sure​ ​all​ ​the​ ​keys​ ​inserted​ ​in​ ​key-DB​ ​are​ ​unique.

#### Can​ ​concurrency​ ​cause​ ​problems?​​
​As​ ​soon​ ​as​ ​a​ ​key​ ​is​ ​used,​ ​it​ ​should​ ​be​ ​marked​ ​in​ ​the​ ​database​ ​so​ ​that​ ​it​ ​doesn’t​ ​get​ ​used​ ​again.​ ​If there​ ​are​ ​multiple​ ​servers​ ​reading​ ​keys​ ​concurrently,​ ​we​ ​might​ ​get​ ​a​ ​scenario​ ​where​ ​two​ ​or​ ​more​ ​servers​ ​try​ ​to​ ​read​ ​the​ ​same​ ​key​ ​from the​ ​database.​ ​How​ ​can​ ​we​ ​solve​ ​this​ ​concurrency​ ​problem?

1. what would​ ​be​ ​the​ ​key-DB​ ​size?
2. Isn’t​ ​KGS​ ​the​ ​single​ ​point​ ​of​ ​failure?​​ ​
3. Can​ ​each​ ​app​ ​server​ ​cache​ ​some​ ​keys​ ​from​ ​key-DB?
4. How​ ​would​ ​we​ ​perform​ ​a​ ​key​ ​lookup?
5. Should​ ​we​ ​impose​ ​size​ ​limits​ ​on​ ​custom​ ​aliases?​​

## 6. Data​ ​Partitioning​ ​and​ ​Replication

### Range​ ​Based​ ​Partitioning:
1.  ​We​ ​can​ ​store​ ​URLs​ ​in​ ​separate​ ​partitions​ ​based​ ​on​ ​the​ ​first​ ​letter​ ​of​ ​the​ ​URL​ ​or​ ​the​ ​hash​ ​key.
2. ​We​ ​can​ ​even​ ​combine​ ​certain​ ​less​ ​frequently​ ​occurring​ ​letters​ ​into​ ​one​ ​database​ ​partition.
3. The​ ​main​ ​problem​ ​with​ ​this​ ​approach​ ​is​ ​that​ ​it​ ​can​ ​lead​ ​to​ ​unbalanced​ ​servers,​ ​for​ ​instance;​ ​if​ ​we​ ​decide​ ​to​ ​put​ ​all​ ​URLs​ ​starting​ ​with letter​ ​‘E’​ ​into​ ​a​ ​DB​ ​partition,​ ​but​ ​later​ ​we​ ​realize​ ​that​ ​we​ ​have​ ​too​ ​many​ ​URLs​ ​that​ ​start​ ​with​ ​letter​ ​‘E’,​ ​which​ ​we​ ​can’t​ ​fit​ ​into​ ​one​ ​DB partition.

### Hash-Based​ ​Partitioning:
1. we​ ​take​ ​a​ ​hash​ ​of​ ​the​ ​object​ ​we​ ​are​ ​storing,​ ​and​ ​based​ ​on​ ​this​ ​hash​ ​we​ ​figure​ ​out​ ​the​ ​DB partition​ ​to​ ​which​ ​this​ ​object​ ​should​ ​go.​
2. This​ ​approach​ ​can​ ​still​ ​lead​ ​to​ ​overloaded​ ​partitions,​ ​which​ ​can​ ​be​ ​solved​ ​by​ ​using​ ​​Consistent​ ​Hashing​.

## 7. Cache

### How​ ​much​ ​cache​ ​should​ ​we​ ​have?
We​ ​can​ ​start​ ​with​ ​20%​ ​of​ ​daily​ ​traffic​ ​and​ ​based​ ​on​ ​clients’​ ​usage​ ​pattern​ ​we​ ​can​ ​adjust​ ​how many​ ​cache​ ​servers​ ​we​ ​need.​ ​As​ ​estimated​ ​above​ ​we​ ​need​ ​170GB​ ​memory​ ​to​ ​cache​ ​20%​ ​of​ ​daily​ ​traffic​ ​since​ ​a​ ​modern​ ​day​ ​server​ ​can have​ ​256GB​ ​memory,​ ​we​ ​can​ ​easily​ ​fit​ ​all​ ​the​ ​cache​ ​into​ ​one​ ​machine,​ ​or​ ​we​ ​can​ ​choose​ ​to​ ​use​ ​a​ ​couple​ ​of​ ​smaller​ ​servers​ ​to​ ​store​ ​all these​ ​hot​ ​URLs.

### Which​ ​cache​ ​eviction​ ​policy​ ​would​ ​best​ ​fit​ ​our​ ​needs?
Least​ ​Recently​ ​Used​ ​(LRU)​ ​can​ ​be​ ​a​ ​reasonable​ ​policy​ ​for​ ​our​ ​system.

## 8. Load Balancer (LB)
We​ ​can​ ​add​ ​Load​ ​balancing​ ​layer​ ​at​ ​three​ ​places​ ​in​ ​our​ ​system:

1. Between​ ​Clients​ ​and​ ​Application​ ​servers
2. Between ​​Application ​​Servers ​​and ​​database​ ​servers
3. Between​ ​Application ​​Servers​ ​and​ ​Cache ​​servers

## 9. Purging​ ​or​ ​DB​ ​cleanup
Should​ ​entries​ ​stick​ ​around​ ​forever​ ​or​ ​should​ ​they​ ​be​ ​purged?​ ​If​ ​a​ ​user-specified​ ​expiration​ ​time​ ​is​ ​reached,​ ​what​ ​should​ ​happen​ ​to the​ ​link?​ ​If​ ​we​ ​chose​ ​to​ ​actively​ ​search​ ​for​ ​expired​ ​links​ ​to​ ​remove​ ​them,​ ​it​ ​would​ ​put​ ​a​ ​lot​ ​of​ ​pressure​ ​on​ ​our​ ​database.​ ​We​ ​can​ ​slowly remove​ ​expired​ ​links​ ​and​ ​do​ ​a​ ​lazy​ ​cleanup​ ​too.​ ​Our​ ​service​ ​will​ ​make​ ​sure​ ​that​ ​only​ ​expired​ ​links​ ​will​ ​be​ ​deleted,​ ​although​ ​some expired​ ​links​ ​can​ ​live​ ​longer​ ​but​ ​will​ ​never​ ​be​ ​returned​ ​to​ ​users.

1. Whenever​ ​a​ ​user​ ​tries​ ​to​ ​access​ ​an​ ​expired​ ​link,​ ​we​ ​can​ ​delete​ ​the​ ​link​ ​and​ ​return​ ​an​ ​error​ ​to​ ​the​ ​user.
2. A​ ​separate​ ​Cleanup​ ​service​ ​can​ ​run​ ​periodically​ ​to​ ​remove​ ​expired​ ​links​ ​from​ ​our​ ​storage​ ​and​ ​cache.​ ​This​ ​service​ ​should​ ​be​ ​very lightweight​ ​and​ ​can​ ​be​ ​scheduled​ ​to​ ​run​ ​only​ ​when​ ​the​ ​user​ ​traffic​ ​is​ ​expected​ ​to​ ​be​ ​low.
3. We​ ​can​ ​have​ ​a​ ​default​ ​expiration​ ​time​ ​for​ ​each​ ​link,​ ​e.g.,​ ​two​ ​years.
4. After​ ​removing​ ​an​ ​expired​ ​link,​ ​we​ ​can​ ​put​ ​the​ ​key​ ​back​ ​in​ ​the​ ​key-DB​ ​to​ ​be​ ​reused.
5. Should​ ​we​ ​remove​ ​links​ ​that​ ​haven’t​ ​been​ ​visited​ ​in​ ​some​ ​length​ ​of​ ​time,​ ​say​ ​six​ ​months?​ ​This​ ​could​ ​be​ ​tricky.​ ​Since​ ​storage​ ​is
getting​ ​cheap,​ ​we​ ​can​ ​decide​ ​to​ ​keep​ ​links​ ​forever.

## 10. Security​ ​and​ ​Permissions
Can​ ​users​ ​create​ ​private​ ​URLs​ ​or​ ​allow​ ​a​ ​particular​ ​set​ ​of​ ​users​ ​to​ ​access​ ​a​ ​URL?

1. We​ ​can​ ​store​ ​permission​ ​level​ ​(public/private)​ ​with​ ​each​ ​URL​ ​in​ ​the​ ​database.
2. ​We​ ​can​ ​also​ ​create​ ​a​ ​separate​ ​table​ ​to​ ​store​ ​UserIDs that​ ​have​ ​permission​ ​to​ ​see​ ​a​ ​specific​ ​URL.​
3. ​If​ ​a​ ​user​ ​does​ ​not​ ​have​ ​permission​ ​and​ ​try​ ​to​ ​access​ ​a​ ​URL,​ ​we​ ​can​ ​send​ ​an​ ​error​ ​(HTTP 401)​ ​back.​
4. ​Given​ ​that,​ ​we​ ​are​ ​storing​ ​our​ ​data​ ​in​ ​a​ ​NoSQL​ ​wide-column​ ​database​ ​like​ ​Cassandra,​ ​the​ ​key​ ​for​ ​the​ ​table​ ​storing permissions​ ​would​ ​be​ ​the​ ​‘Hash’​ ​(or​ ​the​ ​KGS​ ​generated​ ​‘key’),​ ​and​ ​the​ ​columns​ ​will​ ​store​ ​the​ ​UserIDs​ ​of​ ​those​ ​users​ ​that​ ​have permissions​ ​to​ ​see​ ​the​ ​URL.
