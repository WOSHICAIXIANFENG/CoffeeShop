package edu.mum.coffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.repository.PersonRepository;

@Service
@Transactional
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public boolean isPersonExist(Person person) {
		return personRepository.exists(person.getId());
	}
	
	public List<Person> findAll() {
		return personRepository.findAll();
	}
	
	public List<Person> findByEmail(String email) {
		return personRepository.findByEmail(email);
	}
	
	public Person findByEmailDistinct(String email) {
		//return personRepository.findByEmail(email);
		List<Person> persons = personRepository.findAll();
		if (persons != null) {
			for (Person person : persons) {
				if (email.toLowerCase().equals(person.getEmail().toLowerCase())) {
					return person;
				}
			}
		}
		return null;
	}

	public Person findById(Long id) {
		return personRepository.findOne(id);
	}

	public void removePerson(Person person) {
		personRepository.delete(person);
	}

}
