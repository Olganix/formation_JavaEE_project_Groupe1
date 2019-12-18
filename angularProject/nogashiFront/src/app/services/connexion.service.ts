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


  getUsers() {
    return this._http.get<RestResponse>(environment.nogashiRestUrl + '/getUsers').pipe(
      retry(3),
      map( (rrp: RestResponse) => {
        return new RestResponse(rrp);
      }));
  }

    /*
    signIn(name: string, password: string, email: string, role: string, newsletterEnabled: boolean) {
        return new Promise((resolve, reject) => {
            this._http.get<RestResponse>(environment.nogashiRestUrl + '/signin?name=' + name + '&password=' + password + '&email=' + email + '&role=' + role + '&newsletterEnabled=' + newsletterEnabled).subscribe(
            data => {
                if (('status' in data) && (data.status === 'SUCCESS')) {
                    resolve( true );
                } else {
                    reject('no data in returned object');
                }
            },
            error => {
              console.log('Error occured', error);
              reject(error);
            }
          );
        });
    }

    login(login: string, password: string) {
        return new Promise((resolve, reject) => {
            this._http.get<RestResponse>(environment.nogashiRestUrl + '/login?name=' + name + '&password=' + password).subscribe(
            data => {
                const status = ('status' in data) ? data.status : 'FAIL';
                const user = ('data' in data) ? data.data : null;

                console.log(status);

                if (data.status === 'SUCCESS') {
                    resolve( user );
                } else if (data.errorCode === 2) {
                    reject('Email not validate');
                } else {
                    reject('no data in returned object');
                }
            },
            error => {
              console.log('Error occured', error);
              reject(error);
            }
          );
        });
    }






    // Tests Todo remove :
    getUsers() {
        return new Promise((resolve, reject) => {
          this._http.get<RestResponse>(environment.nogashiRestUrl + '/getUsers').subscribe(
          data => {
            resolve(('data' in data) ? data.data : data);
          },
          error => {
            console.log('Error occured', error);
            reject(null);
          });
        });
    }
    */
  }
