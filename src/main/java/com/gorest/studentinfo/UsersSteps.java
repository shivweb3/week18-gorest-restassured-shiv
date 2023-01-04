package com.gorest.studentinfo;


import com.gorest.constants.EndPoints;
import com.gorest.model.UsersPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class UsersSteps {
    @Step("Creating users with Name : {0}, email: {1}, gender: {2}, and status: {3} ")
    public ValidatableResponse createUsers(String Name, String email, String gender,
                                           String status) {

        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setName(Name);
        usersPojo.setEmail(email);
        usersPojo.setGender(gender);
        usersPojo.setStatus(status);

        return SerenityRest.given().log().all()
               // .header("Accept", "application/json")
             //   .header("Authorization", "Bearer 0f9c492fe9fa1952fa3c73361d40346880784df57eca29ef1832292809c536ae")
                .header("Authorization", "Bearer 15205ae80891734995eabfbadcb20828670fb637b77f8326e3e9ad0fe155ad75")
                .contentType(ContentType.JSON)
                .body(usersPojo)
                .when()
                .post(EndPoints.CREATE_USERS)
                .then();
       // 15205ae80891734995eabfbadcb20828670fb637b77f8326e3e9ad0fe155ad75

               // .contentType(ContentType.JSON)

    }

    @Step("Getting the User information with name: {0}")
    public HashMap<String, Object> getUserInfoByemail(String name) {
        String p1 = "findAll{it.name == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 15205ae80891734995eabfbadcb20828670fb637b77f8326e3e9ad0fe155ad75")

                .when()
                .get(EndPoints.GET_ALL_USER)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
    }

    @Step("Updating user information with name: {0}, email:{1},gender:{2},status:{3} ")
    public ValidatableResponse updateUser(String email, String name, String status, int id,
                                          String gender) {
        UsersPojo usersPojo = new UsersPojo();
        usersPojo.setEmail(email);
        usersPojo.setName(name);
        usersPojo.setStatus(status);

        usersPojo.setGender(gender);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 15205ae80891734995eabfbadcb20828670fb637b77f8326e3e9ad0fe155ad75")
                .pathParam("id", id)
                //.pathParam("user_id", id)
                .body(usersPojo)
                .when()
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Deleting student information with studentId: {0}")
    public ValidatableResponse deleteuser(int id) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 15205ae80891734995eabfbadcb20828670fb637b77f8326e3e9ad0fe155ad75")

                .pathParam("id", id)
               // .pathParam("user_id", id)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }

    @Step("Getting student information with studentId: {0}")
    public ValidatableResponse getuserById(int id) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 15205ae80891734995eabfbadcb20828670fb637b77f8326e3e9ad0fe155ad75")

                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_USER_BY_ID)
                .then();
    }

}
