import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCommerceComponent } from './add-commerce.component';

describe('AddCommerceComponent', () => {
  let component: AddCommerceComponent;
  let fixture: ComponentFixture<AddCommerceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCommerceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCommerceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
