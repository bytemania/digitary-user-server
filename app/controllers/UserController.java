package controllers;

import com.google.inject.Inject;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.UserRepository;

import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class UserController extends Controller {

    private final UserRepository userRepository;
    private final HttpExecutionContext ec;

    @Inject
    public UserController(UserRepository userRepository, HttpExecutionContext ec) {
        this.userRepository = userRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> get(int id){
        return userRepository.get(id).thenApplyAsync(user -> {
                    if (user.isPresent()) {
                        return ok(toJson(user.get()));
                    } else {
                        return badRequest("User with id " + id + " not found");
                    }
                }, ec.current());
    }

    public CompletionStage<Result> list(){
        return userRepository.list().thenApplyAsync(
            userStream -> ok(toJson(userStream.collect(Collectors.toList())))
            , ec.current());
    }
}
