package me.kvq.hospitaltask.service;

import me.kvq.hospitaltask.dao.TariffDao;
import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.mapper.TariffMapper;
import me.kvq.hospitaltask.model.Tariff;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static me.kvq.hospitaltask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class TariffServiceTest {
    @Autowired
    TariffService tariffService;
    @MockBean
    TariffMapper tariffMapper;
    @MockBean
    TariffDao tariffDao;

    @Test
    @DisplayName("Updates/Creates tariff, expected to return same dto")
    void updateTariffTest() {
        TariffDto expected = validTariffDto();
        Tariff tariff = validTariff();
        when(tariffMapper.dtoToEntity(expected)).thenReturn(tariff);
        when(tariffMapper.entityToDto(tariff)).thenReturn(expected);
        when(tariffDao.save(tariff)).thenReturn(tariff);
        TariffDto actual = tariffService.updateTariff(expected);
        assertEquals(expected, actual);
        verify(tariffMapper, times(1)).dtoToEntity(expected);
        verify(tariffMapper, times(1)).entityToDto(tariff);
        verify(tariffDao, times(1)).save(tariff);
    }

    @Test
    @DisplayName("Deletion of existing tariff, no exceptions expected")
    void deleteTariffTest() {
        String tariffName = "Test";
        when(tariffDao.existsById(tariffName)).thenReturn(true);
        tariffService.deleteTariff(tariffName);
        verify(tariffDao, times(1)).existsById(tariffName);
    }

    @Test
    @DisplayName("Gets list of all tariffs")
    void getAllTariffsTest() {
        List<Tariff> tariffList = validTariffList();
        List<TariffDto> expectedList = validTariffDtoList();
        when(tariffDao.findAll()).thenReturn(tariffList);
        when(tariffMapper.entityListToDtoList(tariffList)).thenReturn(expectedList);
        List<TariffDto> actualList = tariffService.getAllTariffs();
        assertEquals(expectedList, actualList);
        verify(tariffDao, times(1)).findAll();
        verify(tariffMapper, times(1)).entityListToDtoList(tariffList);
    }

}