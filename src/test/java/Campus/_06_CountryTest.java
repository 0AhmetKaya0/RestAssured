package Campus;

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


public class _06_CountryTest {

    RequestSpecification reqSpec;
    Faker randomUreteci = new Faker();
    String countryName = "";
    String countryCode = "";
    String countryId = "";

    @BeforeClass
    public void loginCampus() {
        baseURI = "https://test.mersys.io";

        // Token cookies içinde geldi
        // Cookies i alıcam
        // request spec hazırlayacağım
        // enviroment varible : baseURI

        String userCredential = "{\"username\": \"turkeyts\", \"password\": \"TechnoStudy123\", \"rememberMe\": \"true\"}";
        Map<String, String> userCredMap = new HashMap<>();
        userCredMap.put("username", "turkeyts");
        userCredMap.put("password", "TechnoStudy123");
        userCredMap.put("rememberMe", "true");


        Cookies gelenCookies =
                given()
                        //.body(userCredential)
                        .body(userCredMap)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/auth/login")
                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();
        ;

        // System.out.println("gelenCookies = " + gelenCookies);
        reqSpec = new RequestSpecBuilder()
                .addCookies(gelenCookies)
                .setContentType(ContentType.JSON).build();


    }

    //NOT SPEC BİLGİLERİ GİVENDAN HEMEN SONRA YAZILMALI
    @Test
    public void createCountry() {
        // Burada gelen tooken ın yie cookies içinde geri gitmesi lazım : spec
        countryName = randomUreteci.address().country() + randomUreteci.address().countryCode();
        countryCode = randomUreteci.address().countryCode() + randomUreteci.address().countryCode();

        Map<String, String> newCountry = new HashMap<>();
        newCountry.put("name", countryName);
        newCountry.put("code", countryCode);

        countryId = given()
                .spec(reqSpec) // gelen cookies , yeni istek içi login olduğumun kanıtı olarak gönderildi
                .body(newCountry)
                .when()
                .post("/school-service/api/countries")
                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;


    }


    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative() {
        // Yukarıda gönderilen name ve codu tekrar göndererek kayıt işlemi yapılamadığını doğrulayınız
        // Burada gelen tooken ın yie cookies içinde geri gitmesi lazım : spec
        Map<String, String> renewCountry = new HashMap<>();
        renewCountry.put("name", countryName);
        renewCountry.put("code", countryCode);
        given()
                .spec(reqSpec)
                .body(renewCountry)

                .when()
                .post("/school-service/api/countries")

                .then()
                .statusCode(400)
                .log().body();

        ;


    }

    @Test(dependsOnMethods = "createCountryNegative")
    public void updateCountry() {
        //Soru yukarıda create edilenn ülkenin adını bir başka ülke adı ile güncelleyiniz
        // id olmadan olamaz create countr'den id al
        String updateNewName = "ahmet" + randomUreteci.address().country() + randomUreteci.address().country();

        // Burada gelen tooken ın yie cookies içinde geri gitmesi lazım : spec
        Map<String, String> updateCountry = new HashMap<>();
        updateCountry.put("id", countryId);
        updateCountry.put("name", updateNewName);
        updateCountry.put("code", countryCode);


        given().spec(reqSpec)
                .body(updateCountry)

                .when().put("/school-service/api/countries")

                .then().statusCode(200)
                .log().body()
                .body("name",equalTo(updateNewName))


        ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry(){


        given().spec(reqSpec)

                .when().delete("/school-service/api/countries/"+countryId)

                .then().statusCode(200)

                ;
    }

    //Çalışmıyor ÇüNKÜ Site izin vermiyor

    @Test(dependsOnMethods ="deleteCountry" )
    public void deleteNegativeCountry(){

        given().spec(reqSpec)

                .when().delete("/school-service/api/countries/"+countryId)

                .then().statusCode(400)

        ;


    }


}
