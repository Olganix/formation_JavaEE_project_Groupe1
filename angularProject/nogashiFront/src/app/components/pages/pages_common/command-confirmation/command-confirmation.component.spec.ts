import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandConfirmationComponent } from './command-confirmation.component';

describe('CommandConfirmationComponent', () => {
  let component: CommandConfirmationComponent;
  let fixture: ComponentFixture<CommandConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommandConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommandConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
