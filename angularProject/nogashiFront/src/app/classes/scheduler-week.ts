import {SchedulerDay} from './scheduler-day';
import {SchedulerWeekType} from '../enum/scheduler-week-type.enum';


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
