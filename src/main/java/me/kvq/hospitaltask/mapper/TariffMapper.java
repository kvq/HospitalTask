package me.kvq.hospitaltask.mapper;

import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.model.Tariff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TariffMapper {

    public TariffDto entityToDto(Tariff tariff) {
        return TariffDto.builder()
                .name(tariff.getName())
                .price(tariff.getPrice())
                .build();
    }

    public Tariff dtoToEntity(TariffDto tariffDto) {
        return Tariff.builder()
                .name(tariffDto.getName())
                .price(tariffDto.getPrice())
                .build();
    }

    public List<TariffDto> entityListToDtoList(List<Tariff> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
