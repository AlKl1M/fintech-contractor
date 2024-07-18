package com.alkl1m.contractor.web.controller;

import com.alkl1m.contractor.TestBeans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBeans.class)
class ContractorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/contractors.sql")
    void testGetContractorsByParameters_withOneParameter_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/contractor/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": "1"}
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {
                                  "contractors": {
                                    "totalElements": 1,
                                    "totalPages": 1,
                                    "pageable": {
                                      "pageNumber": 0,
                                      "pageSize": 10,
                                      "sort": {
                                        "empty": true,
                                        "sorted": false,
                                        "unsorted": true
                                      },
                                      "offset": 0,
                                      "paged": true,
                                      "unpaged": false
                                    },
                                    "first": true,
                                    "last": true,
                                    "size": 10,
                                    "content": [
                                      {
                                        "id": "1",
                                        "parentId": null,
                                        "name": "Contractor 1",
                                        "nameFull": "Full Name 1",
                                        "inn": "111111111",
                                        "ogrn": "111111111",
                                        "country": {
                                          "id": "ABH",
                                          "name": "Абхазия"
                                        },
                                        "industry": {
                                          "id": 1,
                                          "name": "Авиастроение"
                                        },
                                        "orgForm": {
                                          "id": 1,
                                          "name": "-"
                                        }
                                      }
                                    ],
                                    "number": 0,
                                    "sort": {
                                      "empty": true,
                                      "sorted": false,
                                      "unsorted": true
                                    },
                                    "numberOfElements": 1,
                                    "empty": false
                                  }
                                }
                                """)
                );
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testGetContractorsByParameters_withAllParameters_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/contractor/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "parentId": null,
                                  "name": "Contractor 1",
                                  "nameFull": "Full Name 1",
                                  "inn": "111111111",
                                  "ogrn": "111111111",
                                  "countryName": "Абхазия",
                                  "industry": {
                                    "id": 1,
                                    "name": "Авиастроение"
                                  },
                                  "orgFormName": "-"
                                }
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {
                                  "contractors": {
                                    "totalElements": 1,
                                    "totalPages": 1,
                                    "pageable": {
                                      "pageNumber": 0,
                                      "pageSize": 10,
                                      "sort": {
                                        "empty": true,
                                        "sorted": false,
                                        "unsorted": true
                                      },
                                      "offset": 0,
                                      "paged": true,
                                      "unpaged": false
                                    },
                                    "first": true,
                                    "last": true,
                                    "size": 10,
                                    "content": [
                                      {
                                        "id": "1",
                                        "parentId": null,
                                        "name": "Contractor 1",
                                        "nameFull": "Full Name 1",
                                        "inn": "111111111",
                                        "ogrn": "111111111",
                                        "country": {
                                          "id": "ABH",
                                          "name": "Абхазия"
                                        },
                                        "industry": {
                                          "id": 1,
                                          "name": "Авиастроение"
                                        },
                                        "orgForm": {
                                          "id": 1,
                                          "name": "-"
                                        }
                                      }
                                    ],
                                    "number": 0,
                                    "sort": {
                                      "empty": true,
                                      "sorted": false,
                                      "unsorted": true
                                    },
                                    "numberOfElements": 1,
                                    "empty": false
                                  }
                                }
                                """)
                );
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testGetContractorPageableByIdd_withOneParameter_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/contractor/crud/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"id": "1"}
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {
                                  "contractors": {
                                    "totalElements": 1,
                                    "totalPages": 1,
                                    "pageable": {
                                      "pageNumber": 0,
                                      "pageSize": 10,
                                      "sort": {
                                        "empty": true,
                                        "sorted": false,
                                        "unsorted": true
                                      },
                                      "offset": 0,
                                      "paged": true,
                                      "unpaged": false
                                    },
                                    "first": true,
                                    "last": true,
                                    "size": 10,
                                    "content": [
                                      {
                                        "id": "1",
                                        "parentId": null,
                                        "name": "Contractor 1",
                                        "nameFull": "Full Name 1",
                                        "inn": "111111111",
                                        "ogrn": "111111111",
                                        "country": {
                                          "id": "ABH",
                                          "name": "Абхазия"
                                        },
                                        "industry": {
                                          "id": 1,
                                          "name": "Авиастроение"
                                        },
                                        "orgForm": {
                                          "id": 1,
                                          "name": "-"
                                        }
                                      }
                                    ],
                                    "number": 0,
                                    "sort": {
                                      "empty": true,
                                      "sorted": false,
                                      "unsorted": true
                                    },
                                    "numberOfElements": 1,
                                    "empty": false
                                  }
                                }
                                """)
                );
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testGetContractorPageableById_withAllParameters_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/contractor/crud/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "parentId": null,
                                  "name": "Contractor 1",
                                  "nameFull": "Full Name 1",
                                  "inn": "111111111",
                                  "ogrn": "111111111",
                                  "countryName": "Абхазия",
                                  "industry": {
                                    "id": 1,
                                    "name": "Авиастроение"
                                  },
                                  "orgFormName": "-"
                                }
                                """))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {
                                  "contractors": {
                                    "totalElements": 1,
                                    "totalPages": 1,
                                    "pageable": {
                                      "pageNumber": 0,
                                      "pageSize": 10,
                                      "sort": {
                                        "empty": true,
                                        "sorted": false,
                                        "unsorted": true
                                      },
                                      "offset": 0,
                                      "paged": true,
                                      "unpaged": false
                                    },
                                    "first": true,
                                    "last": true,
                                    "size": 10,
                                    "content": [
                                      {
                                        "id": "1",
                                        "parentId": null,
                                        "name": "Contractor 1",
                                        "nameFull": "Full Name 1",
                                        "inn": "111111111",
                                        "ogrn": "111111111",
                                        "country": {
                                          "id": "ABH",
                                          "name": "Абхазия"
                                        },
                                        "industry": {
                                          "id": 1,
                                          "name": "Авиастроение"
                                        },
                                        "orgForm": {
                                          "id": 1,
                                          "name": "-"
                                        }
                                      }
                                    ],
                                    "number": 0,
                                    "sort": {
                                      "empty": true,
                                      "sorted": false,
                                      "unsorted": true
                                    },
                                    "numberOfElements": 1,
                                    "empty": false
                                  }
                                }
                                """)
                );
    }

    @Test
    void testSaveOrUpdateContractor_withValidPayload_returnsValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "parentId": null,
                                  "name": "Example Contractor",
                                  "nameFull": "Example Contractor Inc.",
                                  "inn": "1234567890",
                                  "ogrn": "9876543210",
                                  "country_id": "ABH",
                                  "industry_id": 1,
                                  "orgForm_id": 2
                                }
                                """))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void testSaveOrUpdateContractor_withNonExistingCountry_returnsErrorMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "parentId": null,
                                  "name": "Example Contractor",
                                  "nameFull": "Example Contractor Inc.",
                                  "inn": "1234567890",
                                  "ogrn": "9876543210",
                                  "country_id": "AAA",
                                  "industry_id": 1,
                                  "orgForm_id": 2
                                }
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                        {"message":"Country not found","errors":null}
                        """)
                );
    }

    @Test
    void testSaveOrUpdateContractor_withNonExistingIndustry_returnsErrorMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "parentId": null,
                                  "name": "Example Contractor",
                                  "nameFull": "Example Contractor Inc.",
                                  "inn": "1234567890",
                                  "ogrn": "9876543210",
                                  "country_id": "ABH",
                                  "industry_id": 1000000,
                                  "orgForm_id": 2
                                }
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                        {"message":"Industry not found","errors":null}
                        """)
                );
    }

    @Test
    void testSaveOrUpdateContractor_withNonExistingOrgForm_returnsErrorMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "1",
                                  "parentId": null,
                                  "name": "Example Contractor",
                                  "nameFull": "Example Contractor Inc.",
                                  "inn": "1234567890",
                                  "ogrn": "9876543210",
                                  "country_id": "ABH",
                                  "industry_id": 1,
                                  "orgForm_id": 100000
                                }
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                        {"message":"OrgForm not found","errors":null}
                        """)
                );
    }

    @Test
    void testSaveOrUpdateContractor_withInvalidPayload_returnsBadRequestMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/contractor/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": null,
                                  "parentId": null,
                                  "name": null,
                                  "nameFull": "Example Contractor Inc.",
                                  "inn": "1234567890",
                                  "ogrn": "9876543210",
                                  "country_id": null,
                                  "industry_id": null,
                                  "orgForm_id": null
                                }
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {
                                  "message": "Validation failed.",
                                  "errors": {
                                    "orgForm_id": "OrgForm ID cannot be null",
                                    "industry_id": "Industry ID cannot be null",
                                    "name": "ID cannot be null",
                                    "id": "ID cannot be null",
                                    "country_id": "Country ID cannot be null"
                                  }
                                }
                                """)
                );
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testGetContractorPageableById_withValidPayload_returnsValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contractor/{id}", 1))
                .andExpectAll(
                        status().isOk(),
                        content().json("""
                                {
                                  "id": "1",
                                  "parentId": null,
                                  "name": "Contractor 1",
                                  "nameFull": "Full Name 1",
                                  "inn": "111111111",
                                  "ogrn": "111111111",
                                  "country": {
                                    "id": "ABH",
                                    "name": "Абхазия"
                                  },
                                  "industry": {
                                    "id": 1,
                                    "name": "Авиастроение"
                                  },
                                  "orgForm": {
                                    "id": 1,
                                    "name": "-"
                                  }
                                }
                                """)
                );
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testDeleteContractorById_withValidId_returnsValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/contractor/delete/{id}", 1))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testChangeMainBorrower_withValidId_returnsValidStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/contractor/main-borrower")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                                {
                                  "contractorId": "1",
                                  "main": true
                                }
                                """))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testChangeMainBorrower_withNullData_returnsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/contractor/main-borrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "contractorId": null,
                                  "main": null
                                }
                                """))
                .andExpectAll(
                        status().isBadRequest(),
                        content().json("""
                                {
                                  "message": "Validation failed.",
                                  "errors": {
                                    "contractorId":"Contractor ID cannot be null"
                                  }
                                }
                                """)
                );
    }

}
