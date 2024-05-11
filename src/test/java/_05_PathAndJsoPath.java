import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class _05_PathAndJsoPath {

    @Test
    public void extractigPath() {

        // Gele body de ki bilgiyi dışarı almanın 2 yöntemini gördük
        // .extract.path("")     , as(Todo.class)

        String postCode = given()

                .when().get("http://api.zippopotam.us/us/90210")

                .then().log().body()
                .extract().path("'post code'");
        ;
        System.out.println("postCode = " + postCode);
        int postInt = Integer.parseInt(postCode);
        System.out.println("postInt = " + postInt);

    }

    @Test
    public void extractingJsonPath() {

        int postCode = given()
                .when().get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .extract().jsonPath().getInt("'post code'");

        ;
        System.out.println("postCode = " + postCode);

    }

    @Test
    public void getZipCode() {

        Response response =
                given()
                        .when().get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().response();
        Location locationAsPath = response.as(Location.class);//  Bütün class yapısı yazılmak zorudadır
        System.out.println("locationAsPath = " + locationAsPath.getPlaces());
        // Bana sadece place dizisi lazım olsa bile, bütün diğer class ları yazmak zorundayım

        List<Place> places = response.jsonPath().getList("place", Place.class);
        // Sadece Place dizisi lazım ise diğerlerini yazmak zorunda değilsin.

        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.

        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ile veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.

    }
    // Soru  : https://gorest.co.in/public/v1/users  endpointte dönen Sadece Data Kısmını POJO
    // dönüşümü ile alarak yazdırınız.

    @Test
    public void soru(){

        // Soru  : https://gorest.co.in/public/v1/users  endpointte dönen Sadece Data Kısmını POJO
        // dönüşümü ile alarak yazdırınız.
        given()
                .when().get("https://gorest.co.in/public/v1/users")
                .then().log().body()


                ;

    }

}
