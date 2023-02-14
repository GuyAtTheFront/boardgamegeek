package iss.nus.workshop27_class.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import iss.nus.workshop27_class.model.Comment;
import iss.nus.workshop27_class.model.Game;

import static iss.nus.workshop27_class.utils.MongoConstants.*;

import java.util.List;
import java.util.Optional;

@Repository
public class GamesMongoRepository {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public String getNextId() {
        // db.comments.find({}, {c_id: 1, _id: 0}).sort({c_id: -1}).limit(1);
        Query query = Query.query(new Criteria())
                        .with(Sort.by(Sort.Direction.DESC, FIELD_C_ID))
                        .limit(1);

        query.fields()
        .include(FIELD_C_ID)
        .exclude(FIELD_ID);
        
        Document document = mongoTemplate.findOne(query, Document.class, COLLECTION_COMMENTS);
        String maxId = document.getOrDefault(FIELD_C_ID, "00000000").toString();

        return nextId(maxId);

    }

    private String nextId(String id) {
        Integer value = Integer.parseInt(id, 16);
        value++;
        return Integer.toHexString(value);
    }

    public void insertComment(Comment comment) {
        /* db.comments.insertOne({
            "c_id" : "1207e5ac",
            "user" : "yishun",
            "rating" : 10,
            "c_text" : "I can run",
            "gid" : NumberInt(1)
            })
        */ 

        // find next ID <-- this sld be in service
        String id = this.getNextId();
        // set Id
        comment.setCommentId(id);

        // insert document
        Document inserted = mongoTemplate.insert(
                comment.toMongoDocument(), COLLECTION_COMMENTS);
        System.out.println(inserted.toJson());
    }

    public Optional<Game> findGameById(Integer id) {
        //db.games.find({gid: 5}).projection({_id: 0, name: 1, url: 1, image: 1, year:1});
        Criteria criteria = Criteria.where(FIELD_GID).is(id);
        
        Query query = Query.query(criteria);

        query.fields()
            .include(FIELD_NAME, FIELD_URL, FIELD_IMAGE, FIELD_YEAR)
            .exclude(FIELD_ID);

        Document document = mongoTemplate.findOne(
                    query, Document.class, COLLECTION_GAMES);
        
        Game game = document.isEmpty() ? null : Game.fromMongoDocument(document);

        return Optional.ofNullable(game);
    }

    public List<Comment> findComments(Integer id){
        return this.findComments(id, 5, 0);
    }

    public List<Comment> findComments(Integer id, Integer limit, Integer offset) {
        //db.comments.find({gid: 1}).sort({c_id: -1}).limit(5).skip(0);

        Criteria criteria = Criteria.where(FIELD_GID).is(id);
        Query query = Query.query(criteria)
                        .with(Sort.by(Sort.Direction.DESC, FIELD_C_ID))
                        .limit(limit)
                        .skip(offset);

        query.fields().exclude(FIELD_ID);

        List<Document> docs = mongoTemplate.find(query, Document.class, COLLECTION_COMMENTS);
        List<Comment> comments = docs.stream()
                                    .map(x -> Comment.fromMongoDocument(x))
                                    .toList();

        return comments;
    }
}
