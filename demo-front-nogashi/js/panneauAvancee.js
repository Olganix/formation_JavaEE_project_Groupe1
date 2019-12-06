                      //PANNEAU RECHERCHE AVANCEE

class PanneauAvancee {
  constructor() {
    this.boutonRecherche = document.getElementById("titre-avancee");
    this.regimeAlimentaire = document.getElementById("bloc-filtre-regime");
    this.allergene = document.getElementById("bloc-filtre-allergene");  
    this.angleHaut = document.getElementById("fa-angle-up");
    this.angleBas = document.getElementById("fa-angle-down"); 
      
    this.time = 500;

}// Fin du constructor
  

    ouverturePanneau() {
        this.angleHaut.addEventListener("click", () => {
            
            setInterval( () => {
                this.regimeAlimentaire.style.visibility = "visible";   
                this.allergene.style.visibility = "visible";
            }, this.time);
            
            this.angleBas.style.visibility = "visible";
            this.angleHaut.style.visibility = "hidden";
        }); //Fin ecoute boutonRecherche
     } //Fin ecoute ouverturePanneau()
    
} //Class PanneauAvancee
    