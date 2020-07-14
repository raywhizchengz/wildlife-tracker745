package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class SightingTest {

    @Before
    public void setUp() throws Exception {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test","calvo-linus","Somoca1421@.");
    }

    @After
    public void tearDown() throws Exception {
        try (Connection con = DB.sql2o.open()){
            String sql ="DELETE FROM sightings *;";
            con.createQuery(sql).executeUpdate();

        }
    }
    @Test
    public void Sighting_instantiatesCorrectly_true(){
        Sighting sighting = new Sighting(1,2,3,"animal");
        assertEquals(true, sighting instanceof Sighting);
    }
    @Test
    public void Sighting_instantiatesWithAnimalId() {
        Sighting sighting = new Sighting(1,2,3,"animal");
        assertEquals(1, sighting.getAnimalId());
    }
    @Test
    public void Sighting_instantiatesWithAnId() {
        Sighting sighting = new Sighting(1,2,3,"animal");
        sighting.save();
        assertTrue(sighting.getId()>0);
    }
    @Test
    public void save_savesCorrectly() {
        Sighting sighting = new Sighting(1,2,3,"animal");
        sighting.save();
        assertTrue(Sighting.all().get(0).equals(sighting));
    }
    @Test
    public void find_returnsSightingWIthSameID_secondSighting(){
        Sighting sighting = new Sighting(1,2,3,"animal");
        sighting.save();
        Sighting sightingTwo = new Sighting(2,3,4,"animal");
        sightingTwo.save();
        assertEquals(Sighting.find(sightingTwo.getId()), sightingTwo);
    }
    @Test
    public void equals_returnsTrueIfSightingsAreSame(){
        Sighting sighting = new Sighting(1,2,3,"animal");
        Sighting sightingTwo = new Sighting(1,2,3,"animal");
        assertTrue(sighting.equals(sightingTwo));
    }
    @Test
    public void save_returnsTrueIfNamesAreTheSame(){
        Sighting sighting = new Sighting(1,2,3,"animal");
        sighting.save();
        assertEquals(Sighting.all().get(0), sighting);
    }
    @Test
    public void all_returnsAllInstancesOfSightings_true(){
        Sighting sightingOne = new Sighting(1,2,3,"animal");
        sightingOne.save();
        Sighting sightingTwo = new Sighting(2,3,4,"animal");
        sightingTwo.save();
        assertEquals(Sighting.all().get(0), sightingOne);
        assertEquals(Sighting.all().get(1), sightingTwo);
    }
    @Test
    public void save_assignsIdToObject() {
        Sighting sighting = new Sighting(1,2,3,"animal");
        sighting.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(sighting.getId(), savedSighting.getId());
    }

    @Test
    public void delete_deletesSighting_true(){
        Sighting sighting = new Sighting(1,2,3,"animal");
        sighting.save();
        int sightingId = sighting.getId();
        sighting.delete();
        assertEquals(null, Sighting.find(sightingId));
    }
}