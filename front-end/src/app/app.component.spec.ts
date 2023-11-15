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
import { By } from '@angular/platform-browser';

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
      MoveUpButtonComponent
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
  it(`clicking "Add New Rule" should increase the amount of rules`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();

    expect(app.ruleset.length).toBe(2);
  });
  it('button should be rendered', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const button = fixture.debugElement.query(By.css('#ruleButton'));

    expect(button).toBeTruthy();
  });
  it('clicking "Add New Rule" should display all of the conditions and buttons for them', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(1);
    fixture.debugElement.query(By.css('#ruleButton')).nativeElement.click();
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelectorAll('#rulesComponent').length).toBe(2);
  });
  it('all buttons should be displayed on screen', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    expect(fixture.debugElement.nativeElement.querySelector('#conditionButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#deleteButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#moveUpButton')).toBeTruthy();
    expect(fixture.debugElement.nativeElement.querySelector('#moveDownButton')).toBeTruthy();
  });
});
