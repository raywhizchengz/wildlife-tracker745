package interfaces;

import models.Animal;

import java.util.List;

public interface AnimalInterface {
    //SAVE
    void save();
    void update(String name);

    // DELETE
    void delete();
    // void clearAllTasks();
}
