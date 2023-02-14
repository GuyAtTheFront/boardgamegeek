# Setup:

### Mongo

* Import `game.json` into `boardgames` database as `games` collection
```
mongoimport "mongodb://localhost:27017" -d boardgames -c games --jsonArray --file json/game.json --drop
```

* Import `comment.json` into `boardgames` database as `comments` collection
```
mongoimport "mongodb://localhost:27017" -d boardgames -c comments --jsonArray --file json/comment.json --drop
```

### SQL

* Import `bgg.sql`
```
help me
```

# Mongo Queries Used

* `.insertOne()`
```
db.comments.insertOne({
        "c_id" : "1207e5ac",
        "user" : "yishun",
        "rating" : 10,
        "c_text" : "I can run",
        "gid" : NumberInt(1)
        });
```


* Get max ID
```
db.comments.find({}, {c_id: 1, _id: 0}).sort({c_id: -1}).limit(1);
```


* `find` + `projection`
```
db.games.find({gid: 5}).projection({_id: 0, name: 1, url: 1, image: 1, year:1});
```


* `sort` + `limit` + `skip`
```
db.comments.find({gid: 1}).sort({c_id: -1}).limit(5).skip(0);
```