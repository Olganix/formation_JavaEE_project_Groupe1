class Main2 {
    constructor() {
        this.ajax = new Ajax(); //Instance Ajax()
        //this.menuApp = new MenuApp(); //Instance MenuApp()
        this.map = new Map(); //Instance Map()
        this.bouton = new Bouton(); //Instance Bouton()
    }//Fin constructor 
};//Fin class Main

let app = null;

window.onload = () => { //Chargement de l'instance Main au lancement du site
  app = new Main2();
}; 