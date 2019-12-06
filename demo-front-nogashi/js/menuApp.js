           //MENU

class MenuApp {
    constructor() {
       this.blocMenu = document.getElementById("bloc-menu");
       this.hamburgerMenu = document.getElementById("hamburger-menu"); 
       this.blocQuitter = document.getElementById("bloc-quitter");  
       
       this.ouvrirMenu();
       this.fermerMenu(); 
}//Fin constructor MenuApp


    ouvrirMenu() {
       this.blocMenu = document.getElementById("bloc-menu");
       this.hamburgerMenu = document.getElementById("hamburger-menu"); 
       this.blocQuitter = document.getElementById("bloc-quitter");            
       
       this.hamburgerMenu.addEventListener("click", () => {
           this.blocMenu.style.visibility = "visible";
       }) 
    }//Fin ouvrirMenu()
    
    fermerMenu() {
       this.blocMenu = document.getElementById("bloc-menu");
       this.hamburgerMenu = document.getElementById("hamburger-menu"); 
       this.blocQuitter = document.getElementById("bloc-quitter");  
       
       this.blocQuitter.addEventListener("click", () => {
          this.blocMenu.style.visibility = "hidden"; 
       }) 
    }//Fin fermerMenu()

};//Fin class MenuApp