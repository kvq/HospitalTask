package me.kvq.HospitalTask.service;

import me.kvq.HospitalTask.model.Person;
import me.kvq.HospitalTask.dto.PersonDto;
import me.kvq.HospitalTask.mapper.PersonMapper;
import me.kvq.HospitalTask.utils.PhoneNumberUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HospitalService<T extends Person, D extends PersonDto> {

    private JpaRepository<T,Long> dao;
    private PersonMapper<T, D> mapper;

    public HospitalService(JpaRepository<T,Long> dao, PersonMapper<T, D> mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    public void add(D person) {
        person.setId(0);
        person.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(person.getPhoneNumber()));
        dao.save(mapper.dtoToEntity(person));
    }

    public void update(long id, D person) {
        if (!dao.existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        person.setId(id);
        person.setPhoneNumber(PhoneNumberUtils.fixPhoneNumber(person.getPhoneNumber()));
        dao.save(mapper.dtoToEntity(person));
    }

    public boolean delete(long id) {
        if (!dao.existsById(id)) {
            throw new NoSuchElementException("User does not exists");
        }
        dao.deleteById(id);
        return true;
    }

    public List<D> getList() {
        return mapper.entityListToDTOList(dao.findAll());
    }

    public D get(long id){
        T entity = dao.getById(id);
        if (entity == null) {
            throw new NoSuchElementException("User does not exists");
        }
        return mapper.entityToDto(entity);
    }
}
