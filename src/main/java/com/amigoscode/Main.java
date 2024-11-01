package com.amigoscode;

import org.hibernate.annotations.processing.Find;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/customers")
public class Main {

    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String lastname, String email) {
    }

    /**
     * Updates a customer with the given ID with the given details.
     * @param id the ID of the customer to update
     * @param request the details of the customer to update
     */
    @PutMapping({"/{customerId}"})
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        //customerRepository.findById(id);
        Customer customer = customerRepository.getReferenceById(id);
        customer.setName(request.name());
        customer.setLastname(request.lastname());
        customer.setEmail(request.email());
        customerRepository.save(customer);
    }

    /**
     * Retrieves a customer by the given customer ID.
     * @param id the ID of the customer to retrieve
     * @return the Customer object with the specified ID
     */
    @GetMapping({"/{customerId}"})
    public Customer getCustomer(@PathVariable("customerId") Integer id) {
         return customerRepository.findById(id).get();
    }

    /**
     * Adds a new customer to the database.
     * @param request the details of the new customer
     */
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setLastname(request.lastname());
        customer.setEmail(request.email());
        
        customerRepository.save(customer);
    }

    @DeleteMapping({"/{customerId}"})
    public void deleteCustome(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }
}
