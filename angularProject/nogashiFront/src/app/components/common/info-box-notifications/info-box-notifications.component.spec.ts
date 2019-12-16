import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoBoxNotificationsComponent } from './info-box-notifications.component';

describe('InfoBoxNotificationsComponent', () => {
  let component: InfoBoxNotificationsComponent;
  let fixture: ComponentFixture<InfoBoxNotificationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfoBoxNotificationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoBoxNotificationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
