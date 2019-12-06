       //MAP

class Map {
  constructor() {
        this.mapTile = null;
      
        this.iconeMap = L.icon({  //Icone Open
           iconUrl: 'css/img/map/marker.png',  //Image Icone
           shadowUrl: 'css/img/map/ombre-marqueur.png', //Ombre Icone
            
           iconSize: [40, 57], //Taille icone  [largeur, hauteur]
           iconAnchor: [50, 20], // Position icone
            
           popupAnchor:  [-12, 0], //Position Popup
        })
        
        this.carte();
        this.addMarker();
} //Fin Constructor




   //OPENSTREETMAP
   carte() {
       this.mapTile = L.map('mapid').setView([50.63609, 3.06387], 13);
            L.tileLayer('https://stamen-tiles-{s}.a.ssl.fastly.net/toner-lite/{z}/{x}/{y}{r}.{ext}', {
                attribution: 'Map tiles by <a href="http://stamen.com">Stamen Design</a>, <a href="http://creativecommons.org/licenses/by/3.0">CC BY 3.0</a> &mdash; Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
                subdomains: 'abcd',
                ext: 'png'
        }).addTo(this.mapTile);

        this.addMarker(); //Ajout du marker sur la map avec options.
   }//Fin carte()
    
   addMarker() {
       let marker = L.marker([50.73845, 3.18781]).addTo(this.mapTile).setIcon(this.iconeMap).bindPopup("Lille, 59000");   
   }//Fin addMarker()   
    
    
};//Fin Class Map