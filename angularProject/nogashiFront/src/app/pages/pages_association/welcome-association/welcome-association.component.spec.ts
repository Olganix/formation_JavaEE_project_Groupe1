import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WelcomeAssociationComponent } from './welcome-association.component';

describe('WelcomeAssociationComponent', () => {
  let component: WelcomeAssociationComponent;
  let fixture: ComponentFixture<WelcomeAssociationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WelcomeAssociationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WelcomeAssociationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
