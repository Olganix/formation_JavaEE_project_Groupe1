import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListProductsTemplatesComponent } from './list-products-templates.component';

describe('ListProductsTemplatesComponent', () => {
  let component: ListProductsTemplatesComponent;
  let fixture: ComponentFixture<ListProductsTemplatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListProductsTemplatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListProductsTemplatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
