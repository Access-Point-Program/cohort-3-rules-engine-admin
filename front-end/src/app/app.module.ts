import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { WhenComponent } from './conditions/when/when.component';
import { IsComponent } from './conditions/is/is.component';
import { ThenComponent } from './then/then.component';
import { ConditionsComponent } from './conditions/conditions.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { CreateNewConditionComponent } from './create-new-condition/create-new-condition.component';

@NgModule({
  declarations: [
    AppComponent,
    WhenComponent,
    IsComponent,
    ThenComponent,
    ConditionsComponent,
    SidebarComponent,
    CreateNewConditionComponent
  ],
  imports: [
    BrowserModule    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
