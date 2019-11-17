export class AppareilService
{
    appareils = 
    [
        {
          id: 1,
          name: 'Machine à laver',
          status: 'éteint'
        },
        {
          id: 2,
          name: 'Frigo',
          status: 'allumé'
        },
        {
          id: 3,
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

    getAppareilById(id: number) 
    {
        const appareil = this.appareils.find((s) => 
        {
            return s.id === id;
        });
        return appareil;
    }
}