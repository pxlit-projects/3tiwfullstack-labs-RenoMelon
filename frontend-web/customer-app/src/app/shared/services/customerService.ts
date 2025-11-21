import { Injectable } from '@angular/core';
import { Customer } from '../models/customer.model';
import { FilterModel } from '../models/FilterModel';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  
  customers: Customer[] = [
    new Customer('Dries Swinnen', 'dries@pxl.be', 'Pelt', 'mystreet', 'Belgium', 21),
    new Customer('John Doe', 'john@doe.be', 'New York', '5th Avenue', 'USA', 6),
    new Customer('Jane Doe', 'jane@doe.be', 'Los Angeles', 'Sunset Boulevard', 'USA', 6)
]

  getCustomers(): Customer[] {
      return this.customers;
  } 

  addCustomer(customer: Customer): void {
      this.customers.push(customer);
  }

  filterCustomers(filter: FilterModel): Customer[] {
    return this.customers.filter(customer => this.isCustomerMatchingFilter(customer, filter));
  }

  private isCustomerMatchingFilter(customer: Customer, filter: FilterModel): boolean {
    const matchesName = customer.name.toLowerCase().includes(filter.name.toLowerCase());
    const matchesCity = customer.city.toLowerCase().includes(filter.city.toLowerCase());
    const matchesVat = filter.vat ? customer.vat === filter.vat : true;

    return matchesName && matchesCity && matchesVat;
  }
  
}