import {SchedulerDay} from './scheduler-day';
import {SchedulerWeekType, SchedulerWeekType_toDisplayString} from '../enum/scheduler-week-type.enum';
import {SchedulerHoursRange} from './scheduler-hours-range';
import {DayOfWeek_toDisplayString} from '../enum/day-of-week.enum';


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

    /* tslint:disable:no-string-literal */
    if ( (obj !== undefined) && (obj !== null) ) {

      if ( (obj.hasOwnProperty('type')) && (obj['type'] !== null)) {
        switch (obj['type']) {
          case 'GROUP': this._type = SchedulerWeekType.GROUP; break;
          case 'OPEN': this._type = SchedulerWeekType.OPEN; break;
          case 'PRODUCT_PROMOTION': this._type = SchedulerWeekType.PRODUCT_PROMOTION; break;
          case 'PRODUCT_UNSOLD': this._type = SchedulerWeekType.PRODUCT_UNSOLD; break;
        }
      }

      this._group = [];
      if ((obj.hasOwnProperty('group')) && (obj['group'] !== null)) {
        for (const tmp of obj['group']) {
          this._group.push(new SchedulerWeek(tmp));
        }
      }

      this._days = [];
      if ((obj.hasOwnProperty('days')) && (obj['days'] !== null)) {
        for (const tmp of obj['days']) {
          this._days.push(new SchedulerDay(tmp));
        }
      }
    }
  }


  toHttpObject(): any {
    const obj = {name: this._name, description: this._description, type: SchedulerWeekType_toDisplayString(this._type), group: [], days: [] };
    if (this._type === SchedulerWeekType.GROUP ) {
      for (const sw of this._group) {
        obj.group.push( sw.toHttpObject() );
      }
    } else {
      for (const sd of this._days) {
        obj.days.push( sd.toHttpObject() );
      }
    }
    return obj;
  }

  copy(other: SchedulerWeek) {
    if (other === null) {
      return;
    }
    this._name = other._name;
    this._description = other._description;
    this._type = other._type;

    this._group = [];
    for (const tmp of other._group) {
      const sw = new SchedulerWeek();
      sw.copy(tmp);
      this._group.push(sw);
    }

    this._days = [];
    for (const tmp of other._days) {
      const sd = new SchedulerDay();
      sd.copy(tmp);
      this._days.push(sd);
    }
  }



  getSimplifiedRangeForTextDisplay_typed(type: SchedulerWeekType): any {

    if (type === SchedulerWeekType.GROUP) {
      return [];
    }

    if (this._type === type) {
      return this.getSimplifiedRangeForTextDisplay();

    } else if (this._type === SchedulerWeekType.GROUP) {
      for (const w of this._group) {
        if (w.type === type) {
          return w.getSimplifiedRangeForTextDisplay();
        }
      }
    }
    return [];
  }

  addSimplifiedRange_typed(similarDay: any, type: SchedulerWeekType) {
    if (type === SchedulerWeekType.GROUP) {
      return;
    }

    if (this._type === type) {
      return this.addSimplifiedRange(similarDay);

    } else if (this._type === SchedulerWeekType.GROUP) {
      for (const w of this._group) {
        if (w.type === type) {
          return w.addSimplifiedRange(similarDay);
        }
      }
    }
  }

  removeSimplifiedRange_typed(similarDay: any, type: SchedulerWeekType) {
    if (type === SchedulerWeekType.GROUP) {
      return;
    }

    if (this._type === type) {
      return this.removeSimplifiedRange(similarDay);

    } else if (this._type === SchedulerWeekType.GROUP) {
      for (const w of this._group) {
        if (w.type === type) {
          return w.removeSimplifiedRange(similarDay);
        }
      }
    }
  }




  addSimplifiedRange(similarDay: any) {

    if ((similarDay === undefined) ||
        (similarDay.day === undefined) ||
        (similarDay.dayRanges === undefined)) {       // todo add check of day class
      return;
    }

    for (const dayR of similarDay.dayRanges) {

      dayR.startDay = dayR.startDay % 7;
      dayR.endDay = dayR.endDay % 7;
      if (dayR.endDay < dayR.startDay) {
        dayR.endDay += 7;
      }

      for (let i = dayR.startDay; i <= dayR.endDay; i++) {
        const dayIndex = i % 7;

        let isFound = false;
        for (const day of this._days) {
          if (day.day === dayIndex) {
            isFound = true;
            day.mergeHours(similarDay.day);
          }
        }
        if (!isFound) {

          const hr: SchedulerHoursRange[] = [];
          for (const hrt of similarDay.day.hoursRanges) {
            hr.push( new SchedulerHoursRange({startTime: hrt.startTime, endTime: hrt.endTime} ));
          }

          this._days.push( new SchedulerDay( {day: dayIndex, hoursRanges:  hr} ) );
        }
      }
    }
  }


  removeSimplifiedRange(similarDay: any) {

    if ((similarDay === undefined) ||
      (similarDay.day === undefined) ||
      (similarDay.dayRanges === undefined)) {       // todo add check of day class
      return;
    }

    for (const dayR of similarDay.dayRanges) {

      dayR.startDay = dayR.startDay % 7;
      dayR.endDay = dayR.endDay % 7;
      if (dayR.endDay < dayR.startDay) {
        dayR.endDay += 7;
      }

      for (let i = dayR.startDay; i <= dayR.endDay; i++) {
        const dayIndex = i % 7;
        console.log('--- remove ' + DayOfWeek_toDisplayString(dayIndex) + ' :');

        for (const day of this._days) {
          if (day.day === dayIndex) {
            day.removeHours(similarDay.day);
          }
        }
      }
    }
  }

  getSimplifiedRangeForTextDisplay(): any {

    const similarDaysIndex = [];

    if (this._days.length === 0) {
      return similarDaysIndex;
    }

    this.reOrderDays();             // on ordonne les elements pour qu'ils soit plus facile a comparer



    let isFound: boolean;
    let day: SchedulerDay;
    for (let i = 0 ; i < this._days.length; i++) {
      day = this._days[i];
      if (day.hoursRanges.length === 0) {
        continue;
      }

      isFound = false;
      for (const s of similarDaysIndex) {
        if (s.day.isSimilarDay(day)) {
          isFound = true;
          s.similars.push(i);
          break;
        }
      }
      if (!isFound) {
        const hoursRanges: SchedulerHoursRange[] = [];
        for ( const ht of this._days[i].hoursRanges) {
          hoursRanges.push(new SchedulerHoursRange({startTime: ht.startTime, endTime: ht.endTime}) );
        }

        similarDaysIndex.push( {day: new SchedulerDay({day: this._days[i].day, hoursRanges}), similars: [] } );
      }
    }

    for (const s of similarDaysIndex) {                           // on cherche les jours consécutifs, pour l'affichage.: du Lundi au Mercredi, du Vendredi au Samedi.
      s.dayRanges = [{startDay: s.day.day, endDay: s.day.day}];
      for (const sim of s.similars) {
        day = this._days[sim];
        if ( day.day === s.dayRanges[s.dayRanges.length - 1].endDay + 1) {
          s.dayRanges[s.dayRanges.length - 1].endDay = day.day;
        } else {
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
