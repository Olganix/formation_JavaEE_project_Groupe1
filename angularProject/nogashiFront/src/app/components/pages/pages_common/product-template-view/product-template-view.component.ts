import {Component, Input, OnInit} from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';
import {ConnexionService} from '../../../../services/connexion.service';
import { UserRole } from 'src/app/enum/user-role.enum';
import { SchedulerWeekType } from 'src/app/enum/scheduler-week-type.enum';

@Component({
  selector: 'app-product-template-view',
  templateUrl: './product-template-view.component.html',
  styleUrls: ['./product-template-view.component.scss']
})
export class ProductTemplateViewComponent implements OnInit {

  SchedulerWeekType = SchedulerWeekType;
  UserRole = UserRole;

  @Input() productTemplate: ProductTemplate;

  constructor(private connexionService: ConnexionService) { }

  ngOnInit() {
  }

}
