import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/interval';
import { Subscription } from 'rxjs/Subscription';
import {SchedulerWeek} from '../../../classes/scheduler-week';
import {SchedulerWeekType} from '../../../enum/scheduler-week-type.enum';
import {SchedulerHoursRange} from '../../../classes/scheduler-hours-range';
import {SchedulerDay} from '../../../classes/scheduler-day';
import {DayOfWeek} from '../../../enum/day-of-week.enum';
import {UserRole} from '../../../enum/user-role.enum';
import {ConnexionService} from '../../../services/connexion.service';
import {User} from '../../../classes/user';
import {RestResponse} from '../../../classes/rest-response';
import {InfoBoxNotificationsService} from '../../../services/InfoBoxNotifications.services';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit {

  SchedulerWeekType = SchedulerWeekType;

  displayTest = false;
  scheduler: SchedulerWeek;
  editMode = false;

  title = 'NoGashi';

  secondes: number;
  counterSubscription: Subscription;


  private loginList: any = [];
  private loginSelectedIndex = 4;
  private userConnectedName: string = null;


  constructor(private connexionService: ConnexionService,
              private infoBoxNotificationsService: InfoBoxNotificationsService,
              ) { }



  ngOnInit() {

    this.loginList = [
      {name: 'Admin', password: 'totototo', role: UserRole.ADMIN},
      {name: 'Merchant', password: 'totototo', role: UserRole.MERCHANT},
      {name: 'User', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Association', password: 'totototo', role: UserRole.ASSOCIATION},
      {name: 'Anonymous', password: 'totototo', role: UserRole.INDIVIDUAL},

      {name: 'BASILIC & CO Développement', password: 'totototo', role: UserRole.MERCHANT},
      {name: 'Daily-juicery', password: 'totototo', role: UserRole.MERCHANT},
      {name: 'Boulangerie-Mathieu', password: 'totototo', role: UserRole.MERCHANT},
      {name: 'Paul', password: 'totototo', role: UserRole.MERCHANT},
      {name: 'Big Fernand', password: 'totototo', role: UserRole.MERCHANT},

      {name: 'Anaïs Despins', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Éléonore Asselin', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Elita Quenneville', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Geoffrey Verreau', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Delmar Dumont', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Nathalie Gabriaux', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Charles Margand', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Belisarda De La Vergne', password: 'totototo', role: UserRole.INDIVIDUAL},
      {name: 'Gilles Collin', password: 'totototo', role: UserRole.INDIVIDUAL},

      {name: 'La Tente des Glaneurs', password: 'totototo', role: UserRole.ASSOCIATION},
      {name: 'Les Gars\'pilleurs', password: 'totototo', role: UserRole.ASSOCIATION},
      {name: 'Zéro-Gâchis', password: 'totototo', role: UserRole.ASSOCIATION},
      {name: 'Banques Alimentaire du Nord', password: 'totototo', role: UserRole.ASSOCIATION}
    ];

    this.userConnectedName = this.connexionService.getLocalConnectedName();
    for (let i = 0; i < this.loginList.length; i++) {
      if (this.loginList[i].name === this.userConnectedName) {
        this.loginSelectedIndex = i;
        break;
      }
    }

    this.createSampleScheduler();

    const counter = Observable.interval(1000);

    this.counterSubscription = counter.subscribe(
      (value) => {
        this.secondes = value;
      },
      (error) => {
        console.log('Uh-oh, an error occurred! : ' + error);
      },
      () => {
        console.log('Observable complete!');
      }
    );
  }

  ngOnDestroy() {
    this.counterSubscription.unsubscribe();
  }

  toggleTestdisplay() {
    this.displayTest = ! this.displayTest;
  }


  // ---------------------------------
  loginChange() {


    const user = new User();
    const login = this.loginList[this.loginSelectedIndex];
    user.setLogin(login.name, login.password);

    this.connexionService.login( user ).subscribe(
      (rrp: RestResponse) => {

        console.log('component.login: ');
        console.log(rrp);


        if (rrp.status === 'SUCCESS') {

          this.infoBoxNotificationsService.addMessage('info', 'Vous êtes connecté.', 2);

          this.userConnectedName = this.connexionService.getLocalConnectedName();
          for (let i = 0; i < this.loginList.length; i++) {
            if (this.loginList[i].name === this.userConnectedName) {
              this.loginSelectedIndex = i;
              break;
            }
          }

        } else {

          if (rrp.errorCode === 2) {                       // email not valid
            this.infoBoxNotificationsService.addMessage('error', 'Echec de la connexion car votre email n\'est pas validé.<br>Veuilliez vous référer au message en dessous du bouton "connexion"', 20);
          } else {
            this.infoBoxNotificationsService.addMessage('error', 'Echec de la connexion : ' + rrp.errormessage, 10);
          }
        }
      },
      error => {
        console.log('Error occured', error);
        this.infoBoxNotificationsService.addMessage('error', 'Echec de la connexion : ' + error, 10);
      });
  }


  // ---------------------------------






  createSampleScheduler() {
    this.scheduler = new SchedulerWeek();
    this.scheduler.type = SchedulerWeekType.GROUP;
    this.scheduler.group = [];

    const sched_commerceOpen = new SchedulerWeek();
    sched_commerceOpen.type = SchedulerWeekType.OPEN;
    sched_commerceOpen.description = 'Horaires d\'ouverture du commerce pour la semaine';
    sched_commerceOpen.days = [];

    const sched_hr_am = new SchedulerHoursRange();
    sched_hr_am.startTime = 9 * 60;
    sched_hr_am.endTime = 12 * 60 + 30;

    const sched_hr_pm = new SchedulerHoursRange();
    sched_hr_pm.startTime = 14 * 60;
    sched_hr_pm.endTime = 19 * 60 + 30;

    let sched_d;
    for (let i = 0; i < 5; i++) {    // Monday -> Friday
      sched_d = new SchedulerDay();
      sched_d.day = i;
      sched_d.hoursRanges = [];
      sched_d.hoursRanges.push(sched_hr_am);
      sched_d.hoursRanges.push(sched_hr_pm);
      sched_commerceOpen.days.push(sched_d);
    }

    sched_d = new SchedulerDay();
    sched_d.day = DayOfWeek.SATURDAY;
    sched_d.hoursRanges = [];
    sched_d.hoursRanges.push(sched_hr_am);
    sched_commerceOpen.days.push(sched_d);

    this.scheduler.group.push(sched_commerceOpen);

    // --------------------------------------

    const sched_productPromotion = new SchedulerWeek();
    sched_productPromotion.type = SchedulerWeekType.PRODUCT_PROMOTION;
    sched_productPromotion.description = 'Horaires a laquel un produit donné sera en promotion';
    sched_productPromotion.days = [];

    const sched_hr_promotion = new SchedulerHoursRange();
    sched_hr_promotion.startTime = 18 * 60;
    sched_hr_promotion.endTime = 19 * 60;

    for (let i = 0; i < 5; i++) {    // Monday -> Friday
      sched_d = new SchedulerDay();
      sched_d.day = i;
      sched_d.hoursRanges = [];
      sched_d.hoursRanges.push(sched_hr_promotion);
      sched_productPromotion.days.push(sched_d);
    }

    const sched_hr_promotion2 = new SchedulerHoursRange();
    sched_hr_promotion2.startTime = 11 * 60 + 30;
    sched_hr_promotion2.endTime = 12 * 60;

    sched_d = new SchedulerDay();
    sched_d.day = DayOfWeek.SATURDAY;
    sched_d.hoursRanges = [];
    sched_d.hoursRanges.push(sched_hr_promotion2);
    sched_productPromotion.days.push(sched_d);

    this.scheduler.group.push(sched_productPromotion);

    // --------------------------------------

    const sched_productUnsold = new SchedulerWeek();
    sched_productUnsold.type = SchedulerWeekType.PRODUCT_UNSOLD;
    sched_productUnsold.description = 'Horaires a laquel un produit donné sera en promotion';
    sched_productUnsold.days = [];

    const sched_hr_unsold = new SchedulerHoursRange();
    sched_hr_unsold.startTime = 19 * 60;
    sched_hr_unsold.endTime = 19 * 60 + 30;

    for (let i = 0; i < 5; i++) {    // Monday -> Friday
      sched_d = new SchedulerDay();
      sched_d.day = i;
      sched_d.hoursRanges = [];
      sched_d.hoursRanges.push(sched_hr_unsold);
      sched_productUnsold.days.push(sched_d);
    }

    const sched_hr_unsold2 = new SchedulerHoursRange();
    sched_hr_unsold2.startTime = 12 * 60;
    sched_hr_unsold2.endTime = 12 * 60 + 30;

    sched_d = new SchedulerDay();
    sched_d.day = DayOfWeek.SATURDAY;
    sched_d.hoursRanges = [];
    sched_d.hoursRanges.push(sched_hr_unsold2);
    sched_productUnsold.days.push(sched_d);

    this.scheduler.group.push(sched_productUnsold);
  }

}
