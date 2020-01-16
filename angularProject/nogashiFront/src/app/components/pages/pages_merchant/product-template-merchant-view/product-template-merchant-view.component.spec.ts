import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductTemplateMerchantViewComponent } from './product-template-merchant-view.component';

describe('ProductTemplateMerchantViewComponent', () => {
  let component: ProductTemplateMerchantViewComponent;
  let fixture: ComponentFixture<ProductTemplateMerchantViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductTemplateMerchantViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductTemplateMerchantViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
