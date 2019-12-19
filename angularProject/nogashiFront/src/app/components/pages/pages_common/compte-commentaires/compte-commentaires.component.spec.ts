import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompteCommentairesComponent } from './compte-commentaires.component';

describe('CompteCommentairesComponent', () => {
  let component: CompteCommentairesComponent;
  let fixture: ComponentFixture<CompteCommentairesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompteCommentairesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompteCommentairesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
