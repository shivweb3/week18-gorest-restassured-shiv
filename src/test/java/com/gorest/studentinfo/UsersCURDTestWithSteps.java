package com.gorest.studentinfo;


import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

/**
 * Created by Jay
 */
@RunWith(SerenityRunner.class)
public class UsersCURDTestWithSteps extends TestBase {

    static String name = "PrimUser" + TestUtils.getRandomValue();
    //static String lastName = "PrimeUser" + TestUtils.getRandomValue();
    static String email =TestUtils.getRandomValue()+"xyz@gmail.com";
    static String status = "active";
    static String gender = "male";

    static int userId;


    @Steps
    UsersSteps usersSteps;

    @Title("This will create a new User")
    @Test
    public void test001() {

        ValidatableResponse response = usersSteps.createUsers( name,email,gender, status);
        response.log().all().statusCode(201);
    }
      //  response.extract().path("v2/users");
       // response.log().all().statusCode(201);
        //String jsonString = response.extract().asString();
        //token = JsonPath.from(jsonString).get("token");


    @Title("Verify if the User was added to the application")
    @Test
    public void test002() {
        HashMap<String, Object> studentMap = usersSteps.getUserInfoByemail(name);
        Assert.assertThat(studentMap, hasValue(name));
        userId = (int) studentMap.get("id");
    }

    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";

        usersSteps.updateUser(email, name, status, userId, gender).log().all().statusCode(200);
        //ValidatableResponse  response =usersSteps.updateUser(userId, name,email,gender, status);
       //.log().all().statusCode(200);
        HashMap<String, Object> userMap = usersSteps.getUserInfoByemail(name);
        Assert.assertThat(userMap, hasValue(name));
    }

    @Title("Delete the student and verify if the student is deleted!")
    @Test
    public void test004() {
        usersSteps.deleteuser(userId).statusCode(204);
        usersSteps.getuserById(userId).statusCode(404);
    }
}
