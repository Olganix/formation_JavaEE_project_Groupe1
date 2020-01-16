import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SchedulerWeek} from '../../../classes/scheduler-week';
import {SchedulerWeekType} from '../../../enum/scheduler-week-type.enum';
import {SchedulerDay} from '../../../classes/scheduler-day';
import {DayOfWeek, DayOfWeek_toDisplayString} from '../../../enum/day-of-week.enum';
import {SchedulerHoursRange} from '../../../classes/scheduler-hours-range';
import {Utils} from '../../../classes/utils';

@Component({
  selector: 'app-scheduler-week-txt',
  templateUrl: './scheduler-week-txt.component.html',
  styleUrls: ['./scheduler-week-txt.component.scss']
})
export class SchedulerWeekTxtComponent implements OnInit {

  minutesToTimeDisplay_TimeZoneClient = Utils.minutesToTimeDisplay_TimeZoneClient;

  @Input() scheduler: SchedulerWeek;
  @Input() schedulerType: SchedulerWeekType;
  @Output() schedulerChange = new EventEmitter();
  @Input() editMode = false;

  scheduler_simplified: any;
  edit_scheduler_simplified = null;

  ip_dayStart = 0;
  ip_dayEnd = 0;
  ip_listDayIndex = [0, 1, 2, 3, 4, 5, 6];
  ip_hourStart_h = 8;
  ip_hourStart_min = 0;
  ip_listHours = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];
  ip_listMinutes = [0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55];
  ip_hourEnd_h = 18;
  ip_hourEnd_min = 0;

  constructor() { }

  ngOnInit() {
    this.init();
  }

  init() {
    this.edit_scheduler_simplified = {day: new SchedulerDay(), dayRanges: [] };

    this.scheduler_simplified = this.scheduler.getSimplifiedRangeForTextDisplay_typed(this.schedulerType);
  }

  dayName(id: DayOfWeek): string { return DayOfWeek_toDisplayString(id); }
  typeName(id: SchedulerWeekType): string {
    switch (id) {
      case 1: return `d'ouverture`;
      case 2: return ` en promotion`;
      case 3: return ` en Invendu`;
    }
  }



  // --------------------------------- Add Simplified Scheduler
  addDayRange() {
    this.edit_scheduler_simplified.dayRanges.push({startDay: this.ip_dayStart, endDay: this.ip_dayEnd});
    this.ip_dayStart = this.ip_dayEnd = (this.ip_dayEnd + 1) % 7;
  }
  removeDayRange(index: number) {
    if (index < this.edit_scheduler_simplified.dayRanges.length) {
      this.edit_scheduler_simplified.dayRanges.splice(index, 1);
    }
  }

  addHoursRange() {

    if (this.ip_hourStart_h > this.ip_hourEnd_h) {
      let tmp = this.ip_hourStart_h;
      this.ip_hourStart_h = this.ip_hourEnd_h;
      this.ip_hourEnd_h = tmp;

      tmp = this.ip_hourStart_min;
      this.ip_hourStart_min = this.ip_hourEnd_min;
      this.ip_hourEnd_min = tmp;

    } else if ((this.ip_hourStart_h === this.ip_hourEnd_h) && (this.ip_hourStart_min > this.ip_hourEnd_min)) {
      const tmp = this.ip_hourStart_min;
      this.ip_hourStart_min = this.ip_hourEnd_min;
      this.ip_hourEnd_min = tmp;
    }

    const timeZoneOffset = (new Date()).getTimezoneOffset();
    this.edit_scheduler_simplified.day.hoursRanges.push({startTime: (this.ip_hourStart_h * 60 + this.ip_hourStart_min - timeZoneOffset), endTime: (this.ip_hourEnd_h * 60 + this.ip_hourEnd_min - timeZoneOffset)});
  }
  removeHoursRange(index: number) {
    if (index < this.edit_scheduler_simplified.dayRanges.length) {
      this.edit_scheduler_simplified.dayRanges.splice(index, 1);
    }
  }

  saveSimplifiedScheduler() {

    this.scheduler.addSimplifiedRange_typed(this.edit_scheduler_simplified, this.schedulerType);

    this.init();

    this.schedulerChange.emit(this.scheduler);
  }

  remove(index: number) {

    this.scheduler.removeSimplifiedRange_typed(this.scheduler_simplified[index], this.schedulerType);

    this.scheduler_simplified.splice(index, 1);

    this.schedulerChange.emit(this.scheduler);
  }
}
