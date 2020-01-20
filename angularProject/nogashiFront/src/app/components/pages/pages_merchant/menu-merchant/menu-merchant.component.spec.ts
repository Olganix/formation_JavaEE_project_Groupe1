import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuMerchantComponent } from './menu-merchant.component';

describe('MenuMerchantComponent', () => {
  let component: MenuMerchantComponent;
  let fixture: ComponentFixture<MenuMerchantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MenuMerchantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
