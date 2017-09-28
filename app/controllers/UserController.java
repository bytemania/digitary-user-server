package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import repository.UserRepository;
import util.Util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public CompletionStage<Result> list(int pageSize, int pageNumber, int since){
        Timestamp sinceTs = Util.subtractMinutesCurrTime(since);
        return userRepository.list(pageSize, pageNumber, sinceTs).thenApplyAsync(
            paginated -> ok(toJson(paginated)), ec.current());
    }

    public CompletionStage<Result> listAll(){
        return userRepository.list().thenApplyAsync(
                userStream -> ok(toJson(userStream.collect(Collectors.toList())))
                , ec.current());
    }

    public CompletionStage<Result> getTotalPages(int pageSize, int since){
        Timestamp sinceTs = Util.subtractMinutesCurrTime(since);
        return userRepository.getTotalPages(pageSize, sinceTs).thenApplyAsync(
                nPages -> {
                    DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
                    String reportDate = df.format(sinceTs);

                    ObjectNode result = Json.newObject();
                    result.put("totalPages", nPages);
                    result.put("pageSize", pageSize);
                    result.put("since:", since);
                    result.put("date:", reportDate);

                    return ok(toJson(result));
                }
        );
    }
}
