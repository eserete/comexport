package com.example.comexport.accountant;

import com.example.comexport.ComexportApplication;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(
        classes = ComexportApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountantSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountantEntryRepository accountantEntryRepository;

    @Autowired
    private ObjectIdGenerators.UUIDGenerator uuidGenerator;

    @Autowired
    private MongoTemplate mongoTemplate;

    private JSONObject requestBody;

    private AccountantEntry accountantEntry;

    private ResultActions resultActions;

    private UUID uuid;
    private Integer accountNumber;
    private double expectedMin;
    private double expectedMax;
    private double expectedAverage;
    private double expectedSum;
    private long expectedCount;

    @After
    public void reset() {
        mongoTemplate.dropCollection(AccountantEntry.class);
        uuid = null;
        accountNumber = null;
        resultActions = null;
        accountantEntry = null;
    }


    @Given("the client has a valid registry")
    public void theClientHasAValidRegistry() throws JSONException {
        requestBody = new JSONObject();
        requestBody.put("contaContabil", 1111001);
        requestBody.put("data", 20170130);
        requestBody.put("valor", 25000.15);
    }


    @When("^the client attempts to save an accountant entry$")
    public void theClientAttemptToSaveAnAccountantEntry() throws Throwable {
        resultActions = mockMvc.perform(post("/lancamentos-contabeis")
                .content(String.valueOf(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Then("^the system return a uuid of the accountant entry$")
    public void theSystemReturnAUuidOfTheAccountantEntry() throws Throwable {
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        JSONObject response = new JSONObject(responseContent);
        Assert.assertNotNull(UUID.fromString(response.get("id").toString()));
    }

    @And("^the system returns status (\\d+)$")
    public void theSystemReturnStatusCREATED(int status) throws Throwable {
        resultActions.andExpect(status().is(status));
    }

    @Given("^the client has a invalid registry$")
    public void theClientHasAInvalidRegistry() throws Throwable {
        requestBody = new JSONObject();
        requestBody.put("contaContabil", 1111001);
        requestBody.put("data", 20170130);
    }

    @Given("^the client has a existing registry uuid$")
    public void theClientHasAExistingRegistryUuid() {
        uuid = accountantEntry.getId();
    }

    @When("^the client attempts to find the accountant entry$")
    public void theClientAttemptToFindTheAccountantEntry() throws Exception {
        resultActions = mockMvc.perform(get("/lancamentos-contabeis/" + uuid))
                .andDo(print());
    }

    @And("^the system returns the expected accountant entry$")
    public void theSystemReturnTheExpectedAccountantEntry() throws Throwable {
        AccountantEntry accountantEntry = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                AccountantEntry.class);
        Assert.assertEquals(accountantEntry.getAccountNumber(), this.accountantEntry.getAccountNumber());
        Assert.assertEquals(accountantEntry.getAmount(), this.accountantEntry.getAmount(), 0.0);
        Assert.assertEquals(accountantEntry.getEntryDate(), this.accountantEntry.getEntryDate());
    }

    @And("^the system has at least one accountant entry registered$")
    public void theSystemHasAnAccountantEntryRegistered() {
        accountantEntry = new AccountantEntry();
        accountantEntry.setAccountNumber(12345);
        accountantEntry.setAmount(123.23);
        accountantEntry.setEntryDate(new Date());
        accountantEntry.setId(uuidGenerator.generateId(accountantEntry));
        accountantEntryRepository.save(accountantEntry);

        AccountantEntry accountantEntry2 = new AccountantEntry();
        accountantEntry2.setAccountNumber(12345);
        accountantEntry2.setAmount(22.5);
        accountantEntry2.setEntryDate(new Date());
        accountantEntry2.setId(uuidGenerator.generateId(accountantEntry2));
        accountantEntryRepository.save(accountantEntry2);

        AccountantEntry accountantEntry3 = new AccountantEntry();
        accountantEntry3.setAccountNumber(34567);
        accountantEntry3.setAmount(435.67);
        accountantEntry3.setEntryDate(new Date());
        accountantEntry3.setId(uuidGenerator.generateId(accountantEntry3));
        accountantEntryRepository.save(accountantEntry3);

        AccountantEntry accountantEntry4 = new AccountantEntry();
        accountantEntry4.setAccountNumber(34567);
        accountantEntry4.setAmount(999.0);
        accountantEntry4.setEntryDate(new Date());
        accountantEntry4.setId(uuidGenerator.generateId(accountantEntry3));
        accountantEntryRepository.save(accountantEntry4);
    }

    @Given("^the client has a not registered accountant uuid$")
    public void theClientHasANotRegisteredAccountantUuid() {
        uuid = UUID.randomUUID();
    }

    @And("^the system returns empty body$")
    public void theSystemReturnEmptyBody() throws UnsupportedEncodingException {
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        Assert.assertTrue(StringUtils.isEmpty(responseBody));
    }

    @And("^the client has a existing account number$")
    public void theClientHasAExistingAccountNumber() {
        accountNumber = accountantEntry.getAccountNumber();
    }

    @When("^the client attempts to find accountant entries by account number$")
    public void theClientAttemptToFindAccountantEntriesByAccountNumber() throws Throwable {
        resultActions = mockMvc.perform(get("/lancamentos-contabeis?contaContabil=" + accountNumber))
                .andDo(print());
    }

    @And("^the system returns the expected accountant entries$")
    public void theSystemReturnsTheExpectedAccountantEntries() throws Throwable {
        Set<AccountantEntry> response = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                new TypeReference<Set<AccountantEntry>>() {});
        Assert.assertTrue(response
                .stream()
                .allMatch(entry -> entry.getAccountNumber().equals(accountNumber)));
    }

    @When("^the client attempt to get account stats$")
    public void theClientAttemptToGetAccountStats() throws Throwable {
        resultActions = mockMvc.perform(get("/lancamentos-contabeis/_stats")
                .content(String.valueOf(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @And("^the system returns expected stats numbers$")
    public void theSystemReturnsExpectedStatsNumbers() throws Throwable {
        Stats response = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                Stats.class);
        Assert.assertEquals(expectedCount, response.getCount());
        Assert.assertEquals(expectedSum, response.getSum(), 0.0);
        Assert.assertEquals(expectedAverage, response.getAverage(), 0.0);
        Assert.assertEquals(expectedMax, response.getMax(), 0.0);
        Assert.assertEquals(expectedMin, response.getMin(), 0.0);
    }

    @Given("^the system has a list of accountant entry register$")
    public void theSystemHasAListOfAccountantEntryRegister() {
        List<AccountantEntry> accountantEntries = new ArrayList<>();
        AccountantEntry accountantEntry = new AccountantEntry();
        accountantEntry.setAccountNumber(12345);
        accountantEntry.setAmount(123.23);
        accountantEntry.setEntryDate(new Date());
        accountantEntry.setId(uuidGenerator.generateId(accountantEntry));
        accountantEntries.add(accountantEntry);

        AccountantEntry accountantEntry2 = new AccountantEntry();
        accountantEntry2.setAccountNumber(12345);
        accountantEntry2.setAmount(22.5);
        accountantEntry2.setEntryDate(new Date());
        accountantEntry2.setId(uuidGenerator.generateId(accountantEntry2));
        accountantEntries.add(accountantEntry2);

        AccountantEntry accountantEntry3 = new AccountantEntry();
        accountantEntry3.setAccountNumber(34567);
        accountantEntry3.setAmount(435.67);
        accountantEntry3.setEntryDate(new Date());
        accountantEntry3.setId(uuidGenerator.generateId(accountantEntry3));
        accountantEntries.add(accountantEntry3);

        AccountantEntry accountantEntry4 = new AccountantEntry();
        accountantEntry4.setAccountNumber(34567);
        accountantEntry4.setAmount(999.0);
        accountantEntry4.setEntryDate(new Date());
        accountantEntry4.setId(uuidGenerator.generateId(accountantEntry4));
        accountantEntries.add(accountantEntry4);

        accountantEntryRepository.saveAll(accountantEntries);

        Supplier<DoubleStream> amountSupplier = () -> accountantEntries.stream().mapToDouble(AccountantEntry::getAmount);
        expectedMin = amountSupplier.get().min().orElse(0.0);
        expectedMax = amountSupplier.get().max().orElse(0.0);
        expectedAverage = amountSupplier.get().average().orElse(0.0);
        expectedSum = amountSupplier.get().sum();
        expectedCount = amountSupplier.get().count();
    }

    @Given("^the client has a specific account number to search$")
    public void theClientHasASpecificAccountNumberToSearch() {
        accountNumber = 12345;
    }

    @And("^the system has a list of accountant entry register with account number$")
    public void theSystemHasAListOfAccountantEntryRegisterWithAccountNumber() {
        List<AccountantEntry> accountantEntries = new ArrayList<>();
        AccountantEntry accountantEntry = new AccountantEntry();
        accountantEntry.setAccountNumber(accountNumber);
        accountantEntry.setAmount(123.23);
        accountantEntry.setEntryDate(new Date());
        accountantEntry.setId(uuidGenerator.generateId(accountantEntry));
        accountantEntries.add(accountantEntry);

        AccountantEntry accountantEntry2 = new AccountantEntry();
        accountantEntry2.setAccountNumber(accountNumber);
        accountantEntry2.setAmount(22.5);
        accountantEntry2.setEntryDate(new Date());
        accountantEntry2.setId(uuidGenerator.generateId(accountantEntry2));
        accountantEntries.add(accountantEntry2);

        AccountantEntry accountantEntry3 = new AccountantEntry();
        accountantEntry3.setAccountNumber(accountNumber);
        accountantEntry3.setAmount(435.67);
        accountantEntry3.setEntryDate(new Date());
        accountantEntry3.setId(uuidGenerator.generateId(accountantEntry3));
        accountantEntries.add(accountantEntry3);

        AccountantEntry accountantEntry4 = new AccountantEntry();
        accountantEntry4.setAccountNumber(34567);
        accountantEntry4.setAmount(999.0);
        accountantEntry4.setEntryDate(new Date());
        accountantEntry4.setId(uuidGenerator.generateId(accountantEntry4));
        accountantEntries.add(accountantEntry4);

        accountantEntryRepository.saveAll(accountantEntries);

        Supplier<DoubleStream> amountSupplier = () -> accountantEntries
                .stream()
                .filter(entry -> entry.getAccountNumber().equals(accountNumber))
                .mapToDouble(AccountantEntry::getAmount);
        expectedMin = amountSupplier.get().min().orElse(0.0);
        expectedMax = amountSupplier.get().max().orElse(0.0);
        expectedAverage = amountSupplier.get().average().orElse(0.0);
        expectedSum = amountSupplier.get().sum();
        expectedCount = amountSupplier.get().count();
    }

    @When("^the client attempt to get account stats by account number$")
    public void theClientAttemptToGetAccountStatsByAccountNumber() throws Throwable {
        resultActions = mockMvc.perform(get("/lancamentos-contabeis/_stats?contaContabil=" + accountNumber)
                .content(String.valueOf(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
