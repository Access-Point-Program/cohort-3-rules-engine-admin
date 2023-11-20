import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CreateRulesetComponent} from './create-ruleset/create-ruleset.component'
import {AppComponent} from './app.component'

const routes: Routes = [
  { path: '**', redirectTo: '/ruleset', pathMatch: 'full' },
  { path: 'ruleset', component: CreateRulesetComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }