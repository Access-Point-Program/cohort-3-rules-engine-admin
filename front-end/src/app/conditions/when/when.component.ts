import { Component } from '@angular/core';
import { FactType } from '../../types/factType';

@Component({
  selector: 'when-condition',
  templateUrl: './when.component.html',
  styleUrls: ['./when.component.css']
})
export class WhenComponent {

  public factType = Object.values(FactType);
  public returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    this.returnedValue = returnedValue;
  };
}