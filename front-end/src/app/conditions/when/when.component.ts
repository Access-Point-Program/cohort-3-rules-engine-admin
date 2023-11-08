import { Component } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { FactType } from '../../factType'


@Component({
  selector: 'when-condition',
  templateUrl: './when.component.html',
  styleUrls: ['./when.component.css']
})
export class WhenComponent {

  constructor(public factType: FactType) { }

  factTypes = Object.values(this.factType);

  returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    this.returnedValue = returnedValue;
  };
}
