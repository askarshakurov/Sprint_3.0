import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    public final String login;
    public final String password;
    public final String firstname;

    public Courier(String login, String password, String firstname) {
        this.login = login;
        this.password = password;
        this.firstname = firstname;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
        this.firstname = null;
    }

    public Courier(String login)
    {
        this.login = login;
        this.password = null;
        this.firstname = null;
    }

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getRandomNoName() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password);
    }
}
