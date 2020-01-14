import {Component, Input, OnInit} from '@angular/core';
import {SchedulerWeek} from '../../../classes/scheduler-week';
import {Utils} from '../../../classes/utils';
import {DayOfWeek, DayOfWeek_toDisplayString} from '../../../enum/day-of-week.enum';
import {SchedulerWeekType} from '../../../enum/scheduler-week-type.enum';
import {SchedulerHoursRange} from '../../../classes/scheduler-hours-range';
import {SchedulerDay} from '../../../classes/scheduler-day';

@Component({
  selector: 'app-scheduler-week-vsl',
  templateUrl: './scheduler-week-vsl.component.html',
  styleUrls: ['./scheduler-week-vsl.component.scss']
})
export class SchedulerWeekVslComponent implements OnInit {

  @Input() scheduler: SchedulerWeek;

  minutesToTimeDisplay_TimeZoneClient = Utils.minutesToTimeDisplay_TimeZoneClient;



  displaylistDay = [DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY];
  displaylistHours = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23];


  constructor() { }

  ngOnInit() {

  }

  dayName(id: DayOfWeek): string { return DayOfWeek_toDisplayString(id); }

}
