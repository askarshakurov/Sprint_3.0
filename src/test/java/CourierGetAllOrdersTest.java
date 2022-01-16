import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierGetAllOrdersTest {
    public OrderClient orderClient;
    private int orderId;

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {orderClient.orderCancel(orderId); }

    @Test
    public void testOrdersResponseNotNull(){
        Order order = Order.getFakeOrder();
        orderId = orderClient.orderCreateGivesTrack(order);
        orderClient.viewOrders(orderId);
    }
}
