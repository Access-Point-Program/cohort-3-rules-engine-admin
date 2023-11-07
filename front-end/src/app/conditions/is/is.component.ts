import { Component } from '@angular/core';

@Component({
  selector: 'is-value',
  templateUrl: './is.component.html',
  styleUrls: ['./is.component.css']
})
export class IsComponent {

  valueType: Array<string> = [
    "WALL",
    "EMPTY",
    "END",
  ];
  
  returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    console.log(returnedValue);
    this.returnedValue = returnedValue;
  };
}