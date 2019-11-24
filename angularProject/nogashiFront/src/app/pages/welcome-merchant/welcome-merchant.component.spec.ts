import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WelcomeMerchantComponent } from './welcome-merchant.component';

describe('WelcomeMerchantComponent', () => {
  let component: WelcomeMerchantComponent;
  let fixture: ComponentFixture<WelcomeMerchantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WelcomeMerchantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomeMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
