import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/interval';
import { Subscription } from 'rxjs/Subscription';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent implements OnInit 
{

  displayTest:boolean = false;

  title = 'NoGashi';

  secondes: number;
  counterSubscription: Subscription;

  constructor() { }

  ngOnInit() 
  {
    const counter = Observable.interval(1000);
    
    this.counterSubscription = counter.subscribe(
      (value) => 
      {
        this.secondes = value;
      },
      (error) => 
      {
        console.log('Uh-oh, an error occurred! : ' + error);
      },
      () => 
      {
        console.log('Observable complete!');
      }
    );
  }

  ngOnDestroy() {
    this.counterSubscription.unsubscribe();
  }

  toggleTestdisplay()
  {
    this.displayTest = ! this.displayTest;
  }

}
