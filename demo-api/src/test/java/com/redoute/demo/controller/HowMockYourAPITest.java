package com.redoute.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.redoute.demo.config.LiquibaseConfiguration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class) // Signify we test with spring
@WebMvcTest(controllers = CustomerRestController.class) // configure automatically the test (init conf spring)
@AutoConfigureDataJpa // configure jpa
@AutoConfigureTestDatabase // configure a database automatically
@ContextConfiguration(classes={LiquibaseConfiguration.class})
// @AutoConfigureWireMock(stubs="classpath:/stubs")
public class HowMockYourAPITest  {
 
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8089); // No-args constructor defaults to port 8080
	

	@Autowired
	private WebApplicationContext context;

	// allow to make request for our controller
	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				//.apply(springSecurity()) 
				.build();
	}

	@Test
	public void canGetACustomer() throws Exception {
		// prepare
		stubFor((get(urlEqualTo("/customer")).withHeader("Accept", equalTo("text/xml")) 
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "text/xml")
						.withBody("<response>Some content</response>"))));

		// do
		mvc.perform(get("http://localhost:8089/customer").accept(MediaType.TEXT_XML))
		.andDo(print());;
		/*
		 given().
		    when().
		        get("http://localhost:8090/customer").
		    then().
		        assertThat().
		        statusCode(200).
		        and().
		        // test
		        assertThat().body("list", org.hamcrest.Matchers.equalTo("Empty"));
		 */

		/*
		 Result result = myHttpServiceCallingObject.doSomething();

		 assertTrue(result.wasSuccessful());

		 verify(postRequestedFor(urlMatching("/my/resource/[a-z0-9]+"))
				 .withRequestBody(matching(".*<message>1234</message>.*"))
				 .withHeader("Content-Type", notMatching("application/json")));
		 */
	}
}
