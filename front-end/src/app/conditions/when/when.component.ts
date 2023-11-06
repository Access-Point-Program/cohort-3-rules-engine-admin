import { Component } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'when-condition-selector',
  templateUrl: './when.component.html',
  styleUrls: ['./when.component.css']
})
export class WhenComponent {
  constructor(private whenConditionDropDown: NgbDropdownModule) {}
}
