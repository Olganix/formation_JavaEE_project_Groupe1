import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordRescueComponent } from './password-rescue.component';

describe('PasswordRescueComponent', () => {
  let component: PasswordRescueComponent;
  let fixture: ComponentFixture<PasswordRescueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordRescueComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordRescueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
