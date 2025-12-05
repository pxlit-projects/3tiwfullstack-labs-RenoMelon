import { ComponentFixture, TestBed } from "@angular/core/testing";
import { CustomerItem } from "./customer-item";
import { Customer } from "../../../shared/models/customer.model";
import { provideRouter } from "@angular/router";
import { NO_ERRORS_SCHEMA, provideZonelessChangeDetection } from "@angular/core";
import { By } from "@angular/platform-browser";

describe('CustomerItem', () => {
  let component: CustomerItem;
  let fixture: ComponentFixture<CustomerItem>;
  const mockCustomer: Customer = new Customer('Alice Johnson', 'alice.johnson@example.com', 'Brussels', 'anotherstreet', 'Belgium', 35);

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CustomerItem],
      providers: [
        provideRouter([]), provideZonelessChangeDetection()
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();
  
    fixture = TestBed.createComponent(CustomerItem);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  })

  it('should log the customer details to the console', () => {
    component.customer = mockCustomer;
    spyOn(console, 'log');

    component.getDetails();

    expect(console.log).toHaveBeenCalledWith(mockCustomer);
  })

      it('should render customer name in the template', () => {
    component.customer = mockCustomer;
    fixture.detectChanges();
    
    const debugElement = fixture.debugElement.query(By.css('h2'));
    expect(debugElement.nativeElement.textContent).toContain('Alice Johnson');
  });
});