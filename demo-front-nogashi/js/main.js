class Main {
    constructor() {
        //this.panneauFaq = new PanneauFaq(); //Instance PanneauFaq()
        this.menuApp = new MenuApp(); //Instance MenuApp()
        this.diaporama = new Diaporama(); //Instance Diaporama()
        this.bouton = new Bouton(); //Instance Bouton()
        //this.map = new Map(); //Instance Map();
    }//Fin constructor 
};//Fin class Main

let app = null;

window.onload = () => { //Chargement de l'instance Main au lancement du site
  app = new Main();
}; 