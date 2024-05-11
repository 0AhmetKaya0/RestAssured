import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class _01_ApiTest {

    @Test
    public void Test() {

        given()
                // Hazırlık kodları buraya yazılıyor


                .when()
                // endpoint(url), metodu ile birlikte istek göderilme aşaması


                .then();
        // assertion, test, data işlemleri


    }

    @Test
    public void statusCodeTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // gelen body göster
                .statusCode(200); // assert kısmı status 200 mü?

        ;
    }

    @Test
    public void contentTypeTest() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // gelen body göster
                .statusCode(200) // assert kısmı status 200 mü?
                .contentType(ContentType.JSON) // dönen datanın tipi JSON mı?


        ;


    }

    @Test
    public void checkCountryResponseBody() {
        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")


                .then()
                .log().body() // gelen body göster
                .statusCode(200) // assert kısmı status 200 mü?
                .body("country", equalTo("United States")) // Country dışarı almadan
        // bulunduğu yeeri(path i) vererek içeride assertion hamcrest kütüphanesi yapıyor


        ;


    }

    @Test
    public void checkCountryResponseBody2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız
        given()
                .when()

                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].state", equalTo("California"))


        ;


    }

    @Test
    public void generalHasItem() {
        // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
        // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
        // olduğunu doğrulayınız
        given()
                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                .log().body()
                .body("places.'place name'", hasItem("Dörtağaç Köyü"))


        ;


    }

    @Test
    public void arraySizeHasSize() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1))
        ;
    }

    @Test
    public void cumbiningTest() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places[0].state", equalTo("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
        ;
    }

    @Test
    public void pathParamTest() {

        given()
                .pathParam("ulke", "us")
                .pathParam("postaKodu", 90210)
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKodu}")

                .then()
                .log().body()
        ;

    }

    @Test
    public void queryParamTest() {

        given()

                .param("page", 1)
                .log().uri()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                //https://gorest.co.in/public/v1/users?page=3

                .then()
                .log().body()


        ;

    }


    @Test
    public void queryParamSoru() {

        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i <= 10; i++) {


            given()

                    .param("page", i)
                    .log().uri()
                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .body("meta.pagination.page", equalTo(i))


            ;


        }
    }

}
