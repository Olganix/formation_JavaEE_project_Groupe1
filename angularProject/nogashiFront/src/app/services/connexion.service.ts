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
    

    
    signIn(name: String, password : String, email : String)
    {

        return new Promise((resolve, reject) => 
        {
            this._http.get(this.APP_URL + '/signin?name='+ name +"password="+ password +"&email="+ email).subscribe(
            data => 
            {
                console.log(data["status"]);
                
                if(("status" in data) && (data["status"] == "success")) 
                    resolve( true );
                else
                    reject(false);
            },
            error => 
            {
              console.log('Error occured', error);
              reject(false);
            }
          );
        });
    }

    signOut() 
    {
        this.isAuth = false;
    }


    
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
    
  }