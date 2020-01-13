import {Component, OnInit} from '@angular/core';
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


  scheduler: SchedulerWeek;
  schedulerType: SchedulerWeekType;
  scheduler_simplified: any;
  editMode = true;

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
    this.createSampleScheduler();       // test todo remove
    this.schedulerType = SchedulerWeekType.OPEN;

    this.init();
  }

  init() {
    this.edit_scheduler_simplified = {day: new SchedulerDay(), dayRanges: [{startDay: DayOfWeek.MONDAY, endDay: DayOfWeek.FRIDAY}] };
    const hr = new SchedulerHoursRange();
    hr.startTime = 10 * 60;
    hr.endTime = 10 * 60;
    this.edit_scheduler_simplified.day.hoursRanges.push(hr);


    // todo find the way to update scheduler_simplified from a update of scheduler, and also test change on editMode = true;
    this.scheduler_simplified = this.scheduler.getSimplifiedRangeForTextDisplay_typed(this.schedulerType);
  }

  dayName(id: DayOfWeek): string { return DayOfWeek_toDisplayString(id); }




  // --------------------------------- Add Simplified Scheduler
  addDayRange() {
    this.edit_scheduler_simplified.dayRanges.push({startDay: this.ip_dayStart, endDay: this.ip_dayEnd});
    this.ip_dayStart = this.ip_dayEnd = (this.ip_dayEnd + 1) % 6;
  }
  removeDayRange(index: number) {
    if (index < this.edit_scheduler_simplified.dayRanges.length) {
      this.edit_scheduler_simplified.dayRanges.splice(index, 1);
    }
  }

  addHoursRange() {
    this.edit_scheduler_simplified.day.hoursRanges.push({startTime: (this.ip_hourStart_h * 60 + this.ip_hourStart_min), endTime: (this.ip_hourEnd_h * 60 + this.ip_hourEnd_min)});
  }
  removeHoursRange(index: number) {
    if (index < this.edit_scheduler_simplified.dayRanges.length) {
      this.edit_scheduler_simplified.dayRanges.splice(index, 1);
    }
  }

  saveSimplifiedScheduler() {
    this.scheduler.addSimplifiedRange_typed(this.edit_scheduler_simplified, this.schedulerType);
    this.init();
  }

  remove(index: number) {
    this.scheduler_simplified.splice(index, 1);
  }

  // ---------------------------------






  createSampleScheduler() {
    this.scheduler = new SchedulerWeek();
    this.scheduler.type = SchedulerWeekType.GROUP;
    this.scheduler.group = [];

    const sched_commerceOpen = new SchedulerWeek();
    sched_commerceOpen.type = SchedulerWeekType.OPEN;
    sched_commerceOpen.description = 'Horaires d\'ouverture du commerce pour la semaine';
    sched_commerceOpen.days = [];

    const sched_hr_am = new SchedulerHoursRange();
    sched_hr_am.startTime = 9 * 60;
    sched_hr_am.endTime = 12 * 60 + 30;

    const sched_hr_pm = new SchedulerHoursRange();
    sched_hr_pm.startTime = 14 * 60;
    sched_hr_pm.endTime = 19 * 60 + 30;

    let sched_d;
    for (let i = 0; i < 5; i++) {    // Monday -> Friday
      sched_d = new SchedulerDay();
      sched_d.day = i;
      sched_d.hoursRanges = [];
      sched_d.hoursRanges.push(sched_hr_am);
      sched_d.hoursRanges.push(sched_hr_pm);
      sched_commerceOpen.days.push(sched_d);
    }

    sched_d = new SchedulerDay();
    sched_d.day = DayOfWeek.SATURDAY;
    sched_d.hoursRanges = [];
    sched_d.hoursRanges.push(sched_hr_am);
    sched_commerceOpen.days.push(sched_d);

    this.scheduler.group.push(sched_commerceOpen);

    // --------------------------------------

    const sched_productPromotion = new SchedulerWeek();
    sched_productPromotion.type = SchedulerWeekType.PRODUCT_PROMOTION;
    sched_productPromotion.description = 'Horaires a laquel un produit donné sera en promotion';
    sched_productPromotion.days = [];

    const sched_hr_promotion = new SchedulerHoursRange();
    sched_hr_promotion.startTime = 18 * 60;
    sched_hr_promotion.endTime = 19 * 60;

    for (let i = 0; i < 5; i++) {    // Monday -> Friday
      sched_d = new SchedulerDay();
      sched_d.day = i;
      sched_d.hoursRanges = [];
      sched_d.hoursRanges.push(sched_hr_promotion);
      sched_productPromotion.days.push(sched_d);
    }

    const sched_hr_promotion2 = new SchedulerHoursRange();
    sched_hr_promotion2.startTime = 11 * 60 + 30;
    sched_hr_promotion2.endTime = 12 * 60;

    sched_d = new SchedulerDay();
    sched_d.day = DayOfWeek.SATURDAY;
    sched_d.hoursRanges = [];
    sched_d.hoursRanges.push(sched_hr_promotion2);
    sched_productPromotion.days.push(sched_d);

    this.scheduler.group.push(sched_productPromotion);

    // --------------------------------------

    const sched_productUnsold = new SchedulerWeek();
    sched_productUnsold.type = SchedulerWeekType.PRODUCT_UNSOLD;
    sched_productUnsold.description = 'Horaires a laquel un produit donné sera en promotion';
    sched_productUnsold.days = [];

    const sched_hr_unsold = new SchedulerHoursRange();
    sched_hr_unsold.startTime = 19 * 60;
    sched_hr_unsold.endTime = 19 * 60 + 30;

    for (let i = 0; i < 5; i++) {    // Monday -> Friday
      sched_d = new SchedulerDay();
      sched_d.day = i;
      sched_d.hoursRanges = [];
      sched_d.hoursRanges.push(sched_hr_unsold);
      sched_productUnsold.days.push(sched_d);
    }

    const sched_hr_unsold2 = new SchedulerHoursRange();
    sched_hr_unsold2.startTime = 12 * 60;
    sched_hr_unsold2.endTime = 12 * 60 + 30;

    sched_d = new SchedulerDay();
    sched_d.day = DayOfWeek.SATURDAY;
    sched_d.hoursRanges = [];
    sched_d.hoursRanges.push(sched_hr_unsold2);
    sched_productUnsold.days.push(sched_d);

    this.scheduler.group.push(sched_productUnsold);
  }

}
