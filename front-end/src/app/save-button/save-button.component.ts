import { Component, Injector } from '@angular/core';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  callPost(data: string){
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    this.http.post(`http://localhost:9004/ruleset`, data, { headers: headers }).subscribe({
      next: res => {
        window.alert("Rule Set saved!");
        window.location.href = "http://localhost:9030/rulesets";  
      },
      error: err => {
        if (err.error == "Name cannot be empty") {
          window.alert("Rule Set could not be saved!\nError: " + err.error);
        }else{
          window.alert("Rule Set could not be saved!\nPlease ensure all fields are filled out.");
        }
      }
    })
  }

  saveData(){
    const mapToConditions = (condition: ConditionsComponent): ruleCondition => {
      const { conditionIsValue: value_type, conditionWhenValue: fact_type } = condition;
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
    this.callPost(data);
  }
}

type ruleCondition = {
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
