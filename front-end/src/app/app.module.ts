import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { WhenComponent } from './conditions/when/when.component';
import { IsComponent } from './conditions/is/is.component';
import { ThenComponent } from './then/then.component';
import { ConditionsComponent } from './conditions/conditions.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { CreateNewConditionComponent } from './create-new-condition/create-new-condition.component';
import { DeleteRuleButtonComponent } from './delete-rule-button/delete-rule-button.component';
import { MoveUpButtonComponent } from './move-up-button/move-up-button.component';
import { MoveDownButtonComponent } from './move-down-button/move-down-button.component';
import { AddNewRuleButtonComponent } from './add-new-rule-button/add-new-rule-button.component';
import { RulesComponentComponent } from './rules-component/rules-component.component';
import { RulesetNameComponent } from './ruleset-name/ruleset-name.component';
import { SaveButtonComponent } from './save-button/save-button.component';
import { DashboardPathComponent } from './dashboard-path/dashboard-path.component';
import { CreateRulesetComponent } from './create-ruleset/create-ruleset.component';
import { CancelButtonComponent } from './cancel-button/cancel-button.component';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from "@angular/router";
import { HttpClientModule } from '@angular/common/http';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { UpdateRulesetComponent } from './update-ruleset/update-ruleset.component';
import { UpdateSaveButtonComponent } from './update-save-button/update-save-button.component';


@NgModule({
  declarations: [
    AppComponent,
    WhenComponent,
    IsComponent,
    ThenComponent,
    ConditionsComponent,
    SidebarComponent,
    CreateNewConditionComponent,
    DeleteRuleButtonComponent,
    MoveUpButtonComponent,
    MoveDownButtonComponent,
    AddNewRuleButtonComponent,
    RulesComponentComponent,
    RulesetNameComponent,
    SaveButtonComponent,
    DashboardPathComponent,
    CreateRulesetComponent,
    CancelButtonComponent,
    UpdateRulesetComponent,
    UpdateSaveButtonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    HttpClientModule
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }