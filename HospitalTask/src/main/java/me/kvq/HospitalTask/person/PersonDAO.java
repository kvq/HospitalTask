package me.kvq.HospitalTask.person;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import me.kvq.HospitalTask.repositories.DoctorRepo;

public abstract class PersonDAO<T extends Person> {

  protected JpaRepository<T, Long> repository;
  
  protected PersonDAO(JpaRepository<T, Long> repository) {
    this.repository = repository;
  }

  public Optional<T> get(long id){
    return Optional.ofNullable(repository.getById(id));
  }
  
  public void save(T person) {
    repository.save(person);
  }
  
  public List<T> getList(){
    return repository.findAll();
  }
  
  public boolean isExists(long id) {
    return repository.existsById(id);
  }
  
  public void delete(long id) {
    repository.deleteById(id);
  }

}
