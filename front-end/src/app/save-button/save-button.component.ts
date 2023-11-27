import { Component, Injector } from '@angular/core';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { HttpClient } from '@angular/common/http';
import { ConditionsComponent } from '../conditions/conditions.component';

@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.css']
})
export class SaveButtonComponent {


  
  public _parentRuleset: CreateRulesetComponent;

  constructor(private _injector: Injector, private http: HttpClient) { 
    const _parent_parent: CreateRulesetComponent = this._injector.get<CreateRulesetComponent>(CreateRulesetComponent);
    this._parentRuleset = _parent_parent;
  }


  saveData(){
    
    const mapToConditions = (condition: ConditionsComponent): ruleCondition => {
      const {conditionIsValue: value_type, conditionWhenValue: fact_type} = condition;

      return { fact_type, value_type};
    }

    const jsonDataToUse = this._parentRuleset.saveButtonClick()
      .reduce<ruleset>((acc, rule) => {
        const { thenValue: event_type, priority } = rule;
        const conditions = rule.childrenConditions.map<ruleCondition>(mapToConditions);

        acc.rules.push({ priority, event_type, conditions });

        return acc;
      }, { name: this._parentRuleset.getName() , rules:[] });

    const data = JSON.stringify(jsonDataToUse, null, 2);
    console.log("HERE!!!!!")
    console.log(data);


    return this.http.post(`http://localhost:8080/ruleset`, data);
  }


}
type ruleCondition={
      fact_type: String
      value_type: String
    }
type rulesetRule = {
    priority: Number
    event_type: String
    conditions: ruleCondition[]
  }
type ruleset = {
  name: String
  rules: rulesetRule[]
}

// {"name":"Test 2","rules": [
//   {"priority": 7, "event_type": "FORWARD", "conditions": [
//       {
//           "fact_type":"RIGHT","value_type":"EMPTY"
//       }
//   ]},