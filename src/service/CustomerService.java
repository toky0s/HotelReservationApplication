package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import model.Customer;

public class CustomerService {
  private static CustomerService INSTANCE;

  public static CustomerService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CustomerService();
    }
    return INSTANCE;
  }

  private Collection<Customer> customers = new ArrayList<Customer>();

  public void addCustomer(String email, String firstName, String lastName) throws IllegalArgumentException {
    Optional<Customer> optionalCustomer = customers.stream().filter(c -> c.getEmail().equals(email)).findFirst();
    if (optionalCustomer.isPresent()) {
      throw new IllegalStateException("Customer already added");
    } else {
      Customer customer = new Customer(firstName, lastName, email);
      customers.add(customer);
    }
  }

  public void addCustomer(Customer customer) {
    customers.add(customer);
  }

  public Customer getCustomer(String customerEmail) throws IllegalArgumentException {
    Optional<Customer> optionalCustomer = customers.stream().filter(c -> c.getEmail().equals(customerEmail))
        .findFirst();
    if (optionalCustomer.isPresent()) {
      return optionalCustomer.get();
    } else {
      throw new IllegalArgumentException("Customer with email " + customerEmail + " does not exist");
    }
  }

  public Collection<Customer> getAllCustomers() {
    return customers;
  }
}
