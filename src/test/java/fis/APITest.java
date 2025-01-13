package fis;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class APITest {

    @Test
    public void testCurrentPriceAPI() {
        // 1. Send GET request to the API
        Response response = RestAssured
                .given()
                .baseUri("https://api.coindesk.com")
                .basePath("/v1/bpi/currentprice.json")
                .when()
                .get();

        // 2. Verify the response code is 200 (OK)
        response.then().statusCode(200);

        // 3. Verify the response contains 3 BPIs: USD, GBP, EUR
        response.then().body("bpi.size()", equalTo(3));
        response.then().body("bpi.keySet()", hasItems("USD", "GBP", "EUR"));

        // 4. Verify that the GBP 'description' equals 'British Pound Sterling'
        response.then().body("bpi.GBP.description", equalTo("British Pound Sterling"));
        System.out.println(response.asString());
    }
}
