package me.kvq.HospitalTask.person;

import java.util.List;
import java.util.stream.Collectors;

public abstract class PersonMapper<T extends Person,D extends PersonDTO> {

  public abstract D entityToDto(T t);
  
  public abstract T dtoToEntity(D d);
  
  public List<D> entityListToDTOList(List<T> list) {
    return list.stream().map(this::entityToDto).collect(Collectors.toList());
  }
  
  public List<T> dtoListToEntityList(List<D> list) {
    return list.stream().map(this::dtoToEntity).collect(Collectors.toList());
  }
  
}
