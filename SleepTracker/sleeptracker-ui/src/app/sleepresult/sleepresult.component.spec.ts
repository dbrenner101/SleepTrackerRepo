import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SleepresultComponent } from './sleepresult.component';

describe('SleepresultComponent', () => {
  let component: SleepresultComponent;
  let fixture: ComponentFixture<SleepresultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SleepresultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SleepresultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
