import { Component } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'when-condition',
  templateUrl: './when.component.html',
  styleUrls: ['./when.component.css']
})
export class WhenComponent {

  factType: Array<string> = [
    "FRONT",
    "RIGHT",
    "LEFT",
    "BEHIND"
  ];

  returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    console.log(returnedValue);
    this.returnedValue = returnedValue;
  };
}
