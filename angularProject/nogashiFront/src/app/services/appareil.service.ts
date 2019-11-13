export class AppareilService
{
    appareils = [
        {
          name: 'Machine à laver',
          status: 'éteint'
        },
        {
          name: 'Frigo',
          status: 'allumé'
        },
        {
          name: 'Ordinateur',
          status: 'éteint'
        }
      ];


    switchAppareil(i:number, enable: boolean) 
    {
        this.appareils[i].status = enable ? 'allumé' : 'éteint';
    }

    switchAllAppareil(enable: boolean) 
    {
        for(let appareil of this.appareils)
            appareil.status = enable ? 'allumé' : 'éteint';
    }

    
}