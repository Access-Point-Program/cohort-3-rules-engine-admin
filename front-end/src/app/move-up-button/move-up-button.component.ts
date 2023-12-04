import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { Injector } from '@angular/core'; 
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';
import { UpdateRulesetComponent } from '../update-ruleset/update-ruleset.component';

@Component({
  selector: 'app-move-up-button',
  templateUrl: './move-up-button.component.html',
  styleUrls: ['./move-up-button.component.css']
})
export class MoveUpButtonComponent implements OnChanges {
  @Input() rulePriority!: number;
  @Input() rulesetLength!: number;
  @Output() rulePriorityChange = new EventEmitter<number>();

  public _parentRuleset!: CreateRulesetComponent | UpdateRulesetComponent;
  public buttonDisabled: boolean = false;

  constructor(private _injector: Injector) { 
    try {
      const _parent_parent: CreateRulesetComponent = this._injector.get<CreateRulesetComponent>(CreateRulesetComponent);
      this._parentRuleset = _parent_parent;
  
    } catch (e) {
      const _parent_parent: UpdateRulesetComponent = this._injector.get<UpdateRulesetComponent>(UpdateRulesetComponent);
      this._parentRuleset = _parent_parent;
    }
  }

  ngOnChanges(){
    setTimeout(() => {
      this.buttonDisabled = this.checkIfFirstPriority();
    }, 50);
  }
  
  public checkIfFirstPriority(): boolean{
    const rulesetArray = this._parentRuleset.ruleset; 
    const currentPriority: number = this.rulePriority;
    const currentRuleIndexInRuleset: number = rulesetArray.map(rule => rule.priority).indexOf(currentPriority);
    const priorityOfRuleAbove = rulesetArray[currentRuleIndexInRuleset - 1] ? rulesetArray[currentRuleIndexInRuleset - 1].priority : 0;
    if(priorityOfRuleAbove == 0) return true;
    return false;
  }

  public moveRuleUpInPriority() {
    const rulesetArray = this._parentRuleset.ruleset; 
    const currentPriority: number = this.rulePriority;
    const currentRuleIndexInRuleset: number = rulesetArray.map(rule => rule.priority).indexOf(currentPriority);
    const ruleAbovePriority: number = rulesetArray[currentRuleIndexInRuleset - 1] ? rulesetArray[currentRuleIndexInRuleset - 1].getPriority() : 0;
    const ruleTwiceAbovePriority: number = rulesetArray[currentRuleIndexInRuleset - 2] ? rulesetArray[currentRuleIndexInRuleset - 2].getPriority() : 0;
    const newPriority: number = (ruleTwiceAbovePriority + ruleAbovePriority)/2;
    this.rulePriority = newPriority;
    this.rulePriorityChange.emit(newPriority);
    this._parentRuleset.priorityMoveUp();
    this._parentRuleset.updateRuleset();
  }
}