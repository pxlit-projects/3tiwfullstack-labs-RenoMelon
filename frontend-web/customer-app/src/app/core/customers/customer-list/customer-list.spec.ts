import { ComponentFixture, TestBed } from "@angular/core/testing";
import { CustomerList } from "./customer-list";
import { CustomerService } from "../../../shared/services/customerService";
import { Customer } from "../../../shared/models/customer.model";
import { of } from "rxjs";
import { Filter } from "../filter/filter";
import { FilterModel } from "../../../shared/models/FilterModel";
import { provideZonelessChangeDetection } from "@angular/core";

describe('CustomerList', () => {
  let component: CustomerList;
  let fixture: ComponentFixture<CustomerList>;
  let customerServiceMock: jasmine.SpyObj<CustomerService>;
  const mockCustomers: Customer[] = [
    new Customer('Dries Swinnen', 'dries@d-ries.be', 'Pelt', 'mystreet', 'Belgium', 21),
    new Customer('Alice Johnson', 'alice.johnson@example.com', 'Brussels', 'anotherstreet', 'Belgium', 35)
  ];
  beforeEach(() => {
   customerServiceMock = jasmine.createSpyObj('CustomerService', ['getCustomers', 'filterCustomers']);

    TestBed.configureTestingModule({
      imports: [CustomerList],
      providers: [
        { provide: CustomerService, useValue: customerServiceMock },
        provideZonelessChangeDetection()
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CustomerList);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy()
  })

  it('should fetch customers on initialization', () => {
    customerServiceMock.getCustomers.and.returnValue(of(mockCustomers));
    fixture.detectChanges();
    expect(customerServiceMock.getCustomers).toHaveBeenCalled();
    expect(component.filteredData).toEqual(mockCustomers);
  });

  it('should fetch customers and set filteredData$', () => {
   customerServiceMock.getCustomers.and.returnValue(of(mockCustomers));

   component.fetchData();

   expect(customerServiceMock.getCustomers).toHaveBeenCalled();
   expect(component.filteredData).toEqual(mockCustomers);
  })

  it('should filter customers based on the filter criteria', () => {
    const filter: FilterModel = { name: 'dries', city: '', vat: undefined };
    const filteredCustomers: Customer[] = [new Customer('Dries Swinnen', 'dries@d-ries.be', 'Pelt', 'mystreet', 'Belgium', 21)];
    customerServiceMock.filterCustomers.and.returnValue(of(filteredCustomers));

    component.handleFilter(filter);

    expect(customerServiceMock.filterCustomers).toHaveBeenCalledWith(filter);
    expect(component.filteredData).toEqual(filteredCustomers);
    });
  });


