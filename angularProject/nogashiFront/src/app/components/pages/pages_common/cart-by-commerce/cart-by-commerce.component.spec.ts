import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CartByCommerceComponent } from './cart-by-commerce.component';

describe('CartByCommerceComponent', () => {
  let component: CartByCommerceComponent;
  let fixture: ComponentFixture<CartByCommerceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CartByCommerceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CartByCommerceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
