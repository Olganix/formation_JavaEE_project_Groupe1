import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ConnexionService} from '../../../../services/connexion.service';

@Component({
  selector: 'app-menu-merchant',
  templateUrl: './menu-merchant.component.html',
  styleUrls: ['./menu-merchant.component.scss']
})
export class MenuMerchantComponent implements OnInit {

  @Input() sectionDisplayed;
  @Output() sectionChange = new EventEmitter();

  constructor(private connexionService: ConnexionService
          ) { }

  ngOnInit() {
  }

  showSection(index) {
    this.sectionDisplayed = index;
    this.sectionChange.emit(this.sectionDisplayed);
  }
}
