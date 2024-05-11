import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class _02_ApiTest {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    @BeforeClass
    public void setup(){
        baseURI="https://gorest.co.in/public/v1"; //when  // hazırda tanımlanmış RESTASSURED ait değişken

        requestSpec=new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI) // log.uri
                .build();

        responseSpec=new ResponseSpecBuilder()
                .expectStatusCode(200)  // statusCode(200)
                .log(LogDetail.BODY)// log().body()
                .expectContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void test(){

        given()
                .spec(requestSpec)

                .when()
                .get("/users") // başında http yok ise baseURI başına eklenir
                // https://gorest.co.in/public/v1/users

                .then()
        // test özellikleri , response özellikleri
                .spec(responseSpec)
        ;

    }
}
