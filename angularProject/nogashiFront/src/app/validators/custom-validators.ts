import {AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';


export class CustomValidators {

  private static listForbiddenName = ['admin', 'root', '', 'login', 'god'];     // todo complet the list
  public static passwordRules = {minLength: 8, maxLength: 16, pattern: ''};        // Todo partern


  public static nameCheck(): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
      if ((control.value != null) && (control.value !== '')) {
        if (this.listForbiddenName.includes( control.value.toLowerCase().trim())) {
          return {error_name: true};
        }
      }
      return null;
    };
  }


  public static email(): ValidatorFn {

    return (control: AbstractControl): ValidationErrors | null => {
      if ((control.value != null) && (control.value !== '')) {

        // '[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}'
        // '^[0-9a-z._-]+@[0-9a-z.-]{2,}\\.[a-z]{2,5}'
        // const regex = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/ );    // https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript
        // const regex = new RegExp('^(([^<>()\\[\\]\\\\.,;:\\s@"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@"]+)*)|(".+"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$');
        // const regex = new RegExp('^[0-9a-z._-]+@[0-9a-z-]{2,}\\.[a-z]{2,5}', 'i');
        const regex = new RegExp('[A-Za-z0-9._%-]+@[A-Za-z0-9._%-]+\\.[a-z]{2,3}', 'i');
        if (!regex.test(control.value)) {
          return {error_email: true};
        }
      }
      return null;
    };
  }


  public static match_password(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      if (control.get('password').value !== control.get('confirm').value) {
        return { match_password: true};
      }
      return null;
    };
  }


}
