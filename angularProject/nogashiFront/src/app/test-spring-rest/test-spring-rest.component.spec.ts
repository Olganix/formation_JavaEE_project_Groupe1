import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestSpringRestComponent } from './test-spring-rest.component';

describe('TestSpringRestComponent', () => {
  let component: TestSpringRestComponent;
  let fixture: ComponentFixture<TestSpringRestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestSpringRestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestSpringRestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
