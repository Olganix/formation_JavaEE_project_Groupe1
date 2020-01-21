import {SchedulerWeekType} from '../enum/scheduler-week-type.enum';
import {SchedulerDay} from './scheduler-day';
import {ProductTemplate} from './product-template';
import {Commerce} from './commerce';


export class ProductTemplateListForAdd {

  productTemplates: ProductTemplate[];
  commerces: Commerce[];
  listIndexPtEachCommerce: number[];
  listNbProductEachCommerce: number[];

  constructor(obj?: object) {
    if (obj !== null) {
      Object.assign(this, obj);
    }


    for (let i = 0 ; i < this.productTemplates.length; i++) {
      this.productTemplates[i] = new ProductTemplate(this.productTemplates[i]);
    }

    for (let i = 0 ; i < this.commerces.length; i++) {
      this.commerces[i] = new Commerce(this.commerces[i]);
    }
  }
}
