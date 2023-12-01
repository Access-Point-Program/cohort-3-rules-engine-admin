import { Component } from '@angular/core';
import { RulesComponentComponent } from '../rules-component/rules-component.component';
import { Injector } from '@angular/core';
import { ConditionsComponent } from '../conditions/conditions.component';

@Component({
  selector: 'app-create-new-condition',
  templateUrl: './create-new-condition.component.html',
  styleUrls: ['./create-new-condition.component.css']
})
export class CreateNewConditionComponent {
  public _parent: RulesComponentComponent;

  constructor(private _injector: Injector) { 
    const _parent: RulesComponentComponent = this._injector.get<RulesComponentComponent>(RulesComponentComponent);
    this._parent = _parent;
  }

  public addCondition():void {
    this._parent.childrenConditions.push(new ConditionsComponent);
  }
}