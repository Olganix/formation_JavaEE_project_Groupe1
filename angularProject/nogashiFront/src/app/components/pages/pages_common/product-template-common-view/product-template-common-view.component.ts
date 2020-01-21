import {Component, Input, OnInit} from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';
import * as $ from 'jquery';
import {RestResponse} from '../../../../classes/rest-response';
import {environment} from '../../../../../environments/environment';
import {map, retry} from 'rxjs/operators';
import {BuyerService} from '../../../../services/buyer.service';
import {InfoBoxNotificationsService} from '../../../../services/InfoBoxNotifications.services';
import {ProductTemplateListForAdd} from '../../../../classes/product-template-list-for-add';
import {Commerce} from '../../../../classes/commerce';

@Component({
  selector: 'app-product-template-common-view',
  templateUrl: './product-template-common-view.component.html',
  styleUrls: ['./product-template-common-view.component.scss']
})
export class ProductTemplateCommonViewComponent implements OnInit {

  Number = Number;
  String = String;
  @Input() productTemplate: ProductTemplate;
  @Input() productTemplateListForAdd: ProductTemplateListForAdd;

  commerces: Commerce[];
  nbProductByCommerce: number[];
  currentCommerceIndex = 0;

  numberProductTemplate = 0;

  constructor(private buyerService: BuyerService,
              private infoBoxNotificationsService: InfoBoxNotificationsService
              ) { }

  ngOnInit() {

    this.commerces = [];
    this.nbProductByCommerce = [];

    let indexPt = -1;
    for (let i = 0; i < this.productTemplateListForAdd.productTemplates.length; i++) {
      if (this.productTemplateListForAdd.productTemplates[i].id === this.productTemplate.id) {
        indexPt = i;
        break;
      }
    }

    for (let i = 0; i < this.productTemplateListForAdd.listIndexPtEachCommerce.length; i++) {
      if (this.productTemplateListForAdd.listIndexPtEachCommerce[i] === indexPt){
        this.commerces.push(this.productTemplateListForAdd.commerces[i]);
        this.nbProductByCommerce.push(this.productTemplateListForAdd.listNbProductEachCommerce[i]);
      }
    }
  }

  addProduct() {

    this.buyerService.addProductToShoppingCart(this.productTemplate.id, this.commerces[this.currentCommerceIndex].id, this.numberProductTemplate).subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.infoBoxNotificationsService.addMessage('info', 'Ces produits ont été ajouté a votre panier.', 20);
          this.numberProductTemplate = 0;
        } else if (rrp.errorCode === 7) {
          this.infoBoxNotificationsService.addMessage('info', 'Seulement ' + rrp.data + ' produits ont été ajouté a votre panier.', 20);
        } else {
          this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'ajout des produits. : ' + rrp.errormessage, 10);
        }
      },
error => {
        console.log('Error occured', error);
        this.infoBoxNotificationsService.addMessage('error', 'Echec de l\'ajout des produits. : ' + error, 10);
      });
  }

}
