import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-ruleset-name',
  templateUrl: './ruleset-name.component.html',
  styleUrls: ['./ruleset-name.component.css']
})
export class RulesetNameComponent {
  @Input() name !: string;
  @Output() nameChange = new EventEmitter<string>();


  public setName(returnedValue: any): void {
    this.name = returnedValue.target.value;
    this.nameChange.emit(this.name);
  };
  
}