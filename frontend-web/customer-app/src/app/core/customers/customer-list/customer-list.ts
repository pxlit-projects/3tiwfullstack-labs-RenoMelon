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

  filteredData!: Customer[]
  customerService: CustomerService = inject(CustomerService);

  ngOnInit(): void{

    this.customerService.getCustomers().subscribe({
     next: () => this.fetchData()
    });
  }


  handleFilter(filter: FilterModel){
    this.customerService.filterCustomers(filter).subscribe({
      next: customers => this.filteredData = customers
    });
  }


  fetchData(): void {
    this.customerService.getCustomers().subscribe({
      next: customers => {
        this.filteredData = customers;
      }
    });
  }

}




