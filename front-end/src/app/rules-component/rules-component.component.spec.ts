import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ThenComponent } from '../then/then.component';
import { ConditionsComponent } from '../conditions/conditions.component';
import { WhenComponent } from '../conditions/when/when.component';
import { IsComponent } from '../conditions/is/is.component';
import { CreateNewConditionComponent } from '../create-new-condition/create-new-condition.component';
import { DeleteRuleButtonComponent } from '../delete-rule-button/delete-rule-button.component';
import { MoveDownButtonComponent } from '../move-down-button/move-down-button.component';
import { MoveUpButtonComponent } from '../move-up-button/move-up-button.component';
import { By } from '@angular/platform-browser';
import { RulesComponentComponent } from './rules-component.component';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';

describe('RulesComponentComponent', () => {
  let component: RulesComponentComponent;
  let fixture: ComponentFixture<RulesComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        RulesComponentComponent, 
        ThenComponent, 
        ConditionsComponent, 
        WhenComponent, 
        IsComponent, 
        CreateNewConditionComponent, 
        DeleteRuleButtonComponent, 
        MoveDownButtonComponent, 
        MoveUpButtonComponent
      ],
      providers: [CreateRulesetComponent]
    });
    fixture = TestBed.createComponent(RulesComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create "rules-component" component', () => {
    expect(component).toBeTruthy();
  });

  it('When "Add Condition" button is clicked, new condition component is rendered on screen', () => {
    let conditionComponentsArray = fixture.debugElement.nativeElement.querySelectorAll('.conditionsComponent');
    expect(conditionComponentsArray.length).toBe(1);
    let addConditionButton = fixture.debugElement.query(By.css('.addConditionButton')).nativeElement;
    addConditionButton.click();
    fixture.detectChanges();
    conditionComponentsArray = fixture.debugElement.nativeElement.querySelectorAll('.conditionsComponent');
    expect(conditionComponentsArray.length).toBe(2);
  });

});