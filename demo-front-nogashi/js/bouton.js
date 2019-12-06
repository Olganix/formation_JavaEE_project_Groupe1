           //BOUTON 

class Bouton {
    constructor() {
       this.boutonUp = document.getElementById("bouton-up"); 
       this.boutonHaut();    
    }//Fin constructor bouton


    boutonHaut() {
       this.boutonUp.addEventListener("click", () => {
           window.scrollTo({
               behavior: "smooth",
               top: 0,
           });  
        }) 
    }//Fin boutonHaut()
};//Fin class Bouton