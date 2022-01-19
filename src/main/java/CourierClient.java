import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class CourierClient extends RestAssuredClient{

    private static final String COURIER_PATH = "/api/v1/courier/";

    @Step("Create courier {courier}")
    public boolean create (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .path("ok");
    }

    @Step("Check courier created status 201")
    public int createCode (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .extract()
                .statusCode();
    }

    @Step("Create courier {courier} again")
    public int recreate (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("code");
    }

    @Step("Create courier {courier} again")
    public String recreateErrorText (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("message");
    }

    @Step("Create courier {courier} without firstName")
    public int createWithoutFirstName (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .extract()
                .path("code");
    }

    @Step("Create courier {courier} without password")
    public int createWithoutPassword (Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("code");
    }

    @Step("Login with courier credentials")
    public int login (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH+ "login")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Step("Login with courier credentials")
    public int logSuccessCode (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH+ "login")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .statusCode();
    }

    @Step("Login without required fields of courier data")
    public String loginWithoutRequiredFieldsReturnsMessage (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH+ "login")
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .extract()
                .path("message");
    }

    @Step("Login without required fields of courier data")
    public String loginWithNotExistingLoginReturnsMessage (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH+ "login")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .path("message");
    }

    @Step("Login without required fields of courier data")
    public Integer loginWithWrondCredentialsReturnsCode (CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH+ "login")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .extract()
                .statusCode();
    }

    @Step("delete courier after all tests")
    public boolean delete (int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .extract()
                .path("ok");
    }
}
