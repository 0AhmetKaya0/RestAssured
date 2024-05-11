package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GoRestUserTest {
    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    int userId = 0;

    // Token aldım
    // UsersCreate için neler lazım ( Body'de gönderilcek bilgiler( User bilgileri))
    // endpoint i aldım gidiş metodu
    // Before class'ın içinde yapılacaklar ar mı? nelerdir? url set ve spec hazırlanmalı


    @BeforeClass
    public void setup() {

        baseURI = "https://gorest.co.in/public/v2/users";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer a1273baef64187c5d68c5306fc5861692ac02477b60eb0d156717b98940a5628")
                .setContentType(ContentType.JSON).build();


    }

    //Create User Testini yapınız
    @Test
    public void CreateUser() {
        String rndFullName = randomUretici.name().fullName();
        String rndEmail = randomUretici.internet().emailAddress();
        Map<String, String> newUser = new HashMap<>();
        newUser.put("name", rndFullName);
        newUser.put("gender", "male");
        newUser.put("email", rndEmail);
        newUser.put("status", "active");

        userId = given()
                .spec(reqSpec)
                .body(newUser)

                .when()
                .post("") //http yok ise direkt baseurı alıyor

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")

        ;

    }

    //Get User BY İd testini yapınız

    @Test(dependsOnMethods = "CreateUser")
    public void GetUserById() {


        given()
                .spec(reqSpec)

                .when()
                .get("/" + userId)


                .then()
                .statusCode(200)
                .log().body()
                .body("id", equalTo(userId))


        ;

    }

    // Update User yapınız
    @Test(dependsOnMethods = "GetUserById")
    public void updateUser() {
        String name = "Ahmet Kaya";
        Map<String, String> updateUser = new HashMap<>();
        updateUser.put("name", name);

        given()
                .spec(reqSpec)
                .body(updateUser)
                .when().put("/" + userId)

                .then()
                .statusCode(200)
                .log().body()
                .body("id", equalTo(userId))
                .body("name", equalTo(name))


        ;

    }

    // Delete User
    @Test(dependsOnMethods = "updateUser")
    public void deleteUser(){

        given().spec(reqSpec)


                .when().delete("/"+userId)

                .then()
                .statusCode(204)
                .log().body()

                ;


    }
    //Delete user negative

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegative(){


        given().spec(reqSpec)


                .when()
                .delete("/"+userId)

                .then()
                .statusCode(404)

        ;


    }



}
