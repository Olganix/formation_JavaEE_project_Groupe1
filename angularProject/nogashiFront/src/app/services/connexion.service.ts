export class ConnexionService 
{
    isAuth = false;

    signIn() 
    {

        return new Promise((resolve, reject) => 
        {
            setTimeout(() => 
            {
                this.isAuth = true;                 //simulation connexion aux serveur pour l'authentification.
                resolve(true);
            }, 2000);
        });
    }

    signOut() 
    {
        this.isAuth = false;
    }
  }