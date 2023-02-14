package iss.nus.workshop27_class.model;

import org.bson.Document;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static iss.nus.workshop27_class.utils.MongoConstants.*;

public class Game {
    
    private Integer gid;
    private String name;
    private Integer year;
    private String url;
    private String image;

    public static Game fromSql(SqlRowSet rs) {
        Game game = new Game();
        game.setGid(rs.getInt("gid"));
        game.setName(rs.getString("name"));
        return game;
    }

    public static Game fromMongoDocument(Document doc) {
        Game game = new Game();
        game.setName(doc.getString(FIELD_NAME));
        game.setYear(doc.getInteger(FIELD_YEAR, 0));
        game.setUrl(doc.getString(FIELD_URL));
        game.setImage(doc.getString(FIELD_IMAGE));
        return game;
    }


    @Override
    public String toString() {
        return "Game [gid=" + gid + ", name=" + name + ", year=" + year + ", url=" + url + ", image=" + image + "]";
    }

    
    public void setYear(Integer year) {
        this.year = year;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
