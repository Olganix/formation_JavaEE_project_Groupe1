import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandRecapComponent } from './command-recap.component';

describe('CommandRecapComponent', () => {
  let component: CommandRecapComponent;
  let fixture: ComponentFixture<CommandRecapComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommandRecapComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommandRecapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
