import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../classes/user';
import {RestResponse} from '../classes/rest-response';
import {Observable} from 'rxjs';
import {map, retry} from 'rxjs/operators';
import {ProductTemplate} from '../classes/product-template';
import {UserRole} from '../enum/user-role.enum';


// info pb perte de sessions avec Angular : https://stackoverflow.com/questions/43773762/session-http-spring-and-angular-2  https://weblog.west-wind.com/posts/2019/Apr/07/Creating-a-custom-HttpInterceptor-to-handle-withCredentials

@Injectable()
export class ConnexionService {

  private connectedUser: User = null;

  constructor(private _http: HttpClient
              ) {}



  signIn(user: User): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/user/signin', user.toHttpObject_signin(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  emailValidation(token: string): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/user/emailValidation', token, { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  sendEmailValidation(user: User): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/user/sendEmailValidation', user.email, { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  login(user: User): Observable<RestResponse> {

    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/user/login', user.toHttpObject_login(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);
        this.connectedUser = (rrpTmp.status === 'SUCCESS') ? (new User(rrpTmp.data)) : null;
        return rrpTmp;
      }));
  }

  logout(): Observable<RestResponse> {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/user/logout', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        this.connectedUser = null;                                // Same if it's a fail (because is not connected), the result is the same.
        return new RestResponse(rrp);
      }));
  }

  isLoged() {

    if ( (localStorage) && (localStorage.getItem('connectedUser'))) { // Todo supprimer car pas tres secure. cf probleme de switch de page
      this.connectedUser = new User( JSON.parse( localStorage.getItem('connectedUser') ));
    }

    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/user/isLogged', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);
        this.connectedUser = (rrpTmp.status === 'SUCCESS') ? (new User(rrpTmp.data)) : null;

        if (localStorage) {           // Todo supprimer car pas tres secure. cf probleme de switch de page
          localStorage.setItem('connectedUser', JSON.stringify(this.connectedUser));
        }

        return rrpTmp;
      }));
  }


  passwordRescue(user: User): Observable<RestResponse> {

    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/user/passwordRescue', user.email, { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  passwordRescueModification(user: User): Observable<RestResponse> {

    console.log('service.passwordRescueModification()');
    console.log(user.toHttpObject_passwordRescueModification());

    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/user/passwordRescueModification', user.toHttpObject_passwordRescueModification(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }



  getUsers() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/user/getUsers', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }
  getMerchants() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/user/getMerchants', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }





  checkRemoteConnected() {

    this.isLoged().subscribe(
      (rrp: RestResponse) => {
        console.log('checkRemoteConnected: isLoged');
        console.log(rrp);
      },
      error => {
        console.log('Error occured', error);
      });
  }
  isLocalConnected() {
    return (this.connectedUser != null);
  }
  getLocalConnectedName() {
    return (this.connectedUser != null) ? this.connectedUser.name : null;
  }
  getLocalConnectedRole() {
    return (this.connectedUser != null) ? this.connectedUser.role : null;
  }



  getProductsTemplates() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/merchant/getProductsTemplates', { withCredentials: true }).pipe(
      retry(3),
      map((rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  addProductTemplate(productTemplate: ProductTemplate): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/merchant/addProductTemplate', productTemplate.toHttpObject() , { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }



}
