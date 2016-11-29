/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webstore.ws.api;

import com.webstore.model.Order;
import com.webstore.model.Product;
import com.webstore.model.ProductComment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author WeiliangOuyang
 */
@RestController
public class ProductController {
    
    private static List<Product> products= new ArrayList<Product>();

    public static void setProducts(List<Product> products) {
        ProductController.products = products;
    }

    public static List<Product> getProducts() {
        return products;
    }

    static{
    	List<ProductComment> comment = new ArrayList<>();
    		comment.add(new ProductComment(1,"good"));
    		comment.add(new ProductComment(2,"Nice"));
    		
       List<ProductComment> comment2 = new ArrayList<>();
    		comment2.add(new ProductComment(3,"Very Good "));
    		comment2.add(new ProductComment(4,"Nice Product"));

          products.add(new Product(1,"Product1",20.00,comment));
          products.add(new Product(2,"Product2",10.00,comment2));
    }
      
    
    @RequestMapping(value = "/api/products",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Product>> getAllProduct() {
    	// http://127.0.0.1:8080/api/products
        return new ResponseEntity<Collection<Product>>(products, HttpStatus.OK);
    }
    
    Product findProduct(int id){
        
        for( int i = 0; i <products.size();i++ ){
            Product p = products.get(i);
             if(p.getProductId()==id){
        
                return p;
            }
        }
       
        return null;
    }
    
    
    @RequestMapping(value = "/api/products/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable final int id) {
    	// http://127.0.0.1:8080/api/products/1
        final Product p =findProduct(id);
        if (p == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(p, HttpStatus.OK);
    }
    
    /* 
     * http://127.0.0.1:8080/api/products  
     * 
     * Post Man
     * 
      {
    "productId": 100,
    "name": "Product100",
    "price": 100,
    "comment": [
	      {
	        "id": 100,
	        "comment1": "100 Nice"
	      },
	      {
	        "id": 101,
	        "comment1": "101 Nice"
	      }]
	 }
     * 
     * 
     * 
     */
    
    @RequestMapping(value = "/api/products",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody  Product p) {
            p.setProductId(products.size()+1);
            products.add(p);
            
            //POSTMAN  Content-Type: application/json   
            // content:  {"productId":6,"name":"Product6","price":20.0}

        return new ResponseEntity<Product>(p, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/api/products",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody  Product p) {

            
            Product pro =findProduct(p.getProductId());
            products.remove(pro);
            products.add(p);
            
        return new ResponseEntity<Product>(p, HttpStatus.CREATED);
    }
    
    
    @RequestMapping(value = "/api/products/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {

        Product pro =findProduct(id);
        products.remove(pro);

        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
    
    
    
    @RequestMapping(value = "/api/listproducts",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Product>> createListProduct(@RequestBody  List<Product> listPro) {

    	System.out.println("List Pro ==="+listPro.size());

        return new ResponseEntity<Collection<Product>>(listPro, HttpStatus.OK);
    }
    
    /*
     * 
     *  Post a list of product
     *  http://127.0.0.1:8080/api/postlistproducts 
     *  
     *  Post Man Content: {"pro":[{"productId":9,"name":"Product9","price":29.0},{"productId":8,"name":"Product8","price":29.0},{"productId":9,"name":"Product9","price":29.0},{"productId":8,"name":"Product8","price":29.0}]}



PostMan JSON Data

{
	"pro":[
	{"productId":111,
	"name":"Product 111",
	"price":29.0,
	"comment": [
	      {
	        "id": 100,
	        "comment1": "100 Nice"
	      },
	      {
	        "id": 101,
	        "comment1": "101 Nice"
	      }]
	 },
	{"productId":122,"name":"Product 122","price":29.0,
	"comment": [
	      {
	        "id": 100,
	        "comment1": "100 Nice"
	      },
	      {
	        "id": 101,
	        "comment1": "101 Nice"
	      }]
	}
	]
}
     * 
     * 
     */
    
    @RequestMapping(value = "/api/postlistproducts",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Product>> createListProduct(@RequestBody  ProductWrapper pro) {

    	
    	for(Product p:pro.getPro()){
    		products.add(p);
    	}
    	System.out.println("List Pro ==="+pro.getPro().size());

        return new ResponseEntity<Collection<Product>>(pro.getPro(), HttpStatus.OK);
    }
    
}
