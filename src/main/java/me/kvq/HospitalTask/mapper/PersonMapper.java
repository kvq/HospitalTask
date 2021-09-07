package me.kvq.HospitalTask.mapper;

import me.kvq.HospitalTask.model.Person;
import me.kvq.HospitalTask.dto.PersonDto;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PersonMapper<T extends Person, D extends PersonDto> {

    public abstract D entityToDto(T t);

    public abstract T dtoToEntity(D d);

    public List<D> entityListToDTOList(List<T> list) {
        return list.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<T> dtoListToEntityList(List<D> list) {
        return list.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
