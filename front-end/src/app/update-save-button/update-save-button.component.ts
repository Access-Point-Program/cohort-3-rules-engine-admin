import { Component, Injector } from '@angular/core';
import { UpdateRulesetComponent } from '../update-ruleset/update-ruleset.component';
import { ConditionsComponent } from '../conditions/conditions.component';

@Component({
  selector: 'app-update-save-button',
  templateUrl: './update-save-button.component.html',
  styleUrls: ['./update-save-button.component.css']
})
export class UpdateSaveButtonComponent {
  public _parentRuleset: UpdateRulesetComponent;
  public responseData: {} = {};
  

  constructor(private _injector: Injector) { 
    const _parent_parent: UpdateRulesetComponent = this._injector.get<UpdateRulesetComponent>(UpdateRulesetComponent);
    this._parentRuleset = _parent_parent;
  }

  async callPut(data: string){
    const response = await fetch(`http://localhost:8080/ruleset` + this._parentRuleset.id, {
      method: 'PUT',
      body: data,
      headers: {'Content-Type': 'application/json'} 
    });
    console.log("CALL PUT");
    console.log(response);
    if (!response.ok) { 
      console.log('Success: ' + response);
      this.responseData = this.responseData;
      console.log(this.responseData);
    } else {
      console.log('Error: ' + response)
    }
  }

  updateData(){
    const mapToConditions = (condition: ConditionsComponent): ruleCondition => {
      const {conditionIsValue: value_type, conditionWhenValue: fact_type, conditionDatabaseId: id} = condition;
      return { fact_type, value_type, id};
    }
    const jsonDataToUse = this._parentRuleset.updateSaveButtonClick()
      .reduce<ruleset>((acc, rule) => {
        const { thenValue: event_type, priority, ruleDatabaseId: id} = rule;
        const conditions = rule.childrenConditions.map<ruleCondition>(mapToConditions);
        acc.rules.push({ priority, event_type, id, conditions });
        return acc;
      }, 
      { 
        name: this._parentRuleset.getName(), 
        rules: [],
        id: this._parentRuleset.rulesetDatabaseId
      });
    const data = JSON.stringify(jsonDataToUse, null, 2);
    console.log(data);
    this.callPut(data);
  }
}

type ruleCondition = {
  fact_type: String
  value_type: String
  id: number | undefined
}
type rulesetRule = {
  priority: Number
  event_type: String
  conditions: ruleCondition[]
  id: number | undefined
}
type ruleset = {
  name: String
  rules: rulesetRule[]
  id: number | undefined
}