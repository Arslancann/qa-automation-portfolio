package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class FirstApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }


    @Test
    void tekBirPost_getirilmeli() {

        //kodun ne çektiği yazdırılır ama kontrolden sonra silinmelidir.
        /*var response = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();
        System.out.println(response.asPrettyString());*/

        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", not(emptyString()));
    }

    @Test
    void tumPostlar_listelenmeli() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", hasItem(1));
    }

    @Test
    void varOlmayanPost_404_donmeli() {
        given()
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404);
    }

    @Test
    void yeniPost_olusturulmali() {
        String requestBody = """
                {
                    "title": "e-ihale otomasyon testi",
                    "body": "Rest Assured ile olusturuldu",
                    "userId": 1
                }
                """;

        given()
                .contentType(ContentType.JSON)   // "JSON gönderiyorum" header'ı
                .body(requestBody)                // gönderilecek JSON
                .when()
                .post("/posts")
                .then()
                .statusCode(201)                  // 201 = Created (oluşturuldu)
                .body("title", equalTo("e-ihale otomasyon testi"))
                .body("userId", equalTo(1))
                .body("id", notNullValue());      // sunucu yeni id atamış olmalı
    }

    @Test
    void pathVeQueryParametreleri_kullanilmali() {
        // PATH parametresi: /posts/{id} -> id yerine 1 koyulur
        given()
                .pathParam("postId", 1)
                .when()
                .get("/posts/{postId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));

        // QUERY parametresi: /posts?userId=1 -> filtreleme
        given()
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("userId", everyItem(equalTo(1)));  // hepsi userId=1 olmalı
    }

    @Test
    void response_pojoya_donusturulmeli() {
        Post post = given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Post.class);   // ← JSON'u Post nesnesine dönüştür

        // Artık tip güvenli nesneyle çalışıyoruz
        assertEquals(1, post.getId());
        assertEquals(1, post.getUserId());
        assertNotNull(post.getTitle());
        assertFalse(post.getTitle().isEmpty());
    }
}
