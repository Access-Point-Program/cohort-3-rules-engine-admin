import { Component, Injector, Input } from '@angular/core';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { UpdateRulesetComponent } from '../update-ruleset/update-ruleset.component';

@Component({
  selector: 'app-delete-rule-button',
  templateUrl: './delete-rule-button.component.html',
  styleUrls: ['./delete-rule-button.component.css']
})
export class DeleteRuleButtonComponent {
  @Input() ruleIndex!: number;

  public _parentRuleset: CreateRulesetComponent | UpdateRulesetComponent;
  
  constructor(private _injector: Injector) { 
    try {
      const _parent_parent: CreateRulesetComponent = this._injector.get<CreateRulesetComponent>(CreateRulesetComponent);
      this._parentRuleset = _parent_parent;
  
    } catch (e) {
      const _parent_parent: UpdateRulesetComponent = this._injector.get<UpdateRulesetComponent>(UpdateRulesetComponent);
      this._parentRuleset = _parent_parent;
    }
  }

  public deleteRule() {
    this._parentRuleset.ruleset.map(rule => rule.setPriority(-1));
    this._parentRuleset.ruleset.splice(this.ruleIndex, 1);
  }
}