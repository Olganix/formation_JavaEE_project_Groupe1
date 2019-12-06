class panneauFaq {
    consctructor() {
        this.titreFaq = document.getElementById("question");
        this.reponseFaq = document.getElementById("reponse");
        
        
        
    }//Fin constructor


    apparitionPanneau() {
        this.titreFaq.addEventListener("click", () => {
        
            this.reponseFaq.classList.toggle('is-visible');
         }); //Fin ecoute titreFaq
     } //Fin ecoute ouverturePanneau()







} //Fin class panneauFaq