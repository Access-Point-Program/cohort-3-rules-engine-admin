import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { WhenComponent } from './conditions/when/when.component';
import { IsComponent } from './conditions/is/is.component';
import { ThenComponent } from './conditions/then/then.component';

@NgModule({
  declarations: [
    AppComponent,
    WhenComponent,
    IsComponent,
    ThenComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
