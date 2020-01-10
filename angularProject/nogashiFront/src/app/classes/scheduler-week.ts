import {SchedulerDay} from './scheduler-day';
import {SchedulerWeekType} from '../enum/scheduler-week-type.enum';
import {SchedulerHoursRange} from './scheduler-hours-range';


export class SchedulerWeek {

  private _name: string;
  private _description: string;
  private _type: SchedulerWeekType;
  private _group: SchedulerWeek[];
  private _days: SchedulerDay[];

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }


  getSimplifiedRangeForTextDisplay_open(): any {

    if (this._type === SchedulerWeekType.OPEN) {
      return this.getSimplifiedRangeForTextDisplay();

    } else if (this._type === SchedulerWeekType.GROUP) {
      for(const w of this._group){
        if (w.type === SchedulerWeekType.OPEN) {
          return w.getSimplifiedRangeForTextDisplay();
        }
      }
    }
    return null;
  }


  getSimplifiedRangeForTextDisplay(): any {

    if (this._days.length === 0) {
      return null;
    }

    this.reOrderDays();             // on ordonne les elements pour qu'ils soit plus facile a comparer

    const similarDaysIndex = [];

    let isFound: boolean;
    let day: SchedulerDay;
    for (let i = 0 ; i < this._days.length; i++) {
      day = this._days[i];
      isFound = false;
      for (const s of similarDaysIndex) {
        if (s.day.isSimilarDay(day)) {
          isFound = true;
          s.similars.push(i);
          break;
        }
      }
      if (!isFound) {
        similarDaysIndex.push( {day, similars: [] } );
      }
    }

    for (const s of similarDaysIndex) {                           // on cherche les jours consécutifs, pour l'affichage.: du Lundi au Mercredi, du Vendredi au Samedi.
      s.dayRanges = [{startDay: s.day.day, endDay: s.day.day}];
      for(const sim of s.similars) {
        day = this._days[sim];
        if ( day.day === s.dayRanges[s.dayRanges.length - 1].endDay + 1) {
          s.dayRanges[s.dayRanges.length - 1].endDay = day.day;
        } else{
          s.dayRanges = [{startDay: day.day, endDay: day.day}];
        }
      }
    }
    return similarDaysIndex;
  }

  reOrderDays() {
    this._days.sort((a: SchedulerDay, b: SchedulerDay) => (a.day === b.day) ? 0 : ((a.day < b.day) ? -1 : 1) );
    for (const d of this._days) {
      d.hoursRanges.sort((a: SchedulerHoursRange, b: SchedulerHoursRange) => (a.startTime === b.startTime) ? 0 : ((a.startTime < b.startTime) ? -1 : 1) );
    }
  }



  // ---------------------------------

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }

  get type(): SchedulerWeekType {
    return this._type;
  }

  set type(value: SchedulerWeekType) {
    this._type = value;
  }

  get group(): SchedulerWeek[] {
    return this._group;
  }

  set group(value: SchedulerWeek[]) {
    this._group = value;
  }

  get days(): SchedulerDay[] {
    return this._days;
  }

  set days(value: SchedulerDay[]) {
    this._days = value;
  }
}
