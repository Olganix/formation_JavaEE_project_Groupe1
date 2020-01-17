import {Component, Input, OnInit} from '@angular/core';
import {Commerce} from '../../../../classes/commerce';
import { SchedulerWeekType } from 'src/app/enum/scheduler-week-type.enum';

@Component({
  selector: 'app-commerce-merchant-view',
  templateUrl: './commerce-merchant-view.component.html',
  styleUrls: ['./commerce-merchant-view.component.scss']
})
export class CommerceMerchantViewComponent implements OnInit {

  SchedulerWeekType = SchedulerWeekType;
  @Input() commerce: Commerce;

  constructor() { }

  ngOnInit() {
    console.log(this.commerce);
  }

}
