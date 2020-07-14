package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class RangerTest {

    @Before
    public void setUp() throws Exception {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test","calvo-linus","Somoca1421@.");
    }

    @After
    public void tearDown() throws Exception {
        try (Connection conn = DB.sql2o.open()){
            String sql = "DELETE FROM rangers *;";
            conn.createQuery(sql).executeUpdate();
        }
    }
    @Test
    public void Ranger_instantiatesCorrectly_true(){
        Ranger ranger = new Ranger("Donson", 89898);
        assertEquals(true, ranger instanceof Ranger);
    }
    @Test
    public void Ranger_instantiatesWithName_String() {
        Ranger ranger = new Ranger("Donson", 89898);
        assertEquals("Donson", ranger.getName());
    }
    @Test
    public void Ranger_instantiatesWithAnId() {
        Ranger ranger = new Ranger("Donson", 89898);
        ranger.save();
        assertTrue(ranger.getId()>0);
    }
    @Test
    public void save_savesCorrectly() {
        Ranger ranger = new Ranger("Donson", 89898);
        ranger.save();
        assertTrue(Ranger.all().get(0).equals(ranger));
    }
    @Test
    public void find_returnsRangerWIthSameID_secondRanger(){
        Ranger ranger = new Ranger("Donson", 89898);
        ranger.save();
        Ranger rangerTwo = new Ranger("Kalius", 89798);
        rangerTwo.save();
        assertEquals(Ranger.find(rangerTwo.getId()), rangerTwo);
    }
    @Test
    public void equals_returnsTrueIfRangersAreSame(){
        Ranger ranger = new Ranger("Donson", 89898);
        Ranger rangerTwo = new Ranger("Donson", 89898);
        assertTrue(ranger.equals(rangerTwo));
    }
    @Test
    public void save_returnsTrueIfNamesAreTheSame(){
        Ranger ranger = new Ranger("Donson", 89898);
        ranger.save();
        assertEquals(Ranger.all().get(0), ranger);
    }
    @Test
    public void all_returnsAllInstancesOfRangers_true(){
        Ranger rangerOne = new Ranger("Donson", 89898);
        rangerOne.save();
        Ranger rangerTwo = new Ranger("Kalius", 89798);
        rangerTwo.save();
        assertEquals(Ranger.all().get(0), rangerOne);
        assertEquals(Ranger.all().get(1), rangerTwo);
    }
    @Test
    public void save_assignsIdToObject() {
        Ranger ranger = new Ranger("Donson", 89898);
        ranger.save();
        Ranger savedRanger = Ranger.all().get(0);
        assertEquals(ranger.getId(), savedRanger.getId());
    }
    @Test
    public void update_updateRanger_true(){
        Ranger ranger = new Ranger("Donson", 89898);
        ranger.save();
        ranger.update("Simon", 90899);
    }
    @Test
    public void delete_deletesRanger_true(){
        Ranger ranger = new Ranger("Donson", 89898);
        ranger.save();
        int rangerId = ranger.getId();
        ranger.delete();
        assertEquals(null, Ranger.find(rangerId));
    }

}