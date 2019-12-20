import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComptePreferencesComponent } from './compte-preferences.component';

describe('ComptePreferencesComponent', () => {
  let component: ComptePreferencesComponent;
  let fixture: ComponentFixture<ComptePreferencesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComptePreferencesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComptePreferencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
