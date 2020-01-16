import {Component, Input, OnInit} from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';

@Component({
  selector: 'app-product-template-merchant-view',
  templateUrl: './product-template-merchant-view.component.html',
  styleUrls: ['./product-template-merchant-view.component.scss']
})
export class ProductTemplateMerchantViewComponent implements OnInit {

  @Input() productTemplate: ProductTemplate;

  constructor() { }

  ngOnInit() {
  }

}
