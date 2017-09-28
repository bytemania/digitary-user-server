package global;

import com.google.inject.Inject;
import models.Contact;
import models.User;
import play.Logger;
import repository.UserRepository;
import util.Util;

import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Singleton
public class OnStartup {

    private static final Logger.ALogger logger = Logger.of(OnStartup.class);
    private final UserRepository userRepository;

    @Inject
    public OnStartup(UserRepository userRepository) {
        this.userRepository = userRepository;
        seed();
    }

    private void seed()
    {
        logger.info("Seeding data...");
        IntStream.rangeClosed(1, 100).forEach(i -> userRepository.create(createUser(i)));
    }

    private User createUser(int i)
    {
        final int DAY_MINUTES = 60*24;

        User user = new User();
        user.setName("name" + i);

        Timestamp ts = Util.subtractMinutesCurrTime(DAY_MINUTES * i);
        user.setDate(ts);

        Contact contact = new Contact();
        contact.setEmail("email@example.org" +i);
        contact.setAddress1("address1_" + i);
        contact.setAddress2("address2_" + i);
        contact.setCity("city" + i);
        contact.setPostalCode("postalCode" + i);
        contact.setCountry("country" + i);
        List<String> phones = Arrays.asList("000000" + i, "111111" + i, "222222" + i);
        contact.setPhones(phones);

        user.setContact(contact);

        return user;
    }

}
