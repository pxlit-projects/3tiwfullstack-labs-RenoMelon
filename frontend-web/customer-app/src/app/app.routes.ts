import { Routes } from '@angular/router';
import { CustomerList } from './core/customers/customer-list/customer-list';
import { AddCustomer } from './core/customers/add-customer/add-customer';
import { CustomerDetail } from './core/customers/customer-detail/customer-detail';
import { confirmLeaveGuard } from './confirm-leave-guard';

export const routes: Routes = [
    {path: 'customers', component: CustomerList},
    {path: 'add', component: AddCustomer},
    {path: '', redirectTo: 'customers', pathMatch: 'full'},
    {path: 'customer/:id', component: CustomerDetail},
    {path: 'add', component: AddCustomer, canDeactivate: [confirmLeaveGuard]},
];
