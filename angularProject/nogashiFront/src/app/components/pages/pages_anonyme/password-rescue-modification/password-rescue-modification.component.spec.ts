import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordRescueModificationComponent } from './password-rescue-modification.component';

describe('PasswordRescueModificationComponent', () => {
  let component: PasswordRescueModificationComponent;
  let fixture: ComponentFixture<PasswordRescueModificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordRescueModificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordRescueModificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
