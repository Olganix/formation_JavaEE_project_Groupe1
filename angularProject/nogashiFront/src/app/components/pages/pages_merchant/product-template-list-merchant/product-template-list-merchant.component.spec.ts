import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductTemplateListMerchantComponent } from './product-template-list-merchant.component';

describe('ProductTemplateListMerchantComponent', () => {
  let component: ProductTemplateListMerchantComponent;
  let fixture: ComponentFixture<ProductTemplateListMerchantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductTemplateListMerchantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductTemplateListMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
