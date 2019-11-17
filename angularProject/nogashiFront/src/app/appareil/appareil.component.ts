import { Component, Input, OnInit } from '@angular/core';
import { AppareilService } from '../services/appareil.service';

@Component({
  selector: 'app-appareil',
  templateUrl: './appareil.component.html',
  styleUrls: ['./appareil.component.scss']
})
export class AppareilComponent implements OnInit {

  //appareilName: string = 'Appareil';
  //appareilStatus: string = 'éteint';
  @Input() appareilName: string;                //use attribut of same name in tag declared for display component
  @Input() appareilStatus: string;
  @Input() index: number;
  @Input() id: number;

  constructor(private appareilService: AppareilService) { }

  ngOnInit() {
  }

  getStatus() {
    return this.appareilStatus;
  }


  getColor() 
  {
    if(this.appareilStatus === 'allumé') {
      return 'green';
    } else if(this.appareilStatus === 'éteint') {
      return 'red';
    }
  }


  onSwitch() 
  {
    this.appareilService.switchAppareil(this.index, (this.appareilStatus === 'éteint'));
  }

}
