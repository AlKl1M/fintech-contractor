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
class IndustryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetAllIndustries_withValidPayload_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/industry/all"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(105),
                        jsonPath("$[0].id").exists(),
                        jsonPath("$[0].name").value("Авиастроение"),
                        jsonPath("$[0].isActive").value(true)
                );
    }

    @Test
    void testGetIndustryById_withValidPayload_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/industry/{id}", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                """
                                        {"id":1,"name":"Авиастроение","isActive":true}
                                        """
                        )
                );
    }

    @Test
    void testGetIndustryById_withInvalidPayload_returnsErrorMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/industry/{id}", 100000))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {"message":"Industry with id 100000 not found!","errors":null}
                                """)
                );
    }

    @Test
    void testSaveIndustry_withValidPayload_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/industry/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": 10, "name": "testIndustry"}
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {"id":10,"name":"testIndustry"}
                                """)
                );
    }

    @Test
    void testSaveIndustry_withInvalidPayload_returnsErrorMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/industry/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": null, "name": null}
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {"message":"Validation failed.","errors":{"name":"Name cannot be null"}}
                                """)
                );
    }

    @Test
    void testDeleteIndustry_withValidData_returnsValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/industry/delete/{id}", 10))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void testDeleteIndustry_withInvalidPayload_returnsErrorMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/industry/delete/{id}", 100000))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {"message":"Industry with id 100000 not found","errors":null}
                                """)
                );
    }

}
