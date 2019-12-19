import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompteCommandesComponent } from './compte-commandes.component';

describe('CompteCommandesComponent', () => {
  let component: CompteCommandesComponent;
  let fixture: ComponentFixture<CompteCommandesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompteCommandesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompteCommandesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
