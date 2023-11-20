import { ComponentFixture, TestBed } from '@angular/core/testing';

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
});
