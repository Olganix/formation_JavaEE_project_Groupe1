import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from '../../environments/environment';
import {User} from '../classes/user';
import {RestResponse} from '../classes/rest-response';
import {Observable} from 'rxjs';
import {map, retry} from 'rxjs/operators';

@Injectable()
export class ConnexionService {

  private static AUTH_KEY = 'authentification';
  private connectedUser: User = null;

  constructor(private _http: HttpClient) {

  }



  signIn(user: User): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/signin', user.toHttpObject_signin()).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  emailValidation(token: string): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/emailvalidation', token).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  sendEmailValidation(user: User): Observable<RestResponse> {
    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/sendemailvalidation', user.email).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

  login(user: User): Observable<RestResponse> {

    console.log('service login:');
    console.log(user.toHttpObject_login());

    return this._http.post<RestResponse>(environment.nogashiRestUrl + '/login', user.toHttpObject_login()).pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);
        this.connectedUser = (rrpTmp.status === 'SUCCESS') ? (new User(rrpTmp.data)) : null;
        return rrpTmp;
      }));
  }

  logout(): Observable<RestResponse> {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/logout').pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        this.connectedUser = null;                                // Same if it's a fail (because is not connected), the result is the same.
        return new RestResponse(rrp);
      }));
  }

  isLoged() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/isloged').pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        const rrpTmp = new RestResponse(rrp);
        this.connectedUser = (rrpTmp.status === 'SUCCESS') ? (new User(rrpTmp.data)) : null;
        return rrpTmp;
      }));
  }


  getUsers() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getUsers').pipe(
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






}
