import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../classes/user';
import {RestResponse} from '../classes/rest-response';
import {Observable} from 'rxjs';
import {map, retry} from 'rxjs/operators';


// info pb perte de sessions avec Angular : https://stackoverflow.com/questions/43773762/session-http-spring-and-angular-2  https://weblog.west-wind.com/posts/2019/Apr/07/Creating-a-custom-HttpInterceptor-to-handle-withCredentials

@Injectable()
export class ConnexionService {

  private static AUTH_KEY = 'authentification';
  private connectedUser: User = null;

  constructor(private _http: HttpClient) {

  }



  signIn(user: User): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/signin', user.toHttpObject_signin(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  emailValidation(token: string): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/emailvalidation', token, { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  sendEmailValidation(user: User): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/sendemailvalidation', user.email, { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  login(user: User): Observable<RestResponse> {

    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/login', user.toHttpObject_login(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);
        this.connectedUser = (rrpTmp.status === 'SUCCESS') ? (new User(rrpTmp.data)) : null;
        return rrpTmp;
      }));
  }

  logout(): Observable<RestResponse> {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/logout', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        this.connectedUser = null;                                // Same if it's a fail (because is not connected), the result is the same.
        return new RestResponse(rrp);
      }));
  }

  isLoged() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/isloged', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);
        this.connectedUser = (rrpTmp.status === 'SUCCESS') ? (new User(rrpTmp.data)) : null;
        return rrpTmp;
      }));
  }


  passwordRescue(user: User): Observable<RestResponse> {

    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/passwordRescue', user.email, { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  passwordRescueModification(user: User): Observable<RestResponse> {

    console.log('service.passwordRescueModification()');
    console.log(user.toHttpObject_passwordRescueModification());

    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/passwordRescueModification', user.toHttpObject_passwordRescueModification(), { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }



  getUsers() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getUsers', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }
  getMerchants() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getMerchants', { withCredentials: true }).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }
  getMerchantCommerces() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getCommerces', { withCredentials: true }).pipe(
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
  getLocalConnectedRole() {
    return (this.connectedUser != null) ? this.connectedUser.role : null;
  }






}
