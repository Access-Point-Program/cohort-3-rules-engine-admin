import { Component, Injector } from '@angular/core';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { HttpClient } from '@angular/common/http';
import { ConditionsComponent } from '../conditions/conditions.component';

@Component({
  selector: 'app-save-button',
  templateUrl: './save-button.component.html',
  styleUrls: ['./save-button.component.css']
})
export class SaveButtonComponent {


  
  public _parentRuleset: CreateRulesetComponent;
//private http: HttpClient
  constructor(private _injector: Injector) { 
    const _parent_parent: CreateRulesetComponent = this._injector.get<CreateRulesetComponent>(CreateRulesetComponent);
    this._parentRuleset = _parent_parent;
  }


  // makePostRequest(url: string, body: any) {
  //   return this.http.post(url, body);
  // }

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


    
    console.log("HERE!!!!!")
    console.log(jsonDataToUse);
    console.log(JSON.stringify(jsonDataToUse, null, 2));


    // this.makePostRequest('http://localhost:4200/ruleset', data);
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