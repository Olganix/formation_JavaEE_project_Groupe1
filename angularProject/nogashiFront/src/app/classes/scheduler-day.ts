import {SchedulerHoursRange} from './scheduler-hours-range';
import {DayOfWeek, DayOfWeek_toDisplayString} from '../enum/day-of-week.enum';
import {Utils} from './utils';

export class SchedulerDay {

  private _day: DayOfWeek;
  private _hoursRanges: SchedulerHoursRange[] = [];

  // --- Information to complete the java class
  // SchedulerWeek parent;

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }

  isSimilarDay(other: SchedulerDay): boolean {

    if (this._hoursRanges.length !== other.hoursRanges.length) {
      return false;
    }

    for (let i = 0; i < this._hoursRanges.length; i++) {
      if ( (this._hoursRanges[i].startTime !== other.hoursRanges[i].startTime) ||
           (this._hoursRanges[i].endTime !== other.hoursRanges[i].endTime) ) {
        return false;
      }
    }
    return true;
  }

  mergeHours(other: SchedulerDay) {

    const hrs = this._hoursRanges;
    this._hoursRanges = [];

    for (const hr of other.hoursRanges) {
      hrs.push(hr);
    }

    if (hrs.length === 0) {
      return;
    }

    hrs.sort( (a, b) => (a.startTime === b.startTime) ? 0 : ((a.startTime < b.startTime) ? 1 : -1));
    console.log(hrs);

    const hrs_tmp: SchedulerHoursRange[] = [];

    for (const hr of hrs) {

      let itTaked = false;
      for (const hrt of hrs_tmp) {

        const sint = Utils.mergeIntervals({start: hrt.startTime, end: hrt.endTime}, {start: hr.startTime, end: hr.endTime});

        if (sint.length === 1) {       // hr include or extend hrt (hr is not distinct for hrt)
          hrt.endTime = sint[0].end;              // notice, with previous sort on startTime, we could only extend for endTime.
          itTaked = true;
          break;
        }
      }
      if (!itTaked) {
        hrs_tmp.push(hr);
      }

    }

    this._hoursRanges = hrs_tmp;
  }

  removeHours(other: SchedulerDay) {

    for (const hr of other.hoursRanges) {

      console.log('---------- trying  remove hoursRange ' + hr.startTime + ' -> ' + hr.endTime + ' : ');

      for (let i = 0; i < this._hoursRanges.length; i++) {
        const hrt = this._hoursRanges[i];

        if ( ( hrt.startTime === hr.startTime) && ( hrt.endTime === hr.endTime) ) {

          console.log('removed');
          this._hoursRanges.splice(i, 1);
        }
      }
    }
  }



  // ---------------------------------

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
