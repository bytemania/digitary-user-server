package repository;

import com.google.inject.Inject;
import models.User;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JpaUserRepository implements UserRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JpaUserRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<User> create(User user) {
        return supplyAsync(() -> wrap(em -> insert(em, user)), executionContext);
    }

    @Override
    public CompletionStage<Optional<User>> get(int id) {
        return supplyAsync(() -> wrap(em -> get(em, id)), executionContext);
    }

    @Override
    public CompletionStage<Stream<User>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<User> get(EntityManager em, int id) {
        User user = em.find(User.class, id);
        if (user != null) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }

    }

    private User insert(EntityManager em, User user) {
        em.persist(user);
        return user;
    }

    private Stream<User> list(EntityManager em) {
        List<User> persons = em.createQuery("select u from User u", User.class).getResultList();
        return persons.stream();
    }

}
