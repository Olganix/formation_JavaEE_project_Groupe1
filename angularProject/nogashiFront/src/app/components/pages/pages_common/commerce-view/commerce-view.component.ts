import {Component, Input, OnInit} from '@angular/core';
import {Commerce} from '../../../../classes/commerce';
import { SchedulerWeekType } from 'src/app/enum/scheduler-week-type.enum';

@Component({
  selector: 'app-commerce-view',
  templateUrl: './commerce-view.component.html',
  styleUrls: ['./commerce-view.component.scss']
})
export class CommerceViewComponent implements OnInit {

  SchedulerWeekType = SchedulerWeekType;
  @Input() commerce: Commerce;

  constructor() { }

  ngOnInit() {
  }

}
