export enum DayOfWeek {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY
}


export function DayOfWeek_toDisplayString(day: DayOfWeek): string {
  switch (day) {
    case DayOfWeek.MONDAY:    return 'Lundi';
    case DayOfWeek.TUESDAY:   return 'Mardi';
    case DayOfWeek.WEDNESDAY: return 'Mercredi';
    case DayOfWeek.THURSDAY:  return 'Jeudi';
    case DayOfWeek.FRIDAY:    return 'Vendredi';
    case DayOfWeek.SATURDAY:  return 'Samedi';
    case DayOfWeek.SUNDAY:    return 'Dimanche';
  }
  return '';
}


export function DayOfWeek_toHttpObjectString(day: DayOfWeek): string {
  switch (day) {
    case DayOfWeek.MONDAY:    return 'MONDAY';
    case DayOfWeek.TUESDAY:   return 'TUESDAY';
    case DayOfWeek.WEDNESDAY: return 'WEDNESDAY';
    case DayOfWeek.THURSDAY:  return 'THURSDAY';
    case DayOfWeek.FRIDAY:    return 'FRIDAY';
    case DayOfWeek.SATURDAY:  return 'SATURDAY';
    case DayOfWeek.SUNDAY:    return 'SUNDAY';
  }
  return '';
}

export function DayOfWeek_parseFromDisplay(day: string): DayOfWeek {

  switch(day) {
    case 'Lundi':    return DayOfWeek.MONDAY;
    case 'Mardi':    return DayOfWeek.TUESDAY;
    case 'Mercredi': return DayOfWeek.WEDNESDAY;
    case 'Jeudi':    return DayOfWeek.THURSDAY;
    case 'Vendredi': return DayOfWeek.FRIDAY;
    case 'Samedi':   return DayOfWeek.SATURDAY;
    case 'Dimanche': return DayOfWeek.SUNDAY;
  }
  return null;
}
