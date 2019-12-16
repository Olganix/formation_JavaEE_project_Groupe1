import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandStepsComponent } from './command-steps.component';

describe('CommandStepsComponent', () => {
  let component: CommandStepsComponent;
  let fixture: ComponentFixture<CommandStepsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommandStepsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommandStepsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
