import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestNewComponentComponent } from './test-new-component.component';

describe('TestNewComponentComponent', () => {
  let component: TestNewComponentComponent;
  let fixture: ComponentFixture<TestNewComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestNewComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestNewComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
