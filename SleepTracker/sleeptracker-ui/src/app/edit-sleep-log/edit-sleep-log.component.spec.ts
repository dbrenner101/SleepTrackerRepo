import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSleepLogComponent } from './edit-sleep-log.component';

describe('EditSleepLogComponent', () => {
  let component: EditSleepLogComponent;
  let fixture: ComponentFixture<EditSleepLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditSleepLogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSleepLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
