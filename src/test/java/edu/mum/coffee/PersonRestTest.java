package edu.mum.coffee;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.mum.coffee.domain.Person;

public class PersonRestTest {
	public static final String REST_SERVICE_URI = "http://localhost:8080/coffeeShop";
    
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllPersons(){
        System.out.println("Testing listAllPersons API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> personsMap = restTemplate.getForObject(REST_SERVICE_URI+"/person/", List.class);
         
        if(personsMap!=null){
            for(LinkedHashMap<String, Object> map : personsMap){
                System.out.println("Person : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No person exist----------");
        }
    }
     
    /* GET */
    private static void getPerson(){
        System.out.println("Testing getPerson API----------");
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject(REST_SERVICE_URI+"/person/1", Person.class);
        System.out.println(person);
    }
     
    /* POST */
    private static void createPerson() {
        System.out.println("Testing create Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        Person person = new Person();
        // todo
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/person/", person, Person.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updatePerson() {
        System.out.println("Testing update Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        Person person  = new Person();
        // todo
        restTemplate.put(REST_SERVICE_URI+"/person/1", person);
        System.out.println(person);
    }
 
    /* DELETE */
    private static void deletePerson() {
        System.out.println("Testing delete Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/person/3");
    }
 
 
    /* DELETE */
    private static void deleteAllPerson() {
        System.out.println("Testing all delete Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/person/");
    }
 
    public static void main(String args[]){
        listAllPersons();
        getPerson();
        createPerson();
        listAllPersons();
        updatePerson();
        listAllPersons();
        deletePerson();
        listAllPersons();
        deleteAllPerson();
        listAllPersons();
    }
}
