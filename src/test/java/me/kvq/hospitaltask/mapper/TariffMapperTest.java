package me.kvq.hospitaltask.mapper;

import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.model.Tariff;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static me.kvq.hospitaltask.testData.TestDataGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TariffMapperTest {
    @Autowired
    TariffMapper mapper;

    @Test
    @DisplayName("Converts entity to dto then compares fields")
    void entityToDto() {
        Tariff expected = validTariff();
        TariffDto actual = mapper.entityToDto(expected);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @DisplayName("Converts dto to entity then compares fields")
    void dtoToEntity() {
        TariffDto expected = validTariffDto();
        Tariff actual = mapper.dtoToEntity(expected);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    @DisplayName("Converts entity list to dto list then compares fields for each in the list")
    void entityListToDtoList() {
        List<Tariff> expectedList = validTariffList();
        List<TariffDto> actualList = mapper.entityListToDtoList(expectedList);
        assertEquals(expectedList.size(), actualList.size());
        for (int index = 0; index < expectedList.size(); index++) {
            Tariff expected = expectedList.get(index);
            TariffDto actual = actualList.get(index);
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getPrice(), actual.getPrice());
        }
    }

}