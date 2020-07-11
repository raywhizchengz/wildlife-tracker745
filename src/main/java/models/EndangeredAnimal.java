package models;
import interfaces.EndangeredInterface;
import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class EndangeredAnimal extends Animal implements EndangeredInterface  {

    private String health;
    private String age;


    public static final String ANIMAL_TYPE = "endangered";
    public static final String ADULT = "adult";
    public static final String YOUNG = "youthful";
    public static final String NEWBORN = "newborn";
    public static final String HEALTHY = "very healthy";
    public static final String AVERAGE = "averagely unhealthy";
    public static final String ILL = "very unhealthy";

    public EndangeredAnimal(String name,String age, String health){
        super(name);
        this.type = ANIMAL_TYPE;
        this.health = health;
        this.age = age;

    }

    public String getAge() {
        return age;
    }

    public String getHealth() {
        return health;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Animal){
            Animal animal = (Animal) o;
            return (this.getName().equals(animal.getName()));
        }
        return false;

    }
    @Override
    public void saveEn() {
        String sql = "INSERT INTO animals (name,age,health,type) VALUES(:name,:age,:health,:type)";
        try(Connection con = DB.sql2o.open()) {
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", name)
                    .addParameter("age", age)
                    .addParameter("health", health)
                    .addParameter("type", type)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
        }

    }

    public static List<EndangeredAnimal> allEnda() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT name, type FROM animals WHERE type=:type";
            return con.createQuery(sql)
                    .addParameter("type", "endangered")
                    .executeAndFetch(EndangeredAnimal.class);
        }
    }

    public static Animal findEndangered(int id) {
        try(Connection con = DB.sql2o.open()){
            String sql = "SELECT * FROM animals WHERE id=:id AND type=:type";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("type", "endangered")
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animal.class);
        }
    }
}
