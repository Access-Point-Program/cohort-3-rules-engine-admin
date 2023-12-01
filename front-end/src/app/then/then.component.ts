import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EventType } from '../types/eventType';

@Component({
  selector: 'then-event',
  templateUrl: './then.component.html',
  styleUrls: ['./then.component.css']
})
export class ThenComponent {
  @Input() thenValue!: string;
  @Output() thenValueChange = new EventEmitter<string>();
  @Input() neverThenEntry!: boolean;
  @Output() neverThenEntryChange = new EventEmitter<boolean>();
  public eventType = Object.values(EventType);

  selectedOption(option: string): boolean {
    return this.thenValue === option;
  }

  public setThenValue(returnedValue: string): void {
    this.neverThenEntry = false;
    this.neverThenEntryChange.emit(this.neverThenEntry);
    this.thenValue = returnedValue;
    this.thenValueChange.emit(this.thenValue);
  };
}