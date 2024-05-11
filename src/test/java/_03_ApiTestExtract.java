import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _03_ApiTestExtract {


    @Test
    public void checkCountryResponseBody() {
        String ulkeAdi = given()
                .when().get("http://api.zippopotam.us/us/90210")
                .then().extract().path("country"); // path'i coutry olan değeri extract yap

        System.out.println("ulkeAdi = " + ulkeAdi);
        Assert.assertEquals(ulkeAdi, "United States"); // alınan değer eşit mi

    }

    @Test
    public void Soru() {

        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız
        String dogrulama = given()
                .when().get("http://api.zippopotam.us/us/90210")

                .then().extract().path("places[0].state");
        System.out.println("dogrulama = " + dogrulama);

        Assert.assertEquals(dogrulama, "California");


    }

    @Test
    public void soru2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
        // olduğunu testNG Assertion ile doğrulayınız

        String dogrulama =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then().extract().path("places[0].'place name'");
        System.out.println("dogrulama = " + dogrulama);
        Assert.assertEquals(dogrulama, "Beverly Hills");


    }

    @Test
    public void soru3() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.

        int dogrulama = given().when().get("https://gorest.co.in/public/v1/users")
                .then()
                .extract().path("meta.pagination.limit");
        System.out.println("dogrulama = " + dogrulama);
        Assert.assertEquals(dogrulama, 10);


    }

    @Test
    public void topluAlimKontrol() {

        List<Integer> idler = given()
                .when().get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .extract().path("data.id");
        System.out.println("idler = " + idler);


    }


    @Test
    public void soru4() {
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // bütün name leri yazdırınız.

        List<String> names = given()
                .when().get("https://gorest.co.in/public/v1/users")

                .then()
                .extract().path("data.name");
        System.out.println("names = " + names);


    }

    @Test
    public void birdenFazlaBilgiAlma(){


        Response donenData=given()
                .when().get("https://gorest.co.in/public/v1/users")

                .then()
                .extract().response()
                ;

        List<Integer>idler=donenData.path("data.id");
        List<String>names=donenData.path("data.name");
        int limit=donenData.path("meta.pagination.limit");
        System.out.println("idler = " + idler);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(idler.contains(6892472));
        Assert.assertTrue(names.contains("Geeta Gandhi"));
        Assert.assertEquals(limit,10);

    }
}
