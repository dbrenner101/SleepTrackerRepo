import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SleepconditionsComponent } from './sleepconditions.component';

describe('SleepconditionsComponent', () => {
  let component: SleepconditionsComponent;
  let fixture: ComponentFixture<SleepconditionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SleepconditionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SleepconditionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
