package me.kvq.hospitaltask.controller;

import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.security.SecurityUserService;
import me.kvq.hospitaltask.service.TariffService;
import me.kvq.hospitaltask.testData.TestMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static me.kvq.hospitaltask.testData.TestDataGenerator.validTariffDto;
import static me.kvq.hospitaltask.testData.TestDataGenerator.validTariffJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TariffController.class)
class TariffControllerTest {
    @MockBean
    TariffService service;
    @MockBean
    SecurityUserService securityUserService;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "UPDATE_TARIFF")
    @DisplayName("Update tariff, expects same fields back")
    void updateTest() throws Exception {
        TariffDto expected = validTariffDto();
        String json = validTariffJson();
        when(service.updateTariff(any(TariffDto.class))).thenReturn(expected);
        mockMvc.perform(post("/tariff/update")
                        .content(validTariffJson()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(TestMatchers.matchTariff("$", expected));
    }

    @Test
    @WithMockUser(authorities = "DELETE_TARIFF")
    @DisplayName("Delete tariff by id, expects ok status")
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/tariff/delete/test"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "SEE_ALL_TARIFFS")
    @DisplayName("Gets list of all tariffs, checks json fields")
    void list() throws Exception {
        TariffDto expected = validTariffDto();
        when(service.getAllTariffs()).thenReturn(Arrays.asList(expected));
        mockMvc.perform(get("/tariff/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(TestMatchers.matchTariff("$[0]", expected));
    }

}