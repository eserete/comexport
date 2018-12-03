package com.example.comexport.accountant;

import com.example.comexport.ComexportApplication;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComexportApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountantTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("contaContabil", 1111001);
        requestBody.put("data", 20170130);
        requestBody.put("value", 25000.15);

        JSONObject responseBody = new JSONObject();
        responseBody.put("id", "97c9e6e8-dedb-497a-bb94-7fe01bf415ed");

        mockMvc.perform(post("/lancamentos-contabeis")
                .content(requestBody.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
//        .andExpect(content().json(requestBody.toString()));
    }

}
