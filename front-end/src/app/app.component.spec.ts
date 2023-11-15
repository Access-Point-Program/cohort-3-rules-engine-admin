import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { WhenComponent } from './conditions/when/when.component';
import { IsComponent } from './conditions/is/is.component';
import { ThenComponent } from './then/then.component';
import { ConditionsComponent } from './conditions/conditions.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { By } from '@angular/platform-browser';
import { AddNewRuleButtonComponent } from './add-new-rule-button/add-new-rule-button.component';


describe('AppComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [AppComponent,
      WhenComponent,
      IsComponent,
      ThenComponent,
      ConditionsComponent, 
      SidebarComponent,
      AddNewRuleButtonComponent,
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
  fit('button should be rendered', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    const button = fixture.debugElement.query(By.css('#ruleButton'));

    expect(button).toBeTruthy();
  });
});
