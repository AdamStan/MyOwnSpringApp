package deanoffice.services;

import java.util.List;

public interface BaseService<T> {
    T getObjectToAdd();
    T getObjectToEdit(String id);
    void removeObject(String id);
    List<T> getAll();
}
