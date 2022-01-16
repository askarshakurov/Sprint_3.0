import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class CourierLoginTest {
    public CourierClient courierClient;
    private int courierId;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Курьер может авторизоваться/Успешный запрос возвращает ")
    public void testCourierIsLoggedIn() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        int codeSuccess = courierClient.logSuccessCode(CourierCredentials.getCourierCredentials(courier));

        assertThat("Courier ID is incorrect", courierId, is(not(0)));
        assertThat("Courier ID is incorrect", codeSuccess, is(200));

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Для авторизации нужно передать все обязательные поля/Если какого-то поля нет, запрос возвращает ошибку")
    public void testCourierLoginWithoutRequiredFields() {
        Courier courierWithoutPassword = new Courier("ninja", null, null);
        Courier courierWithoutLogin = new Courier(null, "1234", null);

        String errorTextOfNoPassword = courierClient.loginWithoutRequiredFieldsReturnsMessage(CourierCredentials.getCourierCredentials(courierWithoutPassword));
        assertThat("Courier ID is incorrect", errorTextOfNoPassword, is("Недостаточно данных для входа"));

        String errorTextOfNoLogin = courierClient.loginWithoutRequiredFieldsReturnsMessage(CourierCredentials.getCourierCredentials(courierWithoutLogin));
        assertThat("Courier ID is incorrect", errorTextOfNoLogin, is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин или пароль")
    public void testCourierLoginWithWrondCredetialsReturnsCode() {
        Courier courierWithWrongPassword = new Courier("ninja", "1234", null);
        int errorCode = courierClient.loginWithWrondCredetialsReturnsCode(CourierCredentials.getCourierCredentials(courierWithWrongPassword));

        assertThat("Courier ID is incorrect", errorCode, is(404));

    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void testCourierLoginWithNoExistedLoginReturnsErrorText() {
        Courier courierWithWrongPassword = new Courier("accountForApiTesting", "1234", null);
        String errorText = courierClient.loginWithNotExistingLoginReturnsMessage(CourierCredentials.getCourierCredentials(courierWithWrongPassword));

        assertThat("Courier ID is incorrect", errorText, is("Учетная запись не найдена"));

    }
}
