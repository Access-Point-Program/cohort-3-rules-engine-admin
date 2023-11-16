import { Component } from '@angular/core';
import { EventType } from '../types/eventType';

@Component({
  selector: 'then-event',
  templateUrl: './then.component.html',
  styleUrls: ['./then.component.css']
})
export class ThenComponent {

  public eventType = Object.values(EventType);
  private returnedValue: string = '';

  public setReturnedValue(returnedValue: string): void {
    this.returnedValue = returnedValue;
  };
  public getReturnedValue(): string {
    return this.returnedValue;
  };
}