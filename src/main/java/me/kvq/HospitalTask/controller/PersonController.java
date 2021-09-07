package me.kvq.HospitalTask.controller;

import me.kvq.HospitalTask.service.HospitalService;
import me.kvq.HospitalTask.dto.PersonDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public abstract class PersonController<T extends PersonDto> {

    public HospitalService<?, T> service;

    public PersonController(HospitalService<?, T> service) {
        this.service = service;
    }

    @GetMapping("/list")
    public List<T> list() {
        return service.getList();
    }

    @PostMapping("/add")
    public String add(@RequestBody T person) {
        service.add(person);
        return "success";
    }

    @PatchMapping("/edit/{id}")
    public String patch(@PathVariable long id, @RequestBody T person) {

        service.update(id, person);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        service.delete(id);
        return "success";
    }

}
