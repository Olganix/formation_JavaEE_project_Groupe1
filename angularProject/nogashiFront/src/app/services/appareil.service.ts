import { Subject } from 'rxjs/Subject';


export class AppareilService
{
    appareilsSubject = new Subject<any[]>();


    private appareils = 
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

    emitAppareilSubject() 
    {
      this.appareilsSubject.next(this.appareils.slice());
    }

    switchAppareil(i:number, enable: boolean) 
    {
        this.appareils[i].status = enable ? 'allumé' : 'éteint';
        this.emitAppareilSubject();
    }

    switchAllAppareil(enable: boolean) 
    {
        for(let appareil of this.appareils)
            appareil.status = enable ? 'allumé' : 'éteint';
        
        this.emitAppareilSubject();
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