import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Customer } from './shared/models/customer.model';
import { CommonModule } from '@angular/common';
import { Navbar } from './core/navbar/navbar';
import { CustomerList } from './core/customers/customer-list/customer-list';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, Navbar, CustomerList],
  templateUrl: './app.html',
  styleUrl: './app.css'
})

export class App {
  protected readonly title = signal('customer-app');

    constructor() {
    const c1 = new Customer('Dries Swinnen', 'dries@d-ries.be','Pelt','mystreet','Belgium',21);
    const c2 = new Customer('John Doe', 'john@doe.com','New York','5th Avenue','USA',6, 'john.png');
    c2.isLoyal = true;
    
    console.log(c1);
    console.log(c2);
  }
}


