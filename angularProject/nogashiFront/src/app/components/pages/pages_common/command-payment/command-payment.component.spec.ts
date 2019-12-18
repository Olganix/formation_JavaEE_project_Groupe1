import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandPaymentComponent } from './command-payment.component';

describe('CommandPaymentComponent', () => {
  let component: CommandPaymentComponent;
  let fixture: ComponentFixture<CommandPaymentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommandPaymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommandPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
