import {Component, Input, OnInit} from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';

@Component({
  selector: 'app-product-template-common-view',
  templateUrl: './product-template-common-view.component.html',
  styleUrls: ['./product-template-common-view.component.scss']
})
export class ProductTemplateCommonViewComponent implements OnInit {

  @Input() productTemplate: ProductTemplate;

  constructor() { }

  ngOnInit() {
  }

}
