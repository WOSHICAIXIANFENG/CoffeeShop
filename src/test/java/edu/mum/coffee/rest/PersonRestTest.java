package edu.mum.coffee.rest;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Person;

public class PersonRestTest {
	public static final String REST_SERVICE_URI = "http://localhost:8080/";
    
    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllPersons(){
        System.out.println("Testing listAllPersons API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> personsMap = restTemplate.getForObject(REST_SERVICE_URI+"/person/", List.class);
         
        if(personsMap!=null){
            for(LinkedHashMap<String, Object> map : personsMap){
                System.out.println("Person : " + map.get("id")+", Name="+map.get("firstName")+", email="+map.get("email")+", phone="+map.get("phone"));;
            }
        }else{
            System.out.println("No person exist----------");
        }
    }
     
    /* GET */
    private static void getPerson(){
        System.out.println("Testing getPerson API----------");
        RestTemplate restTemplate = new RestTemplate();
        Person person = restTemplate.getForObject(REST_SERVICE_URI+"/person/51", Person.class);
        System.out.println(person);
    }
     
    /* POST */
    private static void createPerson() {
        System.out.println("Testing create Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        Person person = new Person();
        person.setEmail("Samuel.Cai@gmail.com");
        person.setFirstName("firstName");
        person.setLastName("lastName");
        person.setPhone("1323434535");
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
        restTemplate.delete(REST_SERVICE_URI+"/person/52");
    }
    
    private static void getPersonByEmail() {
    	RestTemplate restTemplate = new RestTemplate();
    	restTemplate.setErrorHandler(new TestErrorHandler());
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("email", "samuel@gg.com");

    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

    	ResponseEntity<Person> response = restTemplate.postForEntity( REST_SERVICE_URI + "/personByEmail/", request, Person.class );

    	
    	//URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/personByEmail/", Person.class);
    	System.out.println("Test get person by email response = " + response);
    }
    
    public static void main(String args[]){
//    	createPerson();
//    	createPerson();
//        listAllPersons();
//        getPerson();
//        createPerson();
//        listAllPersons();
//        updatePerson();
//        listAllPersons();
//        deletePerson();
//        listAllPersons();
        
    	getPersonByEmail();
    }
}
