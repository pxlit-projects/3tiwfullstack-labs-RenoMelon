import { Component, OnDestroy, inject } from '@angular/core';
import { AsyncPipe, NgClass, NgIf } from '@angular/common';
import { CustomerService } from '../../../shared/services/customerService';
import { Customer } from '../../../shared/models/customer.model';
import { Observable, Subscription, tap } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-customer-detail',
  imports: [NgClass, AsyncPipe],
  templateUrl: './customer-detail.html',
  styles: [
  ]
})
export class CustomerDetail implements OnDestroy {
  customerService: CustomerService = inject(CustomerService);
  route: ActivatedRoute = inject(ActivatedRoute);
  id: number = this.route.snapshot.params['id'];
  sub!: Subscription;

  customer$: Observable<Customer> = this.customerService.getCustomer(this.id);

  toggleLoyal(customer: Customer) {
    customer.isLoyal = !customer.isLoyal;
    this.sub = this.customerService.updateCustomer(customer).subscribe({
      next: (data) => {
        console.log("done!");
      }
    });
  }

  ngOnDestroy(): void {
    if(this.sub){
      this.sub.unsubscribe();
    }
  }
}