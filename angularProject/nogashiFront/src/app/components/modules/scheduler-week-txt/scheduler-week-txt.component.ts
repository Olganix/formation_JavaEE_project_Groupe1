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
  scheduler_simplified: any;
  editMode = true;

  edit_scheduler_simplified = [];

  constructor() { }

  ngOnInit() {
    this.createSampleScheduler();

    this.scheduler_simplified = this.scheduler.getSimplifiedRangeForTextDisplay_open();

    console.log(this.scheduler_simplified );
  }


  dayName(id: DayOfWeek): string { return DayOfWeek_toDisplayString(id); }

  remove(index: number) {
    this.scheduler_simplified.splice(index, 1);
  }

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
