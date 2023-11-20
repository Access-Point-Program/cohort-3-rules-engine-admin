import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'conditions',
  templateUrl: './conditions.component.html',
  styleUrls: ['./conditions.component.css']
})
export class ConditionsComponent implements OnInit {
  
  //givenCondition is a passed conditionComponent of the previous/current instance of this component
  @Input() givenCondition!: ConditionsComponent;
  public conditionWhenValue: string = "";
  conditionNeverEntry: boolean = true;
  public conditionIsValue: string = "";
  conditionNeverIsEntry: boolean = true;

  // When conditions component is initialized, it will update the whenValue and NeverEntry based on the previous instance
  ngOnInit() {
    this.conditionWhenValue = this.givenCondition.conditionWhenValue;
    this.conditionNeverEntry = this.givenCondition.conditionNeverEntry;
    this.conditionIsValue = this.givenCondition.conditionIsValue;
    this.conditionNeverIsEntry = this.givenCondition.conditionNeverIsEntry;
  }
}
