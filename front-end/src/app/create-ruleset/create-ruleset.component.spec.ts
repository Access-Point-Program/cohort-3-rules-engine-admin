import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { CreateRulesetComponent } from './create-ruleset.component';
import { By } from '@angular/platform-browser';
import { WhenComponent } from '../conditions/when/when.component';
import { IsComponent } from '../conditions/is/is.component';
import { ThenComponent } from '../then/then.component';
import { ConditionsComponent } from '../conditions/conditions.component';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { CreateNewConditionComponent } from '../create-new-condition/create-new-condition.component';
import { AddNewRuleButtonComponent } from '../add-new-rule-button/add-new-rule-button.component';
import { DeleteRuleButtonComponent } from '../delete-rule-button/delete-rule-button.component';
import { MoveDownButtonComponent } from '../move-down-button/move-down-button.component';
import { MoveUpButtonComponent } from '../move-up-button/move-up-button.component';
import { DashboardPathComponent } from '../dashboard-path/dashboard-path.component';
import { RulesetNameComponent } from '../ruleset-name/ruleset-name.component';
import { SaveButtonComponent } from '../save-button/save-button.component';


describe('CreateRulesetComponent', () => {
  let component: CreateRulesetComponent;
  let fixture: ComponentFixture<CreateRulesetComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateRulesetComponent,
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
        SaveButtonComponent]
    });
    fixture = TestBed.createComponent(CreateRulesetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it(`clicking "Add New Rule" should increase the amount of rules`, () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const app = fixture.componentInstance;
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();

    expect(app.ruleset.length).toBe(2);
  });
  it('button should be rendered', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const app = fixture.componentInstance;
    const button = fixture.debugElement.query(By.css('#ruleButton'));

    expect(button).toBeTruthy();
  });
  it('clicking "Add New Rule" should display all of the conditions and buttons for them', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(1);
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(2);
  });
  it('all buttons should be displayed on screen', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelector('#conditionButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#deleteButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#moveUpButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#moveDownButton')).toBeTruthy();
  });

  it('When clicking add new rule button, rule priority defaults are set correctly', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const createRulesetComponentInstance = fixture.componentInstance;
    fixture.detectChanges();
    // Click the add rule button 3 times for 4 total rules in ruleset
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    // Check that it is 4 rules in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(4);
    // Check that the rule priorities are 1, 2, 3, and 4, in that order.
    expect(createRulesetComponentInstance.ruleset.map(rule => rule.priority)).toEqual([1, 2, 3, 4]);
  })

  it('Clicking "move-down" button on 1st rule when only 2 rules, priority correctly calculates math', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const createRulesetComponentInstance = fixture.componentInstance;
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(1);
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(2);
    fixture.debugElement.queryAll(By.css('#moveDownButtonInside'))[0].nativeElement.click();
    fixture.detectChanges();
    expect(createRulesetComponentInstance.ruleset.map(rule => rule.priority)).toEqual([2, 2.5]);
  })

  it('Clicking "move-down" button on 1st rule when 3 rules, priority correctly calculates math', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const createRulesetComponentInstance = fixture.componentInstance;
    fixture.detectChanges();
    // Click the add rule button 2 times for 3 total rules in ruleset
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    // Check that it is 3 rules in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(3);
    fixture.debugElement.queryAll(By.css('#moveDownButtonInside'))[0].nativeElement.click();
    fixture.detectChanges();
    // Check that the new order of new priorities is correct
    expect(createRulesetComponentInstance.ruleset.map(rule => rule.priority)).toEqual([2, 2.5, 3]);
  })

  it('Clicking "up-down" button on 2nd rule when only 2 rules, priority correctly calculates math', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const createRulesetComponentInstance = fixture.componentInstance;
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(1);
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(2);
    fixture.debugElement.queryAll(By.css('#moveUpButtonInside'))[1].nativeElement.click();
    fixture.detectChanges();
    expect(createRulesetComponentInstance.ruleset.map(rule => rule.priority)).toEqual([0.5, 1]);
  })
  
  it('Clicking "move-up" button on 2nd rule when 3 rules, priority correctly calculates math', () => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const createRulesetComponentInstance = fixture.componentInstance;
    fixture.detectChanges();
    // Click the add rule button 2 times for 3 total rules in ruleset
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    // Check that it is 3 rules in the ruleset
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(3);

    fixture.debugElement.queryAll(By.css('#moveUpButtonInside'))[1].nativeElement.click();
    fixture.detectChanges();
    // Check the priorities of the ruleset
    expect(createRulesetComponentInstance.ruleset.map(rule => rule.priority)).toEqual([0.5, 1, 3]);
  })

  it('"move-down" button on 2nd rule when 2 rules is disabled', fakeAsync(() => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    const createRulesetComponentInstance = fixture.componentInstance;
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#moveDownButtonInside')[1].disabled).toBeTruthy();
  }))

  it('"move-up" button on 1st rule when 2 rules is disabled', fakeAsync(() => {
    const fixture = TestBed.createComponent(CreateRulesetComponent);
    fixture.detectChanges();
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    tick(100);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#moveUpButtonInside')[0].disabled).toBeTruthy();
  }))
});
