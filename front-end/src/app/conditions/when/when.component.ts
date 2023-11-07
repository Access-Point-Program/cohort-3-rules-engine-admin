import { Component } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'when-condition-selector',
  templateUrl: './when.component.html',
  styleUrls: ['./when.component.css']
})
export class WhenComponent {


  // constructor(public factType: FactType) {}

  factType: Array<string> = [
    "FRONT",
    "RIGHT",
    "LEFT",
    "BEHIND"
  ];

  returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    this.returnedValue = returnedValue;
  };


  public checkIfDropdownOpen(isOpen: boolean): void {
    isOpen ? console.log("True") : console.log("False");
  }
}
