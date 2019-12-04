import { HttpClient } from '@angular/common/http';

export class ConnexionService 
{
    isAuth = false;
    readonly APP_URL = 'http://localhost:8080/nogashi';

    constructor( private _http: HttpClient)
    {

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
  }