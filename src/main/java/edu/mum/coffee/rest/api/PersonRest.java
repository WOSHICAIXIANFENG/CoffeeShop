package edu.mum.coffee.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@RestController
public class PersonRest {
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value="/person", method=RequestMethod.GET)
	public ResponseEntity<List<Person>> getAllPerson() {
		List<Person> persons = personService.findAll();
		if (persons.isEmpty()) {
			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        System.out.println("Fetching person with id " + id);
        Person person = personService.findById(id);
        if (person == null) {
            System.out.println("Person with id " + id + " not found");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }
	
	@RequestMapping(value="/person", method=RequestMethod.POST)
	public ResponseEntity<Void> createPerson(@RequestBody Person person, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Person " + person.getFirstName() + person.getLastName());
		if (personService.isPersonExist(person)) {
			System.out.println("A Person with name = " + (person.getFirstName() + person.getLastName()) + " , already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		personService.savePerson(person);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/person/{id}").buildAndExpand(person.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/person/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Person> updatePerson(@PathVariable("id") long id, @RequestBody Person person) {
		System.out.println("Updating Person " + id);
		Person currentPerson = personService.findById(id);
		if (currentPerson == null) {
			System.out.println("Person with id " + id + " not found");
			return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
		}
		currentPerson.setAddress(person.getAddress());
		currentPerson.setEmail(person.getEmail());
		currentPerson.setFirstName(person.getFirstName());
		currentPerson.setLastName(person.getLastName());
		
		personService.savePerson(currentPerson);
		return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/person/{id}")
	public ResponseEntity<Person> deletePerson(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Person with id " + id);
		Person person = personService.findById(id);
		if (person == null) {
			System.out.println("Unable to delete. Person with id " + id + " not found");
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		personService.removePerson(person);
		return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
	}
}
