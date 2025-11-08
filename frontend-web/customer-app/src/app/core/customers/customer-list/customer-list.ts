import { Component } from '@angular/core';
import { CustomerItem } from '../customer-item/customer-item';
import { Filter } from '../filter/filter';

@Component({
  selector: 'app-customer-list',
  imports: [CustomerItem, Filter],
  templateUrl: './customer-list.html',
  styleUrl: './customer-list.css',
})
export class CustomerList {

}
