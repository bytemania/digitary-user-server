package service;

import com.google.inject.Inject;
import models.User;
import play.db.jpa.Transactional;
import repository.UserRepository;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CompletionStage<User> create(User user) {
        return userRepository.create(user);
    }

    @Override
    @Transactional(readOnly = true)
    public CompletionStage<Stream<User>> list() {
        return userRepository.list();
    }
}
