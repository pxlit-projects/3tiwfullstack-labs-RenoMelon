import { Component, inject, OnInit } from '@angular/core';
import { CustomerItem } from '../customer-item/customer-item';
import { Filter } from '../filter/filter';
import { Customer } from '../../../shared/models/customer.model';
import { FilterModel } from '../../../shared/models/FilterModel';
import { AddCustomer } from '../add-customer/add-customer';
import { CustomerService } from '../../../shared/services/customerService';

@Component({
  selector: 'app-customer-list',
  imports: [CustomerItem, Filter, AddCustomer],
  templateUrl: './customer-list.html',
  styleUrl: './customer-list.css',
})
export class CustomerList implements OnInit{

  customers!:Customer[]
  filteredData!: Customer[]
  customerService: CustomerService = inject(CustomerService);

  ngOnInit(): void{

    this.customers = this.customerService.getCustomers();
 
    this.customers[1].isLoyal = true;
    this.filteredData = this.customers;
  }

  
  handleFilter(filter: FilterModel){
    this.filteredData = this.customers.filter(customer => this.customerService.filterCustomers(filter));
  }

  private isCustomerMatchingFilter(customer: Customer, filter: FilterModel): boolean {
    const matchesName = customer.name.toLowerCase().includes(filter.name.toLowerCase());
    const matchesCity = customer.city.toLowerCase().includes(filter.city.toLowerCase());
    const matchesVat = filter.vat ? customer.vat === filter.vat : true;

    return matchesName && matchesCity && matchesVat;
  } 

  processAdd(customer: Customer){
    this.customerService.addCustomer(customer);
    this.filteredData = this.customerService.getCustomers();
  }

}




