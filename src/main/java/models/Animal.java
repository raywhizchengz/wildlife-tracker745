package models;
import interfaces.AnimalInterface;
import org.sql2o.Connection;
import java.util.List;
import java.util.Objects;

public class Animal  implements AnimalInterface{
    public int id;
    public String name;
    public    String type;
    public static final String ANIMAL_TYPE = "animal";

    public Animal(String name){
        this.name = name;
        this.type = ANIMAL_TYPE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public static String getAnimalType() {
        return ANIMAL_TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Animal)) return false;

        Animal animal = (Animal) o;

        if (getId() != animal.getId()) return false;
        if (!getName().equals(animal.getName())) return false;
        return getType().equals(animal.getType());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }
    @Override
    public void save() {
        try(Connection conn = DB.sql2o.open()) {
            String sql = "INSERT INTO animals(name,type) VALUES(:name,:type)";
            this.id= (int) conn.createQuery(sql,true)
                    .addParameter("name",this.name)
                    .addParameter("type",this.type)
                    .executeUpdate()
                    .getKey();
        }
    }

    public  static List<Animal> all() {
        String sql = "SELECT * FROM animals WHERE type=:type";
        try (Connection conn = DB.sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("type","animal")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);
        }
    }
    public static Animal findById(int id){
        try(Connection conn = DB.sql2o.open()){
            String sql = "SELECT * FROM  animals WHERE id=:id AND type=:type";
            return conn.createQuery(sql)
                    .addParameter("id",id)
                    .addParameter("type","animal")
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animal.class);
        }
    }
    @Override
    public void update(String name) {
        String sql = "UPDATE animals SET name=:name WHERE id=:id";
        try (Connection conn = DB.sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("name",name)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }
    @Override
    public void delete() {
        try (Connection conn = DB.sql2o.open()){
            String sql = "DELETE FROM animals WHERE id=:id;";
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }
}
