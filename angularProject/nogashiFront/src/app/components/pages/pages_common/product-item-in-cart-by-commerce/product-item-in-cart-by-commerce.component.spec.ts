import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductItemInCartByCommerceComponent } from './product-item-in-cart-by-commerce.component';

describe('ProductItemInCartByCommerceComponent', () => {
  let component: ProductItemInCartByCommerceComponent;
  let fixture: ComponentFixture<ProductItemInCartByCommerceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductItemInCartByCommerceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductItemInCartByCommerceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
