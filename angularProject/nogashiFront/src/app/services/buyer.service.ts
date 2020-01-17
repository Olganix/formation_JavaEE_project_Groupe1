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

  constructor(private _http: HttpClient) { }




}
