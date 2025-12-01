import { CanDeactivateFn } from '@angular/router';
import { AddCustomer } from './core/customers/add-customer/add-customer';

export const confirmLeaveGuard: CanDeactivateFn<AddCustomer> = (component, currentRoute, currentState, nextState) => {
  if(component.customerForm.dirty){
    return window.confirm("Are you sure you want to leave this page?");
  }else{
    return true;
  }
};