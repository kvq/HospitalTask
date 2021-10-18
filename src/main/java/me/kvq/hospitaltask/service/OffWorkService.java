package me.kvq.hospitaltask.service;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dao.OffWorkDao;
import me.kvq.hospitaltask.dto.OffWorkDto;
import me.kvq.hospitaltask.mapper.OffWorkMapper;
import me.kvq.hospitaltask.model.OffWork;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OffWorkService {
    private final OffWorkDao dao;
    private final OffWorkMapper mapper;

    public boolean isAvailableAtDate(LocalDate date, long doctorId) {
        return dao.isAvailableAtDate(date, doctorId);
    }

    public List<OffWorkDto> getAllActiveOffWorks(long doctorId) {
        LocalDate currentDate = LocalDate.now();
        List<OffWork> offWorkList = dao.finaAllAfterDate(currentDate, doctorId);
        return mapper.entityListToDtoList(offWorkList);
    }

    public OffWorkDto updateOffWork(OffWorkDto dto) {
        OffWork offWork = mapper.dtoToEntity(dto);
        OffWork result = dao.save(offWork);
        return mapper.entityToDto(result);
    }

}
