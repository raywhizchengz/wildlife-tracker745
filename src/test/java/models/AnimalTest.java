package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class AnimalTest {

    @Before
    public void setUp()  {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "calvo-linus", "Somoca1421@.");
    }

    @After
    public void tearDown() {
        try(Connection con = DB.sql2o.open()) {
            String sqlAnimal = "DELETE FROM animals *;";
            con.createQuery(sqlAnimal).executeUpdate();
        }
    }

    @Test
    public void instantiatesCorrectly_true(){
        Animal animal = new Animal("Chui");
        assertEquals(true, animal instanceof Animal);
    }
    @Test
    public void Animal_instantiatesCorrectlyWithType_String() {
        Animal animal = new Animal("Chui");
        assertEquals("animal", animal.getType());
    }
    @Test
    public void Animal_instantiatesWithName_String() {
        Animal animal = new Animal("Chui");
        assertEquals("Chui", animal.getName());
    }

    @Test
    public void Animal_instantiatesCorrectlyWithAnId() {
        Animal animal = new Animal("Chui");
        animal.save();
        assertTrue(animal.getId()>0);
    }
    @Test
    public void save_CorrectlyIntoTheDatabase() {
        Animal animal = new Animal("Chui");
        animal.save();
        assertTrue(Animal.all().get(0).equals(animal));
    }
    @Test
    public void findById_returnsAnimalWIthSameID_secondAnimal(){
        Animal animalOne = new  Animal("Chui");
        animalOne.save();
        Animal animalTwo = new Animal("Impalla");
        animalTwo.save();
        assertEquals(Animal.findById(animalTwo.getId()), animalTwo);
    }
    @Test
    public void equals_returnsTrueIfAnimalsAreSame(){
        Animal animalOne = new Animal("Chui");
        Animal animalTwo = new Animal("Chui");
        assertTrue(animalOne.equals(animalTwo));
    }
    @Test
    public void save_returnsTrueIfNamesAreTheSame(){
        Animal animal = new Animal("Chui");
        animal.save();
        assertEquals(Animal.all().get(0), animal);
    }
    @Test
    public void all_returnsAllInstancesOfAnimals_true(){
        Animal animalOne = new  Animal("Chui");
        animalOne.save();
        Animal animalTwo = new Animal("Impala");
        animalTwo.save();
        assertEquals(Animal.all().get(0), animalOne);
        assertEquals(Animal.all().get(1), animalTwo);
    }
    @Test
    public void save_assignsIdToObject() {
        Animal animal = new Animal("Chui");
        animal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(animal.getId(), savedAnimal.getId());
    }

    @Test
    public void delete_deletesAnimal_true(){
        Animal animal = new Animal("Chui");
        animal.save();
        int animalId = animal.getId();
        animal.delete();
        assertEquals(null, Animal.findById(animalId));
    }

}