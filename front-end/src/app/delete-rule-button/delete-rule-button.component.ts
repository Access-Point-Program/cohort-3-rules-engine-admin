import { Component, Injector, Input } from '@angular/core';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';

@Component({
  selector: 'app-delete-rule-button',
  templateUrl: './delete-rule-button.component.html',
  styleUrls: ['./delete-rule-button.component.css']
})
export class DeleteRuleButtonComponent {
  @Input() ruleIndex!: number;

  _parentRuleset: CreateRulesetComponent;

  constructor(private _injector: Injector) { 
    const _parent_parent: CreateRulesetComponent = this._injector.get<CreateRulesetComponent>(CreateRulesetComponent);
    this._parentRuleset = _parent_parent;
  }

  public deleteRule() {
    console.log(this.ruleIndex)
    // const rulesetArray = this._parentRuleset.ruleset; 
    // const currentRule = this.ruleIndex;
    // rulesetArray.splice(currentRule, 1);
  }
}
