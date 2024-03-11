import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MainTest {

    @Test
    public void testOpenShift() {
        String sessionid = given()
                .when().post(MainPage.getUrlLogin())
                .then().extract().response().jsonPath().getString("sessionId");

        given().body(MainPage.getCloseShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlCloseShift())
                .then();

        given().body(MainPage.getOpenShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlOpenShift())
                .then().statusCode(200).log().all()
                .body("shift.params.cashier.id", equalTo("2"))
                .body("shift.params.cashier.name", equalTo("Иванов И.И."));
    }

    @Test
    public void testOpenShiftWithOpenedShift() {
        String sessionid = given()
                .when().post(MainPage.getUrlLogin())
                .then().extract().response().jsonPath().getString("sessionId");

        given().body(MainPage.getOpenShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlOpenShift())
                .then();

        given().body(MainPage.getOpenShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlOpenShift())
                .then().statusCode(400).log().all()
                .body("error.message", equalTo("Смена открыта (команда возможна только при закрытой смене)"));
    }

    @Test
    public void testOpenShiftWithoutAutorization() {
        given().body(MainPage.getOpenShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", "")
                .when().post(MainPage.getUrlOpenShift())
                .then().statusCode(401).log().all();
    }

    @Test
    public void testCloseShift() {
        String sessionid = given()
                .when().post(MainPage.getUrlLogin())
                .then().extract().response().jsonPath().getString("sessionId");

        given().body(MainPage.getOpenShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlOpenShift())
                .then();

        given().body(MainPage.getCloseShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlCloseShift())
                .then().statusCode(200).log().all()
                .body("zReport.xReport.tokenInfo.unp", equalTo("193736223"));
    }

    @Test
    public void testCloseShiftWithoutOpenedShift() {
        String sessionid = given()
                .when().post(MainPage.getUrlLogin())
                .then().extract().response().jsonPath().getString("sessionId");

        given().body(MainPage.getCloseShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlCloseShift())
                .then();

        given().body(MainPage.getCloseShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", sessionid)
                .when().post(MainPage.getUrlCloseShift())
                .then().statusCode(400).log().all()
                .body("error.message", equalTo("Смена закрыта (команда возможна только при открытой смене)"));
    }

    @Test
    public void testCloseShiftWithoutAutorization() {
        given().body(MainPage.getCloseShiftBody())
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("sessionid", "")
                .when().post(MainPage.getUrlCloseShift())
                .then().statusCode(401).log().all();
    }

}
