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
});