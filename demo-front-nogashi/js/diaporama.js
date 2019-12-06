                      //DIAPORAMA 

class Diaporama {
  constructor(imagesDiapo, textesDiapo) {
    this.imagesDiapo = document.getElementById("image");
    this.textesDiapo = document.getElementById("slogan"); 
    this.numeroDiapo = document.getElementById("numero"); 
 
    this.boutonPlay = document.getElementById("play");
    this.boutonPause = document.getElementById("pause"); 
    this.arrowLeft = document.getElementById("arrow-left");
    this.arrowRight = document.getElementById("arrow-right"); 
    
    this.index = 0;
    this.time = 5000;
    this.image = imagesDiapo;
    this.texte = textesDiapo;
    
    this.imageDiapo.src = this.image[this.index];
    this.texteDiapo.innerHTML = this.texte[this.index];
    this.numeroDiapo.innerHTML = this.index +1; 
    
    this.automatique();
    this.pause();
    this.play();  
    this.listener();  
}// Fin du constructor
    
  
automatique() {
     this.slide = setInterval( () => {
          window.addEventListener('load', this.flecheDroite())
     }, this.time);
  }//Fin automatique() 
  
  
  pause() {
     this.boutonPause.addEventListener("click", (e) => {
          clearInterval(this.slide);
          this.boutonPause.style.visibility = "hidden";
          this.boutonPlay.style.visibility = "visible";
     }); //Fin clic boutonPause() 
  }//Fin pause() 
    
  
  play() {
     this.boutonPlay.addEventListener("click", (e) => {
          this.playing = true; 
          this.automatique();
          this.boutonPause.style.visibility = "visible";
          this.boutonPlay.style.visibility = "hidden";
     }); //Fin clic boutonPlay() 
  }//Fin play()   
    
  

  flecheDroite() {
    this.index++;
    
    if (this.index > (this.image.length) - 1) {
      this.index = 0;
    }
      this.imageDiapo.src = this.image[this.index];
      this.texteDiapo.innerHTML = this.texte[this.index];
      this.numeroDiapo.innerHTML = this.index +1;
    }//Fin flecheDroite()

  
  flecheGauche() {
    this.index--;
      
    if (this.index < 0) {
      this.index = (this.image.length) - 1;
    }
      this.imageDiapo.src = this.image[this.index];
      this.texteDiapo.innerHTML = this.texte[this.index];
      this.numeroDiapo.innerHTML = this.index +1;
    }//Fin flecheGauche()

  
  clavier(e) {
    const code = e.keyCode;
    
    switch (code) {
      case 39:  //Fleche Droite (clavier)
        this.flecheDroite();
        break;
      case 37:  //Fleche Gauche (clavier)
        this.flecheGauche();
        break;
    }//fin switch
  }//Fin clavier()

  
  listener() {
    this.arrowRight.addEventListener("click", () => {
      this.flecheDroite();
    });

    this.arrowLeft.addEventListener("click", () => {
      this.flecheGauche();
    });

    document.addEventListener("keydown", (e) => {
      this.clavier(e);
    });
   }// Fin Listener()


};//Fin Class



const images = [];
const textes = [];

 


        //TABLEAU IMAGES
images[0] = "../css/img/diaporama/boulangerie.jpg";
images[1] = "../css/img/diaporama/boulangerie2.jpg";
images[2] = "../css/img/diaporama/boulangerie3.jpg";
images[3] = "../css/img/diaporama/boulangerie.jpg";
images[4] = "../css/img/diaporama/boulangerie2.jpg";
images[5] = "../css/img/diaporama/boulangerie3.jpg";


      //TABLEAU TEXTES
textes[0] = "Cliquez sur le marqueur de la station.";
textes[1] = "Vérifiez le nombre de vélos.";
textes[2] = "Inscrivez votre nom et votre prénom.";
textes[3] = "Cliquez sur réserver.";
textes[4] = "Signez puis valider pour confirmer.";
textes[5] = "Allez à la station pour récupérer votre vélo.";

 



