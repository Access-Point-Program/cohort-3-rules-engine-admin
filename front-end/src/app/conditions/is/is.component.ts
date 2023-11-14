import { Component } from '@angular/core';
import { ValueType } from '../../types/valueType';

@Component({
  selector: 'is-value',
  templateUrl: './is.component.html',
  styleUrls: ['./is.component.css']
})
export class IsComponent {

  public valueType = Object.values(ValueType);
  public returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    this.returnedValue = returnedValue;
  };
  public getReturnedValue(): string {
    return this.returnedValue;
  };
}