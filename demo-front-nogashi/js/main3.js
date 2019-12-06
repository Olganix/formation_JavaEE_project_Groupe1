class Mainf {
    constructor() {
        this.panneauAvancee = new PanneauAvancee();  //Instance PanneauAvancee()
        this.bouton = new Bouton(); //Instance Bouton()
    }//Fin constructor 
};//Fin class Main

let app = null;

window.onload = () => { //Chargement de l'instance Main au lancement du site
  app = new Mainf();
}; 