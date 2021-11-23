package ca.concordia.poll.app.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(int id);

    List<T> getAll();

    T save(T t);

    T update(T t, String[] params);

    T delete(T t);
}
