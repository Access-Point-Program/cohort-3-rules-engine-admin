import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateSaveButtonComponent } from './update-save-button.component';
import { By } from '@angular/platform-browser';
import { UpdateRulesetComponent } from '../update-ruleset/update-ruleset.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from "@angular/router/testing";

describe('UpdateSaveButtonComponent', () => {
  let component: UpdateSaveButtonComponent;
  let fixture: ComponentFixture<UpdateSaveButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateSaveButtonComponent],
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [ UpdateRulesetComponent ]
    });
    fixture = TestBed.createComponent(UpdateSaveButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create update save button', () => {
    expect(component).toBeTruthy();
  });

  it('should render an update save button on the screen', () => {
    const saveButton = fixture.debugElement.query(By.css('#updateSaveButton'));
    expect(saveButton).toBeTruthy();
  });

  it('When update save button is clicked mocked saveData() function is called', () => {
    fixture.detectChanges();
    spyOn(component, 'updateData');
    fixture.debugElement.nativeElement.querySelector('#updateSaveButton').click();
    fixture.detectChanges();
    expect(component.updateData).toHaveBeenCalled();  
  });
});
