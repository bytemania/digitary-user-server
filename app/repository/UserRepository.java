package repository;

import com.google.inject.ImplementedBy;
import models.Paginated;
import models.User;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(JpaUserRepository.class)
public interface UserRepository {

    CompletionStage<User> create(User user);

    CompletionStage<Optional<User>> get(int id);

    CompletionStage<Paginated<User>> list(int pageSize, int pageNumber, Timestamp sinceTs);

    CompletionStage<Integer> getTotalPages(int pageSize, Timestamp ts);

    CompletionStage<Stream<User>> list();
}
