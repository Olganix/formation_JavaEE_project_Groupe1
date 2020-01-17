import { Component, OnInit } from '@angular/core';
import {BuyerService} from '../../../../services/buyer.service';
import {ActivatedRoute} from '@angular/router';
import {RestResponse} from '../../../../classes/rest-response';
import {Commerce} from '../../../../classes/commerce';

@Component({
  selector: 'app-commerce-sheet',
  templateUrl: './commerce-sheet.component.html',
  styleUrls: ['./commerce-sheet.component.scss']
})
export class CommerceSheetComponent implements OnInit {

  commerce: Commerce;

  constructor(private buyerService: BuyerService,
              private route: ActivatedRoute
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.params.id;

    this.buyerService.getCommerceById(+id).subscribe(    // le + c'est pour caster un string en number
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.commerce = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des fiches commerces : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des fiches commerces : ', error);
      });
  }
}
