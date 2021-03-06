import {Utils} from './utils';
import {DayOfWeek_toDisplayString} from '../enum/day-of-week.enum';

export class SchedulerHoursRange {

  private _startTime: number;     // todo serialize LocalTime into timestamp from start of the day UTC : https://stackoverflow.com/questions/21981902/best-practice-to-serialize-java-time-localdatetime-java-8-to-js-date-using-gso
  private _endTime: number;       // en minutes depuis 00h00 UTC.

  // --- Information to complete the java class
  // SchedulerDay parent;






  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);                       // le json via http crée une liste d'objects, mais pas de Users, donc ici on essaye de regler le soucis, avec un Pip dans le service.
    }
  }

  toHttpObject(): any { return {startTime: this._startTime, endTime: this._endTime}; }


  copy(other: SchedulerHoursRange) {
    if (other === null) {
      return;
    }

    this._startTime = other._startTime;
    this._endTime = other._endTime;
  }


  get startTime(): number {
    return this._startTime;
  }

  set startTime(value: number) {
    this._startTime = value;
  }

  get endTime(): number {
    return this._endTime;
  }

  set endTime(value: number) {
    this._endTime = value;
  }





}
