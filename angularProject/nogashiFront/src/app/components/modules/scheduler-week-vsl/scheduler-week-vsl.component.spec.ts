import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedulerWeekVslComponent } from './scheduler-week-vsl.component';

describe('SchedulerWeekVslComponent', () => {
  let component: SchedulerWeekVslComponent;
  let fixture: ComponentFixture<SchedulerWeekVslComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SchedulerWeekVslComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SchedulerWeekVslComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
