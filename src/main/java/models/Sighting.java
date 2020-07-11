package models;

import interfaces.SightingInterface;
import org.sql2o.Connection;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Sighting implements SightingInterface {

    private int id;
    private int animalId;
    private int locationId;
    private Timestamp date;
    private String animalType;
    private int rangerId;

    public Sighting(int animalId, int rangerId,int locationId, String animalType){
        this.animalId = animalId;
        this.rangerId = rangerId;
        this.locationId = locationId;
        this.date= new Timestamp(new Date().getTime());
        this.animalType = animalType;
    }

    public int getId() {
        return id;
    }

    public int getRangerId() {
        return rangerId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getAnimalType() {
        return animalType;
    }

    public String getLocationName() {
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT name FROM locations WHERE id=:id")
                    .addParameter("id", this.locationId)
                    .executeAndFetchFirst(String.class);
        }
    }

    public String getAnimalName() {
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT name FROM animals WHERE id=:id")
                    .addParameter("id", this.animalId)
                    .executeAndFetchFirst(String.class);
        }
    }


    public String getRangerName() {
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT name FROM rangers WHERE id=:id")
                    .addParameter("id", this.rangerId)
                    .executeAndFetchFirst(String.class);
        }
    }
    @Override
    public void save(){
        try(Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO sightings (animalId, rangerId, locationId, date, animalType) VALUES (:animalId," +
                    " " +
                    ":rangerId, :locationId, :date, :animalType)";
            this.id = (int) con.createQuery(sql,true)
                    .addParameter("animalId", this.animalId)
                    .addParameter("rangerId", this.rangerId)
                    .addParameter("locationId", this.locationId)
                    .addParameter("date", this.date)
                    .addParameter("animalType", this.animalType)
                    .throwOnMappingFailure(false)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Sighting> all(){

        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM sightings")
                    .executeAndFetch(Sighting.class);
        }
    }

    public static List<Sighting> allAnimals() {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM sightings WHERE animalType=:type")
                    .addParameter("type", Animal.ANIMAL_TYPE)
                    .executeAndFetch(Sighting.class);
        }
    }

    public static List<Sighting> allEndangered() {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM sightings WHERE animalType=:type")
                    .addParameter("type", EndangeredAnimal.ANIMAL_TYPE)
                    .executeAndFetch(Sighting.class);
        }
    }

    public static Sighting find(int id){
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM sightings WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
        }
    }

    @Override
    public boolean equals (Object otherSighting){
        if (!(otherSighting instanceof Sighting)){
            return false;
        }else{
            Sighting sighting =(Sighting) otherSighting;
            return this.getId() == sighting.getId()&&
                    this.getAnimalId()==sighting.getAnimalId() &&
                    this.getLocationId()==sighting.getLocationId()&&
                    this.getRangerId()==sighting.getRangerId();
        }
    }

    @Override
    public void delete() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM sightings WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}


