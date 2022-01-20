import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.*;
import io.restassured.response.ValidatableResponse;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders/";

    @Step("create new order that receives trackId")
    public Integer orderCreateGivesTrack(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("track");
    }

    @Step("create new order")
    public Integer orderCreateGivesCode(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .statusCode();
    }

    @Step("cancel order")
    public boolean orderCancel(int orderId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .put(ORDER_PATH+ "cancel?track=" + orderId)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }

    @Step("view all orders")
    public ValidatableResponse viewOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}
