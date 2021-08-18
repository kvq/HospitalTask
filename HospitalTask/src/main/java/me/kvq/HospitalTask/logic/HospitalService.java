package me.kvq.HospitalTask.logic;

import java.util.List;
import java.util.NoSuchElementException;
import me.kvq.HospitalTask.person.Person;
import me.kvq.HospitalTask.person.PersonDAO;
import me.kvq.HospitalTask.person.PersonDTO;
import me.kvq.HospitalTask.person.PersonMapper;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;

public class HospitalService<T extends Person,D extends PersonDTO> {
  
  private PersonDAO<T> dao;
  private PersonMapper<T,D> mapper;
  
  public HospitalService(PersonDAO<T> dao, PersonMapper<T,D> mapper) {
    this.dao = dao;
    this.mapper = mapper;
  }
  
  public void add(D person) {
    person.setId(0);
    person.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(person.getPhoneNumber()));
    dao.save(mapper.dtoToEntity(person));
  }
  
  public void update(long id,D person) {
    if (!dao.isExists(id)) throw new NoSuchElementException("User does not exists");
    person.setId(id);
    person.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(person.getPhoneNumber()));
    dao.save(mapper.dtoToEntity(person));
  }
  
  public boolean delete(long id) {
    if (!dao.isExists(id)) throw new NoSuchElementException("User does not exists");
    dao.delete(id);
    return true;
  }
  
  public List<D> getList(){
    return mapper.entityListToDTOList(dao.getList());
  }

}
