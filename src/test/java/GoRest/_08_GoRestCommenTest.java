package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class _08_GoRestCommenTest {
    // Soru CreateComment Testini Yapınız

    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    int commentId = 0;

    // Token aldım
    // UsersCreate için neler lazım ( Body'de gönderilcek bilgiler( User bilgileri))
    // endpoint i aldım gidiş metodu
    // Before class'ın içinde yapılacaklar ar mı? nelerdir? url set ve spec hazırlanmalı

//    {
//        "id": 95377,
//            "post_id": 122490,
//            "name": "Ahmet Kaya",
//            "email": "deneme@techno.com",
//            "body": "In ut vel."
//    }


    @BeforeClass
    public void setup() {

        baseURI = "https://gorest.co.in/public/v2/comments";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer a1273baef64187c5d68c5306fc5861692ac02477b60eb0d156717b98940a5628")
                .setContentType(ContentType.JSON).build();


    }

    @Test
    public void CreateComment() {
        String fullName = randomUretici.name().fullName();
        String email = randomUretici.internet().emailAddress();
        String body = randomUretici.lorem().paragraph();
        String postId = "122490";

        Map<String, String> newComment = new HashMap<>();
        newComment.put("name", fullName);
        newComment.put("email", email);
        newComment.put("body", body);
        newComment.put("post_id", postId);


        commentId = given().spec(reqSpec)
                .body(newComment)


                .when().post("https://gorest.co.in/public/v2/comments")


                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")

        ;
        System.out.println("newComment = " + commentId);

    }

    // Create edilen comment'ı get ile çağırık kontrolünü sağla

    @Test(dependsOnMethods = "CreateComment")
    public void getCommentById() {


        given().spec(reqSpec)

                .when().get("https://gorest.co.in/public/v2/comments/" + commentId)

                .then().log().body()
                .body("id", equalTo(commentId))

        ;
    }

    // Soru Create edilen comment'ın name güncelleyiniz

    @Test(dependsOnMethods = "getCommentById")
    public void updateComment() {
        String name = "Ahmet Kaya";
        Map<String, String> updateComment = new HashMap<>();
        updateComment.put("name", name);

        given().spec(reqSpec)
                .body(updateComment)

                .when().put("https://gorest.co.in/public/v2/comments/" + commentId)

                .then().log().body()
                .body("id", equalTo(commentId))
                .body("name", equalTo(name))

        ;

    }
    // Soru Delete Comment yapınız

    @Test(dependsOnMethods = "updateComment")
    public void deleteComment(){

        given().spec(reqSpec)


                .when().delete("https://gorest.co.in/public/v2/comments/" + commentId)

                .then().statusCode(204)


        ;

    }
    @Test(dependsOnMethods = "deleteComment")
    public void deleteCommentNegative(){

        given().spec(reqSpec)


                .when().delete("https://gorest.co.in/public/v2/comments/" + commentId)

                .then().log().body()
                .statusCode(404)


        ;

    }


}
