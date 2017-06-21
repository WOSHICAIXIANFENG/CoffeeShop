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

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.service.OrderService;


@RestController
public class OrderRest {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/order", method=RequestMethod.GET)
	public ResponseEntity<List<Order>> getAllOrder() {
		List<Order> orders = orderService.findAll();
		if (orders.isEmpty()) {
			return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
        System.out.println("Fetching Order with id " + id);
        Order order = orderService.findById((int)id);
        if (order == null) {
            System.out.println("Order with id " + id + " not found");
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }
	
	@RequestMapping(value="/order", method=RequestMethod.POST)
	public ResponseEntity<Void> createOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating Order " + order.getId());
		if (orderService.isOrderExist(order)) {
			System.out.println("A Order with name = " + order.getId() + " , already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		orderService.save(order);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/order/{id}").buildAndExpand(order.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/order/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @RequestBody Order order) {
		System.out.println("Updating Order " + id);
		Order currentOrder = orderService.findById((int)id);
		if (currentOrder == null) {
			System.out.println("Order with id " + id + " not found");
			return new ResponseEntity<Order>(currentOrder, HttpStatus.OK);
		}
		currentOrder.setOrderDate(order.getOrderDate());
		currentOrder.setPerson(order.getPerson());
		// update Orderline List
				
		orderService.save(currentOrder);
		return new ResponseEntity<Order>(currentOrder, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/order/{id}")
	public ResponseEntity<Order> deleteOrder(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Order with id " + id);
		Order order = orderService.findById((int)id);
		if (order == null) {
			System.out.println("Unable to delete. Order with id " + id + " not found");
			return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
		}
		orderService.delete(order);
		return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
	}
}
