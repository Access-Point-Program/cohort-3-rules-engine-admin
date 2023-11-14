import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoveDownButtonComponent } from './move-down-button.component';

describe('MoveDownButtonComponent', () => {
  let component: MoveDownButtonComponent;
  let fixture: ComponentFixture<MoveDownButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoveDownButtonComponent]
    });
    fixture = TestBed.createComponent(MoveDownButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
