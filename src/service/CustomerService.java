package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import model.Customer;

public class CustomerService {
  public static CustomerService INSTANCE = new CustomerService();

  private Collection<Customer> customers = new ArrayList<Customer>();

  public void addCustomer(String email, String firstName, String lastName) {
    Customer customer = new Customer(firstName, lastName, email);
    customers.add(customer);
  }

  public void addCustomer(Customer customer) {
    customers.add(customer);
  }

  public Customer getCustomer(String customerEmail) {
    Optional<Customer> optionalCustomer = customers.stream().filter(c -> c.getEmail().equals(customerEmail)).findFirst();
    if (optionalCustomer.isPresent()) {
      return optionalCustomer.get();
    }
    else {
      throw new IllegalArgumentException("Customer with email " + customerEmail + " does not exist");
    }
  }

  public Collection<Customer> getAllCustomers() {
    return customers;
  }
  
}
