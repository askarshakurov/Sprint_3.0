import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;

import java.util.List;

public class CourierGetAllOrdersTest {
    public OrderClient orderClient;

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Проверка, что в тело ответа возвращается список заказов")
    public void testOrdersResponseNotNull(){
        ValidatableResponse response = orderClient.viewOrders();
        List<Object> orders = response.extract().jsonPath().getList("orders");

        Assert.assertFalse(orders.isEmpty());
    }
}
