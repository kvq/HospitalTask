package me.kvq.hospitaltask.mapper;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dto.DoctorDto;
import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.model.Doctor;
import me.kvq.hospitaltask.model.Tariff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DoctorMapper {
    private final TariffMapper tariffMapper;

    public DoctorDto entityToDto(Doctor doctor) {
        TariffDto tariffDto = doctor.getTariff() == null ? null
                : tariffMapper.entityToDto(doctor.getTariff());
        return DoctorDto.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .patronymic(doctor.getPatronymic())
                .birthDate(doctor.getBirthDate())
                .phoneNumber(doctor.getPhoneNumber())
                .speciality(doctor.getSpeciality())
                .tariff(tariffDto)
                .build();
    }

    public Doctor dtoToEntity(DoctorDto doctorDto) {
        Tariff tariff = doctorDto.getTariff() == null ? null
                : tariffMapper.dtoToEntity(doctorDto.getTariff());
        return Doctor.builder()
                .id(doctorDto.getId())
                .firstName(doctorDto.getFirstName())
                .lastName(doctorDto.getLastName())
                .patronymic(doctorDto.getPatronymic())
                .birthDate(doctorDto.getBirthDate())
                .phoneNumber(doctorDto.getPhoneNumber())
                .speciality(doctorDto.getSpeciality())
                .tariff(tariff)
                .build();
    }

    public List<DoctorDto> entityListToDtoList(List<Doctor> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

}
