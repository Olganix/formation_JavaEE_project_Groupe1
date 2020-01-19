import {Injectable} from '@angular/core';
import {RestResponse} from '../classes/rest-response';
import {environment} from '../../environments/environment';
import {map, retry} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Commerce} from '../classes/commerce';
import {MerchantService} from './merchant.service';
import {ConnexionService} from './connexion.service';
import {UserRole} from '../enum/user-role.enum';
import {ProductTemplate} from '../classes/product-template';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private _http: HttpClient,
              private merchantService: MerchantService,
              private connexionService: ConnexionService
            ) { }


  getProductTemplateById(id) {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/user/productTemplate/' + id.toString(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);

        if (this.connexionService.getLocalConnectedRole() === UserRole.MERCHANT) {
          this.merchantService.lastProductTemplate = (rrpTmp.status === 'SUCCESS') ? (new ProductTemplate(rrpTmp.data)) : null;
        }

        return rrpTmp;
      }));
  }

  getCommerceById(id) {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/user/commerce/' + id.toString(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);

        if (this.connexionService.getLocalConnectedRole() === UserRole.MERCHANT) {
          this.merchantService.lastCommerce = (rrpTmp.status === 'SUCCESS') ? (new Commerce(rrpTmp.data)) : null;
        }

        return rrpTmp;
      }));
  }
}
