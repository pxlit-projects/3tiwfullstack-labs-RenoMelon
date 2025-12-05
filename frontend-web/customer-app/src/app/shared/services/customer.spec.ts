import { HttpTestingController, provideHttpClientTesting } from "@angular/common/http/testing";
import { Customer } from "../models/customer.model";
import { CustomerService } from "./customerService";
import { TestBed } from "@angular/core/testing";
import { provideHttpClient } from "@angular/common/http";
import { Filter } from "../../core/customers/filter/filter";
import { FilterModel } from "../models/FilterModel";
import { provideZonelessChangeDetection } from "@angular/core";

describe('CustomerService', () => {
  let service: CustomerService;
  let httpTestingController: HttpTestingController;

  const mockCustomers: Customer[] = [
    new Customer('Dries Swinnen', 'dries@pxl.be', 'Pelt', 'mystreet', 'Belgium', 21),
    new Customer('John Doe', 'john@doe.be', 'New York', '5th Avenue', 'USA', 6),
    new Customer('Jane Doe', 'jane@doe.be', 'Los Angeles', 'Sunset Boulevard', 'USA', 6)
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [
          CustomerService,
          provideHttpClient(),
          provideHttpClientTesting(),
          provideZonelessChangeDetection()
        ],
      }).compileComponents();
    service = TestBed.inject(CustomerService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

   it('should retrieve customers via GET', () => {
        service.getCustomers().subscribe(customers => {
          expect(customers).toEqual(mockCustomers);
        });
      
        const req = httpTestingController.expectOne(service.api);
        expect(req.request.method).toBe('GET');
        req.flush(mockCustomers);
    });

    it('should add a customer via POST', () => {
        const newCustomer = new Customer('Alice Smith', 'alice@smith.com', 'Paris', 'Champs-Élysées', 'France', 42);
      
        service.addCustomer(newCustomer).subscribe(customer => {
          expect(customer).toEqual(newCustomer);
        });
      
        const req = httpTestingController.expectOne(service.api);
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(newCustomer);
        req.flush(newCustomer);
});

it('should update a customer via PUT', () => {
    const updatedCustomer = { ...mockCustomers[0], name: 'Updated Name' };
    
    service.updateCustomer(updatedCustomer).subscribe(customer => {
        expect(customer).toEqual(updatedCustomer);
    });
    
    const req = httpTestingController.expectOne(`/api/customers/${updatedCustomer.id}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedCustomer);
    req.flush(updatedCustomer);
});

it('should retrieve a single customer by ID via GET', () => {
        const customerId = 21;
      
        service.getCustomer(customerId).subscribe(customer => {
          expect(customer).toEqual(mockCustomers[0]);
        });
      
        const req = httpTestingController.expectOne(`api/customers/${customerId}`);
        expect(req.request.method).toBe('GET');
        req.flush(mockCustomers[0]);
      });

it('should filter customers based on the filter criteria', () => {
        const filter: FilterModel = { name: 'dries', city: 'pelt', vat: 21 };
      
        service.filterCustomers(filter).subscribe(customers => {
          expect(customers).toEqual([mockCustomers[0]]);
        });
      
        const req = httpTestingController.expectOne(service.api);
        req.flush(mockCustomers);
      });

it('should match customers correctly with the filter criteria', () => {
        const filter: FilterModel = { name: 'jane', city: 'los angeles', vat: 6 };
        // note: make the isCustomerMatchingFilter method public in the service
        expect(service.isCustomerMatchingFilter(mockCustomers[2], filter)).toBeTrue();
        expect(service.isCustomerMatchingFilter(mockCustomers[0], filter)).toBeFalse();
});

});