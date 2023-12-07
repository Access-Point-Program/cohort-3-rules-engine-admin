import { ComponentFixture, TestBed, fakeAsync } from '@angular/core/testing';
import { SaveButtonComponent } from './save-button.component';
import { By } from '@angular/platform-browser';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { ConditionsComponent } from '../conditions/conditions.component';
import { RulesComponentComponent } from '../rules-component/rules-component.component';

const condition1 = new ConditionsComponent();
  condition1.conditionDatabaseId = 100;
  condition1.conditionWhenValue = "FRONT";
  condition1.conditionIsValue = "END";
  condition1.conditionNeverEntry = false;
  condition1.conditionNeverIsEntry = false;
const rule1 = new RulesComponentComponent();
  rule1.ruleIndex = 10;
  rule1.priority = 1.0;
  rule1.thenValue = "FORWARD";
  rule1.neverThenEntry = false;
  rule1.childrenConditions = [condition1];
const condition2 = new ConditionsComponent();
  condition1.conditionDatabaseId = 200;
  condition1.conditionWhenValue = "RIGHT";
  condition1.conditionIsValue = "EMPTY";
  condition1.conditionNeverEntry = false;
  condition1.conditionNeverIsEntry = false;
const condition3 = new ConditionsComponent();
  condition1.conditionDatabaseId = 300;
  condition1.conditionWhenValue = "LEFT";
  condition1.conditionIsValue = "WALL";
  condition1.conditionNeverEntry = false;
  condition1.conditionNeverIsEntry = false;
const rule2 = new RulesComponentComponent();
  rule2.ruleIndex = 20;
  rule2.priority = 2.0;
  rule2.thenValue = "LEFT";
  rule2.neverThenEntry = false;
  rule2.childrenConditions = [condition2, condition3];
const fakeRulesetData: RulesComponentComponent[] = [rule1, rule2];

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

  it('When clicking the save button, data saved pops up', () => {
    spyOn(component, 'saveButtonClick').and.returnValue(fakeRulesetData);
    fixture.detectChanges();
    spyOn(window, 'alert');

    fixture.debugElement.nativeElement.querySelector('#saveButton').click();
    fixture.detectChanges();

    expect(window.alert).toBeTruthy();
  });
});