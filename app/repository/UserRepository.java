package repository;

import com.google.inject.ImplementedBy;
import models.User;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(JpaUserRepository.class)
public interface UserRepository {

    CompletionStage<User> create(User user);

    CompletionStage<Stream<User>> list();
}
