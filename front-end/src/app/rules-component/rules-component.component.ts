import { Component, Input, ViewChildren, OnInit, QueryList, AfterViewInit } from '@angular/core';
import { ConditionsComponent } from '../conditions/conditions.component';

@Component({
  selector: 'app-rules-component',
  templateUrl: './rules-component.component.html',
  styleUrls: ['./rules-component.component.css']
})
export class RulesComponentComponent implements OnInit, AfterViewInit {

  @ViewChildren(ConditionsComponent) viewChildren!: QueryList<ConditionsComponent>;
  @Input() priority!: number;
  @Input() public childrenConditions: ConditionsComponent[] = [new ConditionsComponent()];
  @Input() ruleset!: RulesComponentComponent[];
  @Input() givenRule!: RulesComponentComponent;
  @Input() ruleIndex!: number;
  @Input() thenValue: string = "";
  @Input() ruleDatabaseId?: number;
  neverThenEntry: boolean = true;
  ngOnInit() {
    if (this.givenRule != undefined){
      this.thenValue = this.givenRule.thenValue;
      this.neverThenEntry = this.givenRule.neverThenEntry;
    }
  }

  ngAfterViewInit(): void {
    this.childrenConditions = this.viewChildren.toArray();
  }

  public addCondition():void {
    this.childrenConditions.push(new ConditionsComponent());
  }

  public getPriority() {
    return this.priority;
  }

  public setPriority(newPriority: number){
    this.priority = newPriority;
  }
}