import { Component, OnInit } from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';
import {ActivatedRoute} from '@angular/router';
import {BuyerService} from '../../../../services/buyer.service';
import {RestResponse} from '../../../../classes/rest-response';
import {UserService} from '../../../../services/user.service';

@Component({
  selector: 'app-product-sheet',
  templateUrl: './product-sheet.component.html',
  styleUrls: ['./product-sheet.component.scss']
})
export class ProductSheetComponent implements OnInit {

  productTemplate: ProductTemplate;

  constructor(private userService: UserService,
              private route: ActivatedRoute
              ) { }

  ngOnInit() {
    const id = this.route.snapshot.params.id;

    this.userService.getProductTemplateById(+id).subscribe(    // le + c'est pour caster un string en number
    (rrp: RestResponse) => {

      if (rrp.status === 'SUCCESS') {
        this.productTemplate = rrp.data;
      } else {
        console.log('Echec de la recuperation de la liste des fiches produits : ' + rrp.errormessage);
      }
    },
    error => {
      console.log('Echec de la recuperation de la liste des fiches produits : ', error);
    });
  }

}
