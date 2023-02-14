package iss.nus.workshop27_class.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import static iss.nus.workshop27_class.utils.MongoConstants.*;

import org.bson.Document;

public class Comment {
    private String commentId;
    private String user;
    private Integer rating;
    private String commentText;
    private Integer gameId;

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add(FIELD_C_ID, this.getCommentId())
            .add(FIELD_USER, this.getUser())
            .add(FIELD_RATING, this.getRating())
            .add(FIELD_C_TEXT, this.getCommentText())
            .add(FIELD_GID, this.getGameId())
            .build();
    }

    public Document toMongoDocument() {
        return Document.parse(this.toJson().toString());
    }

    public static Comment fromMongoDocument(Document doc) {
        Comment comment = new Comment();
        comment.setCommentId(doc.getString(FIELD_C_ID));
        comment.setUser(doc.getString(FIELD_USER));
        comment.setRating(doc.getInteger(FIELD_RATING, 0));
        comment.setCommentText(doc.getString(FIELD_C_TEXT));
        comment.setGameId(doc.getInteger(FIELD_GID, 0));
        return comment;
    }
    
    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", user=" + user + ", rating=" + rating + ", commentText="
                + commentText + ", gameId=" + gameId + "]";
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }


    

}
