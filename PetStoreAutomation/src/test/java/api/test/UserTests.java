package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;


public class UserTests {
	
	Faker faker ;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker =new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		userPayload.setUsername(faker.name().username());
		userPayload.setPassword(faker.internet().password());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		//logs
		logger = LogManager.getLogger(this.getClass());

	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("******************Creating User****************");
		Response response = UserEndPoints.createUser(userPayload);
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******************User is created****************");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("******************Reading User info ****************");
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
				response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("****************** User info is Displayed****************");
	}
	@Test(priority=3)
	public void testUpdateUser() {
		logger.info("******************Updating User info ****************");
	  //update data using payload by below 
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		Response responseafterUpdate = UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		responseafterUpdate.then().log().all();
		Assert.assertEquals(responseafterUpdate.getStatusCode(), 200);
		logger.info("*****************User is Updated****************");
	}	
	@Test(priority=4)
	public void testdeleteUser() {
		logger.info("******************Deleting User****************");
		Response responseafterdelete = UserEndPoints.deleteUser(this.userPayload.getUsername());
		responseafterdelete.then().log().all();
		Assert.assertEquals(responseafterdelete.getStatusCode(), 200);
		
		logger.info("******************User Deleted****************");
	}
		
}
