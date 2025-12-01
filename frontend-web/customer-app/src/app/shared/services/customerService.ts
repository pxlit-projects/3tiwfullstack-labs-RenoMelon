import { inject, Injectable } from '@angular/core';
import { Customer } from '../models/customer.model';
import { FilterModel } from '../models/FilterModel';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {

  api: string = "api/customers";
  http: HttpClient = inject(HttpClient);
  
  getCustomers(): Observable<Customer[]>{
    return this.http.get<Customer[]>(this.api)
  }

  addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.api, customer);
}

 filterCustomers(filter: FilterModel): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.api).pipe(
      map((customers: Customer[]) => customers.filter(customer => this.isCustomerMatchingFilter(customer, filter)))
    );
  }

  updateCustomer(customer: Customer): Observable<Customer>{
    return this.http.put<Customer>('/api/customers/' + customer.id, customer);
  }

  getCustomer(id: number): Observable<Customer> {
    return this.http.get<Customer>('api/customers/' + id);
  }

  private isCustomerMatchingFilter(customer: Customer, filter: FilterModel): boolean {
    const matchesName = customer.name.toLowerCase().includes(filter.name.toLowerCase());
    const matchesCity = customer.city.toLowerCase().includes(filter.city.toLowerCase());
    const matchesVat = filter.vat ? customer.vat === filter.vat : true;

    return matchesName && matchesCity && matchesVat;
  }
  
}