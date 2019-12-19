import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RestResponse} from '../classes/rest-response';
import {environment} from '../../environments/environment';
import {map, retry} from 'rxjs/operators';

@Injectable()
export class ListingService {

  constructor(private _http: HttpClient) { }

  getProductsTemplates() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getProductsTemplates').pipe(
      retry(3),
      map((rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));

  }
}
