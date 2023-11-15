import { Component, Input } from '@angular/core';
import { CreateNewConditionComponent } from '../create-new-condition/create-new-condition.component';
import { ConditionsComponent } from '../conditions/conditions.component';
@Component({
  selector: 'app-rules-component',
  templateUrl: './rules-component.component.html',
  styleUrls: ['./rules-component.component.css']
})
export class RulesComponentComponent {
  @Input()
  priority: number;

  constructor(){
    this.priority = 0;
  }

  public getPriority(){
    return this.priority;
  }

  public setPriority(priority: number){
    this.priority = priority;
  }

  public conditionCounter: ConditionsComponent[] = [new ConditionsComponent];
}