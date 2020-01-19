import {Component, Input, OnInit} from '@angular/core';
import {Commerce} from '../../../../classes/commerce';
import { SchedulerWeekType } from 'src/app/enum/scheduler-week-type.enum';
import {ActivatedRoute} from '@angular/router';
import {MerchantService} from '../../../../services/merchant.service';
import {ConnexionService} from '../../../../services/connexion.service';
import { UserRole } from 'src/app/enum/user-role.enum';

@Component({
  selector: 'app-commerce-view',
  templateUrl: './commerce-view.component.html',
  styleUrls: ['./commerce-view.component.scss']
})
export class CommerceViewComponent implements OnInit {

  SchedulerWeekType = SchedulerWeekType;
  UserRole = UserRole;
  // SchedulerWeekType = SchedulerWeekType;
  @Input() commerce: Commerce;

  constructor(private connexionService: ConnexionService,

      ) { }

  ngOnInit() {
  }

}
