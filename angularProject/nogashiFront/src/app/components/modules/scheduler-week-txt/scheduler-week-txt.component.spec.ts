import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedulerWeekTxtComponent } from './scheduler-week-txt.component';

describe('SchedulerWeekTxtComponent', () => {
  let component: SchedulerWeekTxtComponent;
  let fixture: ComponentFixture<SchedulerWeekTxtComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SchedulerWeekTxtComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SchedulerWeekTxtComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
