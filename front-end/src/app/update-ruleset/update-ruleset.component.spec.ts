import { ComponentFixture, TestBed } from '@angular/core/testing';
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
import { By } from '@angular/platform-browser';

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
        CancelButtonComponent,
        UpdateSaveButtonComponent
      ],
      imports: [RouterTestingModule]
    });
    fixture = TestBed.createComponent(UpdateRulesetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "Update-ruleset" component', () => {
    expect(component).toBeTruthy();
  });

  // TODO: Create tests for update-ruleset

  // it('', () => {

  // });

  // TODO: Fix This Test via fetch mocking
  it('When moving a rule up, making the priority go past 10 decimal places, then it resets all priorities', ()=> {
    const fixture = TestBed.createComponent(UpdateRulesetComponent);
    fixture.detectChanges();
    const updateRulesetComponentInstance = fixture.componentInstance;
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
    expect(updateRulesetComponentInstance.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([10, 9, 0]);
    // Do one more move up to push over 10 decimal places
    fixture.debugElement.queryAll(By.css('#moveUpButtonInside'))[1].nativeElement.click();
    fixture.detectChanges();
    // Make sure that decimal length of all rules is now 0
    expect(updateRulesetComponentInstance.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([0, 0, 0]);
    // Check that the priority has reset back to default whole numbers 1, 2, 3
    expect(updateRulesetComponentInstance.ruleset.map(rule => rule.getPriority())).toEqual([1, 2, 3]);
  })
  // TODO: Fix This Test via fetch mocking
  it('When moving a rule down, making the priority go past 10 decimal places, then it resets all priorities', ()=> {
    const fixture = TestBed.createComponent(UpdateRulesetComponent);
    fixture.detectChanges();
    const updateRulesetComponentInstance = fixture.componentInstance;
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
    expect(updateRulesetComponentInstance.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([9, 10, 0]);
    // Do one more move up to push over 10 decimal places
    fixture.debugElement.queryAll(By.css('#moveDownButtonInside'))[0].nativeElement.click();
    fixture.detectChanges();
    // Make sure that decimal length of all rules is now 0
    expect(updateRulesetComponentInstance.ruleset.map(rule => rule.getPriority().toString().split(".")[1] ? rule.getPriority().toString().split(".")[1].length : 0)).toEqual([0, 0, 0]);
    // Check that the priority has reset back to default whole numbers 1, 2, 3
    expect(updateRulesetComponentInstance.ruleset.map(rule => rule.getPriority())).toEqual([1, 2, 3]);
  })
});