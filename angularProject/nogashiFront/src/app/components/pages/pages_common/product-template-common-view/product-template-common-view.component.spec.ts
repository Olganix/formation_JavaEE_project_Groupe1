import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductTemplateCommonViewComponent } from './product-template-common-view.component';

describe('ProductTemplateCommonViewComponent', () => {
  let component: ProductTemplateCommonViewComponent;
  let fixture: ComponentFixture<ProductTemplateCommonViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductTemplateCommonViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductTemplateCommonViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
