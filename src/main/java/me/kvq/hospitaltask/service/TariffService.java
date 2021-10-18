package me.kvq.hospitaltask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dao.TariffDao;
import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.exception.NotFoundException;
import me.kvq.hospitaltask.mapper.TariffMapper;
import me.kvq.hospitaltask.model.Tariff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffService {
    private final TariffMapper tariffMapper;
    private final TariffDao tariffDao;

    public TariffDto updateTariff(TariffDto tariffDto) {
        Tariff tariff = tariffMapper.dtoToEntity(tariffDto);
        Tariff result = tariffDao.save(tariff);
        return tariffMapper.entityToDto(result);
    }

    public void deleteTariff(String name) {
        if (!tariffDao.existsById(name)) {
            throw new NotFoundException("No tariff found by that id");
        }
        tariffDao.deleteById(name);
    }

    public List<TariffDto> getAllTariffs() {
        List<Tariff> tariffList = tariffDao.findAll();
        return tariffMapper.entityListToDtoList(tariffList);
    }

}
