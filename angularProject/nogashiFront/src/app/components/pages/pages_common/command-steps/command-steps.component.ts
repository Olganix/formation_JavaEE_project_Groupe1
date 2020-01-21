import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-command-steps',
  templateUrl: './command-steps.component.html',
  styleUrls: ['./command-steps.component.scss']
})
export class CommandStepsComponent implements OnInit {

  @Input() step: number;

  constructor() { }

  ngOnInit() {
  }

}
