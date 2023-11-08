import { Component } from '@angular/core';

@Component({
  selector: 'then-event',
  templateUrl: './then.component.html',
  styleUrls: ['./then.component.css']
})
export class ThenComponent {

  eventType: Array<string> = [
    "FORWARD",
    "RIGHT",
    "LEFT"
  ];
  
  returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    this.returnedValue = returnedValue;
  };

}
