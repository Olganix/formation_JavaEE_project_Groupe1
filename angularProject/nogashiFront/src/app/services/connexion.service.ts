import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class ConnexionService 
{
    isAuth = false;
    readonly APP_URL = 'http://localhost:8080/nogashi';

    constructor(private _http: HttpClient)
    {

    }

    
    

    
    signIn(name: String, password : String, email : String, role: String, newsletterEnabled: boolean)
    {
        return new Promise((resolve, reject) => 
        {
            this._http.get(this.APP_URL + '/signin?name='+ name +"&password="+ password +"&email="+ email + "&role="+ role + "&newsletterEnabled="+ newsletterEnabled).subscribe(
            data => 
            {
                console.log(data["status"]);
                
                if(("status" in data) && (data["status"] == "SUCCESS")) 
                    resolve( true );
                else
                    reject("no data in returned object");
            },
            error => 
            {
              console.log('Error occured', error);
              reject(error);
            }
          );
        });
    }

    login(login: String, password : String)
    {
        return new Promise((resolve, reject) => 
        {
            this._http.get(this.APP_URL + '/login?name='+ name +"&password="+ password).subscribe(
            data => 
            {
                let status = ("status" in data) ? data["status"] : "FAIL";
                let user = ("data" in data) ? data["data"] : null;

                console.log(status);
                
                if(data["status"] == "SUCCESS") 
                    resolve( user );
                else if(data["errorCode"] === 2) 
                    reject("Email not validate");
                else
                    reject("no data in returned object");
            },
            error => 
            {
              console.log('Error occured', error);
              reject(error);
            }
          );
        });
    }
    

   


    //Tests Todo remove : 
    getUsers()
    {
        return new Promise((resolve, reject) => 
        {
          this._http.get(this.APP_URL + '/getUsers').subscribe(
          data => 
          {
            resolve(("data" in data) ? data["data"] : data);
          },
          error => 
          {
            console.log('Error occured', error);
            reject(null);
          });
        });
    }
    

    signIn_fake()
    {

        return new Promise((resolve, reject) => 
        {
          setTimeout(()=>
          {
            this.isAuth = true;                 //simulation connexion aux serveur pour l'authentification.
            resolve(true);
          }, 1500);
        });
    }
    signOut() 
    {
        this.isAuth = false;
    }
  }