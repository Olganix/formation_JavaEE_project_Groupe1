import { Component, OnInit } from '@angular/core';
import {RestResponse} from '../../../../classes/rest-response';
import {environment} from '../../../../../environments/environment';
import {map, retry} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-list-products-templates',
  templateUrl: './list-products-templates.component.html',
  styleUrls: ['./list-products-templates.component.scss']
})
export class ListProductsTemplatesComponent implements OnInit {

  listProductsTemplates: any;

  constructor(private _http: HttpClient) {

  }

  ngOnInit() {

    this.getProductsTemplates().subscribe(
      (rrp: RestResponse) => {

        if (rrp.status === 'SUCCESS') {
          this.listProductsTemplates = rrp.data;
        } else {
          console.log('Echec de la recuperation de la liste des fiches produits : ' + rrp.errormessage);
        }
      },
      error => {
        console.log('Echec de la recuperation de la liste des fiches produits : ', error);
      });
  }


  getProductsTemplates() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getProductsTemplates').pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

}
