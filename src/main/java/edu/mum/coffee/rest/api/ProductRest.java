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

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.ProductService;

@RestController
public class ProductRest {
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/products", method=RequestMethod.GET)
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> products = productService.getAllProduct();
		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        System.out.println("Fetching product with id " + id);
        Product product = productService.getProduct((int)id);
        if (product == null) {
            System.out.println("Product with id " + id + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
	
	@RequestMapping(value="/product", method=RequestMethod.POST)
	public ResponseEntity<Void> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
		System.out.println("Creating product " + product.getDescription());
		if (productService.isProductExist(product)) {
			System.out.println("A Product with name = " + product.getProductName() + " , already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		productService.save(product);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/product/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		System.out.println("Updating Product " + id);
		Product currentProduct = productService.getProduct((int)id);
		if (currentProduct == null) {
			System.out.println("Product with id " + id + " not found");
			return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
		}
		currentProduct.setDescription(product.getDescription());
		currentProduct.setPrice(product.getPrice());
		currentProduct.setProductName(product.getProductName());
		currentProduct.setProductType(product.getProductType());
		
		productService.save(currentProduct);
		return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/product/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
		System.out.println("Fetching & Deleting Product with id " + id);
		Product product = productService.getProduct(id);
		if (product == null) {
			System.out.println("Unable to delete. Product with id " + id + " not found");
			return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		productService.delete(product);
		return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	}
	
 
}
