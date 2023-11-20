import { Component, EventEmitter, Injector, Input, Output } from '@angular/core';
import { CreateRulesetComponent } from '../create-ruleset/create-ruleset.component';

@Component({
  selector: 'app-move-down-button',
  templateUrl: './move-down-button.component.html',
  styleUrls: ['./move-down-button.component.css']
})
export class MoveDownButtonComponent {

  @Input() rulePriority!: number;
  @Input() rulesetLength!: number;
  @Output() rulePriorityChange = new EventEmitter<number>();

  public _parentRuleset: CreateRulesetComponent;
  public buttonDisabled: boolean = false;

  constructor(private _injector: Injector) { 
    const _parent_parent: CreateRulesetComponent = this._injector.get<CreateRulesetComponent>(CreateRulesetComponent);
    this._parentRuleset = _parent_parent;
  }

  ngOnChanges(){
    setTimeout(() => {
      this.buttonDisabled = this.checkIfLastPriority();
    }, 50);
  }
  
  public checkIfLastPriority(): boolean{
    const rulesetArray = this._parentRuleset.ruleset; 
    const currentPriority: number = this.rulePriority;
    const currentRuleIndexInRuleset: number = rulesetArray.map(rule => rule.priority).indexOf(currentPriority);
    const priorityOfRuleBelow = rulesetArray[currentRuleIndexInRuleset + 1] ? rulesetArray[currentRuleIndexInRuleset + 1].priority : 0;
    if(priorityOfRuleBelow == 0) return true;
    return false;
  }

  public moveRuleDownInPriority() {
    const rulesetArray = this._parentRuleset.ruleset; 
    const currentPriority: number = this.rulePriority;
    const currentRuleIndexInRuleset: number = rulesetArray.map(rule => rule.priority).indexOf(currentPriority);
    const ruleBelowPriority: number = rulesetArray[currentRuleIndexInRuleset + 1] ? rulesetArray[currentRuleIndexInRuleset + 1].getPriority() : 0;
    const ruleTwiceBelowPriority: number = rulesetArray[currentRuleIndexInRuleset + 2] ? rulesetArray[currentRuleIndexInRuleset + 2].getPriority() : ruleBelowPriority + 1;
    const newPriority: number = (ruleTwiceBelowPriority + ruleBelowPriority)/2;
    this.rulePriority = newPriority;
    this.rulePriorityChange.emit(newPriority);
    this._parentRuleset.priorityMoveDown();
    this._parentRuleset.updateRuleset();
  }
}
