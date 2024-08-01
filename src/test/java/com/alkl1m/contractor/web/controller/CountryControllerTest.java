package com.alkl1m.contractor.web.controller;

import com.alkl1m.contractor.JwtUtil;
import com.alkl1m.contractor.TestBeans;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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
        List<String> roles = Arrays.asList("SUPERUSER");
        String jwt = JwtUtil.generateJwt("superuser", roles);
        mockMvc.perform(MockMvcRequestBuilders.get("/country/all")
                        .cookie(new Cookie("jwt", jwt)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.length()").value(254),
                        jsonPath("$[0].id").value("ABH"),
                        jsonPath("$[0].name").value("Абхазия")
                );
    }

    @Test
    void testGetCountryById_withValidPayload_returnsValidData() throws Exception {
        List<String> roles = Arrays.asList("SUPERUSER");
        String jwt = JwtUtil.generateJwt("superuser", roles);
        mockMvc.perform(MockMvcRequestBuilders.get("/country/{id}", "ABH")
                        .cookie(new Cookie("jwt", jwt)))
                .andExpectAll(
                        status().isOk(),
                        content().json(
                                """
                                        {
                                          "id": "ABH",
                                          "name": "Абхазия"
                                        }
                                        """
                        )
                );
    }

    @Test
    void testGetCountryById_withInvalidId_returnsError() throws Exception {
        List<String> roles = Arrays.asList("SUPERUSER");
        String jwt = JwtUtil.generateJwt("superuser", roles);
        mockMvc.perform(MockMvcRequestBuilders.get("/country/{id}", "AAA")
                        .cookie(new Cookie("jwt", jwt)))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {"message":"Country with id AAA not found!","errors":null}
                                """
                        )
                );
    }

    @Test
    void testSaveCountry_withValidPayload_returnsValidData() throws Exception {
        List<String> roles = Arrays.asList("SUPERUSER");
        String jwt = JwtUtil.generateJwt("superuser", roles);
        mockMvc.perform(MockMvcRequestBuilders.put("/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("jwt", jwt))
                        .content("""
                                {
                                  "id": "ABH",
                                  "name": "testCountry"
                                }
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {
                                  "id": "ABH",
                                  "name": "testCountry"
                                }
                                """)
                );
    }

    @Test
    void testSaveCountry_withInvalidPayload_returnsErrorMessage() throws Exception {
        List<String> roles = Arrays.asList("SUPERUSER");
        String jwt = JwtUtil.generateJwt("superuser", roles);
        mockMvc.perform(MockMvcRequestBuilders.put("/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("jwt", jwt))
                        .content("""
                                {
                                  "id": null,
                                  "name": null
                                }
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {
                                  "message": "Validation failed.",
                                  "errors": {
                                    "name": "Name cannot be null",
                                    "id": "ID cannot be null"
                                  }
                                }
                                """)
                );
    }

    @Test
    void testDeleteCountry_withValidPayload_returnsValidStatus() throws Exception {
        List<String> roles = Arrays.asList("SUPERUSER");
        String jwt = JwtUtil.generateJwt("superuser", roles);
        mockMvc.perform(MockMvcRequestBuilders.delete("/country/delete/{id}", "ABH")
                        .cookie(new Cookie("jwt", jwt)))
                .andExpectAll(
                        status().isOk()
                );
    }

}
