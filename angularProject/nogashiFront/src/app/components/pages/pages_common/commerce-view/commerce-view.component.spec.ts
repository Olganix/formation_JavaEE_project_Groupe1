import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommerceViewComponent } from './commerce-view.component';

describe('CommerceViewComponent', () => {
  let component: CommerceViewComponent;
  let fixture: ComponentFixture<CommerceViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommerceViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommerceViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
