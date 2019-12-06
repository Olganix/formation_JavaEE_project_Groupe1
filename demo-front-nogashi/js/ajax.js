              //FONCTION ajaxGET

class Ajax {
    constructor() {
       this.req = new XMLHttpRequest();
}//Fin constructor()
       
         ajaxGet(url, callback) {
             this.req.open("GET", url);
             
             this.req.addEventListener("load", () => {
                if (this.req.status >= 200 && this.req.status < 400) {
                  callback(JSON.parse(this.req.responseText));
                } else {
                  console.error(this.req.status + " " + this.req.statusText + " " + url);
                }
              });
             
             this.req.addEventListener("error", function () {
             console.error("Erreur réseau avec l'URL " + url);
             });
             this.req.send(null);
      }//Fin ajaxGet()
}//Fin class Ajax