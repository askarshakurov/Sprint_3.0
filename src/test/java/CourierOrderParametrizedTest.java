import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class CourierOrderParametrizedTest  {
    private final Order order;
    public OrderClient orderClient;
    private final int expectedCode;
    private int orderId;

    @Before
    public void setup() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {orderClient.orderCancel(orderId); }

    public CourierOrderParametrizedTest(Order order, int expectedCode) {
        this.order = order;
        this.expectedCode = expectedCode;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderParams() {
        return new Object[][] {
                {Order.getOrderWithColors(new String[]{"GREY"}), 201},
                {Order.getOrderWithColors(new String[]{"BLACK"}), 201},
                {Order.getOrderWithColors(new String[]{"GREY","BLACK"}), 201},
                {Order.getOrderWithoutColors(), 201}
        };
    }

    @Test
    public void testCreateOrderWithDifferentColours() {
        Order order = Order.getFakeOrder();
        int actualCode = orderClient.orderCreateGivesCode(order);
        orderId = orderClient.orderCreateGivesTrack(order);

        Assert.assertEquals(actualCode, expectedCode);
        assertThat("ID not created", orderId, is(not(0)));
    }
}
