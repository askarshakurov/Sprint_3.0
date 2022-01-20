import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    public  String login;
    public  String password;
    public  String firstname;


    public Courier(String login, String password, String firstname) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
    }

    public Courier() {}

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }

    public Courier setFirstName(String firstName) {
        this.firstname = firstname;
        return this;
    }

    public static Courier getRandomCourierWithAllCredentials() {
        return new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(10))
                .setPassword(RandomStringUtils.randomAlphabetic(10))
                .setFirstName(RandomStringUtils.randomAlphabetic(10));
    }

    public static Courier getRandomCourierWithNoName() {
        return new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(10))
                .setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public static Courier getRandomCourierWithLoginOnly() {
        return new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(10));
    }

    public static Courier getRandomCourierWithPasswordOnly() {
        return new Courier()
                .setPassword(RandomStringUtils.randomAlphabetic(10));
    }
}
