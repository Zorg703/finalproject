package by.mordas.project.dao;

import by.mordas.project.entity.Entity;

import java.util.List;

public abstract class AbstractDAO<K,T extends Entity>{
    public abstract List<T> findAll();
    public abstract T findEntityById(int id);
    public abstract boolean delete(int id);
    public abstract boolean create(T entity);
    public abstract T update(T entity);
}
