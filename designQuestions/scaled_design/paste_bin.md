# Designing Pastebin

## 1. What is pastebin?
Pastebin​ ​like​ ​services​ ​enable​ ​users​ ​to​ ​store​ ​plain​ ​text​ ​or​ ​images​ ​over​ ​the​ ​network​ ​and​ ​generate​ ​unique​ ​URLs​ ​to access​ ​the​ ​uploaded​ ​data.​ ​Such​ ​services​ ​are​ ​also​ ​used​ ​to​ ​share​ ​data​ ​over​ ​the​ ​network​ ​quickly,​ ​as​ ​users​ ​would​ ​just​ ​need​ ​to​ ​pass​ ​the​ ​URL to​ ​let​ ​other​ ​users​ ​see​ ​it.

## 2. Requirements

### Functional​ ​Requirements:
1. Users​ ​should​ ​be​ ​able​ ​to​ ​upload​ ​or​ ​“paste”​ ​their​ ​data​ ​and​ ​get​ ​a​ ​unique​ ​URL​ ​to​ ​access​ ​it.
2. Users​​ will ​​only ​​be​ ​able​ ​to​ ​upload ​​text.
3. Data ​​and ​​links ​​will​ ​expire ​​after​ a ​​specific ​​timespan ​​automatically; users ​should ​​also ​​be ​​able ​​to​ ​specify ​​expiration ​​time.
4. Users​ ​should ​​optionally ​​be ​​able​ ​to ​​pick​ ​a ​​custom​​ alias​​ for ​​their​ ​paste.

### Non-Functional​ ​Requirements:
1. The​ ​system​ ​should​ ​be​ ​highly​ ​reliable,​ ​any​ ​data​ ​uploaded​ ​should​ ​not​ ​be​ ​lost.
2. The​​ system ​​should ​​be ​​highly​ ​available. ​​This ​​is ​​required ​​because​ ​if ​​our​ ​service ​​is​ ​down, ​​users ​​will​ ​not​ ​be ​​able ​​to​ ​access ​​their Pastes.
3. Users ​​should ​​be ​​able ​​to ​​access ​​their ​​Pastes ​​in​ ​real-time ​​with​​ minimum ​​latency.
4. Paste​ ​links​ ​should​ ​not ​​be ​​guessable ​​( not​ ​predictable ).

### Extended​ ​Requirements:
1. Analytics,​ ​e.g.,​ ​how​ ​many​ ​times​ ​a​ ​redirection​ ​happened?
2. Our​ ​service​​ should​ ​also​​ be​​ accessible ​​through ​​REST ​​APIs ​​by ​​other​ ​services.

## 3. Design Consideration:
1. What​ ​should​ ​be​ ​the​ ​limit​ ​on​ ​the​ ​amount​ ​of​ ​text​ ​user​ ​can​ ​paste​ ​at​ ​a​ ​time?​​
2. Should​ ​we​ ​impose​ ​size​ ​limits​ ​on​ ​custom​ ​URLs?

## 4. Capacity​ ​Estimation​ ​and​ ​Constraints
1. Our​ ​services​ ​would​ ​be​ ​read​ ​heavy.
2. ​there​ ​will​ ​be​ ​more​ ​read​ ​requests​ ​compared​ ​to​ ​new​ ​Pastes​ ​creation.
3. We​ ​can​ ​assume​ ​5:1​ ​ratio​ ​between read​ ​and​ ​write.

### Traffic​ ​estimates:
​we​ ​get​ ​one million​ ​new​ ​pastes​ ​added​ ​to​ ​our​ ​system​ ​every​ ​day.​ ​This​ ​leaves​ ​us​ ​with​ ​five​ ​million​ ​reads​ ​per​ ​day.
```
New​ ​Pastes​ ​per​ ​second: 
1M​ ​/​ ​(24​ ​hours​ ​*​ ​3600​ ​seconds)​ ​~=​ ​12​ ​pastes/sec
Paste​ ​reads​ ​per​ ​second:
5M​ ​/​ ​(24​ ​hours​ ​*​ ​3600​ ​seconds)​ ​~=​ ​58​ ​reads/sec
```

### Storage​ ​estimates:​​
Users​ ​can​ ​upload​ ​maximum​ ​10MB​ ​of​ ​data;​ ​commonly​ ​Pastebin​ ​like​ ​services​ ​are​ ​used​ ​to​ ​share​ ​source​ ​code,
configs​ ​or​ ​logs.​ ​Such​ ​texts​ ​are​ ​not​ ​huge,​ ​so​ ​let’s​ ​assume​ ​that​ ​each​ ​paste​ ​on​ ​average​ ​contains​ ​10KB.
```
1M​ ​*​ ​10KB​ ​=>​ ​10​ ​GB/day
3.6B​ ​*​ ​6​ ​=>​ ​22​ ​GB
```

### Bandwidth​ ​estimates:
1. ​For​ ​write​ ​requests,​ ​we​ ​expect​ ​12​ ​new​ ​pastes​ ​per​ ​second,​ ​resulting​ ​in​ ​120KB​ ​of​ ​ingress​ ​per​ ​second.
`12​ ​*​ ​10KB​ ​=>​ ​120​ ​KB/s`
2. As​ ​for​ ​read​ ​request,​ ​we​ ​expect​ ​58​ ​requests​ ​per​ ​second.​ ​Therefore,​ ​total​ ​data​ ​egress​ ​(sent​ ​to​ ​users)​ ​will​ ​be​ ​0.6​ ​MB/s.
`58​ ​*​ ​10KB​ ​=>​ ​0.6​ ​MB/s`

### Memory​ ​estimates:
We​ ​can​ ​cache​ ​some​ ​of​ ​the​ ​hot​ ​pastes​ ​that​ ​are​ ​frequently​ ​accessed.​ ​Following​ ​80-20​ ​rule,​ ​meaning​ ​20%​ ​of​ ​hot pastes​ ​generate​ ​80%​ ​of​ ​traffic,​ ​we​ ​would​ ​like​ ​to​ ​cache​ ​these​ ​20%​ ​pastes.
`0.2​ ​*​ ​5M​ ​*​ ​10KB​ ​~=​ ​10​ ​GB`

## 5. ​System​ ​APIs
```
addPaste(api_dev_key,​ ​paste_data,​ ​custom_url​=​None​ ​user_name​=​None,​ ​paste_name​=​None,​ ​expire_date​=​None)
getPaste(api_dev_key,​ ​api_paste_key)
deletePaste(api_dev_key,​ ​api_paste_key)
```

## 6. Database​ ​Design

### Observation
1. We​ ​need​ ​to​ ​store​ ​billions​ ​of​ ​records.
2. Each​​ metadata​ ​object​ ​we ​​are ​​going​​ to​ ​store​ ​would ​​be ​​small​ ​(less​ ​than​ ​100​ ​bytes).
3. Each ​​paste ​​object ​​we​​ are​ ​storing ​​can​​ be​ ​of ​​medium ​​size​​(it ​​can ​​be​ ​a ​​few ​​MB).
4. There ​​are ​​no ​​relationships ​​between ​​records, ​​except​ ​if​ ​we ​​want ​​to ​​store ​​which ​​user​ ​created​ ​what​ ​Paste.
5. Our ​​service ​​is ​​read ​​heavy.

### Database Schema
We​ ​would​ ​need​ ​two​ ​tables,
```
Paste (
  hash varchar(16) primary key,
  origingalURL varchar(512),
  contentPath varchar(512),
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

## 7. High​ ​Level​ ​Design
1. At​ ​a​ ​high​ ​level,​ ​we​ ​need​ ​an​ ​application​ ​layer​ ​that​ ​will​ ​serve​ ​all​ ​the​ ​read​ ​and​ ​write​ ​requests.​ ​
2. Application​ ​layer​ ​will​ ​talk​ ​to​ ​a​ ​storage​ ​layer to​ ​store​ ​and​ ​retrieve​ ​data.​ ​We​ ​can​ ​segregate​ ​our​ ​storage​ ​layer​ ​with​ ​one​ ​database​ ​storing​ ​metadata​ ​related​ ​to​ ​each​ ​paste,​ ​users,​ ​etc.,
3. while​ ​the​ ​other​ ​storing​ ​paste​ ​contents​ ​in​ ​some​ ​sort​ ​of​ ​block​ ​storage​ ​or​ ​a​ ​database.
4. This​ ​division​ ​of​ ​data​ ​will​ ​allow​ ​us​ ​to​ ​scale​ ​them individually.

## 8. Component​ ​Design

### 1. Application​ ​layer

#### How​ ​to​ ​handle​ ​a​ ​write​ ​request?
1. Upon​ ​receiving​ ​a​ ​write​ ​request,​ ​our​ ​application​ ​server​ ​will​ ​generate​ ​a​ ​six-letter​ ​random​ ​string, which​ ​would​ ​serve​ ​as​ ​the​ ​key​ ​of​ ​the​ ​paste​ ​(if​ ​the​ ​user​ ​has​ ​not​ ​provided​ ​a​ ​custom​ ​key).
2. ​The​ ​application​ ​server​ ​will​ ​then​ ​store​ ​the contents​ ​of​ ​the​ ​paste​ ​and​ ​the​ ​generated​ ​key​ ​in​ ​the​ ​database.​
3. After​ ​the​ ​successful​ ​insertion,​ ​the​ ​server​ ​can​ ​return​ ​the​ ​key​ ​to​ ​the​ ​user.
4. One​ ​possible​ ​problem​ ​here​ ​could​ ​be​ ​that​ ​the​ ​insertion​ ​fails​ ​because​ ​of​ ​a​ ​duplicate​ ​key.​ ​Since​ ​we​ ​are​ ​generating​ ​a​ ​random​ ​key,​ ​there​ ​is​ ​a possibility​ ​that​ ​the​ ​newly​ ​generated​ ​key​ ​could​ ​match​ ​an​ ​existing​ ​one.
5. ​In​ ​that​ ​case,​ ​we​ ​should​ ​regenerate​ ​a​ ​new​ ​key​ ​and​ ​try​ ​again.​ ​We should​ ​keep​ ​retrying​ ​until​ ​we​ ​don’t​ ​see​ ​a​ ​failure​ ​due​ ​to​ ​the​ ​duplicate​ ​key.​ ​We​ ​should​ ​return​ ​an​ ​error​ ​to​ ​the​ ​user​ ​if​ ​the​ ​custom​ ​key​ ​they have​ ​provided​ ​is​ ​already​ ​present​ ​in​ ​our​ ​database.
6. We can use Key Generation Service (KGS) as discussed [here](https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#2-generating-keys-offline)

#### How​ ​to​ ​handle​ ​a​ ​paste​ ​read​ ​request?
Upon​ ​receiving​ ​a​ ​read​ ​paste​ ​request,​ ​the​ ​application​ ​service​ ​layer​ ​contacts​ ​the​ ​datastore.​ ​The datastore​ ​searches​ ​for​ ​the​ ​key,​ ​and​ ​if​ ​it​ ​is​ ​found,​ ​returns​ ​the​ ​paste’s​ ​contents.​ ​Otherwise,​ ​an​ ​error​ ​code​ ​is​ ​returned.

### 2. Datastore​ ​layer
We​ ​can​ ​divide​ ​our​ ​datastore​ ​layer​ ​into​ ​two:

#### Metadata​ ​database:
We​ ​can​ ​use​ ​a​ ​relational​ ​database​ ​like​ ​MySQL​ ​or​ ​a​ ​Distributed​ ​Key-Value​ ​store​ ​like​ ​Dynamo​ ​or​ ​Cassandra.

#### Block​ ​storage:
We​ ​can​ ​store​ ​our ​​contents ​​in ​​a ​​block ​​storage ​​that ​​could​ ​be​ ​a ​​distributed ​​file​​ storage​ ​or ​​an ​​SQL-like​ ​database. Whenever​ ​we​ ​feel​ ​like​ ​hitting​ ​our​ ​full​ ​capacity​ ​on​ ​content​ ​storage,​ ​we​ ​can​ ​easily​ ​increase​ ​it​ ​by​ ​adding​ ​more​ ​servers.

## 9. Purging​ ​or​ ​DB​ ​Cleanup
[https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#9-purging-or-db-cleanup](https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#9-purging-or-db-cleanup)

## 10. ​Data​ ​Partitioning​ ​and​ ​Replication
[https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#6-data-partitioning-and-replication](https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#6-data-partitioning-and-replication)

## 11. Cache​ ​and​ ​Load​ ​Balancer
[https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#7-cache](https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#7-cache)

## 12. Security​ ​and​ ​Permissions
[https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#10-security-and-permissions](https://github.com/rajpootmohan/learning/blob/master/designQuestions/url_shortening.md#10-security-and-permissions)


