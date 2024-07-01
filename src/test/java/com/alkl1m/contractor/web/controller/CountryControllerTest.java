package com.alkl1m.contractor.web.controller;

import com.alkl1m.contractor.TestBeans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBeans.class)
class CountryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetAllCountries_withValidPayload_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/country/all"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(254),
                        jsonPath("$[0].id").value("ABH"),
                        jsonPath("$[0].name").value("Абхазия"),
                        jsonPath("$[0].active").value(true)
                );
    }

    @Test
    void testGetCountryById_withValidPayload_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/country/{id}", "ABH"))
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                """
                                        {"id":"ABH","name":"Абхазия","active":true}
                                        """
                        )
                );
    }

    @Test
    void testSaveCountry_withValidPayload_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": "ABH", "name": "testCountry"}
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {"id":"ABH","name":"testCountry"}
                                """)
                );
    }

    @Test
    void testSaveCountry_withInvalidPayload_returnsErrorMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": null, "name": null}
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {"message":"Validation failed.","errors":{"name":"Name cannot be null","id":"ID cannot be null"}}
                                """)
                );
    }

    @Test
    void testDeleteCountry_withValidPayload_returnsValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/country/delete/{id}", "ABH"))
                .andExpectAll(
                        status().isOk()
                );
    }

}
