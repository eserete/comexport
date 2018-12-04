package com.example.comexport.accountant;

import com.example.comexport.ComexportApplication;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(
        classes = ComexportApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountantSteps {

    @Autowired
    private MockMvc mockMvc;

    private JSONObject requestBody;

    private ResultActions resultActions;

    @Given("the client has a valid registry")
    public void theClientHasAValidRegistry() throws JSONException {
        requestBody = new JSONObject();
        requestBody.put("contaContabil", 1111001);
        requestBody.put("data", 20170130);
        requestBody.put("valor", 25000.15);
    }


    @When("^the client attempt to save an accountant entry$")
    public void theClientAttemptToSaveAnAccountantEntry() throws Throwable {
        resultActions = mockMvc.perform(post("/lancamentos-contabeis")
                .content(String.valueOf(requestBody))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Then("^the system return a uuid of the accountant entry$")
    public void theSystemReturnAUuidOfTheAccountantEntry() throws Throwable {
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject response = new JSONObject(responseContent);

        Assert.assertNotNull(UUID.fromString(response.get("id").toString()));
    }

    @And("^the system return status (\\d+)$")
    public void theSystemReturnStatusCREATED(int status) throws Throwable {
        resultActions.andExpect(status().is(status));
    }

    @Given("^the client has a invalid registry$")
    public void theClientHasAInvalidRegistry() throws Throwable {
        requestBody = new JSONObject();
        requestBody.put("contaContabil", 1111001);
        requestBody.put("data", 20170130);
    }
}
