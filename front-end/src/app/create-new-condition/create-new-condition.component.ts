import { Component } from '@angular/core';

@Component({
  selector: 'app-create-new-condition',
  templateUrl: './create-new-condition.component.html',
  styleUrls: ['./create-new-condition.component.css']
})
export class CreateNewConditionComponent {
  private conditionCounter: string[] = [""];

  public getConditionCounter():string[] {
    return this.conditionCounter;
  }

  public addCondition():void {
    this.conditionCounter.push("");
  }

  //<conditions *ngFor="let condition of getConditionCounter()"></conditions>
}
