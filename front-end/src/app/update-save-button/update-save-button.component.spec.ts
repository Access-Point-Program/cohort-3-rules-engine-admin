import { ComponentFixture, TestBed, async, fakeAsync, tick, waitForAsync } from '@angular/core/testing';
import { UpdateSaveButtonComponent } from './update-save-button.component';
import { By } from '@angular/platform-browser';
import { UpdateRulesetComponent } from '../update-ruleset/update-ruleset.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { ConditionsComponent } from '../conditions/conditions.component';

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

  it('should create "update-save-button" component', () => {
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

  it('When clicking the save button, confirmation pops up', () => {
    fixture.detectChanges();
    spyOn(window, 'confirm');
    fixture.debugElement.nativeElement.querySelector('#updateSaveButton').click();
    fixture.detectChanges();
    expect(window.confirm).toBeTruthy();
  });

  it("When clicking the save button, update data is called", () => {
    fixture.detectChanges();
    spyOn(component, 'updateData');
    fixture.debugElement.nativeElement.querySelector('#updateSaveButton').click();
    fixture.detectChanges();
    expect(component.updateData).toHaveBeenCalled();
  });

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

  it("When clicking the save button, updateSaveButtonClick is called", fakeAsync (() => {
    spyOn(component, 'updateSaveButtonClick').and.returnValue(fakeRulesetData);
    spyOn(component, 'callPut').and.returnValue(Promise.resolve());
    fixture.detectChanges();
    fixture.debugElement.nativeElement.querySelector('#updateSaveButton').click();
    fixture.detectChanges();
    fixture.whenStable();
    fixture.detectChanges();
    expect(component.updateSaveButtonClick).toHaveBeenCalled();
  }));

  it('When clicking the save button, callPut is called', fakeAsync(() => {
    spyOn(component, 'callPut').and.returnValue(Promise.resolve());
    spyOn(component, 'updateSaveButtonClick').and.returnValue(fakeRulesetData);
    fixture.detectChanges();
    fixture.debugElement.nativeElement.querySelector('#updateSaveButton').click();
    fixture.whenStable()
    fixture.detectChanges();
    expect(component.callPut).toHaveBeenCalled();
  }));

  fit('calling callPut with good data it does the changes saved', fakeAsync(() => {
    spyOn(component, 'callPut').and.returnValue(Promise.resolve());
    spyOn(component, 'updateSaveButtonClick').and.returnValue(fakeRulesetData);
    spyOn(window, 'alert');

    fixture.detectChanges();
    fixture.debugElement.nativeElement.querySelector('#updateSaveButton').click();
    fixture.whenStable()
    fixture.detectChanges();
    expect(window.alert).toHaveBeenCalledWith("The ruleset changes have been saved.");

  }));

  });