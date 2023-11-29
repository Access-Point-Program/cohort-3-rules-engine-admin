import { Component, QueryList, ViewChildren, AfterViewInit, AfterViewChecked, ChangeDetectorRef } from '@angular/core';
import { RulesComponentComponent } from '../rules-component/rules-component.component';

@Component({
  selector: 'app-create-ruleset',
  templateUrl: './create-ruleset.component.html',
  styleUrls: ['./create-ruleset.component.css']
})
export class CreateRulesetComponent implements AfterViewInit, AfterViewChecked {

  @ViewChildren(RulesComponentComponent) viewChildren!: QueryList<RulesComponentComponent>;
  public ruleset: RulesComponentComponent[] = [new RulesComponentComponent()]
  public name:string = '';

  constructor(private changeDetectorRef: ChangeDetectorRef){}

  ngAfterViewInit(): void {
    this.ruleset = this.viewChildren.toArray();
    this.changeDetectorRef.detectChanges();
  }

  ngAfterViewChecked() {
    for(let i = 0; i < this.ruleset.length; i++){
      if(this.ruleset[i].priority == undefined || this.ruleset[i].priority == -1 ){
        this.ruleset[i].priority = this.viewChildren.toArray()[i].priority;
      }
    }
    // Console logs to be used for save button logic
    // console.log("After View checked Ruleset");
    // console.log(this.ruleset);
    // console.log(this.viewChildren.toArray());
  }

  forceUpdateRuleset() {
    this.ruleset = this.viewChildren.toArray();
    this.ruleset.forEach(rule => {
      rule.childrenConditions = rule.viewChildren.toArray();
    })
  }

  onAddRuleClick() {
    this.ruleset.push(new RulesComponentComponent());
  }

  priorityMoveUp() {
    this.forceUpdateRuleset();
  }
  priorityMoveDown() {
    this.forceUpdateRuleset();
  }

  saveButtonClick():RulesComponentComponent[]{
    this.forceUpdateRuleset();
    return this.ruleset;
  }

  getName():string{
    return this.name;
  }

  public updateRuleset() {
    this.ruleset.sort(function(a, b) {
      const priorityA = a.priority;
      const priorityB = b.priority;
      return (priorityA < priorityB) ? -1 : (priorityA > priorityB) ? 1 : 0;
    });
  }
}
