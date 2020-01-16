import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommerceMerchantViewComponent } from './commerce-merchant-view.component';

describe('CommerceMerchantViewComponent', () => {
  let component: CommerceMerchantViewComponent;
  let fixture: ComponentFixture<CommerceMerchantViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommerceMerchantViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommerceMerchantViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
