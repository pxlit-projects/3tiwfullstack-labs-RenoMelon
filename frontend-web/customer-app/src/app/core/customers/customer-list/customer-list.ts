import { Component, OnInit } from '@angular/core';
import { CustomerItem } from '../customer-item/customer-item';
import { Filter } from '../filter/filter';
import { Customer } from '../../../shared/models/customer.model';
import { FilterModel } from '../../../shared/models/FilterModel';
import { AddCustomer } from '../add-customer/add-customer';

@Component({
  selector: 'app-customer-list',
  imports: [CustomerItem, Filter, AddCustomer],
  templateUrl: './customer-list.html',
  styleUrl: './customer-list.css',
})
export class CustomerList implements OnInit{

  customers!:Customer[]
  filteredData!: Customer[]

  ngOnInit(): void{
    this.customers = [
       new Customer('Dries Swinnen', 'dries@pxl.be', 'Pelt', 'mystreet', 'Belgium', 21),
       new Customer('John Doe', 'john@doe.be', 'New York', '5th Avenue', 'USA', 6),
       new Customer('Jane Doe', 'jane@doe.be', 'Los Angeles', 'Sunset Boulevard', 'USA', 6),
    ]

    this.customers[1].isLoyal = true;
    this.filteredData = this.customers;
  }

  
  handleFilter(filter: FilterModel){
    this.filteredData = this.customers.filter(customer => this.isCustomerMatchingFilter(customer, filter));
  }

  private isCustomerMatchingFilter(customer: Customer, filter: FilterModel): boolean {
    const matchesName = customer.name.toLowerCase().includes(filter.name.toLowerCase());
    const matchesCity = customer.city.toLowerCase().includes(filter.city.toLowerCase());
    const matchesVat = filter.vat ? customer.vat === filter.vat : true;

    return matchesName && matchesCity && matchesVat;
  } 

  processAdd(customer: Customer){
    this.customers.push(customer);
    this.filteredData = this.customers;
  }

}




