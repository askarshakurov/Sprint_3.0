import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {
    public CourierClient courierClient;
    private int courierId;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {courierClient.delete(courierId); }

    @Test
    @DisplayName("Курьера можно создать/Успешный запрос возвращает ok: true;\n")
    public void testCourierIsCreating() {
        Courier courier = Courier.getRandom();

        boolean isCreated = courierClient.create(courier);
        assertTrue("Courier is not created", isCreated);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    public void testCourierCodeOfCreatingCheck() {
        Courier courier = Courier.getRandom();

        int code = courierClient.createCode(courier);
        assertThat("Courier ID is incorrect", code, is(201));

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void testCourierCreateExistedCheckCode() {
        Courier fistCreated = Courier.getRandom();
        Courier secondCreated = new Courier(fistCreated.login, fistCreated.password, fistCreated.firstname);
        courierClient.create(fistCreated);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(fistCreated));

        int code = courierClient.recreate(secondCreated);
        assertThat("Courier ID is incorrect", code, is(409));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров;")
    public void testCourierCreateExistedCheckErrorText() {
        Courier fistCreated = Courier.getRandom();
        Courier secondCreated = new Courier(fistCreated.login, fistCreated.password, fistCreated.firstname);
        courierClient.create(fistCreated);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(fistCreated));

        //Текст не сходится с документацией,поэтому он будет падать.
        String errorText = courierClient.recreateErrorText(secondCreated);
        assertThat("Courier ID is incorrect", errorText, is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void testCreateCourierWithoutFirstName(){
        Courier courier = Courier.getRandomNoName();
        boolean isCreated = courierClient.create(courier);
        assertTrue("Courier is not created", isCreated);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

     @Test
     @DisplayName("Если одного из полей нет, запрос возвращает ошибку")
    public void testCreateWithoutPassword() {
        Courier courier = new Courier("loginChecker123");
        Integer errorCode = courierClient.createWithoutPassword(courier);
        assertThat("Courier ID is incorrect", errorCode, is(400));

         //Это был единственный тест из всех,где не нужно было удалять ID курьера после создания,
         // поэтому сделал костыль из создания и удаления курьера чтобы тест не падал
        Courier courierForDeleting = Courier.getRandom();
        courierClient.create(courierForDeleting);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courierForDeleting));

     }
}
