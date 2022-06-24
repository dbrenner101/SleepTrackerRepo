import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSleepLogComponent } from './new-sleep-log.component';

describe('NewSleepLogComponent', () => {
  let component: NewSleepLogComponent;
  let fixture: ComponentFixture<NewSleepLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewSleepLogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSleepLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
