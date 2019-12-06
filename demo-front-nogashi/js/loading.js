                   // Loading Page 


//Ajout Ecran de chargement
$(function() {
    $('body').append('<div class="container col-12 no-gutters"><div id="bloc-loading" class="bloc-loading"><div class="lds-ripple"><div></div><div></div></div><p class="text-loading">Chargement...</p></div></div>');
});

//Arret Ecran Au Chargement
$(windows).on('load', function() {
    
    $('#bloc-loading').fadeOut('slow');
});