package global;

import com.google.inject.Inject;
import models.Contact;
import models.User;
import play.Logger;
import service.UserService;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Singleton
public class OnStartup {

    private static final Logger.ALogger logger = Logger.of(OnStartup.class);
    private final UserService userService;

    @Inject
    public OnStartup(UserService userService) {
        this.userService = userService;
        seed();
    }

    private void seed()
    {
        logger.info("Seeding data...");
        IntStream.rangeClosed(1, 100).forEach(i -> userService.create(createUser(i)));
    }

    private User createUser(int i)
    {
        User user = new User();
        user.setName("name" + i);

        Contact contact = new Contact();
        contact.setEmail("email@example.org" +i);
        contact.setAddress1("address1_" + i);
        contact.setAddress2("address2_" + i);
        contact.setCity("city" + i);
        contact.setPostalCode("postalCode" + i);
        contact.setCountry("country" + i);
        List<String> phones = Arrays.asList("000_" + i, "111_" + i, "222_" + i);
        contact.setPhones(phones);

        user.setContact(contact);

        return user;
    }

}
