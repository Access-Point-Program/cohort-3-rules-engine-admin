import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateSaveButtonComponent } from './update-save-button.component';
import { By } from '@angular/platform-browser';

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

  it('should create update save button', () => {
    expect(component).toBeTruthy();
  });

  it('should render an update save button on the screen', () => {
    const saveButton = fixture.debugElement.query(By.css('#saveButton'));
    expect(saveButton).toBeTruthy();
  });

  fit('When update save button is clicked mocked saveData() function is called', () => {
    fixture.detectChanges();
    spyOn(component, 'updateData');
    fixture.debugElement.nativeElement.querySelector('#saveButton').click();
    fixture.detectChanges();
    expect(component.updateData).toHaveBeenCalled();  
  });
});
