package pl.edu.agh.metal.awatroba.classregister.webservices;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class InMemoryRepository<T, ID extends Serializable> implements JpaRepository<T, ID> {

    protected Map<ID, T> database = new LinkedHashMap<>();

    @Override
    public <S extends T> S save(S entity) {
        if (retrieveId(entity) == null) {
            assignId(entity);
        }
        database.put(retrieveId(entity), entity);
        return entity;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public boolean existsById(ID id) {
        return database.containsKey(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        List<T> result = new ArrayList<>();
        ids.forEach(id -> {
            if (database.containsKey(id))
                result.add(database.get(id));
        });
        return result;
    }

    @Override
    public long count() {
        return database.size();
    }

    @Override
    public void deleteById(ID id) {
        database.remove(id);
    }

    @Override
    public void delete(T entity) {
        database.remove(retrieveId(entity));
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            database.remove(retrieveId(entity));
        }
    }

    @Override
    public void deleteAll() {
        database.clear();
    }

    @Override
    public List<T> findAll(Sort sort) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public void flush() {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public void deleteAllInBatch() {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public T getOne(ID id) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        throw new RuntimeException("Method not implemented.");
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        throw new RuntimeException("Method not implemented.");
    }

    private <S extends T> void assignId(S entity) {
        try {
            entity.getClass().getDeclaredMethod("setId", Long.class).invoke(entity, ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE));
        } catch (Exception e) {
            throw new RuntimeException("Couldn't assign entity ID.");
        }
    }

    @SuppressWarnings("unchecked")
    private <S extends T> ID retrieveId(S entity) {
        try {
            return (ID) entity.getClass().getDeclaredMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't retrieve entity ID.");
        }
    }
}
