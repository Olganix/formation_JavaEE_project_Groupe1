import {Component, Input, OnInit} from '@angular/core';
import {ProductTemplate} from '../../../../classes/product-template';

@Component({
  selector: 'app-product-template-view',
  templateUrl: './product-template-view.component.html',
  styleUrls: ['./product-template-view.component.scss']
})
export class ProductTemplateViewComponent implements OnInit {

  @Input() productTemplate: ProductTemplate;

  constructor() { }

  ngOnInit() {
  }

}
