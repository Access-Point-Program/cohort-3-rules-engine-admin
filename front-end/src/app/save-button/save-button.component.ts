import { Component, Injector } from '@angular/core';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { RulesComponentComponent } from '../rules-component/rules-component.component';

@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.css']
})
export class SaveButtonComponent {


  
  public _parentRuleset: CreateRulesetComponent;

  constructor(private _injector: Injector) { 
    const _parent_parent: CreateRulesetComponent = this._injector.get<CreateRulesetComponent>(CreateRulesetComponent);
    this._parentRuleset = _parent_parent;
  }

  saveData(){
    const data:RulesComponentComponent[] = this._parentRuleset.saveButtonClick();
    console.log("HERE!!!!!")
    console.log(data);
    //Todo: 
  }


}
