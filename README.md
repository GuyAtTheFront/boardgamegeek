# Setup:

### Mongo

* Import `game.json` into `boardgames` database as `games` collection
```
mongoimport "mongodb://localhost:27017" -d boardgames -c games --jsonArray --file json/game.json --drop
```

For cloud, note the additional `--authenticationDatabase admin` portion:
```
mongoimport "<MONGO_CONNECTION_URL>" -d boardgames -c games --jsonArray --file json/game.json --drop --authenticationDatabase admin
```

* Import `comment.json` into `boardgames` database as `comments` collection
```
mongoimport "mongodb://localhost:27017" -d boardgames -c comments --jsonArray --file json/comment.json --drop
```

### SQL

1. Login to SQL as root
```
mysql -uroot -p
// terminal will prompt you for password
```

2. Import `bgg.sql`
```
source sql/bgg.sql;
```

3. Verify database has been imported
```
show databases;
```

4. Grant user access to database
```
grant all privileges on bgg.* to <user>@'%';
flush privileges;
```

5. Exit and test database with user
```
exit;
mysql -u<user> -p
use bgg;
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