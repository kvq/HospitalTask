package me.kvq.hospitaltask.mapper;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.dto.OffWorkDto;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.model.OffWork;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OffWorkMapper {
    private final DoctorMapper doctorMapper;

    public OffWorkDto entityToDto(OffWork offWork) {
        DoctorDto doctorDto = doctorMapper.entityToDto(offWork.getDoctor());
        return OffWorkDto.builder()
                .dateFrom(offWork.getDateFrom())
                .dateUntil(offWork.getDateUntil())
                .doctor(doctorDto)
                .reason(offWork.getReason())
                .build();
    }

    public OffWork dtoToEntity(OffWorkDto offWorkDto) {
        Doctor doctor = doctorMapper.dtoToEntity(offWorkDto.getDoctor());
        return OffWork.builder()
                .dateFrom(offWorkDto.getDateFrom())
                .dateUntil(offWorkDto.getDateUntil())
                .doctor(doctor)
                .reason(offWorkDto.getReason())
                .build();
    }

    public List<OffWorkDto> entityListToDtoList(List<OffWork> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
