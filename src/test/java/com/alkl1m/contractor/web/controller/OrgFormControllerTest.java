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

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBeans.class)
class OrgFormControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetAllOrgForms() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orgform/all"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(206),
                        jsonPath("$[0].id").exists(),
                        jsonPath("$[0].name").value("-"),
                        jsonPath("$[0].active").value(true)
                );
    }

    @Test
    void testGetOrgFormById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orgform/{id}", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                """
                                {"id":1,"name":"-","active":true}
                                """
                        )
                );
    }

    @Test
    void testSaveOrgForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/orgform/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"id": 10, "name": "testOrgForm"}
                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                        {"id":10,"name":"testOrgForm"}
                        """)
                );
    }

    @Test
    void testSaveOrgForm_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/orgform/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {"id": null, "name": null}
                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                        {"message":"Validation failed.","errors":{"name":"Name cannot be null","id":"Id cannot be null"}}
                        """)
                );
    }

    @Test
    void testDeleteOrgForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/orgform/delete/{id}", 10))
                .andExpectAll(
                        status().isOk()
                );
    }

}
