import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { WhenComponent } from './conditions/when/when.component';
import { IsComponent } from './conditions/is/is.component';
import { ThenComponent } from './then/then.component';
import { ConditionsComponent } from './conditions/conditions.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { RulesComponentComponent } from './rules-component/rules-component.component';
import { CreateNewConditionComponent } from './create-new-condition/create-new-condition.component';
import { AddNewRuleButtonComponent } from './add-new-rule-button/add-new-rule-button.component';
import { DeleteRuleButtonComponent } from './delete-rule-button/delete-rule-button.component';
import { MoveDownButtonComponent } from './move-down-button/move-down-button.component';
import { MoveUpButtonComponent } from './move-up-button/move-up-button.component';
import { CreateRulesetComponent } from './create-ruleset/create-ruleset.component';
import { DashboardPathComponent } from './dashboard-path/dashboard-path.component';
import { RulesetNameComponent } from './ruleset-name/ruleset-name.component';
import { SaveButtonComponent } from './save-button/save-button.component';


describe('AppComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [AppComponent,
      WhenComponent,
      IsComponent,
      ThenComponent,
      ConditionsComponent, 
      SidebarComponent,
      RulesComponentComponent,
      CreateNewConditionComponent,
      AddNewRuleButtonComponent,
      DeleteRuleButtonComponent,
      MoveDownButtonComponent,
      MoveUpButtonComponent,
      CreateRulesetComponent,
      DashboardPathComponent,
      RulesetNameComponent,
      SaveButtonComponent,
      
    ]
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'front-end'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('front-end');
  });
});