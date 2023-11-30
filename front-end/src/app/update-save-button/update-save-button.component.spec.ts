import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateSaveButtonComponent } from './update-save-button.component';

describe('UpdateSaveButtonComponent', () => {
  let component: UpdateSaveButtonComponent;
  let fixture: ComponentFixture<UpdateSaveButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateSaveButtonComponent]
    });
    fixture = TestBed.createComponent(UpdateSaveButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
