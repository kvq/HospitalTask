package me.kvq.HospitalTask.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import me.kvq.HospitalTask.logic.HospitalService;
import me.kvq.HospitalTask.person.PersonDTO;


public abstract class PersonRESTController<T extends PersonDTO>{
  
  protected HospitalService<?,T> service;
  
  protected PersonRESTController(HospitalService<?,T> service){
    this.service = service;
  }
  
  @GetMapping("/list")
  public List<T> list() {
    return service.getList(); 
  }
  
  @PostMapping("/add")
  public String add(@RequestBody T doc) {
    service.add(doc);
    return "success";
  }
  
  @PatchMapping("/edit/{id}")
  public String patch(@PathVariable long id,@RequestBody T doc) {
  
    service.update(id,doc);
    return "success";
  }
  
  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable long id) {
    service.delete(id);
    return "success";
  }

}
