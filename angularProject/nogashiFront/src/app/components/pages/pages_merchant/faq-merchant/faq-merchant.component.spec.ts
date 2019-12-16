import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FaqMerchantComponent } from './faq-merchant.component';

describe('FaqMerchantComponent', () => {
  let component: FaqMerchantComponent;
  let fixture: ComponentFixture<FaqMerchantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FaqMerchantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FaqMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
