package service;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import models.User;
import repository.UserRepository;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(UserServiceImpl.class)
public interface UserService {

    CompletionStage<User> create(User user);

    CompletionStage<Stream<User>> list();

}
