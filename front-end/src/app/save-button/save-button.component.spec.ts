import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { SaveButtonComponent } from './save-button.component';
import { By } from '@angular/platform-browser';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';

describe('SaveButtonComponent', () => {
  let component: SaveButtonComponent;
  let fixture: ComponentFixture<SaveButtonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaveButtonComponent],
      imports: [ HttpClientTestingModule ],
      providers: [ CreateRulesetComponent ]
    });
    fixture = TestBed.createComponent(SaveButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  
  it('should create "save-button" component', () => {
    expect(component).toBeTruthy();
  });

  it('should render a save button on the screen', () => {
    const saveButton = fixture.debugElement.query(By.css('#saveButton'));
    expect(saveButton).toBeTruthy();
  });

  it('When save button is clicked mocked saveData() function is called', () => {
    fixture.detectChanges();
    spyOn(component, 'saveData');
    fixture.debugElement.nativeElement.querySelector('#saveButton').click();
    fixture.detectChanges();
    expect(component.saveData).toHaveBeenCalled();  
  });

  fit('When clicking the save button, data saved pops up', fakeAsync(() => {
    fixture.detectChanges();
    spyOn(window, 'alert');
    spyOn(component, 'saveData');

    fixture.debugElement.nativeElement.querySelector('#saveButton').click();
    fixture.detectChanges();

    expect(window.alert).toBeTruthy();
    expect(component.saveData).toHaveBeenCalled();
  }));
});