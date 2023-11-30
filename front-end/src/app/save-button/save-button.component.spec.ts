import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SaveButtonComponent } from './save-button.component';
import { By } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';


describe('SaveButtonComponent', () => {
  let component: SaveButtonComponent;
  let fixture: ComponentFixture<SaveButtonComponent>;
  let httpClient: HttpClient;
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaveButtonComponent],
      providers: [ CreateRulesetComponent ]
    });

    httpClient = TestBed.inject(HttpClient);

    fixture = TestBed.createComponent(SaveButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    
  });

  it('should create', () => {
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

 });
