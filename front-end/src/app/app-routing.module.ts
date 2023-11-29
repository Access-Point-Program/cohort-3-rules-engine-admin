import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CreateRulesetComponent} from './create-ruleset/create-ruleset.component'
import { UpdateRulesetComponent } from './update-ruleset/update-ruleset.component';

const routes: Routes = [
  { path: 'create-ruleset', component: CreateRulesetComponent},
  { path: '', redirectTo: '/create-ruleset', pathMatch: 'full' },
  { path: 'update-ruleset', component: UpdateRulesetComponent},
  { path: '', redirectTo: '/update-ruleset', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }