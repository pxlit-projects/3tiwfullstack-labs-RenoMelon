import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Filter } from './filter';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { FilterModel } from '../../../shared/models/FilterModel';
import { CommonModule } from '@angular/common';
import { provideZonelessChangeDetection } from '@angular/core';

describe('Filter', () => {
  let component: Filter;
  let fixture: ComponentFixture<Filter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Filter, FormsModule, CommonModule],
      providers: [provideZonelessChangeDetection()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Filter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit filterChanged event on form submission', () => {
    spyOn(component.filterChanged, 'emit');

    const expectedFilter: FilterModel = { name: 'test', city: 'test city', vat: 1234 };
    component.filter = { ...expectedFilter };

    fixture.detectChanges();

    const form = fixture.debugElement.query(By.css('form'));
    form.triggerEventHandler('ngSubmit', null);

    expect(component.filterChanged.emit).toHaveBeenCalledWith({
      ...expectedFilter,
      name: expectedFilter.name.toLowerCase(),
      city: expectedFilter.city.toLowerCase()
    });
  });

  it('should convert name and city to lowercase before emitting', () => {
    spyOn(component.filterChanged, 'emit');

    const filterWithUppercase: FilterModel = { name: 'TEST', city: 'TEST CITY', vat: 1234 };
    component.filter = { ...filterWithUppercase };

    fixture.detectChanges();

    const form = fixture.debugElement.query(By.css('form'));
    form.triggerEventHandler('ngSubmit', null);

    expect(component.filterChanged.emit).toHaveBeenCalledWith({
      name: 'test',
      city: 'test city',
      vat: 1234
    });
  });

  it('should emit filterChanged event if form is valid', () => {
    spyOn(component.filterChanged, 'emit');
    const form = { valid: true };
    component.onSubmit(form);
    expect(component.filterChanged.emit).toHaveBeenCalled();
  });
});