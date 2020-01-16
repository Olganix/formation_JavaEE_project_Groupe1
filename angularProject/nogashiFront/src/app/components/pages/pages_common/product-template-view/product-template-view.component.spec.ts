import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductTemplateViewComponent } from './product-template-view.component';

describe('ProductTemplateViewComponent', () => {
  let component: ProductTemplateViewComponent;
  let fixture: ComponentFixture<ProductTemplateViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductTemplateViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductTemplateViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
