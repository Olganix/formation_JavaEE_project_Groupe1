import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WelcomeIndividualComponent } from './welcome-individual.component';

describe('WelcomeIndividualComponent', () => {
  let component: WelcomeIndividualComponent;
  let fixture: ComponentFixture<WelcomeIndividualComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WelcomeIndividualComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomeIndividualComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
