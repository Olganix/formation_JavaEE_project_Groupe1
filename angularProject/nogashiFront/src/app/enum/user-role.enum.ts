export enum UserRole {
  INDIVIDUAL,
  MERCHANT,
  ASSOCIATION,
  ADMIN
}


export function UserRole_toDisplayString(role: UserRole): string {
  switch (role) {
    case UserRole.INDIVIDUAL:     return 'INDIVIDUAL';
    case UserRole.MERCHANT:       return 'MERCHANT';
    case UserRole.ASSOCIATION:    return 'ASSOCIATION';
    case UserRole.ADMIN:          return 'ADMIN';
  }
  return '';
}

export function UserRole_parseFromDisplay(role: string): UserRole {

  switch (role) {
    case 'INDIVIDUAL':    return UserRole.INDIVIDUAL;
    case 'MERCHANT':      return UserRole.MERCHANT;
    case 'ASSOCIATION':   return UserRole.ASSOCIATION;
    case 'ADMIN':         return UserRole.ADMIN;
  }
  return null;
}
