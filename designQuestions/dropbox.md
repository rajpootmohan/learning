# Designing​ ​Dropbox

## 1. ​Why​ ​Cloud​ ​Storage?
Cloud​ ​file​ ​storage​ ​services​ ​have​ ​become​ ​very​ ​popular​ ​recently​ ​as​ ​they​ ​simplify​ ​the​ ​storage​ ​and​ ​exchange​ ​of​ ​digital​ ​resources​ ​among multiple​ ​devices.​ ​The​ ​shift​ ​from​ ​using​ ​single​ ​personal​ ​computers​ ​to​ ​using​ ​multiple​ ​devices​ ​with​ ​different​ ​platforms​ ​and​ ​operating systems​ ​such​ ​as​ ​smartphones​ ​and​ ​tablets​ ​and​ ​their​ ​portable​ ​access​ ​from​ ​various​ ​geographical​ ​locations​ ​at​ ​any​ ​time​ ​is​ ​believed​ ​to​ ​be accountable​ ​for​ ​the​ ​huge​ ​popularity​ ​of​ ​cloud​ ​storage​ ​services.​ ​Some​ ​of​ ​the​ ​top​ ​benefits​ ​of​ ​such​ ​services​ ​are:
1. __Availability__:​​ ​The​ ​motto​ ​of​ ​cloud​ ​storage​ ​services​ ​is​ ​to​ ​have​ ​data​ ​availability​ ​anywhere​ ​anytime.​ ​Users​ ​can​ ​access​ ​their​ ​files/photos from​ ​any​ ​device​ ​whenever​ ​and​ ​wherever​ ​they​ ​like.
2. __Reliability​ ​and​ ​Durability__:​​ ​Another​ ​benefit​ ​of​ ​cloud​ ​storage​ ​is​ ​that​ ​it​ ​offers​ ​100%​ ​reliability​ ​and​ ​durability​ ​of​ ​data.​ ​Cloud​ ​storage ensures​ ​that​ ​users​ ​will​ ​never​ ​lose​ ​their​ ​data,​ ​by​ ​keeping​ ​multiple​ ​copies​ ​of​ ​the​ ​data​ ​stored​ ​on​ ​different​ ​geographically​ ​located​ ​servers.
3. __Scalability__:​​ ​Users​ ​will​ ​never​ ​have​ ​to​ ​worry​ ​about​ ​getting​ ​out​ ​of​ ​storage​ ​space.​ ​With​ ​cloud​ ​storage,​ ​you​ ​have​ ​unlimited​ ​storage​ ​as​ ​long as​ ​you​ ​are​ ​ready​ ​to​ ​pay​ ​for​ ​it.

## 2.​ ​Requirements​ ​and​ ​Goals​ ​of​ ​the​ ​System
1. Users​ ​should​ ​be​ ​able​ ​to​ ​upload​ ​and​ ​download​ ​their​ ​files/photos​ ​from​ ​any​ ​device.
2. Users ​​should​ ​be​ ​able​ ​to​ ​share ​​files ​​or ​​folders ​​with ​​other ​​users.
3. Our​ ​service​ ​should​​ support ​​automatic ​​synchronization ​​between​ ​devices, ​​i.e.,​​ after ​​updating ​​a ​​file ​​on ​​one ​​device, ​​it​ ​should​ ​get synchronized​ ​on​ ​all​ ​devices.
4. The​​ system​​ should​​ support​ ​storing ​​large​ ​files ​​up​​to ​​a ​​GB.
5. ACID-ity​ ​is​ ​required.​ ​Atomicity,​ ​Consistency,​ ​Isolation​ ​and​ ​Durability ​​of​​ all​ ​file​ ​operations​ ​should ​​be ​​guaranteed.
6. Our ​​system​ ​should​ ​support​ ​offline​ ​editing.​​ Users​ ​should​ ​be​​ able​ ​to​ ​add/delete/modify​ ​files​​ while ​​offline, ​​and ​​as​ ​soon​ ​as​ ​they come​ ​online,​ ​all​ ​their​ ​changes​ ​should​ ​be​ ​synced​ ​to​ ​the​ ​remote​ ​servers​ ​and​ ​other​ ​online​ ​devices.

## 3. Some​ ​Design​ ​Considerations
1. We​ ​should​ ​expect​ ​huge​ ​read​ ​and​ ​write​ ​volumes.
2. Read​ ​to​ ​write​ ​ratio​ ​is​ ​expected​ ​to​ ​be​ ​nearly​ ​the​ ​same.
3. Internally,​ ​files​ ​can​ ​be​ ​stored​ ​in​ ​small​ ​parts​ ​or​ ​chunks​ ​(say​ ​4MB),​ ​this​ ​can​ ​provide​ ​a​ ​lot​ ​of​ ​benefits​ ​e.g.​ ​all​ ​failed​ ​operations​ ​shall only​ ​be​ ​retried​ ​for​ ​smaller​ ​parts​ ​of​ ​a​ ​file.​ ​If​ ​a​ ​user​ ​fails​ ​to​ ​upload​ ​a​ ​file,​ ​then​ ​only​ ​the​ ​failing​ ​chunk​ ​will​ ​be​ ​retried.
4. We​ ​can​ ​reduce​ ​the​ ​amount​ ​of​ ​data​ ​exchange​ ​by​ ​transferring​ ​updated​ ​chunks​ ​only.
5. By​ ​removing​ ​duplicate​ ​chunks,​ ​we​ ​can​ ​save​ ​storage​ ​space​ ​and​ ​bandwidth​ ​usage.
6. Keeping​ ​a​ ​local​ ​copy​ ​of​ ​the​ ​metadata​ ​(file​ ​name,​ ​size,​ ​etc.)​ ​with​ ​the​ ​client​ ​can​ ​save​ ​us​ ​a​ ​lot​ ​of​ ​round​ ​trips​ ​to​ ​the​ ​server.
7. For​ ​small​ ​changes,​ ​clients​ ​can​ ​intelligently​ ​upload​ ​the​ ​diffs​ ​instead​ ​of​ ​the​ ​whole​ ​chunk.

## 4. ​Capacity​ ​Estimation​ ​and​ ​Constraints
1. Let’s​ ​assume​ ​that​ ​we​ ​have​ ​500M​ ​total​ ​users,​ ​and​ ​100M​ ​daily​ ​active​ ​users​ ​(DAU).
2. Let’s​ ​assume​ ​that​ ​on​ ​average​ ​each​ ​user​ ​connects​ ​from​ ​three​ ​different​ ​devices.
3. On​ ​average​ ​if​ ​a​ ​user​ ​has​ ​200​ ​files/photos,​ ​we​ ​will​ ​have​ ​100​ ​billion​ ​total​ ​files.
4. Let’s​ ​assume​ ​that​ ​average​ ​file​ ​size​ ​is​ ​100KB,​ ​this​ ​would​ ​give​ ​us​ ​ten​ ​petabytes​ ​of​ ​total​ ​storage. `100B​ ​*​ ​100KB​ ​=>​ ​10PB`

## 5. High​ ​Level​ ​Design
1. The​ ​user​ ​will​ ​specify​ ​a​ ​folder​ ​as​ ​the​ ​workspace​ ​on​ ​their​ ​device.​ ​Any​ ​file/photo/folder​ ​placed​ ​in​ ​this​ ​folder​ ​will​ ​be​ ​uploaded​ ​to​ ​the cloud,​ ​and​ ​whenever​ ​a​ ​file​ ​is​ ​modified​ ​or​ ​deleted,​ ​it​ ​will​ ​be​ ​reflected​ ​in​ ​the​ ​same​ ​way​ ​in​ ​the​ ​cloud​ ​storage.​ ​The​ ​user​ ​can​ ​specify​ ​similar workspaces​ ​on​ ​all​ ​their​ ​devices​ ​and​ ​any​ ​modification​ ​done​ ​on​ ​one​ ​device​ ​will​ ​be​ ​propagated​ ​to​ ​all​ ​other​ ​devices​ ​to​ ​have​ ​the​ ​same​ ​view of​ ​the​ ​workspace​ ​everywhere.
2. At​ ​a​ ​high​ ​level,​ ​we​ ​need​ ​to​ ​store​ ​files​ ​and​ ​their​ ​metadata​ ​information​ ​like​ ​File​ ​Name,​ ​File​ ​Size,​ ​Directory,​ ​etc.,​ ​and​ ​who​ ​this​ ​file​ ​is shared​ ​with.​ ​So,​ ​we​ ​need​ ​some​ ​servers​ ​that​ ​can​ ​help​ ​the​ ​clients​ ​to​ ​upload/download​ ​files​ ​to​ ​Cloud​ ​Storage​ ​and​ ​some​ ​servers​ ​that​ ​can facilitate​ ​updating​ ​metadata​ ​about​ ​files​ ​and​ ​users.​ ​We​ ​also​ ​need​ ​some​ ​mechanism​ ​to​ ​notify​ ​all​ ​clients​ ​whenever​ ​an​ ​update​ ​happens​ ​so they​ ​can​ ​synchronize​ ​their​ ​files.
3. Block​ ​servers​ ​will​ ​work​ ​with​ ​the​ ​clients​ ​to​ ​upload/download​ ​files​ ​from​ ​cloud​ ​storage,​ ​and​ ​Metadata servers​ ​will​ ​keep​ ​metadata​ ​of​ ​files​ ​updated​ ​in​ ​a​ ​SQL​ ​or​ ​NoSQL​ ​database.​ ​Synchronization​ ​servers​ ​will​ ​handle​ ​the​ ​workflow​ ​of​ ​notifying all​ ​clients​ ​about​ ​different​ ​changes​ ​for​ ​synchronization.
![Image](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/dropbox_high_level_design.png)

## 6. Component Design

### 1. Client
1. Upload​ ​and​ ​download​ ​files.
2. Detect​ ​file ​​changes​ ​in​​ the​ ​workspace ​​folder.
3. Handle​ ​conflict ​​due ​​to ​​offline ​​or​​ concurrent​ ​updates.

#### How​ ​do​ ​we​ ​handle​ ​file​ ​transfer​ ​efficiently?
we​ ​can​ ​break​ ​each​ ​file​ ​into​ ​smaller​ ​chunks​ ​so​ ​that​ ​we​ ​transfer​ ​only those​ ​chunks​ ​that​ ​are​ ​modified​ ​and​ ​not​ ​the​ ​whole​ ​file.​ ​Let’s​ ​say​ ​we​ ​divide​ ​each​ ​file​ ​into​ ​fixed​ ​size​ ​of​ ​4MB​ ​chunks.​ ​We​ ​can​ ​statically calculate​ ​what​ ​could​ ​be​ ​an​ ​optimal​ ​chunk​ ​size​ ​based​ ​on​ ​1)​ ​Storage​ ​devices​ ​we​ ​use​ ​in​ ​the​ ​cloud​ ​to​ ​optimize​ ​space​ ​utilization​ ​and Input/output​ ​operations​ ​per​ ​second​ ​(IOPS)​ ​2)​ ​Network​ ​bandwidth​ ​3)​ ​Average​ ​file​ ​size​ ​in​ ​the​ ​storage​ ​etc.​ ​In​ ​our​ ​metadata,​ ​we​ ​should also​ ​keep​ ​a​ ​record​ ​of​ ​each​ ​file​ ​and​ ​the​ ​chunks​ ​that​ ​constitute​ ​it.

#### Should​ ​we​ ​keep​ ​a​ ​copy​ ​of​ ​metadata​ ​with​ ​Client?
Keeping​ ​a​ ​local​ ​copy​ ​of​ ​metadata​ ​not​ ​only​ ​enable​ ​us​ ​to​ ​do​ ​offline​ ​updates​ ​but​ ​also saves​ ​a​ ​lot​ ​of​ ​round​ ​trips​ ​to​ ​update​ ​remote​ ​metadata.

#### How​ ​can​ ​clients​ ​efficiently​ ​listen​ ​to​ ​changes​ ​happening​ ​on​ ​other​ ​clients?
With​ ​long​ ​polling,​ ​the​ ​client​ ​requests​ ​information​ ​from​ ​the​ ​server​ ​with the​ ​expectation​ ​that​ ​the​ ​server​ ​may​ ​not​ ​respond​ ​immediately.​ ​If​ ​the​ ​server​ ​has​ ​no​ ​new​ ​data​ ​for​ ​the​ ​client​ ​when​ ​the​ ​poll​ ​is​ ​received, instead​ ​of​ ​sending​ ​an​ ​empty​ ​response,​ ​the​ ​server​ ​holds​ ​the​ ​request​ ​open​ ​and​ ​waits​ ​for​ ​response​ ​information​ ​to​ ​become​ ​available.​ ​Once it​ ​does​ ​have​ ​new​ ​information,​ ​the​ ​server​ ​immediately​ ​sends​ ​an​ ​HTTP/S​ ​response​ ​to​ ​the​ ​client,​ ​completing​ ​the​ ​open​ ​HTTP/S​ ​Request. Upon​ ​receipt​ ​of​ ​the​ ​server​ ​response,​ ​the​ ​client​ ​can​ ​immediately​ ​issue​ ​another​ ​server​ ​request​ ​for​ ​future​ ​updates.

Based​ ​on​ ​the​ ​above​ ​considerations​ ​we​ ​can​ ​divide​ ​our​ ​client​ ​into​ ​following​ ​four​ ​parts:
1. __Internal​ ​Metadata​ ​Database__​​ ​will​ ​keep​ ​track​ ​of​ ​all​ ​the​ ​files,​ ​chunks,​ ​their​ ​versions,​ ​and​ ​their​ ​location​ ​in​ ​the​ ​file​ ​system.
2. __Chunker​__​ ​will​ ​split​ ​the​ ​files​ ​into​ ​smaller​ ​pieces​ ​called​ ​chunks.​ ​It​ ​will​ ​also​ ​be​ ​responsible​ ​for​ ​reconstructing​ ​a​ ​file​ ​from​ ​its​ ​chunks. Our​ ​chunking​ ​algorithm​ ​will​ ​detect​ ​the​ ​parts​ ​of​ ​the​ ​files​ ​that​ ​have​ ​been​ ​modified​ ​by​ ​the​ ​user​ ​and​ ​only​ ​transfer​ ​those​ ​parts​ ​to​ ​the​ ​Cloud Storage;​ ​this​ ​will​ ​save​ ​us​ ​bandwidth​ ​and​ ​synchronization​ ​time.
3. __Watcher__​​ ​will​ ​monitor​ ​the​ ​local​ ​workspace​ ​folders​ ​and​ ​notify​ ​the​ ​Indexer​ ​(discussed​ ​below)​ ​of​ ​any​ ​action​ ​performed​ ​by​ ​the​ ​users, e.g.,​ ​when​ ​users​ ​create,​ ​delete,​ ​or​ ​update​ ​files​ ​or​ ​folders.​ ​Watcher​ ​also​ ​listens​ ​to​ ​any​ ​changes​ ​happening​ ​on​ ​other​ ​clients​ ​that​ ​are broadcasted​ ​by​ ​Synchronization​ ​service.
4. __​​Indexer__​​ ​will​ ​process​ ​the​ ​events​ ​received​ ​from​ ​the​ ​Watcher​ ​and​ ​update​ ​the​ ​internal​ ​metadata​ ​database​ ​with​ ​information​ ​about​ ​the chunks​ ​of​ ​the​ ​modified​ ​files.​ ​Once​ ​the​ ​chunks​ ​are​ ​successfully​ ​submitted/downloaded​ ​to​ ​the​ ​Cloud​ ​Storage,​ ​the​ ​Indexer​ ​will communicate​ ​with​ ​the​ ​remote​ ​Synchronization​ ​Service​ ​to​ ​broadcast​ ​changes​ ​to​ ​other​ ​clients​ ​and​ ​update​ ​remote​ ​metadata​ ​database.
![Image](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/dropbox_client_responsibility.png)

#### How​ ​should​ ​clients​ ​handle​ ​slow​ ​servers?​​ 
Clients​ ​should​ ​exponentially​ ​back-off​ ​if​ ​the​ ​server​ ​is​ ​busy/not-responding.​ ​Meaning,​ ​if​ ​a server​ ​is​ ​too​ ​slow​ ​to​ ​respond,​ ​clients​ ​should​ ​delay​ ​their​ ​retries,​ ​and​ ​this​ ​delay​ ​should​ ​increase​ ​exponentially.

#### Should​ ​mobile​ ​clients​ ​sync​ ​remote​ ​changes​ ​immediately?​​
Unlike​ ​desktop​ ​or​ ​web​ ​clients,​ ​that​ ​check​ ​for​ ​file​ ​changes​ ​on​ ​a​ ​regular basis,​ ​mobile​ ​clients​ ​usually​ ​sync​ ​on​ ​demand​ ​to​ ​save​ ​user’s​ ​bandwidth​ ​and​ ​space.

### 2. Metadata​ ​Database
The​ ​Metadata​ ​Database​ ​is​ ​responsible​ ​for​ ​maintaining​ ​the​ ​versioning​ ​and​ ​metadata​ ​information​ ​about​ ​files/chunks,​ ​users,​ ​and workspaces.​ ​The​ ​Metadata​ ​Database​ ​can​ ​be​ ​a​ ​relational​ ​database​ ​such​ ​as​ ​MySQL,​ ​or​ ​a​ ​NoSQL​ ​database​ ​service​ ​such​ ​as​ ​DynamoDB. Regardless​ ​of​ ​the​ ​type​ ​of​ ​the​ ​database,​ ​the​ ​Synchronization​ ​Service​ ​should​ ​be​ ​able​ ​to​ ​provide​ ​a​ ​consistent​ ​view​ ​of​ ​the​ ​files​ ​using​ ​a database,​ ​especially​ ​if​ ​more​ ​than​ ​one​ ​user​ ​work​ ​with​ ​the​ ​same​ ​file​ ​simultaneously.​ ​Since​ ​NoSQL​ ​data​ ​stores​ ​do​ ​not​ ​support​ ​ACID properties​ ​in​ ​favor​ ​of​ ​scalability​ ​and​ ​performance,​ ​we​ ​need​ ​to​ ​incorporate​ ​the​ ​support​ ​for​ ​ACID​ ​properties​ ​programmatically​ ​in​ ​the logic​ ​of​ ​our​ ​Synchronization​ ​Service​ ​in​ ​case​ ​we​ ​opt​ ​for​ ​this​ ​kind​ ​of​ ​databases.​ ​However,​ ​using​ ​a​ ​relational​ ​database​ ​can​ ​simplify​ ​the implementation​ ​of​ ​the​ ​Synchronization​ ​Service​ ​as​ ​they​ ​natively​ ​support​ ​ACID​ ​properties.
Metadata​ ​Database​ ​should​ ​be​ ​storing​ ​information​ ​about​ ​following​ ​objects:
1. Chunks
2. Files
3. User
4. Devices
5. Workspace​​(sync​​folders)

### 3. Synchronization​ ​Service
1. The​ ​Synchronization​ ​Service​ ​is​ ​the​ ​component​ ​that​ ​processes​ ​file​ ​updates​ ​made​ ​by​ ​a​ ​client​ ​and​ ​applies​ ​these​ ​changes​ ​to​ ​other subscribed​ ​clients.
2. It​ ​also​ ​synchronizes​ ​clients’​ ​local​ ​databases​ ​with​ ​the​ ​information​ ​stored​ ​in​ ​the​ ​remote​ ​Metadata​ ​DB.​
3. Desktop​ ​clients​ ​communicate​ ​with​ ​the​ ​Synchronization​ ​Service​ ​to​ ​either​ ​obtain​ ​updates​ ​from​ ​the​ ​Cloud Storage​ ​or​ ​send​ ​files​ ​and​ ​updates​ ​to​ ​the​ ​Cloud​ ​Storage​ ​and​ ​potentially​ ​other​ ​users.​
4. If​ ​a​ ​client​ ​was​ ​offline​ ​for​ ​a​ ​period,​ ​it​ ​polls​ ​the system​ ​for​ ​new​ ​updates​ ​as​ ​soon​ ​as​ ​it​ ​becomes​ ​online.​
5. When​ ​the​ ​Synchronization​ ​Service​ ​receives​ ​an​ ​update​ ​request,​ ​it​ ​checks​ ​with​ ​the Metadata​ ​Database​ ​for​ ​consistency​ ​and​ ​then​ ​proceeds​ ​with​ ​the​ ​update.​ ​Subsequently,​ ​a​ ​notification​ ​is​ ​sent​ ​to​ ​all​ ​subscribed​ ​users​ ​or devices​ ​to​ ​report​ ​the​ ​file​ ​update.
6. The​ ​Synchronization​ ​Service​ ​should​ ​be​ ​designed​ ​in​ ​such​ ​a​ ​way​ ​to​ ​transmit​ ​less​ ​data​ ​between​ ​clients​ ​and​ ​the​ ​Cloud​ ​Storage​ ​to​ ​achieve better​ ​response​ ​time
7. ​Instead​ ​of​ ​transmitting​ ​entire​ ​files​ ​from​ ​clients​ ​to​ ​the​ ​server​ ​or​ ​vice​ ​versa,​ ​we​ ​can​ ​just transmit​ ​the​ ​difference​ ​between​ ​two​ ​versions​ ​of​ ​a​ ​file.
8. ​Server​ ​and​ ​clients​ ​can​ ​calculate​ ​a​ ​hash​ ​(e.g.,​ ​SHA-256)​ ​to​ ​see​ ​whether​ ​to update​ ​the​ ​local​ ​copy​ ​of​ ​a​ ​chunk​ ​or​ ​not.​ ​On​ ​server​ ​if​ ​we​ ​already​ ​have​ ​a​ ​chunk​ ​with​ ​a​ ​similar​ ​hash​ ​(even​ ​from​ ​another​ ​user)​ ​we​ ​don’t need​ ​to​ ​create​ ​another​ ​copy,​ ​we​ ​can​ ​use​ ​the​ ​same​ ​chunk.​

### 4. Message​ ​Queuing​ ​Service
1. ​A scalable​ ​Message​ ​Queuing​ ​Service​ ​that​ ​supports​ ​asynchronous​ ​message-based​ ​communication​ ​between​ ​clients​ ​and​ ​the Synchronization​ ​Service​ ​instances​ ​best​ ​fits​ ​the​ ​requirements​ ​of​ ​our​ ​application.​
2. The​ ​Message​ ​Queuing​ ​Service​ ​supports​ ​asynchronous and​ ​loosely​ ​coupled​ ​message-based​ ​communication​ ​between​ ​distributed​ ​components​ ​of​ ​the​ ​system.
3. The​ ​Message​ ​Queuing​ ​Service should​ ​be​ ​able​ ​to​ ​efficiently​ ​store​ ​any​ ​number​ ​of​ ​messages​ ​in​ ​a​ ​highly​ ​available,​ ​reliable​ ​and​ ​scalable​ ​queue.
4. Message​ ​Queuing​ ​Service​ ​will​ ​implement​ ​two​ ​types​ ​of​ ​queues​ ​in​ ​our​ ​system.​ ​The​ ​Request​ ​Queue​ ​is​ ​a​ ​global​ ​queue,​ ​and​ ​all​ ​client​ ​will share​ ​it.​ ​Clients’​ ​requests​ ​to​ ​update​ ​the​ ​Metadata​ ​Database​ ​will​ ​be​ ​sent​ ​to​ ​the​ ​Request​ ​Queue​ ​first,​ ​from​ ​there​ ​Synchronization​ ​Service will​ ​take​ ​it​ ​to​ ​update​ ​metadata.
5. ​The​ ​Response​ ​Queues​ ​that​ ​correspond​ ​to​ ​individual​ ​subscribed​ ​clients​ ​are​ ​responsible​ ​for​ ​delivering the​ ​update​ ​messages​ ​to​ ​each​ ​client.​ ​Since​ ​a​ ​message​ ​will​ ​be​ ​deleted​ ​from​ ​the​ ​queue​ ​once​ ​received​ ​by​ ​a​ ​client,​ ​we​ ​need​ ​to​ ​create separate​ ​Response​ ​Queues​ ​for​ ​each​ ​subscribed​ ​client​ ​to​ ​share​ ​update​ ​messages.
![Image](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/dropbox_message_queuing_service.png)

### 5. Cloud/Block​ ​Storage
Cloud/Block​ ​Storage​ ​stores​ ​chunks​ ​of​ ​files​ ​uploaded​ ​by​ ​the​ ​users.​ ​Clients​ ​directly​ ​interact​ ​with​ ​the​ ​storage​ ​to​ ​send​ ​and​ ​receive​ ​objects from​ ​it.​ ​Separation​ ​of​ ​the​ ​metadata​ ​from​ ​storage​ ​enables​ ​us​ ​to​ ​use​ ​any​ ​storage​ ​either​ ​in​ ​cloud​ ​or​ ​in-house.
![Image](https://raw.githubusercontent.com/rajpootmohan/learning/master/images/dropbox_block_storage.png)

## 7. File​ ​Processing​ ​Workflow
1. Client​ ​A​ ​uploads​ ​chunks​ ​to​ ​cloud​ ​storage.
2. Client ​​A ​​updates ​​meta data ​​and ​​commits ​​changes.
3. Client​ ​A​​ gets​ ​confirmation, ​​and ​​notifications ​​are ​​sent ​​to​ ​Clients ​​B ​​and ​​C ​​about​ ​the​ ​changes.
4. Client ​​B ​​and​ ​C​ ​receive​ ​meta data​ ​changes​ ​and​ ​download​ ​updated​ ​chunks.

## 8.​ ​Data​ ​Deduplication
Data​ ​deduplication​ ​is​ ​a​ ​technique​ ​used​ ​for​ ​eliminating​ ​duplicate​ ​copies​ ​of​ ​data​ ​to​ ​improve​ ​storage​ ​utilization.​ ​It​ ​can​ ​also​ ​be​ ​applied​ ​to network​ ​data​ ​transfers​ ​to​ ​reduce​ ​the​ ​number​ ​of​ ​bytes​ ​that​ ​must​ ​be​ ​sent.​ ​For​ ​each​ ​new​ ​incoming​ ​chunk,​ ​we​ ​can​ ​calculate​ ​a​ ​hash​ ​of​ ​it and​ ​compare​ ​that​ ​hash​ ​with​ ​all​ ​the​ ​hashes​ ​of​ ​the​ ​existing​ ​chunks​ ​to​ ​see​ ​if​ ​we​ ​already​ ​have​ ​same​ ​chunk​ ​present​ ​in​ ​our​ ​storage.
We​ ​can​ ​implement​ ​deduplication​ ​in​ ​two​ ​ways​ ​in​ ​our​ ​system:

### 1. Post-process​ ​deduplication
With​ ​post-process​ ​deduplication,​ ​new​ ​chunks​ ​are​ ​first​ ​stored​ ​on​ ​the​ ​storage​ ​device,​ ​and​ ​later​ ​some​ ​process​ ​analyzes​ ​the​ ​data​ ​looking for​ ​duplication.​ ​The​ ​benefit​ ​is​ ​that​ ​clients​ ​will​ ​not​ ​need​ ​to​ ​wait​ ​for​ ​the​ ​hash​ ​calculation​ ​or​ ​lookup​ ​to​ ​complete​ ​before​ ​storing​ ​the​ ​data, thereby​ ​ensuring​ ​that​ ​there​ ​is​ ​no​ ​degradation​ ​in​ ​storage​ ​performance.​ ​Drawbacks​ ​of​ ​this​ ​approach​ ​are​ ​1)​ ​We​ ​will​ ​unnecessarily​ ​be storing​ ​duplicate​ ​data,​ ​though​ ​for​ ​a​ ​short​ ​time,​ ​2)​ ​Duplicate​ ​data​ ​will​ ​be​ ​transferred​ ​consuming​ ​bandwidth.

### 2. ​In-line​ ​deduplication
Alternatively,​ ​deduplication​ ​hash​ ​calculations​ ​can​ ​be​ ​done​ ​in​ ​real-time​ ​as​ ​the​ ​clients​ ​are​ ​entering​ ​data​ ​on​ ​their​ ​device.​ ​If​ ​our​ ​system identifies​ ​a​ ​chunk​ ​which​ ​it​ ​has​ ​already​ ​stored,​ ​only​ ​a​ ​reference​ ​to​ ​the​ ​existing​ ​chunk​ ​will​ ​be​ ​added​ ​in​ ​the​ ​metadata,​ ​rather​ ​than​ ​the​ ​full copy​ ​of​ ​the​ ​chunk.​ ​This​ ​approach​ ​will​ ​give​ ​us​ ​optimal​ ​network​ ​and​ ​storage​ ​usage.

## 9. Metadata​ ​Partitioning

### 1.​ ​Vertical​ ​Partitioning:
​We​ ​can​ ​partition​ ​our​ ​database​ ​in​ ​such​ ​a​ ​way​ ​that​ ​we​ ​store​ ​tables​ ​related​ ​to​ ​one​ ​particular​ ​feature​ ​on​ ​one server.​ ​For​ ​example,​ ​we​ ​can​ ​store​ ​all​ ​the​ ​user​ ​related​ ​tables​ ​in​ ​one​ ​database​ ​and​ ​all​ ​files/chunks​ ​related​ ​tables​ ​in​ ​another​ ​database. Although​ ​this​ ​approach​ ​is​ ​straightforward​ ​to​ ​implement​ ​it​ ​has​ ​some​ ​issues:
1. Will​ ​we​ ​still​ ​have​ ​scale​ ​issues?​ ​What​ ​if​ ​we​ ​have​ ​trillions​ ​of​ ​chunks​ ​to​ ​be​ ​stored​ ​and​ ​our​ ​database​ ​cannot​ ​support​ ​to​ ​store​ ​such huge​ ​number​ ​of​ ​records?​ ​How​ ​would​ ​we​ ​further​ ​partition​ ​such​ ​tables?
2. Joining​ ​two​ ​tables ​​in ​​two ​​separate​​ databases​ ​can​ ​cause​​ performance ​​and ​​consistency ​​issues.​ ​How​​ frequently ​​do ​​we ​​have​ ​to​ ​join user​ ​and​ ​file​ ​tables?

### 2. ​Range​ ​Based​ ​Partitioning:​​
1. ​What​ ​if​ ​we​ ​store​ ​files/chunks​ ​in​ ​separate​ ​partitions​ ​based​ ​on​ ​the​ ​first​ ​letter​ ​of​ ​the​ ​File​ ​Path.​ ​So,​ ​we save​ ​all​ ​the​ ​files​ ​starting​ ​with​ ​letter​ ​‘A’​ ​in​ ​one​ ​partition​ ​and​ ​those​ ​that​ ​start​ ​with​ ​letter​ ​‘B’​ ​into​ ​another​ ​partition​ ​and​ ​so​ ​on.​ ​This approach​ ​is​ ​called​ ​range​ ​based​ ​partitioning.​ ​We​ ​can​ ​even​ ​combine​ ​certain​ ​less​ ​frequently​ ​occurring​ ​letters​ ​into​ ​one​ ​database​ ​partition. We​ ​should​ ​come​ ​up​ ​with​ ​this​ ​partitioning​ ​scheme​ ​statically​ ​so​ ​that​ ​we​ ​can​ ​always​ ​store/find​ ​a​ ​file​ ​in​ ​a​ ​predictable​ ​manner.
2. The​ ​main​ ​problem​ ​with​ ​this​ ​approach​ ​is​ ​that​ ​it​ ​can​ ​lead​ ​to​ ​unbalanced​ ​servers.​ ​For​ ​example,​ ​if​ ​we​ ​decide​ ​to​ ​put​ ​all​ ​files​ ​starting​ ​with letter​ ​‘E’​ ​into​ ​a​ ​DB​ ​partition,​ ​and​ ​later​ ​we​ ​realize​ ​that​ ​we​ ​have​ ​too​ ​many​ ​files​ ​that​ ​start​ ​with​ ​letter​ ​‘E’,​ ​to​ ​such​ ​an​ ​extent​ ​that​ ​we​ ​cannot fit​ ​them​ ​into​ ​one​ ​DB​ ​partition.

### 3. Hash-Based​ ​Partitioning:​​
​In​ ​this​ ​scheme​ ​we​ ​take​ ​a​ ​hash​ ​of​ ​the​ ​object​ ​we​ ​are​ ​storing​ ​and​ ​based​ ​on​ ​this​ ​hash​ ​we​ ​figure​ ​out​ ​the​ ​DB partition​ ​to​ ​which​ ​this​ ​object​ ​should​ ​go.​ ​In​ ​our​ ​case,​ ​we​ ​can​ ​take​ ​the​ ​hash​ ​of​ ​the​ ​‘FileID’​ ​of​ ​the​ ​File​ ​object​ ​we​ ​are​ ​storing​ ​to​ ​determine the​ ​partition​ ​the​ ​file​ ​will​ ​be​ ​stored.​ ​Our​ ​hashing​ ​function​ ​will​ ​randomly​ ​distribute​ ​objects​ ​into​ ​different​ ​partitions,​ ​e.g.,​ ​our​ ​hashing function​ ​can​ ​always​ ​map​ ​any​ ​ID​ ​to​ ​a​ ​number​ ​between​ ​[1...256],​ ​and​ ​this​ ​number​ ​would​ ​be​ ​the​ ​partition​ ​we​ ​will​ ​store​ ​our​ ​object.

## 10. Caching
1. We​ ​can​ ​have​ ​two​ ​kinds​ ​of​ ​caches​ ​in​ ​our​ ​system.​ ​To​ ​deal​ ​with​ ​hot​ ​files/chunks,​ ​we​ ​can​ ​introduce​ ​a​ ​cache​ ​for​ ​Block​ ​storage.​ ​We​ ​can​ ​use an​ ​off-the-shelf​ ​solution​ ​like​ ​Memcache,​ ​that​ ​can​ ​store​ ​whole​ ​chunks​ ​with​ ​their​ ​respective​ ​IDs/Hashes,​ ​and​ ​Block​ ​servers​ ​before hitting​ ​Block​ ​storage​ ​can​ ​quickly​ ​check​ ​if​ ​the​ ​cache​ ​has​ ​desired​ ​chunk.​ ​Based​ ​on​ ​clients’​ ​usage​ ​pattern​ ​we​ ​can​ ​determine​ ​how​ ​many cache​ ​servers​ ​we​ ​need.​ ​A​ ​high-end​ ​commercial​ ​server​ ​can​ ​have​ ​up​ ​to​ ​144GB​ ​of​ ​memory;​ ​So,​ ​one​ ​such​ ​server​ ​can​ ​cache​ ​36K​ ​chunks.
2. __Which​ ​cache​ ​replacement​ ​policy​ ​would​ ​best​ ​fit​ ​our​ ​needs?__ When​ ​the​ ​cache​ ​is​ ​full,​ ​and​ ​we​ ​want​ ​to​ ​replace​ ​a​ ​chunk​ ​with​ ​a newer/hotter​ ​chunk,​ ​how​ ​would​ ​we​ ​choose?​ ​Least​ ​Recently​ ​Used​ ​(LRU)​ ​can​ ​be​ ​a​ ​reasonable​ ​policy​ ​for​ ​our​ ​system.​ ​Under​ ​this​ ​policy, we​ ​discard​ ​the​ ​least​ ​recently​ ​used​ ​chunk​ ​first.

## 11. Load​ ​Balancer​ ​(LB)
We​ ​can​ ​add​ ​Load​ ​balancing​ ​layer​ ​at​ ​two​ ​places​ ​in​ ​our​ ​system​ ​1)​ ​Between​ ​Clients​ ​and​ ​Block​ ​servers​ ​and​ ​2)​ ​Between​ ​Clients​ ​and Metadata​ ​servers.​ ​Initially,​ ​a​ ​simple​ ​Round​ ​Robin​ ​approach​ ​can​ ​be​ ​adopted;​ ​that​ ​distributes​ ​incoming​ ​requests​ ​equally​ ​among backend​ ​servers.​ ​This​ ​LB​ ​is​ ​simple​ ​to​ ​implement​ ​and​ ​does​ ​not​ ​introduce​ ​any​ ​overhead.​ ​Another​ ​benefit​ ​of​ ​this​ ​approach​ ​is​ ​if​ ​a​ ​server​ ​is dead,​ ​LB​ ​will​ ​take​ ​it​ ​out​ ​of​ ​the​ ​rotation​ ​and​ ​will​ ​stop​ ​sending​ ​any​ ​traffic​ ​to​ ​it.​ ​A​ ​problem​ ​with​ ​Round​ ​Robin​ ​LB​ ​is,​ ​it​ ​won’t​ ​take​ ​server load​ ​into​ ​consideration.​ ​If​ ​a​ ​server​ ​is​ ​overloaded​ ​or​ ​slow,​ ​the​ ​LB​ ​will​ ​not​ ​stop​ ​sending​ ​new​ ​requests​ ​to​ ​that​ ​server.​ ​To​ ​handle​ ​this,​ ​a more​ ​intelligent​ ​LB​ ​solution​ ​can​ ​be​ ​placed​ ​that​ ​periodically​ ​queries​ ​backend​ ​server​ ​about​ ​their​ ​load​ ​and​ ​adjusts​ ​traffic​ ​based​ ​on​ ​that.

## 12. Security,​ ​Permissions​ ​and​ ​File​ ​Sharing
One​ ​of​ ​primary​ ​concern​ ​users​ ​will​ ​have​ ​while​ ​storing​ ​their​ ​files​ ​in​ ​the​ ​cloud​ ​would​ ​be​ ​the​ ​privacy​ ​and​ ​security​ ​of​ ​their​ ​data.​ ​Especially since​ ​in​ ​our​ ​system​ ​users​ ​can​ ​share​ ​their​ ​files​ ​with​ ​other​ ​users​ ​or​ ​even​ ​make​ ​them​ ​public​ ​to​ ​share​ ​it​ ​with​ ​everyone.​ ​To​ ​handle​ ​this,​ ​we will​ ​be​ ​storing​ ​permissions​ ​of​ ​each​ ​file​ ​in​ ​our​ ​metadata​ ​DB​ ​to​ ​reflect​ ​what​ ​files​ ​are​ ​visible​ ​or​ ​modifiable​ ​by​ ​any​ ​user.
