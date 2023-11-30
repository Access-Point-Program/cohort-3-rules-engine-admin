import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ValueType } from '../../types/valueType';

@Component({
  selector: 'is-value',
  templateUrl: './is.component.html',
  styleUrls: ['./is.component.css']
})
export class IsComponent {

  @Input() isValue!: string;
  @Output() isValueChange = new EventEmitter<string>();
  @Input() neverIsEntry!: boolean;
  @Output() neverIsEntryChange = new EventEmitter<boolean>();
  public valueType = Object.values(ValueType);

  selectedOption(option: string): boolean {
    return this.isValue === option;
  }

  public setIsValue(returnedValue: string): void {
    this.neverIsEntry = false;
    this.neverIsEntryChange.emit(this.neverIsEntry);
    this.isValue = returnedValue;
    this.isValueChange.emit(this.isValue);
  };
}