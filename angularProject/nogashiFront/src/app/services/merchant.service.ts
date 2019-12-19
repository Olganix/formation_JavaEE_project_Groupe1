import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RestResponse} from '../classes/rest-response';
import {environment} from '../../environments/environment';
import {map, retry} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {Commerce} from '../classes/commerce';

@Injectable({
  providedIn: 'root'
})
export class MerchantService {

  constructor(private _http: HttpClient
              ) { }




  getCommerces() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getCommerces', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  addCommerce(commerce: Commerce): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/addCommerce', commerce.toHttpObject(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }


}
