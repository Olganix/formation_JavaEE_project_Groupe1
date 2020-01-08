import {SchedulerHoursRange} from './scheduler-hours-range';
import {DayOfWeek} from '../enum/day-of-week.enum';

export class SchedulerDay {

  private _day: DayOfWeek;
  private _hoursRanges: SchedulerHoursRange[];

  // --- Information to complete the java class
  // SchedulerWeek parent;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }


  get day(): DayOfWeek {
    return this._day;
  }

  set day(value: DayOfWeek) {
    this._day = value;
  }

  get hoursRanges(): SchedulerHoursRange[] {
    return this._hoursRanges;
  }

  set hoursRanges(value: SchedulerHoursRange[]) {
    this._hoursRanges = value;
  }
}
