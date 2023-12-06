import { ComponentFixture, TestBed, fakeAsync, flush, tick } from '@angular/core/testing';
import { UpdateRulesetComponent } from './update-ruleset.component';
import { UpdateSaveButtonComponent } from '../update-save-button/update-save-button.component';
import { RouterTestingModule } from '@angular/router/testing';
import { CancelButtonComponent } from '../cancel-button/cancel-button.component';
import { DashboardPathComponent } from '../dashboard-path/dashboard-path.component';
import { AddNewRuleButtonComponent } from '../add-new-rule-button/add-new-rule-button.component';
import { RulesetNameComponent } from '../ruleset-name/ruleset-name.component';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { WhenComponent } from '../conditions/when/when.component';
import { IsComponent } from '../conditions/is/is.component';
import { ThenComponent } from '../then/then.component';
import { ConditionsComponent } from '../conditions/conditions.component';
import { CreateNewConditionComponent } from '../create-new-condition/create-new-condition.component';
import { DeleteRuleButtonComponent } from '../delete-rule-button/delete-rule-button.component';
import { MoveDownButtonComponent } from '../move-down-button/move-down-button.component';
import { MoveUpButtonComponent } from '../move-up-button/move-up-button.component';
import { ActivatedRoute, RouterModule, convertToParamMap} from '@angular/router';
import { By } from '@angular/platform-browser';
import { of } from 'rxjs';
import { Component } from '@angular/core';

const fakeFetchBody = {
  "id": 1,
  "name": "Mock Test 1",
  "creation_date": "2023-11-20T17:16:53.190+00:00",
  "rules": [
    {
      "id": 10,
      "priority": 1.0,
      "event_type": "FORWARD",
      "conditions": [
        {
          "id": 100,
          "fact_type": "FRONT",
          "value_type": "END"
        }
      ]
    },
    {
      "id": 20,
      "priority": 2.5,
      "event_type": "LEFT",
      "conditions": [
        {
          "id": 200,
          "fact_type": "RIGHT",
          "value_type": "EMPTY"
        }
      ]
    }
  ]
}

describe('UpdateRulesetComponent', () => {
  let component: UpdateRulesetComponent;
  let fixture: ComponentFixture<UpdateRulesetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        UpdateRulesetComponent,
        UpdateSaveButtonComponent,
        WhenComponent,
        IsComponent,
        ThenComponent,
        ConditionsComponent, 
        RulesComponentComponent,
        CreateNewConditionComponent,
        AddNewRuleButtonComponent,
        DeleteRuleButtonComponent,
        MoveDownButtonComponent,
        MoveUpButtonComponent,
        DashboardPathComponent,
        RulesetNameComponent,
        CancelButtonComponent
      ],
      imports: [RouterTestingModule],
      providers: [
        {provide: ActivatedRoute, useValue: {paramMap: of(convertToParamMap({id: 1}))}}
      ]
    });
    fixture = TestBed.createComponent(UpdateRulesetComponent);
    component = fixture.componentInstance;
  });

  it('should create "Update-ruleset" component', () => {
    expect(component).toBeTruthy();
  });

  it("when component is initialized, customFetch method is called", () => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    fixture.detectChanges();
    expect(component.customFetch).toHaveBeenCalled();
  })

  it("when component is initialized, convertResponseToJson method is called", fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    expect(component.convertResponseToJson).toHaveBeenCalled();
  }));

  it('when name field is overwritten, new name displays correctly', () => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    component.name = "Mock Test 1";
    fixture.detectChanges();
    expect(fixture.debugElement.query(By.css('#rulesetName')).nativeElement.value).toBe("Mock Test 1")
  })

  it('when ruleset field is overwritten, the webpage renders the correct number of rules and conditions', () => {
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

    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    component.setRuleset(fakeRulesetData);
    fixture.detectChanges();
    // There should be 2 rules
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(2);
    // There should be 3 conditions
    expect(fixture.debugElement.nativeElement.querySelectorAll('.conditionsComponent').length).toBe(3);
  })

  it('When moving a rule up, making the priority go past 10 decimal places, then it resets all priorities', () => {
    // Mocking the ngOnInit fetching to ensure that the promises resolve, but ruleset is not being updated.
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    // Create 2 new rules
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    // Move 2nd rule above 1st rule 10 times, so the decimal place length is 10
    for (let i = 0; i < 10; i++){
      fixture.debugElement.queryAll(By.css('#moveUpButtonInside'))[1].nativeElement.click();
      fixture.detectChanges();
    }
    // Make sure decimal length of 1st rule is 10, 2nd rule is 9, and 3rd rule is 0
    expect(component.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([10, 9, 0]);
    // Do one more move up to push over 10 decimal places
    fixture.debugElement.queryAll(By.css('#moveUpButtonInside'))[1].nativeElement.click();
    fixture.detectChanges();
    // Make sure that decimal length of all rules is now 0
    expect(component.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([0, 0, 0]);
    // Check that the priority has reset back to default whole numbers 1, 2, 3
    expect(component.ruleset.map(rule => rule.getPriority())).toEqual([1, 2, 3]);
  })

  it('When moving a rule down, making the priority go past 10 decimal places, then it resets all priorities', ()=> {
    // Mocking the ngOnInit fetching to ensure that the promises resolve, but ruleset is not being updated.
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    // Create 2 new rules
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    // Move 2nd rule below 1st rule 10 times, so the decimal place length is 10
    for (let i = 0; i < 10; i++){
      fixture.debugElement.queryAll(By.css('#moveDownButtonInside'))[0].nativeElement.click();
      fixture.detectChanges();
    }
    // Make sure decimal length of 1st rule is 9, 2nd rule is 10, and 3rd rule is 0
    expect(component.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([9, 10, 0]);
    // Do one more move up to push over 10 decimal places
    fixture.debugElement.queryAll(By.css('#moveDownButtonInside'))[0].nativeElement.click();
    fixture.detectChanges();
    // Make sure that decimal length of all rules is now 0
    expect(component.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([0, 0, 0]);
    // Check that the priority has reset back to default whole numbers 1, 2, 3
    expect(component.ruleset.map(rule => rule.getPriority())).toEqual([1, 2, 3]);
  })

  it(`clicking "Add New Rule" should increase the amount of rules`, fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    expect(component.ruleset.length).toBe(3);
  }));

  it('"Add New Rule" button should be rendered', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    const button = fixture.debugElement.query(By.css('#ruleButton'));
    expect(button).toBeTruthy();
  }));
  
  it('all buttons should be displayed on screen', () => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelector('#conditionButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#deleteButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#moveUpButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#moveDownButton')).toBeTruthy();
  });

  it('When clicking add new rule button, rule priority defaults are set correctly', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    // Click the add rule button 3 times for 4 total rules in ruleset
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    tick(100);
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    tick(100);
    // Check that it is 4 rules in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(4);
    // Check that the rule priorities are correct
    expect(component.ruleset.map(rule => rule.priority)).toEqual([1, 2.5, 3, 4]);
  }))

  it('Clicking "move-down" button on 1st rule when only 2 rules, priority correctly calculates math', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    fixture.debugElement.queryAll(By.css('#moveDownButtonInside'))[0].nativeElement.click();
    tick(100);
    expect(component.ruleset.map(rule => rule.priority)).toEqual([2.5, 3]);
  }))

  it('Clicking "move-down" button on 1st rule when 3 rules, priority correctly calculates math', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    // Check that it is 3 rules in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(3);
    fixture.debugElement.queryAll(By.css('#moveDownButtonInside'))[0].nativeElement.click();
    tick(100);
    // Check that the new order of new priorities is correct
    expect(component.ruleset.map(rule => rule.priority)).toEqual([2.5, 2.75, 3]);
  }))

  it('Clicking "up-down" button on 2nd rule when only 2 rules, priority correctly calculates math', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    fixture.debugElement.queryAll(By.css('#moveUpButtonInside'))[1].nativeElement.click();
    tick(100);
    expect(component.ruleset.map(rule => rule.priority)).toEqual([0.5, 1]);
  }))
  
  it('Clicking "move-up" button on 2nd rule when 3 rules, priority correctly calculates math', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    tick(100);
    // Check that it is 3 rules in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(3);
    fixture.debugElement.queryAll(By.css('#moveUpButtonInside'))[1].nativeElement.click();
    tick(100);
    // Check the priorities of the ruleset
    expect(component.ruleset.map(rule => rule.priority)).toEqual([0.5, 1, 3]);
  }))

  it('"move-down" button on 2nd rule when 2 rules is disabled', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#moveDownButtonInside')[1].disabled).toBeTruthy();
  }))

  it('"move-up" button, on 1st rule, when 2 rules, is disabled', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#moveUpButtonInside')[0].disabled).toBeTruthy();
  }))


  fit('clicking a rules "Delete" button deletes the rule from the ruleset array', fakeAsync(() => {
    spyOn(component, 'customFetch').and.returnValue(Promise.resolve(new Response(JSON.stringify(fakeFetchBody), {status: 200})));
    spyOn(component, 'convertResponseToJson').and.returnValue(Promise.resolve(fakeFetchBody));
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    // click to add 1 new rules
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    tick(100);
    fixture.detectChanges();
    // ensure that there is 3 rules now
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(3);
    // ensure that the priorities are correct, meaning the intended rule was the one deleted
    expect(component.ruleset[0].getPriority()).toBe(1);
    expect(component.ruleset[1].getPriority()).toBe(2.5);
    expect(component.ruleset[2].getPriority()).toBe(3);

    // click 2nd rule delete button
    fixture.debugElement.nativeElement.querySelectorAll('#deleteButtonInside')[1].click();
    fixture.detectChanges();
    tick(100);
    // ensure that after clicking the delete rule button, there is 1 less rule in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(2);
    // ensure that the remaining priorities are correct, meaning the intended rule was the one deleted
    expect(component.ruleset[0].getPriority()).toBe(1);
    expect(component.ruleset[1].getPriority()).toBe(3);

    // click new 2nd rule delete button
    fixture.debugElement.nativeElement.querySelectorAll('#deleteButtonInside')[1].click();
    fixture.detectChanges();
    tick(100);
    // ensure that after clicking the delete rule button, there is now only 1 rule in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(1);
    // ensure that the remaining priorities are correct, meaning the intended rule was the one deleted
    expect(component.ruleset[0].getPriority()).toBe(1);
  }))
});