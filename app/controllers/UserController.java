package controllers;

import com.google.inject.Inject;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import service.UserService;

import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

public class UserController extends Controller {

    private final UserService userService;
    private final HttpExecutionContext ec;

    @Inject
    public UserController(UserService userService, HttpExecutionContext ec) {
        this.userService = userService;
        this.ec = ec;
    }

    public CompletionStage<Result> get(int id){
        return userService.get(id).thenApplyAsync(user -> {
                    if (user.isPresent()) {
                        return ok(toJson(user.get()));
                    } else {
                        return badRequest("User with id " + id + " not found");
                    }
                }, ec.current());
    }

    public CompletionStage<Result> list(){
        return userService.list().thenApplyAsync(
            userStream -> ok(toJson(userStream.collect(Collectors.toList())))
            , ec.current());
    }
}
