package models;

import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class Ranger {

    private int id;
    private String name;
    private int tag;
    public Ranger(String name,int tag){
        this.name = name;
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ranger)) return false;

        Ranger ranger = (Ranger) o;

        if (getId() != ranger.getId()) return false;
        if (getTag() != ranger.getTag()) return false;
        return getName().equals(ranger.getName());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getTag();
        return result;
    }

    public void save(){
        try (Connection conn = DB.sql2o.open()){
            String sql = "INSERT INTO rangers(name,tag) VALUES(:name,:tag)";
            this.id = (int) conn.createQuery(sql,true)
                    .addParameter("name",name)
                    .addParameter("tag",this.tag)
                    .executeUpdate()
                    .getKey();
        }
    }
    public static List<Ranger> all(){
        try(Connection con = DB.sql2o.open()){
            return  con.createQuery("SELECT * FROM  rangers")
                    .executeAndFetch(Ranger.class);
        }
    }

    public static Ranger find(int id){
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM rangers WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Ranger.class);
        }

    }
    public void update(String name, int badgeNumber){
        try(Connection con = DB.sql2o.open()){
            String sql = "UPDATE rangers SET name=:name, tag=:tag WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("tag", tag)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
    public void delete() {
        try(Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM rangers WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}
