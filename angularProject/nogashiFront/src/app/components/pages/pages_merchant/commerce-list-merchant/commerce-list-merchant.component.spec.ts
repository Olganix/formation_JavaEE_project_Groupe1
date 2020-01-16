import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommerceListMerchantComponent } from './commerce-list-merchant.component';

describe('CommerceListMerchantComponent', () => {
  let component: CommerceListMerchantComponent;
  let fixture: ComponentFixture<CommerceListMerchantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommerceListMerchantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommerceListMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
