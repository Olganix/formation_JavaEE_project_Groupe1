import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RestResponse} from '../classes/rest-response';
import {environment} from '../../environments/environment';
import {map, retry} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {Commerce} from '../classes/commerce';
import {ProductTemplate} from '../classes/product-template';
import {User} from '../classes/user';

@Injectable({
  providedIn: 'root'
})
export class MerchantService {

  lastCommerce: Commerce;                                   // commerce precedent pour eviter d'aller toujours charger des donénes d'une page a l'autre. Note : ce ne previent pas d'un rechargement de page, où il faut quand meme aller rechercher les données
  lastProductTemplate: ProductTemplate;                     // (De meme) DONC, il faut toujours comparer les id, a chaque utilisation.

  constructor(private _http: HttpClient
              ) { }




  getCommerces() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/merchant/commerces', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  addOrUpdateCommerce(c: Commerce): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/merchant/commerce/addOrUpdate', c.toHttpObject(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {

        const rrpTmp = new RestResponse(rrp);
        this.lastCommerce = (rrpTmp.status === 'SUCCESS') ? (new Commerce(rrpTmp.data)) : null;

        return rrpTmp;
      }));
  }


  getProductTemplates() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/merchant/productTemplates', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  addOrUpdateProductTemplate(pt: ProductTemplate) {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/merchant/productTemplate/addOrUpdate', pt.toHttpObject(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {

        const rrpTmp = new RestResponse(rrp);
        this.lastProductTemplate = (rrpTmp.status === 'SUCCESS') ? (new ProductTemplate(rrpTmp.data)) : null;

        return rrpTmp;
      }));
  }

}
