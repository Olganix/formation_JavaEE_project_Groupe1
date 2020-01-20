import {UserRole} from './user-role.enum';

export enum SchedulerWeekType {
  GROUP,
  OPEN,
  PRODUCT_PROMOTION,
  PRODUCT_UNSOLD,
}


export function SchedulerWeekType_toDisplayString(type: SchedulerWeekType): string {
  switch (type) {
    case SchedulerWeekType.GROUP:               return 'GROUP';
    case SchedulerWeekType.OPEN:                return 'OPEN';
    case SchedulerWeekType.PRODUCT_PROMOTION:   return 'PRODUCT_PROMOTION';
    case SchedulerWeekType.PRODUCT_UNSOLD:      return 'PRODUCT_UNSOLD';
  }
  return '';
}

export function SchedulerWeekType_parseFromDisplay(type: string): SchedulerWeekType {

  switch (type) {
    case 'GROUP':               return SchedulerWeekType.GROUP;
    case 'OPEN':                return SchedulerWeekType.OPEN;
    case 'PRODUCT_PROMOTION':   return SchedulerWeekType.PRODUCT_PROMOTION;
    case 'PRODUCT_UNSOLD':      return SchedulerWeekType.PRODUCT_UNSOLD;
  }
  return null;
}
