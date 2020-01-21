import { Injectable } from '@angular/core';
import {RestResponse} from '../classes/rest-response';
import {environment} from '../../environments/environment';
import {map, retry} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {ProductTemplate} from '../classes/product-template';

@Injectable({
  providedIn: 'root'
})
export class BuyerService {

  constructor(private _http: HttpClient
              ) { }



  getProductTemplates() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/buyer/productTemplates', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  getProductTemplatesForAdd() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/buyer/productTemplatesForAdd', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  getCommerceByProductTemplate(id: number) {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/buyer/productTemplate/' + id + '/commerces', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }
  getProductByCommerce(id: number) {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/buyer/commerce/' + id + '/products', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }


  getProductsByName(name) {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/buyer/products/' + name, { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  getShoppingCart() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/buyer/shoppingCart', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }


  addProductToShoppingCart(id_pt: number, id_c: number, numberProductTemplate: number) {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/buyer/cart/add/' + id_pt + '/' + id_c + '/' + numberProductTemplate, { withCredentials: true }).pipe(
    retry(3),
    map( (rrp: RestResponse) => {
      return new RestResponse(rrp);
    }));
  }

}
