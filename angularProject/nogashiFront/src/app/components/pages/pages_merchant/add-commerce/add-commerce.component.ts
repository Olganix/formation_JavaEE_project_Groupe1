import {Component, OnInit} from '@angular/core';
import {Commerce} from '../../../../classes/commerce';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MerchantService} from '../../../../services/merchant.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {ActivatedRoute, Router} from '@angular/router';
import {RestResponse} from '../../../../classes/rest-response';
import {UserService} from '../../../../services/user.service';
import {Address} from '../../../../classes/address';
import {SchedulerWeekType} from 'src/app/enum/scheduler-week-type.enum';
import {SchedulerWeek} from '../../../../classes/scheduler-week';

@Component({
  selector: 'app-add-commerce',
  templateUrl: './add-commerce.component.html',
  styleUrls: ['./add-commerce.component.scss']
})
export class AddCommerceComponent implements OnInit {

  SchedulerWeekType = SchedulerWeekType;

  form1: FormGroup;

  id: FormControl;
  name: FormControl;
  codeSiret: FormControl;
  uniqueIdName: FormControl;
  description: FormControl;

  address_id: FormControl;
  address_address: FormControl;
  address_addressExtra: FormControl;
  address_postalCode: FormControl;
  address_cityName: FormControl;
  address_stateName: FormControl;
  address_longitude: FormControl;
  address_latitude: FormControl;

  private schedulerWeek: SchedulerWeek;
  private schedulerWeek_onError = false;

  pictureLogo: FormControl;
  private defaultLogo = 'NoLogo.jpg';
  pictureDescription: FormControl;
  private defaultDescription = 'NoDescription.jpg';
  isOpened: FormControl;

  /*
    commerceCategories: FormControl; // TODO
    productTemplates: FormControl; // TODO
    products: FormControl; // TODO
    shoppingCartByCommerces: FormControl; // TODO
   */

  constructor(private merchantService: MerchantService,
              private userService: UserService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              private router: Router,
              private route: ActivatedRoute,
              private fb: FormBuilder
      ) { }

  ngOnInit() {
    const id = ((this.route.snapshot.params.id !== undefined) && (this.route.snapshot.params.id !== '')) ? Number(this.route.snapshot.params.id) : 0;

    this.id = new FormControl(id, [ Validators.required ]);
    this.name = new FormControl(null, [ Validators.required ]);
    this.codeSiret = new FormControl(null, [ Validators.required ]);
    this.uniqueIdName = new FormControl(null, [ ]);
    this.description = new FormControl(null, [ Validators.required ]);

    this.address_id = new FormControl(null, []);
    this.address_address = new FormControl(null, [ Validators.required ]);
    this.address_addressExtra = new FormControl(null, []);
    this.address_postalCode = new FormControl(null, [ Validators.required ]);
    this.address_cityName = new FormControl(null, [ Validators.required ]);
    this.address_stateName = new FormControl(null, [ Validators.required ]);
    this.address_longitude = new FormControl(null, [ Validators.required ]);
    this.address_latitude = new FormControl(null, [ Validators.required ]);

    this.schedulerWeek_onError = false;
    this.schedulerWeek = new SchedulerWeek();
    this.schedulerWeek.type = SchedulerWeekType.OPEN;
    this.schedulerWeek.days = [];

    this.pictureLogo = new FormControl(null, [ ]);
    this.pictureDescription = new FormControl(null, [ ]);
    this.isOpened = new FormControl(false, [ ]);


    this.form1 = this.fb.group({
      id : this.id,
      name : this.name,
      codeSiret : this.codeSiret,
      uniqueIdName: this.uniqueIdName,
      description: this.description,

      address_id: this.address_id,
      address_address: this.address_address,
      address_addressExtra: this.address_addressExtra,
      address_postalCode: this.address_postalCode,
      address_cityName: this.address_cityName,
      address_stateName: this.address_stateName,
      address_longitude: this.address_longitude,
      address_latitude: this.address_latitude,

      pictureLogo: this.pictureLogo,
      pictureDescription: this.pictureDescription,
      isOpened: this.isOpened
    });

    // recuperation les info pour l'update.
    if (id !== 0) {
      if ((this.merchantService.lastCommerce) && (this.merchantService.lastCommerce.id === id)) {               // on a gardé le dernier pour eviter les problemes dans les changement de page.
        this.__setFormData(this.merchantService.lastCommerce);
      } else {

        this.userService.getCommerceById(id).subscribe(    // le + c'est pour caster un string en number
          (rrp: RestResponse) => {

            if (rrp.status === 'SUCCESS') {
              this.__setFormData(new Commerce(rrp.data));
            } else {
              console.log('Echec de la recuperation d\'un commerce : ' + rrp.errormessage);
            }
          },
          error => {
            console.log('Echec de la recuperation d\'un commerce : ', error);
          });
      }
    }
  }


  private __setFormData(c: Commerce) {

    this.name.setValue(c.name);
    this.codeSiret.setValue(c.codeSiret);
    this.uniqueIdName.setValue(c.uniqueIdName);
    this.description.setValue(c.description);

    this.address_address.setValue(c.address.address);
    this.address_addressExtra.setValue(c.address.addressExtra);
    this.address_postalCode.setValue(c.address.postalCode);
    this.address_cityName.setValue(c.address.cityName);
    this.address_stateName.setValue(c.address.stateName);
    this.address_longitude.setValue(c.address.longitude);
    this.address_latitude.setValue(c.address.latitude);

    this.schedulerWeek_onError = false;
    this.schedulerWeek = new SchedulerWeek();
    this.schedulerWeek.copy(c.schedulerWeek);

    this.defaultLogo = c.pictureLogo;
    this.defaultDescription = c.pictureDescription;
    this.isOpened.setValue(c.isOpened);
  }

  onSubmit(e) {

    e.preventDefault();
    e.stopPropagation();

    this.schedulerWeek_onError = false;
    if ((this.schedulerWeek.type !== SchedulerWeekType.OPEN) ||      // check if there is a minimum required.
      (this.schedulerWeek.days.length === 0) ||
      (this.schedulerWeek.days[0].hoursRanges.length === 0)
    ) {
      this.schedulerWeek_onError = true;
      return false;
    }



    if (this.form1.valid) {


      console.log('form:');
      console.log(this.form1.value);

      const c = new Commerce();
      const a = new Address();
      a.setAddress(this.form1.value.address_id, this.form1.value.address_address, this.form1.value.address_addressExtra, this.form1.value.address_postalCode, this.form1.value.address_cityName, this.form1.value.address_stateName, this.form1.value.address_longitude, this.form1.value.address_latitude);
      c.setAddCommerce(this.form1.value.id, this.form1.value.name, this.form1.value.codeSiret, this.form1.value.uniqueIdName, this.form1.value.description, a, ((this.form1.value.pictureLogo !== null) && (this.form1.value.pictureLogo.trim() !== '') ) ? this.form1.value.pictureLogo : this.defaultLogo, ((this.form1.value.pictureDescription !== null) && (this.form1.value.pictureDescription.trim() !== '') ) ? this.form1.value.pictureDescription : this.defaultDescription, this.form1.value.isOpened);
      c.schedulerWeek = this.schedulerWeek;

      this.merchantService.addOrUpdateCommerce( c ).subscribe(
        (rrp: RestResponse) => {

          console.log('addOrUpdateCommerce: ');
          console.log(rrp);

          if (rrp.status === 'SUCCESS') {
            this.infoBoxNotificationsService.addMessage('info', 'La sauvegarde de la fiche commerce a bien été effectuée.', 10);

            const cRet = new Commerce(rrp.data);
            this.router.navigate(['/commerceSheet/' + cRet.id]);

          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de la sauvegarde de la fiche commerce : ' + rrp.errormessage, 10);
          }
        },
        error => {
          console.log('Error occured', error);
          this.infoBoxNotificationsService.addMessage('error', 'Echec de la sauvegarde de la fiche commerce : ' + error, 10);
        });
    }

    return false;
  }


  public controlName(): string {
    if (this.name.touched) {
      if (this.name.hasError('required')) {
        return `Le nom du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlcodeSiret(): string {
    if (this.codeSiret.touched) {
      if (this.codeSiret.hasError('required')) {
        return `Le code Siret du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlDescription(): string {
    if (this.description.touched) {
      if (this.description.hasError('required')) {
        return `La description du commerce est obligatoire`;
      }
    }
    return null;
  }



  public controlAddress_address(): string {
    if (this.address_address.touched) {
      if (this.address_address.hasError('required')) {
        return `La rue du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlAddress_postalCode(): string {
    if (this.address_postalCode.touched) {
      if (this.address_postalCode.hasError('required')) {
        return `Le code postal du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlAddress_cityName(): string {
    if (this.address_cityName.touched) {
      if (this.address_cityName.hasError('required')) {
        return `La ville du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlAddress_stateName(): string {
    if (this.address_stateName.touched) {
      if (this.address_stateName.hasError('required')) {
        return `Le pays du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlAddress_longitude(): string {
    if (this.address_longitude.touched) {
      if (this.address_longitude.hasError('required')) {
        return `La longitude du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlAddress_latitude(): string {
    if (this.address_latitude.touched) {
      if (this.address_latitude.hasError('required')) {
        return `La latitude du commerce est obligatoire`;
      }
    }
    return null;
  }



  public controlPictureLogo(): string {
    if (this.pictureLogo.touched) {
      if (this.pictureLogo.hasError('required')) {
        return `Le logo du commerce est obligatoire`;
      }
    }
    return null;
  }

  public controlPictureDescription(): string {
    if (this.pictureDescription.touched) {
      if (this.pictureDescription.hasError('required')) {
        return `L'image de description du commerce est obligatoire`;
      }
    }
    return null;
  }
}
