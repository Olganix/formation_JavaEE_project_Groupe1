import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandMerchantViewComponent } from './command-merchant-view.component';

describe('CommandMerchantViewComponent', () => {
  let component: CommandMerchantViewComponent;
  let fixture: ComponentFixture<CommandMerchantViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommandMerchantViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommandMerchantViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
