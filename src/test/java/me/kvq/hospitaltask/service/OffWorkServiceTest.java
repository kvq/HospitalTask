package me.kvq.hospitaltask.service;

import me.kvq.hospitaltask.dao.OffWorkDao;
import me.kvq.hospitaltask.dto.OffWorkDto;
import me.kvq.hospitaltask.mapper.OffWorkMapper;
import me.kvq.hospitaltask.model.OffWork;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static me.kvq.hospitaltask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class OffWorkServiceTest {
    @MockBean
    OffWorkDao offWorkDao;
    @MockBean
    OffWorkMapper offWorkMapper;
    @Autowired
    OffWorkService offWorkService;

    @Test
    @DisplayName("Checks if doctor has offwork at specific date. Expected true")
    void isAvailableAtDateTest() {
        long doctorId = 1;
        LocalDate date = LocalDate.now();
        when(offWorkDao.isAvailableAtDate(date, doctorId)).thenReturn(true);
        assertTrue(offWorkService.isAvailableAtDate(date, doctorId));
    }

    @Test
    @DisplayName("Gets list of all active offworks, expects valid list")
    void getAllActiveOffWorks() {
        long doctorId = 1;
        List<OffWork> offWorkList = validOffWorkList();
        List<OffWorkDto> expectedList = validOffWorkDtoList();
        when(offWorkDao.finaAllAfterDate(any(LocalDate.class), eq(doctorId))).thenReturn(offWorkList);
        when(offWorkMapper.entityListToDtoList(offWorkList)).thenReturn(expectedList);
        List<OffWorkDto> actualList = offWorkService.getAllActiveOffWorks(doctorId);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Adds new offwork, expected to return same dto")
    void addNewOffWork() {
        OffWork offWork = validOffWorkFuture();
        OffWorkDto expectedDto = validOffWorkDtoFuture();
        when(offWorkMapper.dtoToEntity(expectedDto)).thenReturn(offWork);
        when(offWorkDao.save(offWork)).thenReturn(offWork);
        when(offWorkMapper.entityToDto(offWork)).thenReturn(expectedDto);
        OffWorkDto actualDto = offWorkService.updateOffWork(expectedDto);
        assertEquals(expectedDto, actualDto);
    }

}