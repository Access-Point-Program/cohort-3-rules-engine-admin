import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CreateRulesetComponent} from './create-ruleset/create-ruleset.component'

const routes: Routes = [
  { path: 'ruleset', component: CreateRulesetComponent},
  { path: '', redirectTo: '/ruleset', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }