import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommerceSheetComponent } from './commerce-sheet.component';

describe('CommerceSheetComponent', () => {
  let component: CommerceSheetComponent;
  let fixture: ComponentFixture<CommerceSheetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommerceSheetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommerceSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
