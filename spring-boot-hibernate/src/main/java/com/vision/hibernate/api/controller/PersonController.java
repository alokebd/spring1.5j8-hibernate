package com.vision.hibernate.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vision.hibernate.api.dao.PersonDao;
import com.vision.hibernate.api.model.Person;

@RestController
@RequestMapping("/v1")
public class PersonController {

	@Autowired
	private PersonDao dao;

	@PostMapping("/persons")
	public String save(@RequestBody Person person) {
		dao.savePerson(person);
		return "success";
	}

	@GetMapping("/persons")
	public List<Person> getALlPersons() {
		return dao.getPersons();
	}
	
	@PutMapping("/persons/{id}")
	public String updateByUserId(@PathVariable ("id") int id,
			@RequestBody Person person) {
		Person dbPerson = dao.getPersonById(id);
		if (dbPerson == null) {
			return "not found";
		}
		dbPerson.setName(person.getName());
		dbPerson.setDob(person.getDob());
		int result = dao.updatePerson(dbPerson);
		return result + " person updated";
	}
	
	@DeleteMapping("/persons/{id}")
	public String removePersonById(@PathVariable ("id") int id) {
		int result = dao.deletePerson(id);
		return result + " person deleted";
	}

}
