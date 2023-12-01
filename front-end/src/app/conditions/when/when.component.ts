import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FactType } from '../../types/factType';

@Component({
  selector: 'when-condition',
  templateUrl: './when.component.html',
  styleUrls: ['./when.component.css']
})
export class WhenComponent {

  @Input() whenValue!: string;
  @Output() whenValueChange = new EventEmitter<string>();
  @Input() neverEntry!: boolean;
  @Output() neverEntryChange = new EventEmitter<boolean>();
  public factType = Object.values(FactType);

  selectedOption(option: string): boolean {
    return this.whenValue === option;
  }

  public setWhenValue(returnedValue: string): void {
    this.neverEntry = false;
    this.neverEntryChange.emit(this.neverEntry);
    this.whenValue = returnedValue;
    this.whenValueChange.emit(this.whenValue);
  };
}