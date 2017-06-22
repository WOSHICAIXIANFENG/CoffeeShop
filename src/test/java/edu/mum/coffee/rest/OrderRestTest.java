package edu.mum.coffee.rest;

import java.net.URI;
import java.util.Date;

import org.springframework.web.client.RestTemplate;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;

public class OrderRestTest {
	public static final String REST_SERVICE_URI = "http://localhost:8080/";
	/* POST */
    private static void createOrder() {
        System.out.println("Testing create Person API----------");
        RestTemplate restTemplate = new RestTemplate();
        Person person = new Person();
        person.setEmail("Samuel.Cai@gmail.com");
        person.setFirstName("firstName");
        person.setLastName("lastName");
        person.setPhone("1323434535");
        
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setPerson(person);
        
        Orderline line = new Orderline();
        line.setProduct(new Product());
        line.setQuantity(10);
        //order.addOrderLine(line);
        // todo
        restTemplate.setErrorHandler(new TestErrorHandler());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/order/", order, Order.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
    
    private static void getOrderByPersonId() {
    	///orderByPerson/
    	RestTemplate restTemplate = new RestTemplate();
    	restTemplate.setErrorHandler(new TestErrorHandler());
    	Order[] orders= restTemplate.getForObject(REST_SERVICE_URI + "/orderByPerson/66", Order[].class);
    	for (Order order : orders) {
    		System.out.println(order.getPerson().getEmail());
    	}
    }
    
    
    public static void main(String args[]){
    	createOrder();
    	getOrderByPersonId();
    }
}
