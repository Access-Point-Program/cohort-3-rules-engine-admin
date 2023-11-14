import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoveUpButtonComponent } from './move-up-button.component';

describe('MoveUpButtonComponent', () => {
  let component: MoveUpButtonComponent;
  let fixture: ComponentFixture<MoveUpButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MoveUpButtonComponent]
    });
    fixture = TestBed.createComponent(MoveUpButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
