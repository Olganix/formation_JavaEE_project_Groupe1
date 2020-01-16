import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandListMerchantComponent } from './command-list-merchant.component';

describe('CommandListMerchantComponent', () => {
  let component: CommandListMerchantComponent;
  let fixture: ComponentFixture<CommandListMerchantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommandListMerchantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommandListMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
